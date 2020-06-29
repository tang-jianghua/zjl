package com.mfangsoft.zhuangjialong.integration.prepayment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.integration.prepayment.mapper.PrepayValueMapper;
import com.mfangsoft.zhuangjialong.integration.prepayment.model.PrepayValue;
import com.mfangsoft.zhuangjialong.integration.prepayment.service.PrepayValueService;

@Service
public class PrepayValueServiceImpl implements PrepayValueService{
	@Autowired
	private PrepayValueMapper prepayValueMapper;
	
	@Override
	public List<PrepayValue> selectAll() {
		// TODO Auto-generated method stub
		
		return prepayValueMapper.selectAll();
	}
	
}
