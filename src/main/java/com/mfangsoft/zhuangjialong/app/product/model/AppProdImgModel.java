package com.mfangsoft.zhuangjialong.app.product.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mfangsoft.zhuangjialong.integration.newproduct.model.BaseBrandProdImgEntity;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月10日
* 
*/
@JsonInclude(value=Include.NON_NULL)
public class AppProdImgModel extends BaseBrandProdImgEntity{

}
