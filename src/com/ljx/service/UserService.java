package com.ljx.service;

import com.ljx.dao.UserDao;
import com.ljx.domain.User;
import com.ljx.exception.UserException;

public class UserService {
    UserDao userDao = new UserDao();

    //注册
    public void regist(User user){
        /*
        调用Dao层的add方法进行注册
         */
        userDao.add(user);
    }

    //登录
    public User login(String username,String password) throws UserException {
        /*
        1.判断用户名是否存在，调用Dao层的findByUsername方法
        2.存在，findByUsername返回了user对象，不存在，抛异常
        3.user的password和传递过来的参数password进行比较，密码是否正确，不正确，抛异常
         */
        User user = userDao.findByUsername(username);
        if (user==null)
            throw new UserException("用户名不存在！");
        if (!(user.getPassword().equals(password))){
            throw new UserException("密码错误！");
        }
        return user;
    }
}
