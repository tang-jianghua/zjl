package com.mfangsoft.zhuangjialong.integration.promotion.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandMainProductEntity;
import com.mfangsoft.zhuangjialong.integration.promotion.mapper.UnionProductMapper;
import com.mfangsoft.zhuangjialong.integration.promotion.model.UnionProduct;
import com.mfangsoft.zhuangjialong.integration.promotion.service.UnionProductService;

@Controller(value="unionproduct")
@RequestMapping("/server")
public class UnionProductController {
	@Autowired
	private UnionProductService unionProductService;
	@Autowired
	private UnionProductMapper unionProductMapper;
	/**
	 * 品牌管理员后台-活动管理-品牌活动-联盟活动-已选择参加活动产品列表分页显示
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/unionproductpage",method=RequestMethod.POST)
	@ResponseBody
	public Page<Map<String, Object>> getUnionProductListPage(@RequestBody Page<Map<String, Object>> page){
		return unionProductService.getUnionProductListPage(page);
	}
	/**
	 * 品牌管理员后台-活动管理-品牌活动-联盟活动-已选择参加活动产品列表,不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/unionproductlist",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getUnionProductList(@RequestBody Page<Map<String, Object>> page){
		return unionProductService.getUnionProductList(page);
	}
	/**
	 * 品牌管理员后台-活动管理-品牌活动-联盟活动-已选择参加活动产品删除
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/deleteunionproduct",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String> deleteUnionProduct(@RequestBody List<Integer> idList){
		ResponseMessage<String> message = new ResponseMessage<String>();
		try {
			unionProductMapper.deleteByPrimaryKey(idList);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}
		catch (Exception e){
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	/**
	 * 品牌管理员后台-活动管理-品牌活动-联盟活动 点设置 弹出列表显示所有未被选择的品牌产品
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/selectunionproduct",method=RequestMethod.POST)
	@ResponseBody
	public  Page<Map<String,Object>> getNotSelectedBrandProductPage(@RequestBody Page<Map<String,Object>> page){
		return unionProductService.getNotSelectedBrandProductPage(page);
		
	}
	/**
	 * 品牌管理员后台-活动管理-品牌活动-联盟活动 添加活动产品
	 * @param [{"promotion_id":5,"brand_id":33,"product_id":1},{"promotion_id":5,"brand_id":33,"product_id":2}]
	 * @return
	 */
	@RequestMapping(value="/addunionproduct",method=RequestMethod.POST)
	@ResponseBody
	public  ResponseMessage<String> addUnionProduct(@RequestBody List<UnionProduct> unionProducts){
		ResponseMessage<String> message  = new ResponseMessage<>();
		if (unionProductService.addUnionProductList(unionProducts)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}
		else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
}
