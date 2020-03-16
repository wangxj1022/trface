var treeGrid;
$(function () {
    treeGrid = $('#treeGrid').treegrid({
        url : ctx+'/organization/treeGrid',
        idField : 'id',
        treeField : 'name',
        parentField : 'pid',
        fit : true,
        singleSelect : true,
        fitColumns : false,
        border : false,
        columns : [ [ {
            field : 'name',
            title : '部门名称',
            width : 200,
            align: 'center',
        }, {
            field : 'code',
            title : '部门代号',
            width : 150,
            align: 'center',
        },/*{
            title : '地址',
            field : 'address',
            width : 300,
            align: 'center',
        },*//*{
            title : '部门编号',
            field : 'id',
            width : 80,
            align: 'center',
        },{
            title : '父级编号',
            field : 'pid',
            width : 80,
            align: 'center',
        },*/{
            field : 'seq',
            title : '排序',
            width : 40,
            align: 'center',
        }, {
            field : 'createTime',
            title : '创建时间',
            width : 150,
            align: 'center',
        }, {
            field : 'updateTime',
            title : '更新时间',
            width : 150,
            align: 'center',
        }
        ] ],
        toolbar : '#toolbar'
    });
});

function addFun() {
    var node = treeGrid.treegrid('getSelected');
    if(null==node||node.id==null){
        $.messager.alert('提示', "请选择数据！", 'warning');
        return false;
    }

    var url;
    if(null!=node && null!=node.id){
        url=ctx+'/organization/addPage?pid='+node.id;
    }else{
        url=ctx+'/organization/addPage';
    }
    parent.$.modalDialog({
        title : '添加部门',
        width : 350,
        height : 320,
        href : url,
        buttons : [ {
            text : '保存',
            iconCls: "icon-ok",
            handler : function() {
                parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#organizationAddForm');
                f.submit();
            }
        } ],
        onLoad:function () {
            var f = parent.$.modalDialog.handler.find('#organizationAddForm');
            f.form({
                url : ctx+'/organization/add',
                onSubmit : function() {
                    return f.form('validate');
                },
                success : function(result) {
                    result = $.parseJSON(result);
                    if (result.success) {
                        parent.$.messager.alert('成功', result.msg, 'info');
                        $('#treeGrid').treegrid('reload');
                        parent.$.modalDialog.handler.dialog('close');
                    }else{
                        parent.$.messager.alert('失败', result.msg, 'error');
                    }
                }
            });
        }
    });
}

function editFun() {
    var node = treeGrid.treegrid('getSelected');
    if(null==node||node.id==null){
        $.messager.alert('提示', "请选择数据！", 'warning');
    }else{
        parent.$.modalDialog({
            title : '编辑部门',
            width : 350,
            height : 320,
            href : ctx+'/organization/editPage?id=' + node.id,
            buttons : [ {
                text : '编辑',
                iconCls: "icon-ok",
                handler : function() {
                    parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#organizationEditForm');
                    f.submit();
                }
            } ],
            onLoad:function () {
                var f = parent.$.modalDialog.handler.find('#organizationEditForm');
                f.form({
                    url : ctx+'/organization/edit',
                    onSubmit : function() {
                        return f.form('validate');
                    },
                    success : function(result) {
                        result = $.parseJSON(result);
                        if (result.success) {
                            parent.$.messager.alert('成功', result.msg, 'info');
                            $('#treeGrid').treegrid('reload');
                            parent.$.modalDialog.handler.dialog('close');
                        }else{
                            parent.$.messager.alert('失败', result.msg, 'error');
                        }
                    }
                });
            }
        });
    }
}

function delFun() {
    var node = treeGrid.treegrid('getSelected');
    if(null==node||node.id==null){
        $.messager.alert('提示', "请选择数据！", 'warning');
    }else{
        $.messager.confirm('询问','您确定删除选中的记录[ '+node.name+' ]?',function(r){
            if (r){
                $.post(ctx+'/organization/delete',{id:node.id},function(result){
                    if (result.success){
                        $.messager.alert('成功',result.msg, 'info');
                        $('#treeGrid').treegrid('reload');
                    } else {
                        $.messager.alert('失败',result.msg, 'error');
                    }
                },'json');
            }
        });
    }
}