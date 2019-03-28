package com.xmcc.wx_sell.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.xmcc.wx_sell.entity.OrderMaster;

/**
 * 支付service
 */
public interface PayService {

    //根据订单id查询订单
    OrderMaster findOrderById(String orderId);
    //创建预付单
    PayResponse create(OrderMaster orderMaster);
    //异步通知
    void weixin_notify(String notifyData);
    //微信退款
    RefundResponse refund(OrderMaster orderMaster);


}
