package com.okay.testcenter.mapper.user;


import com.okay.testcenter.domain.bean.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<Menu> selectByMenu(Menu menu);

    Menu selectByMenuSingle(Menu menu);

    List<Menu> selectByMenuByRole(@Param("roleId") int roleId);

    List<Menu> selectByMenuNotDeleteByRole(@Param("roleId") int roleId);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menu record);
}