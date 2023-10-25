package com.okay.testcenter.common.convert;


import com.okay.testcenter.domain.bean.User;
import com.okay.testcenter.domain.user.UserRequest;
import com.okay.testcenter.domain.user.UserResponse;

/**
 * @author XieYangYang
 * @date 2019/11/21 16:31
 */
public class UserConvert {

    public static User userReqToUser(UserRequest userRequest) {
        User user = new User();
        user.setUserName(userRequest.getUserName());
        user.setUserEmail(userRequest.getUserEmail());
        user.setUserPassword(userRequest.getPassword());
        return user;
    }


    public static UserResponse userToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUserName(user.getUserName());
        userResponse.setUserEmail(user.getUserEmail());
        userResponse.setUserPassword(user.getUserPassword());
        userResponse.setPosition(user.getPosition());
        userResponse.setRoleId(user.getRoleId());
        userResponse.setSex(user.getSex());
        return userResponse;
    }
}
