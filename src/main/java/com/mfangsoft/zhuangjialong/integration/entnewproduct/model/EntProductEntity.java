package com.mfangsoft.zhuangjialong.integration.entnewproduct.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProdSalesAttrEntity;

public class EntProductEntity extends BaseEntProductEntity {
	
	
    private List<EntProdSalesAttrEntity> prodSalesAttrEntity;
	
	

	public List<EntProdSalesAttrEntity> getProdSalesAttrEntity() {
		return prodSalesAttrEntity;
	}



	public void setProdSalesAttrEntity(List<EntProdSalesAttrEntity> prodSalesAttrEntity) {
		this.prodSalesAttrEntity = prodSalesAttrEntity;
	}



	public List<BaseEntProdImgEntity> getBaseBrandProdImgEntities() {
		return baseBrandProdImgEntities;
	}



	public void setBaseBrandProdImgEntities(List<BaseEntProdImgEntity> baseBrandProdImgEntities) {
		this.baseBrandProdImgEntities = baseBrandProdImgEntities;
	}



	public List<BaseEntProdSeriesEntity> getBaseBrandProdSeriesEntities() {
		return baseBrandProdSeriesEntities;
	}



	public void setBaseBrandProdSeriesEntities(List<BaseEntProdSeriesEntity> baseBrandProdSeriesEntities) {
		this.baseBrandProdSeriesEntities = baseBrandProdSeriesEntities;
	}



	public List<EntProdAttrValueEntity> getBrandProdAttrValueEntities() {
		return brandProdAttrValueEntities;
	}



	public void setBrandProdAttrValueEntities(List<EntProdAttrValueEntity> brandProdAttrValueEntities) {
		this.brandProdAttrValueEntities = brandProdAttrValueEntities;
	}



	private List<BaseEntProdImgEntity> baseBrandProdImgEntities;
	
	
	
	private List<BaseEntProdSeriesEntity> baseBrandProdSeriesEntities;
	
	
	
	private List<EntProdAttrValueEntity> brandProdAttrValueEntities;
	
	
private List<BaseEntProdSalesAttrEntity> serverprodSalesAttrEntity;
	
	

	public List<BaseEntProdSalesAttrEntity> getServerprodSalesAttrEntity() {
		

		List<BaseEntProdSalesAttrEntity> attrEntities = new ArrayList<>();
		
		
		if(prodSalesAttrEntity!=null&&prodSalesAttrEntity.size()>0){
			
			
			for (EntProdSalesAttrEntity brandProdSalesAttrEntity : prodSalesAttrEntity) {
				List<BaseEntProdSalesAttrEntity> a=brandProdSalesAttrEntity.getStandardArray();
				for (BaseEntProdSalesAttrEntity brandProdSalesAttrEntity2 : a) {
					
					EntProdSalesAttrEntity attrEntity = new EntProdSalesAttrEntity();
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



	public void setServerprodSalesAttrEntity(List<BaseEntProdSalesAttrEntity> serverprodSalesAttrEntity) {
		this.serverprodSalesAttrEntity = serverprodSalesAttrEntity;
		
	}
	
	public List<Map<String, Object>> getProductViewAttrAndVaule() {
		return productViewAttrAndVaule;
	}



	public void setProductViewAttrAndVaule(List<Map<String, Object>> productViewAttrAndVaule) {
		this.productViewAttrAndVaule = productViewAttrAndVaule;
	}



	private List<Map<String,Object>>  productViewAttrAndVaule;

}
