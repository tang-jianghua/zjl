package com.mfangsoft.zhuangjialong.app.seller.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *   施工员/推广导购信息表
 * table t_app_seller_info
 *
 * @MLTH_generated do_not_delete_during_merge
 */
@JsonInclude(value=Include.NON_NULL)
public class BaseSellerInfoEntity implements Serializable {
    /**
     *   主键id
     * column id
     *
     * @MLTH_generated
     */
    private Long id;

    /**
     *   施工员/服务导购ID
     * column seller_id
     *
     * @MLTH_generated
     */
    private Long seller_id;

    /**
     *   支付宝账户
     * column ali_account
     *
     * @MLTH_generated
     */
    private String ali_account;

    /**
     *   施工人员信息表ID
     * column construct_info_id
     *
     * @MLTH_generated
     */
    private Long construct_info_id;

    /**
     *   提成比例
     * column commission_rate
     *
     * @MLTH_generated
     */
    private Double commission_rate;

    /**
     *   推广员id
     * column expand_id
     *
     * @MLTH_generated
     */
    private Long expand_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_app_seller_info
     *
     * @MLTH_generated
     */
    private static final long serialVersionUID = 1L;

    /**
     *   主键id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getId() {
        return id;
    }

    /**
     *   主键id
     * @param id the value for 
     *
     * @MLTH_generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *   施工员/服务导购ID
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getSeller_id() {
        return seller_id;
    }

    /**
     *   施工员/服务导购ID
     * @param seller_id the value for 
     *
     * @MLTH_generated
     */
    public void setSeller_id(Long seller_id) {
        this.seller_id = seller_id;
    }

    /**
     *   支付宝账户
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getAli_account() {
        return ali_account;
    }

    /**
     *   支付宝账户
     * @param ali_account the value for 
     *
     * @MLTH_generated
     */
    public void setAli_account(String ali_account) {
        this.ali_account = ali_account == null ? null : ali_account.trim();
    }

    /**
     *   施工人员信息表ID
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getConstruct_info_id() {
        return construct_info_id;
    }

    /**
     *   施工人员信息表ID
     * @param construct_info_id the value for 
     *
     * @MLTH_generated
     */
    public void setConstruct_info_id(Long construct_info_id) {
        this.construct_info_id = construct_info_id;
    }

    /**
     *   提成比例
     * @return the value of DOUBLE
     *
     * @MLTH_generated
     */
    public Double getCommission_rate() {
        return commission_rate;
    }

    /**
     *   提成比例
     * @param commission_rate the value for 
     *
     * @MLTH_generated
     */
    public void setCommission_rate(Double commission_rate) {
        this.commission_rate = commission_rate;
    }

    /**
     *   推广员id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getExpand_id() {
        return expand_id;
    }

    /**
     *   推广员id
     * @param expand_id the value for 
     *
     * @MLTH_generated
     */
    public void setExpand_id(Long expand_id) {
        this.expand_id = expand_id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", seller_id=").append(seller_id);
        sb.append(", ali_account=").append(ali_account);
        sb.append(", construct_info_id=").append(construct_info_id);
        sb.append(", commission_rate=").append(commission_rate);
        sb.append(", expand_id=").append(expand_id);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}