package com.xmcc.wx_sell.service;

import com.xmcc.wx_sell.common.ResultResponse;
import com.xmcc.wx_sell.dto.OrderMasterDto;
import com.xmcc.wx_sell.entity.OrderMaster;

public interface OrderMasterService {
    ResultResponse insertOrder(OrderMasterDto orderMasterDto);

    ResultResponse queryList(String openid, Integer page, Integer size);

    ResultResponse<OrderMaster> findByOrderIdAndOpenId(String orderId, String openid);

    ResultResponse cancelOrder(String openid, String orderId);

    ResultResponse updateStatus(OrderMaster orderMaster,int status);


}
