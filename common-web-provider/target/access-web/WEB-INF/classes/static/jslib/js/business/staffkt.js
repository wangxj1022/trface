var dataGrid;
var dataGrid1;
var dataGrid2;

$(function () {

    // 隐藏两窗口
    $('#currentDb1').dialog('close');
    $('#currentDb2').dialog('close');

    dataGrid = $('#dataGrid').datagrid({
        url : ctx+'/staffkt/dataGrid',
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
                field : 'staffktNumber',
                title : '员工编号',
                width : 180,
                align: 'center',
            }, {
                field : 'staffktName',
                title : '姓名',
                width : 120,
                align: 'center',
            },  {
                field: 'staffktDept',
                title: '部门',
                width: 150,
                align: 'center',
            },{
                field : 'isOutWork',
                title : '是否外勤',
                width : 100,
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
                field : 'staffktIcNo',
                title : '一卡通芯片号',
                width : 180,
                align: 'center',
            },{
                field : 'updateFlag',
                title : '同步标识',
                width : 120,
                align: 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '未同步';
                        case 1:
                            return '已同步';
                        default:
                            return '未同步';
                    }
                }
            },{
                field : 'syncRetryCnt',
                title : '同步尝试次数',
                width : 120,
                align: 'center',
            },{
                field : 'status',
                title : '状态',
                width : 100,
                align: 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '禁用';
                        case 1:
                            return '启用';
                        default:
                            return '禁用';
                    }
                }
            },{
                field : 'picStatus',
                title : '是否有照片',
                width : 100,
                align: 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '无';
                        case 1:
                            return '有';
                        default:
                            return '有';
                    }
                }
            }, {
                field : '_operate',
                title : '操作',
                width : 120,
                align: 'center',
                formatter:function (value, row, index) {
                    return '<button href="javascript:void(0)" onclick="previewPic('+row.id+')" > 编辑 </button>'
                        + '     <button href="javascript:void(0)" onclick="reAddFun('+row.id+')" > 重发 </button>';
                }
            }
        ] ],
        toolbar : '#toolbar'
    });
});


