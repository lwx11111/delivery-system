/**
 *
 * @param token 标明客户端
 * @returns {WebSocket}
 */
export function createScoket(token) {
    var socket;
    if (typeof (WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
    } else {
        var host = window.location.origin.replace("http:", "ws:")
        //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
        socket = new WebSocket("ws://127.0.0.1:8000/ws/" + token);
        //打开事件
        socket.onopen = function () {
            console.log("Socket 已打开");
            socket.send("这是来自客户端的消息" + location.href + new Date());
        };

        //关闭事件
        socket.onclose = function () {
            console.log("Socket已关闭");
        };
        //发生了错误事件
        socket.onerror = function () {
            console.log("Socket发生了错误");
        }
        //窗口关闭
        // $(window).unload(function (event) {
        //     socket.close();
        // });
    }
    return socket;
}





