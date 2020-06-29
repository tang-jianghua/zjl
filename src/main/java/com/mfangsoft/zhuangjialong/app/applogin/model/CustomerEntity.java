package com.mfangsoft.zhuangjialong.app.applogin.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 消费者表 table t_app_customer
 *
 * @MLTH_generated do_not_delete_during_merge
 */

@JsonInclude(value=Include.NON_NULL)
public class CustomerEntity implements Serializable {
	
	/**
	 * 验证码
	 *
	 * @MLTH_generated
	 */
	private String vcode;
	
	/**
	 * 用户id column id
	 *
	 * @MLTH_generated
	 */
	private Long id;

	/**
	 * 用户姓名 column name
	 *
	 * @MLTH_generated
	 */
	private String name;

	/**
	 * 用户账号 column account
	 *
	 * @MLTH_generated
	 */
	private String account;

	/**
	 * 用户密码 column password
	 *
	 * @MLTH_generated
	 */
	private String password;

	/**
	 * 用户密码 column password
	 *
	 * @MLTH_generated
	 */
	private String new_password;
	
	/**
	 * 用户头像 column head_url
	 *
	 * @MLTH_generated
	 */
	private String head_url;

	/**
	 * 用户头像base64字符代码 column head_url
	 *
	 * @MLTH_generated
	 */
	private String imgBase64;
	/**
	 * 0：男;1:女 column gender
	 *
	 * @MLTH_generated
	 */
	private Integer gender;

	/**
	 * 用户生日 column birthday
	 *
	 * @MLTH_generated
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date birthday;
	
	/**
	 * 注册时间 column create_time
	 *
	 * @MLTH_generated
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date create_time;


	/**
	 * 联系电话 column phone
	 *
	 * @MLTH_generated
	 */
	private String phone;

	/**
	 * 邀请注册/自主注册 column property
	 *
	 * @MLTH_generated
	 */
	private Integer property;

	/**
	 * 消费者 column inviter_id
	 *
	 * @MLTH_generated
	 */
	private Long inviter_id;

	/**
	 * 手机号MD5 column exshopper_id
	 *
	 * @MLTH_generated
	 */
	private Long exshopper_id;

	/**
	 * 邮箱 column email
	 *
	 * @MLTH_generated
	 */
	private String email;

	/**
	 * 0:普通用户,1:会员 column level
	 *
	 * @MLTH_generated
	 */
	private Integer level;

	/**
	 * 喜好品类 column class_id
	 *
	 * @MLTH_generated
	 */
	private Integer class_id;

	/**
	 * 喜好空间 column space
	 *
	 * @MLTH_generated
	 */
	private Integer space;

	/**
	 * 喜好风格 column style
	 *
	 * @MLTH_generated
	 */
	private Integer style;
	
	private Boolean isreceive_message_order;
	
	private Boolean isreceive_message_appointment;

	private Boolean isreceive_message_note;
	
	private Boolean isreceive_message_smart;
	/**
	 * 被该用户推广的用户总数，即exshopper_id等于该用户id的记录总数
	 */
	private Integer byExpandCustomerCount;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database table t_app_customer
	 *
	 * @MLTH_generated
	 */
	private static final long serialVersionUID = 1L;

	public Boolean getIsreceive_message_order() {
		return isreceive_message_order;
	}

	public void setIsreceive_message_order(Boolean isreceive_message_order) {
		this.isreceive_message_order = isreceive_message_order;
	}

	public Boolean getIsreceive_message_appointment() {
		return isreceive_message_appointment;
	}

	public void setIsreceive_message_appointment(Boolean isreceive_message_appointment) {
		this.isreceive_message_appointment = isreceive_message_appointment;
	}

	public Boolean getIsreceive_message_note() {
		return isreceive_message_note;
	}

	public void setIsreceive_message_note(Boolean isreceive_message_note) {
		this.isreceive_message_note = isreceive_message_note;
	}

	public String getNew_password() {
		return new_password;
	}

	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public String getImgBase64() {
		return imgBase64;
	}

	public void setImgBase64(String imgBase64) {
		this.imgBase64 = imgBase64;
	}

	/**
	 * 用户id
	 * 
	 * @return the value of BIGINT
	 *
	 * @MLTH_generated
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 用户id
	 * 
	 * @param id
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 用户姓名
	 * 
	 * @return the value of VARCHAR
	 *
	 * @MLTH_generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * 用户姓名
	 * 
	 * @param name
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	/**
	 * 用户账号
	 * 
	 * @return the value of VARCHAR
	 *
	 * @MLTH_generated
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * 用户账号
	 * 
	 * @param account
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setAccount(String account) {
		this.account = account == null ? null : account.trim();
	}

	/**
	 * 用户密码
	 * 
	 * @return the value of VARCHAR
	 *
	 * @MLTH_generated
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 用户密码
	 * 
	 * @param password
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	/**
	 * 用户头像
	 * 
	 * @return the value of VARCHAR
	 *
	 * @MLTH_generated
	 */
	public String getHead_url() {
		return head_url;
	}

