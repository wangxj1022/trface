var dataGrid;
$(function () {
    dataGrid = $('#dataGrid').datagrid({
        url : ctx+'/visitor/dataGrid',
        striped : true,         //行条纹化
        rownumbers : true,      //显示带有行号的列
        pagination : true,      //分页工具栏
        singleSelect : true,    //只允许选中一行
        fitColumns : false,     //列自动展开/折叠以适应数据网格
        method: 'post',
        idField : 'id',  //指示哪个字段是标识字段
        pageSize : 20,          //初始化页面尺寸
        pageList : [ 10, 20, 50, 100],     //初始化页面尺寸的选择列表
        columns : [ [
            /*{
                field : 'id',
                title : '客户编号',
                width : 150,
                align: 'center',
                formatter:function (value, row, index) {
                    return '<a href="javascript:void(0)" onclick="detailFun('+row.id+')" > '+'No_'+value+' </a>';
                }
            },*/
            /*{
                field : 'visitorCompany',
                title : '公司名称',
                width : 120,
                align: 'center',
            },*/
            {
            field : 'visitorName',
            title : '姓名',
            width : 150,
            align: 'center',
            },
            {
                field : 'visitorCardId',
                title : '身份证号',
                width : 200,
                align: 'center',
            },
            /*{
                field : 'mobile',
                title : '手机号码',
                width : 220,
                align: 'center',
            },*/
            /*{
                field : 'updateTime',
                title : '最后到访时间',
                width : 160,
                align: 'center',
            },*/
            {
                field : 'inTime',
                title : '预约开始日期',
                width : 250,
                align: 'center',
            },
            {
                field : 'outTime',
                title : '预约结束日期',
                width : 250,
                align: 'center',
            }
            /*{
                field : 'remark',
                title : '不良信息',
                width : 130,
                align: 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                        case "":
                            return '无';
                        default:
                            return "无";
                    }
                }
            },*/
            /*{
                field : '_operate',
                title : '操作',
                width : 120,
                align: 'center',
                formatter : function(value, row, index) {
                    return '<button href="javascript:void(0)" onclick="deleteFun('+row.id+')" > 删除 </button>';
                }
            }*/
        ] ],
        toolbar : '#toolbar'    // 数据网格（datagrid）面板的头部工具栏
    });
});


function searchFun() {
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    $('#dataGrid').datagrid('clearSelections');
}

function detailFun( id ){
    if( null==id ){
        parent.$.messager.alert('提示', "请选择数据", 'warning');
    }else {
        parent.$.modalDialog({
            title: '访客详情',
            width: 550,
            height: 300,
            href: ctx+'/visitor/detail?id=' + id,
        });
    }
}

function chearFun() {
    $('#searchForm').form('reset');
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
}

function deleteFun( id ) {
    if (null!=id ) {
        parent.$.messager.confirm('提示','您确定要删除此条数据?',function(r){
            if (r){
                $.post(ctx+'/visitor/delete',{id:id},function (data) {
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


//访客信息同步方法：
function syncFun(){
    parent.$.messager.confirm('提示','确定进行同步吗?',function(r){
        if (r){
            // 添加Loading效果
            $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
            $("<div class=\"datagrid-mask-msg\"></div>").html("同步中，请稍候...").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});

            $.post(ctx+'/sync/visitor',function (data) {
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
