package com.mfangsoft.zhuangjialong.app.personalCenter.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * table t_app_customer_message_type
 *
 * @MLTH_generated do_not_delete_during_merge
 */
@JsonInclude(value = Include.NON_NULL)
public class MessageTypeEntity implements Serializable {
    /**
     *   主键
     * column id
     *
     * @MLTH_generated
     */
    private Integer id;

    /**
     *   消息类型
     * column type_id
     *
     * @MLTH_generated
     */
    private Integer type_id;

    /**
     *   消息标题模板
     * column content
     *
     * @MLTH_generated
     */
    private String title;
    /**
     *   消息内容模板
     * column content
     *
     * @MLTH_generated
     */
    private String content;

    /**
     *   消息分组 1订单消息 2活动消息 3通知消息
     * column group
     *
     * @MLTH_generated
     */
    private Integer group;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_app_customer_message_type
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

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
     *   消息类型
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getType_id() {
        return type_id;
    }

    /**
     *   消息类型
     * @param type_id the value for 
     *
     * @MLTH_generated
     */
    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    /**
     *   消息内容模板
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getContent() {
        return content;
    }

    /**
     *   消息内容模板
     * @param content the value for 
     *
     * @MLTH_generated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     *   消息分组 1订单消息 2活动消息 3通知消息
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getGroup() {
        return group;
    }

    /**
     *   消息分组 1订单消息 2活动消息 3通知消息
     * @param group the value for 
     *
     * @MLTH_generated
     */
    public void setGroup(Integer group) {
        this.group = group;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type_id=").append(type_id);
        sb.append(", content=").append(content);
        sb.append(", group=").append(group);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}