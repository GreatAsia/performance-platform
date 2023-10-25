function showLogs() {
    console.log("查看日志");
    getLogLevel();
    //iframe层
    layer.open({
        type: 1,
        title: '<span class="laytit">接口实时日志</span>当前日志等级 <span class="logleveltext"></span>',
        shadeClose: false,
        shade: 0.7,
        maxmin: true,
        area: ['80%', '70%'],
        content: $("#logdiv").html(), //iframe的url
        btn: ['INFO', 'DEBUG', 'WARN','ERROR','刷新','清屏'], //可以无限个按钮
        cancel: function(index){
            closeSocket();
        },yes: function(index, layero){
            //按钮【按钮一】的回调
            changeLogLevel("INFO");
            $(".logleveltext").html('<button class="layui-btn" style="background-color: #AAC000;">INFO</button>');
            return false;
        },btn2: function(index, layero){
            //按钮【按钮二】的回调
            changeLogLevel("DEBUG");
            $(".logleveltext").html('<button class="layui-btn" style="background-color: #AAC000;">DEBUG</button>');
            return false;
        },btn3: function(index, layero){
            //按钮【按钮三】的回调
            changeLogLevel("WARN");
            $(".logleveltext").html('<button class="layui-btn" style="background-color: #BB872B;">WARN</button>');
            return false;
        },btn4: function(index, layero){
            //按钮【按钮四】的回调
            changeLogLevel("ERROR");
            $(".logleveltext").html('<button class="layui-btn" style="background-color: #D55E56;">ERROR</button>');
            return false;
        },btn5: function(index, layero){
            //按钮【按钮五】的回调
            closeSocket();
            openSocket();
            return false;
        },btn6: function(index, layero){
            //按钮【按钮六】的回调
            $("#log-container div").html("");
            return false;
        },min: function(index) { //点击最小化后的回调函数
            $(".layui-layer-title").html('接口日志');
            $(".layui-layer-shade").hide();
        },restore: function() { //点击还原后的回调函数
            getLogLevel();
            $(".layui-layer-title").html('<span class="laytit">接口实时日志</span>当前日志等级 <span class="logleveltext"></span>');
            $(".layui-layer-shade").show();
        }
    });
};

<!-- 日志实时推送业务处理 -->
var stompClient = null;

function openSocket() {
    if (stompClient == null) {
        if($("#log-container").find("span").length==0){
            $("#log-container div").after("<span>通道连接成功,静默等待.....</span><img src='images/loading.gif'>");
        }
        var socket = new SockJS('websocket?token=kl');
        stompClient = Stomp.over(socket);
        stompClient.connect({token: "kl"}, function (frame) {
            stompClient.subscribe('/topic/pullLogger', function (event) {
                var content = JSON.parse(event.body);
                var leverhtml = '';
                var className = '<span class="classnametext">' + content.className + '</span>';
                switch (content.level) {
                    case 'INFO':
                        leverhtml = '<span class="infotext">' + content.level + '</span>';
                        break;
                    case 'DEBUG':
                        leverhtml = '<span class="debugtext">' + content.level + '</span>';
                        break;
                    case 'WARN':
                        leverhtml = '<span class="warntext">' + content.level + '</span>';
                        break;
                    case 'ERROR':
                        leverhtml = '<span class="errortext">' + content.level + '</span>';
                        break;
                }
                $("#log-container div").append("<p class='logp'>" + content.timestamp + " " + leverhtml + " --- [" + content.threadName + "] " + className + " ：" + content.body + "</p>");
                if (content.exception != "") {
                    $("#log-container div").append("<p class='logp'>" + content.exception + "</p>");
                }
                if (content.cause != "") {
                    $("#log-container div").append("<p class='logp'>" + content.cause + "</p>");
                }
                $("#log-container").scrollTop($("#log-container div").height() - $("#log-container").height());
            }, {
                token: "kltoen"
            });
        });
    }
}

function closeSocket() {
    if (stompClient != null) {
        stompClient.disconnect();
        stompClient = null;
    }
}

function changeLogLevel(logLevel) {
    var data = {'configuredLevel' : logLevel};
    $.ajax({
        url : "loggers/com.okay",
        type : "post",
        dataType: 'json',
        contentType:"application/json",
        data : JSON.stringify(data),
        success : function(e) {
            layer.msg("日志等级变更为 "+logLevel, {icon: 1});
        }
    });
}

function getLogLevel() {
    $.get("loggers/com.okay", function(result){
        if(result.configuredLevel=="null"){
            defuiltLogLevel(result.effectiveLevel);
        }else{
            defuiltLogLevel(result.configuredLevel);
        }
    });
}

function defuiltLogLevel(logLevel) {
    switch (logLevel) {
        case 'INFO':
            $(".logleveltext").html('<button class="layui-btn" style="background-color: #AAC000;">'+logLevel+'</button>');
            break;
        case 'DEBUG':
            $(".logleveltext").html('<button class="layui-btn" style="background-color: #AAC000;">'+logLevel+'</button>');
            break;
        case 'WARN':
            $(".logleveltext").html('<button class="layui-btn" style="background-color: #BB872B;">'+logLevel+'</button>');
            break;
        case 'ERROR':
            $(".logleveltext").html('<button class="layui-btn" style="background-color: #D55E56;">'+logLevel+'</button>');
            break;
    }
}
