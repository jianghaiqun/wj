package cn.com.sinosoft.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.OrderProductDao;
import cn.com.sinosoft.entity.OrderProduct;

@Repository
public class OrderProductDaoImpl extends BaseDaoImpl<OrderProduct, String> implements OrderProductDao {
}