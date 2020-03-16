$(function () {

    $.ajax({
        type : 'POST',
        url : ctx+'/resource/tree',
        success : function(data) {
            if(data.success == false){
                $.messager.alert('失败', "获取菜单失败！", 'error');
                return false;
            }

            $.each(data.obj, function(i, n) {//加载父类节点即一级菜单
                var id = n.id;
                $('#layout_west_accordion').accordion('add', {
                    title : n.text,
                    content : '<div style="padding:10px;height:auto"><ul id="tree-'+ id + '" name="'+n.text+'"></ul></div>'
                });
                $.parser.parse();
                $("#tree-" + id).tree({
                    data : n.children,
                    onClick : function(node) {
                        if (node.attributes) {
                            var tabTitle = node.text;
                            var url = node.attributes.url;
                            var pid = node.id;
                            addTab(node.text,node.attributes.url);
                        }
                    }
                });
            });
            $("#layout_west_accordion").accordion('getSelected').panel('collapse')
        }
    });
    var copyright=new Date();//取得当前的日期
    var year=copyright.getFullYear();//取得当前的年份
    $("#southDiv").html("©"+year +"北京趋势科特科技有限公司. 版权所有");
    var index_tabs=$('#easyui_tabs_id').tabs({
        fit : true,
        border : false,
        tools : [{
            iconCls : 'icon-home',
            handler : function() {
                index_tabs.tabs('select', 0);
            }
        }, {
            iconCls : 'icon-refresh',
            handler : function() {
                var currentTab = $('#easyui_tabs_id').tabs('getSelected');
                RefreshTab(currentTab);
            }
        } , {
            iconCls : 'icon-del',
            handler : function() {
                var arrTitle = new Array();
                var id = "#easyui_tabs_id";//Tab所在的层的ID
                var tabs = $(id).tabs("tabs");//获得所有小Tab
                var tCount = tabs.length;
                if(tCount>0){
                    //收集所有Tab的title
                    for(var i=0;i<tCount;i++){
                        var title=tabs[i].panel('options').title;
                        if('系统首页'!=title){
                            arrTitle.push(title)
                        }
                    }
                    //根据收集的title一个一个删除=====清空Tab
                    for(var i=0;i<arrTitle.length;i++){
                        $(id).tabs("close",arrTitle[i]);
                    }
                }
            }
        }, {
            iconCls : 'icon-screen',
            handler : function() {
                toggleFullScreen();
                }
            }
        ]
    });

    // 生成Echarts图表
    // 1. 初始化echarts实例
    var myChart = echarts.init(document.getElementById('mainPart'));
    // 2. 指定图表的配置项和数据
    var option = {

        title: {
            text: '人员统计',
            subtext: '        --北京趋势科特'
        },

        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#283b56'
                }
            }
        },

        color: ['#7EC0EE', '#EEA9B8'],

        legend: {
            data: ['员工', '访客']
        },

        toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },

        xAxis: {
            type: 'category',
            data: []
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            name: '员工',
            type: 'bar',
            data: []
            },
            {
                name: '访客',
                type: 'bar',
                data: []
        }]
    };


    // 3. 获取所需数据
    $.ajax({
        type : "post",
        async : true,
        url : ctx + "/echarts/indexView",
        dataType:"json",
        success:function(series) {
            if( series != null )
            {
                myChart.setOption({
                    xAxis:{
                        data:series.xAxisDatas
                    },
                    series:series.series
                });
            }
        }
    });

    // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
});

