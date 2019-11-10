<!DOCTYPE html>
<html>
<head>
    <title>后台登录</title>
    <!-- for-mobile-apps -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <!-- //for-mobile-apps -->
    <link href="static/css/style.css" rel="stylesheet" type="text/css" media="all" />
    <script src="static/plugins/jquery-1.12.4.min.js"></script>
    <script src="static/plugins/select2/select2.js"></script>
    <script src="static/plugins/dome.js"></script>

    <script type="text/javascript" src="static/plugins/js/common.js" ></script>
    <script src="static/plugins/md5/md5.js" ></script>
    <script type="text/javascript" src="static/plugins/js/ssotool.js" ></script>
</head>
<body>
<!-- main -->
<div class="main">
    <h1>
        后台登录系统
    </h1>
    <form>
        <input type="text" value="用户名" onFocus="this.value = '';" onBlur="if (this.value == '') {this.value = '用户名';}"
               required="" id="username">
        <input type="password" value="Password" onFocus="this.value = '';" onBlur="if (this.value == '') {this.value = '';}"
               required="" id="password">
        <input type="button" id="usernameloginBtn" value="登录">
        <div  id="usernameloginerror"></div>
    </form>
</div>
<div class="footer">
    <p>
        Copyright &copy;2019 朗绿科技 Inc. All rights reserved.
    </p>
</div>
<!-- //main -->

<script>
    var origUrl = SSOTool.getUrlParam("origUrl");
    var projectType = SSOTool.getUrlParam("projectType");

    $("#usernameloginBtn").on("click", function(){

        var username=$("#username").val();
        var password=$("#password").val();
        if(!username){
            $("#usernameloginerror").text("请输入用户名");
            return false;
        }
        if(!password){
            $("#usernameloginerror").text("请输入密码");
            return false;
        }
        var paramObj = {"username":username, "password":hex_md5(password),"origUrl":origUrl,"projectType":projectType};
        $.ajax({
            url:"usernameLogin",
            data:paramObj,
            dataType:"json",
            type:"POST",
            success:function (data) {
                location.href=data.origUrl;
            },
            error:function (data) {
                location.href=data.origUrl;
            }
        });
    });
</script>
</body>
</html>