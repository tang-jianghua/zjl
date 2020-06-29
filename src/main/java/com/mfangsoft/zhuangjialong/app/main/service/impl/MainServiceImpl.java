package com.mfangsoft.zhuangjialong.app.main.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.article.model.ArticleEntity;
import com.mfangsoft.zhuangjialong.app.headline.mapper.HeadLineEntityMapper;
import com.mfangsoft.zhuangjialong.app.jumptoweb.mapper.BasePathMapper;
import com.mfangsoft.zhuangjialong.app.jumptoweb.model.BasePath;
import com.mfangsoft.zhuangjialong.app.main.mapper.CaseEntityMapper;
import com.mfangsoft.zhuangjialong.app.main.mapper.ColumnImgMapper;
import com.mfangsoft.zhuangjialong.app.main.mapper.MainBannerEntityMapper;
import com.mfangsoft.zhuangjialong.app.main.model.CaseEntity;
import com.mfangsoft.zhuangjialong.app.main.model.ColumnImg;
import com.mfangsoft.zhuangjialong.app.main.model.IntroduceProductModel;
import com.mfangsoft.zhuangjialong.app.main.model.MainBannerEntity;
import com.mfangsoft.zhuangjialong.app.main.service.MainService;
import com.mfangsoft.zhuangjialong.app.newproductcore.mapper.SuperCore;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.PointEntityMapper;
import com.mfangsoft.zhuangjialong.app.product.model.ProductListModel;
import com.mfangsoft.zhuangjialong.app.promotion.model.AppPromotionTypeEnum;
import com.mfangsoft.zhuangjialong.app.promotion.model.Promotion_type;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.DateUtils;
import com.mfangsoft.zhuangjialong.common.utils.MySolrDoc;
import com.mfangsoft.zhuangjialong.common.utils.NumberUtil;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.appointment.util.AppointmentState;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.introduce.mapper.IntroduceClassEntityMapper;
import com.mfangsoft.zhuangjialong.integration.introduce.mapper.IntroduceProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.BrandNewProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProductEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.PromotionZhekouEntityMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.model.PromotionZhekouEntity;
import com.mfangsoft.zhuangjialong.integration.shop.mapper.ShopEntityMapper;
import com.mfangsoft.zhuangjialong.integration.user.mapper.UserEntityMapper;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年6月3日
* 
*/
@Service
public class MainServiceImpl extends SuperCore implements MainService{
         
	@Autowired
	private MainBannerEntityMapper mainBannerEntityMapper;
	@Autowired
	private ColumnImgMapper columnImgMapper;
	@Autowired
	private HeadLineEntityMapper headLineEntityMapper;
	@Autowired
	private CaseEntityMapper caseEntityMapper;
	@Autowired
	private BrandNewProductEntityMapper brandProductEntityMapper;
	@Autowired
	private BrandEntityMapper brandEntityMapper;
	@Autowired
	PointEntityMapper pointEntityMapper;
	@Autowired
	UserEntityMapper userEntityMapper;
	@Autowired
	ShopEntityMapper shopEntityMapper;
	@Autowired
	IntroduceClassEntityMapper introduceClassEntityMapper;
	@Autowired
	IntroduceProductEntityMapper introduceProductEntityMapper;
	@Autowired
	BasePathMapper basePathMapper;
	@Autowired
	PromotionZhekouEntityMapper promotionZhekouEntityMapper;
	
	@Override
	public List<MainBannerEntity> selectMainBanners(String region_code) {
		List<MainBannerEntity> banners =new ArrayList<>();
		if(region_code!=""&&region_code!=null){
	      banners = mainBannerEntityMapper.selectMainBanners(region_code);
		}
		if(banners.size()==0){
			banners = mainBannerEntityMapper.selectMainBannersForKFZ();
		}
		return banners;
	}

	@Override
	public List<ColumnImg> selectByImgType(Integer img_type) {
		return columnImgMapper.selectByImgType(img_type);
	}

	@Override
	public Map<String, Object> selectMainHeadLine() {
		Map<String, Object> map=new HashMap<>();
		List<ColumnImg> ColumnImgs = columnImgMapper.selectByImgType(ColumnImg.TOP_IMG_TYPE);
		
		map.put("headline_img", ColumnImgs.get(0).getImgurl());
		map.put("headline_news", headLineEntityMapper.selectByState(1));
		
		return map;
		
	}

	
	@Override
	public List<CaseEntity> selectCaseList(Map<String, String> map) {
		return caseEntityMapper.selectCaseList(map.get("region_code"));
	}

