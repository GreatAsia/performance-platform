//添加用例模态框
function showAddPlan() {

    $("#addModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}


//添加计划
function addPlan() {

    var planSet = $.trim($('#planSetId').val());
    var setName = $.trim($('#setName').val());


    if (!planSet) {
        alert("请输入测试计划集合");
        return false;
    }

    if (!setName) {
        alert("请输入集合名称");
        return false;
    }


    var SAVEDATA = [];
    var requestData = {
        "plan_set": planSet,
        "set_name": setName,
    };
    SAVEDATA.push(requestData);

    $.ajax({
        type: "POST",
        url: "/perf/dubbo/per/insertPlanSet",
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
        url: "/perf/dubbo/per/deletePlanSet?id=" + deleteId,
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


//给更新计划模态框传值
function showUpdatePlan(obj) {

    var id = $(obj).attr("data-id");
    var plan_set = $(obj).attr('data-plan_set');
    var set_name = $(obj).attr('data-set_name');

    $("#updateId").val(id);
    $("#updatePlanSet").val(plan_set);
    $("#updateSetName").val(set_name);

    $("#updateModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}

// 修改接口用例
function updatePlan() {

    var id = $("#updateId").val().trim();
    var plan_set = $("#updatePlanSet").val().trim();
    var set_name = $("#updateSetName").val().trim();


    var SAVEDATA = [];

    if (!plan_set) {
        alert("请输入计划集合");
        return false;
    }
    if (!set_name) {
        alert("请输入集合名称");
        return false;
    }


    var requestData = {
        "id": id,
        "plan_set": plan_set,
        "set_name": set_name,
    };
    SAVEDATA.push(requestData);

    $.ajax({
        type: "POST",
        url: "/perf/dubbo/per/updatePlanSet",
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


//给运行计划集合模态框传值
function runJmeterSet(obj) {

    var setId = $(obj).attr("data-set_id");
    var url = "/perf/dubbo/per/run/set?setId=" + setId;
    console.log("url==" + url);
    $("#respose").val("运行中...");

    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        contentType: "application/json",
        timeout: 60000000,
        success: function (data) {
            var json = JSON.stringify(data, null, 4);
            $("#respose").val(json);
            //显示模态框
            $("#runCase").modal({
                backdrop: 'static',
                keyboard: false
            });
        },
        error: function (e) {
            $("#respose").val(e.responseText);
            //显示模态框
            $("#runCase").modal({
                backdrop: 'static',
                keyboard: false
            });
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


    var url = "/perf/dubbo/per/set/getlist?currentPage=" + currentPage + "&pageSize=10";

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

        contents += "<tr><td>" + dubboCase[i].id + "</td>\n" +
            "                    <td>" + dubboCase[i].plan_set + "</td>\n" +
            "                    <td>" + dubboCase[i].set_name + "</td>\n" +
            "                    <td>\n" +
            "                        <a href=\"javascript:void(0)\"\n" +
            "                           data-set_id=\'" + dubboCase[i].id + "\'\n" +
            "                           data-plan_set=\'" + dubboCase[i].plan_set + "\'\n" +
            "                           data-set_name=\'" + dubboCase[i].set_name + "\'\n" +
            "                           class=\"btn btn-sm btn-success\"\n" +
            "                           onclick=\"runPlanModal(this)\">运行</a>\n" +
            "                        <a href=\"javascript:void(0)\"\n" +
            "                           data-id=\'" + dubboCase[i].id + "\'\n" +
            "                           data-plan_set=\'" + dubboCase[i].plan_set + "\'\n" +
            "                           data-set_name=\'" + dubboCase[i].set_name + "\'\n" +
            "                           class=\"btn  btn-sm btn-info \"\n" +
            "                           onclick=\"showUpdatePlan(this)\">修改</a>\n" +
            "\n" +
            "                        <a href=\"javascript:void(0)\" data-id=\'" + dubboCase[i].id + "\' class=\"btn btn-sm btn-danger\"\n" +
            "                           onclick=\"showDeletePlan(this)\">删除</a>\n" +
            "                    </td></tr>"

    }

    return contents;


}


function search() {

    var setName = $("#searchName").val().trim();

    if (!setName) {
        alert("请输入集合名称");
        return
    }

    var url = "/perf/dubbo/per/set/findBySetName?setName=" + setName;

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