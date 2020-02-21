package com.mystudy.spring.service;

import com.mystudy.spring.domain.User;
import com.mystudy.spring.enums.UserRoleEnum;
import com.mystudy.spring.exception.NotFoundException;
import com.mystudy.spring.exception.myResult;
import com.mystudy.spring.form.UserAuthenticationform;
import com.mystudy.spring.form.UserLoginForm;
import com.mystudy.spring.repository.UserRepository;
import com.mystudy.spring.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mystudy.spring.util.Util.getNullPropertyNames;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PictureService pictureService;

    public Object login(UserLoginForm userLoginForm){
        String username = userLoginForm.getUsername();
        String password = userLoginForm.getPassword();
        return  userRepository.findByUsernameAndPassword(username,password);
    }

//    用户注册
    public Object addUser(User user)
    {
        if(userRepository.findByUsername(user.getUsername())==null){
            return userRepository.save(user);
        }else{
            return null;
        }
    }

    public Object findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> getUserList() {
        return userRepository.findAll();
}

    public User findUserByBookId(Integer bid){
        return userRepository.findUserByBookId(bid);
    }
//
//    public Object getUser(String id) throws NotFoundException
//    {
//        User currentInstance = userRepository.findOne(id);
//        if (currentInstance == null)
//        {
//            throw new NotFoundException("user " + id + " is not exist!", myResult.ErrorCode.USER_NOT_FOUND.getCode());
//        }
//        return userRepository.findOne(id);
//    }
//
//    public void deleteUser(String id)
//    {
//        userRepository.delete(id);
//    }
//
//    public User update(String id, User user)
//    {
//        User currentInstance = userRepository.findOne(id);
//
//        //支持部分更新
//        String[] nullPropertyNames = getNullPropertyNames(user);
//        BeanUtils.copyProperties(user, currentInstance, nullPropertyNames);
//
//        return userRepository.save(currentInstance);
//    }
    public ResponseVo authentication(int userId, UserAuthenticationform form){

        /*
        * 实名验证成功
        * */
        User user = userRepository.findOne(userId);
        user.setRole(UserRoleEnum.REAL_USER.getCode());
        user.setId_card(form.getId_card());
        user.setTrue_name(form.getTrue_name());
        userRepository.save(user);
        return ResponseVo.success();
    }


}
