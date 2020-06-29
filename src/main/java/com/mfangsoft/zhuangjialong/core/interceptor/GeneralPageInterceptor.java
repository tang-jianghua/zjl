/**
 * Copyright (C) 2015 StateUnion, Inc. All Rights Reserved.
 */
package com.mfangsoft.zhuangjialong.core.interceptor;


import static com.mfangsoft.zhuangjialong.core.controller.BaseController.END_INDEX;
import static com.mfangsoft.zhuangjialong.core.controller.BaseController.PAGE_SIZE;
import static com.mfangsoft.zhuangjialong.core.controller.BaseController.START_INDEX;
import static com.mfangsoft.zhuangjialong.core.controller.BaseController.TOTAL_COUNT;
import static com.mfangsoft.zhuangjialong.core.controller.BaseController.TOTAL_PAGES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.core.controller.BaseController;


/**
 * 
 * 分页拦截器 首先拦截prepare方法来改分页SQL，来做count查询。然后我拦截handleResultSets方法来获取最后的处理结果，将结果放到Page对象中
 * 
 * @author fangli
 *
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class,Integer.class }) })
public class GeneralPageInterceptor implements Interceptor {
    private final static Logger logger = LoggerFactory.getLogger(GeneralPageInterceptor.class);

    private String dialect = "mysql";

    private String pageSqlId = ".*Page$";
    
    private DefaultObjectWrapperFactory defaultObjectWrapperFactory = new DefaultObjectWrapperFactory(); 
    
    private DefaultReflectorFactory defaultReflectorFactory = new DefaultReflectorFactory();

    @SuppressWarnings("unchecked")

    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            
            
            MetaObject metaStatementHandler = MetaObject.forObject(statementHandler,null,defaultObjectWrapperFactory,defaultReflectorFactory);
            // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环
            // 可以分离出最原始的的目标类)
            while (metaStatementHandler.hasGetter("h")) {
                Object object = metaStatementHandler.getValue("h");
                metaStatementHandler = MetaObject.forObject(object,null,null,null);
            }
            // 分离最后一个代理对象的目标类
            while (metaStatementHandler.hasGetter("target")) {
                Object object = metaStatementHandler.getValue("target");
                metaStatementHandler = MetaObject.forObject(object,null,defaultObjectWrapperFactory,defaultReflectorFactory);
            }
            MappedStatement mappedStatement =(MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            // 只重写需要分页的sql语句。通过MappedStatement的ID匹配，默认重写以Page结尾的
            // MappedStatement的sql
            if (mappedStatement.getId().matches(pageSqlId)) {
                BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
                Object parameterObject = boundSql.getParameterObject();
                if (parameterObject == null) {
                    throw new NullPointerException("parameterObject is null!");
                } else {
                    // 分页参数作为参数对象parameterObject的一个属性
                    Page search =(Page) metaStatementHandler.getValue("delegate.boundSql.parameterObject");
                    
                    
                    if(search.getIspage()){
                    	
                    String sql = boundSql.getSql();
                    // 重写sql
                    String pageSql = buildPageSql(sql, search);
                    metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
                    // 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
                    metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
                    metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
                    Connection connection = (Connection) invocation.getArgs()[0];
                    // 重设分页参数里的总页数等
                    setPageParameter(sql, connection, mappedStatement, boundSql, search);
                    }
                }
            }
        }
        return invocation.proceed();
    }


    public Object plugin(Object target) {
        if (target instanceof StatementHandler || target instanceof ResultSetHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

 
    public void setProperties(Properties properties) {

    }

    /**
     * 修改原SQL为分页SQL
     * 
     * @param sql
     * @param page
     * @return
     */
    private String buildPageSql(String sql, Page page) {
        if (page != null) {
            StringBuilder pageSql = new StringBuilder();
            if ("mysql".equals(dialect)) {
                pageSql = buildPageSqlForMysql(sql, page);
            } else if ("oracle".equals(dialect)) {
                //pageSql = buildPageSqlForOracle(sql, search);
            } else {
                return sql;
            }
            return pageSql.toString();
        } else {
            return sql;
        }
    }

    public StringBuilder buildPageSqlForMysql(String sql, Page page) {
        StringBuilder pageSql = new StringBuilder(2000);
        pageSql.append(sql);
        
        pageSql.append(" limit " + (page.getPage()-1)*page.getPageSize() + "," + page.getPageSize());
        return pageSql;
    }

//    public StringBuilder buildPageSqlForOracle(String sql, Map<String, Object> search) {
//        StringBuilder pageSql = new StringBuilder(2000);
//        pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
//        pageSql.append(sql);
//        pageSql.append(" ) temp where rownum <= ").append(search.get(END_INDEX));
//        pageSql.append(") where row_id > ").append(search.get(START_INDEX));
//        return pageSql;
//    }

    /**
     * 从数据库里查询总的记录数并计算总页数，回写进分页参数<code>Page</code>,这样调用 者就可用通过 分页参数<code>Page</code>获得相关信息。
     * 
     * 如果分页的数据带in 查询条件的 需要 注意 <foreach item="id"  item的值要为id 
     * 
     * 
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws NoSuchFieldException 
     * @throws SecurityException 
     */
    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement,
            BoundSql boundSql, Page page) {
        // 记录总记录数
        String countSql = "select count(1) from (" + sql + ") tase";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS =
                    new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(),boundSql.getParameterObject());
            // 修改countBS的 参数值
      
            ObjectFactory a ;
            MetaObject selectpara = (MetaObject) MetaObject.forObject(boundSql,null,defaultObjectWrapperFactory,defaultReflectorFactory).getValue("metaParameters");
            if(null != selectpara){
                MetaObject count = MetaObject.forObject(countBS,null,defaultObjectWrapperFactory,defaultReflectorFactory);
                count.setValue("metaParameters", selectpara);
            }
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            //page.setTotalCount(totalCount);
            //search.put(TOTAL_COUNT, totalCount);
            int pageSize = (Integer) page.getPageSize();
            int totalPage = totalCount / pageSize + ((totalCount % pageSize == 0) ? 0 : 1);
            page.setTotal(totalCount);
        } catch (SQLException e) {
        	e.printStackTrace();
            logger.error("Ignore this exception", e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
            try {
                countStmt.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
        }
    }

    /**
     * 对SQL参数(?)设值
     * 
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
            Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = mappedStatement.getConfiguration().newParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

    /**
     * @return the dialect
     */
    public String getDialect() {
        return dialect;
    }

    /**
     * @param dialect the dialect to set
     */
    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    /**
     * @return the pageSqlId
     */
    public String getPageSqlId() {
        return pageSqlId;
    }

    /**
     * @param pageSqlId the pageSqlId to set
     */
    public void setPageSqlId(String pageSqlId) {
        this.pageSqlId = pageSqlId;
    }

}
