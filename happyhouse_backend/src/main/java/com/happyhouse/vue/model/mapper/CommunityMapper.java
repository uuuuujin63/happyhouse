package com.happyhouse.vue.model.mapper;

import java.util.List;
import java.util.Map;

import com.happyhouse.vue.model.CommunityDto;
import com.happyhouse.vue.model.CommunityReplyDto;

public interface CommunityMapper {

	List<CommunityDto> listCommunity(Map<String, String> map) throws Exception;

	CommunityDto getCommunity(String idx) throws Exception;

	int updateCommunity(CommunityDto communityDto) throws Exception;

	int removeCommunity(CommunityDto communityDto) throws Exception;

	int registerCommunity(CommunityDto communityDto) throws Exception;
	
	String getDongName(String dongCode) throws Exception;

	boolean registerReply(CommunityReplyDto communityReplyDto) throws Exception;

	int removeReply(int no) throws Exception;
	
}
