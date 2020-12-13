package com.gjc.service;

import com.gjc.dao.ActivateDao;
import com.gjc.dao.UserDao;
import com.gjc.domain.Activate;
import com.gjc.domain.User;
import com.gjc.exception.UserException;

import java.util.Date;

public class UserService {
    private UserDao userDao = new UserDao();
    private ActivateDao activateDao = new ActivateDao();

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

    //将验证码写入数据库
    public void addActivate(Activate activate){
        activateDao.add(activate);
    }

    public void activate(String code) throws UserException {
        /*
        1.查看验证码是否正确，不正确的话，抛出异常，正确的话，获取vipid
        2.根据vipid查询是否激活，如果已经激活，抛出异常
        3.没有激活过，激活
         */
        Activate activate = activateDao.findByCode(code);
        if (activate == null){
            throw new UserException("验证码错误");
        }
        if (activate.getExpiredate().getTime() < new Date().getTime()){
            throw new UserException("验证码过期");
        }
        int vipid = activate.getVipid();
        if (userDao.findStatus(vipid)){
            throw new UserException("不能重复激活");
        }
        //激活
        userDao.updateStatus(vipid);
    }

    //判断用户名是否被占用
    public boolean validateUsername(String username){
        /*
        1.调用findByUsername方法
        2.根据返回User，判断User对象是否为空，没有被占用，返回true
        3.不为空，返回false
         */
        User user = userDao.findByUsername(username);
        if (user==null){
            return true;
        }
        return false;
    }

    //更新最近一次登录时间
    public void updateLastLoginTime(User user){
        user.setLastlogintime(new Date());
        //调用Dao曾来更新lastlogintime
        userDao.updateLastLoginTime(user.getLastlogintime(),user.getVipid());
    }
}
