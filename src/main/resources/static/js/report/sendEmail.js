//发送邮件
var request;

function sendEmail() {
    var sendTo = $('#sendTo').val();
    if (!sendTo) {
        alert("请输入收件人");
        return false;
    }
    var query = window.location.pathname;

    if (query.indexOf("middle") > 0) {
        request = middleContent(query);

    } else if (query.indexOf("uipad") > 0) {
        request = uiContent(query);

    } else if (query.indexOf("dubbo") > 0) {

        request = dubboContent(query);

    } else {
        console.log("URL不存在关键词");
    }
    send(request);


};

function middleContent(query) {

    var sendTo = $('#sendTo').val();
    var title = "中间层压力测试报告";
    var item = "middle";
    var id = query.split("/");
    var historyId = id[id.length - 1];


    var requestData = {
        "sendTo": sendTo,
        "title": title,
        "item": item,
        "historyId": historyId
    };

    return requestData;


}

function uiContent(query) {

    var sendTo = $('#sendTo').val();
    var title = "PAD自动化测试报告";
    var item = "app";
    var id = query.split("/");
    var historyId = id[id.length - 1];


    var requestData = {
        "sendTo": sendTo,
        "title": title,
        "item": item,
        "historyId": historyId
    };

    return requestData;


}

function dubboContent(query) {

    var sendTo = $('#sendTo').val();
    var title = "后端自动化测试报告";
    var item = "dubbo";
    var id = query.split("/");
    var historyId = id[id.length - 1];


    var requestData = {
        "sendTo": sendTo,
        "title": title,
        "item": item,
        "historyId": historyId
    };
    console.log("[requestData]" + JSON.stringify(requestData));
    return requestData;

}


function send(requestData) {

    $.ajax({
        type: "POST",
        url: "/sendEmail",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        timeout: 60000,
        success: function (data) {
            if (data.code == 200) {
                alert("发送成功");
            } else {
                alert("发送失败：" + JSON.stringify(data));
            }

        },
        error: function (e) {
            alert("发送失败：" + e);
        }
    });
}
