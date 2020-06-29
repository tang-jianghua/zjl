package com.mfangsoft.zhuangjialong.core.quartz;

import java.util.Enumeration;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.mfangsoft.zhuangjialong.common.utils.SpringBeantUtils;

public class SpringQtz {

	protected void execute(){
System.out.println("----------SpringQtz-------------------------size--" + QuestsManagerBean.questList.size());
		Enumeration<Quest> eles = QuestsManagerBean.questList.elements();
		while (eles.hasMoreElements()) {
			Quest quest = eles.nextElement();
			if (quest != null) {
				try {
					if (quest.condition()) {
						quest.execute();
						if (quest.delete())
							QuestsManagerBean.delQuest(quest);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					QuestsManagerBean.questList.removeElement(quest);
				}
			}
		}

	}

	private void test(){
	///// 使用方法//////////////////////////////////////

	 QuestsManagerBean questsManagerBean =
	 (QuestsManagerBean)SpringBeantUtils.getBean("questsManagerBean");
	
	 questsManagerBean.addQuest(new Quest() {
	
	 @Override
	 public boolean execute() {
		 return true;
	 }
	
	 @Override
	 public boolean condition() {
		 return true;
	 }

	@Override
	public boolean delete() {
		return false;
	}
	 });
	}
	///////////////////////////////////////////

}
