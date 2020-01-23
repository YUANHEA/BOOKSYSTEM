package com.mystudy.spring.api;

import com.fengwenyi.javalib.result.Result;
import com.mystudy.spring.domain.User;
import com.mystudy.spring.exception.myResult;
import com.mystudy.spring.form.UserLoginForm;
import com.mystudy.spring.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;


@RestController
//@RequestMapping("/user")
public class UserController
{
    @Autowired
    private UserService userService;

    @ApiOperation(value="用户登录", notes="用户登录")
    @PostMapping(value = "/user/login")
    public Result userLogin(@RequestBody UserLoginForm userLoginform,HttpServletRequest request){
        User user = (User)userService.login(userLoginform);
        System.out.println(userLoginform.toString());
        if(user!=null){
            HttpSession session = request.getSession();
            user.setPassword("null");
            session.setAttribute("userdata",user);
            System.out.println(session);
            return  Result.success(user);
        }else{
            return Result.error(1,"密码或用户名错误");
        }
    }

    @ApiOperation(value="用户注册", notes="用户注册")
    @PostMapping(value = "/user/register")
    public Result userRegister(@RequestBody User user){
        if(userService.addUser(user)!=null){
            return  Result.error(0,"注册成功");
        }else{
            return Result.error(1,"用户名已存在");
        }
    }

    @ApiOperation(value="获取登录用户信息", notes="获取登录用户信息")
    @GetMapping(value = "/user")
    public Result userInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Result.success(session.getAttribute("userdata"));

    }
    @ApiOperation(value="退出登录", notes="退出登录")
    @PostMapping(value = "/user/logout")
    public Result logout(HttpServletRequest request){
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("userdata");
        }catch (Exception e){
            return Result.error(1,"服务器异常");
        }finally {
            return Result.error(0,"退出成功");
        }
    }





//    @ApiOperation(value="获取用户列表", notes="获取用户列表")
//    @GetMapping(value = "/users")
//    public List<User> getUserList()
//    {
//        return userService.getUserList();
//    }
//
//    @ApiOperation(value="添加用户", notes="添加用户")
//    @PostMapping(value = "/users")
//    public Object addUser(@RequestBody User user){
//        return userService.addUser(user);
//    }
//
//    @ApiOperation(value="获取用户信息", notes="根据id获取用户信息")
//    @GetMapping(value = "/users/{id}")
//    public Object getUser(@PathVariable("id") String id) throws NotFoundException
//    {
//        return userService.getUser(id);
//    }
//
//    @ApiOperation(value="删除用户", notes="根据id删除用户")
//    @DeleteMapping(value = "/users/{id}")
//    public void deleteUser(@PathVariable("id") String id)
//    {
//        userService.deleteUser(id);
//    }
//
//    @ApiOperation(value="更新用户", notes="更新用户")
//    @PatchMapping(value = "/users/{id}")
//    public User updateUser(@PathVariable("id") String id, @RequestBody User user)
//    {
//        return userService.update(id, user);
//    }



    @ApiOperation(value="测试")
    @GetMapping(value = "/test")
    public myResult test()
    {
        return myResult.success(0,"校验成功");
    }

    @ApiOperation(value="测试")
    @GetMapping(value = "/test1")
    public Result test1()
    {

        return Result.success(userService.getUserList());
    }
}
