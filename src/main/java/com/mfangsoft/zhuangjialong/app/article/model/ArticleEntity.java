package com.mfangsoft.zhuangjialong.app.article.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *   文章列表
 * table t_biz_article
 *
 * @MLTH_generated do_not_delete_during_merge
 */

@JsonInclude(value=Include.NON_NULL)
public class ArticleEntity implements Serializable {
    /**
     *   主键id
     * column id
     *
     * @MLTH_generated
     */
    private Long id;

    /**
     *   图片
     * column image
     *
     * @MLTH_generated
     */
    private String image;

    /**
     *   文章标题
     * column title
     *
     * @MLTH_generated
     */
    private String title;

    /**
     *   创建日期
     * column create_time
     *
     * @MLTH_generated
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date create_time;

    /**
     *   作者
     * column author
     *
     * @MLTH_generated
     */
    private String author;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date update_time;
    

    public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	/**
     *   文章概要
     * column digest
     *
     * @MLTH_generated
     */
    private String digest;

    /**
     *   状态(0 过期 1 可用）
     * column state
     *
     * @MLTH_generated
     */
    private Integer state;

    /**
     *   浏览量
     * column seen_time
     *
     * @MLTH_generated
     */
    private Integer seen_time;
    /**
     *   分享量
     * column seen_time
     *
     * @MLTH_generated
     */
    private Integer share_num;

    /**
     *   文章详情
     * column details
     *
     * @MLTH_generated
     */
    private String details;
    
    private String abum_name;

    public String getAbum_name() {
		return abum_name;
	}

	public void setAbum_name(String abum_name) {
		this.abum_name = abum_name;
	}

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_biz_article
     *
     * @MLTH_generated
     */
    private Integer type;
    
    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Long abum_id;
	
	

	public Long getAbum_id() {
		return abum_id;
	}

	public void setAbum_id(Long abum_id) {
		this.abum_id = abum_id;
	}

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
     *   图片
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getImage() {
        return image;
    }

    /**
     *   图片
     * @param image the value for 
     *
     * @MLTH_generated
     */
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    /**
     *   文章标题
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getTitle() {
        return title;
    }

    /**
     *   文章标题
     * @param title the value for 
     *
     * @MLTH_generated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     *   创建日期
     * @return the value of TIMESTAMP
     *
     * @MLTH_generated
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     *   创建日期
     * @param create_time the value for 
     *
     * @MLTH_generated
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     *   作者
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getAuthor() {
        return author;
    }

    /**
     *   作者
     * @param author the value for 
     *
     * @MLTH_generated
     */
    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    /**
     *   文章概要
     * @return the value of VARCHAR
     *
     * @MLTH_generated
     */
    public String getDigest() {
        return digest;
    }

    /**
     *   文章概要
     * @param digest the value for 
     *
     * @MLTH_generated
     */
    public void setDigest(String digest) {
        this.digest = digest == null ? null : digest.trim();
    }

    /**
     *   状态(0 过期 1 可用）
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getState() {
        return state;
    }

    /**
     *   状态(0 过期 1 可用）
     * @param state the value for 
     *
     * @MLTH_generated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     *   浏览量
     * @return the value of INTEGER
     *
     * @MLTH_generated
     */
    public Integer getSeen_time() {
        return seen_time;
    }

    /**
     *   浏览量
     * @param seen_time the value for 
     *
     * @MLTH_generated
     */
    public void setSeen_time(Integer seen_time) {
        this.seen_time = seen_time;
    }
    

    /**
	 * @return the share_num
	 */
	public Integer getShare_num() {
		return share_num;
	}

	/**
	 * @param share_num the share_num to set
	 */
	public void setShare_num(Integer share_num) {
		this.share_num = share_num;
	}

	/**
     *   文章详情
     * @return the value of LONGVARCHAR
     *
     * @MLTH_generated
     */
    public String getDetails() {
        return details;
    }

    /**
     *   文章详情
     * @param details the value for 
     *
     * @MLTH_generated
     */
    public void setDetails(String details) {
        this.details = details == null ? null : details.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", image=").append(image);
        sb.append(", title=").append(title);
        sb.append(", create_time=").append(create_time);
        sb.append(", author=").append(author);
        sb.append(", digest=").append(digest);
        sb.append(", state=").append(state);
        sb.append(", seen_time=").append(seen_time);
        sb.append(", share_num=").append(share_num);
        sb.append(", details=").append(details);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}