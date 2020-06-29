package com.mfangsoft.zhuangjialong.integration.entnewproduct.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.EntProdAttrValueName;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.EntProductEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.service.EntProductService;

@Controller("ent")
@RequestMapping("/server")
public class EntProductController {

	
	@Autowired
	private EntProductService brandProductService;
	
	@RequestMapping(value="/addnewentproduct",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String>  addBrandProduct(@RequestBody EntProductEntity brandProductEntity){
		
		
		ResponseMessage<String> message = new ResponseMessage<>();
		
		
		
		try {
			brandProductService.addEntProduct(brandProductEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			
			e.printStackTrace();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			// TODO: handle exception
		}
		
		return message;
		
	}
	
	@RequestMapping(value="/modfiynewentproduct",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String>  modfiyEntProduct(@RequestBody EntProductEntity brandProductEntity){
		
		
		ResponseMessage<String> message = new ResponseMessage<>();
		
		
		
		try {
			brandProductService.modfiyEntProduct(brandProductEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			
			e.printStackTrace();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			// TODO: handle exception
		}
		
		return message;
		
	}
	
	
	@RequestMapping(value="/removeentsaleattr/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<String>  modfiyEntProduct(@PathVariable Long id){
		
		
		ResponseMessage<String> message = new ResponseMessage<>();
		
		
		
		try {
			brandProductService.removeSalesAttr(id);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			
			e.printStackTrace();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			// TODO: handle exception
		}
		
		return message;
		
	}
	
	@RequestMapping(value="/queryentproductbyid/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<EntProductEntity>  queryEntProductById(@PathVariable Long id){
		
		
		ResponseMessage<EntProductEntity> message = new ResponseMessage<>();
		
		message.setCode(SysConstant.SUCCESS_CODE);
		
		message.setMessage(SysConstant.SUCCESS_MSG);
		message.setResult(brandProductService.getEntProductById(id));
		
		return message;
		
	}
	@RequestMapping(value="/querynewentproduct",method=RequestMethod.POST)
	@ResponseBody
	public Page<Map<String,Object>>  queryEntProduct(@RequestBody  Page<Map<String,Object>> page){
		
		
		return brandProductService.getEntProductForPage(page);
		
	}
	
	@RequestMapping(value="/querynewentdevproduct",method=RequestMethod.POST)
	@ResponseBody
	public Page<Map<String,Object>>  queryEntDevProduct(@RequestBody  Page<Map<String,Object>> page){
		
		
		return brandProductService.getBrandDevProductListForPage(page);
		
	}
	
	
	@RequestMapping(value="/queryvaluename",method=RequestMethod.GET)
	public void  queryvaluename() throws IOException{
		
		
		List<EntProdAttrValueName>  l=brandProductService.selectproductAttrValueName();
		
		
		for (EntProdAttrValueName entProdAttrValueName : l) {
			
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();  
            ObjectOutputStream objOuts = new ObjectOutputStream(byteArray);  
            objOuts.writeObject(entProdAttrValueName);  
            Map<String,Object> map= new HashMap<>();
            final byte[] objBytes = byteArray.toByteArray();  
            map.put("id", entProdAttrValueName.getProduct_id());
            
            map.put("space", objBytes);
			
			
		}
		
		
	}
}
