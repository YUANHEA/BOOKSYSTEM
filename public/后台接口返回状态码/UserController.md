�е��Ʊʼ� http://note.youdao.com/noteshare?id=c6e5d6b6fc9848d40a62dd2d9e214279

usercontrol api״̬��
�û���¼ /user/login
�ɹ�  
{
  "code": 0,
  "msg": "��¼�ɹ�"
}
ʧ��
{
  "code": 1,
  "msg": ���û������������"
}

�û�ע�� /user/register
�ɹ�  
{
  "code": 0,
  "msg": "ע��ɹ�"
}
ʧ��
{
  "code": 10,
  "msg": ���û����Ѵ���"
}

��ȡ��¼�û���Ϣ /user
�ɹ�  
{
  "code": 0,
  "msg": "Success"
}
ʧ��
{
  "code": 1,
  "msg": ���û�δ��¼"
}

��ȡ��¼�û���Ϣ /user/logout
�ɹ�  
{
  "code": 0,
  "msg": "�˳��ɹ�"
}
ʧ��
{
  "code": 1,
  "msg": ���������쳣"
}
{
  "code": 10,
  "msg": ���û�δ��¼"
}

ERROR(-1, "����˴���"),

    SUCCESS(0, "�ɹ�"),

    PASSWORD_ERROR(1,"�������"),

    USERNAME_EXIST(2, "�û����Ѵ���"),

    PARAM_ERROR(3, "��������"),

    EMAIL_EXIST(4, "�����Ѵ���"),

    NEED_LOGIN(10, "�û�δ��¼, ���ȵ�¼"),

    USERNAME_OR_PASSWORD_ERROR(11, "�û������������"),

    PRODUCT_OFF_SALE_OR_DELETE(12, "��Ʒ�¼ܻ�ɾ��"),

    PRODUCT_NOT_EXIST(13, "��Ʒ������"),

    PROODUCT_STOCK_ERROR(14, "��治��ȷ"),

    CART_PRODUCT_NOT_EXIST(15, "���ﳵ���޴���Ʒ"),

    DELETE_SHIPPING_FAIL(16, "ɾ���ջ���ַʧ��"),

    SHIPPING_NOT_EXIST(17, "�ջ���ַ������"),

    CART_SELECTED_IS_EMPTY(18, "��ѡ����Ʒ���µ�"),

    ORDER_NOT_EXIST(19, "����������"),

    ORDER_STATUS_ERROR(20, "����״̬����"),
    ;



