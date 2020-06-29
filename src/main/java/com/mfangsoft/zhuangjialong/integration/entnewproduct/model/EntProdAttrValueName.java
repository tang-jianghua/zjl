package com.mfangsoft.zhuangjialong.integration.entnewproduct.model;

public class EntProdAttrValueName implements java.io.Serializable {
	
	
	private String space;
	
	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getMadin() {
		return madin;
	}

	public void setMadin(String madin) {
		this.madin = madin;
	}

	private String style;
	
	private  String madin;
	
	private Long product_id;

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}
	

}
