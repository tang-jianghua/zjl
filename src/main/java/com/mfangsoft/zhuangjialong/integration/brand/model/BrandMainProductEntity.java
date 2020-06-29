package com.mfangsoft.zhuangjialong.integration.brand.model;

import java.io.Serializable;

/**
 *   品牌首页产品表
 * table t_biz_brand_main_product
 *
 * @MLTH_generated do_not_delete_during_merge
 */
public class BrandMainProductEntity implements Serializable {
    /**
     *   主键id
     * column id
     *
     * @MLTH_generated
     */
    private Long id;

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
     *   排序号
     * column sort
     *
     * @MLTH_generated
     */
    private Long sort;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_biz_brand_main_product
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
     *   排序号
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getSort() {
        return sort;
    }

    /**
     *   排序号
     * @param sort the value for 
     *
     * @MLTH_generated
     */
    public void setSort(Long sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", product_id=").append(product_id);
        sb.append(", brand_id=").append(brand_id);
        sb.append(", sort=").append(sort);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}