package com.happyhouse.vue.model.service;

import java.util.List;

import com.happyhouse.vue.model.CommunityDto;
import com.happyhouse.vue.model.CommunityReplyDto;

public interface CommunityService {

	List<CommunityDto> listCommunity(String key, String addr) throws Exception;

	CommunityDto getCommunity(String idx) throws Exception;

	boolean updateCommunity(CommunityDto communityDto) throws Exception;

	boolean removeCommunity(CommunityDto communityDto) throws Exception;

	boolean registerCommunity(CommunityDto communityDto) throws Exception;
	
	String getDongName(String dongCode) throws Exception;

	boolean registerReply(CommunityReplyDto communityReplyDto) throws Exception;
	
	boolean removeReply(int no) throws Exception;

	
}
