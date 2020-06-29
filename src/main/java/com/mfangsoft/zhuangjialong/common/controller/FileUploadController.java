package com.mfangsoft.zhuangjialong.common.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.ImageModel;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.ImgUtil;
import com.mfangsoft.zhuangjialong.common.utils.PropUtis;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.brand.model.BrandEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

@Controller
@RequestMapping("/common")
public class FileUploadController {

	@RequestMapping(value = "/updoad", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> upload(@RequestParam("file") MultipartFile file) {

		Long time = System.currentTimeMillis();

		java.text.SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

		String exname = FilenameUtils.getExtension(file.getOriginalFilename());

		String path = PropUtis.getValue(SysConstant.FILE_PATH) + "/" + f.format(new Date());
		System.out.println("path--------------------" + path);
		File filepath = new File(path);
		if (!filepath.exists()) {

			filepath.mkdirs();
		}

		ResponseMessage<String> message = new ResponseMessage<>();

		try {
			File write = new File(filepath.getPath() + "/" + time + "." + exname);
			System.out.println("file--------------------" + filepath.getPath() + "/" + time + "." + exname);
			write.createNewFile();
			FileUtils.writeByteArrayToFile(write, file.getBytes());
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			String resultPath = PropUtis.getValue(SysConstant.FILE_URL) + "/" + f.format(new Date()) + "/" + time + "."
					+ exname;
			message.setResult(resultPath);
			System.out.println("resultPath--------------------" + resultPath);
		} catch (IOException e) {

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			e.printStackTrace();
		}

		return message;
	}
	
	
	@RequestMapping(value = "/uploads", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<String>> uploads(@RequestParam("file") List<MultipartFile> file) {


		ResponseMessage<List<String>> message = new ResponseMessage<>();

		try {
			
			List<String>  list= new ArrayList<>();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			
			int i=0;
			
			for (MultipartFile multipartFile : file) {
				list.add(this.uploadFile(multipartFile,i));
				i++;
			}
			
			message.setResult(list);
			
		} catch (IOException e) {

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			e.printStackTrace();
		}

		return message;
	}
	@RequestMapping(value = "/uploadimageswithsize", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<ImageModel>> uploadImagesWithSize(@RequestParam("file") List<MultipartFile> file) {

		ResponseMessage<List<ImageModel>> message = new ResponseMessage<>();

		try {
			
			List<ImageModel>  list= new ArrayList<>();
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);
			
			int i=0;
			
			for (MultipartFile multipartFile : file) {
				ImageModel imageModel = new ImageModel();
				
				Long time = System.currentTimeMillis()+i;

				java.text.SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

				String exname = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

				String path = PropUtis.getValue(SysConstant.FILE_PATH) + "/" + f.format(new Date());
				System.out.println("path--------------------" + path);
				File filepath = new File(path);
				if (!filepath.exists()) {

					filepath.mkdirs();
				}
					File write = new File(filepath.getPath() + "/" + time + "." + exname);
					System.out.println("file--------------------" + filepath.getPath() + "/" + time + "." + exname);
					write.createNewFile();
					FileUtils.writeByteArrayToFile(write, multipartFile.getBytes());
					
					String resultPath = PropUtis.getValue(SysConstant.FILE_URL) + "/" + f.format(new Date()) + "/" + time + "."
							+ exname;
				  
				imageModel.setUrl(resultPath);
				BufferedImage sourceImg = ImageIO.read(write);
				imageModel.setHeight(sourceImg.getHeight());
				imageModel.setWidth(sourceImg.getWidth());
				list.add(imageModel);
				i++;
			}
			
			message.setResult(list);
			
		} catch (IOException e) {

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			e.printStackTrace();
		}

		return message;
	}
	private String uploadFile(MultipartFile file,int i) throws IOException{
		
		
		Long time = System.currentTimeMillis()+i;

		java.text.SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

		String exname = FilenameUtils.getExtension(file.getOriginalFilename());

		String path = PropUtis.getValue(SysConstant.FILE_PATH) + "/" + f.format(new Date());
		System.out.println("path--------------------" + path);
		File filepath = new File(path);
		if (!filepath.exists()) {

			filepath.mkdirs();
		}

		

		
			File write = new File(filepath.getPath() + "/" + time + "." + exname);
			System.out.println("file--------------------" + filepath.getPath() + "/" + time + "." + exname);
			write.createNewFile();
			FileUtils.writeByteArrayToFile(write, file.getBytes());
			
			String resultPath = PropUtis.getValue(SysConstant.FILE_URL) + "/" + f.format(new Date()) + "/" + time + "."
					+ exname;
		  return resultPath;
	}

	@RequestMapping(value = "/updoadimgbase64", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> updoadimgbase64(@RequestBody HashMap<String, String> map) {

		ResponseMessage<String> message = new ResponseMessage<>();
		String savePath = "";
		try {
			String imgBase64 = map.get("imgBase64");
			if (!StringUtils.isEmpty(imgBase64)) {
				savePath = ImgUtil.saveBase64Img(imgBase64);
			}
			message.setResult(savePath);

		} catch (Exception e) {

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			e.printStackTrace();
		}
		return message;
	}

	@RequestMapping(value = "/updoadimgbase64ForImgList", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<List<String>> updoadimgbase64ForImgList(@RequestBody List<String> list) {

		ResponseMessage<List<String>> message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		List<String> savePath = new ArrayList<String>();
		try {
			for (String s : list) {
				if (!StringUtils.isEmpty(s)) {
					savePath.add(ImgUtil.saveBase64Img(s));
				}
			}
			message.setResult(savePath);
		} catch (Exception e) {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			e.printStackTrace();
		}
		return message;
	}
	@RequestMapping(value = "/updoadForMap", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<Map<String, String>> updoadForImgList(@RequestBody Map<String, String> map ) {

		ResponseMessage<Map<String, String>> message = new ResponseMessage<>();
		message.setCode(SysConstant.SUCCESS_CODE);
		message.setMessage(SysConstant.SUCCESS_MSG);
		for (Iterator<String> keys = map.keySet().iterator();keys.hasNext(); ) {
			String key = keys.next();
		try {
				if (!StringUtils.isEmpty(map.get(key))) {
					map.put(key, ImgUtil.saveBase64Img(map.get(key)));
				}
		} catch (Exception e) {
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			e.printStackTrace();
		}
		}
		message.setResult(map);
		return message;
	}
	
	public static void main(String[] args) {

		Map<String, String> m = new HashMap<String, String>();
		m.put("imgBase64", "http:////dfdfdf.jpg");
		System.out.println(new Gson().toJson(m));

	}

	@RequestMapping(value = "/server/updloadbrandbg", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> uploadbrandbg(@RequestParam("file") MultipartFile file) {

		String time = "";

		UserEntity user = UserContext.getCurrentUser();

		if (user.getUser_type() == UserType.BRAND.getIndex()) {
			BrandEntity brandEntity = (BrandEntity) UserContext.getCurrentUserInfo();

			time = "brand_bg_image_" + brandEntity.getId();
		}

		java.text.SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

		String exname = FilenameUtils.getExtension(file.getOriginalFilename());

		String path = PropUtis.getValue(SysConstant.FILE_PATH) + "/image";

		File filepath = new File(path);
		if (!filepath.exists()) {

			filepath.mkdirs();
		}

		ResponseMessage<String> message = new ResponseMessage<>();

		try {
			File write = new File(filepath.getPath() + "/" + time + "." + exname);
			write.createNewFile();
			FileUtils.writeByteArrayToFile(write, file.getBytes());
			message.setCode(SysConstant.SUCCESS_CODE);
			message.setMessage(SysConstant.SUCCESS_MSG);

			String resultPath = "upload/image/" + time + "." + exname;
			message.setResult(resultPath);

		} catch (IOException e) {

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			e.printStackTrace();
		}

		return message;
	}
	
	@RequestMapping(value="/server/updloadpointmallimg",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseMessage<String> uploadPointMallImg(@RequestParam("file") MultipartFile file){
		
		String time="pointmallimg";
		
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
			
			RedisUtils.setValue("pointmallimg", resultPath);
			
			message.setResult(resultPath);
			
		} catch (IOException e) {
			
			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			e.printStackTrace();
		}
		
		return message;
	}
	
	
	/**
	 * {"state": "SUCCESS","original": "image_6_401.jpg","size": "65818","title": "1474872848492079398.jpg","type": ".jpg","url": "/file/upload/20160926/1474872848492079398.jpg"}
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/ueupload", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public Map<String,Object> ueUpload(@RequestParam("file") MultipartFile file) {

		Long time = System.currentTimeMillis();

		Map<String,Object> responseMap = new HashMap<>();
		
		java.text.SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

		String exname = FilenameUtils.getExtension(file.getOriginalFilename());

		String path = PropUtis.getValue(SysConstant.FILE_PATH) + "/" + f.format(new Date());

		File filepath = new File(path);
		if (!filepath.exists()) {

			filepath.mkdirs();
		}

		ResponseMessage<String> message = new ResponseMessage<>();

		try {
			File write = new File(filepath.getPath() + "/" + time + "." + exname);
			write.createNewFile();
			FileUtils.writeByteArrayToFile(write, file.getBytes());
			
			responseMap.put("state", "SUCCESS");
			responseMap.put("original", file.getOriginalFilename());
			responseMap.put("size", file.getSize());
			responseMap.put("title", time+"."+exname);
			responseMap.put("type", "."+exname);
		
			String resultPath = PropUtis.getValue(SysConstant.FILE_URL) + "/" + f.format(new Date()) + "/" + time + "."
					+ exname;
			responseMap.put("url", resultPath);
		} catch (IOException e) {

			message.setCode(SysConstant.FAILURE_CODE);
			message.setMessage(SysConstant.FAILURE_MSG);
			e.printStackTrace();
		}

		return responseMap;
	}

	
}
