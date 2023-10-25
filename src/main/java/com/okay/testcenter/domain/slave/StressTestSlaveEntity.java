package com.okay.testcenter.domain.slave;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 * @author zhou
 * @date 2021/4/23
 */
@Getter
@Setter
public class StressTestSlaveEntity implements Serializable {


    /**
     * 主键id
     */
    private Long slaveId;

    /**
     * 节点名称
     */
    @NotNull(message = "节点名称不能为空")
    private String slaveName;

    /**
     * IP地址
     */
    @NotNull(message = "IP地址不能为空")
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$")
    private String ip;

    /**
     * 端口号
     */
    @NotNull(message = "Jmeter端口号不能为空")
    @Min(value = 0)
    private String JmeterPort;

    /**
     * 端口号
     */
    @NotNull(message = "ssh端口号不能为空")
    @Min(value = 0)
    private String sshPort;

    private String userName;

    private String passwd;

    /**
     * 子节点的Jmeter路径
     */
    private String homeDir;

    /**
     * 状态  0：禁用  1：正常
     */
    private Integer status;

    /**
     * 分布式节点机权重
     */
    @Min(value = 1)
    private String weight;

    /**
     * 提交的用户
     */
    private String addBy;

    /**
     * 修改的用户
     */
    private String updateBy;

    /**
     * 提交的时间
     */
    private Date addTime;

    /**
     * 更新的时间
     */
    private Date updateTime;

    /**
     * 分布式节点运行的脚本ID，通过缓存保存，不入库
     */
    private Long runFileId;


    /**
     * ip不相同，不为空
     */
    public StressTestSlaveEntity copySlaveEntity() {
        StressTestSlaveEntity clone = new StressTestSlaveEntity();
        clone.setSlaveName(this.getSlaveName());
        clone.setIp(this.getIp() + new Random().nextInt(100));
        clone.setJmeterPort(this.getJmeterPort());
        clone.setUserName(this.getUserName());
        clone.setPasswd(this.getPasswd());
        clone.setSshPort(this.getSshPort());
        clone.setHomeDir(this.getHomeDir());
        clone.setStatus(this.getStatus());
        clone.setWeight(this.getWeight());
        clone.setSlaveId(this.getSlaveId());
        return clone;
    }
}
