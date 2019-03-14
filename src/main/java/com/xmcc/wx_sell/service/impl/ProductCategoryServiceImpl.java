package com.xmcc.wx_sell.service.impl;

import com.google.common.collect.Lists;
import com.xmcc.wx_sell.common.ResultResponse;
import com.xmcc.wx_sell.entity.ProductCategory;
import com.xmcc.wx_sell.repository.ProductCategoryRepository;
import com.xmcc.wx_sell.service.ProductCategoryService;
import com.xmcc.wx_sell.vo.ProductCategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ResultResponse<List<ProductCategoryVo>> findAll() {
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        //利用流转换为vo集合

        return ResultResponse.success(productCategoryList.stream().map(productCategory ->
                ProductCategoryVo.build(productCategory)
        ).collect(Collectors.toList()));
    }
}
