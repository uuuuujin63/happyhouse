package com.happyhouse.vue.model.service;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.happyhouse.vue.model.FileInfoDto;
import com.happyhouse.vue.model.UserDto;
import com.happyhouse.vue.model.UserInterestDto;
import com.happyhouse.vue.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public int idCheck(String id) throws Exception {
        return sqlSession.getMapper(UserMapper.class).idCheck(id);
    }

    @Override
    public UserDto login(UserDto userDto) throws Exception {
        return sqlSession.getMapper(UserMapper.class).login(userDto);
    }

    @Override
    public boolean registerMember(UserDto userDto) throws Exception {
        return sqlSession.getMapper(UserMapper.class).registerMember(userDto) == 1;
    }

    @Override
    public boolean updateUser(UserDto userDto) throws Exception {
        return sqlSession.getMapper(UserMapper.class).updateUser(userDto) == 1;
    }

    @Override
    public boolean removeUser(String id) throws Exception { 
    	sqlSession.getMapper(UserMapper.class).removeInUser(id);
    	sqlSession.getMapper(UserMapper.class).removeAdmin(id);
    	sqlSession.getMapper(UserMapper.class).removeNotice(id);
    	sqlSession.getMapper(UserMapper.class).removeQanda(id);
    	sqlSession.getMapper(UserMapper.class).removeCommunity(id);
    	sqlSession.getMapper(UserMapper.class).removeReply(id);
        return sqlSession.getMapper(UserMapper.class).removeUser(id) == 1;
    }

    @Override
    public UserDto mypageUser(String id) throws Exception {
        return sqlSession.getMapper(UserMapper.class).mypageUser(id);
    }

    @Override
    public UserDto mypageDong(UserDto userDto) throws Exception {
        return sqlSession.getMapper(UserMapper.class).mypageDong(userDto);
    }

    @Override
    public List<UserInterestDto> getInterest(UserDto userDto) throws Exception {
        return sqlSession.getMapper(UserMapper.class).getInterest(userDto);
    }

	@Override
	public UserDto getUserPwd(UserDto userDto) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(UserMapper.class).getUserPwd(userDto);
		 
	}

    @Override
    public boolean uploadAuthFile(FileInfoDto fileInfoDto) throws Exception {
        return sqlSession.getMapper(UserMapper.class).uploadAuthFile(fileInfoDto) == 1;
    }

}
