package com.mfangsoft.zhuangjialong.app.jumptoweb.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年7月14日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class ParameterModel {
          private Long id;
          private Integer type;//跳转类型
          private Map<String, Object> user_data;//公共参数
          private String data_param;//参数
          private String link_url;//链接地址
          
          
		/**
		 * @return the data_param
		 */
		public String getData_param() {
			return data_param;
		}
		/**
		 * @param data_param the data_param to set
		 */
		public void setData_param(String data_param) {
			this.data_param = data_param;
		}
		/**
		 * @return the link_url
		 */
		public String getLink_url() {
			return link_url;
		}
		/**
		 * @param link_url the link_url to set
		 */
		public void setLink_url(String link_url) {
			this.link_url = link_url;
		}
		/**
		 * @return the id
		 */
		public Long getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(Long id) {
			this.id = id;
		}
		/**
		 * @return the type
		 */
		public Integer getType() {
			return type;
		}
		/**
		 * @param type the type to set
		 */
		public void setType(Integer type) {
			this.type = type;
		}
		/**
		 * @return the user_data
		 */
		public Map<String, Object> getUser_data() {
			return user_data;
		}
		/**
		 * @param user_data the user_data to set
		 */
		public void setUser_data(Map<String, Object> user_data) {
			this.user_data = user_data;
		}

	
          
}
