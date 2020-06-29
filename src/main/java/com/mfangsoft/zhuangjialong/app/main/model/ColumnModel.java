package com.mfangsoft.zhuangjialong.app.main.model;

import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月28日
* 
*/

public class ColumnModel<T> extends ResponseMessage<T>{

	private String column_value;

	/**
	 * @return the column_value
	 */
	public String getColumn_value() {
		return column_value;
	}

	/**
	 * @param column_value the column_value to set
	 */
	public void setColumn_value(String column_value) {
		this.column_value = column_value;
	}
	
}
