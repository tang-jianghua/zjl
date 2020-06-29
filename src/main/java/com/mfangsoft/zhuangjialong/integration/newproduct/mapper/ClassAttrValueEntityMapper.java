package com.mfangsoft.zhuangjialong.integration.newproduct.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.ClassAttrValueEntity;
@WriterRepository
public interface ClassAttrValueEntityMapper extends BaseClassAttrValueEntityMapper  {
	
	
	
	List<ClassAttrValueEntity>  getClassAttrValueEntityByAttrIdNoWrite(Long attr_id);
	List<ClassAttrValueEntity>  getAttrValueEntityByAttrId(Long attr_id);
	
	List<ClassAttrValueEntity>  getClassAttrValueEntityByAttrIdWrite(Long attr_id);
	
	
	List<ClassAttrValueEntity>  getClassAttrValueEntityByParent(Map<String,Long> map);

}
