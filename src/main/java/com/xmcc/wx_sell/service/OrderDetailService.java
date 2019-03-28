package com.xmcc.wx_sell.service;

import com.xmcc.wx_sell.common.ResultResponse;
import com.xmcc.wx_sell.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    //批量插入
    void batchInsert(List<OrderDetail> orderDetailList);

    ResultResponse queryByOrderIdWithOrderMaster(String openid, String orderId);

    ResultResponse< List<OrderDetail>> findOrderDetailListByOrderId(String orderId);
}
