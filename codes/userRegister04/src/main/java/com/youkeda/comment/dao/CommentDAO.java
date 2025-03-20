package com.youkeda.comment.dao;

import com.youkeda.comment.dataobject.CommentDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDAO {

    int batchAdd(@Param("list") List<CommentDO> userDOs);

    List<CommentDO> findAll();

    int insert(CommentDO commentDO);

    int update(CommentDO userDO);

    int delete(@Param("id") long id);

    List<CommentDO> findByRefId(@Param("refId") String refId);

    List<CommentDO> findByUserIds(@Param("userIds") List<Long> ids);
}
