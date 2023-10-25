var currentPage = 1;
var totalPage = 1;


//添加JOB模态框
function showAddModal(obj) {

    $("#addModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}

//添加任务
function addJob() {

    var jobName = $('#addModelJobName').val().trim();
    var jobGroup = $('#addModelGroupName').val().trim();
    var jobCron = $('#addModelJobCron').val().trim();
    var jobStatus = $('#addModelJobStatus').val().trim();

    if (!jobName) {
        alert("请输入任务名称");
        return false;
    }
    if (!jobGroup) {
        alert("请输入任务分组");
        return false;
    }
    if (!jobCron) {
        alert("请输入任务执行时间");
        return false;
    }
    if (!jobStatus) {
        alert("请输入任务执行状态");
        return false;
    }

    var SAVEDATA = [];
    var requestData = {
        "jobName": jobName,
        "jobGroup": jobGroup,
        "cron": jobCron,
        "status": jobStatus,
    };

    SAVEDATA.push(requestData);

    $.ajax({
        type: "POST",
        url: "/perf/job/addjob",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        timeout: 6000,
        success: function (data) {

            if (data.code == 200) {
                //隐藏模态框，刷新页面
                $("#addModal").modal('hide');
                location.reload();
            } else {
                alert("添加失败：" + data);
            }

        },
        error: function (e) {
            alert("添加失败：" + JSON.stringify(e));
        }
    });

};


var delObj;

//给确认删除模态框传值
function showDeleteModal(obj) {
    delObj = obj;
    var id = $(obj).attr("data-id");
    $("#deleteID").val(id);
    $("#deleteModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}

//删除Job
function deleteInfo() {

    var deleteId = $('#deleteID').val();
    var SAVEDATA = [];
    var requestData = {
        "jobId": deleteId,
    };
    SAVEDATA.push(requestData);

    $.ajax({
        type: "POST",
        url: "/perf/job/deletejob",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        timeout: 6000,
        success: function (data) {

            if (data.code == 200) {
                //隐藏模态框，删除对应的行
                $("#deleteModal").modal('hide');
                $(delObj).parents("tr").remove();
            } else {
                alert("删除失败：" + data);
            }

        },
        error: function (e) {
            alert("删除失败：" + JSON.stringify(e));
        }
    });

};


//给更新Job模态框传值
function showUpdateModal(obj) {

    var jobId = $(obj).attr("data-id");
    var jobClassName = $(obj).attr("data-jobName");
    var jobGroupName = $(obj).attr("data-jobGroup");
    var cronExpression = $(obj).attr("data-cron");
    var jobStatus = $(obj).attr("data-jobStatus");


    $("#updateID").val(jobId);
    $("#updateModelJobName").val(jobClassName);
    $("#updateModelGroupName").val(jobGroupName);
    $("#updateModelJobCron").val(cronExpression);
    $("#updateModelJobStatus").val(jobStatus);

    $("#updateModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}

//更新Job
function updateJob() {


    var jobId = $('#updateID').val().trim();
    var jobClassName = $("#updateModelJobName").val().trim();
    var jobGroupName = $('#updateModelGroupName').val().trim();
    var cronExpression = $('#updateModelJobCron').val().trim();
    var jobStatus = $('#updateModelJobStatus').val().trim();


    var SAVEDATA = [];

    if (!jobClassName) {
        alert("请输入任务名称");
        return false;
    }
    if (!jobGroupName) {
        alert("请输入任务组名");
        return false;
    }
    if (!cronExpression) {
        alert("请输入任务执行时间");
        return false;
    }
    if (!jobStatus) {
        alert("请输入任务状态");
        return false;
    }
    var requestData = {
        "id": jobId,
        "jobName": jobClassName,
        "jobGroup": jobGroupName,
        "cron": cronExpression,
        "status": jobStatus
    };
    SAVEDATA.push(requestData);

    $.ajax({
        type: "POST",
        url: "/perf/job/updatejob",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        timeout: 6000,
        success: function (data) {

            if (data.code == 200) {
                //隐藏模态框，刷新页面
                $("#updateModal").modal('hide');
                location.reload();
            } else {
                alert("修改失败：" + data);
            }

        },
        error: function (e) {
            alert("修改失败：" + JSON.stringify(e));
        }
    });


};


function pauseJob(obj) {

    var jobStatus = $('#status').text().trim();
    if (jobStatus == '已停止') {
        return false;
    }

    var jobId = $(obj).attr("data-Id");
    var jobClassName = $(obj).attr("data-jobName");
    var jobGroupName = $(obj).attr("data-jobGroup");
    var cronExpression = $(obj).attr("data-cron");


    var SAVEDATA = [];

    var requestData = {
        "id": jobId,
        "jobName": jobClassName,
        "jobGroup": jobGroupName,
        "cron": cronExpression,
    };
    SAVEDATA.push(requestData);

    $.ajax({
        type: "POST",
        url: "/perf/job/pausejob",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        timeout: 6000,
        success: function (data) {

            if (data.code == 200) {
                location.reload();
            } else {
                alert("修改失败：" + data);
            }

        },
        error: function (e) {
            alert("修改失败：" + JSON.stringify(e));
        }
    });

}


function resumeJob(obj) {

    var jobStatus = $('#status').text().trim();
    if (jobStatus == '运行中') {
        return false;
    }

    var jobId = $(obj).attr("data-id");
    var jobClassName = $(obj).attr("data-jobName");
    var jobGroupName = $(obj).attr("data-jobGroup");
    var cronExpression = $(obj).attr("data-cron");

    var SAVEDATA = [];

    var requestData = {
        "id": jobId,
        "jobName": jobClassName,
        "jobGroup": jobGroupName,
        "cron": cronExpression,
    };
    SAVEDATA.push(requestData);

    $.ajax({
        type: "POST",
        url: "/perf/job/resumejob",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        timeout: 6000,
        success: function (data) {

            if (data.code == 200) {
                location.reload();
            } else {
                alert("修改失败：" + data);
            }

        },
        error: function (e) {
            alert("修改失败：" + JSON.stringify(e));
        }
    });

}


// 首页
function homePage() {

    if (currentPage == 1) {
        return
    }
    currentPage = 1;
    updatePageData();

}


// 末页
function lastPage() {

    totalPage = parseInt($("#totalPage").text().replace("共", "").replace("页", ""));
    if (currentPage == totalPage) {
        return
    }

    currentPage = totalPage;
    updatePageData();

}

// 下一页
function nextPage() {

    totalPage = parseInt($("#totalPage").text().replace("共", "").replace("页", ""));
    currentPage = currentPage + 1;

    if (currentPage > totalPage) {
        currentPage = currentPage - 1;

        return;
    }

    updatePageData();

}

// 上一页
function previousPage() {

    currentPage = currentPage - 1;
    if (currentPage <= 0) {
        currentPage = currentPage + 1;
        return;
    }
    updatePageData();

}


// 更新分页的数据
function updatePageData() {


    var url = "/perf/job/getlist?currentPage=" + currentPage + "&pageSize=10";

    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        contentType: "application/json",
        timeout: 60000,
        success: function (data) {

            $("#tbody").html(content(data.list));
            totalPage = data.pages;

            if (data.list.length == 0) {
                $("#currentPage").text("第0页");
            } else {
                $("#currentPage").text("第" + currentPage + "页");
            }

            $("#totalPage").text("共" + totalPage + "页");
        },
        error: function (e) {
            alert("fail:" + JSON.stringify(e));
        }
    });

}

function content(joblist) {

    var contents = "";

    for (var i = 0; i < joblist.length; i++) {

        var status = "";
        if (joblist[i].status == 0) {

            status = "<td  id = \"status\" style=\"font-weight:bold;color:red\"> " + "已停止" + "</td>\n";
        } else {

            status = "<td  id = \"status\" style=\"font-weight:bold;color:green\"> " + "运行中" + "</td>\n";
        }

        contents += "<tr><td>" + joblist[i].id + "</td>\n" +
            "        <td>" + joblist[i].jobName + "</td>\n" +
            "        <td>" + joblist[i].jobGroup + "</td>\n" +
            "        <td>" + joblist[i].cron + "</td>\n" +
            status +
            "<td>\n" +
            " <a href=\"javascript:void(0)\" data-Id=\'" + joblist[i].id + "\'\n" +
            "                           data-jobName=\'" + joblist[i].jobName + "\'\n" +
            "                           data-jobGroup=\'" + joblist[i].jobGroup + "\'\n" +
            "                           data-cron=\'" + joblist[i].cron + "\'\n" +
            "                           class=\"btn  btn-sm btn-warning \"\n" +
            "                           onclick=\"pauseJob(this)\">暂停</a>\n" +
            "                        <a href=\"javascript:void(0)\" data-id=\'" + joblist[i].id + "\'\n" +
            "                           data-jobName=\'" + joblist[i].jobName + "\'\n" +
            "                           data-jobGroup=\'" + joblist[i].jobGroup + "\'\n" +
            "                           data-cron=\'" + joblist[i].cron + "\'\n" +
            "                           class=\"btn  btn-sm btn-info \"\n" +
            "                           onclick=\"resumeJob(this)\">恢复</a>\n" +
            "                        <a href=\"javascript:void(0)\" data-id=\'" + joblist[i].id + "\'\n" +
            "                           data-jobName=\'" + joblist[i].jobName + "\'\n" +
            "                           data-jobGroup=\'" + joblist[i].jobGroup + "\'\n" +
            "                           data-cron=\'" + joblist[i].cron + "\'\n" +
            "                           data-jobStatus=\'" + joblist[i].status + "\'\n" +
            "                           class=\"btn  btn-sm btn-success \"\n" +
            "                           onclick=\"showUpdateModal(this)\">修改</a>\n" +
            "                        <a href=\"javascript:void(0)\" data-id=\'" + joblist[i].id + "\'\n" +
            "                           class=\"btn btn-sm btn-danger \"\n" +
            "                           onclick=\"showDeleteModal(this)\">删除</a>"
            "</td></tr>"

    }

    return contents;


}


function search() {

    var jobName = $("#searchName").val().trim();

    if (jobName == null || jobName == "" || jobName == undefined) {
        alert("请输入任务名称");
        return
    }

    var url = "/perf/job/findJobByName?jobName=" + jobName;

    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        contentType: "application/json",
        timeout: 60000,
        success: function (data) {

            $("#tbody").html(content(data));

            currentPage = 1;
            totalPage = 1;
            $("#currentPage").text("第1页");
            $("#totalPage").text("共1页");
        },
        error: function (e) {
            alert("fail:" + JSON.stringify(e));
        }
    });


}

document.onkeydown = function (e) {
    var ev = document.all ? window.event : e;
    if (ev.keyCode == 13) {
        search();
    }
}