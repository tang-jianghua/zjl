package com.mfangsoft.zhuangjialong.app.charge.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *   话费批次状态接受表
 * table t_biz_charge_back
 *
 * @MLTH_generated do_not_delete_during_merge
 */

@JsonInclude(value=Include.NON_NULL)
public class ChargeBackEntity implements Serializable {
    /**
     *   主键
     * column id
     *
     * @MLTH_generated
     */
    private Integer id;

    /**
     *   任务id
     * column task_id
     *
     * @MLTH_generated
     */
    private String task_id;

    /**
     *   手机号
     * column mobile
     *
     * @MLTH_generated
     */
    private String mobile;

    /**
     *   状态(签名)?1:成功?2:失败 
     * column state
     *
     * @MLTH_generated
     */
    private Integer state;

    /**
     *   消息(签名)
     * column message
     *
     * @MLTH_generated
     */
    private String message;

    /**
     *   签名
     * column sign
     *
     * @MLTH_generated
     */
    private String sign;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_biz_charge_back
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
     *   任务id
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getTask_id() {
        return task_id;
    }

    /**
     *   任务id
     * @param task_id the value for 
     *
     * @MLTH_generated
     */
    public void setTask_id(String task_id) {
        this.task_id = task_id == null ? null : task_id.trim();
    }

    /**
     *   手机号
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getMobile() {
        return mobile;
    }

    /**
     *   手机号
     * @param mobile the value for 
     *
     * @MLTH_generated
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     *   状态(签名)?1:成功?2:失败 
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getState() {
        return state;
    }

    /**
     *   状态(签名)?1:成功?2:失败 
     * @param state the value for 
     *
     * @MLTH_generated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     *   消息(签名)
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getMessage() {
        return message;
    }

    /**
     *   消息(签名)
     * @param message the value for 
     *
     * @MLTH_generated
     */
    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    /**
     *   签名
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getSign() {
        return sign;
    }

    /**
     *   签名
     * @param sign the value for 
     *
     * @MLTH_generated
     */
    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", task_id=").append(task_id);
        sb.append(", mobile=").append(mobile);
        sb.append(", state=").append(state);
        sb.append(", message=").append(message);
        sb.append(", sign=").append(sign);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
    
    
}