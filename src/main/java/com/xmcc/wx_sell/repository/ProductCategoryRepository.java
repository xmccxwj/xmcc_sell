package com.xmcc.wx_sell.repository;

import com.xmcc.wx_sell.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//第一个参数 是实体类名称  第二个参数是主键类型
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
    //根据类型列表查询 集合
    List<ProductCategory> findByCategoryTypeIn(List<Integer> typeList);
    //?1表示第一个参数 ?2表示第二个参数  nativeQuery表示用sql语句查  jpa默认是jpql  用sql语句必须用数据库的表名与字段名
    @Query(value = "select category_name from product_category where category_id=:ids and category_type=:type",nativeQuery = true)

    List<String> queryNameByIdAndType( @Param("ids") Integer id, Integer type);
}
