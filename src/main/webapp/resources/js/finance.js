var opened = false; //是否连接上服务器
var webSocket = new WebSocket('ws://10.0.0.200:12345');
var webSocket1 = new WebSocket('ws://10.0.0.11:10000/customer');
webSocket.onerror = function (event) {
    opened = false;
    onError(event);
};
webSocket1.onerror = function (event) {
    opened = false;
    onError(event);
};
webSocket.onopen = function (event) {
    opened = true;
};
webSocket1.onopen = function (event) {
    opened = true;
};
webSocket.onclose = function (event) {
    opened = false;
};
webSocket1.onclose = function (event) {
    opened = false;
};
webSocket.onmessage = function (event) {
    onMessage(event);
};
webSocket1.onmessage = function (event) {
    onMessage1(event);
};
function onError(event) {
    //连接错误
    // layer.msg("服务器连接失败,尝试重新连接......", { time: 3000, icon: 5 });
    opened = false;
}
function onMessage(event) {
    //data为消息内容，是一个json字符串
    var content = $(".has_new_withdraw").html();
    var soundUrl = "/resources/sounds/new_message.wav";

    if (null == content || 0 == content.length) {
        $(".has_new_withdraw").append("<div class=\"alert alert-info alert-custom alert-dismissible\" role=\"alert\" style=\"padding: 15px;font-size: 18px;\">" +
            "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">" +
            "<span aria-hidden=\"true\" style=\"top: 5px;right:15px;position: relative;font-size: 30px;\">×</span><span class=\"sr-only\">关闭</span></button>" +
            "<i class=\"fa fa-check-circle m-right-xs\" style=\"font-size: 27px;\"></i><strong>提示</strong> 有新的提现申请" +
            "</div><embed src=\"" + soundUrl + "\" autostart=\"true\" loop=\"true\" style=\"position: absolute;opacity: 0;\"></embed>");
    } else {
        $(".has_new_withdraw").html("");
        $(".has_new_withdraw").append("<div class=\"alert alert-warning alert-custom alert-dismissible\" role=\"alert\" style=\"padding: 15px;font-size: 18px;\">" +
            "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">" +
            "<span aria-hidden=\"true\" style=\"top: 5px;right:15px;position: relative;font-size: 30px;\">×</span><span class=\"sr-only\">关闭</span></button>" +
            "<i class=\"fa fa-check-circle m-right-xs\" style=\"font-size: 27px;\"></i><strong>警告</strong> 有多条提现未处理,请及时处理" +
            "</div><embed src=\"" + soundUrl + "\" autostart=\"true\" loop=\"true\" style=\"position: absolute;opacity: 0;\"></embed>");
    }
}
function onMessage1(event) {
    //data为消息内容，是一个json字符串
    var content = $(".has_new_customer").html();
    var soundUrl = "/resources/sounds/new_message.wav";

    if (null == content || 0 == content.length) {
        $(".has_new_customer").append("<div class=\"alert alert-info alert-custom alert-dismissible\" role=\"alert\" style=\"padding: 15px;font-size: 18px;\">" +
            "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">" +
            "<span aria-hidden=\"true\" style=\"top: 5px;right:15px;position: relative;font-size: 30px;\">×</span><span class=\"sr-only\">关闭</span></button>" +
            "<i class=\"fa fa-check-circle m-right-xs\" style=\"font-size: 27px;\"></i><strong>提示</strong> 有新的客服消息" +
            "</div><embed src=\"" + soundUrl + "\" autostart=\"true\" loop=\"true\" style=\"position: absolute;opacity: 0;\"></embed>");
    } else {
        $(".has_new_customer").html("");
        $(".has_new_customer").append("<div class=\"alert alert-warning alert-custom alert-dismissible\" role=\"alert\" style=\"padding: 15px;font-size: 18px;\">" +
            "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">" +
            "<span aria-hidden=\"true\" style=\"top: 5px;right:15px;position: relative;font-size: 30px;\">×</span><span class=\"sr-only\">关闭</span></button>" +
            "<i class=\"fa fa-check-circle m-right-xs\" style=\"font-size: 27px;\"></i><strong>警告</strong> 有多条客服消息未处理,请及时处理" +
            "</div><embed src=\"" + soundUrl + "\" autostart=\"true\" loop=\"true\" style=\"position: absolute;opacity: 0;\"></embed>");
    }
}