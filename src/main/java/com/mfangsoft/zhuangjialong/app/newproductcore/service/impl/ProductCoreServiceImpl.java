package com.mfangsoft.zhuangjialong.app.newproductcore.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.aa.MysqlTest;
import com.mfangsoft.zhuangjialong.app.newproductcore.mapper.NewProductCoreModelMapper;
import com.mfangsoft.zhuangjialong.app.newproductcore.mapper.SuperCore;
import com.mfangsoft.zhuangjialong.app.newproductcore.model.NewProductCoreModel;
import com.mfangsoft.zhuangjialong.app.newproductcore.service.ProductCoreService;
import com.mfangsoft.zhuangjialong.app.order.mapper.OrderProductEntityMapper;
import com.mfangsoft.zhuangjialong.app.salesnum.mapper.SalesNumEntityMapper;
import com.mfangsoft.zhuangjialong.app.salesnum.model.SalesNumEntity;
import com.mfangsoft.zhuangjialong.common.utils.DateUtils;
import com.mfangsoft.zhuangjialong.common.utils.MySolrDoc;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandProdAttrValueEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandProdImgEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandProdSalesAttrEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandProdSeriesEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionZhekouEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouParam;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年8月10日
* 
*/
@Service
public class ProductCoreServiceImpl extends SuperCore implements ProductCoreService{
     @Autowired
	NewProductCoreModelMapper newProductCoreModelMapper;
     @Autowired
     BrandProdSeriesEntityMapper brandProdSeriesEntityMapper;
 	@Autowired
 	BrandProdImgEntityMapper brandProdImgEntityMapper;
 	@Autowired
 	BrandProdSalesAttrEntityMapper brandProdSalesAttrEntityMapper;
 	@Autowired
 	BrandNewProductEntityMapper brandProductEntityMapper;
 	@Autowired
 	BrandProdAttrValueEntityMapper brandProdAttrValueEntityMapper;
 	@Autowired
 	OrderProductEntityMapper orderProductEntityMapper;
 	@Autowired
 	SalesNumEntityMapper salesNumEntityMapper;
 	
 	
	@Override
	public boolean addNewProductDoc(Long product_id) {
		NewProductCoreModel newProductCoreModel = newProductCoreModelMapper.selectProductCoreByProductId(product_id);
		if(newProductCoreModel==null){
			return false;
		}
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		SolrInputDocument document= new SolrInputDocument();
		document.addField("product_id", product_id+"");
		document.addField("series_ids", brandProdSeriesEntityMapper.selectByProductId(product_id));
		document.addField("images", brandProdImgEntityMapper.selectImgByProductId(product_id));
		document.addField("case_images", brandProdImgEntityMapper.selectCaseImgByProductId(product_id));
		document.addField("product_name", newProductCoreModel.getProduct_name());
		document.addField("description", newProductCoreModel.getDescription());
			document.addField("image_url", newProductCoreModel.getImgurl());
			document.addField("brand_id", newProductCoreModel.getBrand_id());
			document.addField("hot_line", newProductCoreModel.getHot_line());
			document.addField("brand_name", newProductCoreModel.getBrand_name());
			document.addField("enterprise_id", newProductCoreModel.getEnterprise_id());
			document.addField("brand_imgurl", newProductCoreModel.getBrand_imgurl());
			document.addField("class_id", newProductCoreModel.getClass_id());
			document.addField("class_name", newProductCoreModel.getClass_name());
			document.addField("citypartner_id", newProductCoreModel.getCitypartner_id());
			document.addField("region_code", newProductCoreModel.getRegion_code());
			document.addField("state", newProductCoreModel.getState());
           if(newProductCoreModel.getCreate_time()!=null){
        	   document.addField("create_time", DateUtils.formatDate_(newProductCoreModel.getCreate_time()));
           }
			if(newProductCoreModel.getNew_time()!=null){
				document.addField("new_time", DateUtils.formatDateYmd(newProductCoreModel.getNew_time()));
			}
			document.addField("isnew", newProductCoreModel.getIsnew());
			document.addField("sort", newProductCoreModel.getSort());
			document.addField("unit", newProductCoreModel.getUnit());
	    	document.addField("sale_num", newProductCoreModel.getSales_num().intValue());
			document.addField("sales_model", newProductCoreModel.getSales_model());
			document.addField("percent", newProductCoreModel.getPercent());
			document.addField("min_price", newProductCoreModel.getMin_price());
			document.addField("min_prom_price", newProductCoreModel.getMin_price());
			document.addField("max_price", newProductCoreModel.getMax_price());
			document.addField("color", brandProdSalesAttrEntityMapper.getColorsByProductId(product_id));
			document.addField("color_model", brandProdSalesAttrEntityMapper.getColorModelByProductId(product_id));
			document.addField("model", brandProdSalesAttrEntityMapper.getModelByProductId(product_id));
			Map<String, String> arrtMap = brandProdAttrValueEntityMapper.selectValuesByProductIdForProductCore(product_id);
			document.addField("attr_value", arrtMap.get("attr_value").split(","));
			document.addField("attr_ids", arrtMap.get("attr_ids").split(","));
			document.addField("attr_names", arrtMap.get("attr_names").split(","));
			document.addField("value_ids", arrtMap.get("value_ids").split(","));
			docs.add(document);
			super.initProduct();
			try {
				UpdateResponse add = solr.add(docs);
				solr.commit();
				if(add.getStatus()!=0){
					System.out.println("添加solr错误信息："+add.getStatus());
				}
			} catch (SolrServerException | IOException e) {
				e.printStackTrace();
				return false;
			}
			
		return true;
	}

