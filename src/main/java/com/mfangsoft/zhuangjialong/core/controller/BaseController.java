package com.mfangsoft.zhuangjialong.core.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.google.common.collect.Maps;
import com.mfangsoft.zhuangjialong.common.utils.StringUtils;
import com.mfangsoft.zhuangjialong.core.utils.StringEscapeEditor;


public class BaseController {

	 /**
     * String of "success"
     */
    public static final String SUCCESS = "success";
    /**
     * String of "message"
     */
    public static final String MESSAGE = "message";
    /**
     * String of "account"
     */
    public static final String ACCOUNT = "account";
    /**
     * String of "操作失败！"
     */
    public static final String OPER_ERROR = "操作失败！";
    /**
     * String of "操作失败！"
     */
    public static final String OPER_SUCCESS = "操作成功！";
    /**
     * String of "校验失败！"
     */
    public static final String VALIDATE_ERROR = "校验失败！";
    /**
     * 当前页数
     */
    public static String PAGE_NO = "pageNo";
    /**
     * 每页多少条
     */
    public static String PAGE_SIZE = "pageSize";
    /**
     * 起始条数
     */
    public static String START_INDEX = "startIndex";
    /**
     * 结束条数
     */
    public static String END_INDEX = "endIndex";
    /**
     * 排序字段
     */
    public static String TOTAL_COUNT = "totalCount";

    /**
     * 排序字段
     */
    public static String TOTAL_PAGES = "totalPages";

    /**
     * 排列顺序 asc升序；desc降序
     */
    public static String SORT_BY = "sortBy";
    /**
     * 排序字段
     */
    public static String ORDER_BY = "orderBy";
    /**
     * 正序
     */
    public static final String ASC = "asc";
    /**
     * 倒序
     */
    public static final String DESC = "desc";
    /**
     * 查询条件
     */
    public static Map<String, String[]> reqMap = Maps.newHashMap();

    /**
     * 构造查询条件，分页条件
     * 
     * @param request
     * @return Map<String, Object>
     */
    public static Map<String, Object> makePageSearch(HttpServletRequest request) {
        Map<String, Object> search = Maps.newHashMap();
        int pageNo = 0;
        int pageSize = 0;
        reqMap = request.getParameterMap();
        if (reqMap != null) {
            for (String key : reqMap.keySet()) {
                String[] values = reqMap.get(key);

                if (key.equals("rows")) {
                    pageSize = Integer.parseInt(values.length == 1 ? values[0].trim() : "20");
                }
                if (key.equals("page")) {
                    pageNo = Integer.parseInt(values.length == 1 ? values[0].trim() : "0");
                }
                if (values.length == 1 && !key.equals("page") && !key.equals("rows")) {
                    if (StringUtils.isNotEmpty(values[0].trim())) {
                        search.put(key, values[0].trim());
                    }
                }

            }
            search.put(PAGE_NO, pageNo);
            search.put(PAGE_SIZE, pageSize);
            int startIndex = pageNo > 0 ? (pageNo - 1) * pageSize : 0;
            int endIndex = pageNo * pageSize;
            search.put(START_INDEX, startIndex);
            search.put(END_INDEX, endIndex);
        }
        return search;
    }

    /**
     * 功能：验证字符串长度是否符合要求，一个汉字等于两个字符
     * @author fangli
     * @param strParameter 要验证的字符串
     * @param limitLength 验证的长度
     * @return 符合长度false 超出范围true
     */
    public boolean validateStrByLength(String strParameter, int limitLength) {
        int temp_int = 0;
        byte[] b = strParameter.getBytes();

        for (int i = 0; i < b.length; i++) {
            if (b[i] >= 0) {
                temp_int = temp_int + 1;
            } else {
                temp_int = temp_int + 2;
                i++;
            }
        }
        if (temp_int > limitLength) {
            return true;
        } else {
            return false;
        }
    }
    
    

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));

        /**
         * 防止XSS攻击
         */
        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
    }

   
    
}
