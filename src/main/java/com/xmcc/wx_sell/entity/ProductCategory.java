package com.xmcc.wx_sell.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity//表示该类为实体类
@DynamicUpdate //如果时间有值 数据库就不会更新为当前时间，加这个注解动态更新时间
@Data //相当于set、get、toString方法
@AllArgsConstructor //全参构造
@NoArgsConstructor //无参构造
@Table(name="product_category") //表名
public class ProductCategory implements Serializable {

    /** 类目id. */
    @Id  //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //表示自增IDENTITY：mysql SEQUENCE:oracle
    private Integer categoryId;

    /** 类目名字. */
    private String categoryName;

    /** 类目编号. */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

}
