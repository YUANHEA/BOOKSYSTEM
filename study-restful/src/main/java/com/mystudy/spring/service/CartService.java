package com.mystudy.spring.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mystudy.spring.domain.Book;
import com.mystudy.spring.domain.Cart;
import com.mystudy.spring.enums.BookStatusEnum;
import com.mystudy.spring.enums.ResponseEnum;
import com.mystudy.spring.form.CartAddForm;
import com.mystudy.spring.form.CartUpdateForm;
import com.mystudy.spring.repository.CartRepository;
import com.mystudy.spring.domain.CartBookVo;
import com.mystudy.spring.vo.CartVo;
import com.mystudy.spring.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    private final static String CART_REDIS_KEY_TEMPLATE = "cart_%d";


    @Autowired
    private CartRepository cartRepository;

    @Autowired
    public StringRedisTemplate stringRedisTemplate;


    private JSONObject object = new JSONObject();


    public ResponseVo<CartVo> list(Integer uid){
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        boolean selectAll = true;
        Integer cartTotalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        CartVo cartVo = new CartVo();
        List<CartBookVo> cartBookVoList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer bookId = Integer.valueOf(entry.getKey());
//            JSONObject obj = JSON.parseObject(entry.getValue());
            Cart cart = JSON.parseObject(entry.getValue(),Cart.class);

            System.out.println("list_cart:"+cart);

            Book book = cartRepository.findOne(bookId);
            if(book != null){
                CartBookVo cartBookVo = new CartBookVo(bookId,
                        cart.getQuantity(),
                        book.getName(),
                        book.getCover(),
                        book.getPrice(),
                        book.getIntro(),
                        book.getAuther(),
                        book.getPress(),
                        book.getPubdate(),
                        book.getStock(),
                        book.getSpecial(),
                        book.getNews(),
                        book.getSale(),
                        book.getStatus(),
                        book.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        book.getStock(),
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

        Book book = cartRepository.findOne(form.getBookId());

        if (book == null) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }

        //商品是否正常在售
        if (!book.getStatus().equals(BookStatusEnum.ON_SALE.getCode())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        //商品库存是否充足
        if (book.getStock() <= 0) {
            return ResponseVo.error(ResponseEnum.PROODUCT_STOCK_ERROR);
        }

        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        Cart cart;
        String value = opsForHash.get(redisKey, String.valueOf(book.getBookId()));
        if (StringUtils.isEmpty(value)) {
            //没有该商品, 新增
            cart = new Cart(book.getBookId(), quantity, form.getSelected());
        }else {
            //已经有了，数量+1
//            object = JSON.parseObject(value);
//            cart = (Cart)object.get(Cart.class);
            cart = JSON.parseObject(value,Cart.class);

            cart.setQuantity(cart.getQuantity() + quantity);
        }

        opsForHash.put(redisKey,
                String.valueOf(book.getBookId()),
                object.toJSONString(cart));
        return list(uid);
    }

    public ResponseVo<CartVo> update(Integer uid, Integer bookId, CartUpdateForm form){
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        String value = opsForHash.get(redisKey, String.valueOf(bookId));
        if (StringUtils.isEmpty(value)) {
            //没有该商品, 报错
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }

        //已经有了，修改内容
        Cart cart = JSON.parseObject(value,Cart.class);
        if (form.getQuantity() != null
                && form.getQuantity() >= 0) {
            cart.setQuantity(form.getQuantity());
        }
        if (form.getSelected() != null) {
            cart.setBookSelected(form.getSelected());
        }
        opsForHash.put(redisKey, String.valueOf(bookId), JSON.toJSONString(cart));
        return list(uid);
    }

    public ResponseVo<CartVo> delete(Integer uid, Integer bookId) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        String value = opsForHash.get(redisKey, String.valueOf(bookId));
        if (StringUtils.isEmpty(value)) {
            //没有该商品, 报错
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }

        opsForHash.delete(redisKey, String.valueOf(bookId));
        return list(uid);
    }

    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        for (Cart cart : listForCart(uid)) {
            cart.setBookSelected(true);
            opsForHash.put(redisKey,
                    String.valueOf(cart.getBookId()),
                    JSON.toJSONString(cart));
        }

        return list(uid);
    }

    public ResponseVo<CartVo> unSelectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        for (Cart cart : listForCart(uid)) {
            cart.setBookSelected(false);
            opsForHash.put(redisKey,
                    String.valueOf(cart.getBookId()),
                    JSON.toJSONString(cart));
        }

        return list(uid);
    }

    public ResponseVo<Integer> sum(Integer uid) {
        Integer sum = listForCart(uid).stream()
                .map(Cart::getQuantity)
                .reduce(0, Integer::sum);
        return ResponseVo.success(sum);
    }

    public List<Cart> listForCart(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        List<Cart> cartList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            cartList.add(JSON.parseObject(entry.getValue(), Cart.class));
        }

        return cartList;
    }
}
