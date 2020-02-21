package com.mystudy.spring.form;

/*
* 身份验证
*
* */

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserAuthenticationform {

    private String id_card;

    private String true_name;

    private MultipartFile o_picture;

    private MultipartFile r_picture;
}
