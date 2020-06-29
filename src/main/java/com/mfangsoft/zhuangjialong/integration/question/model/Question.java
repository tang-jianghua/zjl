package com.mfangsoft.zhuangjialong.integration.question.model;

public class Question {
	
	
	/**
	 * 问题类型ID
	 */
	private Long questionType;
	
	
	private String question_title;
	
	
	private String question_detail;


	public Long getQuestionType() {
		return questionType;
	}


	public void setQuestionType(Long questionType) {
		this.questionType = questionType;
	}


	public String getQuestion_title() {
		return question_title;
	}


	public void setQuestion_title(String question_title) {
		this.question_title = question_title;
	}


	public String getQuestion_detail() {
		return question_detail;
	}


	public void setQuestion_detail(String question_detail) {
		this.question_detail = question_detail;
	}
	
	
	

}
