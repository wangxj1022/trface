var dataGrid;
$(function () {
    dataGrid = $('#dataGrid').datagrid({
        url : ctx+'/user/dataGrid',
        striped : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        fitColumns : false,
        method: 'post',
        idField : 'id',
        sortName : 'create_time',
        sortOrder : 'desc',
        pageSize : 50,
        method:'post',
        pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
        columns : [ [
          {
                field: 'name',
                title: '姓名',
                width: 100,
                align: 'center',

            },  {
                field: 'username',
                title: '登录名',
                width: 100,
                align: 'center',

            }
            /*, {
                field : 'sex',
                title : '性别',
                width : 60,
                align: 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '男';
                        case 1:
                            return '女';
                        default:
                            return "未定义";
                    }
                }
            },{
                title : '年龄',
                field : 'age',
                width : 60,
                align: 'center',
            },*/
            /*{
                title : '部门编号',
                field : 'organizationId',
                width : 60,
                align: 'center',
            }*/
            ,{
                title : '部门',
                field : 'organizationName',
                width : 150,
                align: 'center',
            }
            ,{
                title : '角色',
                field : 'roleNames',
                width : 130,
                align: 'center',
            }
            ,{
                title : '手机号',
                field : 'phone',
                width : 120,
                align: 'center',
            }
            ,{
                title : '状态',
                field : 'isdefault',
                width : 120,
                align: 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '启用';
                        case 1:
                            return '禁用';
                        default:
                            return "启用";
                    }
                }
            }
            , {
                field : 'createTime',
                title : '创建时间',
                width : 170,
                align: 'center',
            },{
                title : '修改时间',
                field : 'updateTime',
                width : 170,
                align: 'center',
            }
        ] ],
        toolbar : '#toolbar'
    });


    $("#beginTime").datebox({
        onSelect : function(beginDate){
            $('#endTime').datebox().datebox('calendar').calendar({
                validator: function(date){
                    return beginDate<=date;
                }
            });
        }
    });
    $("#endTime").datebox({
        onSelect : function(endDate){
            $('#beginTime').datebox().datebox('calendar').calendar({
                validator: function(date){
                    return endDate>=date;
                }
            });
        }
    });
});

