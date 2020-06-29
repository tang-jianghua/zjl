package com.mfangsoft.zhuangjialong.app.weixin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.app.brand.model.BrandModel;
import com.mfangsoft.zhuangjialong.app.weixin.model.WeiXinStore;
import com.mfangsoft.zhuangjialong.app.weixin.service.WeixinStoreService;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;

/**
 * @author 作者 : peidingjun
 *
 * @date 创建时间：2016年11月16日 下午2:00:34
 * 
 * @description ：
 * 
 */

@Controller
@RequestMapping("/app/weixin")
public class WeixinStoreController {
	@Autowired
	WeixinStoreService weixinStoreService;
	private String business_name;

	// 模糊查询门店
	@RequestMapping(value = "/selectWeixinStore", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Page<Map<String, Object>> selectWeixinStore(@RequestBody Page<Map<String, Object>> page) {
		
		Map<String, String> map = (Map<String, String>) page.getParam();
		// resp.setContentType("text/html;charset=utf-8");
		// resp.setHeader("Content-Type","text/html;charset-utf-8");
		String business_name = map.get("business_name");
		List<WeiXinStore> list = weixinStoreService.queryBrandForPage(business_name);
		page.setParam(list);
		return page;
	}

	// 添加门店门店
	@RequestMapping(value = "/insertWeixinStore", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public void insertWeixinStore(@RequestBody WeiXinStore weiXinStore) {
		weixinStoreService.addWeixinStore(weiXinStore);
	}

	// 主键查询门店回显
	@RequestMapping(value = "/editWeixinStore", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
public ResponseMessage<WeiXinStore> editWeixinStore(@RequestBody WeiXinStore param){
	 ResponseMessage<WeiXinStore> responseMessage=new ResponseMessage<>();
	 responseMessage.setCode("0");
	 responseMessage.setMessage("success");
	 responseMessage.setResult(weixinStoreService.selectByPrimaryKey(param));
	return responseMessage;
	}

	// 更新门店
	@RequestMapping(value = "/updateWeixinStore", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public void updateWeixinStore(@RequestBody WeiXinStore weiXinStore) {
		weixinStoreService.updateByPrimaryKey(weiXinStore);
	
	}

	// 根据主键删除门店
	@RequestMapping(value = "/deleteWeixinStore", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public void deleteWeixinStore(@RequestBody WeiXinStore weiXinStore) {
		weixinStoreService.deleteByPrimaryKey(weiXinStore);
		
	}
}
