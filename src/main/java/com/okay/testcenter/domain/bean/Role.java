package com.okay.testcenter.domain.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Role {
    private Integer id;

    private String roleName;

    private String roleCode;

    private String isDelete;

    private String createBy;

    private Date createAt;

    private String updateBy;

    private Date updateAt;

}