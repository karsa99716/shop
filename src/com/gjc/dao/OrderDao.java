package com.gjc.dao;

import com.gjc.domain.*;
import com.gjc.utils.BeanUtils;
import com.gjc.utils.JDBCUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDao {


    //添加订单
    public void addOrder(Order order){
        String sql="insert into orders values(?,?,?,?,?,?,?,?,?,?)";
        java.sql.Timestamp timestamp = new java.sql.Timestamp(order.getOrdertime().getTime());
        Object params[]={
                order.getOrderid(),timestamp,order.getStatuss(),
                order.getAddress().getAddressname(),order.getAddress().getPostcode(),
                order.getAddress().getReceiver(),order.getAddress().getPhone(),
                order.getTotalprice(),order.getContent(),order.getUser().getVipid()
        };

        JDBCUtils.insert(sql,params);

    }
    //添加订单条目
    public void addOrderItem(List<OrderItem> orderItemList){
        String sql = "insert into orderitem values(null,?,?,?,?,?,?,?)";
        Object params[][]=new Object[orderItemList.size()][];//创建二维数组，行数是要插入的条数

        for(int i=0;i<orderItemList.size();i++){
            //取出每一个条目，对应的一个一维数组
            OrderItem item = orderItemList.get(i);
            params[i] =new Object[]{
                    item.getBuycount(),item.getTotal(),item.getProduct().getProductid(),
                    item.getProduct().getName(),item.getProduct().getPrice(),
                    item.getProduct().getPhoto(),item.getOrder().getOrderid()
            };
        }
        List<Number> keys = JDBCUtils.insertbatch(sql,params);
        for(int i=0;i<keys.size();i++){
            OrderItem item =orderItemList.get(i);
            item.setId(keys.get(i).intValue());
        }
    }

    //通过orderid获取一个Order对象
    public static Order findById(String orderid,int vipid){
        String sql = "select * from orders where orderid = ? and vipid = ?";
        Map<String,Object> map = JDBCUtils.select(sql,orderid,vipid).get(0);//获取了order的对象map
        Order order =null;
        try {
            order = BeanUtils.toBean(map,Order.class);
            User user = BeanUtils.toBean(map,User.class);
            Address address = BeanUtils.toBean(map, Address.class);
            order.setUser(user);
            order.setAddress(address);

            //获取订单条目列表

            sql = "select * from orderitem where orderid = ?";
            List<Map<String,Object>> list = JDBCUtils.select(sql,orderid);
            List<OrderItem> orderItemList = new ArrayList<OrderItem>();


            for(int i=0;i<list.size();i++){
                Map<String,Object> map2 = list.get(i);//取的第i条记录
                OrderItem orderitem = BeanUtils.toBean(map2,OrderItem.class);

                //获取商品对象
                Product product = BeanUtils.toBean(map2, Product.class);
                orderitem.setProduct(product);
                orderitem.setOrder(order);
                orderItemList.add(orderitem);
            }
            order.setOrderItemList(orderItemList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    //读取登录用户所有的订单
    public  List<Order> findAll(int vipid){
        String sql="select * from orders where vipid = ?";
        List<Map<String,Object>> listorders = JDBCUtils.select(sql,vipid);
        List<Order> orders = new ArrayList<Order>();
        for(int k=0;k<listorders.size();k++){
            Map<String,Object>  map = listorders.get(k);//取得第k条记录
            //将第k条记录转换成一个order对象
            Order order =null;
            try {
                order = BeanUtils.toBean(map,Order.class);
                User user = BeanUtils.toBean(map,User.class);
                Address address = BeanUtils.toBean(map, Address.class);
                order.setUser(user);
                order.setAddress(address);

                //获取订单条目列表

                sql = "select * from orderitem where orderid = ?";
                List<Map<String,Object>> list = JDBCUtils.select(sql,order.getOrderid());
                List<OrderItem> orderitemList = new ArrayList<OrderItem>();


                for(int i=0;i<list.size();i++){
                    Map<String,Object> map2 = list.get(i);//取的第i条记录
                    OrderItem orderitem = BeanUtils.toBean(map2,OrderItem.class);

                    //获取商品对象
                    Product product = BeanUtils.toBean(map2, Product.class);
                    orderitem.setProduct(product);
                    orderitem.setOrder(order);
                    orderitemList.add(orderitem);
                }
                order.setOrderItemList(orderitemList);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            orders.add(order);
        }

        return orders;
    }
}
