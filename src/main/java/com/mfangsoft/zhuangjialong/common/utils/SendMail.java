package com.mfangsoft.zhuangjialong.common.utils;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.mfangsoft.zhuangjialong.core.utils.Config;

public class SendMail {

	static String subject1 = "你好，这是一封来自于魔力铁盒的验证邮件";
	static String subject2 = "你好";

	final String text1 = "zxxxxxxxxxxxxxxx";
	final String text2 = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";

	public static void setTextMail(String mailAdress, String subject, String text) {
		JavaMailSender sender = (JavaMailSender) SpringBeantUtils.getBean("mailSender");

		SimpleMailMessage mail = new SimpleMailMessage();
		
		mail.setFrom(Config.SENDEMAIL);
		mail.setTo(mailAdress);
		mail.setSubject(subject);
		mail.setText(text);
		mail.setSentDate(new Date());

		sender.send(mail);
	}

	public static void setHtmlMail(String mailAdress, String subject, String text) throws MessagingException {
		JavaMailSender sender = (JavaMailSender) SpringBeantUtils.getBean("mailSender");

		MimeMessage mail = sender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mail, true, "utf-8");
		messageHelper.setTo(mailAdress);

		messageHelper.setSubject(subject);
		messageHelper.setText("<html><head></head><body><h1>" + text + "</h1></body></html>", true);
		messageHelper.setSentDate(new Date());
		sender.send(mail);
	}
}
