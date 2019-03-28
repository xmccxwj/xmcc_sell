package com.xmcc.wx_sell.service;

import com.xmcc.wx_sell.common.ResultResponse;
import com.xmcc.wx_sell.entity.ProductInfo;

public interface ProductInfoService {

    ResultResponse queryList();

    ResultResponse<ProductInfo> queryById(String productId);

    void updateProduct(ProductInfo productInfo);

    ResultResponse<Integer> incrStockById(Integer productQuantity, String productId);
}
