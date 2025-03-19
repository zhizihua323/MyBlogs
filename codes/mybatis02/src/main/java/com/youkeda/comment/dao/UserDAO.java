package com.youkeda.comment.dao;

import com.youkeda.comment.dataobject.UserDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDAO {
    @Select("SELECT id,user_name as userName,pwd,nick_name as nickName,avatar,gmt_created as gmtCreated,gmt_modified as gmtModified FROM user")
    List<UserDO> findAll();

    @Insert("INSERT INTO user (user_name, pwd, nick_name,avatar,gmt_created,gmt_modified) "
            + "VALUES(#{userName}, #{pwd}, #{nickName}, #{avatar},now(),now())")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insert(UserDO userDO);

    @Update("update user set nick_name=#{nickName},gmt_modified=now() where id=#{id}")
    int update(UserDO userDO);

    @Delete("delete from user where id=#{id}")
    int delete(@Param("id") long id);

    @Select("select id,user_name as userName,pwd,nick_name as nickName,avatar,gmt_created as gmtCreated,gmt_modified as gmtModified  from user  where user_name=#{userName} limit 1")
    UserDO findByUserName(@Param("userName") String name);

}
