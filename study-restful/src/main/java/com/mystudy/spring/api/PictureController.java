package com.mystudy.spring.api;

import com.fengwenyi.javalib.util.IdUtil;
import com.mystudy.spring.form.UserAuthenticationform;
import com.mystudy.spring.service.PictureService;
import com.mystudy.spring.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@Slf4j
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @GetMapping("/getImage/{name:.+}")
    public void getImage(HttpServletResponse response, @PathVariable("name") String name) throws IOException {
        response.setContentType("image/jpeg;charset=utf-8");
        response.setHeader("Content-Disposition", "inline; filename=" + name);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(pictureService.getPicture(name));
        outputStream.flush();
        outputStream.close();
    }

//    @PostMapping("/getImage/unloadImage")
//    public String uploadImage( UserAuthenticationform form)  {
//        MultipartFile image = form.getPicture();
//        System.out.println(form.getId_card() + " " + form.getTrue_name());
//        try {
//            String name = image.getOriginalFilename();
//
//            System.out.println(name);
//
//            InputStream inputStream = image.getInputStream();
//            Path directory = Paths.get(UPLOAD_PATH);
//            if(!Files.exists(directory)){
//                Files.createDirectories(directory);
//            }
//            long copy = Files.copy(inputStream, directory.resolve(name));
//
//            return "上传成功，大小："+ copy;
//
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return "上传失败";
//        }
//
//    }

}
