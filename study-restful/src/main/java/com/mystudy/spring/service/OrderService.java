package com.mystudy.spring.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mystudy.spring.domain.*;
import com.mystudy.spring.enums.BookStatusEnum;
import com.mystudy.spring.enums.OrderStatusEnum;
import com.mystudy.spring.enums.PaymentTypeEnum;
import com.mystudy.spring.enums.ResponseEnum;
import com.mystudy.spring.repository.CartRepository;
import com.mystudy.spring.repository.OrderItemRepository;
import com.mystudy.spring.repository.OrderRepository;
import com.mystudy.spring.repository.ShippingRepository;
import com.mystudy.spring.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.xml.ws.Action;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
        Shipping shipping = shippingRepository.findByIdAndUserId(shippingId,uid );
        //收货地址不存在
        if (shipping == null) {
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }

        //获取购物车，校验（是否有商品、库存）
        List<Cart> cartList = cartService.listForCart(uid).stream()
                .filter(Cart::getBookSelected)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cartList)) {
            return ResponseVo.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }

        //获取cartList里的bookIds
        Set<Integer> bookIdSet = cartList.stream()
                .map(Cart::getBookId)
                .collect(Collectors.toSet());
        List<Book> bookList = cartRepository.findByBookIdIn(bookIdSet);
        Map<Integer, Book> map  = bookList.stream()
                .collect(Collectors.toMap(Book::getBookId, book -> book));

        List<OrderItem> orderItemList = new ArrayList<>();
        Long orderNo = generateOrderNo();

        for (Cart cart : cartList) {
            //根据bookId查数据库
            Book book = map.get(cart.getBookId());
            //是否有商品
            if (book == null) {
                return ResponseVo.error(13,
                        "商品不存在. bookId = " + cart.getBookId());
            }
            //商品上下架状态
            if (!BookStatusEnum.ON_SALE.getCode().equals(book.getStatus())) {
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE.getCode(),
                        "商品不是在售状态. " + book.getName());
            }

            //库存是否充足
            if (book.getStock() < cart.getQuantity()) {
                return ResponseVo.error(ResponseEnum.PROODUCT_STOCK_ERROR.getCode(),
                        "库存不正确. " + book.getName());
            }

            OrderItem orderItem = buildOrderItem(uid, orderNo, cart.getQuantity(), book);
            orderItemList.add(orderItem);

            //减库存
            book.setStock(book.getStock() - cart.getQuantity());
            cartRepository.save(book);

        }

        //计算总价，只计算选中的商品
        //生成订单，入库：order和order_item，事务
        Order order = buildOrder(uid, orderNo, shippingId, orderItemList);

        Order rowForOrder = orderRepository.save(order);
        if (rowForOrder == null) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }



        List<OrderItem> rowForOrderItem = orderItemRepository.save(orderItemList);
        if (rowForOrderItem == null) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }


        //更新购物车（选中的商品）
        //Redis有事务(打包命令)，不能回滚
        for (Cart cart : cartList) {
            cartService.delete(uid, cart.getBookId());
        }

        //构造orderVo
        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
        return ResponseVo.success(orderVo);
    }

    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderRepository.findByUserId(uid);

        System.out.println(orderList);

        Set<Long> orderNoSet = orderList.stream()
                .map(Order::getOrderNo)
                .collect(Collectors.toSet());
        List<OrderItem> orderItemList = orderItemRepository.findByOrderNoIn(orderNoSet);


        Map<Long, List<OrderItem>> orderItemMap = orderItemList.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderNo));

        Set<Integer> shippingIdSet = orderList.stream()
                .map(Order::getShippingId)
                .collect(Collectors.toSet());
        List<Shipping> shippingList = shippingRepository.findByIdIn(shippingIdSet);
        Map<Integer, Shipping> shippingMap = shippingList.stream()
                .collect(Collectors.toMap(Shipping::getId, shipping -> shipping));

        List<OrderVo> orderVoList = new ArrayList<>();
        for (Order order : orderList) {
            OrderVo orderVo = buildOrderVo(order,
                    orderItemMap.get(order.getOrderNo()),
                    shippingMap.get(order.getShippingId()));
            orderVoList.add(orderVo);
        }
        PageInfo pageInfo = new PageInfo<>(orderList);
        pageInfo.setList(orderVoList);

        return ResponseVo.success(pageInfo);
    }

    public ResponseVo<OrderVo> detail(Integer uid, Long orderNo) {
        Order order = orderRepository.findByOrderNo(orderNo);
        if (order == null || !order.getUserId().equals(uid)) {
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        Set<Long> orderNoSet = new HashSet<>();
        orderNoSet.add(order.getOrderNo());
        List<OrderItem> orderItemList = orderItemRepository.findByOrderNoIn(orderNoSet);

        Shipping shipping = shippingRepository.findOne(order.getShippingId());

        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
        return ResponseVo.success(orderVo);
    }

    public ResponseVo cancel(Integer uid, Long orderNo) {

        Order order = orderRepository.findByOrderNo(orderNo);
        if (order == null || !order.getUserId().equals(uid)) {
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        //只有[未付款]订单可以取消，看自己公司业务
        if (!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())) {
            return ResponseVo.error(ResponseEnum.ORDER_STATUS_ERROR);
        }

        order.setStatus(OrderStatusEnum.CANCELED.getCode());
        order.setCloseTime(new Date());
        Order row = orderRepository.save(order);
        if (row == null) {
            return ResponseVo.error(1,"更新地址失败");
        }

        return ResponseVo.success();
    }

        /*
    * 订单号生成
    * */
    private Long generateOrderNo() {
        return System.currentTimeMillis() + new Random().nextInt(999);
    }

    private OrderItem buildOrderItem(Integer uid, Long orderNo, Integer quantity, Book book) {
        OrderItem item = new OrderItem();
        item.setUserId(uid);
        item.setOrderNo(orderNo);
        item.setBookId(book.getBookId());
        item.setBookName(book.getName());
        item.setBookImage(book.getCover());
        item.setCurrentUnitPrice(book.getPrice());
        item.setQuantity(quantity);
        item.setTotalPrice(book.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return item;
    }

    private OrderVo buildOrderVo(Order order, List<OrderItem> orderItemList, Shipping shipping) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);
        System.out.println(orderItemList);
        List<OrderItemVo> OrderItemVoList = orderItemList.stream().map(e -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(e, orderItemVo);
            return orderItemVo;
        }).collect(Collectors.toList());
        orderVo.setOrderItemVoList(OrderItemVoList);

        if (shipping != null) {
            orderVo.setShippingId(shipping.getId());
            orderVo.setShippingVo(shipping);
        }

        return orderVo;
    }

    private Order buildOrder(Integer uid,
                             Long orderNo,
                             Integer shippingId,
                             List<OrderItem> orderItemList
    ) {
        BigDecimal payment = orderItemList.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shippingId);
        order.setPayment(payment);
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());
        return order;
    }
}

