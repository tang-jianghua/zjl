package com.mfangsoft.zhuangjialong.integration.region.mapper;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.brand.model.RegionModel;
import com.mfangsoft.zhuangjialong.app.brand.model.RegionWithLetter;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.integration.region.model.RegionEntity;
@WriterRepository
public interface RegionEntityMapper {
    /**
     * 通过主键删除  
     *
     * @MLTH_generated
     */
    int deleteByPrimaryKey(String code);

    /**
     * 插入数据  
     *
     * @MLTH_generated
     */
    int insert(RegionEntity record);

    int insertSelective(RegionEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    RegionEntity selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(RegionEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(RegionEntity record);
    
    /**
     * 查询所有
     * @return
     */
     List<RegionEntity> select();
     
     /**
      * 查询城市
      * @param code
      * @return
      */
     List<RegionEntity> selectCity(String code);
     /**
      * 查询区县
      * @param code
      * @return
      */
     List<RegionEntity> selectCounty(String code);
     
    
     RegionEntity selectProvinceNameByCode(String code);
     
     
     RegionEntity selectCityNameByCode(String code);
     
     
     RegionEntity selectNameByCode(String code);
     
     /**
      * 根据区域编码查询区域名
      * @param code
      * @return
      */
     String selectRegionByCode(String code);
    
     
     /**
      * 获取服务区域（直辖市）
      * @param code
      * @return
      */
     List<RegionWithLetter> selectRegionWithLetterForMunicipality();
     
     /**
      * 获取服务城市（直辖市）
      * @param code
      * @return
      */
     List<RegionWithLetter> selectCityRegionWithLetterForMunicipality();
     /**
      * 获取服务城市（非直辖市）
      * @param code
      * @return
      */
     List<RegionWithLetter> selectCityRegionWithLetterNotForMunicipality();
     /**
      * 获取服务区域（非直辖市）
      * @param code
      * @return
      */
     List<RegionWithLetter> selectRegionWithLetterNotForMunicipality();

     
     /**
      * 获取服务区域
      * @param code
      * @return
      */
     List<RegionModel> selectOpenRegion();
     /**
      * 判断是否在服务范围内
      * @param code
      * @return
      */
     boolean inRegion(Map<String, String> param);
     
     
     String selectreginName(String region_code);

     
     List<RegionEntity> selectServiceRegion();
     
     List<RegionEntity> queryserviceCityByprovence(String code);
     
     List<RegionEntity> queryserviceCountryByCity(String code);
}