有道云笔记 http://note.youdao.com/noteshare?id=c6e5d6b6fc9848d40a62dd2d9e214279

usercontrol api状态码
用户登录 /user/login
成功  
{
  "code": 0,
  "msg": "登录成功"
}
失败
{
  "code": 1,
  "msg": “用户名或密码错误”"
}

用户注册 /user/register
成功  
{
  "code": 0,
  "msg": "注册成功"
}
失败
{
  "code": 10,
  "msg": “用户名已存在"
}

获取登录用户信息 /user
成功  
{
  "code": 0,
  "msg": "Success"
}
失败
{
  "code": 1,
  "msg": “用户未登录"
}

获取登录用户信息 /user/logout
成功  
{
  "code": 0,
  "msg": "退出成功"
}
失败
{
  "code": 1,
  "msg": “服务器异常"
}
{
  "code": 10,
  "msg": “用户未登录"
}

ERROR(-1, "服务端错误"),

    SUCCESS(0, "成功"),

    PASSWORD_ERROR(1,"密码错误"),

    USERNAME_EXIST(2, "用户名已存在"),

    PARAM_ERROR(3, "参数错误"),

    EMAIL_EXIST(4, "邮箱已存在"),

    NEED_LOGIN(10, "用户未登录, 请先登录"),

    USERNAME_OR_PASSWORD_ERROR(11, "用户名或密码错误"),

    PRODUCT_OFF_SALE_OR_DELETE(12, "商品下架或删除"),

    PRODUCT_NOT_EXIST(13, "商品不存在"),

    PROODUCT_STOCK_ERROR(14, "库存不正确"),

    CART_PRODUCT_NOT_EXIST(15, "购物车里无此商品"),

    DELETE_SHIPPING_FAIL(16, "删除收货地址失败"),

    SHIPPING_NOT_EXIST(17, "收货地址不存在"),

    CART_SELECTED_IS_EMPTY(18, "请选择商品后下单"),

    ORDER_NOT_EXIST(19, "订单不存在"),

    ORDER_STATUS_ERROR(20, "订单状态有误"),
    ;



