package com.mfangsoft.zhuangjialong.common.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.app.product.model.ArrModel;

@JsonInclude(value=Include.NON_NULL)
public class Page<T> {
	

	
	/**
	 * 页号
	 */
	private Integer  page;
	/**
	 * 每页条数
	 */
	private Integer  pageSize;
	/**
	 * 总条数
	 */
	private Integer  total;
	
	private Object param;
	
	
	private Boolean  ispage =true;
	
	
	public Boolean getIspage() {
		return ispage;
	}

	public void setIspage(Boolean ispage) {
		this.ispage = ispage;
	}

	/**
	 * @return the param
	 */
	public Object getParam() {
		return param;
	}

	/**
	 * @param param the param to set
	 */
	public void setParam(Object param) {
		this.param = param;
	}

	

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
		
		
	}

	private List<T>  data;
	

	/**
	 * 根据当前页和每页大小拿到分页数据,仅限于对内存数据处理
	 * 
	 * @param pageNo
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @param lstArticle
	 *            数据
	 * @return List<productsForOnePage>
	 */
	public Page setDataByPageNo(int pageNo, int pageSize, List<T> allList) {
		pageSize = pageSize <= 0 || pageSize > allList.size()? allList.size() : pageSize;
		if(pageNo <= 0){
			this.page = 1;
			this.pageSize = pageSize;
			this.setData(allList.subList(0, pageSize));
			return this;
		}
		
		int totalpage = pageNo * pageSize > allList.size() ? allList.size() : pageNo * pageSize;
		this.page = pageNo;
		this.pageSize = pageSize;
		this.setData(allList.subList((pageNo - 1) * pageSize, totalpage));
		return this;
	}
	
	public static void main(String[] args) {
		Page page = new Page<>();
		List<String> list = new ArrayList<String>();
		for(int i= 0;i < 10; i ++){
			list.add("e" + i);
		}
		page = page.setDataByPageNo(0, 0, list);
		
		for(Object e : page.getData()){
			System.out.println(e);
		}
		
	}
	
	
	private ArrModel arrModel;

	/**
	 * @return the arrModel
	 */
	public ArrModel getArrModel() {
		return arrModel;
	}

	/**
	 * @param arrModel the arrModel to set
	 */
	public void setArrModel(ArrModel arrModel) {
		this.arrModel = arrModel;
	}
	
}
