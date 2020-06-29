package com.mfangsoft.zhuangjialong.app.aa;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年11月21日
* 
*/

public class ImageUrlTest {
	//读取远程url图片,得到宽高
    public static int[] returnImgWH(Map<String, String> map ) {
    	String imgurl = map.get("imgurl");
        boolean b=false;
        StringBuffer stringBuffer = new StringBuffer();
        String[] split = imgurl.substring(imgurl.lastIndexOf("file"), imgurl.length()).split("/");
        stringBuffer.append("D:\\a");
        String path=null;
        for (int i = 0; i < split.length; i++) {
			if(i!= split.length-2){
				stringBuffer.append("\\").append(split[i]);
			}else {
				stringBuffer.append("\\").append(split[i]);
				path=stringBuffer.toString();
			}
		}
        if(path!=null){
        	File file = new File(path);  
            if(!file.exists()) {  
            	file.mkdirs();
            } 
        }
        try {
            //实例化url
            URL url = new URL(imgurl);
            //载入图片到输入流
          BufferedInputStream bis = new BufferedInputStream(url.openStream());
            //实例化存储字节数组
            byte[] bytes = new byte[1000];
            //设置写入路径以及图片名称
           // System.out.println(stringBuffer.toString());
            OutputStream bos = new FileOutputStream(new File(stringBuffer.toString()) );
            int len;
            while ((len = bis.read(bytes)) > 0) {
                bos.write(bytes, 0, len);
            }
            bis.close();
            bos.flush();
            bos.close();
            //关闭输出流
            b=true;
        } catch (Exception e) {
            //如果图片未找到
        	System.out.println(imgurl);
            b=false;
        }
        int[] a = new int[3];
        if(b){    //图片存在
            //得到文件
            java.io.File file = new java.io.File(stringBuffer.toString());
            BufferedImage bi = null;
            try {
                //读取图片
                bi = javax.imageio.ImageIO.read(file);
            } catch (IOException ex) {
           	 System.out.println(map.get("id")+"   ,    "+map.get("imgurl"));
           	 return null;
            }
           System.out.println(map.get("id")+"   ,    "+map.get("imgurl"));
            a[0] = bi.getWidth(); //获得 宽度
            a[1] = bi.getHeight(); //获得 高度
            a[2] = (int) file.length(); //获得 高度
            if(a[2]/1024<=200){
            //删除文件
            file.delete();
            }
        }else{     //图片不存在
            a=null;
        }
       return a;

    }

    public static void main(String[] args) {
  /*  	ImageUrlTest i = new ImageUrlTest();
        int[] a=i.returnImgWH("http://www.zjial.com/file/upload/2016-09-13/20160913161415-619.jpg");
        if(a==null){
            System.out.println("图片未找到!");
        }else{
            System.out.println("宽为" + a[0]);
            System.out.println("高为" + a[1]);
            System.out.println(String.format("%.1f",a[2]/1024.0)+"kb");
        }*/
        
        String s = "https://www.zjial.com/file/upload/2017-01-19/20170119175934-791.jpg";
        System.out.println(s.lastIndexOf("file"));
        System.out.println(s.substring(s.lastIndexOf("file"), s.length()));
    }
}
