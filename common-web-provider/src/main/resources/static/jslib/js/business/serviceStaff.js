var dataGrid;
$(function () {

    // 先关闭两个弹窗
    $('#dd1').dialog('close');
    $('#dd2').dialog('close');
    $('#dd3').dialog('close');
    $('#dd4').dialog('close');
    $('#dd5').dialog('close');

    dataGrid = $('#dataGrid').datagrid({
        url : ctx+'/serviceStaff/dataGrid',
        striped : true,
        rownumbers : true,
        pagination : true,
        fitColumns : false,
        method: 'post',
        idField : 'id',
        sortName : 'create_time',
        sortOrder : 'desc',
        pageSize : 20,
        method:'post',
        pageList : [ 10, 20, 50, 100],
        columns : [ [
            {
                field: 'ck',
                width: 30,
                checkbox : true
            },{
                field : 'serviceStaffIcNo',
                title : '员工工号',
                width : 180,
                align: 'center',
            }, {
                field : 'serviceStaffName',
                title : '姓名',
                width : 120,
                align: 'center',
            },  {
                field: 'deptname',
                title: '部门',
                width: 150,
                align: 'center',
            },{
                field : 'isOutWork',
                title : '是否外勤',
                width : 120,
                align: 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '内勤';
                        case 1:
                            return '外勤';
                        default:
                            return '无';
                    }
                }
            },{
                field : 'status',
                title : '状态',
                width : 120,
                align: 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '禁用';
                        case 1:
                            return '启用';
                        default:
                            return '启用';
                    }
                }
            },{
                field : 'picStatus',
                title : '是否有照片',
                width : 120,
                align: 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '无';
                        case 1:
                            return '有';
                    }
                }
            },{
                field : 'serviceStaffNumber',
                title : '员工编号',
                width : 100,
                align: 'center',
            },
            {
                field : 'serviceStaffTel',
                title : '手机号',
                width : 180,
                align: 'center',
            },{
                field : '_operate',
                title : '操作',
                width : 180,
                align: 'center',
                formatter:function (value, row, index) {
                    return '<button href="javascript:void(0)" onclick="previewPic('+row.id+')" > 编辑 </button>'
                        + '     <button href="javascript:void(0)" onclick="reAddFun('+row.id+')" > 重发 </button>'
                        + '     <button href="javascript:void(0)" onclick="synchro('+row.id+')" > 同步状态 </button>';
                }
            }
        ] ],
        toolbar : '#toolbar'
    });

});

function exceltodata() {
    var f =$("#searchForm");
    if ($('#photoCover').val() == "") {
        $.messager.alert("温馨提示", "请选择Excel文件", "info");
        return false;
    }else{
        $.messager.confirm('温馨提示','请确认桌面上存放照片的文件夹名为：staffphoto，照片命名要和Excel文件中的员工工号对应，照片大小:30k-50k',function(r) {
            if (r) {
                var excelname = /^.*\.xlsx$|^.*\.xls$/;
                if (excelname.test($('#photoCover').val())) {
                    //表单提交
                    f.form("submit",{
                        url: ctx + '/serviceStaff/fileUpload',
                        onSubmit: function () {
                            var res = f.form('validate');
                            if (res) {
                                $("<div class=\"datagrid-mask\"></div>").css({
                                    display: "block",
                                    width: "100%",
                                    height: $(window).height()
                                }).appendTo("body");
                                $("<div class=\"datagrid-mask-msg\"></div>").html("正在导入...").appendTo("body").css({
                                    display: "block",
                                    left: ($(document.body).outerWidth(true) - 190) / 2,
                                    top: ($(window).height() - 45) / 2
                                });
                            }
                            return res;
                        },
                        success: function (res) {
                            //去除Loading效果
                            var showLoading = $("body");
                            showLoading.children("div.datagrid-mask-msg").remove();
                            showLoading.children("div.datagrid-mask").remove();
                            res = $.parseJSON(res);
                            if (res.success == true) {
                                parent.$.messager.alert('成功', res.msg, 'success');
                                $('#dataGrid').datagrid('reload');
                                return true;
                            } else {
                                parent.$.messager.alert('失败', res.msg, 'error');
                                return false;
                            }
                        }

                    });
                } else {
                    alert("请选择正确的excel文件格式(支持.xlsx或者.xls)");
                }

            }
        }
    )};
};

