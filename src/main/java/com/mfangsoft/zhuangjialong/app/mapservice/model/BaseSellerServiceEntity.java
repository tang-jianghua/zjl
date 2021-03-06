package com.mfangsoft.zhuangjialong.app.mapservice.model;

import java.io.Serializable;
import java.util.Date;

/**
 *   服务表
 * table t_biz_seller_service
 *
 * @MLTH_generated do_not_delete_during_merge
 */
public class BaseSellerServiceEntity implements Serializable {
    /**
     * column id
     *
     * @MLTH_generated
     */
    private Integer id;

    /**
     *   服务类型(1,产品2.施工)
     * column service_type
     *
     * @MLTH_generated
     */
    private Integer service_type;

    /**
     *   卖家id
     * column seller_id
     *
     * @MLTH_generated
     */
    private Long seller_id;

    /**
     *   产品品类
     * column class_id
     *
     * @MLTH_generated
     */
    private Long class_id;

    /**
     *   品牌id
     * column brand_id
     *
     * @MLTH_generated
     */
    private Long brand_id;

    /**
     *   店铺id
     * column shop_id
     *
     * @MLTH_generated
     */
    private Long shop_id;

    /**
     *   区域编码
     * column region_code
     *
     * @MLTH_generated
     */
    private String region_code;

    /**
     *   经纬度
     * column lbs
     *
     * @MLTH_generated
     */
    private String lbs;

    /**
     *   产品描述(最多200字)
     * column content
     *
     * @MLTH_generated
     */
    private String content;

    /**
     *   创建时间
     * column create_time
     *
     * @MLTH_generated
     */
    private Date create_time;

    /**
     *   修改时间
     * column update_time
     *
     * @MLTH_generated
     */
    private Date update_time;

    /**
     *   状态(1:显示2:不显示)
     * column state
     *
     * @MLTH_generated
     */
    private Integer state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_biz_seller_service
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
     *   服务类型(1,产品2.施工)
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getService_type() {
        return service_type;
    }

    /**
     *   服务类型(1,产品2.施工)
     * @param service_type the value for 
     *
     * @MLTH_generated
     */
    public void setService_type(Integer service_type) {
        this.service_type = service_type;
    }

    /**
     *   卖家id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getSeller_id() {
        return seller_id;
    }

    /**
     *   卖家id
     * @param seller_id the value for 
     *
     * @MLTH_generated
     */
    public void setSeller_id(Long seller_id) {
        this.seller_id = seller_id;
    }

    /**
     *   产品品类
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getClass_id() {
        return class_id;
    }

    /**
     *   产品品类
     * @param class_id the value for 
     *
     * @MLTH_generated
     */
    public void setClass_id(Long class_id) {
        this.class_id = class_id;
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
     *   店铺id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getShop_id() {
        return shop_id;
    }

    /**
     *   店铺id
     * @param shop_id the value for 
     *
     * @MLTH_generated
     */
    public void setShop_id(Long shop_id) {
        this.shop_id = shop_id;
    }

    /**
     *   区域编码
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getRegion_code() {
        return region_code;
    }

    /**
     *   区域编码
     * @param region_code the value for 
     *
     * @MLTH_generated
     */
    public void setRegion_code(String region_code) {
        this.region_code = region_code == null ? null : region_code.trim();
    }

    /**
     *   经纬度
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getLbs() {
        return lbs;
    }

    /**
     *   经纬度
     * @param lbs the value for 
     *
     * @MLTH_generated
     */
    public void setLbs(String lbs) {
        this.lbs = lbs == null ? null : lbs.trim();
    }

    /**
     *   产品描述(最多200字)
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getContent() {
        return content;
    }

    /**
     *   产品描述(最多200字)
     * @param content the value for 
     *
     * @MLTH_generated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    /**
     *   修改时间
     * @return the value of TIMESTAMP
     *
     * @MLTH_generated
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     *   修改时间
     * @param update_time the value for 
     *
     * @MLTH_generated
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     *   状态(1:显示2:不显示)
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getState() {
        return state;
    }

    /**
     *   状态(1:显示2:不显示)
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
        sb.append(", service_type=").append(service_type);
        sb.append(", seller_id=").append(seller_id);
        sb.append(", class_id=").append(class_id);
        sb.append(", brand_id=").append(brand_id);
        sb.append(", shop_id=").append(shop_id);
        sb.append(", region_code=").append(region_code);
        sb.append(", lbs=").append(lbs);
        sb.append(", content=").append(content);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_time=").append(update_time);
        sb.append(", state=").append(state);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}