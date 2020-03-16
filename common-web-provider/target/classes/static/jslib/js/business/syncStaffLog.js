var dataGrid;
$(function () {
    dataGrid = $('#dataGrid').datagrid({
        url : ctx+'/synclog/staffDataGrid',
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
            },/*{
                field: 'loginName',
                title: '操作员',
                width: 100,
                align: 'center',

            }, {
                field : 'ip',
                title : '地址',
                width : 100,
                align: 'center',
            },*/{
                title : '操作',
                field : 'operContent',
                width : 200,
                align: 'center',
            },
            {
                title : '新增数据',
                field : 'recordAdd',
                width : 200,
                align: 'center',
            },
            {
                title : '减少数据',
                field : 'recordReduce',
                width : 200,
                align: 'center',
            },
            {
                field : 'createTime',
                title : '操作时间',
                width : 200,
                align: 'center',
            }
        ] ],
        toolbar : '#toolbar'
    });
});

function searchFun() {
    if($('#newBeginTime').datetimebox('getValue')>$('#newEndTime').datetimebox('getValue')){
        parent.$.messager.alert('提示', "开始时间不能大于结束时间", '');
        return ;
    }
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    $('#dataGrid').datagrid('clearSelections');
}

function chearFun() {
    $('#searchForm').form('reset');
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
}

function delFun(type) {
    var node = dataGrid.datagrid('getSelections');
    if(node.length==0 ){
        $.messager.alert('提示',"请选择数据！", 'warning');
    }else if(node.length>1 && type == 0){
        $.messager.alert('提示',"不能删除多行", 'warning');
    }else {

        $.messager.confirm('询问','您确定删除选中的记录?',function(r){
            if (r){
                var ids ="";
                for(var i = 0;i<node.length;i++){
                    ids=ids+node[i].id+",";
                }
                $.post(ctx+'/synclog/delete',{logIds:ids},function(result){
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
