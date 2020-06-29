package com.mfangsoft.zhuangjialong.app.sendflow.service.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.bridge.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfangsoft.zhuangjialong.app.applogin.mapper.CustomerEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.mapper.UserEquipmentEntityMapper;
import com.mfangsoft.zhuangjialong.app.applogin.model.CustomerEntity;
import com.mfangsoft.zhuangjialong.app.applogin.model.UserEquipmentEntity;
import com.mfangsoft.zhuangjialong.app.personalCenter.mapper.MessageEntityMapper;
import com.mfangsoft.zhuangjialong.app.personalCenter.model.MessageEntity;
import com.mfangsoft.zhuangjialong.app.pointmall.service.PointMallService;
import com.mfangsoft.zhuangjialong.app.pointmall.service.impl.PointMallServiceImpl;
import com.mfangsoft.zhuangjialong.app.sendflow.mapper.FlowBackEntityMapper;
import com.mfangsoft.zhuangjialong.app.sendflow.mapper.FlowBatchEntityMapper;
import com.mfangsoft.zhuangjialong.app.sendflow.model.CallBackModel;
import com.mfangsoft.zhuangjialong.app.sendflow.model.FlowBackEntity;
import com.mfangsoft.zhuangjialong.app.sendflow.model.FlowBatchEntity;
import com.mfangsoft.zhuangjialong.app.sendflow.model.FlowResponseModel;
import com.mfangsoft.zhuangjialong.app.sendflow.service.SendFlowService;
import com.mfangsoft.zhuangjialong.app.sendflow.utils.EMayEncryptionUtils;
import com.mfangsoft.zhuangjialong.app.sendflow.utils.HttpClient;
import com.mfangsoft.zhuangjialong.app.sendflow.utils.HttpClient.HttpMethod;
import com.mfangsoft.zhuangjialong.app.sendsms.service.SendSMSService;
import com.mfangsoft.zhuangjialong.app.sendflow.utils.HttpErrorException;
import com.mfangsoft.zhuangjialong.app.sendflow.utils.HttpRequestBody;
import com.mfangsoft.zhuangjialong.app.sendflow.utils.HttpResponseBody;
import com.mfangsoft.zhuangjialong.common.utils.JPushUtil;
import com.mfangsoft.zhuangjialong.common.utils.MessageConstant;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.core.quartz.Quest;
import com.mfangsoft.zhuangjialong.core.quartz.QuestsManagerBean;
import com.mfangsoft.zhuangjialong.integration.pointmall.mapper.PointConvertEntityMapper;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年9月23日
* 
*/

@Service
public class SendFlowServiceImpl implements SendFlowService{
	   @Autowired
	   private PointMallService pointMallServiceImpl;
	 @Autowired
	   PointMallServiceImpl pointMallServiceImplp;
	@Autowired
	FlowBatchEntityMapper flowBatchEntityMapper;
	@Autowired
	FlowBackEntityMapper flowBackEntityMapper;
	  @Autowired
	    MessageEntityMapper messageEntityMapper;
	   @Autowired
	    UserEquipmentEntityMapper userEquipmentEntityMapper;
	   @Autowired
	    PointConvertEntityMapper pointConvertEntityMapper;
	   @Autowired
	   CustomerEntityMapper customerEntityMapper;
	   @Autowired
	    SendSMSService sendSMSServiceImpl;
	   
	@Override
	public Integer InsertSendFlow(String mobiles, String taskNo, String ctcc, String cucc, String cmcc) {
		FlowBatchEntity batchEntity = new FlowBatchEntity();
		
		batchEntity.setBatch_no(taskNo);
		batchEntity.setMobile(mobiles);
		batchEntity.setCmcc(cmcc);
		batchEntity.setCtcc(ctcc);
		batchEntity.setCucc(cucc);
		
		
		
		// 赠送流量接口
					String url = "http://flow.b2m.cn/outerservice/request";
					// 电信套餐编号，根据提供套餐列表选择，必选
					// 生效类型：0-立即生效，1-下月生效，选填(默认立即生效)【我们会发送给运营商，但是不保证运营商受理】
					String etype = "0";
					// 分配给您的准入TOKEN，必选
					String token = "e05c920045914387";
					// 分配给您的准入APPID，必选
					String appId = "e76c7867-d02b-4ed4-a80b-acf553c92d71";
					StringBuffer buffer = new StringBuffer();
					buffer.append("mobiles=" + mobiles).append("&taskNo=" + taskNo);
					if(ctcc!=null){
						buffer.append("&ctcc=" + ctcc);
					}
					if(cucc!=null){
						buffer.append("&cucc=" + cucc);
					}
                    if(cmcc!=null){
                    	buffer.append("&cmcc=" + cmcc);
                    }
                    buffer.append("&etype=" + etype);
					String code = buffer.toString();
					String key = EMayEncryptionUtils.md5(code);
					String value = EMayEncryptionUtils.parseByte2HexStr(EMayEncryptionUtils.aesEncrypt(code, token));
 
					Map<String, String> params = new HashMap<String, String>();
					params.put("key", key);
					params.put("value", value);
					params.put("appId", appId);
					params.put("version", "2");//不传这个参数默认为1
					System.out.println("key:"+key);
					System.out.println("value"+value);
					HttpClient client = new HttpClient(10, 10, true);

					HttpRequestBody body;
					try {
						body = new HttpRequestBody(url, "UTF-8", HttpMethod.POST, null, null, params);
					} catch (HttpErrorException e) {
						//url is null
						e.printStackTrace();
						return FAILURE;
					}

					HttpResponseBody res = client.service(body);

					if (res.isSuccess() && res.getCode() == 200) {
						String rs = res.getResultString();
						System.out.println(rs);
						/*
						 * rs: { "code":"E0001", //状态吗 "msg":"拒绝访问", //错误信息，成功的话，此处为空
						 * "batchNo":"3028402934283", //批次号，我方放回的批次号，回调以此为准
						 * "errorMobiles":["123","234"], //错误号码，检测到提供的错误不能处理的号码
						 * "mobileNumber":10 //能够处理的号码总数 }
						 */
						ObjectMapper json = new ObjectMapper();
						try {
							FlowResponseModel readValue = json.readValue(rs, FlowResponseModel.class);
							batchEntity.setCode(readValue.getCode());
							batchEntity.setMsg(readValue.getMsg());
							batchEntity.setCreate_time(new Date());
							flowBatchEntityMapper.insertSelective(batchEntity);
							
							if("M0007".equals(readValue.getCode())){
								return NOT_ENOUGH;	
								}
							if(!"M0001".equals(readValue.getCode())){
							return FAILURE;	
							}
							
						} catch (JsonParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return FAILURE;	
						} catch (JsonMappingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return FAILURE;	
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return FAILURE;	
						}
					}else{
						return FAILURE;	
					}
		
		return SUCCESS;
	}

