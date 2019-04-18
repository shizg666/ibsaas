(function () {
    function XCUPload(cfg) {
        cfg = cfg || {};
        this.XHR = null;
        this.xhrHandler = null;
        this.filePiece = 1024 * 1024 * 10;
        this.bytesStart = 0;
        this.cfg = {
            maxSize: cfg.maxSize || 1024 * 1024 * 1024 * 20,
            extFilters: cfg.extFilters || [],//限制的文件名
            savePath: cfg.savePath || "image",  //保存路径
            rename: cfg.rename || true,//是否需要重命名
            encrypt: cfg.enctype || false,//是否需要加密
            analysisApk: cfg.analysisApk || true,//是否需要解析apk
            fileNameUrl: cfg.fileNameUrl ||upImageCfg.fileNameUrl,//获取上传文件名路径
            uploadUrl: cfg.uploadUrl ||upImageCfg.uploadUrl,//上传路径
//            fileNameUrl: cfg.fileNameUrl || "http://pic.ksudi.com:8025/ksudi-upload/upload",//获取上传文件名路径
//            uploadUrl: cfg.uploadUrl || "http://pic.ksudi.com:8025/ksudi-upload/upload",//上传路径
            simLimit: cfg.simLimit || 1,//限制上传文件个数
            onComplete: cfg.onComplete,//上传成功后的回调
            onProgress: cfg.onProgress,//上传过程显示调用
//            onExtNameMisMatch: cfg.onExtNameMisMatch,
//            onFileCountExceed: cfg.onFileCountExceed,
//            onMaxSizeExceed: cfg.onMaxSizeExceed,
            onUploadError: cfg.onUploadError
        };
        this.file = cfg.file;
    }


    XCUPload.prototype = {
        onProgress: function (uploaded, total) {
            console.log(uploaded / total);
        },
        onUploadError: function (msg) {
            console.log(msg);
        },
        onComplete: function (info) {
            console.log(info)
        },
        //文件上传的总路口
        upload: function () {
            var isCheckOk = this.check();

            if (!isCheckOk.isOk) {
                this.get("onUploadError") ? this.get("onUploadError")(isCheckOk.msg) : this.onUploadError(isCheckOk.msg);
                return;
            }
            //需要做大小验证，后缀验证
            var vars = {
                name: this.file.name.toLowerCase(),
                type: this.file.type,
                size: this.file.size,
                dirtype:this.get("savePath"),
                modified: this.file.lastModifiedDate + ""
            };
            var tokenUrl = fAddVars(vars, this.get("fileNameUrl")) + "&" + fGetRandom();
            var that = this;
            $.ajax({
                async: true,
                url: tokenUrl,
                type: 'GET',
                dataType: 'json',
                jsonp: 'callback',
                timeout: 2000,
                success: function (data) {
                    if (data.code == 1 && data.fileName) {
                    	if(data.start == that.file.size){
                    		that.get("onComplete") ? that.get("onComplete")(data) : that.onComplete(data);
                    		return;
                    	}
                        var vars = {
                            saveName: data.fileName,
                            dirtype: that.get("savePath"),
                            analysisApk: that.get("analysisApk")
                        };
                        that.cfg.uploadURL = fAddVars(vars, that.get("uploadUrl"));
                        that.uploadFile(data.start);//
                    } else {
                    	that.get("onUploadError") ? that.get("onUploadError")(data.msg) : that.onUploadError(data.msg);
                    }
                },
                error: function (data) {
                	that.get("onUploadError") ? that.get("onUploadError")(data) : that.onUploadError(data);
                }
            });
        },
        //续传文件
        uploadFile: function (pos) {
            /** whether continue uploading. */
            var _url = this.get("uploadURL");
            this.resetXhr();
            this.bytesStart = pos;
            this.XHR = new XMLHttpRequest;
            this.xhrHandler = fExtend(this.uploadEventHandler, this);
            this.buildXHREventListener()
            this.XHR.upload.addEventListener("progress", this.xhrHandler, false)
            var blob = this.sliceFile(this.file, pos, pos + this.filePiece);
            var range = "bytes " + pos + "-" + (pos + blob.size) + "/" + this.file.size;
            console.log(range);
            this.XHR.open("POST", _url, !0);
            this.XHR.setRequestHeader("Content-Range", range);
            this.XHR.send(blob);
        },
        resetXhr: function () {
            if (this.XHR) {
                try {
                    this.XHR.upload.removeEventListener("progress", this.xhrHandler);
                        //this.XHR.removeEventListener("loadend", this.xhrHandler),
                        //this.XHR.removeEventListener("error", this.xhrHandler),
                        //this.XHR.removeEventListener("abort", this.xhrHandler),
                        //this.XHR.removeEventListener("readystatechange", this.xhrHandler);
                } catch (e) {
                    throw e;
                }
                this.XHR = null;
            }
        },
        uploadEventHandler: function (event) {
            var xhr = this.XHR;
            switch (event.type) {
                case "load":
                    var uploaded = 0; //已经上传的字节数
                    var respJson = null;
                    var bError = false;
                    try {
                        if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 308)) {
                            //解析返回数据
                            uploaded = (respJson = eval("(" + xhr.responseText + ")")) ? respJson.start : -1;
                        } else if (xhr.status < 500 && xhr.status >= 400) {
                            bError = true;
                        } else if (xhr.status < 200) {
                            return;
                        }
                        bError = (bError || respJson.code != 1);
                    } catch (e) {
                        if (!bError) {
                            this.get("onUploadError") ? this.get("onUploadError")(respJson.msg) : this.onUploadError(respJson.msg);
                            return;
                        }
                    }
                    if (bError) {
                        return;
                    }
                    if (uploaded > 0 && uploaded < this.file.size-1) {
                        this.uploadFile(uploaded);
                    } else {
                        this.get("onProgress") ? this.get("onProgress")(this.file.size, this.file.size) : this.onProgress(uploadSize, this.file.size);
                        //回调前端上传成功函数
                        this.get("onComplete") ? this.get("onComplete")(respJson) : this.onComplete(respJson);
                    }
                    break;
                case "error":
                    console.log("error");
                    break;
                case "abort":
                    console.log("abort");
                    break;
                case "progress":
                    var uploadSize = this.bytesStart + this.filePiece;
                    this.get("onProgress") ? this.get("onProgress")(uploadSize, this.file.size) : this.onProgress(uploadSize, this.file.size);
                    break;
                case "readystatechange":
                	console.log("readystatechange");
                	break;
            }
        },


        check: function () {
            if (!this.file) {
                return {isOk: false, msg: "请选择文件"}
            }
            if (this.file.size > this.get("maxSize")) {
                return {isOk: false, msg: "文件过大"}
            }
            if (this.get("extFilters").length == 0) {
                return {isOk: true}
            } else {
                var fileName = this.file.name;
                var suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length);
                var length = this.cfg.extFilters.length;
                if(length!=0){
                	for (var i=0;i<length; i++) {
                    	var str = this.cfg.extFilters[i];
                        if (str == suffix) {
                            return {isOk: true}
                        }
                    }
                }
                return {isOk: false, msg: "上传文件格式不对"}
            }
        },

        //构建XMLHttpRequest 监听器数据
        buildXHREventListener: function () {
            //this.XHR.addEventListener("loadstart", this.xhrHandler, false);
            this.XHR.addEventListener("load", this.xhrHandler, false);
            //this.XHR.addEventListener("abort", this.xhrHandler, false);
            //this.XHR.addEventListener("error", this.xhrHandler, false);
            //this.XHR.addEventListener("loadend", this.xhrHandler, false);
            //this.XHR.addEventListener("readystatechange", this.xhrHandler, false);
        },
        sliceFile: function (f, startPos, endPos) {
            startPos = startPos || 0;
            endPos = endPos || 0;
            return f.slice(startPos, endPos);
        },
        get: function (key) {
            return this.cfg[key];
        }
    }


    /**
     * 随机数
     * @returns {string}
     */
    function fGetRandom() {
        return (new Date).getTime().toString().substring(8);
    }

    function fExtend(a, b) {
        var c = 2 < arguments.length ? [arguments[2]] : null;
        return function () {
            var d = "string" === typeof a ? b[a] : a;
            var e = c ? [arguments[0]].concat(c) : arguments;
            return d.apply(b || d, e);
        };
    }

    function fAddVars(json, url, c) {
        var _array = [], _sep = "&", f = function (json, c) {
            var e = url ? /\[\]$/.test(url) ? url : url + "[" + c + "]" : c;
            "undefined" != e && "undefined" != c
            && _array.push("object" === typeof json
                ? fAddVars(json, e, !0)
                : "[object Function]" === Object.prototype.toString.call(json)
                ? encodeURIComponent(e) + "=" + encodeURIComponent(json())
                : encodeURIComponent(e) + "=" + encodeURIComponent(json))
        };
        if (!c && url)
            _sep = /\?/.test(url) ? /\?$/.test(url) ? "" : "&" : "?",
                _array.push(url),
                _array.push(fAddVars(json));
        else if ("[object Array]" === Object.prototype.toString.call(json)
            && "undefined" != typeof json)
            for (var g = 0, c = json.length; g < c; ++g)
                f(json[g], g);
        else if ("undefined" != typeof json && null !== json && "object" === typeof json)
            for (g in json)
                f(json[g], g);
        else
            _array.push(encodeURIComponent(url) + "=" + encodeURIComponent(json));
        return _array.join(_sep).replace(/^&/, "").replace(/%20/g, "+");
    }

    window.XCUPload = XCUPload;

})
();



