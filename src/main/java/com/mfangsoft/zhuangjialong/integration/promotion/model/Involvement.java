package com.mfangsoft.zhuangjialong.integration.promotion.model;
/**
 * 活动参与表
 * @author Administrator
 *
 */
public class Involvement {
	
	/**
	 * 参与方ID
	 */
	private Long Participants_id;
	
	/**
	 * 活动ID
	 */
	private Long Promotion_id;

	public Long getParticipants_id() {
		return Participants_id;
	}

	public void setParticipants_id(Long participants_id) {
		Participants_id = participants_id;
	}

	public Long getPromotion_id() {
		return Promotion_id;
	}

	public void setPromotion_id(Long promotion_id) {
		Promotion_id = promotion_id;
	}
	
	

}
