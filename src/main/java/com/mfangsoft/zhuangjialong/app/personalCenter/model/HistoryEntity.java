package com.mfangsoft.zhuangjialong.app.personalCenter.model;

import java.io.Serializable;
import java.util.Date;

/**
 *   消费者足迹表
 * table t_app_customer_history
 *
 * @MLTH_generated do_not_delete_during_merge
 */
public class HistoryEntity implements Serializable {
    /**
     *   主键id
     * column id
     *
     * @MLTH_generated
     */
    private Long id;

    /**
     *   消费者id
     * column customer_id
     *
     * @MLTH_generated
     */
    private Long customer_id;

    /**
     *   产品id
     * column product_id
     *
     * @MLTH_generated
     */
    private Long product_id;

    /**
     *   足迹时间
     * column create_time
     *
     * @MLTH_generated
     */
    private Date create_time;

    /**
     * 状态
     * column state
     *
     * @MLTH_generated
     */
    private Integer state;
    
    /**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_app_customer_history
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
     *   消费者id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getCustomer_id() {
        return customer_id;
    }

    /**
     *   消费者id
     * @param customer_id the value for 
     *
     * @MLTH_generated
     */
    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    /**
     *   产品id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getProduct_id() {
        return product_id;
    }

    /**
     *   产品id
     * @param product_id the value for 
     *
     * @MLTH_generated
     */
    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    /**
     *   足迹时间
     * @return the value of TIMESTAMP
     *
     * @MLTH_generated
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     *   足迹时间
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
        sb.append(", customer_id=").append(customer_id);
        sb.append(", product_id=").append(product_id);
        sb.append(", create_time=").append(create_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
    
    private String region_code;

	/**
	 * @return the region_code
	 */
	public String getRegion_code() {
		return region_code;
	}

	/**
	 * @param region_code the region_code to set
	 */
	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}
    
    
}