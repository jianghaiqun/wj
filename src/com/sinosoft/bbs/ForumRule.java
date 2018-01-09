package com.sinosoft.bbs;

import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.schema.ZCForumSchema;

public class ForumRule {
	static final String NO = "N";
	static final String YES = "Y";
	Mapx map = new Mapx();
	
	public ForumRule(long ForumID){
		initRule(ForumID);
	}
	public ForumRule(String ForumID){
		this(Long.parseLong(ForumID));
	}

	/**
	 * 得到论坛的板块设置
	 * @param RuleType
	 * @return
	 */
	public boolean getRule(String ruleType){
		return map.get(ruleType).equals("Y");
	}
	
	private void initRule(long ForumID){
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID(ForumID);
		forum.fill();
		initMap(forum);
	}
	
	private void initMap(ZCForumSchema forum){
		map.put("AllowTheme",forum.getAllowTheme());
		map.put("EditPost", forum.getEditPost());
		map.put("ReplyPost",forum.getReplyPost());
		map.put("Locked", forum.getLocked());
		map.put("AllowFace", forum.getAllowFace());		
	}
}
