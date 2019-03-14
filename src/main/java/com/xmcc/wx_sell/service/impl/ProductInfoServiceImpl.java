package com.xmcc.wx_sell.service.impl;

import com.xmcc.wx_sell.common.ResultEnums;
import com.xmcc.wx_sell.common.ResultResponse;
import com.xmcc.wx_sell.entity.ProductInfo;
import com.xmcc.wx_sell.repository.ProductInfoRepository;
import com.xmcc.wx_sell.service.ProductCategoryService;
import com.xmcc.wx_sell.service.ProductInfoService;
import com.xmcc.wx_sell.vo.ProductCategoryVo;
import com.xmcc.wx_sell.vo.ProductInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Override
    public ResultResponse queryList() {
        ResultResponse<List<ProductCategoryVo>> categoryServiceResult = productCategoryService.findAll();
        List<ProductCategoryVo> categoryVoList = categoryServiceResult.getData();
        if(CollectionUtils.isEmpty(categoryVoList)){
            return categoryServiceResult;//如果分类列表为空 直接返回了
        }
        //获得类目编号集合
        List<Integer> categoryTypeList = categoryVoList.stream().map(categoryVo -> categoryVo.getCategoryType()).collect(Collectors.toList());
        //查询商品列表  这里商品上下架可以用枚举 方便扩展
        List<ProductInfo> productInfoList = productInfoRepository.findByProductStatusAndCategoryTypeIn(ResultEnums.PRODUCT_UP.getCode(), categoryTypeList);
        //多线程遍历 取出每个商品类目编号对应的商品列表 设置进入类目中
        List<ProductCategoryVo> finalResultList = categoryVoList.parallelStream().map(categoryVo -> {
            categoryVo.setProductInfoVoList(productInfoList.stream().
                    filter(productInfo -> productInfo.getCategoryType() == categoryVo.getCategoryType()).map(productInfo ->
                    ProductInfoVo.build(productInfo)).collect(Collectors.toList()));
            return categoryVo;
        }).collect(Collectors.toList());
        return ResultResponse.success(finalResultList);
    }
}
