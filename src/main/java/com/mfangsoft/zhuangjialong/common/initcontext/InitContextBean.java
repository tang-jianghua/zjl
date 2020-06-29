package com.mfangsoft.zhuangjialong.common.initcontext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildClassEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.model.EntProdAttrValueName;
import com.mfangsoft.zhuangjialong.integration.entnewproduct.service.EntProductService;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.ClassAttrEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.mapper.ClassAttrValueEntityMapper;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BrandProdAttrValueName;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.ClassAttrEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.ClassAttrValueEntity;
import com.mfangsoft.zhuangjialong.integration.newproduct.service.BrandProductService;
import com.mfangsoft.zhuangjialong.integration.newproduct.service.ClassAttrService;
import com.mfangsoft.zhuangjialong.integration.sensitivewords.mapper.SensitiveWordsMapper;
import com.mfangsoft.zhuangjialong.integration.sensitivewords.model.BaseSensitiveWordsEntity;
import com.mfangsoft.zhuangjialong.integration.sensitivewords.util.SensitiveUtil;
import com.mfangsoft.zhuangjialong.system.mapper.sysConstantEntityMapper;
import com.mfangsoft.zhuangjialong.system.model.sysConstantEntity;

public class InitContextBean implements  InitializingBean {

	@Autowired
	private ClassAttrEntityMapper classAttrEntityMapper;
	@Autowired
	private BuildClassEntityMapper buildClassEntityMapper;
	@Autowired
	private ClassAttrService classAttrService;
	 
	
	@Autowired
	
	private  EntProductService entProductService;
	
	@Autowired
	private BrandProductService brandProductService;
	
	
	@Autowired
	private sysConstantEntityMapper constantEntityMapper;
	
	@Autowired
	private SensitiveWordsMapper sensitiveWordsMapper;
	
	@Override
	public void afterPropertiesSet(){
		// TODO Auto-generated method stub
		
		
		RedisTemplate<String,Object> redisTemplate=RedisUtils.getRedisTemplate();
		
		List<BuildClassEntity> buildClassEntities=buildClassEntityMapper.geBuildClassEntities();
		
		
		
		HashOperations<String,String,List<ClassAttrEntity>> hashOperations=redisTemplate.opsForHash();
		
		HashOperations<String,String,List<ClassAttrValueEntity>> hashOperations2=redisTemplate.opsForHash();
		for (BuildClassEntity buildClassEntity : buildClassEntities) {
			 List<ClassAttrEntity> classAttrEntities=classAttrEntityMapper.getClassAttrEntityByClassId(buildClassEntity.getId());
		//	 if(hashOperations.get("class_attr", buildClassEntity.getId()+"")==null){
			 
				 hashOperations.put("class_attr", buildClassEntity.getId()+"", classAttrEntities);
			// }
			 for (ClassAttrEntity classAttrEntity : classAttrEntities) {
				 
				 
				List<ClassAttrValueEntity>  attrValueEntities=classAttrService.getClassAttrValueEntityforRedisByAttrId(classAttrEntity.getId(), classAttrEntity.getIswrite());
				//if(hashOperations2.get("class_attr_value", classAttrEntity.getId()+"")==null){
					
					hashOperations2.put("class_attr_value", classAttrEntity.getId()+"", attrValueEntities);
				}
			//}
			 
		}
		HashOperations<String,String,EntProdAttrValueName> hashOperations3=redisTemplate.opsForHash();
		List<EntProdAttrValueName> attrValueNames=entProductService.selectproductAttrValueName();
		
		for (EntProdAttrValueName entProdAttrValueName : attrValueNames) {
			
		//	if(hashOperations3.get("ent_product_attr_value_name", entProdAttrValueName.getProduct_id()+"")==null){
				
				hashOperations3.put("ent_product_attr_value_name", entProdAttrValueName.getProduct_id()+"", entProdAttrValueName);
				
			//}
			
		}
		
//		HashOperations<String,String,BrandProdAttrValueName> hashOperations4=redisTemplate.opsForHash();
//		List<BrandProdAttrValueName> brandProdAttrValueNames=brandProductService.selectproductAttrValueName();
//		for (BrandProdAttrValueName entProdAttrValueName : brandProdAttrValueNames) {
//			
//			
//			if(hashOperations4.get("brand_product_attr_value_name", entProdAttrValueName.getProduct_id()+"")==null){
//				
//				hashOperations4.put("brand_product_attr_value_name", entProdAttrValueName.getProduct_id()+"", entProdAttrValueName);
//			
//			
//			}
//		}
		
		 List<sysConstantEntity> constantEntities=constantEntityMapper.getALLSysConstant();
		
		 for (sysConstantEntity sysConstantEntity : constantEntities) {
			 
			// if(RedisUtils.getMapValue(sysConstantEntity.getType(), sysConstantEntity.getKey())==null){
				 
				 RedisUtils.setMapValue(sysConstantEntity.getType(), sysConstantEntity.getKey(), sysConstantEntity);
			// }

			
			 
		}
		
		 StringBuffer stringBuffer =new StringBuffer();
		 List<BaseSensitiveWordsEntity> sensitiveWords = sensitiveWordsMapper.selectAllSensitiveWords();
		 for (BaseSensitiveWordsEntity baseSensitiveWordsEntity : sensitiveWords) {
			 String[] strings = baseSensitiveWordsEntity.getContent().split("，");
			for (int i = 0; i < strings.length; i++) {
				if(strings[i] !=""){
					if(i<strings.length-1){
					stringBuffer.append(strings[i]).append(",");
					}else{
						stringBuffer.append(strings[i]);
					}
				}
			}
		}
		 SensitiveUtil.addSensitiveWord(stringBuffer.toString());
	}

	

}
