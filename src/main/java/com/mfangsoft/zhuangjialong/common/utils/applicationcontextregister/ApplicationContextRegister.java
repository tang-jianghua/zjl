package com.mfangsoft.zhuangjialong.common.utils.applicationcontextregister;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.mfangsoft.zhuangjialong.common.utils.SpringBeantUtils;

public class ApplicationContextRegister implements ApplicationContextAware {
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringBeantUtils.setApplicationContext(applicationContext);
	}
}
