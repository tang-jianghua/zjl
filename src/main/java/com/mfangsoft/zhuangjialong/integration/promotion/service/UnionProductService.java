package com.mfangsoft.zhuangjialong.integration.promotion.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionProduct;

public interface UnionProductService {
	Page<Map<String, Object>> getUnionProductListPage(Page<Map<String, Object>> page);
	List<Map<String, Object>> getUnionProductList(Page<Map<String, Object>> page);
	Page<Map<String, Object>> getNotSelectedBrandProductPage(Page<Map<String, Object>> page);
	Boolean addUnionProductList(List<UnionProduct> unionProducts);
}
