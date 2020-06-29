package com.mfangsoft.zhuangjialong.common.utils;

import java.util.ResourceBundle;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.WinphoneNotification;
public class JPushUtil {
	private static String APP_KEY;
	private static String MASTER_SECRET;
	private static String SELLER_APP_KEY;
	private static String SELLER_MASTER_SECRET;
	
    static {
    	ResourceBundle resource = ResourceBundle.getBundle("jpush");
    	APP_KEY = resource.getString("app.key");
    	MASTER_SECRET = resource.getString("master.secret");
    	SELLER_APP_KEY = resource.getString("seller.app.key");
    	SELLER_MASTER_SECRET = resource.getString("seller.master.secret");
    }

	public static PushPayload buildPushAllPayload(String alert) {

        return PushPayload.alertAll(alert);
        }
	public static PushPayload buildIOSPayload(String alias,String title, String alert) {
		IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody(title, alert).build();//创建一个IosAlert对象，可指定APNs的alert、title等字段
        return PushPayload.newBuilder()

                .setPlatform(Platform.ios_winphone())

                .setAudience(Audience.alias(alias.replace(".", "_")))

                .setNotification(Notification.newBuilder()

                .setAlert(iosAlert)
                .addPlatformNotification(IosNotification.newBuilder()//指定当前推送的iOS通知
//                		.setAlert(alert)//传一个IosAlert对象，指定apns title、title、subtitle等
                		//.setAlert("ios notification alert")//直接传alert

                		.incrBadge(1)//此项是指定此推送的badge自动加1

                		.setSound("sound.caf")//此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音

//                		.addExtra("iOS 的extras1", "JPush111")//此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）

                		.setContentAvailable(true)//此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification

                		.build())
                .build())
                .setOptions(Options.newBuilder()

                		.setApnsProduction(true)//此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义

                		.setSendno(1)//此字段是给开发者自己给推送编号，方便推送者分辨推送记录

                		.setTimeToLive(86400)//此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；

                		.build())

                 .build();

    }
	private static PushPayload buildAndroidWinphonePayload(String alias,

		    String alert,String title) {

		        return PushPayload.newBuilder()

		                .setPlatform(Platform.android())

		                .setAudience(Audience.alias(alias.replace(".", "_")))

		                .setNotification(Notification.newBuilder()

		                .setAlert(alert)

		                .addPlatformNotification(AndroidNotification.newBuilder()

		                .setTitle(title).build())

		                .build())

		                 .build();
		    }

	public static PushPayload buldPushObject_all_all_alias(String alias, String title, String content) {  
        return PushPayload  
                .newBuilder()  
                .setPlatform(Platform.all())  
                .setAudience(Audience.alias(alias.replace(".", "_")))  
                .setNotification(  
                        Notification  
                                .newBuilder()  
                                .addPlatformNotification(  
                                        IosNotification.newBuilder()  
                                                .setAlert(content)  
                                                .build())  
                                .addPlatformNotification(  
                                        AndroidNotification.newBuilder()  
                                                .setAlert(content)  
                                                .setTitle(title)  
                                                .build())  
                                .addPlatformNotification(  
                                        WinphoneNotification.newBuilder()  
                                                .setAlert(content)  
                                                .build())  
                                .build()).build();  
    }  
	
	public static PushPayload buildPushObject_all_all_alert() {

		IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody("title", "alert body").build();//创建一个IosAlert对象，可指定APNs的alert、title等字段

		return PushPayload.newBuilder()

		.setPlatform(Platform.all())//指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台

		.setAudience(Audience.all())//指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id

		.setNotification(Notification.newBuilder()//jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发

		.addPlatformNotification(AndroidNotification.newBuilder()//指定当前推送的android通知

		.setAlert("029792e98eb18c9152af7ffbafab36d8")

		.setTitle("android notification title")

		.addExtra("extras key", "extras value")//此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）

		.build())

		.addPlatformNotification(IosNotification.newBuilder()//指定当前推送的iOS通知
		.setAlert(iosAlert)//传一个IosAlert对象，指定apns title、title、subtitle等
		//.setAlert("ios notification alert")//直接传alert

		.incrBadge(1)//此项是指定此推送的badge自动加1

		.setSound("sound.caf")//此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音

		.addExtra("iOS 的extras1", "JPush111")//此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）

		.setContentAvailable(true)//此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification

		.build())

		.addPlatformNotification(WinphoneNotification.newBuilder()

		.setAlert("WinPhone notification alert")

		.setOpenPage("xxxx.cs")//指定点击打开的页面（类）,具体后缀忘记，错了请指正

		.addExtra("WinPhone extras key", "WinPhone extras value")

		.build())

		.build())

		.setMessage(Message.newBuilder()//Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的 [通知与自定义消息有什么区别？]了解通知和自定义消息的区别

		.setMsgContent("message content")

		.setTitle("message titile")

		.addExtra("message extras key", "message extras value")

		.build())

		.setOptions(Options.newBuilder()

		.setApnsProduction(true)//此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义

		.setSendno(1)//此字段是给开发者自己给推送编号，方便推送者分辨推送记录

		.setTimeToLive(86400)//此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；

		.build())

		.build();

		}
	
