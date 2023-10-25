package com.okay.testcenter.domain.middle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/*
  middle_run_type表
 */
public class MiddleRunType {


    private int id;


    @NotNull(message = "运行名称不能为空")
    @NotBlank(message = "运行名称不能为空")
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
