package com.mfangsoft.zhuangjialong.integration.entnewproduct.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdSalesAttrEntity;

/**
 * table t_biz_ent_prod_salesattr
 *
 * @MLTH_generated do_not_delete_during_merge
 */
public class EntProdSalesAttrEntity extends BaseEntProdSalesAttrEntity {
	
	
	private List<BaseEntProdSalesAttrEntity> standardArray;

	

	public List<BaseEntProdSalesAttrEntity> getStandardArray() {
		return standardArray;
	}

	public void setStandardArray(List<BaseEntProdSalesAttrEntity> standardArray) {
		this.standardArray = standardArray;
		
		
		
		
	}
  
}