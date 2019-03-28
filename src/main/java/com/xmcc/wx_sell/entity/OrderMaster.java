package com.xmcc.wx_sell.entity;

import com.xmcc.wx_sell.common.OrderEnum;
import com.xmcc.wx_sell.common.PayEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
@Data
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderMaster implements Serializable {

    /** 订单id. */
    @Id
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus ;

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus ;

    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;

    //订单项列表
    @Transient //忽略与数据库对应
    private List<OrderDetail> orderDetailList;

}
