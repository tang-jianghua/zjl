package com.mfangsoft.zhuangjialong.integration.sensitivewords.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.RedisUtils;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.sensitivewords.mapper.SensitiveWordsMapper;
import com.mfangsoft.zhuangjialong.integration.sensitivewords.model.BaseSensitiveWordsEntity;
import com.mfangsoft.zhuangjialong.integration.sensitivewords.service.SensitiveWordsService;
import com.mfangsoft.zhuangjialong.integration.sensitivewords.util.SensitiveUtil;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月28日
* 
*/
@Service
public class SensitiveWordsServiceImpl implements SensitiveWordsService{

	@Autowired
	SensitiveWordsMapper sensitiveWordsMapper;

	@Override
	public Page<BaseSensitiveWordsEntity> querySensitiveWordsForPage(Page<BaseSensitiveWordsEntity> page) {
		 List<BaseSensitiveWordsEntity> list = sensitiveWordsMapper.selectSensitiveWordsForPage(page);
		 page.setData(list);
		 return page;
	}

	@Override
	public Boolean addSensitiveWord(BaseSensitiveWordsEntity sensitiveWordsEntity) {
		sensitiveWordsEntity.setCreate_time(new Date());
		return sensitiveWordsMapper.insertSelective(sensitiveWordsEntity)>0;
	}

	@Override
	public Boolean modifySensitiveWord(BaseSensitiveWordsEntity sensitiveWordsEntity) {
   
		if(sensitiveWordsMapper.updateByPrimaryKeySelective(sensitiveWordsEntity)>0){
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
		return true;
	}

	@Override
	public Boolean publishSensitiveWord(BaseSensitiveWordsEntity sensitiveWordsEntity) {
		if(sensitiveWordsEntity.getState()==1){
			sensitiveWordsEntity.setPublic_time(new Date());
		}
		if(sensitiveWordsMapper.updateByPrimaryKeySelective(sensitiveWordsEntity)>0){
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
		return true;
	}
	
	@Override
	public BaseSensitiveWordsEntity querySensitiveWord(Integer id) {
		return sensitiveWordsMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer deleteSensitiveWord(Integer id) {
		BaseSensitiveWordsEntity entity = sensitiveWordsMapper.selectByPrimaryKey(id);
		if(entity==null){
			return 0;
		}
		if( entity.getState()==1){
			return 2;
		}
		if(sensitiveWordsMapper.deleteByPrimaryKey(id)>0){
		return 1;
		}else{
			return 0;
		}
	}

}
