package com.gjc.service;

import com.gjc.dao.OrderDao;
import com.gjc.domain.Order;
import com.gjc.utils.JDBCUtils;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private OrderDao orderDao = new OrderDao();

    //添加订单
    public void addOrder(Order order){
        try {
            //开启事务
            JDBCUtils.beginTranscation();
            orderDao.addOrder(order);//添加订单
            orderDao.addOrderItem(order.getOrderItemList());//添加订单条目列表
            //提交事务
            JDBCUtils.commitTranscation();
        }catch (Exception e){
            //回滚事务
            try {
                JDBCUtils.rollbackTranscation();
            } catch (SQLException e1) {
                throw new RuntimeException(e);
            }
        }
    }

    public Order findById(String orderid,int vipid) {
        return orderDao.findById(orderid,vipid);
    }

    public List<Order> findAll(int vipid) {
        return orderDao.findAll(vipid);
    }
}
