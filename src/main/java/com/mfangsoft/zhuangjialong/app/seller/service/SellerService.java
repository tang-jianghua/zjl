package com.mfangsoft.zhuangjialong.app.seller.service;

import com.mfangsoft.zhuangjialong.app.seller.model.SellerEntity;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;

public interface SellerService {
	public ResponseMessage<String> register(SellerEntity seller);
}
