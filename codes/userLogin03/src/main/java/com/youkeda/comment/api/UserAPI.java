package com.youkeda.comment.api;

import com.youkeda.comment.model.Result;
import com.youkeda.comment.model.User;
import com.youkeda.comment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
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

    @PostMapping("/api/user/reg")
    @ResponseBody
    public Result<User> reg(@RequestParam("userName") String userName, @RequestParam("pwd") String pwd) {
        return userService.register(userName, pwd);
    }

    @PostMapping("/api/user/login")
    @ResponseBody
    public Result<User> login(HttpServletRequest request, @RequestParam("userName") String userName, @RequestParam("pwd") String pwd) {
        Result<User> result = userService.login(userName, pwd);
        //存入session
        if (result.isSuccess()){
            request.getSession().setAttribute("userId",result.getData().getId());
        }
        return result;
    }
}
