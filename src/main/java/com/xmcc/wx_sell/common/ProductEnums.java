package com.xmcc.wx_sell.common;

import lombok.Getter;

@Getter
public enum ProductEnums {
    PRODUCT_UP(0,"正常"),
    PRODUCT_DOWN(1,"商品下架"),
    PRODUCT_NOT_ENOUGH(10,"商品库存不足")
    ;
    private int code;
    private String msg;

    ProductEnums(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
