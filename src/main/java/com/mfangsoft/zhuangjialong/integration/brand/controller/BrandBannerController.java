package com.mfangsoft.zhuangjialong.integration.brand.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.PropUtis;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.brand.mapper.BrandMainProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandBannerEntity;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandMainProductEntity;
import com.mfangsoft.zhuangjialong.integration.brand.model.HotClassifyEntity;
import com.mfangsoft.zhuangjialong.integration.brand.model.HotClassifyProductEntity;
import com.mfangsoft.zhuangjialong.integration.brand.service.BrandBannerService;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.newproduct.service.BrandProductService;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;
@Controller
@RequestMapping("/server")
public class BrandBannerController {
	
	
	private final static Integer LINK_TYPE_PRODUCT=1;
	
	
	@Autowired
	private BrandBannerService bannerService;
	
	@Autowired
	private BrandProductService brandProductService;
	
	@Autowired
	private BrandMainProductEntityMapper brandMainProductEntityMapper;
	
	
	@RequestMapping(value="/addbrandbanner",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String> addBrandBanner(@RequestBody BrandBannerEntity bannerEntity ){
		
		ResponseMessage<String>  message = new ResponseMessage<>();
		
		 try {
			 bannerService.addBrandBanner(bannerEntity);
			 message.setCode(SysConstant.SUCCESS_CODE);
			 message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			e.printStackTrace();
		}	
		return message;
		
	}
	
	
	@RequestMapping(value="/modfiybrandbanner",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage<String> modfiyBrandBanner(@RequestBody  BrandBannerEntity bannerEntity){
		
		ResponseMessage<String>  message = new ResponseMessage<>();
		
		
		try {
			bannerService.modfiyBrandBanner(bannerEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			e.printStackTrace();
		}
			
		return message;
		
	}
	@RequestMapping(value="/removebrandbanner/{id}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<String> removeBrandBanner(@PathVariable  Long id){
		
		ResponseMessage<String>  message = new ResponseMessage<>();
		
		
		try {
			bannerService.removeBrandBanner(id);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			e.printStackTrace();
		}
			
		return message;
		
	}
	@RequestMapping(value="/querybrandbanner",method=RequestMethod.GET)
	@ResponseBody
	public ResponseMessage<List<BrandBannerEntity>> queryBrandBanner(){
		
		ResponseMessage<List<BrandBannerEntity>>  message = new   ResponseMessage<>();
		
		message.setResult(bannerService.queryBrandBanner());
		
		return message;
		
	}
	/**
	 * 查询产品数据
	 * 品牌管理员后台-品牌管理-界面装修-首页单品设定 点编辑弹出列表显示所有未被选择的品牌产品
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/bannerproductlist",method=RequestMethod.POST)
	@ResponseBody
	public  Page<Map<String,Object>> bannerproductlist(@RequestBody Page<Map<String,Object>> page){
		
		
		return brandProductService.getBrandPaoductBrandMain(page);
		
	}
	
	/**
	 * 查询编辑产品数据
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/brandproductslist",method=RequestMethod.POST)
	@ResponseBody
	public  Page<Map<String,Object>> bannerproductslist(@RequestBody Page<Map<String,Object>> page){
		
		
		return brandProductService.getBrandPaoductBrandMainList(page);
		
	}
	/**
	 * 品牌管理员后台-品牌管理-界面装修-首页单品设定列表分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/brandmainproductpage",method=RequestMethod.POST)
	@ResponseBody
	public Page<Map<String, Object>> getBrandMainProductPage(@RequestBody Page<Map<String, Object>> page){
		return bannerService.getBrandMainProduct(page);
	}
	/**
	 * 品牌管理员后台-品牌管理-界面装修-首页单品设定 返回全部已选择的product_id
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/brandmainproductlist")
	@ResponseBody
	public List<Long> getBrandMainProductList(){
		BrandEntity brandEntity =(BrandEntity) UserContext.getCurrentUserInfo();
		return brandMainProductEntityMapper.selectProductIdByBrandId(brandEntity.getId());
	}

	/**
	 * 添加首页产品
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/addbrandmainproduct",method=RequestMethod.POST)
	@ResponseBody
	public  ResponseMessage<String> addBrandMainProduct(@RequestBody List<BrandMainProductEntity> brandMainProductEntitys){
		
		ResponseMessage<String> message  = new ResponseMessage<>();
		try {
			BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();
			for (BrandMainProductEntity brandMainProductEntity : brandMainProductEntitys){
				brandMainProductEntity.setBrand_id(brandEntity.getId());
				bannerService.addBrandMainProduct(brandMainProductEntity);
			}
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
		
	}
	/**
	 * 删除首页单品设定
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/deletebrandmainproduct/{id}")
	@ResponseBody
	public  ResponseMessage<String> deleteBrandMainProduct(@PathVariable("id") Long id ){
		BrandMainProductEntity brandMainProductEntity = new BrandMainProductEntity();
		brandMainProductEntity.setId(id);
		ResponseMessage<String> message  = new ResponseMessage<>();
		try {
			bannerService.removeBrandMainProduct(brandMainProductEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
		
	}
	/**
	 * 批量删除首页单品设定
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/batchdeletebrandmainproduct",method=RequestMethod.POST)
	@ResponseBody
	public  ResponseMessage<String> batchDeleteBrandMainProduct(@RequestBody List<Long> brandMainProductIDList ){
		ResponseMessage<String> message  = new ResponseMessage<>();
		if (bannerService.batchDeleteBrandMainProduct(brandMainProductIDList)){
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		}
		else{
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		return message;
	}
	/**
	 * 添加热门分类
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/addhotclassify",method=RequestMethod.POST)
	@ResponseBody
	public  ResponseMessage<String> addBrandMainProduct(@RequestBody HotClassifyEntity brandMainProductEntity){
		
		ResponseMessage<String> message  = new ResponseMessage<>();
		try {
			bannerService.addHotClassify(brandMainProductEntity);
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
		
	}
	
	/**
	 * 修改热门
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/modfiyhotclassify",method=RequestMethod.POST)
	@ResponseBody
	public  ResponseMessage<String> modfiyHotClassify(@RequestBody HotClassifyEntity brandMainProductEntity){
		
		ResponseMessage<String> message  = new ResponseMessage<>();
		try {
			bannerService.modfiyHotClassify(brandMainProductEntity);
			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
		
	}
	

	
	/**
	 * 查询热门分类
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/queryhotclassify",method=RequestMethod.GET)
	@ResponseBody
	public  ResponseMessage<List<HotClassifyEntity>> queryHotClassify(){
		
		ResponseMessage<List<HotClassifyEntity>> message  = new ResponseMessage<>();
		try {
			
			
			message.setResult(bannerService.queryHostClassify());

			
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
		
	}
	
	/**
	 * 添加
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/addhotclassifyproduct",method=RequestMethod.POST)
	@ResponseBody
	public  ResponseMessage<String> addHotclassifyProduct(@RequestBody HotClassifyProductEntity classifyProductEntity){
		
		ResponseMessage<String> message  = new ResponseMessage<>();
		try {
			bannerService.addHotClassifyProduct(classifyProductEntity);
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
		}
		
		return message;
		
	}
	
	/**
	 * 添加
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/queryhotclassifyproduct",method=RequestMethod.POST)
	@ResponseBody
	public  Page<Map<String,Object>> queryHotclassifyProduct(@RequestBody Page<Map<String,Object>> page){
		
		return bannerService.getBrandHotClassifyProductPage(page);
		
	}
	
	/**
	 * 保存品牌id
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/saveBrandInfoToRedis",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage saveBrandInfoToRedis(@RequestBody BrandEntity brandEntity){
		
		ResponseMessage<String> message = new ResponseMessage<>();
		
		RedisUtils.setWithTimeLimit("img_brand_id", brandEntity.getId().toString(), 10L);
		
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		return message;
	}
	
	@RequestMapping(value="/updloadbrandbg/{id}",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> uploadbrandbg(@RequestParam("file") MultipartFile file,@PathVariable Long id){
		
		String time="";
//	 UserEntity  user=UserContext.getCurrentUser();
	 
//	 if(user.getUser_type()==UserType.BRAND.getIndex()){
//		BrandEntity brandEntity= (BrandEntity) UserContext.getCurrentUserInfo();
		
//		time= "brand_bg_image_"+brandEntity.getId();
//	 }
		
		time= "brand_bg_image_"+ id;
		
		java.text.SimpleDateFormat  f= new SimpleDateFormat("yyyy-MM-dd");
		
		String exname=FilenameUtils.getExtension(file.getOriginalFilename());
		
		String  path=PropUtis.getValue(SysConstant.FILE_PATH)+"/image";
		
		File filepath = new File(path);
		if(!filepath.exists()){
			
			filepath.mkdirs();
		}
		
		
		ResponseMessage<String> message = new ResponseMessage<>();
		
		try {
			File write = new File(filepath.getPath()+"/"+time+"."+exname);
			write.createNewFile();
			FileUtils.writeByteArrayToFile(write, file.getBytes());
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
		
			String resultPath ="upload/image/"+time+"."+exname;
			message.setResult(resultPath);
			
		} catch (IOException e) {
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			e.printStackTrace();
		}
		
		return message;
	}
	
}
