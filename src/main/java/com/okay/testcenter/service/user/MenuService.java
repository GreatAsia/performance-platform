package com.okay.testcenter.service.user;


import com.okay.testcenter.domain.bean.Menu;

import java.util.List;

/**
 * @author XieYangYang
 * @date 2019/11/21 15:15
 */

public interface MenuService {
    public List<Menu> getMenuByMenu(Menu menu);

    public List<Menu> getMenusByRoleId(int roleId);

    public List<Menu> getAllMenusByRoleId(int roleId);
}
