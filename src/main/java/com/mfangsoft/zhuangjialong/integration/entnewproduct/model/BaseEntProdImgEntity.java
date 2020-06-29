package com.mfangsoft.zhuangjialong.integration.entnewproduct.model;

import java.io.Serializable;

/**
 *   企业产品图片表
 * table t_biz_ent_prod_img
 *
 * @MLTH_generated do_not_delete_during_merge
 */
public class BaseEntProdImgEntity implements Serializable {
    /**
     * column id
     *
     * @MLTH_generated
     */
    private Long id;

    /**
     * column imgurl
     *
     * @MLTH_generated
     */
    private String imgurl;

    /**
     * column product_id
     *
     * @MLTH_generated
     */
    private Long product_id;

    /**
     * column img_sort
     *
     * @MLTH_generated
     */
    private Integer img_sort;

    /**
     * column type
     *
     * @MLTH_generated
     */
    private Integer type;

    /**
     * column impFlag
     *
     * @MLTH_generated
     */
    private String impFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_biz_ent_prod_img
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
    public String getImgurl() {
        return imgurl;
    }

    /**
     * @param imgurl the value for 
     *
     * @MLTH_generated
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    /**
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getProduct_id() {
        return product_id;
    }

    /**
     * @param product_id the value for 
     *
     * @MLTH_generated
     */
    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    /**
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getImg_sort() {
        return img_sort;
    }

    /**
     * @param img_sort the value for 
     *
     * @MLTH_generated
     */
    public void setImg_sort(Integer img_sort) {
        this.img_sort = img_sort;
    }

    /**
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type the value for 
     *
     * @MLTH_generated
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getImpFlag() {
        return impFlag;
    }

    /**
     * @param impFlag the value for 
     *
     * @MLTH_generated
     */
    public void setImpFlag(String impFlag) {
        this.impFlag = impFlag == null ? null : impFlag.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", imgurl=").append(imgurl);
        sb.append(", product_id=").append(product_id);
        sb.append(", img_sort=").append(img_sort);
        sb.append(", type=").append(type);
        sb.append(", impFlag=").append(impFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}