function ExcelImport(){

    $.messager.confirm('温馨提示','请确认桌面上的Excel文件名为：stafflist.xlsx，存放照片的文件夹名为：staffphoto,照片命名要和Excel文件中的员工编号对应,照片大小:30k-50k',function(r) {
        if (r) {
            var url = ctx + '/serviceStaff/excelImport'
            // 添加Loading效果
            $("<div class=\"datagrid-mask\"></div>").css({
                display: "block",
                width: "100%",
                height: $(window).height()
            }).appendTo("body");
            $("<div class=\"datagrid-mask-msg\"></div>").html("正在导入...").appendTo("body").css({
                display: "block",
                left: ($(document.body).outerWidth(true) - 190) / 2,
                top: ($(window).height() - 45) / 2
            });

            $.post(url, function (data) {

                //去除Loading效果
                var showLoading = $("body");
                showLoading.children("div.datagrid-mask-msg").remove();
                showLoading.children("div.datagrid-mask").remove();

                if (data.success == true) {
                    parent.$.messager.alert('成功', data.msg, 'success');
                    $('#dataGrid').datagrid('reload');
                    //document.getElementById('fileImport').value = null;
                    return true;
                } else {
                    parent.$.messager.alert('失败', data.msg, 'error');
                    return false;
                }
            });
        }
    });

}



function reAddFun( id ) {
    $.messager.confirm('温馨提示','您确定给该员工重新下发员工卡?',function(r){
        if (r){
            // 添加Loading效果
            $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
            $("<div class=\"datagrid-mask-msg\"></div>").html("下发中，请稍候...").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});

            $.post(ctx+'/serviceStaff/reAdd',{id:id},function (data) {
                // 去除Loading效果
                var showLoading=$("body");
                showLoading.children("div.datagrid-mask-msg").remove();
                showLoading.children("div.datagrid-mask").remove();

                if(data.success == true){
                    $.messager.alert('成功',data.msg, 'info');
                    $('#treeGrid').treegrid('reload');
                    return true;
                }else{
                    $.messager.alert('失败',data.msg, 'error');
                    return false;
                }
            });
        }
        treeGrid.treegrid("clearChecked");
    });
}


function quanbuFun(){
   // var node = dataGrid.datagrid('getSelections');
    /*if(node.length==0 )
    {
        $.messager.alert('提示', "请选择数据！", 'warning');
    }else{*/
        $.messager.confirm('询问','您确定要重发全部员工信息吗?',function(r){
            if (r) {
                // 添加Loading效果
                $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
                $("<div class=\"datagrid-mask-msg\"></div>").html("下发中，请稍候...").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});

               /* var ids = "";
                for (var i = 0; i < node.length; i++) {
                    ids = ids + node[i].id + ",";
                }*/
                $.post(ctx+'/serviceStaff/quanbuchongfa',function(result){
                    // 去除Loading效果
                    var showLoading=$("body");
                    showLoading.children("div.datagrid-mask-msg").remove();
                    showLoading.children("div.datagrid-mask").remove();

                    if (result.success){
                        $.messager.alert('成功',result.msg, 'info');
                        $('#dataGrid').datagrid('reload');
                        $('#dataGrid').datagrid('clearSelections');
                    } else {
                        $.messager.alert('失败',result.msg, 'error');
                    }
                },'json');
            }
        });

   // }
}

var sync;
function synchro(id) {
    //$('#synchroEnit').form('reset');
    $('#dd5').dialog('open');
    sync = $('#synchroEnitId').datagrid({
        url : ctx+'/serviceStaff/selectByIdCardNo?id='+id,
        striped : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        fitColumns : false,
        method: 'post',
        idField : 'id',
        sortName : 'create_time',
        sortOrder : 'desc',
        pageSize : 5,
        method:'post',
        pageList : [ 5, 10, 15, 20],
        columns : [ [
            {
                field : 'deviceid',
                title : '设备编号',
                width : 100,
                align: 'center'
            },
            {
                field : 'updatetime',
                title : '同步时间',
                width : 100,
                align: 'center'
            },{
                field : 'syncflag',
                title : '同步状态',
                width : 100,
                align: 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '未同步';
                        case 1:
                            return '同步成功';
                        case 100:
                            return '同步成功';
                        default:
                            return '同步失败';
                    }
                }
            },{
                field : 'errmsg',
                title : '同步信息',
                width : 100,
                align: 'center',
            },
        ] ]
    });


}




function searchFun() {
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    $('#dataGrid').datagrid('clearSelections');
}

function chearFun() {
    $('#searchForm').form('reset');
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
}


