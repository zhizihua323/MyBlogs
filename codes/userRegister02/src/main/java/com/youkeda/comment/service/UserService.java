package com.youkeda.comment.service;

import com.youkeda.comment.model.Result;
import com.youkeda.comment.model.User;

public interface UserService {

    /**
     * 创建用户
     * @param UserName
     * @param pwd
     * @return
     */
    public Result<User> register(String UserName,String pwd);
}
