var dataGrid;
$(function () {
    dataGrid = $('#dataGrid').datagrid({
        url : ctx+'/entry/dataGrid',
        striped : true,         //行条纹化
        rownumbers : true,      //显示带有行号的列
        pagination : true,      //分页工具栏
        singleSelect : true,    //只允许选中一行
        fitColumns : false,     //列自动展开/折叠以适应数据网格
        method: 'post',
        idField : 'id',  //指示哪个字段是标识字段
        pageSize : 10,          //初始化页面尺寸
        pageList : [ 10, 20, 50, 100],     //初始化页面尺寸的选择列表
        columns : [ [
            {
                field : 'entryId',
                title : '进出口编号',
                width : 100,
                align: 'center',
            },
            {
                field : 'entryName',
                title : '名称',
                width : 100,
                align: 'center',
            },
            {
                field : 'entryCategory',
                title : '出入口类别',
                width : 120,
                align: 'center',
                formatter:function (value,row,index) {
                    switch (value) {
                        case 0:
                            return '员工通道';
                        case 1:
                            return '访客通道';
                        case 2:
                            return '员工和访客通道';
                        default:
                            return '员工通道';
                    }
                }
            },
            {
                field : 'entryType',
                title : '进出类型',
                width : 120,
                align: 'center',
                formatter:function (value,row,index) {
                    switch (value) {
                        case 0:
                            return '人员';
                        case 1:
                            return '车辆';
                        case 2:
                            return '人员和车辆';
                        default:
                            return '人员';
                    }
                }
            },
            {
                field : 'entryPhone',
                title : '岗亭电话',
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
                           '<button href="javascript:void(0)" onclick="editFun('+row.id+')" > 编辑 </button>';
                }
            }
        ] ],
        toolbar : '#toolbar'    // 数据网格（datagrid）面板的头部工具栏
    });
});

//增加闸机信息
function addFun() {
    parent.$.modalDialog({
        title: '添加出入口',
        width: 550,
        height: 300,
        href: ctx+'/entry/add',
        buttons : [ {
            text : '保存',
            iconCls: "icon-ok",
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = dataGrid;
                var f = parent.$.modalDialog.handler.find('#entryAdd');
                f.submit();
            }
        }],
        onLoad:function () {
            var f = parent.$.modalDialog.handler.find('#entryAdd');
            f.form({
                url : ctx+'/entry/save',
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


function searchFun() {
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    $('#dataGrid').datagrid('clearSelections');
}


function editFun( id ){
    if( null==id ){
        parent.$.messager.alert('提示', "请选择数据", 'warning');
    }else {
        parent.$.modalDialog({
            title: '访客详情',
            width: 550,
            height: 300,
            href: ctx+'/entry/detail?id=' + id,
            buttons : [ {
                text : '保存',
                iconCls: "icon-ok",
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;
                    var f = parent.$.modalDialog.handler.find('#entryDetail');
                    f.submit();
                }
            }],
            onLoad:function () {
                var f = parent.$.modalDialog.handler.find('#entryDetail');
                f.form({
                    url : ctx+'/entry/update',
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
                            parent.$.messager.alert('失败',data.msg, 'error');
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
    if (null!=id ) {
        parent.$.messager.confirm('提示','您确定要删除此条数据?',function(r){
            if (r){
                $.post(ctx+'/entry/delete',{id:id},function (data) {
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
        parent.$.messager.alert('提示', "请选择数据", 'warning');
    }
}