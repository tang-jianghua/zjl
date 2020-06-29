package com.mfangsoft.zhuangjialong.integration.newproduct.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.ClassAttrEntity;
@WriterRepository
public interface ClassAttrEntityMapper extends  BaseClassAttrEntityMapper{
	
	
	List<ClassAttrEntity> getClassAttrEntityByClassId(Long class_id);

}
