package com.xmcc.wx_sell.service.impl;

import com.xmcc.wx_sell.dao.AbstractBatchDao;
import com.xmcc.wx_sell.entity.OrderDetail;
import com.xmcc.wx_sell.service.OrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OrderDetailServiceImpl extends AbstractBatchDao<OrderDetail> implements OrderDetailService {

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
}
