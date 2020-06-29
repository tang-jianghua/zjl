package com.mfangsoft.zhuangjialong.app.jumptoweb.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfangsoft.zhuangjialong.app.headline.mapper.HeadLineEntityMapper;
import com.mfangsoft.zhuangjialong.app.headline.model.HeadLineEntity;
import com.mfangsoft.zhuangjialong.app.jumptoweb.mapper.BasePathMapper;
import com.mfangsoft.zhuangjialong.app.jumptoweb.model.ParameterModel;
import com.mfangsoft.zhuangjialong.app.jumptoweb.service.JumpService;
import com.mfangsoft.zhuangjialong.app.main.mapper.MainBannerEntityMapper;
import com.mfangsoft.zhuangjialong.app.main.model.MainBannerEntity;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandBannerEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandBannerEntity;

/**
 * @description：
 * 
 * @author：Jianghua.Tang @date：2016年7月14日
 * 
 */
@Service
public class JumpServiceImpl implements JumpService {

	@Autowired
	BasePathMapper basePathMapper;
	@Autowired
	MainBannerEntityMapper mainBannerEntityMapper;
	@Autowired
	HeadLineEntityMapper headLineEntityMapper;
	@Autowired
	BrandBannerEntityMapper brandBannerEntityMapper;

	public final static String BATH_PRE = "http://47.93.78.124:8081/";
	// 体验中心
	public final static int DIY_CODE = 2;
	// 产品甄选
	public final static int PROD_SELECT_CODE = 3;
	// 预约到家
	public final static int APPOINTMENT_CODE = 4;
	// 文章详情
	public final static int ARTICLE_CODE = 7;
	// 产品详情
	public final static int PROD_DETAILS_CODE = 8;
	// 外部链接
	public final static int NET_CODE = 9;
	// 品牌首页
	public final static int BRAND_MAIN_CODE = 10;
	// 轮播图
	public final static int MAIN_BANNER_CODE = 11;
	// 头条
	public final static int HEAD_LINE_CODE = 12;
	// 品牌banner
	public final static int BRAND_BANNER_CODE = 13;
	// 品类产品列表
	public final static int PROD_CLASS_CODE = 14;
	// 品牌全部产品
	public final static int BRAND_PROD_CODE = 16;
	// 联盟活动
	public final static int UNION_PROM_CODE = 25;
	// 跳转内部链接
	public final static int INSIDE_LINK_CODE = 33;
	// 单个案例
	public final static int SINGLE_CASE_CODE = 35;

	// 跟前端约定的跳转类型(1:web链接 2:外部页面 3:体验中心 4:预约到家)
	public final static int[] STATE = { 1, 2, 3, 4 };

