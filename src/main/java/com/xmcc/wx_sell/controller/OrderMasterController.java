package com.xmcc.wx_sell.controller;

import com.google.common.collect.Maps;
import com.xmcc.wx_sell.common.ResultResponse;
import com.xmcc.wx_sell.dto.OrderMasterDto;

import com.xmcc.wx_sell.service.OrderDetailService;
import com.xmcc.wx_sell.service.OrderMasterService;
import com.xmcc.wx_sell.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("buyer/order")
@Api(value = "订单相关接口",description = "完成订单的增删改查")
public class OrderMasterController {
   @Autowired
    private OrderMasterService orderMasterService;

   @Autowired
   private OrderDetailService orderDetailService;
    @PostMapping("create")
    @ApiOperation(value = "创建订单接口", httpMethod = "POST", response =ResultResponse.class)
    //@Valid配合刚才在DTO上的注解完成校验
    public ResultResponse create(@Valid @ApiParam(name="订单对象",value = "传入json格式",required = true)
                                             OrderMasterDto orderMasterDto, BindingResult bindingResult){
        Map<String,String> map = Maps.newHashMap();
        //判断是否有参数校验问题
        if(bindingResult.hasErrors()){
            List<String> errList = bindingResult.getFieldErrors().stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList());
            map.put("参数校验错误",JsonUtil.object2string(errList));
           //将参数校验的错误信息返回给前台
            return  ResultResponse.fail(map);
       }
        return orderMasterService.insertOrder(orderMasterDto);
    }


    @GetMapping("list")
    @ApiOperation(value = "订单查询接口", httpMethod = "GET", response =ResultResponse.class)
    public ResultResponse list(String openid,Integer page,Integer size){

        return orderMasterService.queryList(openid,page,size);
    }

    @GetMapping("detail")
    @ApiOperation(value = "根据订单与OPENID查询订单详情", httpMethod = "GET", response =ResultResponse.class)
    public ResultResponse detail(@RequestParam(value="openid",required = true) String openid,
                                 @RequestParam(value ="orderId",required = true) String orderId){
        return orderDetailService.queryByOrderIdWithOrderMaster(openid,orderId);
    }

    @PostMapping("cancel")
    @ApiOperation(value = "取消订单", httpMethod = "POST", response =ResultResponse.class)
    public ResultResponse cancel(@RequestParam(value="openid",required = true) String openid,
                                 @RequestParam(value ="orderId",required = true) String orderId){
        return orderMasterService.cancelOrder(openid,orderId);
    }


}
