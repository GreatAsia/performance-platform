package com.okay.testcenter.domain.dubbo;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class DubboServerInfo {




    private DubboCase dubboCase;
    private List<String> serverList;



}
