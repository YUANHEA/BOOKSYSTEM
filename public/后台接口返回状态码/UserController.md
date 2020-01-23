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





