var addObj;

//添加信息模态框
function showAddEnvProject(obj) {
    addObj = obj;
    $("#addModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}

//添加项目
function addEnvProject() {


    var project_id = $.trim($('#addProjectName').val());
    var env_id = $.trim($('#addEnvName').val());
    var url_header = $.trim($('#addProjectHost').val());
    var uname = $.trim($('#addUserName').val());
    var pwd = $.trim($('#addPwd').val());

    if (!url_header) {
        alert("请输入项目域名");
        return false;
    }
    if (!uname) {
        alert("请输入用户名");
        return false;
    }
    if (!pwd) {
        alert("请输入用户密码");
        return false;
    }


    var requestData = {
        "project_id": project_id,
        "env_id": env_id,
        "url_header": url_header,
        "uname": uname,
        "pwd": pwd
    };

    $.ajax({
        type: "POST",
        url: "/perf/middle/info/insert",
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
    var requestData = {
        "id": deleteId,
    };
    $.ajax({
        type: "POST",
        url: "/perf/middle/info/delete",
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
function showUpdateEnvProject(obj) {
    updateObj = obj;
    var id = $(obj).attr("data-id");
    var project_id = $(obj).attr("data-project_id");
    var env_id = $(obj).attr("data-env_id");
    var url_header = $(obj).attr("data-url_header");
    var uname = $(obj).attr("data-uname");
    var pwd =  $(obj).attr("data-pwd");


    $("#updateID").val(id);
    $("#updateProjectName").val(project_id);
    $("#updateEnvName").val(env_id);
    $("#updateProjectHost").val(url_header);
    $("#updateUserName").val(uname);
    $("#updatePwd").val(pwd);



    $("#updateModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}

//更新项目
function updateEnvProjectModel() {


    var id = $("#updateID").val().trim();
    var project_id = $("#updateProjectName").val().trim();
    var env_id = $("#updateEnvName").val().trim();
    var url_header = $("#updateProjectHost").val().trim();
    var uname = $("#updateUserName").val().trim();
    var pwd = $("#updatePwd").val().trim();


    if (!url_header) {
        alert("请输入项目域名");
        return false;
    }
    if (!uname) {
        alert("请输入用户名");
        return false;
    }
    if (!pwd) {
        alert("请输入用户密码");
        return false;
    }


    var requestData = {
        "id":id,
        "project_id": project_id,
        "env_id": env_id,
        "url_header": url_header,
        "uname": uname,
        "pwd": pwd
    };

    $.ajax({
        type: "POST",
        url: "/perf/middle/info/update",
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
                alert("修改失败：" + JSON.stringify(rdata));
            }

        },
        error: function (e) {
            alert("修改失败：" + JSON.stringify(e));
        }
    });


};


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


    var url = "/perf/middle/info/getlist?currentPage=" + currentPage + "&pageSize=10";

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

function content(envProject) {

    var contents = "";

    for (var i = 0; i < envProject.length; i++) {

        contents += "<tr><td>" + envProject[i].id + "</td>\n" +
            "                    <td>" + envProject[i].project_name + "</td>\n" +
            "                    <td>" + envProject[i].env_name + "</td>\n" +
            "                    <td>" + envProject[i].url_header + "</td>\n" +
            "                    <td>" + envProject[i].uname + "</td>\n" +
            "                    <td style=\"white-space:nowrap;overflow:hidden;text-overflow: ellipsis;\">" + envProject[i].pwd + "</td>\n" +
            "                    <td>\n" +
            "                        <a href=\"javascript:void(0)\"\n" +
            "                           data-id=\'" + envProject[i].id + "\'\n" +
            "                           data-project_id=\'" + envProject[i].project_id + "\'\n" +
            "                           data-env_id=\'" + envProject[i].env_id + "\'\n" +
            "                           data-url_header=\'" + envProject[i].url_header + "\'\n" +
            "                           data-uname=\'" + envProject[i].uname + "\'\n" +
            "                           data-pwd=\'" + envProject[i].pwd + "\'\n" +
            "                           class=\"btn  btn-sm btn-info \"\n" +
            "                           onclick=\"showUpdateEnvProject(this)\">修改</a>\n" +
            "\n" +
            "                        <a href=\"javascript:void(0)\" data-id=\'" + envProject[i].id + "\' class=\"btn btn-sm btn-danger\"\n" +
            "                           onclick=\"showDeleteModal(this)\">删除</a>\n" +
            "                    </td></tr>"

    }

    return contents;


}


function search() {

    var project_name = $("#searchName").val().trim();

    if (project_name == null || project_name == "" || project_name == undefined) {
        alert("请输入项目名称");
        return
    }

    var url = "/perf/middle/info/queryByProjectName?project_name=" + project_name;

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