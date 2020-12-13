package com.gjc.dao;

import com.gjc.domain.User;
import com.gjc.utils.JDBCUtils;

import java.util.Date;

public class UserDao {
    //添加一条记录
    public void add(User user){
        String sql = "insert into users values(null,?,?,?,?,?,?,?,?,?,?)";
        Object params[]={
                user.getUsername(),user.getPassword(),user.getSex(),user.getEmail(), user.getPhoto(),
                user.getScore(),user.getQusetion(),user.getAnswer(),user.getLastlogintime(),user.isStatus(),
        };

        int id = ((Number)JDBCUtils.insert(sql,params)).intValue();
        user.setVipid(id);
    }
    //查找用户名为username的记录
    public User findByUsername(String username){
        String sql = "select * from users where username = ?";
        User user = JDBCUtils.selectToBean(User.class,sql,username);
//        List<Map<String,Object>> list = JDBCUtils.select(sql,username);
//        User user = null;
//        if (list.size()>0){
//            Map<String,Object> map = list.get(0);
//            user = new User();
//            user.setUsername(username);
//            user.setVipid((Integer)map.get("vipid"));
//            user.setQusetion((String)map.get("question"));
//            user.setAnswer((String)map.get("answer"));
//            user.setEmail((String)map.get("email"));
//            user.setLastlogintime((Date) map.get("lastlogintime"));
//            user.setPassword((String)map.get("password"));
//            user.setPhoto((String)map.get("photo"));
//            user.setScore((Long) map.get("score"));
//            user.setSex((String)map.get("sex"));
//        }
        return user;
    }

    //根据vipid查询用户当前状态
    public boolean findStatus(int vipid){
        String sql = "select status from users where vipid = ?";
        return JDBCUtils.selectScalar(sql,vipid);
    }

    //更新用户状态为true
    public void updateStatus(int vipid){
        String sql = "update users set status = true where vipid = ?";
        JDBCUtils.update(sql,vipid);
    }

    //更新最近一次登录时间
    public void updateLastLoginTime(Date lastlogintime,int vipid) {
        String sql = "update users set lastlogintime = ? where vipid = ?";
        Object params[]={
                new java.sql.Timestamp(lastlogintime.getTime()),
                vipid
        };
        JDBCUtils.update(sql,params);
    }
}
