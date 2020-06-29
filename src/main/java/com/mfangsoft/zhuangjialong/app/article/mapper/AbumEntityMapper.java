package com.mfangsoft.zhuangjialong.app.article.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mfangsoft.zhuangjialong.app.article.model.AbumEntity;
import com.mfangsoft.zhuangjialong.app.article.model.Album;
import com.mfangsoft.zhuangjialong.common.model.Page;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface AbumEntityMapper {
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
    int insert(AbumEntity record);

    int insertSelective(AbumEntity record);

    /**
     * 通过主键查询数据 
     *
     * @MLTH_generated
     */
    AbumEntity selectByPrimaryKey(Long id);
    
    /**
     * 通过主键查询名称和图片
     *
     * @MLTH_generated
     */
    Album selectNameAndImgByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AbumEntity record);

    /**
     * 通过主键更新数据  
     *
     * @MLTH_generated
     */
    int updateByPrimaryKey(AbumEntity record);
    
    List<Map<String, Object>> selectAbumForPage(Page<Map<String, Object>> page);
    
    
     List<AbumEntity>   queryAbumList();
     
     List<AbumEntity>   queryAdminAbumList(@Param("user_id") Long user_id );
}