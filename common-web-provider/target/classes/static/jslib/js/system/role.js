var dataGrid;
$(function () {
    dataGrid = $('#dataGrid').datagrid({
        url : ctx+'/role/dataGrid',
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
        columns : [ [/*{
            field: 'code',
            title: '角色编号',
            width: 100,
            align: 'center',
        },*/ {
            field : 'name',
            title : '角色名称',
            width : 200,
            align: 'center',
        },{
            title : '描述',
            field : 'description',
            width : 200,
            align: 'center',
        }, {
            field : 'createTime',
            title : '创建时间',
            width : 200,
            align: 'center',
        }, {
            field : 'updateTime',
            title : '更新时间',
            width : 200,
            align: 'center',
        }
        ] ],
        toolbar : '#toolbar'
    });
});
function addFun() {
    $('#roleAddDiv').dialog('open').dialog('setTitle','添加角色');
    $('#roleAddForm').form('reset');
}
function saveAddRole() {
    if($('#name').val()==null || $('#name').val()==''){
        $.messager.alert('提示',"请输入角色名！", '');
        return false;
    }

    /*if($('#code').val()==null || $('#code').val()==''){
        $.messager.alert('提示',"请输入编号", '');
        return false;
    }*/

    $.ajax({
        type : 'POST',
        url : ctx+'/role/add',
        data:$("#roleAddForm").serialize(),
        success : function(data) {
            if(data.success == true){
                $.messager.alert('成功',data.msg, 'info');
                $('#roleAddDiv').dialog('close');
                $('#dataGrid').datagrid('reload');
                return true;
            }else{
                $.messager.alert('失败',data.msg, 'error');
                return false;
            }
        }
    });
}
function editFun() {
    var node = dataGrid.datagrid('getSelected');
    if(null==node||node.id==null){
        $.messager.alert('提示', "请选择数据！", 'warning');
    }else {
        $('#roleEditDiv').dialog('open').dialog('setTitle','编辑角色');
        $('#roleEditForm').form('reset');
        $('#name_edit').textbox("setValue",node.name);
        $('#code_edit').textbox("setValue",node.code);
        $('#description_edit').textbox("setValue",node.description);
        $('#id').val(node.id);
    }
}

function saveEditRole() {
    if($('#name_edit').val()==null || $('#name_edit').val()==''){
        $.messager.alert('提示',"请输入角色名！", '');
        return false;
    }
    /*if($('#code_edit').val()==null || $('#code_edit').val()==''){
        $.messager.alert('提示',"请输入编号", '');
        return false;
    }*/
    $.ajax({
        type : 'POST',
        url : ctx+'/role/edit',
        data:$("#roleEditForm").serialize(),
        success : function(data) {
            if(data.success == true){
                $.messager.alert('成功',data.msg, 'info');
                $('#roleEditDiv').dialog('close');
                $('#dataGrid').datagrid('reload');
                return true;
            }else{
                $.messager.alert('失败',data.msg, 'error');
                return false;
            }
        }
    });
    $('#dataGrid').datagrid("clearChecked");
}

function delFun() {
    var node = dataGrid.datagrid('getSelected');
    if (node !=null && node.id !=null) {
        $.messager.confirm('提示','您确定删除此记录[ '+node.name+' ]?',function(r){
            if (r){
                $.post(ctx+'/role/delete',{id:node.id},function (data) {
                    if(data.success == true){
                        $.messager.alert('成功',data.msg, 'info');
                        $('#dataGrid').datagrid('reload');
                        return true;
                    }else{
                        $.messager.alert('失败',data.msg, 'error');
                        return false;
                    }
                });
            }
        });
    }else{
        $.messager.alert('提示', "请选择数据！", 'warning');
    }

}
function grantFun() {
    var node = dataGrid.datagrid('getSelected');
    if(null==node||node.id==null){
        $.messager.alert('提示', "请选择数据！", 'warning');
    }else {
        var id=node.id;
        $('#roleGrantDiv').dialog('open').dialog('setTitle','授权');
        $('#id_grant').val(id);
        var ids;
        $('#tree').tree({
            url:ctx+'/resource/allTree?flag=true',
            method:'POST',
            parentField : 'pid',
            cascadeCheck: false,
            lines : true,
            checkbox : true,
            onCheck: function (node,checked){
                // 选中时  一般不进行操作 让用户自己选中
                if(checked){
                    var parentNode = $("#tree").tree('getParent', node.target);
                    if (parentNode != null) {
                        $("#tree").tree('check', parentNode.target);
                    }
                }
            },
            onLoadSuccess : function(node, data) {
                $.post( ctx+'/role/get', {
                    id :id
                }, function(result) {
                    if (result.id != undefined&&result.resourceIds!= undefined) {
                        ids = $.stringToList(result.resourceIds);
                    }
                    if (ids.length > 0) {
                        for ( var i = 0; i < ids.length; i++) {
                            if ( $('#tree').tree('find', ids[i])) {
                                var node = $('#tree').tree('find', ids[i]);
                                $('#tree').tree('check', node.target);
                            }
                        }
                    }
                    ids=null;
                }, 'json');
            },
        });
    }
}
function saveGrantRole() {
    var checknodes =  $('#tree').tree('getChecked');
    var ids = [];
    if (checknodes && checknodes.length > 0) {
        for ( var i = 0; i < checknodes.length; i++) {
            ids.push(checknodes[i].id);
        }
    }
    $('#resourceIds').val(ids);
    $.post(ctx+'/role/grant',$('#roleGrantForm').serialize(),function (data) {
        if(data.success == true){
            $.messager.alert('成功',data.msg, 'info');
            $('#roleGrantDiv').dialog('close');
            $('#dataGrid').datagrid('reload');
            return true;
        }else{
            $.messager.alert('失败',data.msg, 'error');
            return false;
        }
    });
    $('#dataGrid').datagrid("clearChecked");
}