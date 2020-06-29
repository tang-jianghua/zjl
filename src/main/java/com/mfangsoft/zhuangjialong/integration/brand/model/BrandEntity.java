package com.mfangsoft.zhuangjialong.integration.brand.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.mfangsoft.zhuangjialong.integration.bank.model.BankEntity;
import com.mfangsoft.zhuangjialong.integration.commerce.model.CommerceEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildEnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.EnterpriseEntity;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

/**
 *   品牌表
 * table t_biz_brand
 *
 * @MLTH_generated do_not_delete_during_merge
 */
@JsonInclude(value=Include.NON_NULL)
public class BrandEntity implements Serializable {
    /**
     *   品牌id
     * column id
     *
     * @MLTH_generated
     */
    private Long id;

    /**
     *   企业id
     * column enterprise_id
     *
     * @MLTH_generated
     */
    private Long enterprise_id;

    /**
     *   负责人
     * column principal
     *
     * @MLTH_generated
     */
    private String principal;

    /**
     *   联系电话
     * column phone_num
     *
     * @MLTH_generated
     */
    private String phone_num;

    /**
     *   城市合伙人id
     * column citypartner_id
     *
     * @MLTH_generated
     */
    private Long citypartner_id;

    /**
     *   工商信息ID
     * column commerce_id
     *
     * @MLTH_generated
     */
    private Long commerce_id;

    /**
     *   银行信息id
     * column band_info_id
     *
     * @MLTH_generated
     */
    private Long band_info_id;

    public PartnerEntity getPartnerEntity() {
		return partnerEntity;
	}


	public void setPartnerEntity(PartnerEntity partnerEntity) {
		this.partnerEntity = partnerEntity;
	}


	
	
	

	/**
     *   录入人
     * column creater
     *
     * @MLTH_generated
     */
    private String creater;
    
    
    private PartnerEntity partnerEntity;

    
    /**
     * 代理品牌
     */
    private BuildEnterpriseEntity buildEnterpriseEntity;
    public BuildEnterpriseEntity getBuildEnterpriseEntity() {
		return buildEnterpriseEntity;
	}


	public void setBuildEnterpriseEntity(BuildEnterpriseEntity buildEnterpriseEntity) {
		this.buildEnterpriseEntity = buildEnterpriseEntity;
	}



	/**
     *   录入时间
     * column create_time
     *
     * @MLTH_generated
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:MM:ss", timezone="GMT+8")
    private Date create_time;
    
    private String region_name;
    
    private String enterprise_name;
    
    private String brand_name;
    
    
    public String getBrand_name() {
		return brand_name;
	}
    
    
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getEnterprise_name() {
		return enterprise_name;
	}

	public void setEnterprise_name(String enterprise_name) {
		this.enterprise_name = enterprise_name;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	private String partner_principal;

    public String getPartner_principal() {
		return partner_principal;
	}

	public void setPartner_principal(String partner_principal) {
		this.partner_principal = partner_principal;
	}

	/**
     *   修改人
     * column updater
     *
     * @MLTH_generated
     */
    private String updater;

    /**
     *   修改时间
     * column update_time
     *
     * @MLTH_generated
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:MM:ss", timezone="GMT+8")
    private Date update_time;

    /**
     *   1：审核中，0：审核通过
     * column state
     *
     * @MLTH_generated
     */
    private Integer state;

    /**
     *   系统用户id
     * column sys_user_id
     *
     * @MLTH_generated
     */
    private Long sys_user_id;

    /**
     *   必须是城市合伙人子集
     * column region_code
     *
     * @MLTH_generated
     */
    private String region_code;
    
    
    private UserEntity userEntity;

    /**
     *   排序号
     * column sort
     *
     * @MLTH_generated
     */
    private Long sort;

    public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	/**
     *   品牌图片
     * column imgurl
     *
     * @MLTH_generated
     */
    private String imgurl;
    
    /**
     *   热线电话
     * column imgurl
     *
     * @MLTH_generated
     */
    private String hot_line;
    
    private Integer brand_idintify_type;
    
    private BankEntity  bankEntity;
    
    
    private CommerceEntity commerceEntity;
    

    public BankEntity getBankEntity() {
		return bankEntity;
	}

	public void setBankEntity(BankEntity bankEntity) {
		this.bankEntity = bankEntity;
	}

	public CommerceEntity getCommerceEntity() {
		return commerceEntity;
	}

	public void setCommerceEntity(CommerceEntity commerceEntity) {
		this.commerceEntity = commerceEntity;
	}

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_biz_brand
     *
     * @MLTH_generated
     */
    private static final long serialVersionUID = 1L;

