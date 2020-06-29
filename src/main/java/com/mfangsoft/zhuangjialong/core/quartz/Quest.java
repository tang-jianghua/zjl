package com.mfangsoft.zhuangjialong.core.quartz;

public interface Quest {

	public boolean condition();
	
	public boolean execute();
	
	public boolean delete();
	
}
