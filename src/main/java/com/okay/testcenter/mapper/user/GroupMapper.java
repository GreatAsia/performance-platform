package com.okay.testcenter.mapper.user;


import com.okay.testcenter.domain.bean.Group;
import com.okay.testcenter.domain.user.GroupSimpleDetailResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Group record);

    int insertSelective(Group record);

    Group selectByPrimaryKey(Integer id);

    List<Group> selectByGroup(Group record);

    List<GroupSimpleDetailResponse> selectGroupSimpleDetailByGroup(Group record);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);

    int updateOrInsertGroup(@Param("groups") List<Group> groups);

    int deleteByRoleId(Group group);
}