function addFun() {
    $('#serviceStaffAdd').form('reset');
    $('#dd1').dialog('open');

    $("#serviceStaffDept").combotree({
        url: ctx+'/organization/tree',
        valueField: 'id',
        textField: 'text',
        required: true,
        editable: false,
        onlyLeafCheck: true,
        cascaseCheck: true,
        method: 'post',
        onBeforeSelect: function (node) {
            if (!$(this).tree('isLeaf', node.target)) {
                parent.$.messager.alert("提示","请选择子部门","warning");
                return false;//只可以选择tree的叶子
            }
        }
    });

    // 图片信息添加
    var file =$('#file');
    file.change(
        function() {
            // 判断大小
            var reader = new FileReader();
            var f = this.files[0];
            reader.onload = function (e) {
                var dataSrc = e.target.result;
                // 判断格式
                if( dataSrc.indexOf("jpeg") == -1 ){
                    parent.$.messager.alert('提示',"图片请采用jpg/jpeg格式！", 'warning');
                    return;
                }

                var dataEnd = e.target.result.replace("data:image/jpeg;base64,", "");
                // 判断图片大小
                if ( f.size < 30720 ) {
                    parent.$.messager.alert('提示',"图片小于30KB,请重新选择！", 'warning');
                    return;
                }

                if ( f.size > 204800 ) {
                    parent.$.messager.alert('提示',"图片大于200KB,请重新选择！", 'warning');
                    return;
                }

                var arrOne;
                var arrTwo;
                $.ajax({
                    url: ctx+'/serviceStaff/imageList',
                    async: false,
                    success:function (data) {
                        var obj = eval(data);
                        var arrList=obj.pValue;
                        var arr=arrList.split(",");
                        arrOne = arr[0];
                        arrTwo = arr[1];
                    }
                });
                var image = new Image()
                image.onload=function(){
                    var width = image.width;
                    var height = image.height;

                    if( width <arrOne || height <arrTwo){
                        parent.$.messager.alert('提示',"图片不合"+arrOne+" x "+arrTwo+"像素或 以上规格,请重新选择或者设置照片像素！", 'warning');
                        return;
                    }else{
                        $("#img").attr("src", dataSrc );
                        $("#serviceStaffPhoto").val( dataEnd );
                    }
                };
                image.src= dataSrc;

            };
            reader.readAsDataURL(f);
        }
    );

    // 表格信息加载
    var f = $('#serviceStaffAdd');
    f.form({
        url : ctx+'/serviceStaff/insertPerson',
        onSubmit : function() {
            // 校验
            var result = f.form('validate');
            if( result ){
                // 按钮置灰，保存需要一定的等待时间
                $('.dialog-button a:eq(0)').linkbutton('disable');
                // 添加Loading效果
                $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo(f);
                $("<div class=\"datagrid-mask-msg\"></div>").html("添加中，请稍候...").appendTo(f).css({display:"block",left:($(document.body).outerWidth(true) -900) / 2,top:($(window).height() - 300) / 2});
            }
            return result;
        },
        success : function(result) {
            // 按钮置灰，保存需要一定的等待时间
            $('.dialog-button a:eq(0)').linkbutton('enable');
            //去除Loading效果
            f.children("div.datagrid-mask-msg").remove();
            f.children("div.datagrid-mask").remove();

            result = $.parseJSON(result);
            if( result.success == true ){
                $('#dd1').dialog('close');
                $('#dataGrid').datagrid('reload');
                // 添加通过
                $.messager.alert('成功', result.msg, 'info');
                $('#dd1').dialog('close');
            }else{
                // 添加失败
                $.messager.alert('失败',result.msg, 'error');
                $('#dataGrid').datagrid('reload');
                f.dialog('close');
            }
        }
    });
    dataGrid.datagrid("clearChecked");
}


function delFun() {
    var node = dataGrid.datagrid('getSelections');
    if(node.length==0 ){
        $.messager.alert('温馨提示',"请选择数据！", 'warning');
    }else if(node.length>1 ){
        $.messager.alert('温馨提示',"不能删除多行", 'warning');
    }else {
        $.messager.confirm('询问','您确定删除此用户[ '+ node[0].serviceStaffName +' ]?',function(r){
            if (r){
                var ids ="";
                for(var i = 0;i<node.length;i++){
                    ids=ids+node[i].id+",";
                }
                $.post(ctx+'/serviceStaff/delete',{logIds:ids},function(result){
                    if (result.success){
                        $.messager.alert('成功',result.msg, 'info');
                        $('#dataGrid').datagrid('reload');
                        $('#dataGrid').datagrid('clearSelections');
                    } else {
                        $.messager.alert('失败',result.msg, 'error');
                    }
                },'json');
            }
        });
    }
}


