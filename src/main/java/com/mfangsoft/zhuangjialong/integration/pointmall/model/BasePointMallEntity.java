package com.mfangsoft.zhuangjialong.integration.pointmall.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *   积分商城
 * table t_biz_point_mall
 *
 * @MLTH_generated do_not_delete_during_merge
 */
@JsonInclude(value=Include.NON_NULL)
public class BasePointMallEntity implements Serializable {
    /**
     * column id
     *
     * @MLTH_generated
     */
    private Long id;

    /**
     *   product_title
     * column product_title
     *
     * @MLTH_generated
     */
    private String product_title;

    /**
     *   兑换方式(1：流量包2：购物卡，3:普通商品)
     * column convert_type
     *
     * @MLTH_generated
     */
    private Integer convert_type;

    /**
     *   商品图片
     * column product_img
     *
     * @MLTH_generated
     */
    private String product_img;

    /**
     *   所需积分
     * column point
     *
     * @MLTH_generated
     */
    private Integer point;

    /**
     *   数量
     * column product_num
     *
     * @MLTH_generated
     */
    private Integer product_num;

    /**
     *   兑换开始日期
     * column start_time
     *
     * @MLTH_generated
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date start_time;

    /**
     *   兑换结束日期
     * column end_time
     *
     * @MLTH_generated
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date end_time;

    /**
     *   价值
     * column price
     *
     * @MLTH_generated
     */
    private Double price;

    /**
     *   有效期
     * column valid_time
     *
     * @MLTH_generated
     */
    private Integer valid_time;

    /**
     *   有效期单位（year：年，month：月，day：日）
     * column valid_unit
     *
     * @MLTH_generated
     */
    private String valid_unit;

    /**
     *   合伙人
     * column partner_ids
     *
     * @MLTH_generated
     */
    private String partner_ids;

    /**
     *   品牌商
     * column brand_ids
     *
     * @MLTH_generated
     */
    private String brand_ids;

    /**
     *   店铺
     * column shop_ids
     *
     * @MLTH_generated
     */
    private String shop_ids;
    
    
    private Integer state=1;

    public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	/**
     *   产品说明
     * column description
     *
     * @MLTH_generated
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_biz_point_mall
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
     *   product_title
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getProduct_title() {
        return product_title;
    }

    /**
     *   product_title
     * @param product_title the value for 
     *
     * @MLTH_generated
     */
    public void setProduct_title(String product_title) {
        this.product_title = product_title == null ? null : product_title.trim();
    }

    /**
     *   兑换方式(1：流量包2：购物卡，3:普通商品)
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getConvert_type() {
        return convert_type;
    }

    /**
     *   兑换方式(1：流量包2：购物卡，3:普通商品)
     * @param convert_type the value for 
     *
     * @MLTH_generated
     */
    public void setConvert_type(Integer convert_type) {
        this.convert_type = convert_type;
    }

    /**
     *   商品图片
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getProduct_img() {
        return product_img;
    }

    /**
     *   商品图片
     * @param product_img the value for 
     *
     * @MLTH_generated
     */
    public void setProduct_img(String product_img) {
        this.product_img = product_img == null ? null : product_img.trim();
    }

    /**
     *   所需积分
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getPoint() {
        return point;
    }

    /**
     *   所需积分
     * @param point the value for 
     *
     * @MLTH_generated
     */
    public void setPoint(Integer point) {
        this.point = point;
    }

    /**
     *   数量
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getProduct_num() {
        return product_num;
    }

    /**
     *   数量
     * @param product_num the value for 
     *
     * @MLTH_generated
     */
    public void setProduct_num(Integer product_num) {
        this.product_num = product_num;
    }

    /**
     *   兑换开始日期
     * @return the value of TIMESTAMP
     *
     * @MLTH_generated
     */
    public Date getStart_time() {
        return start_time;
    }

    /**
     *   兑换开始日期
     * @param start_time the value for 
     *
     * @MLTH_generated
     */
    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    /**
     *   兑换结束日期
     * @return the value of TIMESTAMP
     *
     * @MLTH_generated
     */
    public Date getEnd_time() {
        return end_time;
    }

    /**
     *   兑换结束日期
     * @param end_time the value for 
     *
     * @MLTH_generated
     */
    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    /**
     *   价值
     * @return the value of DOUBLE
     *
     * @MLTH_generated
     */
    public Double getPrice() {
        return price;
    }

    /**
     *   价值
     * @param price the value for 
     *
     * @MLTH_generated
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     *   有效期
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getValid_time() {
        return valid_time;
    }

    /**
     *   有效期
     * @param valid_time the value for 
     *
     * @MLTH_generated
     */
    public void setValid_time(Integer valid_time) {
        this.valid_time = valid_time;
    }

    /**
     *   有效期单位（year：年，month：月，day：日）
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public String getValid_unit() {
        return valid_unit;
    }

    /*   有效期单位（year：年，month：月，day：日）
     * @param valid_unit the value for 
     *
     * @MLTH_generated
     */
    public void setValid_unit(String valid_unit) {
        this.valid_unit = valid_unit;
    }


	/**
     *   合伙人
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public String getPartner_ids() {
        return partner_ids;
    }

    /**
     *   合伙人
     * @param partner_ids the value for 
     *
     * @MLTH_generated
     */
    public void setPartner_ids(String partner_ids) {
        this.partner_ids = partner_ids;
    }

    /**
     *   品牌商
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public String getBrand_ids() {
        return brand_ids;
    }

    /**
     *   品牌商
     * @param brand_ids the value for 
     *
     * @MLTH_generated
     */
    public void setBrand_ids(String brand_ids) {
        this.brand_ids = brand_ids;
    }

    /**
     *   店铺
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public String getShop_ids() {
        return shop_ids;
    }

    /**
     *   店铺
     * @param shop_ids the value for 
     *
     * @MLTH_generated
     */
    public void setShop_ids(String shop_ids) {
        this.shop_ids = shop_ids;
    }

    /**
     *   产品说明
     * @return the value of LONGVARCHAR
     *
     * @MLTH_generated
     */
    public String getDescription() {
        return description;
    }

    /**
     *   产品说明
     * @param description the value for 
     *
     * @MLTH_generated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", product_title=").append(product_title);
        sb.append(", convert_type=").append(convert_type);
        sb.append(", product_img=").append(product_img);
        sb.append(", point=").append(point);
        sb.append(", product_num=").append(product_num);
        sb.append(", start_time=").append(start_time);
        sb.append(", end_time=").append(end_time);
        sb.append(", price=").append(price);
        sb.append(", valid_time=").append(valid_time);
        sb.append(", valid_unit=").append(valid_unit);
        sb.append(", partner_ids=").append(partner_ids);
        sb.append(", brand_ids=").append(brand_ids);
        sb.append(", shop_ids=").append(shop_ids);
        sb.append(", description=").append(description);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}