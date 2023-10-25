var delObj;


//给确认删除模态框传值
function showDeleteInterface(obj) {
    delObj = obj;
    var id = $(obj).attr("data-id");
    $("#deleteID").val(id);
    $("#deleteModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}

//删除模块
function deleteInfo() {
    var deleteId = $('#deleteID').val();
    var SAVEDATA = [];
    var requestData = {
        "id": deleteId,
    };
    SAVEDATA.push(requestData);

    $.ajax({
        type: "POST",
        url: "/perf/dubbo/interface/delete",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        timeout: 5000,
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
function showUpdateModal(obj) {
    updateObj = obj;
    var id = $(obj).attr("data-id");
    var caseName = $(obj).attr("data-caseName");
    var envId = $(obj).attr("data-envId");
    var modelId = $(obj).attr("data-modelId");
    var interfaceClass = $(obj).attr("data-interFaceClassName");
    var methodName = $(obj).attr("data-methodName");
    var requestType = $(obj).attr("data-requestType");
    var params = $(obj).attr("data-params");
    var checkDate = $(obj).attr("data-checkData");
    var type = $(obj).attr("data-type");
    var version = $(obj).attr("data-version");

    $("#updateCaseId").val(id);
    $("#updateCaseName").val(caseName);
    $("#updateEnv").val(envId);
    $("#updateModelName").val(modelId);
    $("#updateInterFaceClassName").val(interfaceClass);
    $("#updateMethodName").val(methodName);
    $("#updateRequestType").val(requestType);
    $("#updateParams").val(params);
    $("#updateCheckDate").val(checkDate);
    $("#updateType").val(type);
    $("#updateVersion").val(version);

    $("#updateModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}

//更新模块
function updateInfo() {

    var id = $.trim($('#updateCaseId').val()).trim();
    var caseName = $.trim($('#updateCaseName').val());
    var envId = $.trim($('#updateEnv').val());
    var model = $.trim($('#updateModelName').val());
    var interfaceClass = $.trim($('#updateInterFaceClassName').val());
    var methodName = $.trim($('#updateMethodName').val());
    var requestType = $.trim($('#updateRequestType').val());
    var params = $.trim($('#updateParams').val());
    var checkDate = $.trim($('#updateCheckDate').val());
    var type = $.trim($('#updateType').val());
    var version = $.trim($('#updateVersion').val());
    if (!caseName) {
        alert("请输入用例名");
        return false;
    }
    if (!model) {
        alert("请输入模块");
        return false;
    }
    if (!interfaceClass) {
        alert("请输入接口类型");
        return false;
    }
    if (!methodName) {
        alert("请输入方法名");
        return false;
    }
    if (!requestType) {
        alert("请输入请求类型");
        return false;
    }
    if (!params) {
        alert("请输入请求参数");
        return false;
    }
    if (!checkDate) {
        alert("请输入验证数据");
        return false;
    }

    if (!type) {
        alert("请输入用例类型");
        return false;
    }
    if (!version) {
        alert("请输入版本号");
        return false;
    }

    var SAVEDATA = [];
    var requestData = {
        "id": id,
        "caseName": caseName,
        "envId": envId,
        "model": model,
        "interFaceClassName": interfaceClass,
        "methodName": methodName,
        "requestType": requestType,
        "params": params,
        "checkData": checkDate,
        "type": type,
        "version": version,
    };
    SAVEDATA.push(requestData);

    $.ajax({
        type: "POST",
        url: "/perf/dubbo/interface/update",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        timeout: 5000,
        success: function (data) {

            if (data.code == 200) {
                //隐藏模态框，刷新页面
                $("#updateModal").modal('hide');
                updatePageData();
            } else {
                alert("修改失败：" + data);
            }

        },
        error: function (e) {
            alert("修改失败：" + e);
        }
    });


};


//给运行Dobbo的模态框传值
function runDubboModal(obj) {

    var id = $(obj).attr("data-id");
    var address = $.trim($('#interfaceCaseName').find("option:selected").text());

    var requestData = {
        "id": id,
        "address": address,
    };

    var url = "/perf/dubbo/runById";

    $.ajax({
        type: "POST",
        url: url,
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        timeout: 20000,
        success: function (data) {
            $("#result").val(data.result);
            $("#response").val(data.responseContent);

            //显示模态框
            $("#runModal").modal({
                backdrop: 'static',
                keyboard: false
            });
        },
        error: function (e) {
            alert("fail:" + e)
            var json = JSON.stringify(e, null, 4);
            $("#result").val("FAIL");
            $("#respose").val(json)
            //显示模态框
            $("#runModal").modal({
                backdrop: 'static',
                keyboard: false
            });
        }

    });


}


//给按模块运行Dubbo的模态框传值
function runDubboWithModel(obj) {

    var modelName = $.trim($('#selectModelName').find("option:selected").text());
    var address = $.trim($('#interfaceCaseName').find("option:selected").text());


    var SAVEDATA = [];
    var requestData = {
        "modelName": modelName,
        "address": address,
    };
    SAVEDATA.push(requestData);
    var url = "/perf/dubbo/runByModel";
    $.ajax({
        type: "POST",
        url: url,
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        timeout: 20000,
        success: function (data) {

            var json = JSON.stringify(data, null, 4);
            $("#dubboResult").val("")
            $("#dubboResult").val(json)
            //显示模态框
            $("#runDubboModel").modal({
                backdrop: 'static',
                keyboard: false
            });
        },
        error: function (e) {
            alert("fail")
            var json = JSON.stringify(e, null, 4);
            $("#dubboResult").val("")
            $("#dubboResult").val(json)

        }

    });


}


// 切换List页面环境和接口
function switchListEnvAndInterface() {


    currentPage = 1;
    updatePageData();

}


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

    if ((currentPage > totalPage)) {
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

    var moduleId = $("#selectModelName").val();
    var envId = $("#interfaceCaseName").val();


    var url = "/perf/dubbo/getList?moduleId=" + moduleId + "&envId=" + envId + "&currentPage=" + currentPage + "&pageSize=10";

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

function content(caseList) {

    var contents = "";

    for (var i = 0; i < caseList.length; i++) {
        var c = caseList[i];
        contents += "<tr> <td>" + c.id + "</td>\n" +
            "                    <td>" + c.caseName + "</td>\n" +
            "                    <td>" + c.envId + "</td>\n" +
            "                    <td>" + c.name + "</td>\n" +
            "                    <td>" + c.params + "</td>\n" +
            "                    <td>" + c.checkData + "</td>\n" +
            "                    <td>\n" +
            "                       <a href=\"javascript:void(0)\"\n" +
            "                           data-id=\'" + c.id + "\'\n" +
            "                           class=\"btn btn-sm btn-success\"\n" +
            "                           onclick=\"runDubboModal(this)\">运行</a>\n" +
            "                        <a href=\"javascript:void(0)\"\n" +
            "                           data-id=\'" + c.id + "\'\n" +
            "                           data-caseName=\'" + c.caseName + "\'\n" +
            "                           data-envId=\'" + c.envId + "\'\n" +
            "                           data-modelId=\'" + c.model + "\'\n" +
            "                           data-interFaceClassName=\'" + c.interFaceClassName + "\'\n" +
            "                           data-methodName=\'" + c.methodName + "\'\n" +
            "                           data-requestType=\'" + c.requestType + "\'\n" +
            "                           data-checkData=\'" + c.checkData + "\'\n" +
            "                           data-params=\'" + c.params + "\'\n" +
            "                           data-type=\'" + c.type + "\'\n" +
            "                           data-version=\'" + c.version + "\'\n" +
            "                           class=\"btn  btn-sm btn-info \"\n" +
            "                           onclick=\"showUpdateModal(this)\">修改</a>\n" +
            "\n" +
            "                        <a href=\"javascript:void(0)\" data-id=\'" + c.id + "\'\n" +
            "                           class=\"btn btn-sm btn-danger\"\n" +
            "                           onclick=\"showDeleteInterface(this)\">删除</a>\n" +
            "                    </td></tr>\n";

    }

    return contents;


}


function search() {

    var caseName = $("#searchName").val().trim();

    if (caseName == null || caseName == "" || caseName == undefined) {
        alert("请输入用例名称");
        return
    }

    var url = "/perf/dubbo/findCaseByName?caseName=" + caseName;

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


//处理json数据，采用正则过滤出不同类型参数
function syntaxHighlight(json) {
    if (typeof json != 'string') {
        json = JSON.stringify(json, undefined, 2);
    }
    json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match + '</span>';
    });
};