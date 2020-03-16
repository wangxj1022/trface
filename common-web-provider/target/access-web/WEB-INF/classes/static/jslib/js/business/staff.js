var dataGrid;
$(function () {
    dataGrid = $('#dataGrid').datagrid({
        url : ctx+'/staff/dataGrid',
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
        columns : [ [{
                field: 'staffId',
                title: '员工编号',
                width: 180,
                align: 'center',
            }, {
                field : 'staffName',
                title : '姓名',
                width : 120,
                align: 'center',
            }, {
                field : 'deptId',
                title : '部门',
                width : 150,
                align: 'center',
            }, {
                field : 'isOutWork',
                title : '是否外勤',
                width : 120,
                align: 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '内勤';
                        case 1:
                            return '外勤';
                        default:
                            return '内勤';
                    }
                }
            }, {
                field : 'staffCardId',
                title : '一卡通芯片号',
                width : 180,
                align: 'center',
            },{
                field : '_operate',
                title : '操作',
                width : 150,
                align: 'center',
                formatter:function (value, row, index) {
                    return '<button href="javascript:void(0)" onclick="previewPic('+row.id+')" > 编辑 </button>'
                        + '     <button href="javascript:void(0)" onclick="reAddFun('+row.id+')" > 重发 </button>';

                }
            }
        ] ],
        toolbar : '#toolbar'
    });
});

