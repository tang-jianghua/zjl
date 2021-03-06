package com.mfangsoft.zhuangjialong.integration.role.model;

import java.io.Serializable;

/**
 *   权限角色关系表
 * table t_sys_role_permission
 *
 * @MLTH_generated do_not_delete_during_merge
 */
public class RolePermissionEntity implements Serializable {
    /**
     * column id
     *
     * @MLTH_generated
     */
    private Long id;

    /**
     *   角色id
     * column role_id
     *
     * @MLTH_generated
     */
    private Long role_id;

    /**
     *   权限id
     * column permission_id
     *
     * @MLTH_generated
     */
    private Long permission_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_sys_role_permission
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

    /**
     *   权限id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getPermission_id() {
        return permission_id;
    }

    /**
     *   权限id
     * @param permission_id the value for 
     *
     * @MLTH_generated
     */
    public void setPermission_id(Long permission_id) {
        this.permission_id = permission_id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", role_id=").append(role_id);
        sb.append(", permission_id=").append(permission_id);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}