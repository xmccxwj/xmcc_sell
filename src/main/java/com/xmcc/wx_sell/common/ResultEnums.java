package com.xmcc.wx_sell.common;

        import lombok.Getter;

@Getter//这里是公共的定义 只需要get方法就可以了
public enum  ResultEnums {
    SUCCESS(0,"成功"),
    FAIL(1,"失败"),
    PRODUCT_UP(0,"正常"),
    PRODUCT_DOWN(1,"商品下架");
    private int code;
    private String msg;

    ResultEnums(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
