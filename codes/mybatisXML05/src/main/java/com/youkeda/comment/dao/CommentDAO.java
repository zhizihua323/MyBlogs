package com.youkeda.comment.dao;

import com.youkeda.comment.dataobject.CommentDO;
import com.youkeda.comment.dataobject.UserDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentDAO {

//    @Select("SELECT id,ref_id as refId,user_id as userId,content,parent_id as parentId,gmt_created as gmtCreated,gmt_modified as gmtModified FROM comment")
    List<CommentDO> findAll();

    int insert(CommentDO commentDO);

    int update(CommentDO userDO);

    int delete(@Param("id") long id);

//    @Select("select id,ref_id as refId,user_id as userId,content,parent_id as parentId,gmt_created as gmtCreated,gmt_modified as gmtModified from comment where ref_id=#{refId}")
    List<CommentDO> findByRefId(@Param("refId") String refId);

}
