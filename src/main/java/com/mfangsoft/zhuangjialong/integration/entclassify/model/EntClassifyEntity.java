package com.mfangsoft.zhuangjialong.integration.entclassify.model;

import java.io.Serializable;

/**
 *   企业分类表
 * table t_biz_ent_classify
 *
 * @MLTH_generated do_not_delete_during_merge
 */
public class EntClassifyEntity implements Serializable {
    /**
     * column id
     *
     * @MLTH_generated
     */
    private Long id;

    /**
     *   企业id
     * column ent_id
     *
     * @MLTH_generated
     */
    private Long ent_id;

    /**
     *   分类名称
     * column name
     *
     * @MLTH_generated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_biz_ent_classify
     *
     * @MLTH_generated
     */
    private static final long serialVersionUID = 1L;
    
    
    private Integer sort;

    public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

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
     *   企业id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getEnt_id() {
        return ent_id;
    }

    /**
     *   企业id
     * @param ent_id the value for 
     *
     * @MLTH_generated
     */
    public void setEnt_id(Long ent_id) {
        this.ent_id = ent_id;
    }

    /**
     *   分类名称
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getName() {
        return name;
    }

    /**
     *   分类名称
     * @param name the value for 
     *
     * @MLTH_generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ent_id=").append(ent_id);
        sb.append(", name=").append(name);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}