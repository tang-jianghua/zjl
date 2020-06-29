package com.mfangsoft.zhuangjialong.system.service;

import com.mfangsoft.zhuangjialong.core.utils.PageInfo;
import com.mfangsoft.zhuangjialong.system.model.SysLog;

/**
 * @description：操作日志
 * @author：zhixuan.wang
 * @date：2015/10/30 10:35
 */
public interface LogService {

    void insertLog(SysLog sysLog);

    void findDataGrid(PageInfo pageInfo);
}
