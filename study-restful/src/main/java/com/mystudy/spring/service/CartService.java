package com.mystudy.spring.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mystudy.spring.domain.Book;
import com.mystudy.spring.domain.Cart;
import com.mystudy.spring.enums.BookStatusEnum;
import com.mystudy.spring.enums.ResponseEnum;
import com.mystudy.spring.form.CartAddForm;
import com.mystudy.spring.repository.CartRepository;
import com.mystudy.spring.domain.CartBookVo;
import com.mystudy.spring.vo.CartVo;
import com.mystudy.spring.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    private final static String CART_REDIS_KEY_TEMPLATE = "cart_%d";


    @Autowired
    private CartRepository cartRepository;

//    @Autowired
    private StringRedisTemplate redisTemplate;


    private JSONObject object = new JSONObject();


    public ResponseVo<CartVo> list(Integer uid){
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        boolean selectAll = true;
        Integer cartTotalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        CartVo cartVo = new CartVo();
        List<CartBookVo> cartBookVoList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer bookId = Integer.valueOf(entry.getKey());
            JSONObject obj = JSON.parseObject(entry.getValue());
            Cart cart = (Cart)obj.get(Cart.class);

            Book book =(Book) cartRepository.findByBookId(bookId);
            if(book != null){
                CartBookVo cartBookVo = new CartBookVo(bookId,
                        cart.getQuantity(),
                        book.getBookName(),
                        book.getBookSubtitle(),
                        book.getBookMainImage(),
                        book.getBookPrice(),
                        book.getBookStatus(),
                        book.getBookPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        book.getBookStock(),
                        cart.getBookSelected()
                );
                cartBookVoList.add(cartBookVo);

                if(!cart.getBookSelected()){
                    selectAll = false;
                }

                //计算总价(只计算选中的)
                if (cart.getBookSelected()) {
                    cartTotalPrice = cartTotalPrice.add(cartBookVo.getBookTotalPrice());
                }
            }

            cartTotalQuantity += cart.getQuantity();
        }

        //有一个没有选中，就不叫全选
        cartVo.setSelectedAll(selectAll);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartBookVoList(cartBookVoList);

        return ResponseVo.success(cartVo);
    }

    public ResponseVo<CartVo> add(Integer uid, CartAddForm form){
        Integer quantity = 1;

        Book book = (Book)cartRepository.findByBookId(form.getBookId());

        if (book == null) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }

        //商品是否正常在售
        if (!book.getBookStatus().equals(BookStatusEnum.ON_SALE.getCode())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        //商品库存是否充足
        if (book.getBookStock() <= 0) {
            return ResponseVo.error(ResponseEnum.PROODUCT_STOCK_ERROR);
        }

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        Cart cart;
        String value = opsForHash.get(redisKey, String.valueOf(book.getBookId()));
        if (StringUtils.isEmpty(value)) {
            //没有该商品, 新增
            cart = new Cart(book.getBookId(), quantity, form.getSelected());
        }else {
            //已经有了，数量+1
            object = JSON.parseObject(value);
            cart = (Cart)object.get(Cart.class);
            cart.setQuantity(cart.getQuantity() + quantity);
        }

        opsForHash.put(redisKey,
                String.valueOf(book.getBookId()),
                object.toJSONString(cart));
        return list(uid);
    }


}
