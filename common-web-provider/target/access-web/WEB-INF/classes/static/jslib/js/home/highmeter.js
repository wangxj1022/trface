var socket;
var img_id;
var input_id;

/**
 * 连接高拍仪
 */
function connect(img, input) {

    alert("connect...")
    img_id = img;
    input_id = input;
    this.options = {'timeout': 1500, 'reconnection': false};
    socket = io.connect('http://localhost:20000/video_preview', options);
    //start preview
    socket.on("connected", function (res) {//响应连接
        console.log('.................................................connected');
        if (res['status'] == 0) {
            //关闭摄像头
            var url = "http://localhost:20000/video_close";
            var request = new XMLHttpRequest();
            request.open("post", url, false);
            request.send(null);
            if (request.readyState == 4) {
                if (200 == request.status) {
                    console.log('.............关闭摄像头成功');
                    var request = new XMLHttpRequest();
                    url = "http://localhost:20000/video_list";
                    request.open("post", url, false);
                    request.send(null);
                    if (request.readyState == 4) {
                        if (200 == request.status) {
                            $('#highmeterModal').modal('show');
                            var data = JSON.parse(request.responseText);
                            console.log(data);
                            for (var i = 0; i < data.length; i++) {
                                // if(data[i].name=='USB Camera'){
                                //        console.log("........开启主摄像头"+data[i].id);
                                //        socket.emit('start preview',{'id':data[i].id,'width':1024,"height":768});
                                //        console.log();
                                //        break;
                                //    }

                                if (input == 'image' && (data[i].name == 'S520-2' || data[i].name == 'T500W')) {//现场照片
                                    console.log("........开启辅摄像头" + data[i].id);
                                    socket.emit('start preview', {'id': data[i].id, 'width': 1024, "height": 768});
                                    break;
                                } else if (input == 'imageAuth' && data[i].name == 'USB Camera') {//授权书
                                    console.log("........开启主摄像头" + data[i].id);
                                    socket.emit('start preview', {'id': data[i].id, 'width': 1024, "height": 768});
                                    break;
                                }
                                console.log(input);

                            }
                        }
                    }
                }
            }
        }
    });
    socket.on("connect_timeout", function () {
        toastr.warning("连接高拍仪服务超时", "请检查服务是否启动");
    });
    socket.on("preview frame", function (res) {//响应连接
        console.log(res);
        $("#img").attr("src", "data:image/jpeg;base64," + res.frame);
    });
}

function start_camera(type) {
    console.log("..................切换摄像头");
    //关闭摄像头
    var url = "http://localhost:20000/video_close";
    var request = new XMLHttpRequest();
    request.open("post", url, false);
    request.send(null);
    if (request.readyState == 4) {
        if (200 == request.status) {
            console.log('.............关闭摄像头成功');
            //获取摄像头
            url = "http://localhost:20000/video_list";
            request.open("post", url, false);
            request.send(null);
            if (request.readyState == 4) {
                if (200 == request.status) {
                    var data = JSON.parse(request.responseText);
                    console.log(data);
                    for (var i = 0; i < data.length; i++) {
                        if (type == 0 && (data[i].name == 'S520-2' || data[i].name == 'T500W')) {
                            console.log("........开启辅摄像头" + data[i].id);
                            socket.emit('start preview', {'id': data[i].id, 'width': 1024, "height": 768});
                            break;
                        } else if (type == 1 && data[i].name == 'USB Camera') {
                            console.log("........开启主摄像头" + data[i].id);
                            socket.emit('start preview', {'id': data[i].id, 'width': 1024, "height": 768});
                            break;
                        }
                    }
                }
            }
        }
    }
}

var angle = 0;

/**
 * 旋转头像
 */
function set_rotate() {
    var url = "http://localhost:20000/video_set_rotate";
    var rotate_request = new XMLHttpRequest();
    rotate_request.open("POST", url, true);
    angle = angle + 90;
    var param = {"angle": angle, "center": [-1, -1]};
    rotate_request.send(JSON.stringify(param));
}

