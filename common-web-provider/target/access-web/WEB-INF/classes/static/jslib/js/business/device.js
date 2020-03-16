var dataGrid;
$(function () {
    dataGrid = $('#dataGrid').datagrid({
        url : ctx+'/device/dataGrid',
        striped : true,         //行条纹化
        rownumbers : true,      //显示带有行号的列
        pagination : true,      //分页工具栏
        singleSelect : true,    //只允许选中一行
        fitColumns : false,     //列自动展开/折叠以适应数据网格
        method: 'post',
        idField : 'id',         //指示哪个字段是标识字段
        pageSize : 20,          //初始化页面尺寸
        pageList : [ 10, 20, 50, 100],     //初始化页面尺寸的选择列表
        columns : [ [
            /*{
                field : 'entryId',
                title : '所属进出口',
                width : 100,
                align: 'center',
            },*/
            /*{
                field : 'deviceName',
                title : '设备名称',
                width : 100,
                align: 'center',
            },*/
            {
                field : 'deviceId',
                title : '设备编号',
                width : 120,
                align: 'center',
            },
            {
                field : 'deviceIp',
                title : 'IP地址',
                width : 140,
                align: 'center',
            },
            {
                field : 'devicePort',
                title : '端口号',
                width : 80,
                align: 'center',
            },
            {
                field : 'memory',
                title : '设备用户名',
                width : 120,
                align: 'center',
            },
            {
                field : 'disk',
                title : '设备密码',
                width : 120,
                align: 'center',
            },
            {
                field : 'direction',
                title : '通道方向',
                width : 100,
                align: 'center',
                formatter:function (value,row,index) {
                    switch (value) {
                        case 0:
                            return '进口';
                        case 1:
                            return '出口';
                        default:
                            return '进口';
                    }
                }
            },
            {
                field : 'passagewayId',
                title : '通道编号',
                width : 120,
                align: 'center',
            },
            /*{
                field : 'runMode',
                title : '通信状态',
                width : 100,
                align: 'center',
                formatter:function (value, row, index) {
                    switch(value) {
                        case 0:
                            return '离线';
                        case 1:
                            return '在线';
                        default:
                            return '在线';

                    }
                }
            },*/
            {
                field : 'activeStatus',
                title : '设备状态',
                width : 100,
                align: 'center',
                formatter:function (value, row, index) {
                    switch(value) {
                        case 0:
                            return '禁用';
                        case 1:
                            return '启用';
                        default:
                            return '无';
                    }
                }
            },
            {
                field : 'welcomeDesc',
                title : '注册状态',
                width : 100,
                align: 'center',
                formatter:function (value, row, index) {
                    if (value == ''){
                        return "未注册";
                    }else if(value == null){
                        return "未注册";
                    }else{
                        return "已注册";
                    }
                }
            },
            {
                field : 'isExistTime',
                title : '离线时间',
                width : 140,
                align: 'center',
            },
            {
                field : '_operate',
                title : '操作',
                width : 180,
                align: 'center',
                formatter : function(value, row, index) {
                    return '<button href="javascript:void(0)" onclick="delFun('+row.id+')" > 删除 </button>   ' +
                           '<button href="javascript:void(0)" onclick="editFun('+row.id+')" > 编辑 </button>' +
                        '<button href="javascript:void(0)" onclick="zhuce('+row.id+')" > 重新注册 </button>';
                }
            }
        ] ],
        toolbar : '#toolbar'    // 数据网格（datagrid）面板的头部工具栏
    });

    // 设备检索条件框
    $('#deviceId').combobox({
        url:ctx+'/device/listAll',
        valueField:'deviceIp',
        textField:'deviceIp'
    });
});

//增加闸机信息
function addFun() {
    parent.$.modalDialog({
        title: '新增设备',
        width: 550,
        height: 300,
        href: ctx+'/device/add',
        buttons : [ {
            text : '保存',
            iconCls: "icon-ok",
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = dataGrid;
                var f = parent.$.modalDialog.handler.find('#deviceAdd');
                f.submit();
            }
        }],
        onLoad:function () {
            var f = parent.$.modalDialog.handler.find('#deviceAdd');
            f.form({
                url : ctx+'/device/save',
                onSubmit : function() {
                    return f.form('validate');
                },
                success : function(result) {
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


function searchFun() {
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    $('#dataGrid').datagrid('clearSelections');
}


function editFun( id ){
    if( null==id ){
        parent.$.messager.alert('温馨提示', "请选择数据", 'warning');
    }else {
        parent.$.modalDialog({
            title: '编辑设备信息',
            width: 550,
            height: 300,
            href: ctx+'/device/detail?id=' + id,
            buttons : [ {
                text : '保存',
                iconCls: "icon-ok",
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;
                    var f = parent.$.modalDialog.handler.find('#deviceDetail');
                    f.submit();
                }
            }],
            onLoad:function () {
                var f = parent.$.modalDialog.handler.find('#deviceDetail');
                f.form({
                    url : ctx+'/device/update',
                    onSubmit : function() {
                        return f.form('validate');
                    },
                    success : function(result) {
                        result = $.parseJSON(result);
                        if( result.success == true ){
                            //审批通过
                            parent.$.messager.alert('成功', result.msg, 'info');
                            parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                            parent.$.modalDialog.handler.dialog('close');
                        }else{
                            //审批通过失败
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
}


function chearFun() {
    $('#searchForm').form('reset');
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
}


function delFun( id ) {
    var node = dataGrid.datagrid('getSelected');
    if (null!=id ) {
        parent.$.messager.confirm('温馨提示','您确定要删除此条数据[ '+node.deviceId+' ]?',function(r){
            if (r){
                $.post(ctx+'/device/delete',{id:id},function (data) {
                    if(data.success == false){
                        parent.$.messager.alert('失败',data.msg, 'error');
                        return false;
                    }else{
                        parent.$.messager.alert('成功',data.msg, 'success');
                        $('#dataGrid').datagrid('reload');
                        return true;
                    }
                });
            }
            dataGrid.datagrid("clearChecked");
        });
    }else{
        parent.$.messager.alert('温馨提示', "请选择数据", 'warning');
    }
}

function zhuce(id) {
    var node = dataGrid.datagrid('getSelected');
    if (null!=id ) {
        parent.$.messager.confirm('温馨提示','您确定要重新注册此设备吗?',function(r){
            if (r){
                $.post(ctx+'/device/zhuce',{id:id},function (data) {
                    if(data.success == false){
                        parent.$.messager.alert('失败',data.msg, 'error');
                        return false;
                    }else{
                        parent.$.messager.alert('成功',data.msg, 'success');
                        $('#dataGrid').datagrid('reload');
                        return true;
                    }
                });
            }
            dataGrid.datagrid("clearChecked");
        });
    }else{
        parent.$.messager.alert('温馨提示', "请选择数据", 'warning');
    }

}