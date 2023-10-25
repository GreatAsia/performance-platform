package com.okay.testcenter.domain.user;


import com.okay.testcenter.domain.bean.Menu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * @author XieYangYang
 * @date 2019/11/21 16:48
 */
@Getter
@Setter
public class RoleResponse {
    private Integer id;

    private String roleName;

    private String roleCode;

    private List<Menu> menus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
