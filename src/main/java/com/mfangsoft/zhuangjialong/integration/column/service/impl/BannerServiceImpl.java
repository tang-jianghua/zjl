package com.mfangsoft.zhuangjialong.integration.column.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.main.mapper.MainBannerEntityMapper;
import com.mfangsoft.zhuangjialong.app.main.model.MainBannerEntity;
import com.mfangsoft.zhuangjialong.common.utils.MyCacheManager;
import com.mfangsoft.zhuangjialong.common.utils.PropUtis;
import com.mfangsoft.zhuangjialong.integration.column.service.BannerService;
@Service(value="bannerservice")
public class BannerServiceImpl  implements BannerService{


	@Autowired
	private MainBannerEntityMapper mainBannerEntityMapper;
	
	private MyCacheManager<List<MainBannerEntity>> mblCache;
	public BannerServiceImpl(){
		mblCache = new MyCacheManager<List<MainBannerEntity>>();
	}
	
	@Override
	public Boolean addBanner(MainBannerEntity mainBannerEntity) {
		
		
		if(mainBannerEntityMapper.insertSelective(mainBannerEntity)>0){
			reload();
			return true;
		}
		return true;
	}

	@Override
	public List<MainBannerEntity> getMainBannerList() {
		// TODO Auto-generated method stub
		//添加缓存
		List<MainBannerEntity> mbl = mblCache.getValue("/app/mainbanner");
		if (mbl == null){
			mbl = mainBannerEntityMapper.selectMainBannersList();
			mblCache.addOrUpdateCache("/app/mainbanner", mbl);
		}
		return mbl;
	}

	@Override
	public List<MainBannerEntity> selectMainBanneradList() {
		// TODO Auto-generated method stub
		return mainBannerEntityMapper.selectMainBanneradList();
	}

	@Override
	public Boolean modfiyBanner(MainBannerEntity mainBannerEntity) {
		// TODO Auto-generated method stub
		
		if(mainBannerEntityMapper.updateByPrimaryKeySelective(mainBannerEntity)>0){
			reload();
			return false;
		}
		return true;
	}
	
	public void reload() { 
		mblCache.evictCache(); 
	}

	@Override
	public List<MainBannerEntity> getAdminMainBannerList(Map<String,Object> map) {
		// TODO Auto-generated method stub
		
		
		
		
		return mainBannerEntityMapper.selectAdminMainBannersList(map);
	}

	@Override
	public Boolean removeBanner(Long id) {
		// TODO Auto-generated method stub
		if(mainBannerEntityMapper.deleteByPrimaryKey(id)>0){
			return true;
		}
		return false;
	} 
}
