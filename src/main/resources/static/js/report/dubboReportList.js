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

    var url = "/perf/dubbo/per/result/getlist?currentPage=" + currentPage + "&pageSize=10";

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

        var data;
        if (typeof(report[i].error_data) == "undefined") {
            data = "        <td></td>\n"
        } else {
            data = "        <td>" + report[i].error_data + "</td>\n"
        }

        contents += "<tr><td>" + report[i].id + "</td>\n" +
            "        <td>" + report[i].case_id + "</td>\n" +
            "        <td>" + report[i].case_name + "</td>\n" +
            "        <td>" + report[i].response_time + "</td>\n" +
            "        <td>" + report[i].throughput + "</td>\n" +
            "        <td>" + report[i].error_rate + "</td>\n" +
            data +
            "        <td>" + report[i].start_time + "</td>\n" +
            "        <td>" + report[i].end_time + "</td>\n" +
            "        <td><a href=\"/dubbo/per/report/detail/" + report[i].id + "\">查看报告</a></td>\n" +
            "        </td></tr>\n";

    }

    return contents;


}