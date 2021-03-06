class KsudiTable1 {
    constructor($element, options) {
        this.$element = $element;
        /**
         * 默认参数
         * @description 除了contextPath、tabs、buttons其他参数都可以在tabs中的每一个tab（配置{@link defaultTab}）进行覆写
         * @name defaults
         * @property {string} contextPath 项目名，必填
         * @property {array} tabs 标签页（{@link defaultTab}）,默认空
         * @property {array} buttons 按钮组（{@link defaultButton}）
         * @property {string} url URL，默认''
         * @property {boolean} autoSearch 自动查询，默认false
         * @property {boolean} autoReset 切换标签时自动重置，默认false
         * @property {boolean} multipleEnable 是否使用多选，默认false
         * @property {boolean} paginationEnable 是否分页，默认true
         * @property {boolean} exportEnable 是否允许导出，默认false
         * @property {object} exportOptions 导出配置,当 exportEnable = true 时生效({@link exportOptions})
         * @property {boolean} customEnable 是否允许自定义表单，默认false
         * @property {array} operations 列表操作按钮组（{@link defaultOperation}）
         * @property {array} fields 列表字段（{@link defaultField}）
         * @property {jQuery} searchForm 查询表单
         * @property {jQuery} searchBtn 查询按钮
         * @property {jQuery} resetBtn 重置按钮
         * @property {function} beforeSearch 查询前参数处理
         * @property {function} resetFunc 重置方法
         * @property {function} selectFunc 选中后触发的事件
         */
        this.defaults = {
            contextPath: '',
            tabs: [],
            buttons: [],
            url: '',
            autoSearch: false,
            autoReset: false,
            multipleEnable: false,
            paginationEnable: true,
            exportEnable: false,
            /**
             * 导出配置
             * @name exportOptions
             * @property {integer} module {@link MODULE},默认DATA
             * @property {array} fileType {@link FILETYPE},可以配置多个，默认EXCEL
             * @property {string} ps 操作权限，必填
             * @property {string} name 导出报表名称
             * @property {string} serviceUrl 路径，格式：service/method/VO
             */
            exportOptions: {
                module: MODULE.DATA,
                fileType: [FILETYPE.EXCEL],
                ps: '',
                name: '',
                serviceUrl: ''
            },
            customEnable: false,
            operations: [],
            fields: [],
            searchForm: $(""),
            searchBtn: $(""),
            resetBtn: $(""),
            beforeSearch: function (params) {
                //查询前处理参数
                params = params || {};
                return params;
            },
            resetFunc: function () {
                //只要设置默认值
            },
            selectFunc: function ($selectedTr, value) {
                //选中后触发的事件
            },
            dblclickFunc: function (value) {
                //双击后触发的事件
            }
        };
        this.options = $.extend(true, {}, defaults, options || {});
        /**
         * 默认列表配置
         * @name defaultField
         * @property {string} title 标题
         * @property {string} name 对应数据的key
         * @property {string} type 数据格式,默认{@link FORMAT}.STRING
         * @property {string} class 特殊样式
         * @property {boolean} primaryKey 是否主键，业务标识，默认false
         * @property {function} formatFunc 自定义格式化方法，只有type是{@link FORMAT}.CUSTOM才生效
         */
        this.defaultField = {
            title: "",
            name: "",
            type: FORMAT.STRING,
            class: "",
            primaryKey: false,
            filterEnable: false,
            formatFunc: function (value) {
            }
        };
        /**
         * 默认按钮配置
         * @name defaultButton
         * @property {string} id 按钮id，必填
         * @property {string} text 按钮名称，必填
         * @property {string} ps 按钮权限，必填
         * @property {object} methods 方法
         * @property {function} methods.show 控制按钮显示，返回true：显示，返回false：不显示
         * @property {function} methods.click 点击按钮触发事件，详见{@link defaultButton_methods_click}
         */
        this.defaultButton = {
            id: "",//按钮id
            text: "",//按钮名称
            ps: "",//按钮权限
            methods: {
                show: function () {
                    //控制显示，返回true是显示，反之不显示
                    return true;
                },
                /**
                 * 点击事件
                 * @callback defaultButton_methods_click
                 * @param $selectedTr 当前选中行
                 * @param values 所有选中值
                 */
                click: function ($selectedTr, values) {
                    //$selectedTr 当前选中的$(tr)
                    //$selectedValue 所有选中的值（fields中pringKey为true所对应的值）
                }
            }
        };
        /**
         * 默认标签页配置，特有属性
         * @name defaultTab
         * @property {string} id 标签id，必填
         * @property {string} text 标签名称，必填
         * @property {string} name 标签对应的查询参数名称
         * @property {string} value 标签对应的查询值
         * @property {string} ps 按钮权限，必填
         * @property {boolean} default 是否默认选中，默认false
         * @property {object} methods 方法
         * @property {function} methods.show 控制按钮显示，返回true：显示，返回false：不显示
         * @property {function} methods.click 点击按钮触发事件
         */
        this.defaultTab = {
            id: "",//标签id
            text: "",//标签名称
            name: "",//标签对应的查询参数名称
            value: "",//标签对应的查询值
            default: false,//是否默认选中，如果有多个，以最后一个为主，如果都不配置，以第一个为主
            ps: "",//权限
            methods: {
                show: function () {
                    return true;
                },
                click: function () {

                }
            }
        };
        /**
         * 默认的操作组配置
         * @name defaultOperation
         * @property {string} class 自定义样式
         * @property {string} text 操作名称
         * @property {string} ps 操作权限，必填
         * @property {object} methods 方法
         * @property {function} methods.show 控制按钮显示，返回true：显示，返回false：不显示，详见{@link defaultOperation_methods_show}
         * @property {function} methods.click 点击按钮触发事件，详见{@link defaultOperation_methods_click}
         */
        this.defaultOperation = {
            class: "",//自定义样式
            text: "",
            ps: "",
            methods: {
                /**
                 * 显示控制
                 * @callback defaultOperation_methods_show
                 * @param data 当前选中行对应的data
                 * @return {boolean}
                 */
                show: function (data) {
                    return true;
                },
                /**
                 * 点击事件
                 * @callback defaultOperation_methods_click
                 * @param $selectedTr 当前选中行
                 * @param $selectedValue 当前选中值
                 * @param data 当前选中行对应的data
                 */
                click: function ($selectedTr, $selectedValue, data) {

                }
            }
        };
    }


    build() {
        //没有权限，不做渲染
        let psList = typeof(PERMISSION_SIGNALS) === "undefined" ? "" : PERMISSION_SIGNALS;
        if (psList === "") {
            return false;
        }
        let buildstart = new Date().getTime();
        console.log("======================ksdui-table,build-start:" + buildstart);
        let options_ = this.options;
        options_.target = this.$element;
        this._fuseOptions(options_);
        //当前生效的配置
        let effectiveOptions = options_.currentTab;
        console.log(effectiveOptions);
        //一定要先渲染表格
        let $table = this._createTableContainer(effectiveOptions);
        this.$element.html($table);
        $table.before(this._createButtonContainer(effectiveOptions))
            .before(this._createTabContainer(options_));//生成标签要用原来的options
        if (effectiveOptions.autoSearch) {
            this._search(effectiveOptions);
        }
        let buildend = new Date().getTime();
        console.log("======================ksdui-table,build-end:" + buildend);
        console.log("======================ksdui-table渲染耗时:" + (buildend - buildstart) + "ms");
        return this;
    }

    refresh() {
        let options_ = this.options;
        options_.target = this.$element;
        this._fuseOptions(options_);
        //当前生效的配置
        let effectiveOptions = options_.currentTab;
        this._search(effectiveOptions);
        return this;
    }

    /**
     * 融合配置
     * @param options
     * @private
     */
    _fuseOptions(options) {
        let tabs = options.tabs;
        let defaultIndex = 0;
        //设置当前tab的配置
        if (tabs.length > 0) {
            //找到默认
            $.each(tabs, function (i, tabOption) {
                tabOption = $.extend(true, {}, defaultTab, tabOption);
                tabOption = $.extend({}, options, tabOption);
                if (tabOption.default) {
                    if (defaultIndex > 0) {
                        tab[defaultIndex].default = false;
                    }
                    defaultIndex = i;
                    tabs[defaultIndex].default = true;
                }
                tabs[i] = tabOption;
                //在searchForm中放入div块，方便后面的隐藏域放入
                let $searchForm = tabOption.searchForm;
                //如果查询带分页，需要在searchForm中添加一些隐藏域，方便查询
                if (tabOption.paginationEnable && $searchForm && $searchForm.find(".customParams").length === 0) {
                    let $hiddenInputDiv = $(document.createElement("div")).addClass("customParams");
                    tabOption.searchForm.append($hiddenInputDiv);
                    //如果需要分页，添加
                    //在searchForm中添加两个隐藏域：pageNum 和 pageSize
                    $hiddenInputDiv.append($(document.createElement("input")).attr("type", "hidden").attr("name", "pageNum").addClass("pageNum"))
                        .append($(document.createElement("input")).attr("type", "hidden").attr("name", "pageSize").addClass("pageSize"));
                }
            });
            if (defaultIndex === 0) {
                tabs[0].default = true;
            }
            options.currentTab = tabs[defaultIndex];
            options.tabs = tabs;
        } else {
            options.currentTab = options;
            //在searchForm中放入div块，方便后面的隐藏域放入
            let $searchForm = options.searchForm;
            //如果查询带分页，需要在searchForm中添加一些隐藏域，方便查询
            if (options.paginationEnable && $searchForm && $searchForm.find(".customParams").length === 0) {
                let $hiddenInputDiv = $(document.createElement("div")).addClass("customParams");
                options.searchForm.append($hiddenInputDiv);
                //如果需要分页，添加
                //在searchForm中添加两个隐藏域：pageNum 和 pageSize
                $hiddenInputDiv.append($(document.createElement("input")).attr("type", "hidden").attr("name", "pageNum").addClass("pageNum"))
                    .append($(document.createElement("input")).attr("type", "hidden").attr("name", "pageSize").addClass("pageSize"));
            }
        }
    }

    /**
     * 查询
     * @param options
     * @private
     */
    _search(options) {
        let $target = options.target;
        let params = {};
        //获取json对象
        options.searchForm.serializeArray().map(function (x) {
            params[x.name] = x.value;
        });
        params = options.beforeSearch(params);
        if (params === false) {
            return;
        }
        let url = options.url;
        let searchStart = new Date().getTime();
        console.log("======================ksdui-table,search-start:" + searchStart);
        $.get(
            options.contextPath + url,
            params,
            function (data) {
                let searchHandle = new Date().getTime();
                console.log("======================ksdui-table,search-handle:" + searchHandle);
                console.log("======================ksdui-table查询耗时:" + (searchHandle - searchStart) + "ms");
                //如果有多选，将全选置为非选中状态
                $target.find("#allcheckbox").prop("checked", false);
                //渲染table thead部分
                this._createTableHeader(options);
                //渲染table tbody部分
                this._createTableBody(options, data);
                //渲染pages
                let $pages = this._createPages(options, data);
                $target.find(".ksd-page-total .ksd-page").remove();
                $target.find(".ksd-page-total").append($pages);
                this._resize(options);
                let searchEnd = new Date().getTime();
                console.log("======================ksdui-table,search-end:" + searchEnd);
                console.log("======================ksdui-table查询结果渲染耗时:" + (searchEnd - searchHandle) + "ms");
            })
    }

    /**
     * 重新调整尺寸
     * @param options
     * @private
     */
    _resize(options) {
        let $target = options.target;
        // 设置表格高度
        let innerHeight = document.documentElement.clientHeight;
        let headHeight = $('.header').height();
        let menuHeight = $('.ksd-menu').height();
        let infoHeight = $('.ksd-info').height();
        let scrollHeight = innerHeight - headHeight - menuHeight - infoHeight - 215;
        let tableHeight = $target.find('.ksd-table').height();
        let tableTitleHeight = $target.find('.ksd-table-head tr th').height();
        //控制滚动条
        if (tableHeight >= scrollHeight) {
            $target.find('.ksd-table').css({'height': scrollHeight, 'overflow-y': 'auto"'});
        }
        $target.find('.ksd-table-left tr th').css('height', tableTitleHeight - 1);
        //左侧操作列和右侧同步滚动
        $target.find('.ksd-rt-content').unbind("scroll").bind("scroll", function () {
            let scrollTop = this.scrollTop;
            let scrollLeft = this.scrollLeft;
            if ($target.find(".ksd-table-operate").length > 0) {
                $target.find(".ksd-table-operate")[0].scrollTop = scrollTop;
            }
            $target.find(".tableContent .ksd-table-head")[0].scrollLeft = scrollLeft;
        });
        $target.find('.ksd-table-operate').css('maxHeight', scrollHeight);
        //如果右侧有横向滚动条
        console.log(tableHeight);
        console.log(scrollHeight);
        if (tableHeight >= scrollHeight) {
            $target.find('.ksd-rt-content').css({'height': scrollHeight, 'overflow-y': 'auto"'});
            $target.find('.ksd-table-operate').css('maxHeight', scrollHeight - 20);
        }

        // 设置头部表格的宽度
        let tableWidth = $target[0].clientWidth - 10;
        let operationDivWidth = 0;
        if ($target.find('.ksd-table-left').length > 0) {
            operationDivWidth = $target.find('.ksd-table-left')[0].offsetWidth;
            $target.find('.ksd-table-left').css('width', operationDivWidth);
        }
        $target.find('.ksd-table').css('width', tableWidth);
        let rightTitle = tableWidth - operationDivWidth - 10;
        $target.find('.ksd-table-content').css('width', rightTitle);
        $target.find('.ksd-table-content .ksd-table-title').css('width', rightTitle);
        $target.find('.ksd-table-content table').css('maxWidth', rightTitle);
        // 出现横向滚动条时表格宽度
        let $content = $target.find('.ksd-table-content');
        //实际高度
        let offsetHeight = $content[0].offsetHeight;
        //可视高度
        let clientHeight = $content[0].clientHeight;
        if (offsetHeight > clientHeight) {
            $target.find('.ksd-table-operate').css('height', scrollHeight - 20);
        }
    }

    /**
     * 重置
     * @param options
     * @private
     */
    _reset(options) {
        let $searchForm = options.searchForm;
        let $target = options.target;
        let $customParams = $searchForm.find(".customParams");
        $customParams.remove();
        options.searchForm[0].reset();
        options.searchForm.append($customParams);
        //总记录数设为0
        $target.find(".totalSpan").html(0);
        //页码设为1
        $searchForm.find(".pageNum").val(1);
        $searchForm.find(".pageSize").val($(".page-number-list").html());
        options.resetFunc();
    }

    /**
     * 生成按钮组，形式如下
     * <div class="ksd-btninfo">
     *     <div class="form-group">
     *         <button type="button" class="ksd-btn-border" id="{{id}}">{{text}}</button>
     *     </div>
     * </div>
     * @param options
     * @returns {*|HTMLElement}
     * @private
     */
    _createButtonContainer(options) {
        let $target = options.target;
        let $buttonContainer = $("");
        let buttons = options.buttons;
        if (buttons.length > 0) {
            let $container = $(document.createElement('div'));
            $container.attr("class", "ksd-btninfo");
            let $formGroup = $(document.createElement('div'));
            $formGroup.attr("class", "form-group");
            $container.html($formGroup);
            $.each(buttons, function (i, buttonOption) {
                buttonOption = $.extend(true, {}, defaultButton, buttonOption);
                if (buttonOption.methods.show() && this._hasPermission(buttonOption)) {
                    let $button = $(document.createElement("button"));
                    $button.attr("type", "button").attr("class", "ksd-btn-border").attr("id", buttonOption.id).text(buttonOption.text);
                    $button.unbind("click").bind("click", function () {
                        let $selected = $(".ksd-rt-content table tbody tr.selected");
                        let value;
                        //如果是多选的,取多选框的值，否则取tr上的值
                        if (options.multipleEnable) {
                            value = [];
                            $.each($target.find(".ksd-table-operate table tbody tr input:checked"), function () {
                                value.push($(this).val());
                            });
                        } else {
                            value = $target.find(".ksd-rt-content table tbody tr.selected").data("value");
                        }
                        buttonOption.methods.click($selected, value);
                    });
                    $formGroup.append($button);
                }
            });
            $buttonContainer = $container;
        }
        return $buttonContainer;
    }

    /**
     * 生成tab页签，形式如下
     * <div class="ksd-tab f-cb">
     *     <ul>
     *        <li class="active" id="{{id}}">{{text}}
     *             <div class="ksd-tips">12<i>!</i></div>
     *        </li>
     *     </ul>
     * </div>
     * @param options
     * @returns {*|HTMLElement}
     * @private
     */
    _createTabContainer(options) {
        let $tabContainer = $("");
        let tabs = options.tabs;
        if (tabs.length > 0) {
            let $container = $(document.createElement('div'));
            $container.attr("class", "ksd-tab f-cb");
            let $ul = $(document.createElement('ul'));
            $container.html($ul);
            let name = '';
            let $searchForm = options.searchForm;
            $.each(tabs, function (i, tabOption) {
                if (tabOption.methods.show() && this._hasPermission(tabOption)) {
                    let $tab = $(document.createElement("li"));
                    $tab.attr("id", tabOption.id).text(tabOption.text);
                    //设置默认的标签页
                    if (tabOption.default) {
                        //使用默认tab的searchForm
                        $searchForm = tabOption.searchForm;
                        //再searchForm中添加隐藏域，查询时作为条件
                        name = tabOption.name === "" ? name : tabOption.name;
                        if (name.length > 0) {
                            if ($searchForm.find(".customParams input[name='" + name + "']").length === 0) {
                                $searchForm.find(".customParams").append($(document.createElement("input")).attr("type", "hidden").attr("name", name).val(tabOption.value));
                            }
                        }
                        //默认选中
                        $tab.addClass("active");
                        $searchForm.find(".customParams input[name='" + name + "']").val(tabOption.value);
                    }
                    $tab.unbind("click").bind("click", function () {
                        //点击切换样式
                        $(this).addClass("active").siblings().removeClass("active");
                        //将对应value填入隐藏域
                        $searchForm.find("input[name='" + name + "']").val(tabOption.value);
                        options.currentTab = tabOption;
                        tabOption.methods.click(options);
                        //重新渲染表头
                        let $table = this._createTableContainer(tabOption);
                        let $orignalTable = $(".ksd-table-info");
                        $orignalTable.after($table);
                        $orignalTable.remove();
                        if (tabOption.autoReset) {
                            this._reset(tabOption);
                        }
                        this._search(tabOption);
                    });
                    //查询按钮
                    tabOption.searchBtn.unbind("click").bind("click", function () {
                        this._search(tabOption);
                    });
                    //重置按钮
                    tabOption.resetBtn.unbind("click").bind("click", function () {
                        this._reset(tabOption);
                    });
                    $ul.append($tab);
                }
            });
            $tabContainer = $container;
        } else {
            //查询按钮
            options.currentTab.searchBtn.unbind("click").bind("click", function () {
                this._search(options.currentTab);
            });
            //重置按钮
            options.currentTab.resetBtn.unbind("click").bind("click", function () {
                this._reset(options.currentTab);
            });
        }
        return $tabContainer;
    }

    /**
     * 生成表格
     * <div class="ksd-table-info">
     *  <div class="ksd-table">
     *         {{operationDiv}}
     *         {{tableContent}}
     *  </div>
     *  {{pageinfo}}
     * </div>
     * @param options
     * @returns {*|HTMLElement}
     * @private
     */
    _createTableContainer(options) {
        let $tableDiv = $(document.createElement("div")).attr("class", "ksd-table f-cb");
        if (options.multipleEnable || !options.multipleEnable && options.operations.length > 0) {
            let $operationDiv = this._createOperationDiv(options);
            $tableDiv.append($operationDiv);
        }
        let $tableContent = this._createTableContent(options);
        $tableDiv.append($tableContent);
        let $tableContainer = $(document.createElement("div")).addClass("ksd-table-info").html($tableDiv);
        $tableContainer.append(this._createTableFooter(options));
        return $tableContainer;
    }

    /**
     * <div class="ksd-table-left">
     *     <table><thead><tr></tr></thead></table>
     *      {{operationBody}}
     * </div>
     * @param options
     * @private
     */
    _createOperationDiv(options) {
        let $operationDiv = $("");
        if (options.multipleEnable || !options.multipleEnable && options.operations.length > 0) {
            $operationDiv = $(document.createElement("div")).addClass("ksd-table-left").addClass("operationDiv");
        }
        return $operationDiv;
    }


    /**
     * 生成表容器
     * <div class="ksd-table-content">
     * </div>
     * @param options
     * @returns {*|HTMLElement}
     * @private
     */
    _createTableContent(options) {
        return $(document.createElement("div")).addClass("ksd-table-content").addClass("tableContent");
    }

    /**
     *  生成表头
     *   <div class="ksd-table-head" id="t_r_t">
     *      <div class="ksd-table-title">
     *          <table>
     *             <thead>
     *                  <tr>
     *                      <th>{{title}}</th>
     *                  </tr>
     *              </thead>
     *          </table>
     *      </div>
     *   </div>
     * @param options
     * @return {*}
     * @private
     */
    _createTableHeader(options) {
        let $target = options.target;
        let $tableHeader = $(document.createElement("div")).addClass("ksd-table-head");
        let $tableTitle = $(document.createElement("div")).addClass("ksd-table-title");
        let $table = $(document.createElement("table"));
        let $thead = $(document.createElement("thead"));
        let fields = options.fields;
        if (fields.length > 0) {
            let $tr = $(document.createElement("tr"));
            $thead.html($tr);
            //需要多选
            $.each(fields, function (i, field) {
                field = $.extend(true, {}, this.defaultField, field);
                if (!field.primaryKey) {
                    let $th = $(document.createElement("th")).text(field.title);
                    //允许过滤
                    if (field.filterEnable) {
                        let $filterInput = $(document.createElement("input")).attr("type", "text").addClass("ksd-block-sel");
                        $filterInput.bind("keyup", function (e) {
                            /*let keynum;
                            if (window.event) {//ie
                                keynum = e.keyCode
                            }
                            else if (e.which) {// Netscape/Firefox/Opera
                                keynum = e.which
                            }
                            if (keynum === 13) {*/
                            let value = $(this).val();
                            let index = $th.index();
                            let $tbodyTrs = $target.find(".ksd-rt-content tbody tr");
                            let $opreationTrs = $target.find(".ksd-table-operate tbody tr");
                            if (value.length > 0) {
                                let $trs = $tbodyTrs.find("td:eq(" + index + "):contains('" + value + "')").parent();
                                $tbodyTrs.hide();
                                if ($opreationTrs.length > 0) {
                                    $opreationTrs.hide();
                                }
                                if ($trs.length > 0) {
                                    $trs.show();
                                    if ($opreationTrs.length > 0) {
                                        $.each($trs, function (i, tr) {
                                            console.log($(tr).index());
                                            $opreationTrs.eq(i).show();
                                        });
                                    }
                                }
                            } else {
                                $tbodyTrs.show();
                                if ($opreationTrs.length > 0) {
                                    $opreationTrs.show();
                                }
                            }

                            /*}*/
                        });
                        $th.append($filterInput)
                    }
                    $tr.append($th);
                }
            })
            ;
        }
        $table.append($thead);
        $tableTitle.append($table);
        $tableHeader.append($tableTitle);
        //替换表头
        $target.find(".ksd-table-head").remove();
        $target.find(".ksd-table-content").append($tableHeader);
    }

    /**
     * 生成表体
     * 操作列：
     * <div class="ksd-table-operate" id="cl_freeze">
     *     <table><tbody>
     *         <tr>
     *             <input id="checkbox4" class="ksd-checkbox" type="checkbox">
     *             <label for="checkbox4"></label>
     *         </tr>
     *         <tr>
     *             <div class="ksd-operate">
     *                 <a href="">启用</a>
     *                 <a href="">关闭</a>
     *                 {{operations}}
     *             </div>
     *         </tr>
     *     </tbody></table>
     * </div>
     * 数据列：
     * <div class="ksd-rt-content" id="t_r_content">
     *      <table><tbody>
     *          <tr>
     *              <td>{{fields}}</td>
     *          </tr>
     *      </tbody></table>
     * </div>
     * @param options
     * @param data
     * @private
     */
    _createTableBody(options, data) {
        let $target = options.target;
        //操作列标题
        if (options.multipleEnable || !options.multipleEnable && options.operations.length > 0) {
            let $headTable = $(document.createElement("table"));
            let $thead = $(document.createElement("thead"));
            let $tr = $(document.createElement("tr"));
            if (options.multipleEnable) {
                let $th1 = $(document.createElement("th")).addClass("col-md-min");
                let $allCheckbox = $(document.createElement("input")).addClass("ksd-checkbox")
                    .attr("type", "checkbox").attr("id", "allcheckbox");
                let $alllabel = $(document.createElement("label")).attr("for", "allcheckbox");
                $th1.append($allCheckbox).append($alllabel);
                $tr.append($th1);
                $allCheckbox.unbind("click").bind("click", function () {
                    //找到对应body
                    $allCheckbox.parents("table").next().find("input[type='checkbox']").prop("checked", $allCheckbox.prop("checked"));
                });
            } else if (options.operations.length > 0) {
                let $th = $(document.createElement("th")).html("操作");
                $tr.append($th);
            }
            $thead.html($tr);
            $headTable.html($thead);
            $target.find(".operationDiv").html($headTable);
        }
        let $operationBody = $(document.createElement("div")).addClass("ksd-table-operate");
        let $operationTable = $(document.createElement("table"));
        let $operationTbody = $(document.createElement("tbody"));
        $operationBody.html($operationTable);
        $operationTable.html($operationTbody);

        let $tableContentBody = $(document.createElement("div")).addClass("ksd-rt-content");
        let $table = $(document.createElement("table"));
        let $tbody = $(document.createElement("tbody"));
        let fields = options.fields;
        if (fields.length > 0 && data && data.data) {
            $.each(data.data, function (i, item) {
                //有操作列
                let $optr = $(document.createElement("tr"));
                if (options.multipleEnable || !options.multipleEnable && options.operations.length > 0) {
                    if (options.multipleEnable) {
                        let $td = $(document.createElement("td")).addClass("col-md-min");
                        let $checkbox = $(document.createElement("input")).addClass("ksd-checkbox")
                            .attr("type", "checkbox").attr("id", "tableCheckbox" + i);
                        let $label = $(document.createElement("label")).attr("for", "tableCheckbox" + i);
                        $td.append($checkbox).append($label);
                        $optr.append($td);
                    } else if (options.operations.length > 0) {
                        let $td = $(document.createElement("td"));
                        let $div = $(document.createElement("div")).addClass("ksd-operate");
                        $.each(options.operations, function (i, operation) {
                            let operationOption = $.extend(true, {}, defaultOperation, operation);
                            if (operationOption.methods.show(item) && this._hasPermission(operationOption)) {
                                let $operation = $(document.createElement("a")).addClass(operationOption.class).html(operationOption.text);
                                $operation.bind("click", function () {
                                    let $selected = $(this).parents("tr");
                                    let value = $selected.data("value");
                                    operationOption.methods.click($selected, value, item);
                                });
                                $div.append($operation);
                            }
                        });
                        $td.html($div);
                        $optr.append($td);
                    }
                    $operationTbody.append($optr);
                }

                //展示表格
                let $tr = $(document.createElement("tr"));
                $tbody.append($tr);
                $.each(fields, function (j, field) {
                    //主要业务主键列，不渲染页面元素
                    let value = this._getFieldValue(item, field.name);
                    value = this._format(field.type, value, field.formatFunc);
                    if (field.primaryKey) {
                        $tr.data("value", value);
                        $optr.data("value", value);
                        $optr.find("input").val(value);
                    } else {
                        let $td = $(document.createElement("td")).attr("title", value).data("field", field.name).html(value)
                            .unbind("click").bind("click", function () {
                                $tr.addClass("selected").siblings().removeClass("selected");
                                $optr.addClass("selected").siblings().removeClass("selected");
                                let $checkbox = $target.find("#tableCheckbox" + i);
                                if ($checkbox.length > 0) {
                                    $checkbox.prop("checked", !$checkbox.prop("checked"));
                                    if ($checkbox.parents("tr").siblings().find("input[type='checkbox']").length === $checkbox.parents("tr").siblings().find("input:checked").length && $checkbox.is(":checked")) {
                                        $target.find("#allcheckbox").prop("checked", true);
                                    } else {
                                        $target.find("#allcheckbox").prop("checked", false);
                                    }
                                }
                                options.selectFunc($tr, $optr.data("value"));
                            }).unbind('dblclick').bind('dblclick', function () {
                                options.dblclickFunc($optr.data("value"));
                            });
                        $tr.append($td);
                    }
                })
            })
        }
        $table.html($tbody);
        $tableContentBody.html($table);

        //替换
        $target.find(".operationDiv .ksd-table-operate").remove();
        $target.find(".tableContent .ksd-rt-content").remove();
        $target.find(".operationDiv").append($operationBody);
        $target.find(".tableContent").append($tableContentBody);
    }

    /**
     * 获取对应值
     * @param data
     * @param fieldName
     * @return {*}
     * @private
     */
    _getFieldValue(data, fieldName) {
        let value = data[fieldName];
        if ((typeof(value) === "undefined" || value == null) && fieldName.indexOf("-") > 0) {
            let fields = fieldName.split("-");
            value = data;
            $.each(fields, function (i, field) {
                if (typeof(value) != "undefined" && value != null && value != "") {
                    value = value[field];
                } else {
                    value = "";
                }
            })
        }
        return value;
    }

    /**
     * 其他设置
     * @param $target
     * @private
     */
    _otherSetting($target) {
        //左侧操作列和右侧同步滚动
        $target.find('.ksd-rt-content').unbind("scroll").bind("scroll", function () {
            let scrollTop = this.scrollTop;
            let scrollLeft = this.scrollLeft;
            if ($target.find(".ksd-table-operate").length > 0) {
                $target.find(".ksd-table-operate")[0].scrollTop = scrollTop;
            }
            $target.find(".tableContent .ksd-table-head")[0].scrollLeft = scrollLeft;
        });

        // 设置表格高度
        let innerHeight = document.documentElement.clientHeight;
        let headHeight = $('.header').height();
        let menuHeight = $('.ksd-menu').height();
        let infoHeight = $('.ksd-info').height();
        let scrollHeight = innerHeight - headHeight - menuHeight - infoHeight - 215;

        let tableHeight = $target.find('.ksd-rt-content').height();
        $target.find('.ksd-table-operate').css('maxHeight', scrollHeight);
        if (tableHeight >= scrollHeight) {
            $target.find('.ksd-rt-content').css({'height': scrollHeight, 'overflow-y': 'auto"'});
            $target.find('.ksd-table-operate').css('maxHeight', scrollHeight - 20);
        }

        // 设置头部表格的宽度
        let tableWidth = $target.width() - 10;
        let tableleft = $target.find('.ksd-table-left').length > 0 ? $target.find('.ksd-table-left').width() : 0;
        $target.find('.ksd-table').css('width', tableWidth);
        let rightTitle = tableWidth - tableleft;
        $target.find('.ksd-table-content').css('width', rightTitle);
        $target.find('.ksd-table-title').css('width', rightTitle);
        $target.find('.ksd-table-content table').css('width', rightTitle);
        // 出现横向滚动条时操作列的高度
        let rightTable = $target.find('.ksd-table-content').width();
        if (rightTable < rightTitle) {
            $target.find('.ksd-table-operate').css('height', scrollHeight - 20);
        }
        console.log(tableWidth);
        console.log(tableleft);
        console.log(rightTable);
    }

    /**
     * 表格底部
     * <div class="ksd-page-total">
     *     {{export}}
     *     {{pagination}}-{{totalPageInfo}}
     *                   -{{pages}}
     * </div>
     * @param options
     * @return {*|HTMLElement}
     * @private
     */
    _createTableFooter(options) {
        let $tableFooter = $(document.createElement("div")).addClass("ksd-table-footer").addClass("f-cb");
        //导出
        $tableFooter.append(this._createExportCombobox(options));
        if (options.paginationEnable) {
            let $pagination = $(document.createElement("div")).addClass("ksd-page-total");
            $tableFooter.append($pagination);
            $pagination.append(this._createTotalPageInfo(options));
            $pagination.append(this._createPages(options));
        }
        return $tableFooter;
    }

    /**
     * 导出
     * <div class="ksd-export">导出：<span>Excel</span> <span>Pdf</span></div>
     * @param options
     * @return {*|jQuery|HTMLElement}
     * @private
     */
    _createExportCombobox(options) {
        let $exportCombobox = $("");
        if (options.exportEnable) {
            let exportOptions = options.exportOptions;
            $exportCombobox = $(document.createElement("div")).addClass("ksd-export").append("导出：");
            $.each(exportOptions.fileType, function (i, type) {
                let $exportType = $(document.createElement("span")).html(type.name);
                $exportCombobox.append($exportType);
                $exportType.click(function () {
                    this._export(options, exportOptions, type);
                })
            });
        }
        return $exportCombobox;
    }

    /**
     * 总数和每页条数
     *   <div class="ksd-page-number">
     *       <div class="page-total">总共<span id="totalSpan">{{total}}</span>条记录</div>
     *       <div class="page-total">
     *          <div class="page-text">每页显示</div>
     *          <div class="pagenumber">
     *              <div class="page-number-list">10</div>
     *              <ul class="page-select"><li>50</li><li>40</li><li>30</li><li>20</li><li>10</li></ul>
     *           </div>
     *           <div class="page-text">条数据</div>
     *       </div>
     *   </div>
     * @returns {*|HTMLElement}
     * @private
     */
    _createTotalPageInfo(options) {
        let $target = options.target;
        let $searchForm = options.searchForm;
        let pageSizes = [10, 20, 30, 40, 50];
        let $ksdPageNumber = $(document.createElement("div")).addClass("ksd-page-number");
        let $pageTotal1 = $(document.createElement("div")).addClass("page-total");
        let $totalSpan = $(document.createElement("span")).addClass("totalSpan");
        $pageTotal1.append("总共").append($totalSpan).append("条记录");

        let $pageTotal2 = $(document.createElement("div")).addClass("page-total");
        let $pagenumber = $(document.createElement("div")).addClass("pagenumber");
        let $pageSelect = $(document.createElement("ul")).addClass("page-select");
        $.each(pageSizes, function (i, page) {
            $pageSelect.prepend($(document.createElement("li")).html(page));
        });
        let $pageNumberList = $(document.createElement("div")).addClass("page-number-list").html(pageSizes[0]);
        $searchForm.find(".pageSize").val(pageSizes[0]);
        let $pageText1 = $(document.createElement("div")).addClass("page-text").html("每页显示");
        let $pageText2 = $(document.createElement("div")).addClass("page-text").html("条数据");

        $pagenumber.append($pageSelect).append($pageNumberList);
        $pageTotal2.append($pageText1).append($pagenumber).append($pageText2);
        $ksdPageNumber.append($pageTotal1).append($pageTotal2);

        //绑定事件
        $pageNumberList.unbind("click").bind("click", function () {
            $pageNumberList.toggleClass('active');
            $target.find('.page-select').fadeToggle();
            let oLi = $("ul.page-select li");
            $(oLi).unbind("click").bind("click", function () {
                let index = $(this).index();
                let oLiText = oLi.eq(index).text();
                $pageNumberList.text(oLiText);
                //隐藏域填入相应值
                $searchForm.find(".pageSize").val(oLiText);
                $target.find('.page-select').hide();
                $pageNumberList.removeClass('active');
            })
        });
        return $ksdPageNumber;
    }

    /**
     * 页码
     *   <div class="ksd-page">
     *       <a href=""><img src="../../resources/images/icon-first.png"></a>
     *       <a href=""><img src="../../resources/images/icon-prev.png"></a>
     *       <a href="" class="active">1</a>
     *       <a href="">2</a>
     *       <a href="">3</a>
     *       <a href="">4</a>
     *       <a href="">5</a>
     *       <a href=""><img src="../../resources/images/icon-next.png"></a>
     *       <a href=""><img src="../../resources/images/icon-last.png"></a>
     *   </div>
     * @param options
     * @param data
     * @return {*|HTMLElement}
     * @private
     */
    _createPages(options, data) {
        let $target = options.target;
        let $searchForm = options.searchForm;
        let contextPath = options.contextPath;
        let $ksdPage = $(document.createElement("div")).addClass("ksd-page");
        //首页
        let $first = $(document.createElement("a")).attr("val", 1)
            .html($(document.createElement("img")).attr("src", contextPath + "/ksudi/images/icon-first.png"));
        $ksdPage.append($first);
        if (data && data.pagination) {
            //首页绑定事件
            $first.unbind('click').bind('click', function () {
                $searchForm.find(".pageNum").val($(this).attr("val"));
                this._search(options)
            });
            let pagination = data.pagination;
            let pageNum = pagination[PAGINATION.PAGE_NUMBER];
            console.log(pageNum);

            //总条数赋值
            $target.find(".totalSpan").html(pagination[PAGINATION.RECORD_TOTAL]);

            //上一页
            let $prev = $(document.createElement("a")).attr("val", pagination[PAGINATION.PREVIOUS_PAGE] ? pageNum - 1 : 1)
                .html($(document.createElement("img")).attr("src", contextPath + "/ksudi/images/icon-prev.png"))
                .unbind('click').bind('click', function () {
                    $searchForm.find(".pageNum").val($(this).attr("val"));
                    this._search(options)
                });
            $ksdPage.append($prev);
            //页码
            $.each(pagination[PAGINATION.NAVIGATE_PAGES], function (i, no) {
                let $a = $(document.createElement("a")).attr("val", no).html(no)
                    .unbind('click').bind('click', function () {
                        $searchForm.find(".pageNum").val($(this).attr("val"));
                        this._search(options)
                    });
                if (no === pageNum) {
                    $a.addClass("active");
                }
                $ksdPage.append($a);
            });
            //下一页
            let $next = $(document.createElement("a")).attr("val", pagination[PAGINATION.NEXT_PAGE] ? pageNum + 1 : pageNum)
                .html($(document.createElement("img")).attr("src", contextPath + "/ksudi/images/icon-next.png"))
                .unbind('click').bind('click', function () {
                    $searchForm.find(".pageNum").val($(this).attr("val"));
                    this._search(options)
                });
            $ksdPage.append($next);
            //最后一页
            let $last = $(document.createElement("a")).attr("val", pagination[PAGINATION.PAGE_COUNT])
                .html($(document.createElement("img")).attr("src", contextPath + "/ksudi/images/icon-last.png"))
                .unbind('click').bind('click', function () {
                    $searchForm.find(".pageNum").val($(this).attr("val"));
                    this._search(options)
                });
            $ksdPage.append($last);
        } else {
            //总条数赋值
            $target.find(".totalSpan").html(0);
            //上一页
            let $prev = $(document.createElement("a")).attr("val", 1)
                .html($(document.createElement("img")).attr("src", contextPath + "/ksudi/images/icon-prev.png"));
            $ksdPage.append($prev);
            //页码
            let $a = $(document.createElement("a")).attr("val", 1).addClass("active").html(1);
            $searchForm.find(".pageNum").val(1);
            $ksdPage.append($a);
            //下一页
            let $next = $(document.createElement("a")).attr("val", 1)
                .html($(document.createElement("img")).attr("src", contextPath + "/ksudi/images/icon-next.png"));
            $ksdPage.append($next);
            //最后一页
            let $last = $(document.createElement("a")).attr("val", 1)
                .html($(document.createElement("img")).attr("src", contextPath + "/ksudi/images/icon-last.png"));
            $ksdPage.append($last);
        }
        return $ksdPage;
    }

    /**
     * 重置分页信息
     * @param options
     * @private
     */
    _resetPageInfo(options) {

    }

    /**
     * 格式转化
     * @param type
     * @param value
     * @param callback
     * @return {*}
     * @private
     */
    _format(type, value, callback) {
        if (FORMAT.DATE === type) {
            return this._dateFormat(value, 'yyyy-MM-dd');
        } else if (FORMAT.DATETIME === type) {
            return this._dateFormat(value, 'yyyy-MM-dd hh:mm:ss');
        } else if (FORMAT.MONEY === type) {
            return this._moneyFormat(value);
        } else if (FORMAT.DISTANCE === type) {
            return this._distanceFormat(value);
        } else if (FORMAT.WEIGHT === type) {
            return this._weightFormat(value);
        } else if (FORMAT.CUSTOM === type) {
            return callback(value);
        }
        return value;
    }

    /**
     * 格式化金钱
     * @param value
     * @return {string}
     * @private
     */
    _moneyFormat(value) {
        if (value == null || value === 0 || value === "0") {
            return "0.00";
        }
        let result = "";
        let str = value.toString();
        let len = str.length;
        if (len < 3) {// 小于3位
            if (len === 1) {
                return "0.0" + str;
            } else {
                return "0." + str;
            }
        }
        for (let i = 0; i < len; i++) {
            result += str.charAt(i);
            if (i === len - 3) {
                result += ".";
            }
        }
        return result;
    }

    /**
     * 格式化重量
     * @param value
     * @return {string}
     * @private
     */
    _weightFormat(value) {
        if (value == null || value === 0 || value === "0") {
            return "0.00";
        }
        let result = "";
        let str = value.toString();
        let len = str.length;
        if (len < 4) {// 小于4位
            if (len === 1) {
                return "0.00" + str;
            } else if (len === 2) {
                return "0.0" + str;
            } else if (len === 3 && str.charAt(2) === '0') {
                return "0." + str.charAt(0) + str.charAt(1);
            } else {
                return "0." + str;
            }
        }
        for (let i = 0; i < len; i++) {
            if (i === len - 1 && str.charAt(i) === '0') {
                result += "";
            } else {
                result += str.charAt(i);
            }
            if (i === len - 4) {
                result += ".";
            }
        }
        return result;
    }

    /**
     * 格式化距离
     * @param value
     * @return {string}
     * @private
     */
    _distanceFormat(value) {
        if (value == null || value === 0 || value === "0") {
            return "0.00";
        }
        let result = "";
        let str = value.toString();
        let len = str.length;
        if (len < 4) {// 小于4位
            if (len === 1) {
                return "0.00" + str;
            } else if (len === 2) {
                return "0.0" + str;
            } else if (len === 3 && str.charAt(2) === '0') {
                return "0." + str.charAt(0) + str.charAt(1);
            } else {
                return "0." + str;
            }
        }
        for (let i = 0; i < len; i++) {
            if (i === len - 1 && str.charAt(i) === '0') {
                result += "";
            } else {
                result += str.charAt(i);
            }
            if (i === len - 4) {
                result += ".";
            }
        }
        return result;
    }

    /**
     * 格式化日期
     * @param value
     * @param format
     * @return {*}
     * @private
     */
    _dateFormat(value, format) {
        if (isNaN(value)) {
            return '';
        } else if (Number(value) === 0) {
            return '';
        } else {
            let date = new Date(value);
            let o = {
                "M+": date.getMonth() + 1, // month
                "d+": date.getDate(), // day
                "h+": date.getHours(), // hour
                "m+": date.getMinutes(), // minute
                "s+": date.getSeconds(), // second
                "q+": Math.floor((date.getMonth() + 3) / 3), // quarter
                "S": date.getMilliseconds()
                // millisecond
            };
            if (/(y+)/.test(format))
                format = format.replace(RegExp.$1, (date.getFullYear() + "")
                    .substr(4 - RegExp.$1.length));
            for (let k in o)
                if (new RegExp("(" + k + ")").test(format))
                    format = format.replace(RegExp.$1, RegExp.$1.length === 1 ? o[k]
                        : ("00" + o[k]).substr(("" + o[k]).length));
            return format;
        }
    }

    _export(options, exportOptions, exportType) {
        //解析导出字段
        if (options.fields.length > 0) {
            let kv = {};
            $.each(options.fields, function (i, field) {
                if (!field.primaryKey) {
                    kv[field.title] = field.name;
                }
            });
            let params = {};
            //获取json对象
            options.searchForm.serializeArray().map(function (x) {
                params[x.name] = x.value;
            });
            params = options.beforeSearch(params);
            if (params !== false) {
                $.post(options.contextPath + '/ksudi/template/export', {
                    serviceurl: exportOptions.serviceUrl,
                    name: exportOptions.name,
                    exporttype: exportType.type,
                    projecttype: exportOptions.module,
                    param: JSON.stringify(params),
                    kv: JSON.stringify(kv)
                }, function (result) {
                    if (result.data != null) {
                        $.ksudi.alert("导出成功");
                    } else {
                        $.ksudi.alert("导出失败");
                    }
                })
            }
        }
    }

    /**
     * 判断是否存在权限
     * @param options
     * @returns {boolean}
     * @private
     */
    _hasPermission(options) {
        let psList = typeof(PERMISSION_SIGNALS) === "undefined" ? "" : PERMISSION_SIGNALS;
        if (psList === "") {
            return false;
        }
        if (options.ps === "") {
            return true;
        } else if (options.ps !== "") {
            let marchReg = new RegExp("([\\,\\[]{1})" + options.ps.trim() + "([\\,\\]]{1})");
            if (marchReg.test(psList)) {
                return true;
            }
        }
        return false;
    }
}