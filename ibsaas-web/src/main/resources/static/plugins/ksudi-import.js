let KsudiImport = function (options) {
    this.options = options = $.extend({
        /** 文件类型imput */
        fileInput: $(""),
        /** 文件显示DOM */
        displayDom: $(""),
        /** 搜索文件按钮 */
        explorBtn: $(""),
        /** 确认导入按钮 */
        importBtn: $(""),
        /** context路径 */
        contextPath: "/",
        wsBaseUrl: null,
        /** 导入文件后缀*/
        suffix: ["xls", "xlsx"],
        /** 导入处理的实现类 */
        beanName: null,
        /** 服务器文件路径 */
        filePath: "downupload",
        /** 导入信息对应的key值 */
        headKeys: [],
        /** 第几行开始是表头信息*/
        headIndex: 3,
        /** 文件大小限制(B)*/
        maxFileSize: 3 * 1024 * 1024,
        workid: "",
        step: 1024,
        params: {},
        /**
         * 业务自定义属性，无需配置
         */
        custom: {
            ws: null,
            file: null,
            fileExists: false,
            loaded: 0,
        },
        before: function (params) {
            return params;
        },
        /**
         * 提示信息
         * @param message
         */
        showMessage: function (message) {

        },
        handle: function (servermessage) {

        },
        /**
         * 上传进度控制
         * @param rate
         */
        uploadProgress: function (rate) {

        },
        handleProgress: function (rate) {

        },
        /** 导入成功后续处理 */
        complete: function (event) {

        },
        /** socket连接错误后续处理 */
        error: function (event) {

        }
    }, options);
};
const UploadStatus = {
    CREATE: "100",
    UPLOADING: "101",
    COMPLETE: "199"
};
KsudiImport.prototype = {
    constructor: KsudiImport,
    build: function () {
        let me = this;
        if (me.options.wsBaseUrl === null) {
            _getWsUrl(me.options);
        }
        me.options.explorBtn.click(function () {
            me.options.fileInput.trigger("click");
        });
        me.options.displayDom.click(function () {
            me.options.fileInput.trigger("click");
        });
        me.options.fileInput.change(function () {
            if (me.options.displayDom.is("input")) {
                me.options.displayDom.val(me.options.fileInput.val());
            }
        });
        me.options.importBtn.click(function () {
            //检查通过
            if (_fileCheck(me.options)) {
                //创建socket通讯
                _fileUploadConnection(me.options);
                //确保socket成功连接
                let count = 0;
                let times = setInterval(function () {
                    if (me.options.custom.ws.readyState === 1) {
                        clearInterval(times);
                        _uploadfile(me.options);
                    } else if (count === 20) {
                        clearInterval(times);
                    }
                    count++;

                }, 100);
            }
        });
        return me;
    },
    setParams(newParams) {
        let me = this;
        $.extend(me.options.params,newParams);
        return me;
    }
};

/**
 *
 * @param options
 * @private
 */
let _getWsUrl = function (options) {
    /*let url = window.location.href;
    url = url.replace("http://", "");
    let index = url.indexOf(options.contextPath);
    url = url.substring(0, index);*/
    let url = document.location.host;
    options.wsBaseUrl = "ws://" + url + options.contextPath;
};
/**
 *
 * @param options
 * @returns {boolean}
 * @private
 */
let _fileCheck = function (options) {
    //判断文件是否存在
    let file = options.fileInput[0].files[0];
    if (file == null) {
        options.showMessage("请选择文件");
        return false;
    }
    //判断导入处理的实现类是否存在
    if (options.beanName === null) {
        options.showMessage("处理器类名不能为空");
        return false;
    }
    //判断文件类型是否符合
    let suffix = file.name.substring(file.name.lastIndexOf(".") + 1);
    if ($.inArray(suffix, options.suffix) === -1) {
        options.showMessage("不支持该文件类型");
        return false;
    }
    //判断文件大小限制
    return true;
};

/**
 * 创建文件上传socket
 * @param options
 * @private
 */
