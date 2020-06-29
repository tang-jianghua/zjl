package com.mfangsoft.zhuangjialong.app.personalCenter.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *   收藏表
 * table t_app_customer_collection
 *
 * @MLTH_generated do_not_delete_during_merge
 */
@JsonInclude(value=Include.NON_NULL)
public class CustomerCollectionEntity implements Serializable {
    /**
     *   主键id
     * column id
     *
     * @MLTH_generated
     */
    private Integer id;

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
     *   品牌id
     * column brand_id
     *
     * @MLTH_generated
     */
    private Long brand_id;

    /**
     *   1：收藏/关注中，2：已取消
     * column state
     *
     * @MLTH_generated
     */
    private Integer state;
    
    /**
     *   创建时间
     * column create_time
     *
     * @MLTH_generated
     */
    private Date create_time;

    
    
    
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
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_app_customer_collection
     *
     * @MLTH_generated
     */
    private static final long serialVersionUID = 1L;

    /**
     *   主键id
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getId() {
        return id;
    }

    /**
     *   主键id
     * @param id the value for 
     *
     * @MLTH_generated
     */
    public void setId(Integer id) {
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
     *   品牌id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getBrand_id() {
        return brand_id;
    }

    /**
     *   品牌id
     * @param brand_id the value for 
     *
     * @MLTH_generated
     */
    public void setBrand_id(Long brand_id) {
        this.brand_id = brand_id;
    }

    /**
     *   1：收藏/关注中，2：已取消
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getState() {
        return state;
    }

    /**
     *   1：收藏/关注中，2：已取消
     * @param state the value for 
     *
     * @MLTH_generated
     */
    public void setState(Integer state) {
        this.state = state;
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
        sb.append(", brand_id=").append(brand_id);
        sb.append(", state=").append(state);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
    
    private Integer select_id;




	/**
	 * @return the select_id
	 */
	public Integer getSelect_id() {
		return select_id;
	}

	/**
	 * @param select_id the select_id to set
	 */
	public void setSelect_id(Integer select_id) {
		this.select_id = select_id;
	}
    
    
}