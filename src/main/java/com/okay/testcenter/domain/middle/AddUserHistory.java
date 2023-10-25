package com.okay.testcenter.domain.middle;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加用户历史
 */
public class AddUserHistory {

    private int id;

    @NotNull(message = "userType不能为空")
    private String userType;

    @NotNull(message = "数量不能为空")
    @Min(1)
    private int totalCount;


    @NotNull(message = "环境ID不能为空")
    @Min(1)
    private int envId;


    @NotNull(message = "创建时间不能为空")
    @NotBlank(message = "创建时间不能为空")
    private String createTime;

    @NotNull(message = "更新时间不能为空")
    @NotBlank(message = "更新时间不能为空")
    private String updateTime;

    String envName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int count) {
        this.totalCount = count;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getEnvId() {
        return envId;
    }

    public void setEnvId(int envId) {
        this.envId = envId;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }


}