function addTab(title, url){
    if ($('#easyui_tabs_id').tabs('exists', title)){
        $('#easyui_tabs_id').tabs('select', title);
        var currentTab = $('#easyui_tabs_id').tabs('getSelected');
        RefreshTab(currentTab);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+ctx+url+'" style="width:100%;height:100%;"></iframe>';
        $('#easyui_tabs_id').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}

function logout() {
    window.location.href=ctx+"/logout";
}

function updatePassword() {
    parent.$.modalDialog({
        title : '修改密码',
        width : 350,
        height : 200,
        href : ctx+'/user/updatePwdPage',
        buttons : [ {
            text : '保存',
            iconCls: "icon-ok",
            handler : function() {
                var f = $.modalDialog.handler.find('#updatePasswordEdit');
                f.submit();
            }
        } ],
        onLoad:function () {
            var f =$.modalDialog.handler.find('#updatePasswordEdit');
            f.form({
                url : ctx+'/user/updatePwd',
                onSubmit : function() {
                    if($('#newPwd').textbox('getValue').length<6){
                        $.messager.alert('温馨提示', '新密码不能小于6位', 'warning');
                        return false;
                    }
                    if($('#oldPwd').val().length<32){
                        $('#oldPwd').textbox('setValue',hex_md5($('#oldPwd').textbox('getValue')));
                    }
                    if($('#newPwd').val().length<32){
                        $('#newPwd').textbox('setValue',hex_md5($('#newPwd').textbox('getValue')));
                    }
                    return f.form('validate');
                },
                success : function(result) {
                    result = $.parseJSON(result);
                    if (result.success) {
                        $.messager.alert('成功', result.msg, 'info');
                        $.modalDialog.handler.dialog('close');
                    }else{
                        $.messager.alert('失败', result.msg, 'error');
                    }
                }
            });
        }
    });
}
//刷新当前标签Tabs
function RefreshTab(currentTab) {
    var url = $(currentTab.panel('options')).attr('href');
    $('#easyui_tabs_id').tabs('update', {
        tab: currentTab,
        options: {
            href: url
        }
    });
    currentTab.panel('refresh');
}
function changeTheme(color) {
    if(color.indexOf('light')>0){
        $('.accordion .accordion-header').css('background-color','#eeeeee');
        $('.accordion').css('background-color','#eeeeee');
        $('#easyui-web-north').css('background-color',color.substring(0,color.indexOf('light')));
        $('.tree-node span.tree-title ').css('color','black');
        $('.panel-body.accordion-body').css('background-color','white');
        $('.tree-node').hover(function () {
            $('.tree-node').css('background-color','white');
        });
        $('.tree-node').css('background-color','white');
        $('.panel-title').css('color','#777');
        $('.tree-node-selected').css('background-color','white');
    }else{
        $('#easyui-web-north').css('background-color',color);
        $('.accordion .accordion-header').css('background-color','#222d32');
        $('.accordion').css('background-color','#222d32');
        $('.tree-node span.tree-title ').css('color','white');
        $('.panel-body.accordion-body').css('background-color','#2c3b41');
        $('.tree-node').hover(function () {
            $('.tree-node').css('background-color','#2c3b41');
        });
        $('.tree-node').css('background-color','#2c3b41');
        $('.accordion').hover(function () {
            $('.accordion').css('color','#fff');
        });
        $('.panel-title').css('color','#b8c7ce');
        $('.tree-node-selected').css('background-color','#2c3b41');
    }
}
function toggleFullScreen() {
    if (!document.fullscreenElement && // alternative standard method
        !document.mozFullScreenElement && !document.webkitFullscreenElement && (!document.msFullscreenElement||undefined==document.msFullscreenElement)) {// current working methods
        if (document.documentElement.requestFullscreen) {
            document.documentElement.requestFullscreen();
        } else if (document.documentElement.mozRequestFullScreen) {
            //火狐
            document.documentElement.mozRequestFullScreen();
        } else if (document.documentElement.webkitRequestFullscreen) {
           //360
            document.documentElement.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT);
        }else if(document.documentElement.msRequestFullscreen){
            //ie
            docElm = document.body; //overwrite the element (for IE)
            docElm.msRequestFullscreen();
        }else{
            parent.$.messager.alert("温馨提示","此浏览器版本不支持全屏！","warning");
        }
    } else {
        if (document.cancelFullScreen) {
            document.cancelFullScreen();
        } else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
        } else if (document.webkitCancelFullScreen) {
            document.webkitCancelFullScreen();
        } else if(document.msExitFullscreen){
            document.msExitFullscreen();
        }else{
            parent.$.messager.alert("温馨提示","此浏览器版本不支持全屏！","warning");
        }

    }
}