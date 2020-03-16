var dataGrid;
$(function () {
    dataGrid = $('#dataGrid').datagrid({
        url : ctx+'/param/dataGrid',
        striped : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        fitColumns : false,
        idField : 'id',
        sortName : 'create_time',
        sortOrder : 'desc',
        pageSize : 50,
        pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
        columns : [ [{
            field: 'pName',
            title: '参数名',
            width: 200,
            align: 'center',
        }, {
            field : 'pValue',
            title : '参数值',
            width : 300,
            align: 'center',
        },{
            title : '描述',
            field : 'pDesc',
            width : 300,
            align: 'center',
        },
        {
            field : '_operate',
            title : '操作',
            width : 120,
            align: 'center',
            formatter:function (value, row, index) {
                return "<button href='javascript:void(0)' onclick=editFun('" + row.pName + "') > 编辑 </button>";
            }
        }
        ] ],
        toolbar : '#toolbar'
    });
});


function searchFun() {
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    $('#dataGrid').datagrid('clearSelections');
}


function editFun(value){
    if( null==value ){
        parent.$.messager.alert('提示', "请选择数据", 'warning');
    }else {
        parent.$.modalDialog({
            title: '参数详情',
            width: 400,
            height: 300,
            href: ctx+'/param/detail?value=' + value,
            buttons : [ {
                text : '保存',
                iconCls: "icon-ok",
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;
                    var f = parent.$.modalDialog.handler.find('#paramDetail');
                    f.submit();
                }
            }],
            onLoad:function () {
                var f = parent.$.modalDialog.handler.find('#paramDetail');
                f.form({
                    url : ctx+'/param/update',
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


// 邮件发送
function sendMail( ) {
    parent.$.messager.confirm('提示','您确定要发送邮件?',function(r){
        if (r){
            $.post(ctx+'/warning/email',function (data) {
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
}

// 短信发送
function sendMessage( ) {
    parent.$.messager.confirm('提示','您确定要发送短信?',function(r){
        if (r){
            $.post(ctx+'/warning/message',function (data) {
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
}
