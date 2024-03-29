package com.mfangsoft.zhuangjialong.app.promotion.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * table t_biz_promotion_note
 *
 * @MLTH_generated do_not_delete_during_merge
 */
@JsonInclude(value=Include.NON_NULL)
public class PromotionNoteEntity implements Serializable {
    /**
     *   主键
     * column id
     *
     * @MLTH_generated
     */
    private Long id;

    /**
     *   消费者
     * column customer_id
     *
     * @MLTH_generated
     */
    private Long customer_id;

    /**
     *   活动id
     * column pid
     *
     * @MLTH_generated
     */
    private Long pid;

    /**
     *   活动时间段id
     * column time_id
     *
     * @MLTH_generated
     */
    private Long time_id;

    /**
     *   活动产品id
     * column product_id
     *
     * @MLTH_generated
     */
    private Long product_id;

    /**
     *   更新时间
     * column update_time
     *
     * @MLTH_generated
     */
    private Date update_time;

    private Integer isnoted; 
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_biz_promotion_note
     *
     * @MLTH_generated
     */
    private static final long serialVersionUID = 1L;

    /**
     *   主键
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getId() {
        return id;
    }

    /**
     *   主键
     * @param id the value for 
     *
     * @MLTH_generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *   消费者
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getCustomer_id() {
        return customer_id;
    }

    /**
     *   消费者
     * @param customer_id the value for 
     *
     * @MLTH_generated
     */
    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    /**
     *   活动id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getPid() {
        return pid;
    }

    /**
     *   活动id
     * @param pid the value for 
     *
     * @MLTH_generated
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     *   活动时间段id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getTime_id() {
        return time_id;
    }

    /**
     *   活动时间段id
     * @param time_id the value for 
     *
     * @MLTH_generated
     */
    public void setTime_id(Long time_id) {
        this.time_id = time_id;
    }

    /**
     *   活动产品id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getProduct_id() {
        return product_id;
    }

    /**
     *   活动产品id
     * @param product_id the value for 
     *
     * @MLTH_generated
     */
    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    /**
     *   更新时间
     * @return the value of TIMESTAMP
     *
     * @MLTH_generated
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     *   更新时间
     * @param update_time the value for 
     *
     * @MLTH_generated
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Integer getIsnoted() {
		return isnoted;
	}

	public void setIsnoted(Integer isnoted) {
		this.isnoted = isnoted;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", customer_id=").append(customer_id);
        sb.append(", pid=").append(pid);
        sb.append(", time_id=").append(time_id);
        sb.append(", product_id=").append(product_id);
        sb.append(", update_time=").append(update_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}