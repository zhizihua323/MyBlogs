package com.youkeda.comment.control;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youkeda.comment.dao.CommentDAO;
import com.youkeda.comment.dataobject.CommentDO;
import com.youkeda.comment.model.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentDAO commentDAO;

    /*
    * required = false 表示可选参数
    * */
    @GetMapping("/comments")
    @ResponseBody
    public Paging<CommentDO> getAll(@RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize",required = false,defaultValue = "15")Integer pageSize) {

        // 设置当前页数为1，以及每页3条记录
        Page<CommentDO> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> commentDAO.findAll());

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
