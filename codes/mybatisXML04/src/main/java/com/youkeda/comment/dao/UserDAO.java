package com.youkeda.comment.dao;

import com.youkeda.comment.dataobject.UserDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDAO {

    int add(UserDO userDO);

    int update(UserDO userDO);

    int delete(@Param("id") long id);

    @Select("SELECT id,user_name as userName,pwd,nick_name as nickName,avatar,gmt_created as gmtCreated,gmt_modified as gmtModified FROM user")
    List<UserDO> findAll();

    @Select("select * from user where user_name=#{userName}")
    UserDO findByUserName(@Param("userName") String name);

}