function delFun() {
    var node = dataGrid.datagrid('getSelected');
    if (node !=null && node.id !=null) {
        $.messager.confirm('提示','您确定删除此记录[ '+node.name+' ]?',function(r){
            if (r){
               $.post(ctx+'/user/delete',{id:node.id},function (data) {
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
    $('#dataGrid').datagrid('clearSelections');
}

function editFun() {
    var node = dataGrid.datagrid('getSelected');
    if(null==node||node.id==null){
        $.messager.alert('提示', "请选择数据", 'warning');
    }else{
        $("#organizationId_edit").combotree({
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
                    parent.$.messager.alert("提示","请选择子部门","warning");
                    return false;//只可以选择tree的叶子
                }
            },
        });

        $('#roleId_edit').combotree({
            url:ctx+'/role/tree',
            method:'post',
            valueField:'id',
            textField:'text',
            editable: false,
            required: true,
            cascaseCheck: true,
        });


        $('#userEditDiv').dialog('open').dialog('setTitle','编辑操作员');
        $('#name_edit').textbox('setValue',node.name);                      // 姓名回显
        $('#username_edit').textbox('setValue',node.username);              // 登录名回显
        //$('#sex_edit').combobox('setValue',node.sex+"");
        //$('#age_edit').textbox('setValue',node.age);
        $('#organizationId_edit').combotree( 'setValue',node.organizationId );
        $.post( ctx+"/role/getIdByName",{"roleNames": node.roleNames},function(result){
            $('#roleId_edit').combotree('setValue', result);
        });
        // $('#roleId_edit').combotree('setValue',node.roleId);
        // $('#roleId_edit').textbox('setValue',node.roleId);


        //$('#code_edit').textbox('setValue',node.code);
        $('#phone_edit').textbox('setValue',node.phone);
        $('#password_edit').textbox('setValue',null);
        $('#id').val(node.id);
    }
}
function addFun() {
    $('#userAddDiv').dialog('open').dialog('setTitle','添加操作员');
    $('#userAddForm').form('reset');
    $("#organizationId").combotree({
        url: ctx+'/organization/tree',
        valueField: 'id',
        textField: 'text',
        required: true,
        editable: false,
        cascaseCheck: true,
        method: 'post',
       /* onBeforeSelect: function (node) {
            if (!$(this).tree('isLeaf', node.target)) {
                parent.$.messager.alert("提示","请选择子部门","warning");
                return false;//只可以选择tree的叶子
            }
        },*/
    });

    $('#roleId').combobox({
        url:ctx+'/role/tree',
        method:'post',
        valueField:'id',
        textField:'text',
        editable: false,
        required: true,
        cascaseCheck: true,
    });

}
function saveAddUser() {
    var reg = /^[a-zA-Z0-9_]{6,20}$/;
    if($('#username').textbox('getValue')==null || $('#username').textbox('getValue')==''){
        $.messager.alert('输入有误',"请重新输入登录名！", '');
        return false;
    }
    if($('#username').textbox('getValue').length<4){
        $.messager.alert('输入有误',"请输入登录名4-16字符！", '');
        return false;
    }
    if($('#name').textbox('getValue')==null || $('#name').textbox('getValue')==''){
        $.messager.alert('输入有误',"请重新输入姓名！", '');
        return false;
    }
    if($('#password').textbox('getValue')==null || $('#password').textbox('getValue')==''||!reg.test($("#password").textbox('getValue'))){
        $.messager.alert('输入有误',"密码格式输入不正确,只能输入字母、数字、_ , 长度不低于6！", '');
        return false;
    }
    if($('#rePassword').textbox('getValue')==null || $('#rePassword').textbox('getValue') != $('#password').textbox('getValue')){
        $.messager.alert('输入有误',"两次输入密码不一致，请重新输入!", '');
        return false;
    }


    if($('#organizationId').combotree('getValue')==null || $('#organizationId').combotree('getValue')==''){
        $.messager.alert('输入有误',"请选择所属部门！", '');
        return false;
    }
    if($('#roleId').combobox('getValue')==null || $('#roleId').combobox('getValue')==''){
        $.messager.alert('输入有误',"请选择角色！", '');
        return false;
    }
    if($('#password').textbox('getValue').length<32){
        $('#password').textbox('setValue',hex_md5($('#password').textbox('getValue')));
    }

    $.ajax({
        type : 'POST',
        url : ctx+'/user/add',
        data:$("#userAddForm").serialize(),
        success : function(data) {
            if(data.success == true){

                $.messager.alert('成功',data.msg, 'info');
                $('#userAddDiv').dialog('close');
                $('#dataGrid').datagrid('reload');
                return true;
            }else{
                $.messager.alert('失败',data.msg, 'error');
                return false;
            }
        }
    });
    $('#dataGrid').datagrid('clearSelections');
}
function saveEditUser() {
    var reg = /^[a-zA-Z0-9_]{6,20}$/;
    if($('#username_edit').textbox('getValue')==null || $('#username_edit').textbox('getValue')==''){
        $.messager.alert('输入有误',"请输入登录名！", '');
        return false;
    }
    if($('#username_edit').textbox('getValue').length<4){
        $.messager.alert('输入有误',"请输入登录名4-16字符！", '');
        return false;
    }
    if($('#name_edit').textbox('getValue')==null || $('#name_edit').textbox('getValue')==''){
        $.messager.alert('输入有误',"请输入操作员！", '');
        return false;
    }
    if($('#password_edit').textbox('getValue')!=null &&$('#password_edit').textbox('getValue')!=''){
        if(!reg.test($("#password_edit").textbox('getValue'))){
            $.messager.alert('输入有误',"密码格式输入不正确,只能输入字母、数字、_ , 长度不低于6！", '');
            return false;
        }
    }
    /*if($('#sex_edit').textbox('getValue')==null || $('#sex_edit').textbox('getValue')==''){
        $.messager.alert('输入有误',"请选择性别！", '');
        return false;
    }
    if($('#age_edit').textbox('getValue')==null || $('#age_edit').textbox('getValue')==''){
        $.messager.alert('输入有误',"请输入年龄！", '');
        return false;
    }*/
    if($('#organizationId_edit').combotree('getValue')==null || $('#organizationId_edit').combotree('getValue')==''){
        $.messager.alert('输入有误',"请选择所属部门！", '');
        return false;
    }
    if($('#roleId_edit').combobox('getValue')==null || $('#roleId_edit').combobox('getValue')==''){
        $.messager.alert('输入有误',"请选择角色！", '');
        return false;
    }
    if($('#password_edit').textbox('getValue')!=null&&$('#password_edit').textbox('getValue')!=''&&$('#password_edit').textbox('getValue').length<32){
        $('#password_edit').textbox('setValue',hex_md5($('#password_edit').textbox('getValue')));
    }
    $.ajax({
        type : 'POST',
        url : ctx+'/user/edit',
        data:$("#userEditForm").serialize(),
        success : function(data) {
            if(data.success == true){
                $.messager.alert('成功',data.msg, 'info');
                $('#userEditDiv').dialog('close');
                $('#dataGrid').datagrid('reload');
                return true;
            }else{
                $.messager.alert('失败',data.msg, 'error');
                return false;
            }
        }
    });
    $('#dataGrid').datagrid('clearSelections');
}
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
}