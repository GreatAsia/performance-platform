package com.okay.testcenter.impl.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okay.testcenter.common.constant.Constant;
import com.okay.testcenter.domain.bean.Group;
import com.okay.testcenter.domain.user.GroupSimpleDetailResponse;
import com.okay.testcenter.domain.bean.Menu;
import com.okay.testcenter.domain.bean.Role;
import com.okay.testcenter.domain.user.UserResponse;
import com.okay.testcenter.mapper.user.GroupMapper;
import com.okay.testcenter.service.user.GroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @auth 谢扬扬
 * @date 2020/8/28 14:19
 */
@Service("GroupService")
public class GroupServiceImpl implements GroupService {

    @Resource
    GroupMapper groupMapper;

    @Override
    public Group getGroupByGroup(Group Group) {
        return null;
    }

    @Override
    public Map<Integer, List<Menu>> getGroupMenuByGroup(Group Group) {
        return null;
    }


    /**
     * 加载url对应的roles
     *
     * @param group group
     * @return map key为url value为
     */
    @Override
    public Map<String, String> getAllMenusAndRole(Group group) {
        // 查询所有groupSimpleDetail
        List<GroupSimpleDetailResponse> groupSimpleDetails = groupMapper.selectGroupSimpleDetailByGroup(group);
        Map<String, String> filterAllMenusByRoles = new HashMap<String, String>();
        for (GroupSimpleDetailResponse groupSimpleDetailResponse : groupSimpleDetails) {
            if (groupSimpleDetailResponse.getMenuUrl() == null) {
                continue;
            }
            if (filterAllMenusByRoles == null || filterAllMenusByRoles.get(groupSimpleDetailResponse.getMenuUrl()) == null) {
                filterAllMenusByRoles.put(groupSimpleDetailResponse.getMenuUrl(), groupSimpleDetailResponse.getRoleCode());
                continue;
            }
            // 符合要求

            // roles整理
            String roles = filterAllMenusByRoles.get(groupSimpleDetailResponse.getMenuUrl()) + "," + groupSimpleDetailResponse.getRoleCode();
            filterAllMenusByRoles.put(groupSimpleDetailResponse.getMenuUrl(), roles);
    }
        return filterAllMenusByRoles;
}

    @Override
    public int insertOrUpdateGroupArray(JSONObject json, HttpSession session) {
        JSONArray jsonArray = json.getJSONArray("params");
        UserResponse onlineUser = (UserResponse) session.getAttribute(Constant.ONLINE_USER);
        Group DelGroup = new Group();
        DelGroup.setRoleId(json.getInteger("roleId"));
        DelGroup.setUpdateBy(onlineUser.getUserName());
        DelGroup.setUpdateAt(new Date());
        groupMapper.deleteByRoleId(DelGroup);

        if (jsonArray.size() <= 0) {
            return 1;
        }

        List<Group> groups = new ArrayList<>();

        for (Object g : jsonArray) {
            JSONObject groupJSON = JSONObject.parseObject(JSONObject.toJSONString(g));
            Group group = new Group();
            group.setId(groupJSON.getInteger("id"));
            group.setRoleId(json.getInteger("roleId"));
            group.setMenuId(groupJSON.getString("menuId"));
            group.setIsDelete("0");
            group.setCreateBy(onlineUser.getUserName());
            group.setUpdateBy(onlineUser.getUserName());
            group.setUpdateAt(new Date());
            groups.add(group);
        }
        return groupMapper.updateOrInsertGroup(groups);
    }
}
