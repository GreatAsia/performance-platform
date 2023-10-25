package com.okay.testcenter.impl.user;


import com.okay.testcenter.domain.bean.Menu;
import com.okay.testcenter.mapper.user.MenuMapper;
import com.okay.testcenter.service.user.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author XieYangYang
 * @date 2019/11/22 10:01
 */
@Service("MenuService")
public class MenuServiceImpl implements MenuService {

    @Resource
    MenuMapper menuMapper;

    @Override
    public List<Menu> getMenuByMenu(Menu menu) {
        List<Menu> menus = menuMapper.selectByMenu(menu);
        return menus;
    }


    /**
     * 根据roleId获取Menu信息
     *
     * @param roleId 角色ID
     * @return 菜单
     */
    @Override
    public List<Menu> getMenusByRoleId(int roleId) {
        return menuMapper.selectByMenuNotDeleteByRole(roleId);
    }

    @Override
    public List<Menu> getAllMenusByRoleId(int roleId) {
        return menuMapper.selectByMenuNotDeleteByRole(roleId);
    }


    /**
     * 查找菜单
     *
     * @param menus
     * @return allOrderMenus map的id为父菜单的id
     */
    private Map<Integer, List<Menu>> getOrderMenus(List<Menu> menus) {
        Map<Integer, List<Menu>> allOrderMenus = new LinkedHashMap<>();
        //存放最外层id
        Set<Integer> menuNodeIds = new LinkedHashSet<>();
        for (Menu menu : menus) {
            if (menu.getIsMenu().equals("0")) {
                menuNodeIds.add(menu.getId());
            }
        }
        //整理url
        for (int menuIndex : menuNodeIds) {
            List<Menu> newMenus = new ArrayList<>();
            newMenus = getOrderMenu(menuIndex, menus, newMenus);
            allOrderMenus.put(menuIndex, newMenus);
        }
        return allOrderMenus;
    }

    /**
     * 递归查找菜单
     *
     * @param id
     * @param menus
     * @param newMenus
     * @return
     */
    public List<Menu> getOrderMenu(int id, List<Menu> menus, List<Menu> newMenus) {
        for (Menu menu : menus) {
            if (menu.getId() == id) {
                newMenus.add(menu);
            } else if (Long.valueOf(menu.getParentId()) == id) {
                newMenus.add(menu);
                getOrderMenu(menu.getId(), menus, newMenus);
            }
        }
        return newMenus;
    }
}
