package com.example.dell.zxqy.notwork.utils;

public class Api {
    //登录
    public final static String Login_path="user/v1/login";
    //注册
    public final static String Sing_path="user/v1/register";
    //Banner
    public final static String Banner_path="commodity/v1/bannerShow";
    //首页
    public final static String Show_path="commodity/v1/commodityList";
    //圈子展示
    public final static String Circle_path="circle/v1/findCircleList?page=%d&&count=5";
    //搜索
    public final static String Search_path="commodity/v1/findCommodityByKeyword?keyword=%s&&page=%d&&count=5";
    //一级列表
    public final static String One_path="commodity/v1/findFirstCategory";
    //二级列表
    public final static String Two_path="commodity/v1/findSecondCategory?firstCategoryId=%s";
    //二级查找
    public final static String TwoSeache_path="commodity/v1/findCommodityByCategory?categoryId=%s&page=1&&count=5";
    //热销商品
    public final static String ShowAll="commodity/v1/findCommodityListByLabel?labelId=%d&page=%d&count=%d";
    //详情
    public final static String Detailspath="commodity/v1/findCommodityDetailsById?commodityId=%s";
    //点赞
    public  final static String GreatPath ="circle/verify/v1/addCircleGreat";
    //取消点赞
    public  final static String NoGreatPath ="circle/verify/v1/cancelCircleGreat";
    //加入购物车
    public  final static String AddShopping="order/verify/v1/syncShoppingCart";
    //查询购物车
    public  final static String SearchCarPath ="order/verify/v1/findShoppingCart";
    //查询用户信息
    public  final static String SearchMyPath ="user/verify/v1/getUserById";
    //查询收货地址
    public  final static String SearchAddessPath ="user/verify/v1/receiveAddressList";
    //新增收货地址
    public  final static String AddAddaessPath ="user/verify/v1/addReceiveAddress";
    //设置默认收货地址
    public  final static String MoPath ="user/verify/v1/setDefaultReceiveAddress";
    // 修改收货信息
    public  final static String UpateAddPath ="user/verify/v1/changeReceiveAddress";
    //修改昵称
    public  final static String UpateNamePath="user/verify/v1/modifyUserNick";
    //修改密码
    public  final static String UpatePassWordPath="user/verify/v1/modifyUserPwd";
    //我的圈子
    public  final static String MyCirlePath="circle/verify/v1/findMyCircleById?page=%d&&count=5";
    //我的足迹
    public  final static String MyFootPath="commodity/verify/v1/browseList?page=%d&&count=5";
    //查询钱包的接口
    public static final String SHOW_SELECT_MONEY_URL="user/verify/v1/findUserWallet?page=%d&count=10";
    //支付
    public  final static String PAY_SHOP="order/verify/v1/pay";
    //查询订单状态
    public  final static String INDENT_FIND="order/verify/v1/findOrderListByStatus?status=%d&page=%d&count=5";
    //创建订单
    public  final static String CreatFrom="order/verify/v1/createOrder";
    //确认收货
    public final static String OKSHOUPATH="order/verify/v1/confirmReceipt";
    //上传头像
    public final static String Head = "user/verify/v1/modifyHeadPic";
    //商品评论列表
    public final static String TalkPAth="commodity/v1/CommodityCommentList?page=%d&&commodityId=%s&&count=5";
    //删除订单
    public final static String DelectPath="order/verify/v1/deleteOrder?orderId=%s";
    //发布评论的接口
    public static final String ShowTalk="commodity/verify/v1/addCommodityComment";
    //发布圈子的接口
    public static final String ShowC="circle/verify/v1/releaseCircle";
}
