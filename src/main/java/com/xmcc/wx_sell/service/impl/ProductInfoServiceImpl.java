package com.xmcc.wx_sell.service.impl;

import com.xmcc.wx_sell.common.ProductEnums;

import com.xmcc.wx_sell.common.ResultEnums;
import com.xmcc.wx_sell.common.ResultResponse;
import com.xmcc.wx_sell.entity.ProductInfo;
import com.xmcc.wx_sell.repository.ProductInfoRepository;
import com.xmcc.wx_sell.service.ProductCategoryService;
import com.xmcc.wx_sell.service.ProductInfoService;
import com.xmcc.wx_sell.vo.ProductCategoryVo;
import com.xmcc.wx_sell.vo.ProductInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.util.List;
import java.util.Optional;
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
        List<ProductInfo> productInfoList = productInfoRepository.findByProductStatusAndCategoryTypeIn(ProductEnums.PRODUCT_UP.getCode(), categoryTypeList);
        //多线程遍历 取出每个商品类目编号对应的商品列表 设置进入类目中
        List<ProductCategoryVo> finalResultList = categoryVoList.parallelStream().map(categoryVo -> {
            categoryVo.setProductInfoVoList(productInfoList.stream().
                    filter(productInfo -> productInfo.getCategoryType() == categoryVo.getCategoryType()).map(productInfo ->
                    ProductInfoVo.build(productInfo)).collect(Collectors.toList()));
            return categoryVo;
        }).collect(Collectors.toList());
        return ResultResponse.success(finalResultList);
    }

    @Override
    public ResultResponse<ProductInfo> queryById(String productId) {
        //使用common-lang3 jar的类 没导入自己导入一下
        if(StringUtils.isBlank(productId)){
            return ResultResponse.fail(ResultEnums.PARAM_ERROR.getMsg()+":"+productId);
        }
        Optional<ProductInfo> byId = productInfoRepository.findById(productId);
        if(!byId.isPresent()){
            return ResultResponse.fail(productId+":"+ResultEnums.NOT_EXITS.getMsg());
        }
        ProductInfo productInfo = byId.get();
        //判断商品是否下架
        if(productInfo.getProductStatus()==ProductEnums.PRODUCT_DOWN.getCode()){
            return ResultResponse.fail(ProductEnums.PRODUCT_DOWN.getMsg());
        }

        return ResultResponse.success(productInfo);
    }

    @Override
    @Transactional
    public void updateProduct(ProductInfo productInfo) {
        productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public ResultResponse<Integer> incrStockById(Integer productQuantity, String productId) {
        return ResultResponse.success(productInfoRepository.productInfoRepository(productQuantity,productId));
    }
}
