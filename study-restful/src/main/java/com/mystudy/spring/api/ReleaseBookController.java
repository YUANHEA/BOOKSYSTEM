package com.mystudy.spring.api;


import com.mystudy.spring.ApiConst.ApiConst;
import com.mystudy.spring.domain.User;
import com.mystudy.spring.form.ReleaseAddForm;
import com.mystudy.spring.form.ReleaseModifyForm;
import com.mystudy.spring.service.ReleaseBookService;
import com.mystudy.spring.vo.ResponseVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.ws.Action;

@RestController
public class ReleaseBookController {
    @Autowired
    private ReleaseBookService releaseBookService;

    @ApiOperation(value="添加商品", notes="添加商品")
    @PostMapping("/release/add")
    public ResponseVo add(@RequestBody ReleaseAddForm form,
            HttpSession session){
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
        return releaseBookService.add(user.getId(), form);
    }

    @ApiOperation(value="删除商品", notes="")
    @DeleteMapping("/release/delete/{bookId}")
    public ResponseVo delete(@PathVariable Integer bookId ,HttpSession session){
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
        return releaseBookService.delete(user.getId(),bookId);
    }

    @ApiOperation(value="", notes="")
    @GetMapping("/release/modify/{bookId}")
    public ResponseVo getOne(@PathVariable Integer bookId
            , HttpSession session){
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
        return releaseBookService.getReleaseBookOne(user.getId(), bookId);
    }

    @ApiOperation(value="修改商品", notes="")
    @PutMapping("/release/modify")
    public ResponseVo modify(@RequestBody ReleaseModifyForm form
            , HttpSession session){
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
        return releaseBookService.modify(user.getId(), form);
    }

    @ApiOperation(value="商品列表", notes="")
    @GetMapping("/release/list")
    public ResponseVo list(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                           @RequestParam(required = false, defaultValue = "30") Integer pageSize,
            HttpSession session){
        User user = (User) session.getAttribute(ApiConst.USER_DATA);
        System.out.println(pageNum + " " + pageSize);
        return releaseBookService.list(user.getId(), pageNum, pageSize);
    }


}
