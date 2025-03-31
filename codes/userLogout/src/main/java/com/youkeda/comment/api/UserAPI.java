package com.youkeda.comment.api;

import com.youkeda.comment.model.Result;
import com.youkeda.comment.model.User;
import com.youkeda.comment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author joe
 */
@Controller
public class UserAPI {

    @Autowired
    private UserService userService;

    /**
     * 用户注册服务
     * @param userName
     * @param pwd
     * @return
     */
    @PostMapping("/api/user/reg")
    @ResponseBody
    public Result<User> reg(@RequestParam("userName") String userName, @RequestParam("pwd") String pwd) {
        return userService.register(userName, pwd);
    }

    /**
     * 用户登录服务
     * @param userName
     * @param pwd
     * @param request
     * @return
     */
    @PostMapping("/api/user/login")
    @ResponseBody
    public Result<User> login(@RequestParam("userName") String userName, @RequestParam("pwd") String pwd, HttpServletRequest request) {
        Result<User> result = userService.login(userName, pwd);

        if (result.isSuccess()) {
            request.getSession().setAttribute("userId", result.getData().getId());
        }
        return result;
    }

    @GetMapping("/api/user/logout")
    @ResponseBody
    public Result logout(HttpServletRequest request){
        Result<Object> result = new Result<>();
        // 退出登录，删除Session中的userId
        try {
            request.getSession().removeAttribute("userId");
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
