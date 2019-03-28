package com.xmcc.wx_sell.controller;


import com.lly835.bestpay.model.PayResponse;
import com.xmcc.wx_sell.entity.OrderMaster;
import com.xmcc.wx_sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("pay")
@Slf4j
public class PayController {
    @RequestMapping("notify")
    //获得微信传递的json字符串参数 具体可以查看官网，重要的几个（中文至少要能说的出）
    //out_trade_no:订单id
    //transaction_id:流水号
    //openid:付款人的openid
    //total_fee:订单金额
    public ModelAndView weixin_notify(@RequestBody String notifyData){
        log.info("微信支付,异步回调");
        //调用业务层来处理回调验证，修改订单
        payService.weixin_notify(notifyData);
        //返回到页面  页面的内容会被微信读取，告诉微信我们这边OK了 不然会一直发送异步回调
        return new ModelAndView("weixin/success");
    }
    @Autowired
    private PayService payService;

    @RequestMapping("create")
    /**
     * 根据API文档创建接口
     * orderId: 订单ID 这里只能传递一个ID 防止别人传入非法的金额
     * returnUrl: 回调地址
     */
    public ModelAndView create(@RequestParam("orderId")String orderId,
                               @RequestParam("returnUrl")String returnUrl,
                               Map map){
            //根据id查询订单
        OrderMaster orderMaster = payService.findOrderById(orderId);
        //根据订单创建支付
        PayResponse response = payService.create(orderMaster);
        //将参数设置到map中
        map.put("payResponse",response);
        map.put("returnUrl",returnUrl);
        return new ModelAndView("weixin/pay",map);
    }

}
