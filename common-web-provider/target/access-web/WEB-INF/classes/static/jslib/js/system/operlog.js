var dataGrid;
$(function () {
    dataGrid = $('#dataGrid').datagrid({
        url : ctx+'/log/dataGrid',
        striped : true,
        rownumbers : true,
        pagination : true,
        fitColumns : false,
        method: 'post',
        idField : 'id',
        sortName : 'create_time',
        sortOrder : 'desc',
        pageSize : 50,
        pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
        columns : [ [
            {
                field: 'ck',
                width: 30,
                checkbox : true
            },{
            field: 'loginName',
            title: '操作员',
            width: 100,
            align: 'center',

        }, {
            field : 'ip',
            title : '地址',
            width : 100,
            align: 'center',
        },{
            title : '操作概要',
            field : 'operContent',
            width : 150,
            align: 'center',
        },{
                title : '操作内容',
                field : 'operDetail',
                width : 200,
                align: 'center',
        },{
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
function delFun(type) {
    var node = dataGrid.datagrid('getSelections');
    if(node.length==0 ){
        $.messager.alert('提示',"请选择数据！", 'warning');
    }else if(node.length>1 && type == 0){
        $.messager.alert('提示',"不能删除多行", 'warning');
    }else {
        $.messager.confirm('询问','您确定删除[ '+node[0].loginName+' ]的这条[ '+node[0].operContent+' ]的操作记录 ?',function(r){
            if (r){
                var ids ="";
                for(var i = 0;i<node.length;i++){
                    ids=ids+node[i].id+",";
                }
                $.post(ctx+'/log/delete',{logIds:ids},function(result){
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
function chearFun() {
    $('#searchForm').form('reset');
}
function edit() {
    $('#editDiv').dialog({
        onOpen: function(){
                var editor1 = KindEditor.create('#detail', {
                    width : '590',
                    minHeight : '390px',
                    uploadJson : '../cycx/picFtpUpload.do',
                afterBlur: function () {
                    this.sync();
                },
                    filterMode: true,//是否开启过滤模式
                    allowFileManager: true,
                    afterChange : function() {
                },
                onBeforeClose: function (event, ui) {
// 关闭Dialog前移除编辑器
                    editor1.remove('#detail');
                },
                items : [ 'source', '|', 'undo', 'redo', '|', 'cut', 'copy', 'paste',
            'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
            'justifyfull', 'subscript',
            'superscript', 'quickformat', '|',
            'formatblock', 'fontname', 'fontsize', '|', '/','forecolor', 'hilitecolor', 'bold',
'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
            'table', 'hr', 'pagebreak',
            'link', 'unlink', 'about', '|']
            });
        },
        onClose:function(){
            editor1.remove('#detail');
        },
        modal: true
    }).dialog('open').dialog('setTitle','编辑日志');
}