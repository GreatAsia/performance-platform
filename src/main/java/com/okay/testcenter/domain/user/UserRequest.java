package com.okay.testcenter.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author XieYangYang
 * @date 2019/11/21 11:01
 */

@Getter
@Setter
public class UserRequest {
    @NotNull(message = "用户名不能为空")
    @Size(max = 50, min = 1, message = "用户名长度要在1-50之间")
    private String userName;

    private String userEmail;
    @NotNull(message = "密码不能为空")
    @Size(max = 50, min = 1, message = "密码长度要在1-50之间")
    private String password;

    private boolean rememberMe = true;

   /* public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }*/



    /*public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }*/
}
