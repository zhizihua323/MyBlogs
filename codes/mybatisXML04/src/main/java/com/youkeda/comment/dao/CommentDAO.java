package com.youkeda.comment.dao;

import com.youkeda.comment.dataobject.CommentDO;
import com.youkeda.comment.dataobject.UserDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentDAO {

    @Select("SELECT id,ref_id as refId,user_id as userId,content,parent_id as parentId,gmt_created as gmtCreated,gmt_modified as gmtModified FROM comment")
    List<CommentDO> findAll();

    int insert(CommentDO commentDO);

//    @Update("update comment set content=#{content},gmt_modified=now() where id=#{id}")
    int update(CommentDO userDO);

//    @Delete("delete from comment where id=#{id}")
    int delete(@Param("id") long id);

    @Select("select id,ref_id as refId,user_id as userId,content,parent_id as parentId,gmt_created as gmtCreated,gmt_modified as gmtModified from comment where ref_id=#{refId}")
    List<CommentDO> findByRefId(@Param("refId") String refId);

}
