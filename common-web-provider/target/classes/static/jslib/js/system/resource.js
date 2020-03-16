var treeGrid;
$(function () {
    treeGrid = $('#treeGrid').treegrid({
        url : ctx+'/resource/treeGrid',
        idField : 'id',
        treeField : 'name',
        parentField : 'pid',
        fit : true,
        singleSelect : true,
        fitColumns : false,
        border : false,
        columns : [ [ {
            field : 'name',
            title : '资源名称',
            width : 180,
            align: 'center',
        }, {
            field : 'url',
            title : '资源路径',
            width : 200,
            align: 'center',
        },/*{
            title : '编号',
            field : 'id',
            width : 80,
            align: 'center',
        },*/{
            title : '父级编号',
            field : 'pid',
            width : 80,
            align: 'center',
        }, {
            field: 'seq',
            title: '排序',
            width: 50,
            align: 'center',
        }, {
            field : 'resourceType',
            title : '资源类型',
            width : 80,
            align: 'center',
            formatter : function(value, row, index) {
                switch (value) {
                    case 0:
                        return '菜单';
                    case 1:
                        return '按钮';
                }
            }
        },{
            field : 'description',
            title : '描述',
            width : 200,
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
    if(node!=null&&(node.pid!=null&&node.pid!='')){
        if(node.resourceType==1){
            $.messager.alert('提示', '该节点类型是按钮,不允许添加！', 'warning');
            treeGrid.treegrid("clearChecked");
            return false;
        }
    }
    var url;
    if(null!=node && null!=node.id){
        url=ctx+'/resource/addPage?pid='+node.id;
    }else{
        url=ctx+'/resource/addPage';
    }
    parent.$.modalDialog({
        title : '添加资源',
        width : 350,
        height : 330,
        href : url,
        buttons : [ {
            text : '保存',
            iconCls: "icon-ok",
            handler : function() {
                parent.$.modalDialog.openner_treeGrid = treeGrid;
                var f = parent.$.modalDialog.handler.find('#resourceAddForm');
                f.submit();
            }
        } ],
        onLoad:function () {
            var f = parent.$.modalDialog.handler.find('#resourceAddForm');
            f.form({
                url : ctx+'/resource/add',
                onSubmit : function() {
                    return f.form('validate');
                },
                success : function(result) {
                    result = $.parseJSON(result);
                    if (result.success) {
                        parent.$.messager.alert('成功', result.msg, 'info');
                        parent.$.modalDialog.openner_treeGrid.treegrid('reload');
                        parent.$.modalDialog.handler.dialog('close');
                    }else{
                        parent.$.messager.alert('失败', result.msg, 'error');
                    }
                }
            });
        }
    });
    treeGrid.treegrid("clearChecked");
}

function editFun() {
    var node = treeGrid.treegrid('getSelected');
    if(null==node || null==node.id){
        $.messager.alert('提示', "请选择数据！", 'warning');
    }else {
        parent.$.modalDialog({
            title : '编辑资源',
            width : 350,
            height : 330,
            href : ctx+'/resource/editPage?id=' + node.id,
            buttons : [ {
                text : '保存',
                iconCls: "icon-ok",
                handler : function() {
                    parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#resourceEditForm');
                    f.submit();
                }
            } ],
            onLoad:function () {
                var f = parent.$.modalDialog.handler.find('#resourceEditForm');
                f.form({
                    url : ctx+'/resource/edit',
                    onSubmit : function() {
                        return f.form('validate');
                    },
                    success : function(result) {
                        result = $.parseJSON(result);
                        if (result.success) {
                            parent.$.messager.alert('成功', result.msg, 'info');
                            parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为resource.jsp页面预定义好了
                            parent.$.modalDialog.handler.dialog('close');
                        }else{
                            parent.$.messager.alert('失败', result.msg, 'error');

                        }
                    }
                });
            },
        });
    }
    treeGrid.treegrid("clearChecked");
}

function delFun() {
    var node = treeGrid.treegrid('getSelected');
    if (null!=node && null!=node.id) {
        $.messager.confirm('提示','您确定删除此记录[ '+node.name+' ]?',function(r){
            if (r){
                $.post(ctx+'/resource/delete',{id:node.id},function (data) {
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
    }else{
        $.messager.alert('提示', "请选择数据！", 'warning');
    }
}