function previewPic( id ){
    $('#serviceStaffEdit').form('reset');

    $("#serviceStaffDept1").combotree({
        url: ctx+'/organization/tree',
        valueField: 'text',
        textField: 'text',
        required: true,
        editable: false,
        onlyLeafCheck: true,
        cascaseCheck: true,
        method: 'post',
        onBeforeSelect: function (node) {
            if (!$(this).tree('isLeaf', node.target)) {
                parent.$.messager.alert("温馨提示","请选择子部门","warning");
                return false;//只可以选择tree的叶子
            }
        },
    });

    // 照片函数绑定
    var file1 =$('#file1');
    file1.change(
        function() {
            // 判断大小
            var reader = new FileReader();
            var f = this.files[0];
            reader.onload = function (e) {
                var dataSrc = e.target.result;
                // 判断格式
                if( dataSrc.indexOf("jpeg") == -1 ){
                    parent.$.messager.alert('温馨提示',"图片请采用jpg/jpeg格式！", 'warning');
                    return;
                }

                var dataEnd = e.target.result.replace("data:image/jpeg;base64,", "");
                // 判断图片大小
                if ( f.size < 30720 ) {
                    parent.$.messager.alert('温馨提示',"图片小于30KB,请重新选择！", 'warning');
                    return;
                }

                if ( f.size > 204800 ) {
                    parent.$.messager.alert('温馨提示',"图片大于200KB,请重新选择！", 'warning');
                    return;
                }

                // 判断宽高
                var arrOne;
                var arrTwo;
                $.ajax({
                    url: ctx+'/serviceStaff/imageList',
                    async: false,
                    success:function (data) {
                        var obj = eval(data);
                        var arrList=obj.pValue;
                        var arr=arrList.split(",");
                        arrOne = arr[0];
                        arrTwo = arr[1];
                    }
                });
                var image = new Image()
                image.onload=function(){
                    var width = image.width;
                    var height = image.height;

                    if( width <arrOne || height <arrTwo){
                        parent.$.messager.alert('温馨提示',"图片不合"+arrOne+" x "+arrTwo+"像素或 以上规格,请重新选择或者设置照片像素！", 'warning');
                        return;
                    }else{
                        $("#img1").attr("src", dataSrc );
                        $("#serviceStaffPhoto1").val( dataEnd );
                    }
                };
                image.src= dataSrc;


            };
            reader.readAsDataURL(f);
        });


    $.ajax({
        url : ctx+'/serviceStaff/preview?id=' + id,
        success:function(msg){
            var obj = eval(msg);
            var data = [];
            data.push({ "text": obj.deptname, "id": obj.serviceStaffDept });
            // 部门赋值
            var f = $('#serviceStaffDept1');
            f.combobox("setValue", data);    // easyui需要转化一下
            // id赋值
            $("#id1").val(obj.id);
            //  姓名赋值
            $("#serviceStaffName1").textbox('setValue',obj.serviceStaffName);
            //部门赋值
            //$("#serviceStaffDept1").textbox('setValue',obj.serviceStaffDept);
            // 员工编号赋值
            $("#serviceStaffNumber1").textbox('setValue',obj.serviceStaffNumber);
            //  一卡通芯片号赋值
            $("#serviceStaffIcNo1").textbox('setValue',obj.serviceStaffIcNo);
            //  照片赋值
            $("#img1").attr("src", "data:image/jpeg;base64," + obj.serviceStaffPhoto);
            // 内勤 外勤
            if( obj.isOutWork == 0 ){
                // 外勤
                $('#isOutWork1').combobox('setValue',obj.isOutWork);
                $('#isOutWork1').combobox('setText', "内勤");
            }else if( obj.isOutWork == 1 ){
                // 内勤
                $('#isOutWork1').combobox('setValue',obj.isOutWork);
                $('#isOutWork1').combobox('setText', "外勤");
            }else{
                $('#isOutWork1').combobox('setValue',obj.isOutWork);
                $('#isOutWork1').combobox('setText', "无");
            }
            $("#serviceStaffTel1").textbox('setValue',obj.serviceStaffTel);


            /*// 状态赋值
            var data1 = [];
            data1.push({ "text": obj.status, "id": obj.status });
            $("#status1").combobox("setValue", data1);  // easyui需要转化一下*/

            // base64值
            $("#serviceStaffPhoto1").val(obj.serviceStaffPhoto);
        }
    });

    // 打开窗口
    $('#dd3').dialog('open');

    // 按钮信息加载
    var f = $('#serviceStaffEdit');
    f.form({
        url : ctx+'/serviceStaff/update',
        onSubmit : function() {
            // 校验
            var result = f.form('validate');
            if( result ){
                // 按钮置灰，保存需要一定的等待时间
                $('.dialog-button a:eq(0)').linkbutton('disable');
                // 添加Loading效果
                $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo(f);
                $("<div class=\"datagrid-mask-msg\"></div>").html("添加中，请稍候...").appendTo(f).css({display:"block",left:($(document.body).outerWidth(true) -900) / 2,top:($(window).height() - 300) / 2});
            }
            return result;
        },
        success : function(result) {
            // 按钮置灰，保存需要一定的等待时间
            $('.dialog-button a:eq(0)').linkbutton('enable');
            //去除Loading效果
            f.children("div.datagrid-mask-msg").remove();
            f.children("div.datagrid-mask").remove();

            result = $.parseJSON(result);
            if( result.success == true ){
                // 添加通过
                $('#dd3').dialog('close');
                $('#dataGrid').datagrid('reload');
                // 添加通过
                $.messager.alert('成功', result.msg, 'info');
                $('#dd3').dialog('close');
            }else{
                // 添加失败
                $.messager.alert('失败',result.msg, 'error');
                $('#dataGrid').datagrid('reload');
                $('#dd3').dialog.dialog('close');
            }
        }
    });
}



