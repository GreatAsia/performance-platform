var addObj;

//添加模块模态框
function showAddModal(obj) {
    addObj = obj;
    $("#addModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}

//添加模块
function addDubboModel() {

    var NAME = $.trim($('#addModelName').val());
    var COMMENTS = $.trim($('#addModelComments').val());
    if (!NAME) {
        alert("请输入模块名");
        return false;
    }
    if (!COMMENTS) {
        alert("请输入备注");
        return false;
    }

    var SAVEDATA = [];
    var requestData = {
        "name": NAME,
        "comments": COMMENTS,
    };
    SAVEDATA.push(requestData);

    $.ajax({
        type: "POST",
        url: "/perf/dobbo/module/insert",
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
        url: "/perf/dubbo/module/delete",
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
function showUpdateModal(obj) {
    updateObj = obj;
    var id = $(obj).attr("data-id");
    var name = $(obj).attr("data-name");
    var comments = $(obj).attr("data-comments");
    $("#updateID").val(id);
    $("#updateModelName").val(name);
    $("#updateModelComments").val(comments);

    $("#updateModal").modal({
        backdrop: 'static',
        keyboard: false
    });

}

//更新模块
function updateDubboModel() {

    var name = $('#updateModelName').val().trim();
    var comments = $('#updateModelComments').val().trim();
    var id = $('#updateID').val().trim();


    if (!name) {
        alert("请输入模块名");
        return false;
    }
    if (!comments) {
        alert("请输入备注");
        return false;
    }
    var requestData = {
        "id": id,
        "name": name,
        "comments": comments,
    };
    $.ajax({
        type: "POST",
        url: "/perf/dubbo/module/update",
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

