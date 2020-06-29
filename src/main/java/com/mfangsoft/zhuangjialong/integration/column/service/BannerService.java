package com.mfangsoft.zhuangjialong.integration.column.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.main.model.MainBannerEntity;

public interface BannerService {
	
	
	Boolean  addBanner(MainBannerEntity mainBannerEntity);
	
	List<MainBannerEntity> getAdminMainBannerList(Map<String,Object> map);
	
	List<MainBannerEntity> getMainBannerList();
	
	
	List<MainBannerEntity> selectMainBanneradList();
	
	
	Boolean modfiyBanner(MainBannerEntity mainBannerEntity);
	
	Boolean removeBanner(Long id);
	
	/**
	 * 轻松选材
	 */
	public  final static Integer  LINK_SELECT=1;
	
	/**
	 * DIY设计
	 */
	public final static Integer  LINK_DIY=2;
	
	/**
	 * 产品甄选
	 */
	public final static Integer  LINK_PICK=3;
	
	/**
	 * 预约到家
	 */
	public final static Integer  LINK_APPOINTMENT=4;
	
	/**
	 * 文章
	 */
	public final static Integer  LINK_ARTICLE=5;
	
	
	public final static Integer  LINK_TEXTURES=6;
	
	
	/**
	 * 活动
	 */
	public final static Integer  LINK_PROMOTION=7;
	
	/**
	 * web
	 */
	public final static Integer  LINK_WEB=8;
	
	
	/**
	 * 产品
	 */
	public final static Integer  LINK_PRODUCT=9;

}
