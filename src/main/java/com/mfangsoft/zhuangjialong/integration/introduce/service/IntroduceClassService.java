package com.mfangsoft.zhuangjialong.integration.introduce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.introduce.model.IntroduceClass;
import com.mfangsoft.zhuangjialong.integration.introduce.model.IntroduceClassEntity;
import com.mfangsoft.zhuangjialong.integration.introduce.model.IntroduceProductEntity;
import com.mfangsoft.zhuangjialong.integration.introduce.model.IntroduceProductParam;
import com.mfangsoft.zhuangjialong.integration.introduce.model.PartnerClass;

@Service
public interface IntroduceClassService {

	public List<PartnerClass> getAllPartnerClassWithIntroState();
	
	public boolean addIntroduceClass(IntroduceClassEntity record);
	
	public List<IntroduceClassEntity> queryIntroduceClass();
	
	public void deleteIntroduceClass(Long id);
	
	public boolean deleteIntroduceProduct(Long introduce_id, Long introduce_product_id);
	
	public boolean modifyIntroduceClass(Long id1,Long id2);
	
	public ResponseMessage addIntroduceProduct(IntroduceProductParam record, ResponseMessage responseMessage);
	
	public List<IntroduceClass> queryIntroduceProduct();
	
	public boolean addClassShowImg(BuildClassEntity record);
}
