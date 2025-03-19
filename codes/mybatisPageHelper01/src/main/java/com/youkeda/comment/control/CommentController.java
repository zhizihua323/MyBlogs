package com.youkeda.comment.control;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youkeda.comment.dao.CommentDAO;
import com.youkeda.comment.dataobject.CommentDO;
import com.youkeda.comment.dataobject.UserDO;
import com.youkeda.comment.model.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentDAO commentDAO;

    @GetMapping("/comments")
    @ResponseBody
    public Paging<CommentDO> getAll() {
        // 1、使用mybatis pagehelper分页组件
        // 1.1、设置当前页数为1，每页3条记录
        Page<CommentDO> page = PageHelper.startPage(1, 3).doSelectPage(() -> commentDAO.findAll());

//        return commentDAO.findAll();
        // 2、使用分页模型  修改返回类型为Paging<CommentDO>
        return new Paging<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), page.getResult());
    }

    @PostMapping("/comment")
    @ResponseBody
    public CommentDO save(@RequestBody CommentDO commentDO) {
        commentDAO.insert(commentDO);
        return commentDO;
    }

    @PostMapping("/comment/batchAdd")
    @ResponseBody
    public List<CommentDO> batchAdd(@RequestBody List<CommentDO> commentDOS) {
        commentDAO.batchAdd(commentDOS);
        return commentDOS;
    }

    @PostMapping("/comment/update")
    @ResponseBody
    public CommentDO update(@RequestBody CommentDO commentDO) {
        commentDAO.update(commentDO);
        return commentDO;
    }

    @GetMapping("/comment/del")
    @ResponseBody
    public boolean delete(@RequestParam("id") Long id) {
        return commentDAO.delete(id) > 0;
    }

    @GetMapping("/comment/findByRefId")
    @ResponseBody
    public List<CommentDO> findByRefId(@RequestParam("refId") String refId) {
        return commentDAO.findByRefId(refId);
    }

    @GetMapping("/comment/findByUserIds")
    @ResponseBody
    public List<CommentDO> findByUserIds(@RequestParam("userIds") List<Long> ids) {
        return commentDAO.findByUserIds(ids);
    }
}