	public static void testSendPush() {

		JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, 2);
		PushPayload payload = buildPushObject_all_all_alert();
		try {

		PushResult result = jpushClient.sendPush(payload);


		} catch (APIConnectionException e) {


		} catch (APIRequestException e) {


		}

		}
	
	/**
	 * Android推送
	 * @param alias
	 * @param alert
	 * @param title
	 * @return
	 */
	public static PushResult sendPush(String alias,String title,String alert) {
		PushPayload ppla=buildAndroidWinphonePayload(alias, alert, title);
		try {
			JPushClient jPushClient=new JPushClient(MASTER_SECRET, APP_KEY);
			PushResult sendPush = jPushClient.sendPush(ppla);
			return sendPush;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return null;
		}
	}

	/**
	 * IOS推送
	 * @param alias
	 * @param alert
	 * @return
	 */
	public static PushResult sendPushIOS(String alias,String title, String alert) {
		PushPayload ppla=buildIOSPayload(alias,title, alert);
		try {
			JPushClient jPushClient=new JPushClient(MASTER_SECRET, APP_KEY);
			PushResult sendPush = jPushClient.sendPush(ppla);
			return sendPush;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return null;
		}
	}
	
	/**
	 * 全推送
	 * @param alias
	 * @param alert
	 * @return
	 */
	public static PushResult sendAllPush(String alias,String title, String content) {
		PushPayload ppla = buldPushObject_all_all_alias(alias, title, content);
		try {
			JPushClient jPushClient=new JPushClient(MASTER_SECRET, APP_KEY);
			PushResult sendPush = jPushClient.sendPush(ppla);
			return sendPush;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return null;
		}
	}
	/**
	 * 卖家端Android推送
	 * @param alias
	 * @param alert
	 * @param title
	 * @return
	 */
	public static PushResult sendSellerPush(String alias,String title,String alert) {
		PushPayload ppla=buildAndroidWinphonePayload(alias, alert, title);
		try {
			JPushClient jPushClient=new JPushClient(SELLER_MASTER_SECRET, SELLER_APP_KEY);
			PushResult sendPush = jPushClient.sendPush(ppla);
			return sendPush;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return null;
		}
	}
	/**
	 * 卖家端IOS推送
	 * @param alias
	 * @param alert
	 * @return
	 */
	public static PushResult sendSellerPushIOS(String alias,String title, String alert) {
		PushPayload ppla=buildIOSPayload(alias,title, alert);
		try {
			JPushClient jPushClient=new JPushClient(SELLER_MASTER_SECRET, SELLER_APP_KEY);
			PushResult sendPush = jPushClient.sendPush(ppla);
			return sendPush;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return null;
		}
	}
	/**
	 * 卖家端全推送
	 * @param alias
	 * @param alert
	 * @return
	 */
	public static PushResult sendSellerAllPush(String alias,String title, String content) {
		PushPayload ppla = buldPushObject_all_all_alias(alias, title, content);
		try {
			JPushClient jPushClient=new JPushClient(SELLER_MASTER_SECRET, SELLER_APP_KEY);
			PushResult sendPush = jPushClient.sendPush(ppla);
			return sendPush;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return null;
		}
	}
	
	/*
	 * 卖家端推送
	 */
	public static void sendSellerMessage(String platform, String alias,String title,String content){
		try{
		if(platform == null){
			JPushUtil.sendSellerAllPush(alias, title, content);
		}else if("android".equals(platform.toLowerCase())){
			JPushUtil.sendSellerPush(alias, title, content);
		}else if("ios".equals(platform.toLowerCase())){
			JPushUtil.sendSellerPushIOS(alias, title, content);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*
	 * 买家端推送
	 */
	public static void sendMessage(String platform, String alias,String title,String content){
		try{
			if(platform == null){
				JPushUtil.sendAllPush(alias, title, content);
			}else if("android".equals(platform.toLowerCase())){
				JPushUtil.sendPush(alias, title, content);
			}else if("ios".equals(platform.toLowerCase())){
				JPushUtil.sendPushIOS(alias, title, content);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		sendPush("0ed1058b51f5da3163f9a421ea80eb25", "test title","test content");
//		sendPush("1333B14918924FC084958372EC61F74B", "test content 2");
//		sendAllPush("0ed1058b51f5da3163f9a421ea80eb25", "test title", "test push all content");
//		System.out.println(MD5Util.MD5("111111"));
		sendMessage("ios", "916C9029D6A04AB9B2B294A4A4763A23", "这是title", "收到通知给青智打个电话好吗");
//		testSendPush();
	}
	
}
