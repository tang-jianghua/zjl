package com.mfangsoft.zhuangjialong.app.sendflow.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *   流量批次请求信息表
 * table t_biz_flow_batch
 *
 * @MLTH_generated do_not_delete_during_merge
 */
@JsonInclude(value=Include.NON_NULL)
public class FlowBatchEntity implements Serializable {
    /**
     * column id
     *
     * @MLTH_generated
     */
    private Integer id;

    /**
     *   批次号
     * column batch_no
     *
     * @MLTH_generated
     */
    private String batch_no;

    /**
     *   手机号
     * column mobile
     *
     * @MLTH_generated
     */
    private String mobile;

    /**
     *   电信套餐
     * column ctcc
     *
     * @MLTH_generated
     */
    private String ctcc;

    /**
     *   移动套餐
     * column cmcc
     *
     * @MLTH_generated
     */
    private String cmcc;

    /**
     *   联通套餐
     * column cucc
     *
     * @MLTH_generated
     */
    private String cucc;

    /**
     *   请求状态码
     * column code
     *
     * @MLTH_generated
     */
    private String code;

    /**
     *   请求错误信息
     * column msg
     *
     * @MLTH_generated
     */
    private String msg;

    /**
     *   创建时间
     * column create_time
     *
     * @MLTH_generated
     */
    private Date create_time;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_biz_flow_batch
     *
     * @MLTH_generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the value for 
     *
     * @MLTH_generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *   批次号
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getBatch_no() {
        return batch_no;
    }

    /**
     *   批次号
     * @param batch_no the value for 
     *
     * @MLTH_generated
     */
    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no == null ? null : batch_no.trim();
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
     *   电信套餐
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getCtcc() {
        return ctcc;
    }

    /**
     *   电信套餐
     * @param ctcc the value for 
     *
     * @MLTH_generated
     */
    public void setCtcc(String ctcc) {
        this.ctcc = ctcc == null ? null : ctcc.trim();
    }

    /**
     *   移动套餐
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getCmcc() {
        return cmcc;
    }

    /**
     *   移动套餐
     * @param cmcc the value for 
     *
     * @MLTH_generated
     */
    public void setCmcc(String cmcc) {
        this.cmcc = cmcc == null ? null : cmcc.trim();
    }

    /**
     *   联通套餐
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getCucc() {
        return cucc;
    }

    /**
     *   联通套餐
     * @param cucc the value for 
     *
     * @MLTH_generated
     */
    public void setCucc(String cucc) {
        this.cucc = cucc == null ? null : cucc.trim();
    }

    /**
     *   请求状态码
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getCode() {
        return code;
    }

    /**
     *   请求状态码
     * @param code the value for 
     *
     * @MLTH_generated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     *   请求错误信息
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getMsg() {
        return msg;
    }

    /**
     *   请求错误信息
     * @param msg the value for 
     *
     * @MLTH_generated
     */
    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", batch_no=").append(batch_no);
        sb.append(", mobile=").append(mobile);
        sb.append(", ctcc=").append(ctcc);
        sb.append(", cmcc=").append(cmcc);
        sb.append(", cucc=").append(cucc);
        sb.append(", code=").append(code);
        sb.append(", msg=").append(msg);
        sb.append(", create_time=").append(create_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}