	@Override
	public ArticleEntity selectForCaseDetails(Long id) {
		 return caseEntityMapper.selectForCaseDetails(id);
	}

	@Override
	public List<BrandEntity> selectMainRegionBrand(BrandEntity param) {
		
		return brandEntityMapper.selectMainRegionBrand(param);
	}

	
public static void main(String[] args) {
	
	System.out.println(AppointmentState.state1.replace("T", DateUtils.formatDate_(new Date())));
	
}
	

	@Override
	public List<ColumnImg> selectAllByImgType(Integer img_type) {
		return columnImgMapper.selectAllByImgType(img_type);
	}

	@Override
	public List<ColumnImg> selectMainColumn() {
		List<ColumnImg> columns = columnImgMapper.selectByImgType(ColumnImg.TAG_IMG_TYPE);
		for (int i = 0; i < columns.size(); i++) {
			String imgurl = columns.get(i).getImgurl();
			String[] split = imgurl.split(",");
				columns.get(i).setSelect_url(split[0]);
				columns.get(i).setSelected_url(split[1]);
			columns.get(i).setImgurl(null);
		}
		return columns;
	}

	@Override
	public MainBannerEntity getAd() {
		return mainBannerEntityMapper.selectAd();
	}

	@Override
	public Page<ProductListModel> getSqlMainHotProductForPage(Page<ProductListModel> page) {
		List<ProductListModel> list = brandProductEntityMapper.selectNewMainProductsForPage(page);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setPromotion_types(brandProductEntityMapper.selectPromotionTypesByProductId(list.get(i).getProduct_id()));
		}
		page.setParam(null);
		page.setArrModel(null);
		 page.setData(list);
		 return page;
	}

	@Override
	public Page<IntroduceProductModel> getMainHotProductForPage(Page<IntroduceProductModel> page) {
		List<IntroduceProductModel> list = introduceClassEntityMapper.selectClassForAppForPage(page);
		if(list.size()==0){
			page.setParam(null);
			page.setData(list);
			return page;
		}
		for (IntroduceProductModel introduceProductModel : list) {
			List<Map<String, Object>> products = introduceProductEntityMapper.selectProductIdsByIntroduceId(introduceProductModel.getId());
			StringBuffer sb=new StringBuffer();
           for (int i = 0; i < products.size(); i++) {
        	   if(i<products.size()-1){
       		sb.append("product_id: ").append(products.get(i).get("product_id").toString()).append(" OR ");
        	   }else{
        	sb.append("product_id: ").append(products.get(i).get("product_id").toString());
        	   }
		}
			super.initProduct();
				query.setQuery(sb.toString());
				query.setFields("product_id,image_url,min_price,min_prom_price,sale_num");
				try {
					response=solr.query(query);
				} catch (SolrServerException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<Map<String, Object>> ps = MySolrDoc.toJson(response.getResults());
				for (Map<String, Object> product : ps) {
					String product_id = product.get("product_id").toString();
					
					for (Map<String, Object> pt : products) {
						if(product_id.equals(pt.get("product_id").toString())){
							
							List<Integer> promotionTypes = brandProductEntityMapper.selectPromotionTypesByProductId(Long.valueOf(product_id));
							product.put("promotion_types", promotionTypes);
							
							if(promotionTypes == null || !promotionTypes.contains(AppPromotionTypeEnum.secKill.getTypeValue())){
				    			// 折扣工具
				            	BaseBrandProductEntity entity = brandProductEntityMapper.selectByPrimaryKey(Long.valueOf(product_id));
				            	
				    			PromotionZhekouEntity zhekou = promotionZhekouEntityMapper.selectZhekouToolForCartOneProduct(entity.getBrand_id(), Long.valueOf(product_id));
				    			if(zhekou != null){
				    				String price = product.get("min_prom_price").toString();
				    				product.put("min_prom_price", NumberUtil.round2(zhekou.getDiscount() * Double.parseDouble(price)));
				    			}
				    		}
							
							pt.putAll(product);
						}
					}
				}
			introduceProductModel.setProducts(products);
		}
		page.setData(list);
		page.setParam(null);
		return page;
	}

	@Override
	public List<BasePath> selectMainCoulmn() {
		final Integer FLAG_TYPE=1;//主页按钮类型
		return basePathMapper.selectByFlagType(FLAG_TYPE);
	}

	
        
}
