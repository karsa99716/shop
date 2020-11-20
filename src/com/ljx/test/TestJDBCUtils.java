package com.ljx.test;

import com.ljx.utils.JDBCUtils;

public class TestJDBCUtils {
    public static void main(String[] args){
//        //增加操作
//        String sql = "insert into category value(null,?,?)";
//        Object []params = {
//                "上衣",2
//        };
//        Number i = (Number)JDBCUtils.insert(sql,params);
//        System.out.println(i);
//        //修改操作
//        String sql = "update category set name=?,sort=? where categoryid=?";
//        Object []params = {
//                "短裤",1,5
//        };
//        JDBCUtils.update(sql,params);
//        //删除操作
//        String sql = "delete from category where categoryid=?";
//        Object []params = {
//
//        };
//        JDBCUtils.update(sql,params);
        //查找操作
        String sql = "select * from category";
        System.out.println(JDBCUtils.select(sql));
    }
}
