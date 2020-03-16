var dataGrid;
$(function () {
    dataGrid = $('#dataGrid').datagrid({
        url : ctx+'/staffAccess/dataGrid',
        striped : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
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
                field : 'staffCode',
                title : '员工工号',
                width : 150,
                align: 'center',
            }, {
                field: 'staffName',
                title: '员工姓名',
                width: 160,
                align: 'center',
            }, {
                field : 'inTime',
                title : '刷卡时间',
                width : 250,
                align: 'center',
            },{
                field : 'temperature',
                title : '员工体温',
                width : 100,
                align: 'center'
            },{
                field : 'accessStatus',
                title : '状态',
                width : 120,
                align: 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return  "进入";
                        case 1:
                            return  "离开";
                        case 2:
                            return "已通过";
                        default:
                            return  "已通过"
                    }
                }
            },
            {
                field : '_operate',
                title : '操作',
                width : 100,
                align: 'center',
                formatter:function (value, row, index) {
                    return '<button href="javascript:void(0)" onclick="detailFun('+row.id+')" > 详情 </button>';
                }
            }
        ] ],
        toolbar : '#toolbar'
    });

    // 设备检索条件框
    $('#inDeviceId').combobox({
        url:ctx+'/device/listAll',
        valueField:'deviceIp',
        textField:'deviceIp'
    });


});
function deleteFun() {
    var node = dataGrid.datagrid('getSelected');
    if (null!=node && null!=node.id) {
        parent.$.messager.confirm('温馨提示','您确定要删除此条数据?',function(r){
            if (r){
                $.post(ctx+'/staffAccess/delete',{id:node.id},function (data) {
                    if(data.success == true){
                        parent.$.messager.alert('成功',data.msg, 'success');
                        $('#dataGrid').datagrid('reload');
                        return true;
                    }else{
                        parent.$.messager.alert('失败',data.msg, 'error');
                        return false;
                    }
                });
            }
            dataGrid.datagrid("clearChecked");
        });
    }else{
        parent.$.messager.alert('温馨提示', "请选择数据", 'warning');
    }
}
function detailFun(id) {
    //var node = dataGrid.datagrid('getSelected');
   /* if(null==node || null==node.id){
        parent.$.messager.alert('提示', "请选择数据", 'warning');
    }else {*/
        parent.$.modalDialog({
            title: '详情',
            width: 680,
            height: 330,
            href: ctx+'/staffAccess/detail?id=' +id,
           /* buttons : [ {
                text : '保存',
                iconCls: "icon-ok",
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;
                    var f = parent.$.modalDialog.handler.find('#staffAccessInfoDetail');
                    f.submit();
                }
            } ],*/
        });
    /*}*/
}

function searchFun() {
    if($('#newBeginTime').datetimebox('getValue')>$('#newEndTime').datetimebox('getValue')){
        parent.$.messager.alert('温馨提示', "开始时间不能大于结束时间", '');
        return ;
    }
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    $('#dataGrid').datagrid('clearSelections');
}
function chearFun() {
    $('#searchForm').form('reset');
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
}

//导出员工访问记录方法
function exportFun(){

    var url= ctx+'/staffAccess/exportExcel';
    var startTime = $('#newBeginTime').datetimebox('getValue');
    var endTime = $('#newEndTime').datetimebox('getValue');
    var name = $('#staffName').val();
    var jsonData = {
        name:name,
        startDate:startTime,
        endDate :endTime
    };

    // 添加Loading效果
    $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在导出...").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});

    $.post(url,jsonData,function (data) {

        //去除Loading效果
        var showLoading=$("body");
        showLoading.children("div.datagrid-mask-msg").remove();
        showLoading.children("div.datagrid-mask").remove();

        if(data.success == true){
            parent.$.messager.alert('成功',data.msg, 'success');
            $('#dataGrid').datagrid('reload');
            return true;
        }else{
            parent.$.messager.alert('失败',data.msg, 'error');
            return false;
        }
    });

}