var dataGrid;
$(function () {
    dataGrid = $('#dataGrid').datagrid({
        url : ctx+'/visitorAppointment/dataGrid',
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
                width : 110,
                align: 'center',
            },
            {
                field : 'endTime',
                title : '预约结束日期',
                width : 110,
                align: 'center',
            },
            {
                field : 'appointmentStatus',
                title : '状态',
                width : 100,
                align: 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '未审批';
                        case 1:
                            return '已审批';
                        default:
                            return "未审批";
                    }
                }
            },
            {
                field : '_operate',
                title : '操作',
                width : 80,
                align: 'center',
                formatter : function(value, row, index) {
                    return '<button href="javascript:void(0)" onclick="checkFun('+row.id+')" > 审批 </button>';
                }
            }
        ] ],
        toolbar : '#toolbar'    // 数据网格（datagrid）面板的头部工具栏
    });
});

function checkFun( id ) {

    if ( null!=id ) {
        parent.$.modalDialog({
            title: '预约详情',
            width: 750,
            height: 400,
            href: ctx+'/visitorAppointment/check?id=' + id,
            buttons : [ {
                text : '通过',
                iconCls: "icon-ok",
                handler : function() {
                    var url = ctx+'/visitorAppointment/pass';
                    $.post(url,{id:id},function(data){
                        if( data.success == true ){
                            //审批通过
                            parent.$.messager.alert('完成',data.msg, 'info');
                            $('#dataGrid').datagrid('reload');
                            parent.$.modalDialog.handler.dialog('close');
                        }else{
                            //审批通过失败
                            parent.$.messager.alert('失败',data.msg, 'error');
                            $('#dataGrid').datagrid('reload');
                            parent.$.modalDialog.handler.dialog('close');
                        }
                    });
                    dataGrid.datagrid("clearChecked");
                }
            },{
                    text : '拒绝',
                    iconCls: "icon-cancel",
                    handler : function() {
                        var url = ctx+'/visitorAppointment/reject';
                        $.post(url,{id:id},function(data){
                            if( data.success == true ){
                                //拒绝通过
                                parent.$.messager.alert('完成',data.msg, 'info');
                                $('#dataGrid').datagrid('reload');
                                parent.$.modalDialog.handler.dialog('close');
                            }else{
                                //拒绝失败
                                parent.$.messager.alert('失败',data.msg, 'error');
                                $('#dataGrid').datagrid('reload');
                                parent.$.modalDialog.handler.dialog('close');
                            }
                        });
                        dataGrid.datagrid("clearChecked");
                    }
                }],
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

