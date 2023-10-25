package com.okay.testcenter.domain.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {
    private int id;

    private String userName;

    private String userEmail;

    private String userPassword;

    private String position;

    private Integer roleId;

    private String roleName;

    private String sex;

    private String isDelete;

    private String createBy;

    private String updateBy;

    private Date createAt;

    private Date updateAt;

}