function checkFun() {
    var node = dataGrid.datagrid('getSelected');
    if (null!=node && null!=node.id) {
        parent.$.messager.confirm('提示','您确定该员工标记为审核通过?',function(r){
            if (r){
                $.post(ctx+'/staff/check',{id:node.id},function (data) {
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
        parent.$.messager.alert('提示', "请选中一条记录", '');
    }
}

function leaveFun() {
    var node = dataGrid.datagrid('getSelected');
    if (null!=node && null!=node.id) {
        parent.$.messager.confirm('提示','您确定该员工设置为离职?',function(r){
            if (r){
                $.post(ctx+'/staff/leave',{id:node.id},function (data) {
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
        parent.$.messager.alert('提示', "请选中一条记录", '');
    }
}

function addFun() {
    $('#staffAddDiv').dialog('open').dialog('setTitle','添加');
    $('#staffinfoAddForm').form('reset');
    $("#organizationId").combotree({
        url: ctx+'/organization/tree',
        valueField: 'id',
        textField: 'text',
        required: true,
        editable: false,
        onlyLeafCheck: true,
        cascaseCheck: true,
        method: 'post',
        onBeforeSelect: function (node) {
            if (!$(this).tree('isLeaf', node.target)) {
                parent.$.messager.alert("错误","请选择子部门","error");
                return false;//只可以选择tree的叶子
            }
        },
    });
    $("#file").change(
        function() {
            var reader = new FileReader();
            reader.readAsDataURL(this.files[0]);
            reader.onload = function(e) {
                if (e.target.result.length < 50) {
                    alert("图片太小了");
                    return;
                }
                if (e.target.result.length > 1048576) {
                    alert("图片太大，请重新选择");
                    return;
                }
                $("#img").attr("src", e.target.result);
                $("#curPhoto").val(
                    e.target.result.replace(
                        "data:image/jpeg;base64,", ""));
                console.log("这个是图片吗" + $("#curPhoto").val());
            };
        });
}

function detailFun() {
    var node = dataGrid.datagrid('getSelected');
    if(null==node || null==node.id){
        parent.$.messager.alert('提示', "请选择数据", 'warning');
    }else {
        parent.$.modalDialog({
            title: '详情',
            width: 550,
            height: 530,
            href: ctx+'/staff/detail?id=' + node.id,
        });
    }
}

/* 实际使用方法 */
function searchFun() {
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    $('#dataGrid').datagrid('clearSelections');
}

function chearFun() {
    $('#searchForm').form('reset');
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
}

function reAddFun( id ) {
    $.messager.confirm('提示','您确定给该员工重新下发员工卡?',function(r){
        if (r){
            // 添加Loading效果
            $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
            $("<div class=\"datagrid-mask-msg\"></div>").html("下发中，请稍候...").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});

            $.post(ctx+'/staff/reAdd',{id:id},function (data) {
                // 去除Loading效果
                var showLoading=$("body");
                showLoading.children("div.datagrid-mask-msg").remove();
                showLoading.children("div.datagrid-mask").remove();

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

}



function previewPic( id ){
    parent.$.modalDialog({
        title: '编辑人员',
        width: 650,
        height: 400,
        href: ctx+'/staff/preview?id=' + id,
        buttons : [ {
            text : '保存',
            iconCls: "icon-ok",
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = dataGrid;
                var f = parent.$.modalDialog.handler.find('#staffDetail');
                f.submit();
            }
        }],

        onLoad:function () {
            var f = parent.$.modalDialog.handler.find('#staffDetail');
            // curPhoto属性赋值
            var value = parent.$.modalDialog.handler.find("#img").attr("src");
            parent.$.modalDialog.handler.find("#curPhoto").val(value.replace(
                "data:image/jpeg;base64,", ""));

            // 给文件框绑定函数
            var file =parent.$.modalDialog.handler.find('#file');
            file.change(
                function() {
                    var reader = new FileReader();
                    reader.readAsDataURL(this.files[0]);
                    reader.onload = function(e) {

                        /* 最小设定为50KB ,具体大小存在误差*/
                        if (e.target.result.length < 71200) {
                            alert("图片太小了,请重新选择");
                            return;
                        }

                        /* 数值为200K 此处数字大小存在误差 */
                        if (e.target.result.length > 300000) {
                            alert("图片过大，请重新选择");
                            return;
                        }
                        parent.$.modalDialog.handler.find("#img").attr("src", e.target.result);
                        parent.$.modalDialog.handler.find("#curPhoto").val(
                            e.target.result.replace(
                                "data:image/jpeg;base64,", ""));
                    };
                });

            f.form({
                url : ctx+'/staff/update',
                onSubmit : function() {
                    var dialog = parent.$.modalDialog.handler.find('#staffDetail');
                    // 校验
                    var result = f.form('validate');
                    if( result ){
                        // 按钮置灰，保存需要一定的等待时间
                        parent.$('.dialog-button a:eq(0)').linkbutton('disable');
                        // 添加Loading效果
                        $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo(f);
                        $("<div class=\"datagrid-mask-msg\"></div>").html("添加中，请稍候...").appendTo(f).css({display:"block",left:($(document.body).outerWidth(true) -900) / 2,top:($(window).height() - 300) / 2});
                    }
                    return result;
                },
                success : function(result) {
                    // 按钮置灰，保存需要一定的等待时间
                    parent.$('.dialog-button a:eq(0)').linkbutton('enable');
                    //去除Loading效果
                    f.children("div.datagrid-mask-msg").remove();
                    f.children("div.datagrid-mask").remove();

                    result = $.parseJSON(result);
                    if( result.success == true ){
                        // 添加通过
                        parent.$.messager.alert('成功', result.msg, 'info');
                        parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                        parent.$.modalDialog.handler.dialog('close');
                    }else{
                        // 添加失败
                        parent.$.messager.alert('失败',result.msg, 'error');
                        $('#dataGrid').datagrid('reload');
                        f.dialog('close');
                    }
                }
            });
        }
    });
}

//员工信息同步方法：
function syncFun(){
    parent.$.messager.confirm('提示','确定进行同步吗?',function(r){
        if (r){
            // 添加Loading效果
            $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
            $("<div class=\"datagrid-mask-msg\"></div>").html("同步中，请稍候...").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});

            $.post(ctx+'/sync/staff',function (data) {
                // 去除Loading效果
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
