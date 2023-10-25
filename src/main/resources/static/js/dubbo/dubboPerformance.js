//添加用例模态框
function showAddPlan() {

    $("#addModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}


//添加计划
function addPlan() {

    var caseId = $.trim($('#caseId').val());
    var threads = $.trim($('#threads').val());
    var times = $.trim($('#times').val());


    if (!caseId) {
        alert("请输入用例ID");
        return false;
    }

    if (!threads) {
        alert("请输入并发数");
        return false;
    }

    if (!times) {
        alert("请输入压测时间");
    }

    var SAVEDATA = [];
    var requestData = {
        "case_id": caseId,
        "threads": threads,
        "times": times,

    };
    SAVEDATA.push(requestData);

    $.ajax({
        type: "POST",
        url: "/perf/dubbo/per/insertPlan",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        timeout: 60000,
        success: function (data) {

            if (data.code == 200) {
                //隐藏模态框，刷新页面
                $("#addModal").modal('hide');
                updatePageData();
            } else {
                alert("添加失败：" + JSON.stringify(data));
            }

        },
        error: function (e) {
            alert("添加失败：" + JSON.stringify(e));
        }
    });

};


var delObj;

//给确认删除模态框传值
function showDeletePlan(obj) {
    delObj = obj;
    var id = $(obj).attr("data-id");
    $("#deleteID").val(id);
    $("#deleteModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}


//删除项目
function deleteInfo() {

    var deleteId = $('#deleteID').val();

    $.ajax({
        type: "Get",
        url: "/perf/dubbo/per/deletePlan?id=" + deleteId,
        dataType: "json",
        contentType: "application/json",
        timeout: 60000,
        success: function (data) {

            if (data.code == 200) {
                //隐藏模态框，删除对应的行
                $("#deleteModal").modal('hide');
                $(delObj).parents("tr").remove()
            } else {
                alert("删除失败：" + data);
            }

        },
        error: function (e) {
            alert("删除失败：" + e);
        }
    });

};


function stopJmeter() {

    $.ajax({
        type: "Get",
        url: "/perf/dubbo/per/stop",
        dataType: "json",
        contentType: "application/json",
        timeout: 60000,
        success: function (data) {

            if (data.code == 200) {
                alert("Jmeter已停止");
            } else {
                alert("停止失败：" + data);
            }

        },
        error: function (e) {
            alert("停止失败：" + e);
        }
    });


}


//给更新计划模态框传值
function showUpdatePlan(obj) {

    var id = $(obj).attr("data-id");
    var caseId = $(obj).attr('data-case_id');
    var threads = $(obj).attr('data-threads');
    var times = $(obj).attr('data-times');


    $("#updateId").val(id);
    $("#updateCaseId").val(caseId);
    $("#updateThreads").val(threads);
    $("#updateTimes").val(times);


    $("#updateModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}

// 修改接口用例
function updatePlan() {

    var id = $("#updateId").val().trim();
    var caseId = $("#updateCaseId").val().trim();
    var threads = $("#updateThreads").val().trim();
    var times = $("#updateTimes").val().trim();
    var SAVEDATA = [];

    if (!caseId) {
        alert("请输入用例ID");
        return false;
    }
    if (!threads) {
        alert("请输入并发数");
        return false;
    }
    if (!times) {
        alert("请输入压测时间");
        return false;
    }


    var requestData = {
        "id": id,
        "case_id": caseId,
        "threads": threads,
        "times": times,
    };
    SAVEDATA.push(requestData);

    $.ajax({
        type: "POST",
        url: "/perf/dubbo/per/updatePlan",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        timeout: 60000,
        success: function (data) {

            if (data.code == 200) {
                //隐藏模态框，刷新页面
                $("#updateModal").modal('hide');
                updatePageData();
            } else {
                alert("修改失败：" + JSON.stringify(data));
            }

        },
        error: function (e) {
            alert("修改失败：" + JSON.stringify(e));
        }
    });


};


//给运行计划模态框传值
function runPlanModal(obj) {

    var case_id = $(obj).attr("data-case_id");
    var threads = $(obj).attr("data-threads");
    var times = $(obj).attr("data-times");
    var url = "/perf/dubbo/per/run/" + case_id + "/" + threads + "/" + times;
    console.log("url==" + url);
    $("#respose").val("运行中...");
    //显示模态框
    $("#runCase").modal({
        backdrop: 'static',
        keyboard: false
    });

    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        contentType: "application/json",
        async: true,
        timeout: 600000,
        success: function (data) {
            var json = JSON.stringify(data, null, 4);
            $("#respose").val(json);

        },
        error: function (e) {
            $("#respose").val(e.responseText);

        }

    });
}


//运行Jmeter
function runJmeterPlanModal(obj) {

    var case_id = $(obj).attr("data-case_id");
    var threads = $(obj).attr("data-threads");
    var times = $(obj).attr("data-times");
    var url = "/perf/dubbo/per/run/jmeter?caseId=" + case_id + "&threadNum=" + threads + "&runTime=" + times;
    console.log("url==" + url);
    $("#respose").val("运行中...");
    //显示模态框
    $("#runCase").modal({
        backdrop: 'static',
        keyboard: false
    });

    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        contentType: "application/json",
        timeout: 6000000,
        success: function (data) {
            console.log("请求成功");
            var json = JSON.stringify(data, null, 4);
            $("#respose").val(json);
        },
        error: function (e) {
            console.log("请求失败");
            $("#respose").val(e.responseText);
        }

    });
}

var currentPage = 1;
var totalPage = 1;


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


    var url = "/perf/dubbo/per/getlist?currentPage=" + currentPage + "&pageSize=10";

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

function content(dubboCase) {

    var contents = "";

    for (var i = 0; i < dubboCase.length; i++) {

        var id = dubboCase[i].id;
        var caseId = dubboCase[i].case_id;
        var caseName = dubboCase[i].case_name;
        var threads = dubboCase[i].threads;
        var times = dubboCase[i].times;

        contents += "<tr><td>" + dubboCase[i].id + "</td>\n" +
            "                    <td>" + caseId + "</td>\n" +
            "                    <td>" + caseName + "</td>\n" +
            "                    <td>" + threads + "</td>\n" +
            "                    <td>" + times + "</td>\n" +
            "                    <td>\n" +
            "                        <a href=\"javascript:void(0)\"\n" +
            "                           data-case_id=\'" + caseId + "\'\n" +
            "                           data-threads=\'" + threads + "\'\n" +
            "                           data-times=\'" + times + "\'\n" +
            "                           class=\"btn btn-sm btn-success\"\n" +
            "                           onclick=\"runJmeterPlanModal(this)\">运行</a>\n" +
            "                        <a href=\"javascript:void(0)\"\n" +
            "                           data-id=\'" + id + "\'\n" +
            "                           data-case_id=\'" + caseId + "\'\n" +
            "                           data-threads=\'" + threads + "\'\n" +
            "                           data-times=\'" + times + "\'\n" +
            "                           class=\"btn  btn-sm btn-info \"\n" +
            "                           onclick=\"showUpdatePlan(this)\">修改</a>\n" +
            "\n" +
            "                        <a href=\"javascript:void(0)\" data-id=\'" + id + "\' class=\"btn btn-sm btn-danger\"\n" +
            "                           onclick=\"showDeletePlan(this)\">删除</a>\n" +
            "                    </td></tr>"

    }

    return contents;


}


function search() {

    var caseId = $("#searchName").val().trim();

    if (!caseId) {
        alert("请输入用例ID");
        return
    }

    var url = "/perf/dubbo/per/findByCaseId?caseId=" + caseId;

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