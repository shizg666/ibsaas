/**
 * 自定义js工具
 */
;
(function ($) {
    "use strict";
    $.ksudi = {
        initDialog: function () {
            let option = {
                autoOpen: false,
                modal: true
            };
            let $dialogs = $(".ksd-dialog");
            if ($dialogs.length > 0) {
                $.each($dialogs, function (i, dialog) {
                    let $dialog = $(dialog);
                    let myoption = {
                        width: $dialog.width()
                    };
                    $.extend(myoption, option);
                    let dialogUI = $dialog.dialog(myoption);
                    if ($dialog.data("trigger")) {
                        let $dom = $("" + $dialog.data("trigger"));
                        if ($dom && $dom.length > 0) {
                            $dom.bind('click', function () {
                                dialogUI.dialog("open");
                            })
                        }
                    }
                    let $closeBtn = $dialog.find(".ksd-dialog-foot>.ksd-btn-border");
                    if ($closeBtn && $closeBtn.length > 0) {
                        $closeBtn.bind("click", function () {
                            dialogUI.dialog("close");
                        });
                    }
                })
            }
        },
        initDatePicker: function (option) {
            let defaultOption = {
                showOtherMonths: true,
                selectOtherMonths: true,
                constrainInput: true,
                changeMonth: true,
                changeYear: true,
                showArrow: true
            };
            $.extend(defaultOption, option || {});
            $(".datetime").datetimepicker(defaultOption);
            $(".date").datepicker(defaultOption);
            $(".time").timepicker(defaultOption);
        },
        initValidationEngine: function (option) {
            let defaultOption = {
                scroll: false,
                addPromptClass: 'formError-white',
                maxErrorsPerField: 1,
                promptPosition: 'topLeft',
                onFieldFailure: function () {
                    return false;
                }
            };
            $.extend(defaultOption, option || {});
            $("form.validationEngine").validationEngine('attach', defaultOption);
        },
        /**
         * 警告弹框
         */
        alert: function (content) {
            let $dom = $('<div class="ksd-dialog" title="温馨提示">\n' +
                '    <div class="ksd-dialog-content">\n' +
                '       <div class="ksd-dialog-tips">' +
                content +
                '       </div>' +
                '    </div>\n' +
                '    <div class="ksd-dialog-foot">\n' +
                '        <button type="button" class="ksd-btn-border">关闭</button>\n' +
                '    </div>\n' +
                '</div>');
            $dom.dialog({autoOpen: true, modal: true, width: 400});
            console.log($dom.find(".ksd-btn-border"));
            $dom.find(".ksd-btn-border").click(function () {
                $dom.dialog("close");
            })
        },
        confirm: function (options) {
            let defaults = {
                ksudiStaticPath: "",
                title: "提示",
                content: "确定吗？",
                buttonText: "确定",//自定义的按钮文本
                autoOpen: true,
                confirmFunc: function () {
                    return true;
                }
            };
            $.extend(defaults, options);
            let $dom = $('<div id="dialog2" class="ksd-dialog" title="' + defaults.title + '">\n' +
                '    <div class="ksd-dialog-content">\n' +
                '        <div class="ksd-dialog-status">\n' +
                '            <img src="' + defaults.ksudiStaticPath + '/ksudi/images/icon-dialogtips.png" class="ksd-status-img">\n' +
                '            <div class="ksd-status-text">' +
                defaults.content +
                '</div>\n' +
                '        </div>\n' +
                '    </div>\n' +
                '    <div class="ksd-dialog-foot">\n' +
                '        <button type="button" class="ksd-btn-border">取消</button>\n' +
                '        <button type="button" class="ksd-btn-text">' + defaults.buttonText + '</button>\n' +
                '    </div>\n' +
                '</div>');

            $dom.dialog({autoOpen: defaults.autoOpen, modal: true, width: 400});

            $dom.find(".ksd-btn-border").click(function () {
                $dom.dialog("close");
            });
            $dom.find(".ksd-btn-text").click(function () {
                let flag = defaults.confirmFunc(defaults.custom);
                if(flag){
                    $dom.dialog("close");
                }
            });
        }
    };
    /**
     * 校验自定义弹出错误提示
     */
    $.fn.showValidateError = function (errormsg) {
        $(this).validationEngine('showPrompt', "* " + errormsg, 'error', 'topLeft', true);
    };
    $.fn.ksudiTable = function (options) {
        options = $.extend(true, {}, defaults, options || {});
        return new KsudiTable($(this), options);
    };
    $.fn.ksudiConfirm = function (options) {
        let defaults = {
            autoOpen: true
        };
        $.extend(defaults, options);
        $(this).click(function () {
            $.ksudi.confirm(defaults);
        });
    };
    $.fn.upload = function (myoption) {
        let option = {
            ws: {
                url: 'ws://' + window.location.host + '/websocket/fileupload',
                onOpen: function () {
                    console.log("数据发送中...");
                },
                onMessage: function (evt) {
                    var result = evt.data;
                    console.log("数据已接收...");
                    console.log(result);
                },
                onClose: function () {
                    // 关闭 websocket
                    console.log("连接已关闭...");
                },
                onError: function () {
                    console.log("连接错误")
                }

            },
        }
    };
    //修复弹框下拉不能搜索
    $.widget("ui.dialog", $.ui.dialog, {
        open: function () {

            return this._super();
        },
        _allowInteraction: function (event) {
            return !!$(event.target).is(".select2-search__field") || this._super(event);
        }
    });
    //下拉框
    $("select").select2({language: "zh-CN"});
})(jQuery);

//清除ajax缓存
$.ajaxSetup({cache: false});