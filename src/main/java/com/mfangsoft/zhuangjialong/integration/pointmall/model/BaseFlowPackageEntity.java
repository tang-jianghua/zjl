package com.mfangsoft.zhuangjialong.integration.pointmall.model;

import java.io.Serializable;

/**
 * table t_biz_flow_package
 *
 * @MLTH_generated do_not_delete_during_merge
 */
public class BaseFlowPackageEntity implements Serializable {
    /**
     * column id
     *
     * @MLTH_generated
     */
    private Long id;

    /**
     * column package_num
     *
     * @MLTH_generated
     */
    private String package_num;

    /**
     * column flow
     *
     * @MLTH_generated
     */
    private String flow;

    /**
     * column price
     *
     * @MLTH_generated
     */
    private String price;

    /**
     * column operator
     *
     * @MLTH_generated
     */
    private String operator;

    /**
     * column operator_code
     *
     * @MLTH_generated
     */
    private String operator_code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_biz_flow_package
     *
     * @MLTH_generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the value for 
     *
     * @MLTH_generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getPackage_num() {
        return package_num;
    }

    /**
     * @param package_num the value for 
     *
     * @MLTH_generated
     */
    public void setPackage_num(String package_num) {
        this.package_num = package_num == null ? null : package_num.trim();
    }

    /**
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getFlow() {
        return flow;
    }

    /**
     * @param flow the value for 
     *
     * @MLTH_generated
     */
    public void setFlow(String flow) {
        this.flow = flow == null ? null : flow.trim();
    }

    /**
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the value for 
     *
     * @MLTH_generated
     */
    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    /**
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator the value for 
     *
     * @MLTH_generated
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getOperator_code() {
        return operator_code;
    }

    /**
     * @param operator_code the value for 
     *
     * @MLTH_generated
     */
    public void setOperator_code(String operator_code) {
        this.operator_code = operator_code == null ? null : operator_code.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", package_num=").append(package_num);
        sb.append(", flow=").append(flow);
        sb.append(", price=").append(price);
        sb.append(", operator=").append(operator);
        sb.append(", operator_code=").append(operator_code);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}