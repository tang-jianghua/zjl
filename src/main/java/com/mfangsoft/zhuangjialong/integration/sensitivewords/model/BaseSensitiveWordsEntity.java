package com.mfangsoft.zhuangjialong.integration.sensitivewords.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *   敏感词库
 * table t_biz_sensitive_words
 *
 * @MLTH_generated do_not_delete_during_merge
 */
public class BaseSensitiveWordsEntity implements Serializable {
    /**
     *   主键
     * column id
     *
     * @MLTH_generated
     */
    private Integer id;

    /**
     *   词库名
     * column name
     *
     * @MLTH_generated
     */
    private String name;

    /**
     *   创建时间
     * column create_time
     *
     * @MLTH_generated
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date create_time;

    /**
     *   发布时间
     * column public_time
     *
     * @MLTH_generated
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date public_time;

    /**
     *   状态(true:有效false:无效)
     * column state
     *
     * @MLTH_generated
     */
    private Byte state;

    /**
     *   词库内容
     * column content
     *
     * @MLTH_generated
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_biz_sensitive_words
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
     *   词库名
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getName() {
        return name;
    }

    /**
     *   词库名
     * @param name the value for 
     *
     * @MLTH_generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     *   创建时间
     * @return the value of TIMESTAMP
     *
     * @MLTH_generated
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     *   创建时间
     * @param create_time the value for 
     *
     * @MLTH_generated
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     *   发布时间
     * @return the value of TIMESTAMP
     *
     * @MLTH_generated
     */
    public Date getPublic_time() {
        return public_time;
    }

    /**
     *   发布时间
     * @param public_time the value for 
     *
     * @MLTH_generated
     */
    public void setPublic_time(Date public_time) {
        this.public_time = public_time;
    }

    /**
     *   状态(true:有效false:无效)
     * @return the value of TINYINT
     *
     * @MLTH_generated
     */
    public Byte getState() {
        return state;
    }

    /**
     *   状态(true:有效false:无效)
     * @param state the value for 
     *
     * @MLTH_generated
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     *   词库内容
     * @return the value of LONGVARCHAR
     *
     * @MLTH_generated
     */
    public String getContent() {
        return content;
    }

    /**
     *   词库内容
     * @param content the value for 
     *
     * @MLTH_generated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", create_time=").append(create_time);
        sb.append(", public_time=").append(public_time);
        sb.append(", state=").append(state);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}