package com.mfangsoft.zhuangjialong.integration.role.model;

import java.io.Serializable;

/**
 * table t_sys_role_position
 *
 * @MLTH_generated do_not_delete_during_merge
 */
public class RolePositionEntity implements Serializable {
    /**
     * column id
     *
     * @MLTH_generated
     */
    private Long id;

    /**
     *   岗位id
     * column position_id
     *
     * @MLTH_generated
     */
    private Long position_id;

    /**
     *   角色id
     * column role_id
     *
     * @MLTH_generated
     */
    private Long role_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_sys_role_position
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
     *   岗位id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getPosition_id() {
        return position_id;
    }

    /**
     *   岗位id
     * @param position_id the value for 
     *
     * @MLTH_generated
     */
    public void setPosition_id(Long position_id) {
        this.position_id = position_id;
    }

    /**
     *   角色id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getRole_id() {
        return role_id;
    }

    /**
     *   角色id
     * @param role_id the value for 
     *
     * @MLTH_generated
     */
    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", position_id=").append(position_id);
        sb.append(", role_id=").append(role_id);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}