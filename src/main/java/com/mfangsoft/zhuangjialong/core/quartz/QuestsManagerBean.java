package com.mfangsoft.zhuangjialong.core.quartz;

import java.util.Vector;

import org.springframework.stereotype.Component;

import com.mfangsoft.zhuangjialong.common.utils.SpringBeantUtils;

@Component("questsManagerBean")
public class QuestsManagerBean {

	static Vector<Quest> questList = new Vector<Quest>();
	
	public static QuestsManagerBean instance;

	public QuestsManagerBean QuestsManagerBean(){
		if(instance == null){
			instance = (QuestsManagerBean) SpringBeantUtils.getBean("questsManagerBean");
		}
		return instance;
	}
	
//	public static QuestsManagerBean getInstance(){
//		if(instance == null){
//			instance = (QuestsManagerBean) SpringBeantUtils.getBean("questsManagerBean");
//		}
//		return instance;
//	}
	
	public static void addQuest(Quest quest){
		questList.addElement(quest);
	}
	
	public static void delQuest(Quest quest){
		questList.removeElement(quest);
	}
}
