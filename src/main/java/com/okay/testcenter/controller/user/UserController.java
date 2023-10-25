package com.okay.testcenter.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.okay.testcenter.common.constant.Constant;
import com.okay.testcenter.domain.Page;
import com.okay.testcenter.domain.RetResponse;
import com.okay.testcenter.domain.RetResult;
import com.okay.testcenter.domain.bean.Menu;
import com.okay.testcenter.domain.bean.Role;
import com.okay.testcenter.domain.bean.User;
import com.okay.testcenter.domain.user.UserResponse;
import com.okay.testcenter.mapper.user.MenuMapper;
import com.okay.testcenter.mapper.user.RoleMapper;
import com.okay.testcenter.service.user.GroupService;
import com.okay.testcenter.service.user.RoleService;
import com.okay.testcenter.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @auth 谢扬扬
 * @date 2020/8/26 10:12
 */
@Controller
@RequestMapping("/perf/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    GroupService groupService;

    @Resource
    RoleMapper roleMapper;

    @Resource
    MenuMapper menuMapper;

    @GetMapping("/getUserByPage")
    public String getUserByPage(Model model, User user, Page page) {
        List<User> userList = userService.findUserByPage(user);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("users", userList);
        return "user/userList";
    }


    @ResponseBody
    @GetMapping("/getUser")
    public RetResult<Object> getUser(User user) {
        User userRes = userService.findUserSingle(user);
        return RetResponse.makeOKRsp(userRes);
    }


    @ResponseBody
    @PostMapping("/delUser")
    public RetResult<Object> deleteUser(User user, HttpSession session) {
        user.setIsDelete("1"); //设置为已删除
        UserResponse onlineUser = (UserResponse) session.getAttribute(Constant.ONLINE_USER);
        user.setUpdateAt(new Date());
        user.setUpdateBy(onlineUser.getUserName());
        Boolean isdel = userService.updateUser(user);
        if (isdel) {
            return RetResponse.makeOKRsp();
        } else {
            return RetResponse.makeErrRsp("删除失败");
        }
    }


    @ResponseBody
    @GetMapping("/getRole")
    public RetResult<Object> getRole(Role role) {
        List<Role> roles = roleService.getAllRole(role);
        return RetResponse.makeOKRsp(roles);
    }


    @ResponseBody
    @PostMapping("/updateUserRole")
    public RetResult<Object> updateUserRole(User user, HttpSession session) {
        UserResponse onlineUser = (UserResponse) session.getAttribute(Constant.ONLINE_USER);
        boolean success = false;
        if (user.getId() == 0) {
            user.setCreateBy(onlineUser.getUserName());
            success = userService.insertUser(user);
        } else {
            user.setUpdateAt(new Date());
            user.setUpdateBy(onlineUser.getUserName());
            success = userService.updateUser(user);
        }
        if (success) {
            return RetResponse.makeOKRsp();
        } else {
            return RetResponse.makeErrRsp("失败");
        }
    }


    //**role相关****


    @GetMapping("/getRoleByPage")
    public String getRoleByPage(Model model, Role role, Page page) {
        List<Role> roles = roleService.getAllRole(role);
        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("roles", roles);
        return "user/roleList";
    }


    @ResponseBody
    @GetMapping("/getRoleSingle")
    public RetResult<Object> getRoleSingle(Role role) {
        Role roleRes = roleService.getAllRoleSingle(role);
        return RetResponse.makeOKRsp(roleRes);
    }


    @ResponseBody
    @PostMapping("/updateRole")
    public RetResult<Object> updateRole(Role role, HttpSession session) {
        UserResponse onlineUser = (UserResponse) session.getAttribute(Constant.ONLINE_USER);
        int success = 0;
        if (role.getId() == null) {
            role.setCreateBy(onlineUser.getUserName());
            success = roleMapper.insertSelective(role);
        } else {
            role.setUpdateAt(new Date());
            role.setUpdateBy(onlineUser.getUserName());
            success = roleMapper.updateByPrimaryKeySelective(role);
        }
        if (success > 0) {
            return RetResponse.makeOKRsp();
        } else {
            return RetResponse.makeErrRsp("失败");
        }
    }


    @ResponseBody
    @GetMapping("/getAllMenus")
    public RetResult<Object> getAllMenus() {
        List<Menu> menus = menuMapper.selectByMenu(new Menu());
        return RetResponse.makeOKRsp(menus);
    }


    @ResponseBody
    @GetMapping("/getSingleMenuByMenu")
    public RetResult<Object> getAllMenuByMenu(Menu menu) {
        Menu menures = menuMapper.selectByMenuSingle(menu);
        return RetResponse.makeOKRsp(menures);
    }


    @ResponseBody
    @GetMapping("/getAllMenusByRoleId")
    public RetResult<Object> getAllMenusByRoleId(int id) {
        List<Menu> menus = menuMapper.selectByMenuByRole(id);
        return RetResponse.makeOKRsp(menus);
    }


    @GetMapping("/getMenuByPage")
    public String getMenuByPage(Model model, Menu menu, Page page) {
        List<Menu> menus = menuMapper.selectByMenu(menu);
        PageInfo<Menu> pageInfo = new PageInfo<>(menus);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("menus", menus);
        return "user/menuList";
    }


    @ResponseBody
    @PostMapping("/updateOrInsertMenu")
    public RetResult<Object> updateOrInsertMenu(Menu menu, HttpSession HttpSession) {
        int success = 0;
        UserResponse onlineUser = (UserResponse) HttpSession.getAttribute(Constant.ONLINE_USER);
        if (menu.getId() != null) {
            menu.setUpdateAt(new Date());
            menu.setUpdateBy(onlineUser.getUserName());
            success = menuMapper.updateByPrimaryKeySelective(menu);
        } else {
            menu.setCreateBy(onlineUser.getUserName());
            success = menuMapper.insertSelective(menu);
        }
        if (success > 0) {
            return RetResponse.makeOKRsp();
        } else {
            return RetResponse.makeErrRsp("失败");
        }
    }


    @ResponseBody
    @PostMapping("/updateOrInsertGroup")
    public RetResult<Object> getAllMenusByRoleId(@RequestBody JSONObject json, HttpSession httpSession) {
        int success = groupService.insertOrUpdateGroupArray(json, httpSession);
        if (success > 0) {
            return RetResponse.makeOKRsp();
        } else {
            return RetResponse.makeErrRsp("失败");
        }
    }


}
