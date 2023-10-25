var addObj;

//添加模块模态框
function showAddModule(obj) {
    addObj = obj;
    $("#addModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}

//添加模块
function addModule() {

    var addProjectId = $.trim($('#addProjectName').val());
    var moduleName = $.trim($('#addModuleName').val());
    var moduleComments = $.trim($('#addComments').val());

    if (!moduleName) {
        alert("请输入模块名");
        return false;
    }

    var SAVEDATA = [];
    var requestData = {
        "project_id": addProjectId,
        "name": moduleName,
        "comments": moduleComments,
    };
    SAVEDATA.push(requestData);
    $.ajax({
        type: "POST",
        url: "/perf/middle/addMiddleModule",
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
        url: "/perf/middle/deleteMiddleModule",
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


var updateObj;

//给更新模块模态框传值
function showUpdateModule(obj) {
    updateObj = obj;
    var id = $(obj).attr("data-id");
    var project_id = $(obj).attr("data-projectid");
    var name = $(obj).attr("data-name");
    var comments = $(obj).attr("data-comments");


    $("#updateID").val(id);
    $("#updateProjectName").val(project_id);
    $("#updateModuleName").val(name);
    $("#updateComments").val(comments);


    $("#updateModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}

//更新项目
function updateModuleModel() {

    var id = $('#updateID').val();
    var project_id = $.trim($('#updateProjectName').val());
    var name = $('#updateModuleName').val().trim();
    var comments = $('#updateComments').val().trim();


    var SAVEDATA = [];


    if (!name) {
        alert("请输入模块名");
        return false;
    }
    var requestData = {
        "id": id,
        "project_id": project_id,
        "name": name,
        "comments": comments,
    };
    SAVEDATA.push(requestData);

    $.ajax({
        type: "POST",
        url: "/perf/middle/updateMiddleModule",
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


var currentPage = 1;
var totalPage = 1;


function switchPageInfo() {
    currentPage = 1;

    updatePageData();

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

    var project_id = $("#selectProjectName").val();

    var url = "/perf/middle/module/getlist?project_id=" + project_id + "&currentPage=" + currentPage + "&pageSize=10";

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

function content(middleModule) {

    var contents = "";

    for (var i = 0; i < middleModule.length; i++) {

        contents += "<tr><td>" + middleModule[i].id + "</td>\n" +
            "                    <td>" + middleModule[i].name + "</td>\n" +
            "                    <td>" + middleModule[i].comments + "</td>\n" +
            "                    <td>\n" +
            "                        <a href=\"javascript:void(0)\"\n" +
            "                           data-id=\'" + middleModule[i].id + "\'\n" +
            "                           data-name=\'" + middleModule[i].name + "\'\n" +
            "                           data-comments=\'" + middleModule[i].comments + "\'\n" +
            "                           data-projectid=\'" + middleModule[i].project_id + "\'\n" +
            "                           class=\"btn  btn-sm btn-info \"\n" +
            "                           onclick=\"showUpdateModule(this)\">修改</a>\n" +
            "\n" +
            "                        <a href=\"javascript:void(0)\" data-id=\'" + middleModule[i].id + "\' class=\"btn btn-sm btn-danger\"\n" +
            "                           onclick=\"showDeleteModal(this)\">删除</a>\n" +
            "                    </td></tr>"

    }

    return contents;


}
