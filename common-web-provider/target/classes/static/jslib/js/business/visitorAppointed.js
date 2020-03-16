var dataGrid;
$(function () {
    dataGrid = $('#dataGrid').datagrid({
        url : ctx+'/visitorAppointment/appointedDataGrid',
        striped : true,         //行条纹化
        rownumbers : true,      //显示带有行号的列
        pagination : true,      //分页工具栏
        singleSelect : true,    //只允许选中一行
        fitColumns : false,     //列自动展开/折叠以适应数据网格
        method: 'post',
        idField : 'id',  //指示哪个字段是标识字段
        sortName : 'create_time',   //定义可以排序的列
        sortOrder : 'desc',     //排序顺序
        pageSize : 20,          //初始化页面尺寸
        pageList : [ 10, 20, 50, 100],     //初始化页面尺寸的选择列表
        columns : [ [
            {
                field : 'appointmentId',
                title : '预约编号',
                width : 100,
                align: 'center',
                formatter:function (value, row, index) {
                    return '<a href="javascript:void(0)" onclick="detailFun('+row.id+')" > '+value+' </a>';
                }
            },
            {
                field : 'personCompany',
                title : '公司名称',
                width : 120,
                align: 'center',
            },
            {
                field : 'personName',
                title : '访客姓名',
                width : 90,
                align: 'center',
            },
            {
                field : 'personCode',
                title : '身份证号',
                width : 160,
                align: 'center',
            },
            {
                field : 'staffName',
                title : '被拜访人姓名',
                width : 120,
                align: 'center',
            },
            {
                field : 'appointmentReason',
                title : '事由',
                width : 100,
                align: 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                        case "":
                            return '未说明';
                        default:
                            return "未说明";
                    }
                }
            },
            {
                field : 'beginTime',
                title : '预约开始日期',
                width : 140,
                align: 'center',
            },
            {
                field : 'endTime',
                title : '预约结束日期',
                width : 140,
                align: 'center',
            }
        ] ],
        toolbar : '#toolbar'    // 数据网格（datagrid）面板的头部工具栏
    });
});

function detailFun( id ) {
    if ( null!=id ) {
        parent.$.modalDialog({
            title: '预约详情',
            width: 750,
            height: 300,
            href: ctx+'/visitorAppointment/check?id=' + id,
        });
    }
}

function searchFun() {
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    $('#dataGrid').datagrid('clearSelections');
}

function chearFun() {
    $('#searchForm').form('reset');
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
}

function syncFun(){
    parent.$.messager.confirm('提示','确定进行同步吗?',function(r){
        if (r){
            // 添加Loading效果
            $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
            $("<div class=\"datagrid-mask-msg\"></div>").html("同步中，请稍候...").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});

            $.post(ctx+'/sync/appointment',function (data) {
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
}
