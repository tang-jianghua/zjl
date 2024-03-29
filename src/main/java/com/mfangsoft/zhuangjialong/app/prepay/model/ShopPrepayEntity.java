package com.mfangsoft.zhuangjialong.app.prepay.model;

import java.io.Serializable;
import java.util.Date;

/**
 * table t_app_shop_prepay
 *
 * @MLTH_generated do_not_delete_during_merge
 */
public class ShopPrepayEntity implements Serializable {
    /**
     *   主键
     * column id
     *
     * @MLTH_generated
     */
    private Integer id;

    /**
     *   商店id
     * column shop_id
     *
     * @MLTH_generated
     */
    private Long shop_id;
    private String name;
    /**
     *   用户id
     * column customer_id
     *
     * @MLTH_generated
     */
    private Long customer_id;

    /**
     *   券值
     * column value
     *
     * @MLTH_generated
     */
    private Integer value;

    /**
     *   状态： 0 未使用；1 已使用
     * column state
     *
     * @MLTH_generated
     */
    private Integer state;

    /**
     * column create_time
     *
     * @MLTH_generated
     */
    private Date create_time;

    /**
     * column use_time
     *
     * @MLTH_generated
     */
    private Date use_time;

    private String order_code;
    
    private String product_order_code;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_app_shop_prepay
     *
     * @MLTH_generated
     */
    private static final long serialVersionUID = 1L;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
     *   商店id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getShop_id() {
        return shop_id;
    }

    /**
     *   商店id
     * @param shop_id the value for 
     *
     * @MLTH_generated
     */
    public void setShop_id(Long shop_id) {
        this.shop_id = shop_id;
    }

    /**
     *   用户id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getCustomer_id() {
        return customer_id;
    }

    /**
     *   用户id
     * @param customer_id the value for 
     *
     * @MLTH_generated
     */
    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    /**
     *   券值
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getValue() {
        return value;
    }

    /**
     *   券值
     * @param value the value for 
     *
     * @MLTH_generated
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     *   状态： 0 未使用；1 已使用
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getState() {
        return state;
    }

    /**
     *   状态： 0 未使用；1 已使用
     * @param state the value for 
     *
     * @MLTH_generated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * @return the value of TIMESTAMP
     *
     * @MLTH_generated
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * @param create_time the value for 
     *
     * @MLTH_generated
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * @return the value of TIMESTAMP
     *
     * @MLTH_generated
     */
    public Date getUse_time() {
        return use_time;
    }

    /**
     * @param use_time the value for 
     *
     * @MLTH_generated
     */
    public void setUse_time(Date use_time) {
        this.use_time = use_time;
    }

    public String getOrder_code() {
		return order_code;
	}

	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}

	public String getProduct_order_code() {
		return product_order_code;
	}

	public void setProduct_order_code(String product_order_code) {
		this.product_order_code = product_order_code;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", shop_id=").append(shop_id);
        sb.append(", customer_id=").append(customer_id);
        sb.append(", value=").append(value);
        sb.append(", state=").append(state);
        sb.append(", create_time=").append(create_time);
        sb.append(", use_time=").append(use_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}