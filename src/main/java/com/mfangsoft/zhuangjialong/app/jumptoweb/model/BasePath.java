package com.mfangsoft.zhuangjialong.app.jumptoweb.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *   banner基本路径表
 * table t_biz_banner_basepath
 *
 * @MLTH_generated do_not_delete_during_merge
 */
@JsonInclude(value=Include.NON_NULL)
public class BasePath implements Serializable {
    /**
     *   主键id
     * column type
     *
     * @MLTH_generated
     */
    private Integer type;

    /**
     *   路径地址
     * column basepath
     *
     * @MLTH_generated
     */
    private String basepath;

    /**
     *   类型(1轻松选材，2体验中心，3产品甄选，4预约到家，5文章列表，6活动，7文章图文详情，8产品详情，9外部链接)
     * column name
     *
     * @MLTH_generated
     */
    private String name;

    /**
     *   分类(1:首页按钮)
     * column flag_type
     *
     * @MLTH_generated
     */
    private Integer flag_type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_biz_banner_basepath
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
    public Integer getType() {
        return type;
    }

    /**
     *   主键id
     * @param type the value for 
     *
     * @MLTH_generated
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     *   路径地址
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getBasepath() {
        return basepath;
    }

    /**
     *   路径地址
     * @param basepath the value for 
     *
     * @MLTH_generated
     */
    public void setBasepath(String basepath) {
        this.basepath = basepath == null ? null : basepath.trim();
    }

    /**
     *   类型(1轻松选材，2体验中心，3产品甄选，4预约到家，5文章列表，6活动，7文章图文详情，8产品详情，9外部链接)
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getName() {
        return name;
    }

    /**
     *   类型(1轻松选材，2体验中心，3产品甄选，4预约到家，5文章列表，6活动，7文章图文详情，8产品详情，9外部链接)
     * @param name the value for 
     *
     * @MLTH_generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     *   分类(1:首页按钮)
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getFlag_type() {
        return flag_type;
    }

    /**
     *   分类(1:首页按钮)
     * @param flag_type the value for 
     *
     * @MLTH_generated
     */
    public void setFlag_type(Integer flag_type) {
        this.flag_type = flag_type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", type=").append(type);
        sb.append(", basepath=").append(basepath);
        sb.append(", name=").append(name);
        sb.append(", flag_type=").append(flag_type);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}