package com.mfangsoft.zhuangjialong.app.inviteconvert.service;


import com.mfangsoft.zhuangjialong.app.inviteconvert.model.InviteConvertEntity;
import com.mfangsoft.zhuangjialong.app.inviteconvert.model.InviteConvertMainModel;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年10月31日
* 
*/

public interface InviteConvertService {
	
      /*
       * 获取好友集结令首页
       */
	InviteConvertMainModel getInviteMain(InviteConvertEntity inviteConvertEntity);
	 
	 boolean addInviteConvert(InviteConvertEntity inviteConvertEntity);
	 
}
