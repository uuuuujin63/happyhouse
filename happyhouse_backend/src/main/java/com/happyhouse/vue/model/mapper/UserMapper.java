package com.happyhouse.vue.model.mapper;

import java.util.List;
import com.happyhouse.vue.model.FileInfoDto;
import com.happyhouse.vue.model.UserDto;
import com.happyhouse.vue.model.UserInterestDto;

public interface UserMapper {
    
    int idCheck(String id) throws Exception;

    UserDto login(UserDto userDto) throws Exception;

    int registerMember(UserDto userDto) throws Exception;

    int updateUser(UserDto userDto) throws Exception;

    int removeUser(String id) throws Exception;

    int removeAdmin(String id) throws Exception;
    int removeNotice(String id) throws Exception;
    int removeQanda(String id) throws Exception;
    int removeInUser(String id) throws Exception;
    int removeCommunity(String id) throws Exception;
    int removeReply(String id) throws Exception;

    UserDto mypageUser(String id) throws Exception;

    UserDto mypageDong(UserDto userDto) throws Exception;

    List<UserInterestDto> getInterest(UserDto userDto) throws Exception;

    UserDto getUserPwd(UserDto userDto) throws Exception;

    int uploadAuthFile(FileInfoDto fileInfoDto) throws Exception;
    
}
