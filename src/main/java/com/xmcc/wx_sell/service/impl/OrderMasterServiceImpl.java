package com.xmcc.wx_sell.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xmcc.wx_sell.common.*;
import com.xmcc.wx_sell.dto.OrderDetailDto;
import com.xmcc.wx_sell.dto.OrderMasterDto;
import com.xmcc.wx_sell.entity.OrderDetail;
import com.xmcc.wx_sell.entity.OrderMaster;
import com.xmcc.wx_sell.entity.ProductInfo;
import com.xmcc.wx_sell.exception.CustomException;
import com.xmcc.wx_sell.repository.OrderMasterRepository;
import com.xmcc.wx_sell.service.OrderDetailService;
import com.xmcc.wx_sell.service.OrderMasterService;
import com.xmcc.wx_sell.service.ProductInfoService;
import com.xmcc.wx_sell.util.BigDecimalUtil;
import com.xmcc.wx_sell.util.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailService orderDetailService;


    /**
     * 1.根据购物车(订单项) 传来的商品id 插叙对应的商品 取得价格等相关信息 如果没查到 订单生成失败
     * 2.比较库存 ，库存不足 订单生成失败
     * 3.生成订单项OrderDetail信息
     * 4.减少商品库存
     * 5.算出总价格 ，组装订单信息 插入数据库得到订单号
     * 6.批量插入订单项
     *
     * 注意:1.生成订单就会减少库存 加入购物车不会  所有的网站基本都是这么设计的
     *      2.商品价格以生成订单时候为准，后面商品价格改变不影响已经生成的订单
     *
     */
    @Override
    @Transactional//增删改触发事务
    public ResultResponse insertOrder(OrderMasterDto orderMasterDto) {
        //前面已经进行了参数校验 这儿不需要了  取出订单项即可
        List<OrderDetailDto> items = orderMasterDto.getItems();
        //创建订单detail 集合 将符合的放入其中 待会批量插入
        List<OrderDetail> orderDetailList = Lists.newArrayList();
        //创建订单总金额为0  涉及到钱的都用 高精度计算
        BigDecimal totalPrice = new BigDecimal("0");

        for (OrderDetailDto item : items) {
            ResultResponse<ProductInfo> resultResponse = productInfoService.queryById(item.getProductId());
            //说明该商品未查询到 生成订单失败，因为这儿涉及到事务 需要抛出异常 事务机制是遇到异常才会回滚
            if(resultResponse.getCode()== ResultEnums.FAIL.getCode()){
                throw new CustomException(resultResponse.getMsg());
            }
            //获得查询的商品
            ProductInfo productInfo = resultResponse.getData();
            //说明该商品 库存不足 订单生成失败 直接抛出异常 事务才会回滚
            if(productInfo.getProductStock()<item.getProductQuantity()){
                throw new CustomException(ProductEnums.PRODUCT_NOT_ENOUGH.getMsg());
            }
            //将前台传入的订单项DTO与数据库查询到的 商品数据组装成OrderDetail 放入集合中  @builder
            OrderDetail orderDetail = OrderDetail.builder().detailId(IDUtils.createIdbyUUID()).productIcon(productInfo.getProductIcon())
                    .productId(item.getProductId()).productName(productInfo.getProductName())
                    .productPrice(productInfo.getProductPrice()).productQuantity(item.getProductQuantity())
                    .build();
            orderDetailList.add(orderDetail);
            //减少商品库存
            productInfo.setProductStock(productInfo.getProductStock()-item.getProductQuantity());
            productInfoService.updateProduct(productInfo);
            //计算价格
            totalPrice = BigDecimalUtil.add(totalPrice,BigDecimalUtil.multi(productInfo.getProductPrice(),item.getProductQuantity()));
        }
        //生成订单id
       String orderId = IDUtils.createIdbyUUID();
        //构建订单信息  日期等都用默认的即可
        OrderMaster orderMaster = OrderMaster.builder().buyerAddress(orderMasterDto.getAddress()).buyerName(orderMasterDto.getName())
                .buyerOpenid(orderMasterDto.getOpenid()).orderStatus(OrderEnum.NEW.getCode())
                .payStatus(PayEnum.WAIT.getCode()).buyerPhone(orderMasterDto.getPhone())
                .orderId(orderId).orderAmount(totalPrice).build();
        //将生成的订单id，设置到订单项中
        List<OrderDetail> detailList = orderDetailList.stream().map(orderDetail -> {
            orderDetail.setOrderId(orderId);
            return orderDetail;
        }).collect(Collectors.toList());
        //插入订单项
        orderDetailService.batchInsert(detailList);
        //插入订单
        orderMasterRepository.save(orderMaster);
        HashMap<String, String> map = Maps.newHashMap();
        //按照前台要求的数据结构传入
        map.put("orderId",orderId);
        return ResultResponse.success(map);
    }
}
