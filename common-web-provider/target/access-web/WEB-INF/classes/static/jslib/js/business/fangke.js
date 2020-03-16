var dataGrid;
$(function () {
    dataGrid = $('#dataGrid').datagrid({
        url : ctx+'/fangke/dataGrid',
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
                field : 'logtime',
                title : '录入时间',
                width : 180,
                align: 'center',
            },{
                field : 'xm',
                title : '姓名',
                width : 100,
                align: 'center',
            }, {
                field : 'sfz',
                title : '身份证号',
                width : 180,
                align: 'center',
            },  {
                field: 'phoneno',
                title: '手机号',
                width: 120,
                align: 'center',
            },{
                field : '_operat',
                title : '来自',
                width : 180,
                align: 'center',
                formatter : function(value, row, index) {
                    return row.province + '~' + row.city;
                }
            },{
                field : '_opera',
                title : '交通方式',
                width : 180,
                align: 'center',
                formatter : function(value, row, index) {
                    return row.triptype + '~' + row.tripno;
                }
            },{
                field : 'iscloser',
                title : '密切接触史',
                width : 80,
                align: 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return  "无";
                        case 1:
                            return  "有";
                    }
                }
            },{
                field : 'temperature',
                title : '体温',
                width : 80,
                align: 'center',
            }, {
                field : 'toaddress',
                title : '去向',
                width : 180,
                align: 'center',
            },{
                field : '_operate',
                title : '操作',
                width : 80,
                align: 'center',
                formatter:function (value, row, index) {
                    return '<button href="javascript:void(0)" onclick="detailFun('+row.id+')" > 详情 </button>';
                }
            }
        ] ],
        toolbar : '#toolbar'
    });

});

// 查询
function searchFun() {
   /* if($('#newBeginTime').datetimebox('getValue')>$('#newEndTime').datetimebox('getValue')){
        parent.$.messager.alert('提示', "开始时间不能大于结束时间", '');
        return ;
    }*/
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    $('#dataGrid').datagrid('clearSelections');
}
//清空
function chearFun() {
    $('#searchForm').form('reset');
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
}

function detailFun(id) {
    parent.$.modalDialog({
        title: '详情',
        width: 600,
        height: 500,
        href: ctx+'/fangke/detail?id=' +id,
        buttons : [ {
                text : '关闭',
                iconCls: "icon-cancel",
                handler : function() {
                    parent.$.modalDialog.handler.dialog('close');
                }
            } ],
    });

}








