package com.okay.testcenter.domain.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Menu {
    private Integer id;

    private String menuName;

    private String menuUrl;

    private String isMenu;

    private Integer parentId;

    private String groupId;

    private String roleId;

    private String roleName;

    private String isDelete;

    private String createBy;

    private Date createAt;

    private String updateBy;

    private Date updateAt;

}