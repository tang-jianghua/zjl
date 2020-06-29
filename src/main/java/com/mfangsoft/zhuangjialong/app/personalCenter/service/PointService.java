package com.mfangsoft.zhuangjialong.app.personalCenter.service;

import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.app.personalCenter.model.PointEntity;
import com.mfangsoft.zhuangjialong.core.exception.ServiceException;

/**
* @description：积分接口
* @author：Jianghua.Tang 
* @date：2016年6月2日
* 
*/
@Service
public interface PointService {
       public   PointEntity selectByPrimaryKey(Long id) throws ServiceException;
       public PointEntity selectByCustomerId(long customer_id) throws ServiceException;
}
