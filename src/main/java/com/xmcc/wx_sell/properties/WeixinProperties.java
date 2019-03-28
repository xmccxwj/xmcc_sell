package com.xmcc.wx_sell.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wechat")//配置文件获取前缀为wechat的
@Data
public class WeixinProperties {

    private String appid;
    private String secret;
    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 商户证书路径
     */
    private String keyPath;
    /**
     * 微信支付异步通知
     */
    private String notifyUrl;
}
