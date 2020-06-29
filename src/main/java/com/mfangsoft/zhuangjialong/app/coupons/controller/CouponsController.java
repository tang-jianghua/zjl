package com.mfangsoft.zhuangjialong.app.coupons.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfangsoft.zhuangjialong.app.coupons.model.BrandCouponsModel;
import com.mfangsoft.zhuangjialong.app.coupons.model.FirstPageCouponsListParam;
import com.mfangsoft.zhuangjialong.app.coupons.service.CouponsService;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.CustomerCollectionEntity;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.HttpRequest;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;

/**
 * @description：
 * 
 * @author：Jianghua.Tang @date：2016年5月26日
 * 
 */
@Controller(value = "appcoupons")
@RequestMapping("/app")
public class CouponsController {

	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	@Autowired
	private CouponsService couponsServiceImpl;

	@RequestMapping(value = "/querybrandcoupons", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<BrandCouponsModel>> queryBrandCoupons(@RequestBody CustomerCollectionEntity param) {
		ResponseMessage<List<BrandCouponsModel>> responseMessage = new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(couponsServiceImpl.selectBrandCoupons(param));
		return responseMessage;
	}

	@RequestMapping(value = "/querybrandredbags", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<BrandCouponsModel>> queryBrandRedbags(@RequestBody CustomerCollectionEntity param) {
		ResponseMessage<List<BrandCouponsModel>> responseMessage = new ResponseMessage<>();
		responseMessage.setCode("0");
		responseMessage.setMessage("获取成功");
		responseMessage.setResult(couponsServiceImpl.selectBrandRedbags(param));
		return responseMessage;
	}


	@RequestMapping(value = "/receivebrandcoupons", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> receiveBrandCoupons(@RequestBody BrandCouponsModel param) {
		ResponseMessage<String> responseMessage = new ResponseMessage<>();
		if (param.getCustomer_id() == null) {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.not_login);
			return responseMessage;
		}
		Boolean b = couponsServiceImpl.addBrandCoupons(param);
		if (b) {
			couponsServiceImpl.sendMessage(param);
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}

	/**
	 * 查询活动通用券
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/queryPromotionCoupons", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<BrandCouponsModel> queryPromotionCoupons() {
		ResponseMessage<BrandCouponsModel> responseMessage = new ResponseMessage<>();
		BrandCouponsModel brandCouponsModel = couponsServiceImpl.queryPromotionCoupons();
		if (brandCouponsModel != null) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(brandCouponsModel);
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}

	/**
	 * 查询卡券栏目列表
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/queryFirstPageCouponsList", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Page<Map<String, Object>>> queryFirstPageCouponsList(
			@RequestBody Page<Map<String, Object>> page) {
		ResponseMessage<Page<Map<String, Object>>> responseMessage = new ResponseMessage<>();
		List<Map<String, Object>> list = couponsServiceImpl.queryFirstPageCouponsList(page);
		if (list != null) {
			page.setData(list);
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(page);
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}

	/**
	 * 查询卡券详情
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/queryOneFirstPageCoupons", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String, Object>> queryOneFirstPageCoupons(@RequestBody Map<String, Object> param) {
		
		ResponseMessage<Map<String, Object>> responseMessage = new ResponseMessage<>();
		Map<String, Object> map = couponsServiceImpl.queryOneFirstPageCoupons(param);
		if (map != null) {
			responseMessage.setCode(SysConstant.SUCCESS_CODE);
			responseMessage.setMessage(SysConstant.SUCCESS_MSG);
			responseMessage.setResult(map);
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage(SysConstant.FAILURE_MSG);
		}
		return responseMessage;
	}

	/**
	 * 查询卡券所属店铺列表
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/queryOneFirstPageCouponsShops", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<Map<String, Object>>> queryOneFirstPageCouponsShops(
			@RequestBody Map<String, Object> param) {
		ResponseMessage<List<Map<String, Object>>> responseMessage = new ResponseMessage<>();

		List<Map<String, Object>> map = couponsServiceImpl.queryOneFirstPageCouponsShops(param);

		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		responseMessage.setResult(map);
		return responseMessage;
	}
}
