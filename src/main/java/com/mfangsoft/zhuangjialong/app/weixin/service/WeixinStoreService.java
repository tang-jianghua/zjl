package com.mfangsoft.zhuangjialong.app.weixin.service;

import java.util.List;
import java.util.Map;

import com.mfangsoft.zhuangjialong.app.article.model.ArticleEntity;
import com.mfangsoft.zhuangjialong.app.weixin.model.WeiXinStore;
import com.mfangsoft.zhuangjialong.common.model.Page;

/** 
 * @author  作者 : peidingjun
 *
 * @date 创建时间：2016年11月17日 上午10:43:22
 * 
 *  @description ： 
 */
public interface WeixinStoreService {
	//模糊查询门店
	public List<WeiXinStore> queryBrandForPage(String business_name);
	//添加门店
	 public void addWeixinStore (WeiXinStore weiXinStore);
	 //根据主键查询微信门店
	 public WeiXinStore selectByPrimaryKey(WeiXinStore param);
	 //根据主键更新门店
	 public void updateByPrimaryKey(WeiXinStore weiXinStore);
  //根据主键删除门店
  public void deleteByPrimaryKey(WeiXinStore weiXinStore);
}
