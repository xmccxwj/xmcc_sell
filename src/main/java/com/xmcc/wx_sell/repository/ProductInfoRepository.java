package com.xmcc.wx_sell.repository;

import com.xmcc.wx_sell.common.ResultResponse;
import com.xmcc.wx_sell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    //根据类目编号 查询正常上架的商品
    List<ProductInfo> findByProductStatusAndCategoryTypeIn(Integer status,List<Integer> categoryList);
    @Modifying
    @Query(value = "update product_info set product_stock=product_stock+?1 where product_id=?2",nativeQuery = true)
    int productInfoRepository(Integer productQuantity, String productId);
}
