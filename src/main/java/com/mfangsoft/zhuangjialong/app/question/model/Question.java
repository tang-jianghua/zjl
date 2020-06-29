package com.mfangsoft.zhuangjialong.app.question.model;

/**
* @description：
* @author：Jianghua.Tang 
* @date：2016年5月30日
* 
*/

public class Question {
  private Integer id;
  private Integer question_type_id;
  private String question_type;
  private String question;
  private String detail;
  private String url;
  
/**
 * @return the question_type
 */
public String getQuestion_type() {
	return question_type;
}
/**
 * @param question_type the question_type to set
 */
public void setQuestion_type(String question_type) {
	this.question_type = question_type;
}
/**
 * @return the id
 */
public Integer getId() {
	return id;
}
/**
 * @param id the id to set
 */
public void setId(Integer id) {
	this.id = id;
}
/**
 * @return the question_type_id
 */
public Integer getQuestion_type_id() {
	return question_type_id;
}
/**
 * @param question_type_id the question_type_id to set
 */
public void setQuestion_type_id(Integer question_type_id) {
	this.question_type_id = question_type_id;
}
/**
 * @return the question
 */
public String getQuestion() {
	return question;
}
/**
 * @param question the question to set
 */
public void setQuestion(String question) {
	this.question = question;
}
/**
 * @return the detail
 */
public String getDetail() {
	return detail;
}
/**
 * @param detail the detail to set
 */
public void setDetail(String detail) {
	this.detail = detail;
}
/**
 * @return the url
 */
public String getUrl() {
	return url;
}
/**
 * @param url the url to set
 */
public void setUrl(String url) {
	this.url = url;
}
  
}
