package com.mystudy.spring.api;


import com.mystudy.spring.vo.ResponseVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReleaseBookController {

    @ApiOperation(value="添加商品", notes="添加商品")
    @PostMapping("/release/add")
    public ResponseVo add(){
        return ResponseVo.success();
    }

    @ApiOperation(value="删除商品", notes="")
    @PostMapping("/release/delete")
    public ResponseVo delete(){
        return ResponseVo.success();
    }

    @ApiOperation(value="修改商品", notes="")
    @PostMapping("/release/modify")
    public ResponseVo modify(){
        return ResponseVo.success();
    }

    @ApiOperation(value="商品列表", notes="")
    @PostMapping("/release/list")
    public ResponseVo list(){
        return ResponseVo.success();
    }


}