	/**
	 * 用户头像
	 * 
	 * @param head_url
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setHead_url(String head_url) {
		this.head_url = head_url == null ? null : head_url.trim();
	}

	/**
	 * 0：男;1:女
	 * 
	 * @return the value of INTEGER
	 *
	 * @MLTH_generated
	 */
	public Integer getGender() {
		return gender;
	}

	/**
	 * 0：男;1:女
	 * 
	 * @param gender
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setGender(Integer gender) {
		this.gender = gender;
	}

	/**
	 * 用户生日
	 * 
	 * @return the value of DATE
	 *
	 * @MLTH_generated
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * 用户生日
	 * 
	 * @param birthday
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
   
	
	/**
	 * @return the create_time
	 */
	public Date getCreate_time() {
		return create_time;
	}

	/**
	 * @param create_time the create_time to set
	 */
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	/**
	 * 联系电话
	 * 
	 * @return the value of VARCHAR
	 *
	 * @MLTH_generated
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 联系电话
	 * 
	 * @param phone
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	/**
	 * 邀请注册/自主注册
	 * 
	 * @return the value of INTEGER
	 *
	 * @MLTH_generated
	 */
	public Integer getProperty() {
		return property;
	}

	/**
	 * 邀请注册/自主注册
	 * 
	 * @param property
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setProperty(Integer property) {
		this.property = property;
	}

	/**
	 * 消费者
	 * 
	 * @return the value of BIGINT
	 *
	 * @MLTH_generated
	 */
	public Long getInviter_id() {
		return inviter_id;
	}

	/**
	 * 消费者
	 * 
	 * @param inviter_id
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setInviter_id(Long inviter_id) {
		this.inviter_id = inviter_id;
	}

	/**
	 * 手机号MD5
	 * 
	 * @return the value of BIGINT
	 *
	 * @MLTH_generated
	 */
	public Long getExshopper_id() {
		return exshopper_id;
	}

	/**
	 * 手机号MD5
	 * 
	 * @param exshopper_id
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setExshopper_id(Long exshopper_id) {
		this.exshopper_id = exshopper_id;
	}

	/**
	 * 邮箱
	 * 
	 * @return the value of VARCHAR
	 *
	 * @MLTH_generated
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 邮箱
	 * 
	 * @param email
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	/**
	 * 0:普通用户,1:会员
	 * 
	 * @return the value of INTEGER
	 *
	 * @MLTH_generated
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * 0:普通用户,1:会员
	 * 
	 * @param level
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * 喜好品类
	 * 
	 * @return the value of INTEGER
	 *
	 * @MLTH_generated
	 */
	public Integer getClass_id() {
		return class_id;
	}

	/**
	 * 喜好品类
	 * 
	 * @param class_id
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setClass_id(Integer class_id) {
		this.class_id = class_id;
	}

	/**
	 * 喜好空间
	 * 
	 * @return the value of INTEGER
	 *
	 * @MLTH_generated
	 */
	public Integer getSpace() {
		return space;
	}

	/**
	 * 喜好空间
	 * 
	 * @param space
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setSpace(Integer space) {
		this.space = space;
	}

	/**
	 * 喜好风格
	 * 
	 * @return the value of INTEGER
	 *
	 * @MLTH_generated
	 */
	public Integer getStyle() {
		return style;
	}

	/**
	 * 喜好风格
	 * 
	 * @param style
	 *            the value for
	 *
	 * @MLTH_generated
	 */
	public void setStyle(Integer style) {
		this.style = style;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", name=").append(name);
		sb.append(", account=").append(account);
		sb.append(", password=").append(password);
		sb.append(", head_url=").append(head_url);
		sb.append(", gender=").append(gender);
		sb.append(", birthday=").append(birthday);
		sb.append(", phone=").append(phone);
		sb.append(", property=").append(property);
		sb.append(", inviter_id=").append(inviter_id);
		sb.append(", exshopper_id=").append(exshopper_id);
		sb.append(", email=").append(email);
		sb.append(", level=").append(level);
		sb.append(", class_id=").append(class_id);
		sb.append(", space=").append(space);
		sb.append(", style=").append(style);
		sb.append(", create_time=").append(create_time);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
    private String invode;

	/**
	 * @return the invode
	 */
	public String getInvode() {
		return invode;
	}

	/**
	 * @param invode the invode to set
	 */
	public void setInvode(String invode) {
		this.invode = invode;
	}

	public Integer getByExpandCustomerCount() {
		return byExpandCustomerCount;
	}

	public void setByExpandCustomerCount(Integer byExpandCustomerCount) {
		this.byExpandCustomerCount = byExpandCustomerCount;
	}

	public Boolean getIsreceive_message_smart() {
		return isreceive_message_smart;
	}

	public void setIsreceive_message_smart(Boolean isreceive_message_smart) {
		this.isreceive_message_smart = isreceive_message_smart;
	}
    


}