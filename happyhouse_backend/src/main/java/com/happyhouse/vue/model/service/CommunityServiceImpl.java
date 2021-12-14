package com.happyhouse.vue.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happyhouse.vue.model.CommunityDto;
import com.happyhouse.vue.model.CommunityReplyDto;
import com.happyhouse.vue.model.mapper.CommunityMapper;

@Service
public class CommunityServiceImpl implements CommunityService {

	@Autowired
    private SqlSession sqlSession;
	
	@Override
	public List<CommunityDto> listCommunity(String key, String addr) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key",key);
		map.put("addr",addr);
		return sqlSession.getMapper(CommunityMapper.class).listCommunity(map);
	}
	
	@Override
	public CommunityDto getCommunity(String idx) throws Exception {
		return sqlSession.getMapper(CommunityMapper.class).getCommunity(idx);
	}

	@Override
	public boolean updateCommunity(CommunityDto communityDto) throws Exception {
		return sqlSession.getMapper(CommunityMapper.class).updateCommunity(communityDto)==1;
	}

	@Override
	public boolean removeCommunity(CommunityDto communityDto) throws Exception {
		return sqlSession.getMapper(CommunityMapper.class).removeCommunity(communityDto)==1;
	}

	@Override
	public boolean registerCommunity(CommunityDto communityDto) throws Exception {
		return sqlSession.getMapper(CommunityMapper.class).registerCommunity(communityDto)==1;
	}

	@Override
	public String getDongName(String dongCode) throws Exception {
		return sqlSession.getMapper(CommunityMapper.class).getDongName(dongCode);
	}

	@Override
	public boolean registerReply(CommunityReplyDto communityReplyDto) throws Exception {
		return sqlSession.getMapper(CommunityMapper.class).registerReply(communityReplyDto);
	}

	@Override
	public boolean removeReply(int no) throws Exception {
		return sqlSession.getMapper(CommunityMapper.class).removeReply(no)==1;
	}


	

}
