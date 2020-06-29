package com.mfangsoft.zhuangjialong.system.mapper;

import java.util.List;

import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
import com.mfangsoft.zhuangjialong.core.utils.PageInfo;
import com.mfangsoft.zhuangjialong.system.model.SysLog;
@WriterRepository
public interface SysLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

    List findDataGrid(PageInfo pageInfo);

    int findDataGridCount(PageInfo pageInfo);
}