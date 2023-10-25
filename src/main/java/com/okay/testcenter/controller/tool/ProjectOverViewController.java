package com.okay.testcenter.controller.tool;


import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Api(description = "项目概况接口")
@Controller
@RequestMapping(value = "/api")
public class ProjectOverViewController {


    @GetMapping(value = "/home")
    public String home() {

        return "html/index";
    }



}