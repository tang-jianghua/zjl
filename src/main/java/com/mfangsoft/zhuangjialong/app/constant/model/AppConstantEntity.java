package com.mfangsoft.zhuangjialong.app.constant.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *   app常量表
 * table t_app_constant
 *
 * @MLTH_generated do_not_delete_during_merge
 */
@JsonInclude(value=Include.NON_NULL)
public class AppConstantEntity implements Serializable {
    /**
     *   主键
     * column id
     *
     * @MLTH_generated
     */
    private Integer id;

    /**
     *   数值
     * column value_int
     *
     * @MLTH_generated
     */
    private Integer value_int;

    /**
     *   字符串
     * column value_str
     *
     * @MLTH_generated
     */
    private String value_str;

    /**
     *   时间
     * column value_date
     *
     * @MLTH_generated
     */
    private Date value_date;

    /**
     *   描述
     * column description
     *
     * @MLTH_generated
     */
    private String description;

    /**
     *   LONG数值
     * column value_long
     *
     * @MLTH_generated
     */
    private Long value_long;

    /**
     *   常量名
     * column key
     *
     * @MLTH_generated
     */
    private String key;

    /**
     *   类型
     * column type
     *
     * @MLTH_generated
     */
    private String type;

    /**
     *   大字符串
     * column value_txt
     *
     * @MLTH_generated
     */
    private String value_txt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_app_constant
     *
     * @MLTH_generated
     */
    private static final long serialVersionUID = 1L;

    /**
     *   主键
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getId() {
        return id;
    }

    /**
     *   主键
     * @param id the value for 
     *
     * @MLTH_generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *   数值
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getValue_int() {
        return value_int;
    }

    /**
     *   数值
     * @param value_int the value for 
     *
     * @MLTH_generated
     */
    public void setValue_int(Integer value_int) {
        this.value_int = value_int;
    }

    /**
     *   字符串
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getValue_str() {
        return value_str;
    }

    /**
     *   字符串
     * @param value_str the value for 
     *
     * @MLTH_generated
     */
    public void setValue_str(String value_str) {
        this.value_str = value_str == null ? null : value_str.trim();
    }

    /**
     *   时间
     * @return the value of TIMESTAMP
     *
     * @MLTH_generated
     */
    public Date getValue_date() {
        return value_date;
    }

    /**
     *   时间
     * @param value_date the value for 
     *
     * @MLTH_generated
     */
    public void setValue_date(Date value_date) {
        this.value_date = value_date;
    }

    /**
     *   描述
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getDescription() {
        return description;
    }

    /**
     *   描述
     * @param description the value for 
     *
     * @MLTH_generated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     *   LONG数值
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getValue_long() {
        return value_long;
    }

    /**
     *   LONG数值
     * @param value_long the value for 
     *
     * @MLTH_generated
     */
    public void setValue_long(Long value_long) {
        this.value_long = value_long;
    }

    /**
     *   常量名
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getKey() {
        return key;
    }

    /**
     *   常量名
     * @param key the value for 
     *
     * @MLTH_generated
     */
    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    /**
     *   类型
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getType() {
        return type;
    }

    /**
     *   类型
     * @param type the value for 
     *
     * @MLTH_generated
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     *   大字符串
     * @return the value of LONGVARCHAR
     *
     * @MLTH_generated
     */
    public String getValue_txt() {
        return value_txt;
    }

    /**
     *   大字符串
     * @param value_txt the value for 
     *
     * @MLTH_generated
     */
    public void setValue_txt(String value_txt) {
        this.value_txt = value_txt == null ? null : value_txt.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", value_int=").append(value_int);
        sb.append(", value_str=").append(value_str);
        sb.append(", value_date=").append(value_date);
        sb.append(", description=").append(description);
        sb.append(", value_long=").append(value_long);
        sb.append(", key=").append(key);
        sb.append(", type=").append(type);
        sb.append(", value_txt=").append(value_txt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}