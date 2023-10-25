//请求数据
function catContent(obj) {

    var requestContent = $(obj).attr("data-content");
    $("#contentDetail").html(requestContent);
    //显示模态框
    $("#content").modal({
        backdrop: 'static',
        keyboard: false
    });

}

