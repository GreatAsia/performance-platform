var moduleId = 1;
var envName = "dev";
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

    moduleId = $('#selectModelName').val();
    envName = $.trim($('#selectAddress').find("option:selected").text());

    var url = "/perf/dubbo/report/getList?moduleId=" + moduleId + "&env=" + envName + "&currentPage=" + currentPage + "&pageSize=10";

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

function content(report) {

    var contents = "";

    for (var i = 0; i < report.length; i++) {

        var result = "";
        if (report[i].result == "FAIL") {

            result = "<td style=\"font-weight:bold;color:red\"> " + report[i].result + "</td>\n";
        } else {

            result = "<td style=\"font-weight:bold;color:green\"> " + report[i].result + "</td>\n";
        }

        contents += "<tr><td>" + report[i].id + "</td>\n" +
            "        <td>" + report[i].modelName + "</td>\n" +
            "        <td>" + report[i].env + "</td>\n" +
            "        <td>" + report[i].startTime + "</td>\n" +
            "        <td>" + report[i].totalCase + "</td>\n" +
            "        <td>" + report[i].passCase + "</td>\n" +
            "        <td>" + report[i].failCase + "</td>\n" +
            result +
            "        <td>\n" +
            "        <a href=\"/dubbo/report/detail/" + report[i].id + "\">查看报告</a>\n" +
            "        </td></tr>\n";

    }

    return contents;


}