    /**
     *   品牌id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getId() {
        return id;
    }

    /**
     *   品牌id
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
    public Long getEnterprise_id() {
        return enterprise_id;
    }

    /**
     *   企业id
     * @param enterprise_id the value for 
     *
     * @MLTH_generated
     */
    public void setEnterprise_id(Long enterprise_id) {
        this.enterprise_id = enterprise_id;
    }

    /**
     *   负责人
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getPrincipal() {
        return principal;
    }

    /**
     *   负责人
     * @param principal the value for 
     *
     * @MLTH_generated
     */
    public void setPrincipal(String principal) {
        this.principal = principal == null ? null : principal.trim();
    }

    /**
     *   联系电话
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getPhone_num() {
        return phone_num;
    }

    /**
     *   联系电话
     * @param phone_num the value for 
     *
     * @MLTH_generated
     */
    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num == null ? null : phone_num.trim();
    }

    /**
     *   城市合伙人id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getCitypartner_id() {
        return citypartner_id;
    }

    /**
     *   城市合伙人id
     * @param citypartner_id the value for 
     *
     * @MLTH_generated
     */
    public void setCitypartner_id(Long citypartner_id) {
        this.citypartner_id = citypartner_id;
    }

    /**
     *   工商信息ID
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getCommerce_id() {
        return commerce_id;
    }

    /**
     *   工商信息ID
     * @param commerce_id the value for 
     *
     * @MLTH_generated
     */
    public void setCommerce_id(Long commerce_id) {
        this.commerce_id = commerce_id;
    }

    /**
     *   银行信息id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getBand_info_id() {
        return band_info_id;
    }

    /**
     *   银行信息id
     * @param band_info_id the value for 
     *
     * @MLTH_generated
     */
    public void setBand_info_id(Long band_info_id) {
        this.band_info_id = band_info_id;
    }

    /**
     *   录入人
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getCreater() {
        return creater;
    }

    /**
     *   录入人
     * @param creater the value for 
     *
     * @MLTH_generated
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    /**
     *   录入时间
     * @return the value of TIMESTAMP
     *
     * @MLTH_generated
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     *   录入时间
     * @param create_time the value for 
     *
     * @MLTH_generated
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     *   修改人
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getUpdater() {
        return updater;
    }

    /**
     *   修改人
     * @param updater the value for 
     *
     * @MLTH_generated
     */
    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
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
     *   1：审核中，0：审核通过
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getState() {
        return state;
    }

    /**
     *   1：审核中，0：审核通过
     * @param state the value for 
     *
     * @MLTH_generated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     *   系统用户id
     * @return the value of BIGINT
     *
     * @MLTH_generated
     */
    public Long getSys_user_id() {
        return sys_user_id;
    }

    /**
     *   系统用户id
     * @param sys_user_id the value for 
     *
     * @MLTH_generated
     */
    public void setSys_user_id(Long sys_user_id) {
        this.sys_user_id = sys_user_id;
    }

    /**
     *   必须是城市合伙人子集
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getRegion_code() {
        return region_code;
    }

    /**
     *   必须是城市合伙人子集
     * @param region_code the value for 
     *
     * @MLTH_generated
     */
    public void setRegion_code(String region_code) {
        this.region_code = region_code == null ? null : region_code.trim();
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

    /**
     *   品牌图片
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getImgurl() {
        return imgurl;
    }

    /**
     *   品牌图片
     * @param imgurl the value for 
     *
     * @MLTH_generated
     */
    
    
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    /**
	 * @return the hot_line
	 */
	public String getHot_line() {
		return hot_line;
	}

	/**
	 * @param hot_line the hot_line to set
	 */
	public void setHot_line(String hot_line) {
		this.hot_line = hot_line;
	}

	public Integer getBrand_idintify_type() {
		return brand_idintify_type;
	}


	public void setBrand_idintify_type(Integer brand_idintify_type) {
		this.brand_idintify_type = brand_idintify_type;
	}


	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", enterprise_id=").append(enterprise_id);
        sb.append(", principal=").append(principal);
        sb.append(", phone_num=").append(phone_num);
        sb.append(", citypartner_id=").append(citypartner_id);
        sb.append(", commerce_id=").append(commerce_id);
        sb.append(", band_info_id=").append(band_info_id);
        sb.append(", creater=").append(creater);
        sb.append(", create_time=").append(create_time);
        sb.append(", updater=").append(updater);
        sb.append(", update_time=").append(update_time);
        sb.append(", state=").append(state);
        sb.append(", sys_user_id=").append(sys_user_id);
        sb.append(", region_code=").append(region_code);
        sb.append(", sort=").append(sort);
        sb.append(", imgurl=").append(imgurl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}