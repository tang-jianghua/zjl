package com.mfangsoft.zhuangjialong.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class Mark2D {

	public static BitMatrix baseDimension(String url, int width, int height) throws IOException {

		try {
			QRCodeWriter writer = new QRCodeWriter();
			return writer.encode(url, BarcodeFormat.QR_CODE, height, width);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成2维码图片文件
	 * 
	 */
	public static void get2DMarkToFile(String url, HttpServletResponse response, int width, int height,String fileType, String filePath,
			String fileName) throws IOException {

		if (url != null && !"".equals(url)) {

			Path path = FileSystems.getDefault().getPath(filePath, fileName);

			BitMatrix m = baseDimension(url, width, height);

			MatrixToImageWriter.writeToPath(m, fileType, path);
		}

	}

	/**
	 * 生成2维码流
	 * 
	 */
	public static void get2DMarkToStream(String url, HttpServletResponse response, int width, int height, String fileType)
			throws IOException {

		ServletOutputStream stream = response.getOutputStream();
		
		try {
			
			if (url != null && !"".equals(url)) {

				BitMatrix m = baseDimension(url, width, height);

				MatrixToImageWriter.writeToStream(m, fileType, stream);
			}
			
		} finally {
			
			if (stream != null) {
				stream.flush();
				stream.close();
			}
			
		}
	}
}
