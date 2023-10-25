package com.okay.testcenter.domain.middle;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhou
 * @date 2019/12/13
 */
@Getter
@Setter
public class MiddlePerformanceResult {

    private int id;

    @NotNull(message = "用例ID不能为空")
    @Min(1)
    private int case_id;

    @NotNull(message = "错误率不能为空")
    @NotBlank(message = "错误率不能为空")
    private String error_rate;

    @NotNull(message = "响应时间不能为空")
    @Min(1)
    private int response_time;

    @NotNull(message = "吞吐量不能为空")
    private float throughput;

    @NotNull(message = "开始时间不能为空")
    @NotBlank(message = "开始时间不能为空")
    private String start_time;

    @NotNull(message = "结束时间不能为空")
    @NotBlank(message = "结束时间不能为空")
    private String end_time;

    @NotNull(message = "运行ID不能为空")
    @Min(1)
    private int run_id;

    private String case_name;
    private int set_id;

    private String error_data;

    @NotNull(message = "运行时间不能为空")
    @Min(1)
    private int run_time;


    @NotNull(message = "并发数不能为空")
    @Min(1)
    private int threads;

    private int max_time;
    private int min_time;
    private Long total_request;


}
