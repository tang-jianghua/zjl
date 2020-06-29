package com.mfangsoft.zhuangjialong.integration.newproduct.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BrandProductEntity  extends  BaseBrandProductEntity{
	
	
	private Map<String,Object> color;
	
	
	private List<BrandProdSalesAttrEntity> prodSalesAttrEntity;
	
	
	private List<BaseBrandProdSalesAttrEntity> serverprodSalesAttrEntity;
	
	

	public List<BaseBrandProdSalesAttrEntity> getServerprodSalesAttrEntity() {
		

		List<BaseBrandProdSalesAttrEntity> attrEntities = new ArrayList<>();
		
		
		if(prodSalesAttrEntity!=null&&prodSalesAttrEntity.size()>0){
			
			
			for (BrandProdSalesAttrEntity brandProdSalesAttrEntity : prodSalesAttrEntity) {
				List<BaseBrandProdSalesAttrEntity> a=brandProdSalesAttrEntity.getStandardArray();
				for (BaseBrandProdSalesAttrEntity brandProdSalesAttrEntity2 : a) {
					
					BrandProdSalesAttrEntity attrEntity = new BrandProdSalesAttrEntity();
					attrEntity.setId(brandProdSalesAttrEntity2.getId());
					attrEntity.setColor(brandProdSalesAttrEntity.getColor());
					attrEntity.setColor_url(brandProdSalesAttrEntity.getColor_url());
					
					attrEntity.setModel(brandProdSalesAttrEntity2.getModel());
					
					attrEntity.setPrice(brandProdSalesAttrEntity2.getPrice());
					attrEntity.setStock(brandProdSalesAttrEntity2.getStock());
					attrEntity.setStandard(brandProdSalesAttrEntity2.getStandard());
					attrEntities.add(attrEntity);
				}
			}
			serverprodSalesAttrEntity=attrEntities;
			
			}
		
		return serverprodSalesAttrEntity;
	}



	public void setServerprodSalesAttrEntity(List<BaseBrandProdSalesAttrEntity> serverprodSalesAttrEntity) {
		this.serverprodSalesAttrEntity = serverprodSalesAttrEntity;
	}



	private List<BaseBrandProdImgEntity> baseBrandProdImgEntities;
	
	
	
	private List<BaseBrandProdSeriesEntity> baseBrandProdSeriesEntities;
	
	
	
	private List<BrandProdAttrValueEntity> brandProdAttrValueEntities;
	
	public Map<String, Object> getColor() {
		return color;
	}



	public void setColor(Map<String, Object> color) {
		this.color = color;
	}
	public List<BrandProdSalesAttrEntity> getProdSalesAttrEntity() {
		
		
		return prodSalesAttrEntity;
	}



	public void setProdSalesAttrEntity(List<BrandProdSalesAttrEntity> prodSalesAttrEntity) {
		this.prodSalesAttrEntity = prodSalesAttrEntity;
	}







	
	public List<BaseBrandProdImgEntity> getBaseBrandProdImgEntities() {
		return baseBrandProdImgEntities;
	}



	public void setBaseBrandProdImgEntities(List<BaseBrandProdImgEntity> baseBrandProdImgEntities) {
		this.baseBrandProdImgEntities = baseBrandProdImgEntities;
	}



	public List<BaseBrandProdSeriesEntity> getBaseBrandProdSeriesEntities() {
		return baseBrandProdSeriesEntities;
	}



	public void setBaseBrandProdSeriesEntities(List<BaseBrandProdSeriesEntity> baseBrandProdSeriesEntities) {
		this.baseBrandProdSeriesEntities = baseBrandProdSeriesEntities;
	}



	public List<BrandProdAttrValueEntity> getBrandProdAttrValueEntities() {
		return brandProdAttrValueEntities;
	}



	public void setBrandProdAttrValueEntities(List<BrandProdAttrValueEntity> brandProdAttrValueEntities) {
		this.brandProdAttrValueEntities = brandProdAttrValueEntities;
	}
	
	
	
	private List<Map<String,Object>>  productViewAttrAndVaule;



	public List<Map<String, Object>> getProductViewAttrAndVaule() {
		return productViewAttrAndVaule;
	}



	public void setProductViewAttrAndVaule(List<Map<String, Object>> productViewAttrAndVaule) {
		this.productViewAttrAndVaule = productViewAttrAndVaule;
	}
	

}
