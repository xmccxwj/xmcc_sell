package com.xmcc.wx_sell.repository;

import com.xmcc.wx_sell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
    Page<OrderMaster> findByBuyerOpenid(String openid, Pageable pageable);

    OrderMaster findByOrderIdAndBuyerOpenid(String orderId,String openid);
}
