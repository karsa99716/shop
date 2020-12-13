package com.gjc.test;

import com.gjc.dao.OrderDao;

public class TestORM {
    public static void main(String[] args) throws ClassNotFoundException,IllegalAccessException,IllegalAccessException{
        OrderDao orderDao = new OrderDao();
//        System.out.println(orderDao.findAll(1));
        String orderid = "7bcd31fd59f74e81a3581bf68e934e36";
        System.out.println(orderDao.findById("33baf1dac80a447f93e35bbff08f5ea9",1));
    }
}