	@Override
	public boolean deleteNewProductDoc(List<String> product_ids) {
		super.initProduct();
		try {
			UpdateResponse deleteById = solr.deleteById(product_ids);
			if(deleteById.getStatus()!=0){
				System.out.println("添加solr错误信息："+deleteById.getStatus());
			}
			solr.commit();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	@Override
	public boolean deleteNewProductDoc(Long product_id) {
     super.initProduct();
     try {
		UpdateResponse deleteById = solr.deleteById(product_id+"");
		if(deleteById.getStatus()!=0){
			System.out.println("添加solr错误信息："+deleteById.getStatus());
		}
		solr.commit();
	} catch (SolrServerException | IOException e) {
		e.printStackTrace();
		return false;
	}
		
		return true;
	}

	@Override
	public boolean updateNewProductDocs(List<Long> product_ids) {
		boolean b = this.deleteNewProductDocs(product_ids);
		if(!b){
			return false;
		}
		for (int i = 0; i < product_ids.size(); i++) {
			boolean addProductDoc = this.addNewProductDoc(product_ids.get(i));
			if(!addProductDoc){
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean updateNewProductDoc(Long product_id) {
		boolean deleteProductDoc = this.deleteNewProductDoc(product_id);
		boolean addProductDoc = this.addNewProductDoc(product_id);
		if(deleteProductDoc&&addProductDoc){
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteNewProductDocs(List<Long> product_ids) {
		List<String> ids =new ArrayList<>();
		for (int i = 0; i < product_ids.size(); i++) {
			ids.add(product_ids.get(i)+"");
		}
	    super.initProduct();
	     try {
			UpdateResponse deleteById = solr.deleteById(ids);
			if(deleteById.getStatus()!=0){
				System.out.println("添加solr错误信息："+deleteById.getStatus());
			}
			solr.commit();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
			return false;
		}
			return true;
	}


	@Override
	public boolean updateSalesNumDocs(List<SalesNumEntity> salesNumEntities) {
		for (SalesNumEntity salesNumEntity : salesNumEntities) {
			super.initProduct();
			Map<String, Object> map = new HashMap<>();
					map.put("set", salesNumEntity.getSales_num().intValue());
					SolrInputDocument document= new SolrInputDocument();
					document.addField("product_id", salesNumEntity.getProduct_id()+"");
					document.addField("sale_num", map);	
					try {
						solr.add(document);
						solr.commit();
					} catch (SolrServerException | IOException e) {
						e.printStackTrace();
					}
		}
		return true;
	}
	
	@Override
	public boolean updatePromotionTypesDocs(List<Long> product_ids) {
	for (int i = 0; i < product_ids.size(); i++) {
		super.initProduct();
		List<Integer> promotionTypes = brandProductEntityMapper.selectPromotionTypesByProductId(product_ids.get(i));
		Map<String, Object> map = new HashMap<>();
				map.put("set", promotionTypes);
				SolrInputDocument document= new SolrInputDocument();
				document.addField("product_id", product_ids.get(i)+"");
				document.addField("promotion_types", map);	
				try {
					solr.add(document);
					solr.commit();
				} catch (SolrServerException | IOException e) {
					e.printStackTrace();
					return false;
				}
	}
		return true;
	}

	@Override
	public boolean updateStateDocs(List<Long> product_ids,Integer state) {
		for (int i = 0; i < product_ids.size(); i++) {
			super.initProduct();
			Map<String, Object> map = new HashMap<>();
					map.put("set", state);
					SolrInputDocument document= new SolrInputDocument();
					document.addField("product_id", product_ids.get(i)+"");
					document.addField("state", map);	
					try {
						solr.add(document);
						solr.commit();
					} catch (SolrServerException | IOException e) {
						e.printStackTrace();
						return false;
					}
		}
			return true;
	}

/*	@Override
	public boolean updateStateDocs(List<Map<String, Object>> maps) {
		super.initProduct();
		Collection<SolrInputDocument> documents = new ArrayList<>();
		for (Map<String, Object> map : maps) {
			SolrInputDocument document= new SolrInputDocument();
			document.addField("product_id", map.get("product_id")+"");
			map.remove("product_id");
			document.addField("state", map);	
			documents.add(document);
		}
		try {
			solr.add(documents);
			solr.commit();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
*/	@Override
	public boolean updateStateDocs(List<Map<String, Object>> maps) {
	Collection<SolrInputDocument> documents =new ArrayList<>();
		for (Map<String, Object> map : maps) {
			SolrInputDocument document= new SolrInputDocument();
			document.addField("product_id", map.get("product_id")+"");
           map.remove("product_id");
           document.addField("state", map);	
           documents.add(document);
		}
		super.initProduct();
		try {
			solr.add(documents);
			solr.commit();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	@Override
	public boolean updatePromotionPricesDocs(List<Map<String, Object>> maps) {
		super.initProduct();
		Collection<SolrInputDocument> documents =new ArrayList<>();
		for (int i = 0; i < maps.size(); i++) {
			SolrInputDocument document= new SolrInputDocument();
			document.addField("product_id", maps.get(i).get("product_id"));
			maps.get(i).remove("product_id");
			document.addField("min_prom_price", maps.get(i));
			documents.add(document);
		}
		try {
			solr.add(documents);
			solr.commit();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public boolean updateProductSalesPropertiesDocs(List<Map<String, Object>> maps) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateSalesNumDocsByProductId(Long product_id,Integer sale_num) {
			super.initProduct();
			Map<String, Object> map = new HashMap<>();
					map.put("set", sale_num);
					SolrInputDocument document= new SolrInputDocument();
					document.addField("product_id", product_id+"");
					document.addField("sale_num", map);	
					try {
						solr.add(document);
						solr.commit();
					} catch (SolrServerException | IOException e) {
						e.printStackTrace();
					}
		return true;
	}

	@Override
	public boolean updateProductSeriesDocs(List<Map<String, String>> maps) {
		super.initProduct();
		Collection<SolrInputDocument> documents = new ArrayList<>();
		for (Map<String, String> map : maps) {
			String series = map.get("series_ids");
			Map<String, Object> series_map = new HashMap<>();
			series_map.put("set", series.split(","));
					SolrInputDocument document= new SolrInputDocument();
					document.addField("product_id", map.get("product_id"));
					document.addField("series_ids", series_map);	
					documents.add(document);
		}
		try {
			solr.add(documents);
			solr.commit();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean updateProductImagesDocs(List<Long> product_ids) {
		super.initProduct();
		Collection<SolrInputDocument> documents = new ArrayList<>();
		for (Long product_id : product_ids) {
			List<String> imgs = brandProdImgEntityMapper.selectImgByProductId(product_id);
			SolrInputDocument document= new SolrInputDocument();
			document.addField("product_id", product_id+"");
			//添加图片数组
			Map<String, Object> map = new HashMap<>();
			map.put("set", imgs);
			document.addField("images", map);
			//添加头图
			Map<String, Object> map_first = new HashMap<>();
			map_first.put("set", imgs.get(0));
			document.addField("image_url", map_first);	
			documents.add(document);
		}
		try {
			solr.add(documents);
			solr.commit();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean updateProductSaleNumDocs() {
		super.initProduct();
		  query.setFields("product_id,sale_num");
		  query.setQuery("*:*");
		  query.addFilterQuery("-(product_name:*)");
			query.setRows(Integer.MAX_VALUE);
		  try {
				response=solr.query(query);
			} catch (SolrServerException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				//获取结果
				List<Map<String, Object>> products = MySolrDoc.toJson( response.getResults());
				int size = products.size();
				Collection<SolrInputDocument> documents = new ArrayList<>();
	            for (Map<String, Object> product : products) {
	            	if(product.get("product_id")==null || "".equals(product.get("product_id")+"") || "null".equals(product.get("product_id")+"")){
	            		continue;
	            	}
	            	SolrInputDocument document = new SolrInputDocument();
	            	Map<String, Integer> map = new HashMap<>();
	            	map.put("set", Integer.valueOf((product.get("sale_num")+"")));
	            	document.addField("product_id", product.get("product_id")+"");
	            	document.addField("sale_num", map);
	            	documents.add(document);
	            }
	            super.initProduct();
	            try {
					solr.add(documents);
					solr.commit();
				} catch (SolrServerException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return true;
	}
	
	/*
	 * 清除没有产品名称的脏数据
	 */
	public static boolean deleteProductDocs() {
	SuperCore.initProduct();
		  query.setFields("product_id");
		  query.setQuery("*:*");
		  query.addFilterQuery("-(product_name:*)");
			query.setRows(Integer.MAX_VALUE);
		  try {
				response=solr.query(query);
			} catch (SolrServerException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				//获取结果
				List<Map<String, Object>> products = MySolrDoc.toJson( response.getResults());
				int size = products.size();
				List<String> product_ids = new ArrayList<>();
	            for (Map<String, Object> product : products) {
	            	if(product.get("product_id")==null || "".equals(product.get("product_id")+"") || "null".equals(product.get("product_id")+"")){
	            		continue;
	            	}
	            	product_ids.add(product.get("product_id")+"");
	            }
	            SuperCore.initProduct();
	            try {
					solr.deleteById(product_ids);
					solr.commit();
				} catch (SolrServerException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return true;
	}
	public static boolean update() {
		SuperCore.initProduct();
		query.setQuery("*:*");
		query.setRows(Integer.MAX_VALUE);
		  query.setFields("product_id,image_url");
		  try {
				response=solr.query(query);
			} catch (SolrServerException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				//获取结果
				List<Map<String, Object>> products = MySolrDoc.toJson( response.getResults());
				int size = products.size();
				Collection<SolrInputDocument> documents = new ArrayList<>();
	            for (Map<String, Object> product : products) {
	            	if(product.get("image_url")!=null){
	            	String description = product.get("image_url").toString();
	            	SolrInputDocument document = new SolrInputDocument();
	            	Map<String, String> map = new HashMap<>();
	            	map.put("set", description.replace("http", "https"));
	            	document.addField("product_id", product.get("product_id")+"");
	            	document.addField("image_url", map);
	            	documents.add(document);
	            	}
	            }
	            SuperCore.initProduct();
	            try {
					solr.add(documents);
					solr.commit();
				} catch (SolrServerException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            System.out.println("结束");
		return true;
	}
	
	
	@Override
	public void updateStateDoc(Map<String, String> map) {
		super.initProduct();
		SolrInputDocument document = new SolrInputDocument();
		document.addField("product_id", map.get("product_id"));
		document.addField("state", map);
		map.remove("product_id");
		 try {
				solr.add(document);
				solr.commit();
			} catch (SolrServerException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}


	public static void main(String[] args) {
		List<Map<String, String>> list = MysqlTest.getList();
		Collection<SolrInputDocument> documents =new ArrayList<>();
		for (Map<String, String> map : list) {
			SolrInputDocument document= new SolrInputDocument();
			document.addField("product_id", map.get("product_id"));
			map.remove("product_id");
			document.addField("brand_state", map);	
			documents.add(document);
		}
		SuperCore.initProduct();
		try {
			solr.add(documents);
			solr.commit();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean updateBrandStateDoc(Long brand_id, Integer brand_state) {
		super.initProduct();
		query.setQuery("brand_id:"+brand_id+"");
		query.setRows(Integer.MAX_VALUE);
		  query.setFields("product_id");
		  try {
				response=solr.query(query);
			} catch (SolrServerException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				//获取结果
				List<Map<String, Object>> products = MySolrDoc.toJson( response.getResults());
				Collection<SolrInputDocument> documents =new ArrayList<>();
				for (Map<String, Object> map : products) {
					SolrInputDocument document= new SolrInputDocument();
					document.addField("product_id", map.get("product_id"));
					map.put("set", brand_state);
					map.remove("product_id");
					document.addField("brand_state", map);	
					documents.add(document);
				}
		try {
			solr.add(documents);
			solr.commit();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean updatePartnerStateDoc(Long partner_id, Integer partner_state) {
		super.initProduct();
		query.setQuery("citypartner_id:"+partner_id+"");
		query.setRows(Integer.MAX_VALUE);
		  query.setFields("product_id");
		  try {
				response=solr.query(query);
			} catch (SolrServerException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				//获取结果
				List<Map<String, Object>> products = MySolrDoc.toJson( response.getResults());
				Collection<SolrInputDocument> documents =new ArrayList<>();
				for (Map<String, Object> map : products) {
					SolrInputDocument document= new SolrInputDocument();
					document.addField("product_id", map.get("product_id"));
					map.put("set", partner_state);
					map.remove("product_id");
					document.addField("partner_state", map);	
					documents.add(document);
				}
		try {
			solr.add(documents);
			solr.commit();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		return true;
}
}
