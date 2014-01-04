$(function () {
    //截取字符串
    substring();
});

//截取列表字符长度
var substring = function () {

    $("[data-ellipsis]").each(function () {

        var $this = $(this);

        var $length = $this.attr("data-ellipsis") || 20;
//        $this.tooltip({title:$this.text(),placement :'top'});
        $this.attr("title", $this.text()) ;
        $this.html(subStrAtLen($.trim($this.text()), $length));

    });
};

var subStrAtLen = function (assignStr, assignLen) {
    var len = 0;
    var i = 0;
    var flag = 0;
    var tsubstr = '';
    for (; i < assignStr.length; i++) {

        if (assignStr.charCodeAt(i) > 255 || assignStr.charCodeAt(i) < 0) {

            len += 2;

        } else {

            len++;
        }

        if (assignLen < len) {
            tsubstr = assignStr.substr(0, i) + '<label style=\"font-family:Arial;\">...</label>';
            break;
        } else {
            tsubstr = assignStr;
        }
    }
    return tsubstr;
};


