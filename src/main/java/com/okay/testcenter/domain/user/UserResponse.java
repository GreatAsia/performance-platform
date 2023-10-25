package com.okay.testcenter.domain.user;

import lombok.Getter;
import lombok.Setter;

/**
 * @author XieYangYang
 * @date 2019/11/21 11:01
 */
@Getter
@Setter
public class UserResponse {
    private Integer id;

    private String userName;

    private String userEmail;

    private String userPassword;

    private String position;

    private Integer roleId;

    private String sex;

    private RoleResponse roleResponse;
}
