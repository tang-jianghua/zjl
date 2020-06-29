package com.mfangsoft.zhuangjialong.app.newproductcore.mapper;

import com.mfangsoft.zhuangjialong.app.newproductcore.model.NewProductCoreModel;
import com.mfangsoft.zhuangjialong.common.utils.stereotype.WriterRepository;
@WriterRepository
public interface NewProductCoreModelMapper {
    NewProductCoreModel selectProductCoreByProductId(Long product_id);
}