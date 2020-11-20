package com.ljx.dao;

import com.ljx.domain.User;
import com.ljx.utils.JDBCUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class UserDao {
    //添加一条记录
    public void add(User user){
        String sql = "insert into users values(null,?,?,?,?,?,?,?,?,?)";
        Object params[]={
                user.getUsername(),user.getPassword(),user.getSex(),user.getEmail(),
            user.getPhoto(),user.getScore(),user.getQusetion(),user.getAnswer(),user.getLastlogintime()
        };

        int id = ((Number)JDBCUtils.insert(sql,params)).intValue();
        user.setVipid(id);
    }
    //查找用户名为username的记录
    public User findByUsername(String username){
        String sql = "select * from users where username = ?";
        List<Map<String,Object>> list = JDBCUtils.select(sql,username);
        User user = null;
        if (list.size()>0){
            Map<String,Object> map = list.get(0);
            user = new User();
            user.setUsername(username);
            user.setVipid((Integer)map.get("vipid"));
            user.setQusetion((String)map.get("question"));
            user.setAnswer((String)map.get("answer"));
            user.setEmail((String)map.get("email"));
            user.setLastlogintime((Date) map.get("lastlogintime"));
            user.setPassword((String)map.get("password"));
            user.setPhoto((String)map.get("photo"));
            user.setScore((Long) map.get("score"));
            user.setSex((String)map.get("sex"));
        }
        return user;
    }
}
