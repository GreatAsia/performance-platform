package com.okay.testcenter.domain.middle;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * middle_case 表
 */
public class MiddleCase {



    private int id;

    @NotNull(message = "环境ID不能为空")
    @Min(1)
    private int env_id;

    @NotNull(message = "接口ID不能为空")
    @Min(1)
    private int interface_id;

    @NotNull(message = "用例类型")
    @Min(0)
    private int caseType;

    @NotNull(message = "用例名称不能为空")
    @NotBlank(message = "用例名称不能为空")
    private String name;



    private String request_data;

    @NotNull(message = "验证数据不能为空")
    @NotBlank(message = "验证数据不能为空")
    private String check_data;

    private String interfaceName;

    private int module_id;
    private int project_id;


    private String envName;

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }
    public int getModule_id() {
        return module_id;
    }

    public void setModule_id(int module_id) {
        this.module_id = module_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnv_id() {
        return env_id;
    }

    public void setEnv_id(int env_id) {
        this.env_id = env_id;
    }

    public int getInterface_id() {
        return interface_id;
    }

    public void setInterface_id(int interface_id) {
        this.interface_id = interface_id;
    }

    public int getCaseType() {
        return caseType;
    }

    public void setCaseType(int caseType) {
        this.caseType = caseType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequest_data() {
        return request_data;
    }

    public void setRequest_data(String request_data) {
        this.request_data = request_data;
    }

    public String getCheck_data() {
        return check_data;
    }

    public void setCheck_data(String check_data) {
        this.check_data = check_data;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }






}
