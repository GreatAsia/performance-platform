package com.okay.testcenter.mapper.user;


import com.okay.testcenter.domain.bean.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface RoleMapper {

    int insertSelective(Role record);

    Role selectByRoleSingle(Role role);

    List<Role> selectByRole(Role role);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);
}