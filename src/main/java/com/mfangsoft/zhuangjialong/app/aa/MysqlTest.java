package com.mfangsoft.zhuangjialong.app.aa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月21日
* 
*/

public class MysqlTest {

	 public static final String url = "jdbc:mysql://47.93.78.124:3306/koala_db?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true";  
	    public static final String name = "com.mysql.jdbc.Driver";  
	    public static final String user = "root";  
	    public static final String password = "YrpPV6BfoAN0fkGKr7bgrMoh7z!L&pv6";  
	  
	    public static Connection conn = null;  
	    public static PreparedStatement pst = null;  
	  
	    public static void DBHelper(String sql) {  
	        try {  
	            Class.forName(name);//指定连接类型  
	            conn = DriverManager.getConnection(url, user, password);//获取连接  
	            pst = conn.prepareStatement(sql);//准备执行语句  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	  
	    public  void close() {  
	        try {  
	            this.conn.close();  
	            this.pst.close();  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    
	    
	    public static  List<Map<String, String>> getList(){
	    	String sql = "select bp.`id`,p.`check_state`  from `t_biz_brand_product` bp left join `t_biz_partner` p on bp.`partner_id` = p.`id`";
	    	String sql2 = "SELECT p.`id`,b.`state`  FROM `t_biz_brand_product` p LEFT JOIN `t_biz_brand` b ON p.`brand_id` = b.`id`";
	    	List<Map<String,String>> list = new ArrayList<>();
	    	try {
	    		Class.forName(name);
	    		conn = DriverManager.getConnection(url, user, password);//获取连接  
	    		pst = conn.prepareStatement(sql2);//准备执行语句  
	    		ResultSet resultSet = pst.executeQuery();
	    		while(resultSet.next()){
	    			Map<String, String> map =new HashMap<>();
	    			map.put("product_id", resultSet.getLong("id")+"");
	    			map.put("set",resultSet.getInt("state")+"");
	    			list.add(map);
	    		}
	    		conn.close();  
	    		pst.close();  
	    	} catch (Exception e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}//指定连接类型  
	    	return list;
	    }
	 public static  List<Map<String, String>> getImgList(){
	    	String sql = "select id,imgurl from `t_biz_brand_prod_img` where id >101330";
			List<Map<String,String>> list = new ArrayList<>();
			try {
				Class.forName(name);
				conn = DriverManager.getConnection(url, user, password);//获取连接  
				pst = conn.prepareStatement(sql);//准备执行语句  
				ResultSet resultSet = pst.executeQuery();
				while(resultSet.next()){
					Map<String, String> map =new HashMap<>();
				    map.put("id", resultSet.getLong("id")+"");
				    map.put("imgurl",resultSet.getString("imgurl"));
				    list.add(map);
				}
				conn.close();  
	            pst.close();  
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//指定连接类型  
			return list;
	    }
	 public static void main(String[] args) {
		 List<Map<String, String>> list = getList();
		 
	 }
}
