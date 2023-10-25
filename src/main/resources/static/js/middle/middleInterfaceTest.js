function sendMiddleRequest() {

    var requestMethod = $.trim($('#requestMethod').val());
    var requestUrl = $.trim($('#requestUrl').val());
    var headersContent = $.trim($('#headersContent').val());
    var cookiesContent = $.trim($('#cookiesContent').val());
    var paramsContent = $.trim($('#paramsContent').val());
    var bodyContent = $.trim($('#bodyContent').val());
    var SAVEDATA = [];
    var isNull = "[]";

    if (!requestUrl) {
        alert("请输入URL");
        return false;
    }

    if (requestMethod == "Post-Json") {
        if (!bodyContent) {
            alert("请输入Body");
            return false;
        }

    }
    if (requestMethod == "Post-Form") {
        if (!paramsContent) {
            alert("请输入Params");
            return false;
        }

    }

    var requestData = {
        "method": requestMethod,
        "url": requestUrl,
        "header": headersContent,
        "params": paramsContent,
        "body": bodyContent,
        "cookie": cookiesContent,
    };
    SAVEDATA.push(requestData);

    $.ajax({
        type: "POST",
        url: "/perf/middle/runMiddleInterface",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        timeout: 60000,
        success: function (data) {

            $("#responseBodyContent").html("<pre>" + syntaxHighlight(JSON.parse(data.response)) + "</pre>");
            $("#responseCookiesContent").html("<pre>" + syntaxHighlight(data.cookies) + "</pre>");
            $("#responseHeadersContent").html("<pre>" + syntaxHighlight(data.headers) + "</pre>");
            $('#responseTab li:eq(0) a').tab('show');
        },
        error: function (e) {
            alert("request error==" + JSON.stringify(e));
        }

    });

}


//添加接口模态框
function showAddInterface(obj) {

    // 获取模块名称
    switchModule();

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


$(function () {
    $('#requestTab li:eq(0) a').tab('show');
    $('#responseTab li:eq(0) a').tab('show');
});