function reAddFun( id ) {
    $.messager.confirm('提示','您确定给该员工重新下发员工卡?',function(r){
        if (r){
            // 添加Loading效果
            $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
            $("<div class=\"datagrid-mask-msg\"></div>").html("下发中，请稍候...").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});

            $.post(ctx+'/staffkt/reAdd',{id:id},function (data) {
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


function searchFun() {
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    $('#dataGrid').datagrid('clearSelections');
}

function chearFun() {
    $('#searchForm').form('reset');
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
}

function searchCurrDbFun1() {
    dataGrid1.datagrid('load', $.serializeObject($('#searchForm1')));
    $('#searchDataGrid1').datagrid('clearSelections');
}

function chearCurrDbFun1() {
    $('#searchForm1').form('reset');
    $('#searchDataGrid1').datagrid('load', $.serializeObject($('#searchForm1')));    // 刷新页面
}

function searchCurrDbFun2() {
    dataGrid2.datagrid('load', $.serializeObject($('#searchForm2')));
    $('#searchDataGrid2').datagrid('clearSelections');
}

function chearCurrDbFun2() {
    $('#searchForm2').form('reset');
    $('#searchDataGrid2').datagrid('load', $.serializeObject($('#searchForm2')));    // 刷新页面
}




function addFun() {
    parent.$.modalDialog({
        title: '新增人员',
        width: 650,
        height: 350,
        href: ctx+'/staffkt/add',
        buttons : [ {
            text : '保存',
            iconCls: "icon-ok",
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = dataGrid;
                var f = parent.$.modalDialog.handler.find('#staffktAdd');
                f.submit();
            }
        }],
        onLoad:function () {
            // 给文件框绑定函数
            var file =parent.$.modalDialog.handler.find('#file');
            file.change(
                function() {
                    // 判断大小
                    var reader = new FileReader();
                    var f = this.files[0];
                    //reader.readAsDataURL(this.files[0]);
                    reader.onload = function(e) {
                        var dataSrc = e.target.result;
                        // 判断格式
                        if( dataSrc.indexOf("jpeg") == -1 ){
                            parent.$.messager.alert('提示',"图片请采用jpg/jpeg格式！", 'warning');
                            return;
                        }

                        var dataEnd = e.target.result.replace("data:image/jpeg;base64,", "");
                        // 判断图片大小
                        if ( f.size < 61440 ) {
                            parent.$.messager.alert('提示',"图片过小,请重新选择！", 'warning');
                            return;
                        }

                        if ( f.size > 204800 ) {
                            parent.$.messager.alert('提示',"图片过大,请重新选择！", 'warning');
                            return;
                        }

                        // 判断宽高
                        var image = new Image()
                        image.onload=function(){
                            var width = image.width;
                            var height = image.height;
                            if( width<640 || height <480 ){
                                parent.$.messager.alert('提示',"图片不合640×480 像素或 以上规格,请重新选择！", 'warning');
                                return;
                            }else{
                                parent.$.modalDialog.handler.find("#img").attr("src", dataSrc );
                                parent.$.modalDialog.handler.find("#serviceStaffPhoto").val( dataEnd );
                            }
                        };
                        image.src= dataSrc;
                        /*/!* 最小设定为50KB ,具体大小存在误差*!/
                        if ( e.target.result.length < 71200 ) {
                            alert("图片太小了,请重新选择");
                            return;
                        }

                        /!* 最大200KB, 具体大小此处存在误差  *!/
                        if ( e.target.result.length > 300000 ) {
                            alert("图片过大，请重新选择");
                            return;
                        }

                        parent.$.modalDialog.handler.find("#img").attr("src", e.target.result);
                        parent.$.modalDialog.handler.find("#staffktPhoto").val(
                            e.target.result.replace(
                                "data:image/jpeg;base64,", ""));*/
                    };
                    reader.readAsDataURL(f);
                }
            );

            var f = parent.$.modalDialog.handler.find('#staffktAdd');
            f.form({
                url : ctx+'/staffkt/save',
                onSubmit : function() {
                    var dialog = parent.$.modalDialog.handler.find('#staffktAdd');
                    // 校验
                    var result = f.form('validate');
                    if( result ){
                        // 按钮置灰，保存需要一定的等待时间
                        parent.$('.dialog-button a:eq(0)').linkbutton('disable');
                        // 添加Loading效果
                        $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo(f);
                        $("<div class=\"datagrid-mask-msg\"></div>").html("添加中，请稍候...").appendTo(f).css({display:"block",left:($(document.body).outerWidth(true) -900) / 2,top:($(window).height() - 300) / 2});
                    }
                    return result;
                },
                success : function(result) {
                    // 按钮置灰，保存需要一定的等待时间
                    parent.$('.dialog-button a:eq(0)').linkbutton('enable');
                    //去除Loading效果
                    f.children("div.datagrid-mask-msg").remove();
                    f.children("div.datagrid-mask").remove();

                    result = $.parseJSON(result);
                    if( result.success == true ){
                        // 添加通过
                        parent.$.messager.alert('成功', result.msg, 'info');
                        parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                        parent.$.modalDialog.handler.dialog('close');
                    }else{
                        // 添加失败
                        parent.$.messager.alert('失败',result.msg, 'error');
                        $('#dataGrid').datagrid('reload');
                        f.dialog('close');
                    }
                }
            });
        }
    });
    dataGrid.datagrid("clearChecked");
}


function addFromDbFun1() {
    $('#searchForm1').form('reset');    // 检索条件清空
    $('#currentDb1').dialog('open');     // 打开搜索框
    // 绑定事件
    dataGrid1 = $('#searchDataGrid1').datagrid({
        url : ctx+'/staff/dataGrid',
        striped : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        fitColumns : false,
        method: 'post',
        idField : 'id',
        sortName : 'create_time',
        sortOrder : 'desc',
        pageSize : 10,
        method:'post',
        pageList : [ 5, 10],
        columns : [ [{
            field: 'staffId',
            title: '员工编号',
            width: 180,
            align: 'center',
        }, {
            field : 'staffName',
            title : '姓名',
            width : 120,
            align: 'center',
        }, {
            field : 'deptId',
            title : '部门',
            width : 150,
            align: 'center',
        }, {
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
                        return '内勤';
                }
            }
        }, {
            field : 'staffCardId',
            title : '一卡通芯片号',
            width : 180,
            align: 'center',
        }
        ] ],
    });
            
}

function addFromDbFun2() {
    $('#searchForm2').form('reset');    // 检索条件清空
    $('#currentDb2').dialog('open');     // 打开搜索框
    // 绑定事件
    dataGrid2 = $('#searchDataGrid2').datagrid({
        url : ctx+'/serviceStaff/dataGrid',
        striped : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        fitColumns : false,
        method: 'post',
        idField : 'id',
        sortName : 'create_time',
        sortOrder : 'desc',
        pageSize : 10,
        method:'post',
        pageList : [ 5,10],
        columns : [ [
            {
                field : 'serviceStaffNumber',
                title : '员工编号',
                width : 180,
                align: 'center',
            }, {
                field : 'serviceStaffName',
                title : '姓名',
                width : 120,
                align: 'center',
            },  {
                field: 'serviceStaffDept',
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
                field : 'serviceStaffIcNo',
                title : '一卡通芯片号',
                width : 180,
                align: 'center',
            }
        ] ],
    });


}





function delFun() {
    var node = dataGrid.datagrid('getSelections');
    if(node.length==0 ){
        $.messager.alert('提示',"请选择数据！", 'warning');
    }else {

        $.messager.confirm('询问','您确定删除选中的记录?',function(r){
            if (r){
                var ids ="";
                for(var i = 0;i<node.length;i++){
                    ids=ids+node[i].id+",";
                }
                $.post(ctx+'/staffkt/delete',{logIds:ids},function(result){
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
    parent.$.modalDialog({
        title: '编辑',
        width: 600,
        height: 400,
        href: ctx+'/staffkt/preview?id=' + id,

        buttons : [ {
            text : '保存',
            iconCls: "icon-ok",
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = dataGrid;
                var f = parent.$.modalDialog.handler.find('#staffktDetail');
                f.submit();
            }
        }],

        onLoad:function () {
            var f = parent.$.modalDialog.handler.find('#staffktDetail');
            // curPhoto属性赋值
            var value = parent.$.modalDialog.handler.find("#img").attr("src");
            parent.$.modalDialog.handler.find("#staffktPhoto").val(value.replace(
                "data:image/jpeg;base64,", ""));

            // 给文件框绑定函数
            var file =parent.$.modalDialog.handler.find('#file');
            file.change(
                function() {
                    var reader = new FileReader();
                    reader.readAsDataURL(this.files[0]);
                    reader.onload = function(e) {
                        /* 最小设定为50KB ,具体大小存在误差*/
                        if (e.target.result.length < 71200) {
                            alert("图片太小了,请重新选择");
                            return;
                        }

                        /* 最大200KB, 具体大小此处存在误差  */
                        if (e.target.result.length > 300000 ) {
                            alert("图片过大，请重新选择");
                            return;
                        }
                        parent.$.modalDialog.handler.find("#img").attr("src", e.target.result);
                        parent.$.modalDialog.handler.find("#staffktPhoto").val(
                            e.target.result.replace(
                                "data:image/jpeg;base64,", ""));
                    };
                });

            f.form({
                url : ctx+'/staffkt/update',
                onSubmit : function() {
                    var dialog = parent.$.modalDialog.handler.find('#staffDetail');
                    // 校验
                    var result = f.form('validate');
                    if( result ){
                        // 按钮置灰，保存需要一定的等待时间
                        parent.$('.dialog-button a:eq(0)').linkbutton('disable');
                        // 添加Loading效果
                        $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo(f);
                        $("<div class=\"datagrid-mask-msg\"></div>").html("添加中，请稍候...").appendTo(f).css({display:"block",left:($(document.body).outerWidth(true) -900) / 2,top:($(window).height() - 300) / 2});
                    }
                    return result;
                },
                success : function(result) {
                    // 按钮置灰，保存需要一定的等待时间
                    parent.$('.dialog-button a:eq(0)').linkbutton('enable');
                    // 去除Loading效果
                    f.children("div.datagrid-mask-msg").remove();
                    f.children("div.datagrid-mask").remove();

                    result = $.parseJSON(result);
                    if( result.success == true ){
                        // 添加通过
                        parent.$.messager.alert('成功', result.msg, 'info');
                        //parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                        $('#dataGrid').datagrid('reload');
                        parent.$.modalDialog.handler.dialog('close');
                    }else{
                        // 添加失败
                        parent.$.messager.alert('失败',result.msg, 'error');
                        $('#dataGrid').datagrid('reload');
                        f.dialog('close');
                    }
                }
            });
        }

    });
}