let _fileUploadConnection = function (options) {
    if (options.custom.ws == null) {
        options.custom.ws = new WebSocket(options.wsBaseUrl + "/websocket/fileupload");
        options.custom.ws.binaryType = "arraybuffer";
        options.custom.ws.onopen = function (event) {
            console.log("socket建立连接.......");
        };
        /**
         * 收到服务器返回数据，所做处理
         * @param messageEnv
         */
        options.custom.ws.onmessage = function (messageEnv) {
            console.log("socket通信中.......");
            let data = $.parseJSON(messageEnv.data);
            if (data.code === UploadStatus.CREATE) {
                _readBlob(options);
                options.workid = data.workid;
            } else if (data.code === UploadStatus.UPLOADING) {
                _readBlob(options);
                options.uploadProgress(data.rate);
            } else if (data.code === UploadStatus.COMPLETE) {
                _stopRead(options);
                options.uploadProgress(data.rate);
            } else {//报错
                options.showMessage(data.message);
            }
        };
        options.custom.ws.onclose = function (event) {
            console.log("socket连接关闭.......");
            options.custom.ws = null;
            _progressConnection(options);
            let count = 0;
            let times = setInterval(function () {
                if (options.custom.ws.readyState === 1) {
                    clearInterval(times);
                    _getHandleProgress(options);
                } else if (count === 20) {
                    clearInterval(times);
                }
                count++;
            }, 100);
        };
        options.custom.ws.onerror = function (event) {
            console.log("socket连接出错.......");
            options.showMessage(event.reason);
            options.custom.ws = null;
        };
    }

};

/**
 * 上传文件
 * @param options
 * @private
 */
let _uploadfile = function (options) {
    //第一步，告知服务器：1.beanName、文件存放路径；2.文件信息
    let file = options.fileInput[0].files[0];
    options.custom.file = file;
    options.custom.loaded = 0;
    options.custom.enableRead = true;
    let clientMessage = {
        beanName: options.beanName,
        filePath: options.filePath,
        fileName: file.name,
        fileType: file.type,
        fileSize: file.size,
        lastModifiedDate: file.lastModifiedDate + "",
        headIndex: options.headIndex,
        headKeys: options.headKeys,
        params: JSON.stringify(options.params)
    };
    //让服务器创建文件
    options.custom.ws.send(JSON.stringify(clientMessage));
    //打开本地文件读取
    let fileReader = new FileReader();
    fileReader.onload = function (e) {
        if (options.custom.enableRead === true) {
            console.log("send data start");
            options.custom.ws.send(fileReader.result);
            console.log("send data end");
        }
    };
    options.custom.fileReader = fileReader;
};

/**
 * 分段读取文件
 * @param options
 * @private
 */
let _readBlob = function (options) {
    let file = options.custom.file;
    let blob = file.slice(options.custom.loaded, options.custom.loaded + options.step);
    console.log(options.custom.loaded);
    options.custom.loaded += options.step;
    options.custom.fileReader.readAsArrayBuffer(blob);
};

/**
 * 停止读取文件
 * @param options
 * @private
 */
let _stopRead = function (options) {
    options.custom.enableRead = false;
    options.custom.fileReader.abort();
};

/**
 * 进度查询socket
 * @param options
 * @private
 */
let _progressConnection = function (options) {
    if (options.custom.ws == null) {
        options.custom.ws = new WebSocket(options.wsBaseUrl + "/websocket/handleprogress");
        options.custom.ws.onopen = function (event) {
            console.log("socket建立连接.......");
        };
        /**
         * 收到服务器返回数据，所做处理
         * @param messageEnv
         */
        options.custom.ws.onmessage = function (messageEnv) {
            console.log("socket通信中.......");
            let data = $.parseJSON(messageEnv.data);
            options.handleProgress(data.rate);

        };
        options.custom.ws.onclose = function (event) {
            console.log("socket连接关闭.......");
            clearInterval(options.custom.progressInterval);
            if (event.code === 1000) {
                options.complete(event);
            } else {
                options.error(event);
            }
            options.custom.ws = null;
        };
        options.custom.ws.onerror = function (event) {
            console.log("socket连接出错.......");
            options.showMessage(event.reason);
            options.custom.ws = null;
        };
    }

};

/**
 * 查询进度
 * @param options
 * @private
 */
let _getHandleProgress = function (options) {
    let clientMessage = {
        beanName: options.beanName,
        workid: options.workid
    };
    //让服务器查询进度
    options.custom.ws.send(JSON.stringify(clientMessage));
    //查询进度
    let interval = setInterval(function () {
        options.custom.progressInterval = interval;
        options.custom.ws.send("progressRate");
    }, 100);
};