function closePhoto() {
    var url = "http://localhost:20000/video_close";
    var request = new XMLHttpRequest();
    request.open("post", url, false);
    request.send(null);
    if (request.readyState != 4 || request.status != 200) {
        //关闭异常提示
        console.log(request.responseText);
        if (data.result != 220000 && data.result != 220001) {
            toastr.error("关闭摄像头失败，请您稍后尝试");
        }
    } else {
        console.log(".........关闭摄像头成功");
        //
        // setTimeout("$('#highmeterModal').modal('hide')", 500);
    }
    $("#img").removeAttr("src");
}

/**
 * 水印
 */
function set_watermark() {
    var url = "http://localhost:20000/video_watermark";
    var watermark_request = new XMLHttpRequest();
    watermark_request.open("POST", url, true);
    param = {"is": true, "text": "北京科技有限公司", "position": [100, 100], "size": 50, "color": [200, 0, 0]}
    watermark_request.send(JSON.stringify(param))
    if (watermark_request.readyState != 4 || watermark_request.status != 200) {
        var data = JSON.parse(watermark_request.responseText);
        if (data.result != 220070) {
            //关闭异常提示
            toastr.error("设置水印失败，请您稍后尝试");
        }
    }
}

/**
 * 剪裁
 */
function photo_mode(type) {
    var url = "http://localhost:20000/video_photo_mode";
    var mode_request = new XMLHttpRequest();
    mode_request.open("POST", url, true);
    mode_request.send(JSON.stringify({"photo_mode": type}));
    if (mode_request.readyState != 4 || mode_request.status != 200) {
        var data = JSON.parse(mode_request.responseText);
        if (data.result == 220001) {
            toastr.error("未开启预览，请您稍后尝试");
        } else if (data.result != 220020) {
            //关闭异常提示
            toastr.error("设置裁剪失败，请您稍后尝试");
        }
    }
}

/**
 * 拍照
 */
function photograph() {
    console.log(img_id);
    console.log(input_id);
    var url = "http://localhost:20000/video_photo";
    var photograph_request = new XMLHttpRequest();
    photograph_request.onreadystatechange = function () {
        if (photograph_request.readyState == 4) {
            if (200 == photograph_request.status) {
                var data = JSON.parse(photograph_request.responseText)["data"];
                var photo = data["photo"];

                $("img[name=" + img_id + "]").attr("src", "data:image/jpeg;base64," + photo);
                $("input[name=" + input_id + "]").val(photo);
                closePhoto();
                $('#highmeterModal').modal('hide');
            }
        }
    };
    photograph_request.open("POST", url, true);
    photograph_request.send(JSON.stringify({"format": ".jpg", "width": 1024, "height": 768, "quality": 85}));
}

function readIdCardH() {
    var url = "http://localhost:20000/read-id-card";
    var readIdCard_request = new XMLHttpRequest();
    try {
        readIdCard_request.timeout = 3000; // 超时时间，单位是毫秒
        readIdCard_request.ontimeout = function (e) {
            readIdCard_request.abort();
            toastr.warning("连接高拍仪服务超时,请检查高拍仪服务是否启动");
        };
    } catch (e) {
        console.log(e);
    }
    readIdCard_request.onreadystatechange = function () {
        if (readIdCard_request.readyState == 4) {
            if (200 == readIdCard_request.status) {
                var data = JSON.parse(readIdCard_request.responseText)["data"];
                if (data == undefined) {
                    toastr.warning("未读取到身份证信息，请将放好身份证位置");
                    return;
                }
                if ($("input[name=name]") != undefined) {
                    $("input[name=name]").val(data.name_cn);
                }
                if ($("input[name=agentName]") != undefined) {
                    $("input[name=agentName]").val(data.name_cn);
                }
                $("input[name=idCardNum]").val(data.id_card_no);
            }
        }
    };
    readIdCard_request.open("POST", url, true);
    readIdCard_request.send(null);
}