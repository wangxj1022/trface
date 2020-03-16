var dataGrid;
$(function () {
    dataGrid = $('#dataGrid').datagrid({
        url : ctx+'/visitorAccess/dataGrid',
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
            /*{
                field : 'accessId',
                title : '编号',
                width : 150,
                align: 'center',
                formatter:function (value, row, index) {
                    return '<a href="javascript:void(0)" onclick="detailFun('+row.id+')" > '+value+' </a>';
                }
            },*/
            /*{
                field : 'personCompany',
                title : '公司名称',
                width : 120,
                align: 'center',
            },*/
            {
                field : 'personName',
                title : '访客姓名',
                width : 150,
                align: 'center',
            },
            {
                field : 'personCode',
                title : '身份证号',
                width : 160,
                align: 'center',
            },
            /*{
                field : 'staffName',
                title : '被拜访人姓名',
                width : 120,
                align: 'center',
            },
            {
                field : 'accessReason',
                title : '事由',
                width : 100,
                align: 'center',

            },*/
            {
                field : 'createTime',
                title : '刷卡时间',
                width : 250,
                align: 'center',
            },
            {
                field : 'inDeviceId',
                title : '刷卡设备',
                width : 250,
                align: 'center',
            },
            /*{
                field : 'inTime',
                title : '进入时间',
                width : 140,
                align: 'center',
            },
            {
                field : 'outTime',
                title : '离开时间',
                width : 140,
                align: 'center',
            },*/
            {
                field:'accessStatus',
                title:'来访状态',
                width:120,
                align:'center',
                formatter:function (value, row, index) {
                    switch (value){
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
        parent.$.messager.confirm('提示','您确定要删除此条数据?',function(r){
            if (r){
                $.post(ctx+'/visitorAccess/delete',{id:node.id},function (data) {
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
function detailFun(id) {
    if( null!=id ){
        parent.$.modalDialog({
            title: '来访详情',
            width: 750,
            height: 300,
            href: ctx+'/visitorAccess/detail?id=' + id,
        });
    }
}

function searchFun() {
    /*if($('#newBeginTime').datetimebox('getValue')>$('#newEndTime').datetimebox('getValue')){
        parent.$.messager.alert('提示', "开始时间不能大于结束时间", '');
        return ;
    }*/
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    $('#dataGrid').datagrid('clearSelections');
}
function chearFun() {
    $('#searchForm').form('reset');
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    $('#dataGrid').datagrid('clearSelections');
}

/*
function syncFun(){
    parent.$.messager.confirm('提示','确定进行同步吗?',function(r){
        if (r){
            // 添加Loading效果
            $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
            $("<div class=\"datagrid-mask-msg\"></div>").html("同步中，请稍候...").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});

            $.post(ctx+'/sync/access',function (data) {
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
    });
}*/
