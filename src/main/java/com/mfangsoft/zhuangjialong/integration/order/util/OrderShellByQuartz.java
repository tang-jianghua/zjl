package com.mfangsoft.zhuangjialong.integration.order.util;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.mfangsoft.zhuangjialong.app.order.mapper.OrderEntityMapper;
import com.mfangsoft.zhuangjialong.integration.order.service.impl.OrderServiceImpl;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.order.mapper.OrderShellInfoEntityMapper;
import com.mfangsoft.zhuangjialong.integration.order.mapper.OrderShellListEntityMapper;
import com.mfangsoft.zhuangjialong.integration.order.mapper.OrderShellStateRelationEntityMapper;
import com.mfangsoft.zhuangjialong.integration.order.model.OrderShellInfoEntity;

/**
 * 结算订单
 * 
 * @author LiYanchen
 *
 */
public class OrderShellByQuartz {

	@Autowired
	OrderShellInfoEntityMapper orderShellInfoEntityMapper;
	@Autowired
	OrderEntityMapper orderEntityMapper;
	@Autowired
	OrderShellListEntityMapper orderShellListEntityMapper;
	@Autowired
	OrderShellStateRelationEntityMapper orderShellStateRelationEntityMapper;
	@Autowired
	OrderServiceImpl orderServiceImpl;
	
	
	public void execute() {
//上线时注释打开
		if (SysConstant.getIpAdress().contains(SysConstant.ip)) {
			System.out.println("====OrderShellByQuartz=============");
			checkAndcancelOrders();
		}
	}

	/////////////////// 结算订单////////////////////
	private void checkAndcancelOrders() {
		try{
		OrderShellInfoEntity entity = orderShellInfoEntityMapper.selectData();
		if(entity == null || entity.getStart_time() == null || entity.getDays() <= 0){
			return;
		}
//		if(entity.getEnd_time() == null && entity.getStart_time()!= null && entity.getDays() > 0){
//			
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTimeInMillis(entity.getStart_time().getTime());
//			calendar.add(Calendar.DAY_OF_MONTH, entity.getDays());
//			
//			entity.setEnd_time(new Date(calendar.getTimeInMillis()));
//			orderShellInfoEntityMapper.updateByPrimaryKeySelective(entity);
//		}
		if(validate(entity)){
			
			entity.setExu_state(2);
			orderShellInfoEntityMapper.updateByPrimaryKeySelective(entity);
			
			//执行
			orderServiceImpl.exuShellOrders(entity);
			//执行完更新下次时间段
			
			entity.setExu_state(1);
//			entity.setStart_time(entity.getEnd_time());
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(entity.getStart_time().getTime());
			calendar.add(Calendar.DAY_OF_MONTH, entity.getDays());
			
			entity.setStart_time(new Date(calendar.getTimeInMillis()));
			
			entity.setUpdate_time(new Date());
			orderShellInfoEntityMapper.updateByPrimaryKeySelective(entity);
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private boolean validate(OrderShellInfoEntity entity){
		boolean flag = entity.getDays() > 0;
		flag = flag && entity.getStart_time()!= null && entity.getStart_time().getTime() < System.currentTimeMillis();
//		flag = flag && entity.getEnd_time() != null && entity.getEnd_time().getTime() < System.currentTimeMillis();
		flag = flag && entity.getExu_state() == 1;
		
		return flag;
	}
}
