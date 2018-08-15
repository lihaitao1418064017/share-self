$(function () {
    var ws_protocol = 'ws'; // ws 或 wss
    var ip = '127.0.0.1'
    var port = 9326
    var heartbeatTimeout = 5000; // 心跳超时时间，单位：毫秒
    var reconnInterval = 1000; // 重连间隔时间，单位：毫秒
    var binaryType = 'blob'; // 'blob' or 'arraybuffer';//arraybuffer是字节
    var handler = new DemoHandler()


    var tiows;
    /**
     * 点击注册
     */
    $("#register").click(function () {
        var self=$('#from').val();
        initWs(self);
        function initWs (self) {
            var queryString = 'id='+self;
            var param = null;
            tiows = new tio.ws(ws_protocol, ip, port, queryString, param, handler, heartbeatTimeout, reconnInterval, binaryType)
            tiows.connect()
        }
    });

    /**
     * 点击发送
     */
    $('#sendButton').click(function () {
        var m = $('#textId').val();
        var to =$('#to').val();
        var from=$('#from').val();
        var msgs={
            "id":1,
            "msg":m,
            "to":to,
            "action":1,
            "from":from,
            "status":0
        };
        var msgs=JSON.stringify(msgs);
        tiows.send(msgs);
    });






});