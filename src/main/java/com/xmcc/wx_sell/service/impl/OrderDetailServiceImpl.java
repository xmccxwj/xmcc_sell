package com.xmcc.wx_sell.service.impl;

import com.xmcc.wx_sell.common.ResultEnums;
import com.xmcc.wx_sell.common.ResultResponse;
import com.xmcc.wx_sell.dao.AbstractBatchDao;
import com.xmcc.wx_sell.entity.OrderDetail;
import com.xmcc.wx_sell.entity.OrderMaster;
import com.xmcc.wx_sell.repository.OrderDetailRepository;

import com.xmcc.wx_sell.service.OrderDetailService;
import com.xmcc.wx_sell.service.OrderMasterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderDetailServiceImpl extends AbstractBatchDao<OrderDetail> implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterService orderMasterService;
    /**
     * 因为我们这儿的数据量很少 直接插入就可以了
     * 如果以后遇到大数据量的情况 ,效率很低下，需要用Spring batch
     * Spring batch实现数据库大数据量读写  大家先了解这个 这儿暂时不花时间去学习了
     *
     * 但是也不能直接用jpa的save方法 效率实在太低了
     *
     * 可以使用EntityManager的persist 完成小数据量的批量插入
     * @param orderDetailList
     */
    @Override
    @Transactional //增删改触发事务
    public void  batchInsert(List<OrderDetail> orderDetailList){
        super.batchInsert(orderDetailList);
    }

    @Override
    public ResultResponse queryByOrderIdWithOrderMaster(String openid, String orderId) {
        //这儿不用判断 因为在orderMasterService中判断了
        ResultResponse<OrderMaster> orderMasterResult = orderMasterService.findByOrderIdAndOpenId(orderId, openid);
        OrderMaster orderMaster = orderMasterResult.getData();
        if(orderMasterResult.getCode()==ResultEnums.FAIL.getCode()||orderMaster==null){
            return orderMasterResult;
        }
        ResultResponse<List<OrderDetail>> finalOrderDetailList = findOrderDetailListByOrderId(orderId);
        orderMaster.setOrderDetailList(finalOrderDetailList.getData());
        return orderMasterResult;
    }
    public ResultResponse< List<OrderDetail>> findOrderDetailListByOrderId(String orderId){
        //这儿应该提成一个业务方法的 ,这儿就不提了
        List<OrderDetail>  orderDetailList= orderDetailRepository.findByOrderId(orderId);
        //设置商品图片小图
        if(!CollectionUtils.isEmpty(orderDetailList)) {
            orderDetailList = orderDetailList.stream().map(orderDetail -> {
                orderDetail.setProductImg(orderDetail.getProductIcon());
                return orderDetail;
            }).collect(Collectors.toList());
        }
        return ResultResponse.success(orderDetailList);
    }
}
