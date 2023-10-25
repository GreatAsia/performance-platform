package com.okay.testcenter.service.user;


import com.okay.testcenter.domain.bean.Role;
import com.okay.testcenter.domain.user.RoleResponse;

import java.util.List;

/**
 * @author XieYangYang
 * @date 2019/11/21 15:14
 */


public interface RoleService {
    public RoleResponse getRoleByRoleSingle(Role role);

    public List<Role> getAllRole(Role role);


    public Role getAllRoleSingle(Role role);
}
