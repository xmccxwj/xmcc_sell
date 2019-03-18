package com.xmcc.wx_sell.repository;

import com.xmcc.wx_sell.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
}
