package com.mfangsoft.zhuangjialong.app.main.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.main.model.MainBannerEntity;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;

@WriterRepository
public interface MainBannerEntityMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(MainBannerEntity record);

    int insertSelective(MainBannerEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    MainBannerEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MainBannerEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(MainBannerEntity record);
    
    /**
     *获取所有首页轮播图
     *
     * @MLTH_generated
     */
   List<MainBannerEntity> selectMainBanners(String region_code);
   
   /**
    *获取平台轮播图
    *
    * @MLTH_generated
    */
  List<MainBannerEntity> selectMainBannersForKFZ();
   
   /**
    *获取广告
    *
    * @MLTH_generated
    */
   MainBannerEntity selectAd();
   
   List<MainBannerEntity>  selectAdminMainBannersList(Map<String,Object> map);
   
   List<MainBannerEntity>  selectMainBannersList();
   
   List<MainBannerEntity> selectMainBannersByUserId(Long user_id);
   
   List<MainBannerEntity> selectMainBanneradList();
}