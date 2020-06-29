package com.mfangsoft.zhuangjialong.app.mapservice.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.app.mapservice.model.AskForHelpModel;
import com.mfangsoft.zhuangjialong.app.mapservice.model.BaseAskhelpEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年12月14日
* 
*/
@WriterRepository
public interface AskhelpMapper  extends BaseAskhelpEntityMapper{

	/*
	 * 查询某地区的求助
	 */
	List<AskForHelpModel> selectByRegionCode(String region_code);
	
	/*
	 * 查询单个求助
	 */
	AskForHelpModel selectById(Integer id);
	
	/*
	 * 查询某个经纬度的求助
	 */
	List<Integer> selectByLBSAndUserId(BaseAskhelpEntity askhelpEntity);
}
