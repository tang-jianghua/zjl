package com.mfangsoft.zhuangjialong.integration.region.model;

import java.io.Serializable;

/**
 *   区域编码表; InnoDB free: 11264 kB
 * table region
 *
 * @MLTH_generated do_not_delete_during_merge
 */
public class RegionEntity implements Serializable {
    /**
     *   区域编码
     * column code
     *
     * @MLTH_generated
     */
    private String code;

    /**
     *   区域名称
     * column name
     *
     * @MLTH_generated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table region
     *
     * @MLTH_generated
     */
    private static final long serialVersionUID = 1L;

    /**
     *   区域编码
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getCode() {
        return code;
    }

    /**
     *   区域编码
     * @param code the value for 
     *
     * @MLTH_generated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     *   区域名称
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getName() {
        return name;
    }

    /**
     *   区域名称
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
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}