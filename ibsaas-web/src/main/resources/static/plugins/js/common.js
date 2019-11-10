function isCellphone($logincellphone, $cellphoneloginerror) {
    var re = /^[1-9]+[0-9]*]*$/;
    var cellphone = $logincellphone.val().trim();

    if (cellphone.length != 11 || !re.test(cellphone)) {
        $cellphoneloginerror.text(cellphone + "不是有效的手机号");
        $logincellphone.focus();
        return false;
    }
    return true;
}

function isCellphoneForReturn(cellphoneVal) {
    var re = /^1[0-9]{10}$/;
    var cellphone = cellphoneVal.trim();

    if (cellphone.length != 11 || !re.test(cellphone)) {
        return cellphone + "不是有效的手机号";
    }
    return null;
}

function isEmail($email, $emailerror) {
    var re = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
    var email = $email.val().trim();

    if (!re.test(email)) {
        $emailerror.text(email + "不是有效的邮箱");
        $email.focus();
        return false;
    }
    return true;
}

Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}