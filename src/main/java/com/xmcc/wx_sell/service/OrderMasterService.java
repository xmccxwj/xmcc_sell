package com.xmcc.wx_sell.service;

import com.xmcc.wx_sell.common.ResultResponse;
import com.xmcc.wx_sell.dto.OrderMasterDto;

public interface OrderMasterService {
    ResultResponse insertOrder(OrderMasterDto orderMasterDto);
}
