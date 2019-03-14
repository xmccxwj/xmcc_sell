package com.xmcc.wx_sell.service;

import com.xmcc.wx_sell.common.ResultResponse;
import com.xmcc.wx_sell.vo.ProductCategoryVo;

import java.util.List;

public interface ProductCategoryService {

    ResultResponse<List<ProductCategoryVo>> findAll();

}
