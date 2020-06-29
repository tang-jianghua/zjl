package com.mfangsoft.zhuangjialong.app.prepay.service;

import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.prepay.model.ShopPrepayEntity;
@Service
public interface ShopPrepayService {
	Boolean addShopPrepay(ShopPrepayEntity shopPerpay);
}
