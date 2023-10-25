package com.okay.testcenter.common.convert;

import com.okay.testcenter.domain.bean.Role;
import com.okay.testcenter.domain.user.RoleResponse;

/**
 * @author XieYangYang
 * @date 2019/11/22 15:15
 */
public class RoleConvert {
    public static RoleResponse roleToRoleResponse(Role role) {
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setId(role.getId());
        roleResponse.setRoleCode(role.getRoleCode());
        roleResponse.setRoleName(role.getRoleName());
        return roleResponse;
    }
}
