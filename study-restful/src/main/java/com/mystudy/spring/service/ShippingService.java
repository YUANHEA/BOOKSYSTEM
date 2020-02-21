package com.mystudy.spring.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mystudy.spring.domain.Shipping;
import com.mystudy.spring.enums.ResponseEnum;
import com.mystudy.spring.form.ShippingForm;
import com.mystudy.spring.repository.ShippingRepository;
import com.mystudy.spring.vo.ResponseVo;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShippingService {
    @Autowired
    private ShippingRepository shippingRepository;

    public ResponseVo<Map<String, Integer>> add(Integer uid, ShippingForm form) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(form, shipping);
        shipping.setUserId(uid);
        Shipping shipping1 = shippingRepository.save(shipping);
        if (!shipping1.equals(shipping)) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("shippingId", shipping.getId());

        return ResponseVo.success(map);
    }

    public ResponseVo delete(Integer uid, Integer shippingId) {
        int row= shippingRepository.deleteByIdAndUserId(shippingId,uid );
        if (row == 0) {
            return ResponseVo.error(ResponseEnum.DELETE_SHIPPING_FAIL);
        }
        return ResponseVo.success();
    }

    public ResponseVo update(Integer uid, Integer shippingId, ShippingForm form) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(form, shipping);
        shipping.setUserId(uid);
        shipping.setId(shippingId);
        Shipping shipping1 = shippingRepository.findOne(shippingId);
        if (shipping1 == null) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        shippingRepository.saveAndFlush(shipping);
        return ResponseVo.success();
    }

    public ResponseVo list(Integer uid, Integer pageNum, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNum, pageSize);
        Iterable<Shipping> shippings = shippingRepository.findByUserId(uid,pageable);
        return ResponseVo.success(shippings);
    }

    public ResponseVo getOneShipping(Integer id,Integer uid){
        return ResponseVo.success(shippingRepository.findByIdAndUserId(id,uid));
    }
}
