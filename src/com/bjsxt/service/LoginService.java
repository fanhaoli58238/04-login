package com.bjsxt.service;

import com.bjsxt.pojo.User;

public interface LoginService {
    //校验用户登录信息
    User checkLoginService(String uname,String pwd);

    //void checkLoginService(String uid);
    //校验用户cookie信息
    User checkUidService(String uid);
}
