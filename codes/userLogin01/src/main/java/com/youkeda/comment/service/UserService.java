package com.youkeda.comment.service;

import com.youkeda.comment.model.Result;
import com.youkeda.comment.model.User;

public interface UserService {

    /**
     * 注册用户
     * @param userName
     * @param pwd
     * @return
     */
    public Result<User> register(String userName, String pwd);

    /**
     * 登录用户
     * @param userName
     * @param pwd
     * @return
     */
    public Result<User> login(String userName,String pwd);
}
