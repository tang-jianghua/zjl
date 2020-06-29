package com.mfangsoft.zhuangjialong.integration.comment.service;

import java.util.Map;

import com.mfangsoft.zhuangjialong.common.model.Page;

public interface CommentService {
	
	
	Page<Map<String,Object>> getCommentListForPage(Page<Map<String,Object>> page);

}
