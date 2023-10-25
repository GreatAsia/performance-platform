package com.okay.testcenter.domain.middle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhou
 * @date 2019/12/13
 */
public class MiddlePerformanceSet {


    private int id;

    @NotNull(message = "计划集合不能为空")
    @NotBlank(message = "计划集合不能为空")
    private String plan_set;


    @NotNull(message = "计划集合名称不能为空")
    @NotBlank(message = "计划集合名称不能为空")
    private String set_name;
    private int run_id;
    private int set_id;

    public int getSet_id() {
        return set_id;
    }

    public void setSet_id(int set_id) {
        this.set_id = set_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlan_set() {
        return plan_set;
    }

    public void setPlan_set(String plan_set) {
        this.plan_set = plan_set;
    }

    public String getSet_name() {
        return set_name;
    }

    public void setSet_name(String set_name) {
        this.set_name = set_name;
    }

    public int getRun_id() {
        return run_id;
    }

    public void setRun_id(int run_id) {
        this.run_id = run_id;
    }




}
