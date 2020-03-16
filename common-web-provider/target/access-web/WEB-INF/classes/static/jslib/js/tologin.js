
function submitForm(){
    if ($('#username').val() == "") {
        $('#userCue').html("<font color='red'><b>用户名不能为空</b></font>");
        return false;
    }
    if ($('#username').val().length < 4 || $('#username').val().length > 16) {
        $('#userCue').html("<font color='red'><b>用户名位4-16字符</b></font>");
        return false;
    }
    if ($('#password').val().length < 5) {
        $('#userCue').html("<font color='red'><b>密码不能小于" + 6 + "位</b></font>");
        return false;
    }
    /*if ($('#verifycode').val() == "") {
        $('#userCue').html("<font color='red'><b>验证码不能为空</b></font>");
        return false;
    }*/
    if($('#password').val().length<32){
        $('#password').val(hex_md5($('#password').val()));
    }
    $('#loginForm').submit();
}

/**
 *验证码刷新
 */
function verificationCode(){
    $('#randCodeImage').attr('src',ctx+'/kaptcha?' + Math.floor(Math.random() * 100));
}

$(function() {
    //防止页面嵌套
    var path = window.location.href;
    if (path.indexOf("kickout") > 0) {
       $.messager.confirm("温馨提示","您的账号已在别处登录；若不是您本人操作，请立即修改密码！","warning", function () {
            window.location.href = ctx + "/login";
        });
    }
    var copyright=new Date();//取得当前的日期
    var year=copyright.getFullYear();//取得当前的
    // 年份
    $("#vsup").html("V"+year);
    $("#vdiv").html("©"+year +"北京趋势科特科技有限公司. 版权所有");
    $('#loginForm').form({
        url:ctx+'/user/login',
        success:function(result) {
            if(result!=null&&""!=result){
                var r = $.parseJSON(result);
                if(r.success){
                    window.location.href = ctx+'/index';
                }else{
                    if(r.msg!=null&&r.msg!=''){
                        $('#userCue').html("<font color='red'><b>" + r.msg + "</b></font>");
                        verificationCode();
                    }else{
                        $('#userCue').html("<font color='red'><b>系统异常,请稍后尝试</b></font>");
                    }
                }
            }else{
                $('#userCue').html("<font color='red'><b>系统异常,请稍后尝试</b></font>");
            }
        }
    });
    $('#username').blur(function () {
        if ($('#username').val() == "") {
            $('#username').css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>用户名不能为空</b></font>");
        }if($('#username').val().length < 4 || $('#username').val().length > 16){
            $('#username').css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>用户名位4-16字符</b></font>");
        }else{
            $('#username').css({
                border: "1px solid #D7D7D7",
                boxShadow: "0 0 0px red"
            });
        }
    });
    $('#username').focus(function () {
        $('#username').css({
            border:"1px solid #198BD4",
            boxShadow:"0 0 2px #198BD4"
        });
    });
    $('#password').blur(function () {
        if ($('#password').val().length < 5) {
            $('#password').css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
        $('#userCue').html("<font color='red'><b>密码不能小于" + 6 + "位</b></font>");
        }else{
            $('#password').css({
                border: "1px solid #D7D7D7",
                boxShadow: "0 0 0px red"
            });
        }
    });
    $('#password').focus(function () {
        $('#password').css({
            border:"1px solid #198BD4",
            boxShadow:"0 0 2px #198BD4"
        });
    });
    /*$('#verifycode').blur(function () {
        if ($('#verifycode').val()=="") {
            $('#password').css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>验证码不能为空</b></font>");
        }else{
            $('#verifycode').css({
                border: "1px solid #D7D7D7",
                boxShadow: "0 0 0px red"
            });
        }
    });*/
});