

//添加接口模态框
function showAddInterface(obj) {

    // 获取模块名称
    switchAddModule();

    $("#addModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}

//添加接口
function addInterface() {

    var module_id = $.trim($('#addModuleName').val());
    var name = $.trim($('#addInterfaceName').val());
    var url = $.trim($('#addInterfaceUrl').val());
    var request_method = $.trim($('#addInterfaceRequestMethod').val());
    var comments = $.trim($('#addComments').val());

    if (!name) {
        alert("请输入接口名");
        return false;
    }
    if (!url) {
        alert("请输入接口地址");
        return false;
    }

    var SAVEDATA = [];
    var requestData = {
        "module_id": module_id,
        "name": name,
        "url": url,
        "request_method": request_method,
        "comments": comments,
    };
    SAVEDATA.push(requestData);

    $.ajax({
        type: "POST",
        url: "/perf/middle/addMiddleInterface",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        timeout: 60000,
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
            alert("添加失败：" + e);
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


//删除项目
function deleteInfo() {

    var deleteId = $('#deleteID').val();
    var SAVEDATA = [];
    var requestData = {
        "id": deleteId,
    };
    SAVEDATA.push(requestData);

    $.ajax({
        type: "POST",
        url: "/perf/middle/deleteMiddleInterface",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(requestData),
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




//给更新模块模态框传值
function showUpdateModule(obj) {

    var id = $(obj).attr("data-id");
    var module_id = $(obj).attr("data-module_id");
    var name = $(obj).attr("data-name");
    var url = $(obj).attr("data-url");
    var request_method = $(obj).attr("data-request_method");
    var comments = $(obj).attr("data-comments");
    var project_id = $(obj).attr("data-project_id");

    updateModuleName(module_id, project_id);

    $("#updateID").val(id);
    $("#updateProjectName").val(project_id);

    $("#updateInterfaceName").val(name);
    $("#updateInterfaceUrl").val(url);
    $("#updateInterfaceRequestMethod").val(request_method);
    $("#updateComments").val(comments);


    $("#updateModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}

//更新接口
function updateInterfaceModel() {

    var id = $('#updateID').val().trim();
    var module_id = $.trim($('#updateModuleName').val());
    var name = $.trim($('#updateInterfaceName').val());
    var url = $.trim($('#updateInterfaceUrl').val());
    var request_method = $.trim($('#updateInterfaceRequestMethod').val());
    var comments = $.trim($('#updateComments').val());


    var SAVEDATA = [];



    if (!name) {
        alert("请输入接口名");
        return false;
    }
    if (!url) {
        alert("请输入接口地址");
        return false;
    }
    var requestData = {
        "id": id,
        "module_id": module_id,
        "name": name,
        "url": url,
        "request_method": request_method,
        "comments": comments,
    };
    SAVEDATA.push(requestData);

    $.ajax({
        type: "POST",
        url: "/perf/middle/updateMiddleInterface",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        timeout: 60000,
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
            alert("修改失败：" + e);
        }
    });


};


// 添加接口填充模块内容
function switchAddModule() {

    var projectId = $('#addProjectName').val();
    var url = "/perf/middle/queryModuleByProject?project_id=" + projectId;

    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        contentType: "application/json",
        timeout: 60000,
        success: function (data) {
            var str = "";
            for (var i = 0; i < data.length; i++) {
                str += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
            }
            $("#addModuleName").html(str);


        },
        error: function (e) {
            alert("请求失败：" + e);
        }
    });

}

// 切换模块填充内容

function switchModuleName() {

    var projectId = $('#selectProjectName').val();

    var url = "/perf/middle/queryModuleByProject?project_id=" + projectId;

    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        contentType: "application/json",
        timeout: 60000,
        success: function (data) {
            var str = "";
            for (var i = 0; i < data.length; i++) {

                str += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
            }

            $("#middleModel").html(str);
            // 更新列表数据
            currentPage = 1;
            updatePageData();

        },
        error: function (e) {
            alert("请求失败：" + e);
        }
    });


}


function updateModuleName(module_id, projectId) {

    var url = "/perf/middle/queryModuleByProject?project_id=" + projectId;


    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        contentType: "application/json",
        timeout: 60000,
        success: function (data) {
            var str = "";
            for (var i = 0; i < data.length; i++) {

                if (data[i].id == module_id) {
                    str += '<option value="' + data[i].id + '" + selected>' + data[i].name + '</option>';
                    continue;
                }

                str += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
            }
            $("#updateModuleName").html(str);

        },
        error: function (e) {
            alert("请求失败：" + e);
        }
    });


}


// 更新接口的填充模块名
function switchUpdateModuleName() {


    var projectId = $('#updateProjectName').val();


    var url = "/perf/middle/queryModuleByProject?project_id=" + projectId;


    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        contentType: "application/json",
        timeout: 60000,
        success: function (data) {
            var str = "";
            for (var i = 0; i < data.length; i++) {

                str += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
            }

            $("#updateModuleName").html(str);


        },
        error: function (e) {
            alert("请求失败：" + e);
        }
    });


}


var currentPage = 1;
var totalPage = 1;

// 切换项目名称，填充模块
function switchPageInfo() {

    switchModuleName();


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


// 切换模块
function switchMiddleModule() {
    currentPage = 1;
    updatePageData();

}


// 更新分页的数据
function updatePageData() {

    var module_id = $("#middleModel").val();

    if (module_id == null) {
        $("#tbody").html("");
        return;
    }

    var url = "/perf/middle/interface/getlist?module_id=" + module_id + "&currentPage=" + currentPage + "&pageSize=10";

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

function content(middleInterface) {

    var contents = "";

    for (var i = 0; i < middleInterface.length; i++) {

        contents += "<tr> <td>" + middleInterface[i].id + "</td>\n" +
            "                    <td>" + middleInterface[i].name + "</td>\n" +
            "                    <td>" + middleInterface[i].url + "</td>\n" +
            "                    <td>" + middleInterface[i].request_method + "</td>\n" +
            "                    <td>" + middleInterface[i].comments + "</td>\n" +
            "                    <td>\n" +
            "                        <a href=\"javascript:void(0)\"\n" +
            "                           data-id=\'" + middleInterface[i].id + "\'\n" +
            "                           data-module_id=\'" + middleInterface[i].module_id + "\'\n" +
            "                           data-name=\'" + middleInterface[i].name + "\'\n" +
            "                           data-url=\'" + middleInterface[i].url + "\'\n" +
            "                           data-request_method=\'" + middleInterface[i].request_method + "\'\n" +
            "                           data-comments=\'" + middleInterface[i].comments + "\'\n" +
            "                           data-project_id=\'" + middleInterface[i].project_id + "\'\n" +
            "                           class=\"btn  btn-sm btn-info \"\n" +
            "                           onclick=\"showUpdateModule(this)\">修改</a>\n" +
            "\n" +
            "                        <a href=\"javascript:void(0)\" data-id=\'" + middleInterface[i].id + "\' class=\"btn btn-sm btn-danger\"\n" +
            "                           onclick=\"showDeleteModal(this)\">删除</a>\n" +
            "                    </td></tr>"

    }

    return contents;


}


function search() {

    var interface_name = $("#searchName").val().trim();

    if (interface_name == null || interface_name == "" || interface_name == undefined) {
        alert("请输入接口名称");
        return
    }

    var url = "/perf/middle/findMiddleInterfaceByName?interface_name=" + interface_name;

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