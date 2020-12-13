package com.gjc.dao;

import com.gjc.domain.Activate;
import com.gjc.utils.JDBCUtils;

import java.sql.Timestamp;

public class ActivateDao {
    //增加一条记录
    public void add(Activate activate){
        String sql = "insert into activate value(null,?,?,?)";
        Object params[] = {
                activate.getCode(),new Timestamp(activate.getExpiredate().getTime()),
                activate.getVipid(),
        };
        JDBCUtils.insert(sql,params);
    }

    //根据code来读取记录
    public Activate findByCode(String code){
        String sql = "select * from activate where code = ?";
        return JDBCUtils.selectToBean(Activate.class,sql,code);
    }
}
