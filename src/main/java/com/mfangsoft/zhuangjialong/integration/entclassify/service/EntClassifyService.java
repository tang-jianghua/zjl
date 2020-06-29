package com.mfangsoft.zhuangjialong.integration.entclassify.service;

import java.util.List;

import com.mfangsoft.zhuangjialong.integration.entclassify.model.EntClassifyEntity;

public interface EntClassifyService {
	
	Boolean addEntClassify(EntClassifyEntity entClassifyEntity);
	
	
	Boolean modifyEntClassify(EntClassifyEntity entClassifyEntity);
	
	
	List<EntClassifyEntity> queryEntClassify();
	
	
	Boolean romveEntClassify(Long  id);

}
