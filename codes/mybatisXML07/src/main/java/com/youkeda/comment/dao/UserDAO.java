package com.youkeda.comment.dao;

import com.youkeda.comment.dataobject.UserDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDAO {

    int add(UserDO userDO);

    int update(UserDO userDO);

    int delete(@Param("id") long id);

    List<UserDO> findAll();

    UserDO findByUserName(@Param("userName") String name);

    List<UserDO> query(@Param("keyWord")String keyWord);
}
