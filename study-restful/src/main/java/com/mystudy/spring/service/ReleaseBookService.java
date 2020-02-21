package com.mystudy.spring.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mystudy.spring.domain.Book;
import com.mystudy.spring.domain.ReleaseBook;
import com.mystudy.spring.enums.ResponseEnum;
import com.mystudy.spring.form.ReleaseAddForm;

import com.mystudy.spring.form.ReleaseModifyForm;
import com.mystudy.spring.repository.ProductsRepository;
import com.mystudy.spring.repository.ReleaseBookRepository;
import com.mystudy.spring.repository.UserRepository;
import com.mystudy.spring.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReleaseBookService {


    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public ProductsRepository productsRepository;

    @Autowired
    public PictureService pictureService;

    @Autowired
    public ReleaseBookRepository releaseBookRepository;

    private JSONObject object = new JSONObject();

    public ResponseVo list(Integer uid , Integer pageNum, Integer pageSize){
        /*
        * 未实名
        * */
        if(userRepository.findOne(uid).getRole() != 1){
            return ResponseVo.error(ResponseEnum.RELEASE_ROLE_ERROR);
        }

        List<ReleaseBook> releaseBookList = releaseBookRepository.findByUid(uid);
        Set<Integer> bidSet = releaseBookList.stream()
                .map(ReleaseBook::getBid)
                .collect(Collectors.toSet());

        Pageable pageable = new PageRequest(pageNum, pageSize);
        Iterable<Book> bookIterable = productsRepository.findByBookIdInOrderByCreateTimeDesc(bidSet, pageable);

        return ResponseVo.success(bookIterable);
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

        ReleaseBook releaseBook = releaseBookRepository.findByUidAndBid(uid ,book.getBookId());

        if(releaseBook == null){
            ReleaseBook releaseBook1 = new ReleaseBook();
            releaseBook1.setUid(uid);
            releaseBook1.setBid(book.getBookId());
            releaseBookRepository.save(releaseBook1);
        }

        return ResponseVo.success(book);
    }

    @Transactional
    public ResponseVo delete(Integer uid, Integer bid){
        ReleaseBook releaseBook = releaseBookRepository.findByUidAndBid(uid, bid);
        if(releaseBook == null){
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }
        releaseBookRepository.delete(releaseBook.getId());
        productsRepository.delete(releaseBook.getBid());
        return ResponseVo.success();
    }

    public ResponseVo getReleaseBookOne(Integer uid , Integer bid){
        ReleaseBook releaseBook = releaseBookRepository.findByUidAndBid(uid, bid);
        if(releaseBook == null){
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }
        return ResponseVo.success(productsRepository.findOne(bid));
    }

    public ResponseVo modify(Integer uid, ReleaseModifyForm releaseModifyForm){
        ReleaseBook releaseBook = releaseBookRepository.findByUidAndBid(uid, releaseModifyForm.getBid());
        if(releaseBook == null){
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }

        String picture_name = pictureService.savePicture(releaseModifyForm.getCover());
        Book book = new Book(releaseModifyForm.getBid(),
                releaseModifyForm.getCategoryId(),
                releaseModifyForm.getName(),
                picture_name,
                releaseModifyForm.getPrice(),
                releaseModifyForm.getIntro(),
                releaseModifyForm.getAuther(),
                releaseModifyForm.getPress(),
                releaseModifyForm.getPubdate(),
                releaseModifyForm.getStock(),
                releaseModifyForm.getStatus(),
                releaseModifyForm.getSpecial(),
                releaseModifyForm.getNews(),
                releaseModifyForm.getSale());

        Book newbook = productsRepository.save(book);
        return ResponseVo.success(newbook);
    }


}
