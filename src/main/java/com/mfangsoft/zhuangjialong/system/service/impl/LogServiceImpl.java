package com.mfangsoft.zhuangjialong.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.core.utils.PageInfo;
import com.mfangsoft.zhuangjialong.system.mapper.SysLogMapper;
import com.mfangsoft.zhuangjialong.system.model.SysLog;
import com.mfangsoft.zhuangjialong.system.service.LogService;

/**
 * @description：
 * @author：zhixuan.wang
 * @date：2015/10/30 10:40
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    
    public void insertLog(SysLog sysLog) {
        sysLogMapper.insert(sysLog);
    }

    
    public void findDataGrid(PageInfo pageInfo) {
        pageInfo.setRows(sysLogMapper.findDataGrid(pageInfo));
        pageInfo.setTotal(sysLogMapper.findDataGridCount(pageInfo));
    }
    

    
    
}
