package com.mfangsoft.zhuangjialong.common.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysConstant {
	
	
	public static String SENSITIVE_WORDS="SENSITIVE_WORDS";//敏感词库
	
    /*
     * 活动时间
     */
	public static String NATIONAL_DAY_START="2016-10-1 00:00:00";//国庆活动开始时间
	public static String NATIONAL_DAY_END="2016-10-10 23:59:59";//国庆活动结束时间
	
    public static String REG_PRE_HUNDRED="reg_pre_hundred";//2016-11-4活动redis键名
	
 
	public final static String PLAT_PHONE="400-051-2788";//平台电话
	
	
	public static int MINUTE_PER_DAY = 1440; // 一天有多少分钟
	public static int HOUT_PER_DAY = 24; // 一天有多少小时
	public static int SECOND_PER_MINUTE = 60; // 一分钟有多少秒
	public static int SECOND_PER_HOUR = 3600; // 一个小时有多少秒
	public static int SECOND_PER_DAY = 86400; // 一天有多少秒

	public static int MILLIS_PER_MINUTE = 60000; // 一分钟有多少毫秒
	public static int MILLIS_PER_HOUT = 3600000; // 一个小时有多少毫秒
	public static int MILLIS_PER_DAY = 86400000; // 一天有多少毫秒

	public static int Construct_Point = 50; // 施工加分
	public static int Appointment_Point = 50; // 预约加分
	public static int Expand_Point = 50; // 推广加分
	public static int Order_Point = 10; // 订单积分 10元加1分
	public static int Share_Point = 5; // 分享加分
	public static int Order_Pingjia_Point = 5; // 订单评价加分
	public static int Qustion_Point = 5; // 反馈问题加分

	public static int Expand_Point_Id = 1; // 推广加分
	public static int Appointment_Point_Id = 2; // 预约加分
	public static int Share_Point_Id = 3; // 分享加分
	public static int Order_Point_Id = 4; // 订单积分 10元加1分
	public static int Construct_Point_Id = 5; // 施工加分
	public static int Order_Pingjia_Point_Id = 6; // 订单评价加分
	public static int Qustion_Point_Id = 7; // 反馈问题加分
	
	public static int Day_Order_Point = 7; // 分享加分
	
	public static int Pingjia_Char_Count = 7; // 分享加分
	
	public static String Expand_Name = "邀请好友";
	public static String Qustion_Name = "问题反馈";
	
	public static double Seller_rate = 0.06D; // 卖家分成

	public static boolean debug = false; //是否测试服
	
	public static final String ip = "139.224.29.231";
	
	public static final String orderurl = "http://"+ ip +":8080/shop-web/app/getOrderCode";

	public static final String SUCCESS_CODE = "0";

	public static final String SUCCESS_MSG = "成功";
	

	public static final String FAILURE_CODE = "1";

	public static final String FAILURE_MSG = "失败";
	
	public final static String POINT_NOT_ENOUGH_CODE = "3";
	
	public final static String POINT_NOT_ENOUGH_MSG = "积分不足";
	
	public final static String CTCC = "CTCC";//电信编号
	public final static String CMCC = "CMCC";//移动编号
	public final static String CUCC = "CUCC";//联通编号
	

	public static final String KAPTCHA_SESSION_KEY = "vcode";

	public static final String FILE_PATH = "file.path";

	public static final String FILE_URL = "file.url";
	
	public static final String DEBUG = "debug";

	public static final String APP_CUSTOMER_INFO = "app_customer_info";

	public static final String USER_INFO = "user_info";

	public static final String USER_TOKEN = "token";

	public static final String CUSTOMER_VCODE = "VCODE_";// vcode_123

	public static final String SESSION_USERNAME = "SESSION_NAME";

	public static final String WEBSOCKET_USERNAME = "WEBSOCKET_USERNAME";

	public static final String CUSTOMER_ID = "CUSTOMER_ID";

	public static final String WEBSOCKET_CUSTOMERID = "WEBSOCKET_CUSTOMERID";

	public static final String CART_PRODUCT_INFO = "原价oldPrice";

	public static final String PromotionQuartz = "promotionQuartz";

	/**
	 * 壁纸单位
	 */
	public static final String WALLPAPER_UNIT = "unit";
	/**
	 * 制作工艺
	 */
	public static final String WALLPAPER_CRAFT = "craft";

	/**
	 * 验证码前缀
	 */
	public static final String V_CODE_MESSAGE = "您好，您的验证码为{0},请在10分钟内使用。";
    
	/**
	 * 短信签名
	 */
	public static final String ZJL_MESSAGE_SUF="【装甲龙】";
	
	/**
	 * 未登录
	 */
	public static final String not_login = "未登录";

	/**
	 * 验证邮件标题
	 */
	public static String MAIL_SUBJECT1 = "你好，这是一封来自于魔力铁盒的验证邮件";

	/**
	 * 添加订单失败
	 */
	public static final String ORDER_ADD_FAIL = "获取订单号失败";
	
	/**
	 * 添加订单失败
	 */
	public static final String ORDER_VERIFY_FAIL = "订单产品库存不足";
	
	/**
	 * 查询订单失败
	 */
	public static final String ORDER_SELECT_FAIL = "查询订单失败";

	/**
	 * 添加订单失败
	 */
	public static final String ORDER_UPDATE_FAIL = "修改订单失败";

	/**
	 * 请求订单支付失败
	 */
	public static final String ORDER_PAY_FAIL = "支付订单失败";

	/**
	 * 没有可以使用的优惠券
	 */
	public static final String NO_COUPONS = "没有可以使用的优惠券";

	/**
	 * 没有可以使用的红包
	 */
	public static final String NO_REDBAGS = "没有可以使用的红包";

	/**
	 * 预约时间提示信息
	 */
	public static final String APPOINTMENT_NOTE = "距离预约服务时间还有%s天";

	/**
	 * 预约时间提示信息
	 */
	public static final String APPOINTMENT_NOTE_EXP = "预约服务时间已过期";

	/**
	 * 定金红包
	 */
	public static final String SHOP_REDBAG = "定金红包";

	public static final String REDBAG_DES = "（秒杀产品不适用；红包不可拆分、叠加使用）";

	public static final String MONEY_NOT_ENOUGH = "余额不足";

	public static final String APPLY_NOT_DEAL = "尚有未处理的提现申请，无法再次提交";
	
	public static final String MODIFY_PSW_1 = "新密码不可与旧密码相同";
	
	public static final String MODIFY_PSW = "输入的密码与原密码不符";

	
	/**
	 * 壁纸适用空间
	 */
	public static final String WALLPAPER_SPACE = "space";

	/**
	 * 壁纸风格
	 */
	public static final String WALLPAPER_STYLE = "style";

	/**
	 * 壁纸产地
	 */
	public static final String WALLPAPER_MADEIN = "madein";
	/**
	 * solr产品地址
	 */
//		public static final String product_core = "http://127.0.0.1:8983/solr/product_core";
	public static final String product_core = "http://kaolaj.com:8983/solr/product_core";
	
	public static final Map<String, Object> entmap = new HashMap<>();

	public static final Map<String, Object> brandmap = new HashMap<>();

	public static final String image_bath_path = "http://www.kaolaj.com/file/";

	static {

		entmap.put("1", "47");
		entmap.put("2", "56");
		entmap.put("3", "55");
		entmap.put("4", "57");
		entmap.put("5", "58");
		entmap.put("6", "59");
		entmap.put("7", "60");
		entmap.put("8", "68");
		entmap.put("9", "61");

		brandmap.put("1", "41");
		brandmap.put("2", "43");
		brandmap.put("3", "42");
		brandmap.put("4", "44");
		brandmap.put("5", "45");
		brandmap.put("6", "62");
		brandmap.put("7", "63");
		brandmap.put("8", "54");
		brandmap.put("9", "64");

	}

	public static final String WEIXINSHARETICKET = "weixinShareTicket";
	public static final String WEIXINSHAREACCESSTOKEN = "weixinShareAccessToken";

	public static List<String> getIpAdress() {
		List<String> ipList = new ArrayList<>();
		Enumeration allNetInterfaces;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();

			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						ipList.add(ip.getHostAddress());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ipList;
	}
	
	
	
	
	public final static Long ENTERPRISE_ROLE=71L;
	
	
	public final static Long PARTNER_ROLE=  72L;
	
	
    public final static  Long BRAND_ROLE=73L;
    
    
    public final static Long  SHOP_ROLE=74L;
    
    public final static Long DEV_ROLE=70L;
    
    public final static Integer seller_type_offline = 0;
    public final static Integer seller_type_online = 1;
    
    //坐标已被占用
    public final static String LBS_OCCUPIED_CODE = "2";
    public final static String LBS_OCCUPIED_MESSAGE = "该坐标已被占用";
    
    //广告版本
    public final static String ADVERTISEMENT_CODE="ADVERTISEMENT_CODE";
    
    //向卖家推送预约指定消息
    public final static String APPOINTMENT_SERVICE_TITLE="预约信息";
    public final static String APPOINTMENT_SERVICE_START_CONTENT="您有一条新的预约客户信息！请您及时联系预约客户并为此客户进行服务。";
    public final static String APPOINTMENT_SERVICE_DONE_CONTENT="恭喜您完成了{0}用户的预约服务。";
    public final static String APPOINTMENT_SERVICE_DONE_CONTENT_CLIENT="您所提交的{0}用户的预约服务已完成。";
}