// 添加员工方法
function openhighmeter(type) {
    if (0 == type) {
        connect('img_auth', 'imageAuth');
    } else if (1 == type) {
        connect('img', 'image');
    } else if (2 == type) {
        connect('img_idCard', 'idCardImage');
    }
}

// 修改员工方法
function openhighmeter1(type) {
    if (0 == type) {
        connect1('img_auth', 'imageAuth');
    } else if (1 == type) {
        connect1('img', 'image');
    } else if (2 == type) {
        connect1('img_idCard', 'idCardImage');
    }
}

function clickPic(){
    $("#img").click();
}

function clickPic1(){
    $("#img1").click();
}

var socket;
var img_id;
var input_id;

/**
 * 连接高拍仪
 */

function connect(img, input) {

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
                            $('#dd2').dialog('open');
                            var data = JSON.parse(request.responseText);
                            console.log(data);
                            for (var i = 0; i < data.length; i++) {
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
        $("#highImg").attr("src", "data:image/jpeg;base64," + res.frame);
    });
}


// 连接高拍仪： 编辑方法

/**
 * 连接高拍仪
 */
function connect1(img, input) {

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
                            $('#dd4').dialog('open');
                            var data = JSON.parse(request.responseText);
                            console.log(data);
                            for (var i = 0; i < data.length; i++) {

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
        $("#highImg1").attr("src", "data:image/jpeg;base64," + res.frame);
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
    $("#highImg").removeAttr("src");
}


function closePhoto1() {
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
        // setTimeout("$('#highmeterModal').modal('hide')", 500);
    }
    $("#highImg1").removeAttr("src");
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
                /*$("img[name=" + img_id + "]").attr("src", "data:image/jpeg;base64," + photo);*/
                /*$("input[name=" + input_id + "]").val(photo);*/
                // 照片
                $("#img").attr("src", "data:image/jpeg;base64," + photo);
                // base64值
                $("#serviceStaffPhoto").val(photo);
                closePhoto();
                $('#dd2').dialog('close');
            }
        }
    };
    photograph_request.open("POST", url, true);
    photograph_request.send(JSON.stringify({"format": ".jpg", "width": 1024, "height": 768, "quality": 85}));
}


function photograph1() {
    console.log(img_id);
    console.log(input_id);
    var url = "http://localhost:20000/video_photo";
    var photograph_request = new XMLHttpRequest();
    photograph_request.onreadystatechange = function () {
        if (photograph_request.readyState == 4) {
            if (200 == photograph_request.status) {
                var data = JSON.parse(photograph_request.responseText)["data"];
                var photo = data["photo"];
                /*$("img[name=" + img_id + "]").attr("src", "data:image/jpeg;base64," + photo);*/
                /*$("input[name=" + input_id + "]").val(photo);*/
                // 照片
                $("#img1").attr("src", "data:image/jpeg;base64," + photo);
                // base64值
                $("#serviceStaffPhoto1").val(photo);
                closePhoto1();
                $('#dd4').dialog('close');
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




