package com.mystudy.spring.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mystudy.spring.domain.Book;
import com.mystudy.spring.enums.ResponseEnum;
import com.mystudy.spring.form.ReleaseAddForm;
import com.mystudy.spring.repository.CartRepository;
import com.mystudy.spring.repository.ProductsRepository;
import com.mystudy.spring.repository.UserRepository;
import com.mystudy.spring.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class ReleaseBookService {

    private final static String RELEASE_REDIS_KEY_TEMPLATE = "release_%d";

    private final static String RELEASE_REDIS_KEY_ALL = "all";

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ProductsRepository productsRepository;

    @Autowired
    public PictureService pictureService;

    private JSONObject object = new JSONObject();

    public ResponseVo list(Integer uid){
        /*
        * 未实名
        * */
        if(userRepository.findOne(uid).getRole() != 1){
            return ResponseVo.error(ResponseEnum.RELEASE_ROLE_ERROR);
        }



        return ResponseVo.success();
    }

    @Transactional
    public ResponseVo add(Integer uid, ReleaseAddForm releaseAddForm){

        /*
         * 未实名
         * */
        if(userRepository.findOne(uid).getRole() != 1){
            return ResponseVo.error(ResponseEnum.RELEASE_ROLE_ERROR);
        }

        String picture_name = pictureService.savePicture(releaseAddForm.getCover());
        if(picture_name == null){
            return ResponseVo.error(ResponseEnum.RELEASE_PICTURE_ERROR);
        }

        Book book = new Book(releaseAddForm.getCategoryId(),
                releaseAddForm.getName(),
                picture_name,
                releaseAddForm.getPrice(),
                releaseAddForm.getIntro(),
                releaseAddForm.getAuther(),
                releaseAddForm.getPress(),
                releaseAddForm.getPubdate(),
                releaseAddForm.getStock(),
                releaseAddForm.getStatus(),
                releaseAddForm.getSpecial(),
                releaseAddForm.getNews(),
                releaseAddForm.getSale());

        book = productsRepository.save(book);

        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey  = String.format(RELEASE_REDIS_KEY_TEMPLATE, uid);

        opsForHash.put(redisKey, String.valueOf(RELEASE_REDIS_KEY_ALL), JSON.toJSONString(book.getBookId()));

        return ResponseVo.success();
    }
}
