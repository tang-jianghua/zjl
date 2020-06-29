package com.mfangsoft.zhuangjialong.app.weixin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.weixin.mapper.WeiXinStoreMapper;
import com.mfangsoft.zhuangjialong.app.weixin.model.WeiXinStore;
import com.mfangsoft.zhuangjialong.app.weixin.service.WeixinStoreService;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

/**
 * @author 作者 : peidingjun
 *
 * @date 创建时间：2016年11月17日 上午10:47:42
 * 
 * @description ：查询列表
 */
@Service
public class WeixinStoreServiceImpl implements WeixinStoreService {
	@Autowired
	private WeiXinStoreMapper weiXinStoreMapper;

	@Override
	public List<WeiXinStore> queryBrandForPage(String business_name) {
		List<WeiXinStore> list = weiXinStoreMapper.selectStoreListByBusinessName(business_name);
		return list;
	}

	// 添加门店
	@Override
	public void addWeixinStore(WeiXinStore weiXinStore) {
		weiXinStoreMapper.insert(weiXinStore);
	}

	// 根据主键查询微信门店
	@Override
	public WeiXinStore selectByPrimaryKey(WeiXinStore param) {
		// TODO Auto-generated method stub
	 WeiXinStore weiXinStore = weiXinStoreMapper.selectByPrimaryKey(param.getSid());
return weiXinStore;
	}
  //更新门店
	@Override
	public void updateByPrimaryKey(WeiXinStore weiXinStore) {
		weiXinStoreMapper.updateByPrimaryKey(weiXinStore);
		
	}
    //删除门店
	@Override
	public void deleteByPrimaryKey(WeiXinStore weiXinStore) {
		weiXinStoreMapper.deleteByPrimaryKey(weiXinStore.getSid());
		
	}
}
