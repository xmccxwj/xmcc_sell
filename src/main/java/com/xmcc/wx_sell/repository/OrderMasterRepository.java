package com.xmcc.wx_sell.repository;

import com.xmcc.wx_sell.entity.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
}
