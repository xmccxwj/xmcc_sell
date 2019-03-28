package com.xmcc.wx_sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("weixin")
@Slf4j
public class WeiXinController {
    @RequestMapping("getCode")
    public void getCode(@RequestParam("code") String code){
        log.info("进入getCode回调方法");
        log.info("获得当前微信用户的授权码为:{}",code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxcec0b9e65c084712&secret=05a7e861c1985ced86af77fb8f7163bc&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject(url, String.class);

        System.out.println(forObject);

    }
}
