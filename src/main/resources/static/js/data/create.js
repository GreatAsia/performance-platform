//添加模态框
function showAddAccount() {

    $("#addModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}



function addData() {

    var userType = $.trim($('#userType').find("option:selected").text());
    var totalCount = $.trim($('#total').val());
    var envId = $.trim($('#envId').val());

    if (!userType) {
        alert("请输入用户类型");
        return false;
    }
    if (!totalCount) {
        alert("请输入账号数量");
        return false;
    }

    var url = "/perf/middle/account/createData?type=" + userType + "&count=" + totalCount + "&envId=" + envId;
    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        contentType: "application/json",
        timeout: 600000,
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


function runGenerateModal(obj) {

    var id = $(obj).attr("data-id");
    var type = $(obj).attr("data-userType");
    var count = $(obj).attr("data-totalCount");
    var envId = $(obj).attr("data-envId");

    var url = "/perf/middle/account/generate?id=" + id + "&type=" + type + "&count=" + count + "&envId=" + envId;
    //显示模态框
    $("#runCase").modal({
        backdrop: 'static',
        keyboard: false
    });
    $("#respose").val("请稍等...");
    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        contentType: "application/json",
        async: true,
        timeout: 6000000,
        success: function (data) {
            var json = JSON.stringify(data, null, 4);
            $("#respose").val(json);

        },
        error: function (e) {
            $("#respose").val(e.responseText);
        }

    });


}

function updateDataModal(obj) {

    var historyId = $(obj).attr("data-id");

    var url = "/perf/middle/account/updateAccountData?historyId=" + historyId;
    //显示模态框
    $("#updata").modal({
        backdrop: 'static',
        keyboard: false
    });
    $("#updataRespose").val("请稍等...");
    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        contentType: "application/json",
        async: true,
        timeout: 6000000,
        success: function (data) {
            var json = JSON.stringify(data, null, 4);
            $("#updataRespose").val(json);

        },
        error: function (e) {
            $("#updataRespose").val(e.responseText);
        }

    });


}