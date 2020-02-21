package com.mystudy.spring.api;


import com.mystudy.spring.ApiConst.ApiConst;
import com.mystudy.spring.domain.User;
import com.mystudy.spring.enums.ResponseEnum;
import com.mystudy.spring.exception.myResult;
import com.mystudy.spring.form.UserAuthenticationform;
import com.mystudy.spring.form.UserLoginForm;
import com.mystudy.spring.service.UserService;
import com.mystudy.spring.vo.ResponseVo;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 用户控制
 * 门户_用户接口
 * **/
@RestController
//@RequestMapping("/user")
public class UserController
{
    @Autowired
    private UserService userService;

    @ApiOperation(value="用户登录", notes="用户登录")
    @PostMapping(value = "/user/login")
    public ResponseVo userLogin(@RequestBody UserLoginForm userLoginform, HttpServletRequest request){
        User user = (User)userService.login(userLoginform);
        System.out.println(userLoginform.toString());
        if(user!=null){
            HttpSession session = request.getSession();
            user.setPassword("null");
            session.setAttribute(ApiConst.USER_DATA,user);
            System.out.println(session);
            return  ResponseVo.success(user);
        }else{
            return ResponseVo.error(ResponseEnum.PASSWORD_ERROR);
        }
    }

    @ApiOperation(value="用户注册", notes="用户注册")
    @PostMapping(value = "/user/register")
    public ResponseVo userRegister(@RequestBody User user){
        if(userService.addUser(user)!=null){
            return  ResponseVo.success(0,"注册成功");
        }else{
            return ResponseVo.error(1,"用户名已存在");
        }
    }

    @ApiOperation(value="获取登录用户信息", notes="获取登录用户信息")
    @GetMapping(value = "/user")
    public ResponseVo userInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return ResponseVo.success(session.getAttribute(ApiConst.USER_DATA));

    }
    @ApiOperation(value="退出登录", notes="退出登录")
    @PostMapping(value = "/user/logout")
    public ResponseVo logout(HttpServletRequest request){
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("userdata");
        }catch (Exception e){
            return ResponseVo.error(1,"服务器异常");
        }finally {
            return ResponseVo.error(0,"退出成功");
        }
    }


    @ApiOperation(value="身份验证")
    @PostMapping(value = "/user/auth")
    public ResponseVo authentication(@RequestBody UserAuthenticationform form,
                                 HttpSession session) {
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
        return userService.authentication(user.getId(),form);
    }


}