	@Override
	public String InsertFlowBack(CallBackModel backModel) {
       FlowBackEntity callBackModel = new FlowBackEntity();
       callBackModel.setBatch_no(backModel.getBatchNo());
       callBackModel.setSuccess_count(backModel.getSuccessCount());
       callBackModel.setFaill_count(backModel.getFailCount());
       callBackModel.setCreate_time(new Date());
       if(backModel.getFailCount()>0){
    	   callBackModel.setCode(backModel.getErrorlist().get(0).getCode());
    	   callBackModel.setMsg(backModel.getErrorlist().get(0).getMessage());
    	   callBackModel.setMobile(backModel.getErrorlist().get(0).getMobile());
       }
       int i = flowBackEntityMapper.insertSelective(callBackModel);
       if(i==0){	
    	   return "FAILURE";
       }
		List<FlowBatchEntity> list = flowBatchEntityMapper.selectByBatch(backModel.getBatchNo());
		
		if(list.size()==0){
			return "FAILURE";
		}
		//取出批次号信息
				String convert_code = list.get(0).getBatch_no();
			    String mobiles = list.get(0).getMobile();
			  //查询流量包套餐值信息
				String flow = pointConvertEntityMapper.selectFlowByConvertCode(convert_code).get(0);
				if(flow==null){
					return "FAILURE";
				}
				//构造修改流量包使用状态的参数
				Map<String, Object> map = new HashMap<>();
				map.put("convert_code", convert_code);
				
				//构造信息记录对象
				MessageEntity messageEntity = new MessageEntity();
				messageEntity.setTime(new Date());
				//查询消费者信息
				CustomerEntity customerEntity = customerEntityMapper.selectByAccount(mobiles);
			if(customerEntity==null){
				return "FAILURE";
			}
				messageEntity.setCustomer_id(customerEntity.getId());
				
				//查询消费者设备信息
				final UserEquipmentEntity userEquipmentEntity = userEquipmentEntityMapper.selectByCustomerId(customerEntity.getId());
				
				
				//发送成功
				if(backModel.getSuccessCount()>0){
					
					//添加信息类型
					messageEntity.setType_id(MessageConstant.FlowSuccessExp);
					messageEntity.setParams(flow);
					
					//修改已兑换的流量包使用状态
					map.put("convert_state", pointMallServiceImplp.CONVERT_USED);
					boolean b = pointMallServiceImpl.updateFlowUsedStateByConvertCode(map);
					if(!b){
						return "FAILURE";
					}
					
					//推送成功消息
					if(userEquipmentEntity != null){
						QuestsManagerBean.addQuest(new Quest() {
							@Override
							public boolean execute() {
								JPushUtil.sendMessage(userEquipmentEntity.getPlatform(), userEquipmentEntity.getPushstr(), MessageConstant.FlowSuccessTitle, 
										MessageFormat.format(MessageConstant.FlowSuccessContent, flow));
								return true;
							}
							
							@Override
							public boolean condition() {
								return true;
							}
							
							@Override
							public boolean delete() {
								return true;
							}
						});
					
						
				}
				}else{//发送失败
					
					//添加信息类型
					messageEntity.setType_id(MessageConstant.FlowFailExp);
					messageEntity.setParams(SysConstant.PLAT_PHONE);
					//修改已兑换的流量包使用状态
					map.put("convert_state", pointMallServiceImplp.CONVERT_UNUSED);
					pointMallServiceImpl.updateFlowUsedStateByConvertCode(map);
					
					//发送失败短信
					sendSMSServiceImpl.sendSMS(backModel.getErrorlist().get(0).getMobile(), MessageConstant.FlowFailContent+SysConstant.PLAT_PHONE, SysConstant.ZJL_MESSAGE_SUF);
					
					
					//推送失败消息
				if(userEquipmentEntity != null){
					QuestsManagerBean.addQuest(new Quest() {
						@Override
						public boolean execute() {
							JPushUtil.sendMessage(userEquipmentEntity.getPlatform(), userEquipmentEntity.getPushstr(), MessageConstant.FlowFailTitle, 
									 MessageConstant.FlowFailContent+SysConstant.PLAT_PHONE
									);
							return true;
						}
						
						@Override
						public boolean condition() {
							return true;
						}
						
						@Override
						public boolean delete() {
							return true;
						}
					});
					}
				}
				//添加消息记录
				messageEntityMapper.insertSelective(messageEntity);
				return "SUCCESS";	
		
	}


}
