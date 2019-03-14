package com.xmcc.wx_sell.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.xmcc.wx_sell.entity.ProductCategory;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductCategoryVo implements Serializable {
    /** 类目名字. */
    @JsonProperty("name")
    private String categoryName;

    /** 类目编号. */
    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<ProductInfoVo> productInfoVoList;

    public static ProductCategoryVo build(ProductCategory productCategory){
        ProductCategoryVo productCategoryVo = new ProductCategoryVo();
        BeanUtils.copyProperties(productCategory,productCategoryVo);
        return productCategoryVo;
    }
}
