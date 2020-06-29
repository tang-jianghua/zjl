package com.mfangsoft.zhuangjialong.integration.prepayment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.integration.prepayment.model.PrepayValue;

@Service
public interface PrepayValueService {
	public List<PrepayValue> selectAll();
}