	@Override
	public Map<String, Object> selectByType(ParameterModel param)
			throws JsonProcessingException, UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<>();
		String path = null;
		int state_code = STATE[0];
		ObjectMapper o = new ObjectMapper();
		param.getUser_data().put("native_boolean", true);
		switch (param.getType()) {

		// 轮播图
		case MAIN_BANNER_CODE:
			MainBannerEntity mainBannerEntity = mainBannerEntityMapper.selectByPrimaryKey(param.getId());
			switch (mainBannerEntity.getLink_type()) {
			// 体验中心
			case DIY_CODE:
				state_code = STATE[2];
				break;
			// 预约到家
			case APPOINTMENT_CODE:
				state_code = STATE[3];
				break;
			// 产品甄选
			case PROD_SELECT_CODE:
				path = BATH_PRE + basePathMapper.selectByPrimaryKey(mainBannerEntity.getLink_type()) + "?result="
						+ URLEncoder.encode(o.writeValueAsString(param.getUser_data()), "UTF-8");
				break;
			// 文章详情
			case ARTICLE_CODE:
				param.getUser_data().put("headlineId", Long.parseLong(mainBannerEntity.getData_param()));
				path = BATH_PRE + basePathMapper.selectByPrimaryKey(mainBannerEntity.getLink_type()) + "?result="
						+ URLEncoder.encode(o.writeValueAsString(param.getUser_data()), "UTF-8");
				break;
			// 产品详情
			case PROD_DETAILS_CODE:
				param.getUser_data().put("product_id", Long.parseLong(mainBannerEntity.getData_param()));
				path = BATH_PRE + basePathMapper.selectByPrimaryKey(mainBannerEntity.getLink_type()) + "?result="
						+ URLEncoder.encode(o.writeValueAsString(param.getUser_data()), "UTF-8");
				break;
				// 外部链接
			case NET_CODE:
				path = mainBannerEntity.getImage_link();
				state_code = STATE[1];
				break;
			// 品牌首页
			case BRAND_MAIN_CODE:
				param.getUser_data().put("brand_id", Long.parseLong(mainBannerEntity.getData_param()));
				path = BATH_PRE + basePathMapper.selectByPrimaryKey(mainBannerEntity.getLink_type()) + "?result="
						+ URLEncoder.encode(o.writeValueAsString(param.getUser_data()), "UTF-8");
				break;
			// 品类产品列表
			case PROD_CLASS_CODE:
				param.getUser_data().put("class_id", Long.parseLong(mainBannerEntity.getData_param()));
				path = BATH_PRE + basePathMapper.selectByPrimaryKey(mainBannerEntity.getLink_type()) + "?result="
						+ URLEncoder.encode(o.writeValueAsString(param.getUser_data()), "UTF-8");
				break;
			// 品牌全部产品
			case BRAND_PROD_CODE:
				param.getUser_data().put("brand_id", Long.parseLong(mainBannerEntity.getData_param()));
				path = BATH_PRE + basePathMapper.selectByPrimaryKey(mainBannerEntity.getLink_type()) + "?result="
						+ URLEncoder.encode(o.writeValueAsString(param.getUser_data()), "UTF-8");
				break;
				// 内部链接
			case INSIDE_LINK_CODE:
				path = BATH_PRE +mainBannerEntity.getImage_link()+ "?result="
						+ URLEncoder.encode(o.writeValueAsString(param.getUser_data()), "UTF-8");
				break;
			// banner 默认
			default:
				path = BATH_PRE + basePathMapper.selectByPrimaryKey(mainBannerEntity.getLink_type()) + "?result="
						+ URLEncoder.encode(o.writeValueAsString(param.getUser_data()), "UTF-8");
			}
			;

			break;

		// 头条
		case HEAD_LINE_CODE:
			HeadLineEntity headLineEntity = headLineEntityMapper.selectByPrimaryKey(param.getId());
			switch (headLineEntity.getLink_type()) {
			// 体验中心
			case DIY_CODE:
				state_code = STATE[2];
				break;
			// 产品甄选
			case PROD_SELECT_CODE:
				param.getUser_data().put("select_id", 1);
				path = BATH_PRE + basePathMapper.selectByPrimaryKey(headLineEntity.getLink_type()) + "?result="
						+ URLEncoder.encode(o.writeValueAsString(param.getUser_data()), "UTF-8");
				break;
			// 预约到家
			case APPOINTMENT_CODE:
				state_code = STATE[3];
				break;
			// 文章详情
			case ARTICLE_CODE:
				param.getUser_data().put("headlineId", Long.parseLong(headLineEntity.getData_param()));
				path = BATH_PRE + basePathMapper.selectByPrimaryKey(headLineEntity.getLink_type()) + "?result="
						+ URLEncoder.encode(o.writeValueAsString(param.getUser_data()), "UTF-8");
				break;
			// 产品详情
			case PROD_DETAILS_CODE:
				param.getUser_data().put("product_id", Long.parseLong(headLineEntity.getData_param()));
				path = BATH_PRE + basePathMapper.selectByPrimaryKey(headLineEntity.getLink_type()) + "?result="
						+ URLEncoder.encode(o.writeValueAsString(param.getUser_data()), "UTF-8");
				break;
			// 外部链接
			case NET_CODE:
				state_code = STATE[1];
				path = headLineEntity.getLink();
				break;
			// 品牌首页
			case BRAND_MAIN_CODE:
				param.getUser_data().put("brand_id", Long.parseLong(headLineEntity.getData_param()));
				path = BATH_PRE + basePathMapper.selectByPrimaryKey(headLineEntity.getLink_type()) + "?result="
						+ URLEncoder.encode(o.writeValueAsString(param.getUser_data()), "UTF-8");
				break;
			// 品类产品列表
			case PROD_CLASS_CODE:
				param.getUser_data().put("class_id", Long.parseLong(headLineEntity.getData_param()));
				path = BATH_PRE + basePathMapper.selectByPrimaryKey(headLineEntity.getLink_type()) + "?result="
						+ URLEncoder.encode(o.writeValueAsString(param.getUser_data()), "UTF-8");
				break;
			// 品牌全部产品
			case BRAND_PROD_CODE:
				param.getUser_data().put("brand_id", Long.parseLong(headLineEntity.getData_param()));
				param.getUser_data().put("select_id", 1);
				path = BATH_PRE + basePathMapper.selectByPrimaryKey(headLineEntity.getLink_type()) + "?result="
						+ URLEncoder.encode(o.writeValueAsString(param.getUser_data()), "UTF-8");
				break;
			// 头条默认
			default:
				path = BATH_PRE + basePathMapper.selectByPrimaryKey(headLineEntity.getLink_type()) + "?result="
						+ URLEncoder.encode(o.writeValueAsString(param.getUser_data()), "UTF-8");
			}
			;
			break;

		// 品牌banner
		case BRAND_BANNER_CODE:
			BrandBannerEntity brandBannerEntity = brandBannerEntityMapper.selectByPrimaryKey(param.getId());
			map.put("type", brandBannerEntity.getLink_type());
			switch (brandBannerEntity.getLink_type()) {
			// 产品详情
			case PROD_DETAILS_CODE:
				map.put("id", Long.parseLong(brandBannerEntity.getData_param()));
				break;
			// 外部链接
			case NET_CODE:
				path = brandBannerEntity.getLink();
				break;

			// 联盟活动
			case UNION_PROM_CODE:
				map.put("id", Long.parseLong(brandBannerEntity.getData_param()));
				break;
			}
			;
			break;

		default:

			path = BATH_PRE + basePathMapper.selectByPrimaryKey(param.getType()) + "?result="
					+ URLEncoder.encode(o.writeValueAsString(param.getUser_data()), "UTF-8");
		}

		if (path != null) {
			map.put("path", path);
		}
		map.put("state_code", state_code);
		return map;

	}

	@Override
	public String makeUrlByType(ParameterModel param)
			throws JsonProcessingException, UnsupportedEncodingException {
		String path = null;
		ObjectMapper o = new ObjectMapper();
	
		if( param.getType()==INSIDE_LINK_CODE){
			path = BATH_PRE + param.getLink_url() + "?result="
					+ URLEncoder.encode(o.writeValueAsString(param.getUser_data()), "UTF-8");
		}else{
			String html_url = basePathMapper.selectByPrimaryKey(param.getType());
			
			path = BATH_PRE +html_url.replaceAll("Hoplitosaurus2.0", "mp")  + "?result="
					+ URLEncoder.encode(o.writeValueAsString(param.getUser_data()), "UTF-8");
			}
		return path;
	}
}
