package com.happyhouse.vue.model.service;

import java.util.List;
import com.happyhouse.vue.model.FileInfoDto;
import com.happyhouse.vue.model.UserDto;
import com.happyhouse.vue.model.UserInterestDto;

public interface UserService {
    
    int idCheck(String id) throws Exception;

    boolean registerMember(UserDto userDto) throws Exception;

    UserDto login(UserDto userDto) throws Exception;

    UserDto mypageUser(String id) throws Exception;

    boolean updateUser(UserDto userDto) throws Exception;

    boolean removeUser(String id) throws Exception;

    UserDto mypageDong(UserDto userDto) throws Exception;

    List<UserInterestDto> getInterest(UserDto userDto) throws Exception;

    UserDto getUserPwd(UserDto userDto) throws Exception;
    
    boolean uploadAuthFile(FileInfoDto fileInfoDto) throws Exception;
    
}
