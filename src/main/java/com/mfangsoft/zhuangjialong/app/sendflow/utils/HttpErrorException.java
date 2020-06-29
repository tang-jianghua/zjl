package com.mfangsoft.zhuangjialong.app.sendflow.utils;

/**
 * HttpRequestBody拼装错误
 *
 */
public class HttpErrorException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public HttpErrorException() {
		super();
	}

	public HttpErrorException(String message) {
		super(message);
	}

}
