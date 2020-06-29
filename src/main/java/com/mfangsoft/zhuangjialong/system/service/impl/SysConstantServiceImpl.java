package com.mfangsoft.zhuangjialong.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.system.mapper.sysConstantEntityMapper;
import com.mfangsoft.zhuangjialong.system.model.sysConstantEntity;
import com.mfangsoft.zhuangjialong.system.service.SysConstantService;
@Service
public class SysConstantServiceImpl  implements  SysConstantService{

	
	private sysConstantEntityMapper sysConstantEntityMapper;

	@Override
	public sysConstantEntity getSysConstantById(Integer id) {
		// TODO Auto-generated method stub
		return sysConstantEntityMapper.selectByPrimaryKey(id);
	}

	@Override
	public sysConstantEntity getSysConstantByKey(String key) {
		// TODO Auto-generated method stub
		return sysConstantEntityMapper.getSysConstantByKey(key);
	}

	@Override
	public List<sysConstantEntity> getSysConstantByType(String type) {
		// TODO Auto-generated method stub
		return sysConstantEntityMapper.getSysConstantByType(type);
	}

	@Override
	public Map<String, Object> getSysConstantByKeyForMap(String key) {
		// TODO Auto-generated method stub
		return sysConstantEntityMapper.getSysConstantByKeyForMap(key);
	}
	

}
