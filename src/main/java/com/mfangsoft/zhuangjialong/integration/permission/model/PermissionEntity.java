package com.mfangsoft.zhuangjialong.integration.permission.model;

import java.io.Serializable;

/**
 * table t_sys_permission
 *
 * @MLTH_generated do_not_delete_during_merge
 */
public class PermissionEntity implements Serializable {
    /**
     * column id
     *
     * @MLTH_generated
     */
    private Long id;

    /**
     *   权限名称
     * column name
     *
     * @MLTH_generated
     */
    private String name;

    /**
     *   权限地址
     * column permission_url
     *
     * @MLTH_generated
     */
    private String url;

    /**
     *   父节点
     * column praent_id
     *
     * @MLTH_generated
     */
    private Long parent_id;

    /**
     *   图标url
     * column icon_url
     *
     * @MLTH_generated
     */
    private String icon_url;

    /**
     *   界面层的id（按钮，菜单...）
     * column ui_id
     *
     * @MLTH_generated
     */
    private Long ui_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_sys_permission
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
     *   图标url
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public String getIcon_url() {
        return icon_url;
    }

    /**
     *   图标url
     * @param icon_url the value for 
     *
     * @MLTH_generated
     */
    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    /**
     *   界面层的id（按钮，菜单...）
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getUi_id() {
        return ui_id;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	/**
     *   界面层的id（按钮，菜单...）
     * @param ui_id the value for 
     *
     * @MLTH_generated
     */
    public void setUi_id(Long ui_id) {
        this.ui_id = ui_id;
    }

   
}