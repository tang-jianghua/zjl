package com.mfangsoft.zhuangjialong.common.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;

//旧的jpeg处理类
//import com.sun.image.codec.jpeg.*; 
import com.sun.imageio.plugins.jpeg.JPEGImageWriter;

public class ImgUtil {

	/**
	 * 判断是否图片
	 * 
	 * @param fileName
	 *            文件名或文件路径
	 * @return boolean
	 */
	public static boolean isExpectImg(String fileName) {

		if (StringUtils.isEmpty(fileName)) {
			return false;
		}
		String endStr = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		if ("gif,jpg,jpeg,png,bmp".contains(endStr)) {
			return true;
		} else {
			return false;
		}
	}

	public static String saveBase64Img(String imgBase64){
		String  savePath = PropUtis.getValue(SysConstant.FILE_PATH) + "/" +DateUtils.formatDateYmd(new Date());
		System.out.println("savePath=-----------------------" + savePath);
		String filePath = "";
		try {
			byte[] imgByte = Base64.decode(imgBase64);
			for (int i = 0; i < imgByte.length; ++i) {
				if (imgByte[i] < 0) {// 调整异常数据
					imgByte[i] += 256;
				}
			}
			File forder = new File(savePath);
			if (!forder.exists()) {
				forder.mkdirs();
			}
			if (!forder.exists()) {
				throw new RuntimeException("图片文件夹不存在");
			}
			Random random = new Random();
			filePath = "/" + DateUtils.formatDateNospace(new Date())+ "-"+ random.nextInt(10) + random.nextInt(10)
					+ random.nextInt(10) + ".jpg";
			System.out.println("filePath=-----------------------" + filePath);
			OutputStream out = new FileOutputStream(savePath + filePath);
			System.out.println("outpath=-----------------------" + savePath + filePath);
			out.write(imgByte);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		System.out.println("rturnPath=-----------------------" + PropUtis.getValue(SysConstant.FILE_URL)  + "/" + DateUtils.formatDateYmd(new Date()) + filePath);
		return PropUtis.getValue(SysConstant.FILE_URL)  + "/" + DateUtils.formatDateYmd(new Date()) + filePath;
	}

	public static void main(String[] args) {
		Tosmallerpic("F:\\", new File("F:\\xorxf3oj7q7x2yb9.gif"), "_middle", "xorxf3oj7q7x2yb9.gif", 188, 165,
				(float) 0.7);
	}

	/**
	 * 
	 * @param f
	 *            图片所在的文件夹路径
	 * @param file
	 *            图片路径
	 * @param ext
	 *            扩展名
	 * @param n
	 *            图片名
	 * @param w
	 *            目标宽
	 * @param h
	 *            目标高
	 * @param per
	 *            百分比
	 */
	private static void Tosmallerpic(String f, File file, String ext, String n, int w, int h, float per) {
		Image src;
		try {
			src = javax.imageio.ImageIO.read(file); // 构造Image对象

			String img_midname = f + n.substring(0, n.indexOf(".")) + ext + n.substring(n.indexOf("."));// 文件保存路径
			int old_w = src.getWidth(null); // 得到源图宽
			int old_h = src.getHeight(null);
			int new_w = 0;
			int new_h = 0; // 得到源图长

			double w2 = (old_w * 1.00) / (w * 1.00);
			double h2 = (old_h * 1.00) / (h * 1.00);

			// 图片跟据长宽留白，成一个正方形图。
			// BufferedImage oldpic;
			// if (old_w > old_h) {
			// oldpic = new BufferedImage(old_w, old_w,
			// BufferedImage.TYPE_INT_RGB);
			// } else {
			// if (old_w < old_h) {
			// oldpic = new BufferedImage(old_h, old_h,
			// BufferedImage.TYPE_INT_RGB);
			// } else {
			// oldpic = new BufferedImage(old_w, old_h,
			// BufferedImage.TYPE_INT_RGB);
			// }
			// }
			// Graphics2D g = oldpic.createGraphics();
			// g.setColor(Color.white);
			// if (old_w > old_h) {
			// g.fillRect(0, 0, old_w, old_w);
			// g.drawImage(src, 0, (old_w - old_h) / 2, old_w, old_h,
			// Color.white, null);
			// } else {
			// if (old_w < old_h) {
			// g.fillRect(0, 0, old_h, old_h);
			// g.drawImage(src, (old_h - old_w) / 2, 0, old_w, old_h,
			// Color.white, null);
			// } else {
			// // g.fillRect(0,0,old_h,old_h);
			// g.drawImage(src.getScaledInstance(old_w, old_h,
			// Image.SCALE_SMOOTH), 0, 0, null);
			// }
			// }
			// g.dispose();
			// src = oldpic;
			// 图片调整为方形结束
			if (old_w > w)
				new_w = (int) Math.round(old_w / w2);
			else
				new_w = old_w;
			if (old_h > h)
				new_h = (int) Math.round(old_h / h2);// 计算新图长宽
			else
				new_h = old_h;
			BufferedImage image_to_save = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
			image_to_save.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0, null);
			FileOutputStream fos = new FileOutputStream(img_midname); // 输出到文件流

			// 旧的使用 jpeg classes进行处理的方法
			// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
			// JPEGEncodeParam jep =
			// JPEGCodec.getDefaultJPEGEncodeParam(image_to_save);
			/* 压缩质量 */
			// jep.setQuality(per, true);
			// encoder.encode(image_to_save, jep);

			// 新的方法
			saveAsJPEG(100, image_to_save, per, fos);

			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 以JPEG编码保存图片
	 * 
	 * @param dpi
	 *            分辨率
	 * @param image_to_save
	 *            要处理的图像图片
	 * @param JPEGcompression
	 *            压缩比
	 * @param fos
	 *            文件输出流
	 * @throws IOException
	 */
	public static void saveAsJPEG(Integer dpi, BufferedImage image_to_save, float JPEGcompression,
			FileOutputStream fos) {
		try {
			// useful documentation at
			// http://docs.oracle.com/javase/7/docs/api/javax/imageio/metadata/doc-files/jpeg_metadata.html
			// useful example program at
			// http://johnbokma.com/java/obtaining-image-metadata.html to output
			// JPEG data

			// old jpeg class
			// com.sun.image.codec.jpeg.JPEGImageEncoder jpegEncoder =
			// com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(fos);
			// com.sun.image.codec.jpeg.JPEGEncodeParam jpegEncodeParam =
			// jpegEncoder.getDefaultJPEGEncodeParam(image_to_save);

			// Image writer
			JPEGImageWriter imageWriter = (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpg").next();
			ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
			imageWriter.setOutput(ios);
			// and metadata
			IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image_to_save),
					null);

			if (dpi != null && !dpi.equals("")) {

				// old metadata
				// jpegEncodeParam.setDensityUnit(com.sun.image.codec.jpeg.JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
				// jpegEncodeParam.setXDensity(dpi);
				// jpegEncodeParam.setYDensity(dpi);

				// new metadata
				Element tree = (Element) imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
				Element jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
				jfif.setAttribute("Xdensity", Integer.toString(dpi));
				jfif.setAttribute("Ydensity", Integer.toString(dpi));

			}

			if (JPEGcompression >= 0 && JPEGcompression <= 1f) {

				// old compression
				// jpegEncodeParam.setQuality(JPEGcompression,false);

				// new Compression
				JPEGImageWriteParam jpegParams = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
				jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
				jpegParams.setCompressionQuality(JPEGcompression);

			}

			// old write and clean
			// jpegEncoder.encode(image_to_save, jpegEncodeParam);

			// new Write and clean up
			imageWriter.write(imageMetaData, new IIOImage(image_to_save, null, null), null);
			ios.close();
			imageWriter.dispose();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
