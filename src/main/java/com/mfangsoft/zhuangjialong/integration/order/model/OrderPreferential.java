package com.mfangsoft.zhuangjialong.integration.order.model;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class OrderPreferential  implements java.io.Serializable{
	
	
	
	public String getCouponsName() {
		return couponsName;
	}

	public void setCouponsName(String couponsName) {
		this.couponsName = couponsName;
	}

	public Double getCouponsValue() {
		return couponsValue;
	}

	public void setCouponsValue(Double couponsValue) {
		this.couponsValue = couponsValue;
	}

	public String getRadBagName() {
		return radBagName;
	}

	public void setRadBagName(String radBagName) {
		this.radBagName = radBagName;
	}

	public Double getRadBagValue() {
		return radBagValue;
	}

	public void setRadBagValue(Double radBagValue) {
		this.radBagValue = radBagValue;
	}

	public List<Map<String, Object>> getUnionCouponslist() {
		return unionCouponslist;
	}

	public void setUnionCouponslist(List<Map<String, Object>> unionCouponslist) {
		this.unionCouponslist = unionCouponslist;
	}

	public String getShopCouponsName() {
		return shopCouponsName;
	}

	public void setShopCouponsName(String shopCouponsName) {
		this.shopCouponsName = shopCouponsName;
	}

	public Double getShopCouponsValue() {
		return shopCouponsValue;
	}

	public void setShopCouponsValue(Double shopCouponsValue) {
		this.shopCouponsValue = shopCouponsValue;
	}

	public String getZheKouName() {
		return zheKouName;
	}

	public void setZheKouName(String zheKouName) {
		this.zheKouName = zheKouName;
	}

	public Double getZheKouValue() {
		return zheKouValue;
	}

	public void setZheKouValue(Double zheKouValue) {
		this.zheKouValue = zheKouValue;
	}

	private String couponsName;
	
	private Double couponsValue;
	
	
	private String radBagName;
	
	private Double radBagValue;
	
	private String zheKouName;
	
	private Double zheKouValue;
	
    private List<Map<String,Object>>  unionCouponslist;
    
    
    private String shopCouponsName;
    
    private Double shopCouponsValue;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer  buffer  = new StringBuffer();
		if(StringUtils.isNotEmpty(this.couponsName))
		{
			buffer.append("优惠劵");
			buffer.append("");
			buffer.append(""+this.couponsValue+"   ");
		}
		if(StringUtils.isNotEmpty(this.radBagName))
		{
			buffer.append("红包");
			buffer.append("");
			buffer.append(""+this.radBagValue+"   ");
		}
		
		if(StringUtils.isNotEmpty(this.zheKouName))
		{
			buffer.append("折扣劵");
			buffer.append("");
			buffer.append(""+this.zheKouValue+"   ");
		}
		
		if(unionCouponslist!=null&&unionCouponslist.size()>0)
		{
			for (Map<String, Object> map : unionCouponslist) {
				
				buffer.append("现金劵");
				buffer.append("");
				buffer.append(""+map.get("value")+"   ");
				
			}
		}
		
		if(StringUtils.isNotEmpty(this.shopCouponsName))
		{
			buffer.append(this.shopCouponsName);
			buffer.append("");
			buffer.append(""+this.shopCouponsValue+"   ");
		}
		
		
		return buffer.toString();
	}
    
   

}
