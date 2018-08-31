/**
 * name: common.js
 * desc: 页面公用js
 * author: mayuliang
 * @type {{Self 页面主体, methods 公用方法, utils 公用类库, constant 常量, commonData 公用数据, service 接口}}
 */
var Global = (function () {

    // 解决No Transport错误 / 浏览器支持跨域访问
    jQuery.support.cors = true;
    // 页面主体
    var self = {};
    function Self(obj) {
        var that = this;
        for (var p in obj) {
            if (obj.hasOwnProperty(p)) {
                that[p] = obj[p];
            }
        }
        // 创建时加载
        if (that.created && typeof that.created === 'function') {
            that.created();
        }
        self = that;
    }
    /**
     * 页面加载完成执行方法
     */
    $(function () {
        // 解析自定义指令
        var $renderSingle = $('.renderSingle');
        if ($renderSingle.length > 0) {
            $renderSingle.each(function () {
                var $this = $(this);
                var prop = $this.attr('render-data');
                var content = self[prop] ? self[prop]: '';
                // console.log(self.projectName);
                $this.html(content);
                var newVal = content;
                if (self[prop] !== undefined) {
                    Object.defineProperty(self, prop, {
                        get: function () {
                            return newVal;
                        },
                        set: function (val) {
                            $this.html(val);
                            newVal = val;
                        }
                    });
                }
            });
        }
        // 解析数组
        var $renderArray = $('.renderArray');
        var iteratorSet = {}; // 迭代器集合
        if ($renderArray.length > 0) {
            $renderArray.each(function () {
                var $this = $(this);
                var prop = $(this).attr('render-data');
                var data = self[prop];
                var iterator = new CustomIterator($this);
                iterator.render(data);
                var newVal = self[prop];
                if (iteratorSet[prop]) {
                    iteratorSet[prop].push(iterator);
                } else {
                    iteratorSet[prop] = [iterator];
                }
                Object.defineProperty(self, prop, {
                    get: function () {
                        return newVal;
                    },
                    set: function (val) {
                        iteratorSet[prop].forEach(function (iterator) {
                            iterator.render(val);
                        });
                        newVal = val;
                    }
                });
            });
        }
        // 解析input、textarea等
        var $renderInput = $('.renderInput');
        if ($renderInput.length > 0) {
            $renderInput.each(function () {
                var $this = $(this);
                var prop = $(this).attr('render-data');
                var arr = prop.split('.');
                var defineObj, key, data;
                if (arr.length>1) {
                    defineObj = self[arr[0]];
                    key = arr[1];
                    data = defineObj[key];
                } else {
                    defineObj = self;
                    key = arr[0];
                    data = defineObj[key];
                }
                $this.val(data);
                var newVal = data;
                Object.defineProperty(defineObj, key, {
                    get: function () {
                        return newVal;
                    },
                    set: function (val) {
                        newVal = val;
                        var $renderInput = $('.renderInput');
                        if ($renderInput.length > 0) {
                            $renderInput.each(function () {
                                var $this = $(this);
                                if ($this.attr('render-data') === prop) {
                                    $this.val(val);
                                }
                            });
                        }
                        var $renderSingle = $('.renderSingle');
                        if ($renderSingle.length > 0) {
                            $renderSingle.each(function () {
                                var $this = $(this);
                                if ($this.attr('render-data') === prop) {
                                    $this.html(val);
                                }
                            })
                        }
                    }
                });
                $this.blur(function () {
                    defineObj[key] = $this.val();
                });
            });
        }
        // 解析分页
        var $pagination = $('.pagination');
        if ($pagination.length > 0) {
            $pagination.each(function () {
                var $this = $(this);
                var prop1 = $this.attr('pageObj');
                var prop2 = $this.attr('callback');
                var pageObj = self[prop1];
                var callback = self[prop2];
                new CustomPage($this, pageObj, callback);
            })
        }
        if (self.mounted && typeof self.mounted === 'function') {
            self.mounted();
        }
    });

    // 全局设置
    var settings = {
      // base_rap_url: 'http://rap.ctsp.kedacom.com/mockjs/13',
      ajax_url: 'http://localhost:8080'
    };
    /**
     * 自定义下拉
     * @param ele
     * @param dataObj
     * @param callback
     * @constructor
     */
    function CustomDropDown(ele, dataObj, callback){
        this.dropdown = ele;
        this.placeholder = this.dropdown.find(".placeholder");
        this.menu = this.dropdown.find("ul.dropdown-menu");
        // this.template = this.menu.html();
        this.value = '';
        this.index = 0;//默认为0;
        this.text = this.placeholder.text();
        this.dataObj = dataObj;
        this.callback = callback;
        this.initEvents();
    }
    CustomDropDown.prototype = {
        initEvents:function () {
            var obj = this;

            var array = obj.dataObj || [];
            var arrayLen = array.length;
            (function () {
                for (var i = 0; i < arrayLen; i++) {
                    var $child = $('<li class="ellispis">'+ array[i].name  +'</li>');
                    $child.attr('value', array[i].value).attr('title', array[i].name);
                    obj.menu.append($child);
                    obj.menu.children('li').each(function () {
                        var $this = $(this);
                        if ($this.text() === obj.text) {
                            $this.addClass('active');
                            obj.value = $this.attr('value');
                        }
                    });
                }
            })();

            //这个方法可以不写，因为点击事件被Bootstrap本身就捕获了，显示下面下拉列表
            obj.dropdown.on("click",function(){
                $(this).toggleClass("active");
            });

            //点击下拉列表的选项
            obj.menu.on("click", 'li', function () {
                var opt = $(this);
                obj.value = opt.attr("value");
                obj.index = opt.index();
                opt.addClass('active').siblings().removeClass('active');
                if (opt.text()) {
                    obj.text = opt.text();
                    obj.placeholder.text(obj.text);
                } else {
                    obj.text = '';
                    obj.placeholder.text('请选择');
                }
                obj.callback && obj.callback.call(self);
            });
        },
        getText:function () {
            return this.text;
        },
        getValue:function () {
            return this.value;
        },
        getIndex:function () {
            return this.index;
        },
        /**
         *  禁用
         */
        disabled: function () {
            var $toggleFlag = this.dropdown.find('.dropdown-toggle');
            this.dropdown.click(function () {
                return false
            });
            $toggleFlag.attr('disabled', 'disabled');
        },
        /**
         * 启用
         */
        enable: function () {
            var $toggleFlag = this.dropdown.find('.dropdown-toggle');
            this.dropdown.unbind('click');
            $toggleFlag.removeAttr('disabled');
        },
        setCurrentValue: function (val) {
            var that = this;
            that.value = val + '';
            that.menu.children('li').each(function () {
                var $this = $(this);
                if ($this.attr('value') + '' === that.value) {
                    $this.addClass('active').siblings().removeClass('active');
                    that.placeholder.text($this.text());
                }
            });
            that.callback && that.callback();
        },
        setCurrentText: function (text) {
            var that = this;
            that.text = text + '';
            that.placeholder.text(that.text);
            that.menu.children('li').each(function () {
                var $this = $(this);
                if ($this.text() + '' === that.text) {
                    $this.addClass('active').siblings().removeClass('active');
                }
            });
            that.callback && that.callback();
        }
    };
    /**
     * 自定义单选按钮
     * @param ele
     * @param callback
     * @constructor
     */
    function CustomRadio(ele, callback){
        this.radio = ele;
        this.value = 4;
        this.initEvents();
        this.callback = callback;
    }
    CustomRadio.prototype = {
        initEvents:function(){
            var obj = this;
            var $radios = obj.radio;
            $radios.on('click', 'input', function () {
                var $this = $(this);
                $this.attr('checked', true);
                $this.parent().siblings().find("input[type='radio']").removeAttr('checked');
                obj.value = $this.attr('value');
                obj.callback && obj.callback();
            });
        },
        getValue: function(){
            return this.value;
        }
    };
    /**
     * 自定义分页
     * @param ele
     * @param pageObj
     * @param callback
     * @constructor
     */
    function CustomPage(ele, pageObj, callback) {
        this.page = ele.find('ul');
        this.template = this.page.html();
        // this.pageObj = pageObj;
        // 最多展示条数
        this.maxDispaly = 10;
        var that = this;
        this.pageObj = {
            'pageNo': pageObj.pageNo,
            'pageSize': pageObj.pageSize,
            'total': pageObj.total
        };
        Object.defineProperty(pageObj, 'total', {
            get: function () {
                return that.pageObj['total'];
            },
            set: function (val) {
                that.pageObj['total'] = val;
                that.pageChange();
            }
        });
        Object.defineProperty(pageObj, 'pageNo', {
            get: function () {
                return that.pageObj['pageNo'];
            },
            set: function (val) {
                that.pageObj['pageNo'] = val;
                that.pageChange();
            }
        });
        Object.defineProperty(pageObj, 'pageSize', {
            get: function () {
                return that.pageObj['pageSize'];
            },
            set: function (val) {
                that.pageObj['pageSize'] = val;
                that.pageChange();
            }
        });
        (function () {
            that.page.on('click', 'li', function () {
                var opt = $(this);
                var className = opt.attr('class') || '';
                if (className.indexOf('active') === -1 && className.indexOf('disabled') === -1){
                    if (className.indexOf('prev') !== -1) {
                        that.pageObj.pageNo -= 1;
                    } else if (className.indexOf('next') !== -1) {
                        that.pageObj.pageNo += 1;
                    } else {
                        that.pageObj.pageNo = opt.text() - 1;
                    }
                    callback.call(self);
                }
            })
        })();
    }
    CustomPage.prototype = {
        pageChange: function () {
            var obj = this;
            // renderHtml
            obj.page.html(obj.template);

            var total = obj.pageObj.total;
            if (!total || total === 0) {
                obj.page.addClass('hide');
            } else {
                obj.page.removeClass('hide');
            }
            var pageSize = obj.pageObj.pageSize;
            var display = Math.ceil(total/pageSize);

            var ellipsis = false;
            var maxDisplay = obj.maxDispaly;
            var totalDisplay = display;

            var j; // 分页展示的起始页码
            if (display > maxDisplay) {
                display = maxDisplay;
                ellipsis = true;
                // 起始最大页码
                var max = totalDisplay - maxDisplay + 2 > 1 ? totalDisplay - maxDisplay + 2 : 1;
                // (pageNo+1) - (maxDisplay - 2 - 2) = pageNo - maxDisplay + 5
                j = obj.pageObj.pageNo - maxDisplay + 5 > 1 ? (obj.pageObj.pageNo - maxDisplay + 5) : 1;
                j = (j >= max ? max : j);
            } else {
                j = 1;
            }
            var i = 0;
            var t = 1;
            for (; t <= maxDisplay; i++, j++,t++) {
                if (j!==1 && t===1 && ellipsis === true) {
                    obj.page.children('li').eq(i).after("<li class='ellipsis disabled'><a>"+ '...' +'</a>');
                    j--;
                }else if (ellipsis === true && t === display && j < totalDisplay) {
                    obj.page.children('li').eq(i).after("<li class='ellipsis disabled'><a>"+ '...' +'</a>');
                } else if (j <= totalDisplay){
                    obj.page.children('li').eq(i).after('<li><a>'+ j +'</a>');
                }
            }
            // 加active
            obj.page.children('li').each(function () {
                if (parseInt($(this).text()) === obj.pageObj.pageNo + 1) {
                    $(this).addClass('active')
                }
            });
            // 加disabled
            if (obj.pageObj.pageNo === 0) {
                obj.page.children('.prev').addClass('disabled');
            }
            if (obj.pageObj.pageNo === totalDisplay - 1) {
                obj.page.children('.next').addClass('disabled');
            }
        }
    };
    /**
     * 自定义迭代器
     * @param ele
     * @param callback
     * @constructor
     */
    var CustomIterator = function (ele, callback) {
        this.self = ele;
        this.template = this.self.html();
        this.callback = callback;
        var that = this;
        (function () {
            that.self.html('');
        })()
    };
    CustomIterator.prototype.render = function (data) {
        var that = this;
        var len = data.length;
        that.self.html('');
        for (var i = 0; i < len; i++) {
            // 添加模板html
            that.self.append(that.template);
            var obj = that.self.children().eq(i);
            if (obj.attr('render-class')){
                var prop = obj.attr('render-class');
                obj.addClass(data[i][prop]);
            }
            renderHtml(obj, i);
            that.callback && that.callback();
        }
        function renderHtml(obj, index) {
            var children = obj.children();
            var len = children.length;
            if (len > 0) {
                for (var j = 0; j < len; j++) {
                    if ($(children[j]).attr('property')) {
                        var property = $(children[j]).attr('property');
                        if ($(children[j])[0].tagName === 'INPUT') {
                            $(children[j]).val(data[index][property]);
                            $(children[j]).blur(function () {
                                data[index][property] = $(this).val();
                            });
                        } else {
                            $(children[j]).html(data[index][property]);
                        }
                    }
                    if ($(children[j]).attr('href')) {
                        var href = $(children[j]).attr('href');
                        href = href.replace(/\{\{([^}]+)\}\}/gmi, function(model){
                            var result = '';
                            var property = model.substring(2,model.length-2);
                            result = data[index][property] ? data[index][property]: '';
                            return result;
                        });
                        // console.log(href);
                        $(children[j]).attr('href', href);
                    }
                    if ($(children[j]).attr('@click')) {
                        var func = $(children[j]).attr('@click');
                        var funcName = func.substring(0, func.indexOf('('));
                        var param = func.substring(func.indexOf('(')+1, func.indexOf(')'));
                        param = param.replace(/\{\{([^}]+)\}\}/gmi, function(model){
                            var result = '';
                            var property = model.substring(2,model.length-2);
                            result = data[index][property];
                            return result;
                        });
                        (function (funcName, param) {
                            $(children[j]).on("click", function(){
                                var arg = param.split(',');
                                self[funcName].apply(self, arg);
                            });
                        }(funcName, param));
                    }
                    renderHtml($(children[j]), index);
                }
            }
        }
    };

    /**
     * 上传文件类
     * @param ele jq对象
     * @param setting 配置对象
     */
    var InputFile = function (ele, setting) {
        this.inputFile = ele;
        this.inputFile.fileinput({
            language: 'zh', //设置语言
            uploadUrl: setting.url, //"http://rap.ctsp.kedacom.com/mockjs/13/project/apply/publish", //上传的地址
            showCaption: false,//是否显示标题
            dropZoneEnabled: false,//是否显示拖拽区域
            maxFileCount: setting.max, //表示允许同时上传的最大文件个数
            minFileCount: 1,
            allowedFileExtensions: setting.allowedFileExtensions || '',
            ajaxSettings: {
                xhrFields: {
                    withCredentials: true
                }
            }
            // msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！"
        });
        // 选择完文件之后
        this.inputFile.on("filebatchselected", function() {
            $('.file-preview-success').remove();
        });
    };
    // 上传完成
    InputFile.prototype.fileuploaded = function (successcallback, failcallback) {
        this.inputFile.on("fileuploaded", function (event, data, previewId, index){
            var res = data.response;
            if (res.status === 200) {
                successcallback(event, data, previewId, index);
            } else if (res.status === 401){
                // 401:未登录 && 重定向到登录页
                var start = window.location.href.indexOf('/html/');
                var commonUrl = window.location.href.substring(0, start+6);
                window.location.href = commonUrl + 'login/login.html';
            } else {
                failcallback(event, data, previewId, index);
            }
        });
    };
    // 清空
    InputFile.prototype.filecleared = function (callback) {
        this.inputFile.on("filecleared", function (event, data, previewId, index){
            callback(event, data, previewId, index);
        })
    };


    return {
        // 页面主体类
        Self: Self,
        // 共用方法
        methods: {
            // 获取参数
            getQuery: function () {
                var hashDeatail = location.href.split("?"),
                    params = hashDeatail[1] ? hashDeatail[1].split("&") : [],//参数内容
                    query = {};
                for (var i = 0; i < params.length; i++) {
                    var item = params[i].split("=");
                    query[item[0]] = item[1]
                }
                return query;
            },
            // 头部生成
            headerCreate: function (module) {
                var headerHtml = '<div class="header">\n' +
                    '    <div class="navbar">\n' +
                    '        <div class="navbar-inner">\n' +
                    '            <div class="container">\n' +
                    '                <a class="brand" href="../project/project-ssss.html">Dolphin</a>\n' +
                    '                <ul class="nav">\n' +
                    '                </ul>\n' +
                    '                <div class="userInfo">' +
                    '                   <span class="username"></span>' +
                    '                   <span class="caret"></span>' +
                    '                   <ul class="top-frame"><li><a href="../login/edit-password.html">修改密码</a></li><li><a class="logout">注销</a></li></ul>' +
                    '               </div>\n' +
                    '            </div>\n' +
                    '        </div>\n' +
                    '    </div>\n' +
                    '</div>';
                var template = "<li><a class='item'></a></li>";
                var menuList = Global.commonData.menuList ?
                    Global.commonData.menuList : [];
                var len = menuList.length;
                var $header = $(headerHtml);
                $('body').prepend($header);
                for (var i = 0; i < len; i++) {
                    var $child = $(template);
                    $child.find('.item').attr('href', menuList[i].href).html(menuList[i].name);
                    if (module === menuList[i].module) {
                        $child.attr('class', 'active');
                    }
                    $header.find('.navbar .nav').append($child);
                }
                if (Global.commonData.cd_user.username) {
                    $('.userInfo .username').text(Global.commonData.cd_user.username);
                    $('.userInfo').click(function () {
                        $(this).find('.top-frame').toggle();
                    });
                    $('.userInfo .logout').click(function () {
                       Global.service.logout(function () {
                           sessionStorage.clear();
                           window.location.href = '../login/login.html';
                       },function () {
                           sessionStorage.clear();
                           window.location.href = '../login/login.html';
                       });
                    });
                }
            },
            // 非空验证
            notNull: function(str) {
                return str !== "";
            },
            // 正则验证
            ruleTest: function (str, patternTxt) {
                var patternObj = new RegExp(patternTxt);
                // 中文、英文、数字包括下划线和中划线
                return patternObj.test(str);
            },
            /**
             * ajax请求
             * @param param 参数
             * @param successCallBack 成功回调
             * @param failCallBack 失败回调
             */

            ajaxRequest: function (param, successCallBack, failCallBack) {
                // 跨域设置
                param['xhrFields'] = {};
                param['xhrFields']['withCredentials'] = true;
                $.ajax(param).done(function (res) {
                    if (res.status === 200) {
                        successCallBack(res);
                    } else if (res.status === 401) {
                        // 401:未登录 && 重定向到登录页
                        window.location.href = 'login.html';
                    } else {
                        failCallBack(res);
                    }
                }).fail(function (err) {
                    failCallBack(err);
                });
            },
            /**
             * alert弹框
             * @param setting 配置参数
             */
            alert: function (setting) {
                var className;
                if (setting.type === 'error') {
                    className = 'alert-error';
                } else if (setting.type === 'success') {
                    className = 'alert-success';
                }
                var template =  '<div class="alert alert-block ' + className + '"' +
                    '>\n' +
                    '        <p>'+ setting.info +'</p>\n' +
                    '    </div>';
                var $child = $(template);
                $('body').append($child);
                $child.fadeIn();
                setTimeout(function () {
                    $child.fadeOut();
                }, setting.duration || 1000);
                setTimeout(function () {
                    $child.remove();
                }, setting.duration + 1000 || 1000 + 1000);
            },
            /**
             * 时间转换方法
             * @param timeNumber
             * @param fmt
             * @returns {*}
             */
            format: function (timeNumber, fmt) {
                var date = new Date(timeNumber);
                var o = {
                    'M+': date.getMonth() + 1, // 月份
                    'd+': date.getDate(), // 日
                    'h+': date.getHours(), // 小时
                    'm+': date.getMinutes(), // 分
                    's+': date.getSeconds(), // 秒
                    'q+': Math.floor((date.getMonth() + 3) / 3), // 季度
                    'S': date.getMilliseconds() // 毫秒
                };
                if (/(y+)/.test(fmt)) {
                    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
                }
                for (var k in o) {
                    if (new RegExp('(' + k + ')').test(fmt)) {
                        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)));
                    }
                }
                return fmt;
            },
            /**
             * 返回对象在数组的下标
             * @param param 比较属性
             * @param obj 比较对象
             * @param objArray 目标数组
             * @returns index
             */
             findInArray: function (param, obj, objArray) {
                if (objArray instanceof Array) {
                    var len = objArray.length;
                    for (var i = 0; i < len; i++) {
                        if (objArray[i][param] === obj[param]) {
                            return i;
                        }
                    }
                    return -1;
                } else {
                    throw new Error('请传入正确的参数');
                }
            }
        },
        // 工具类
        utils: {
            // 自定义下拉
            CustomDropDown: CustomDropDown,
            // 自定义单选按钮组
            CustomRadio: CustomRadio,
            // 自定义迭代器
            CustomIterator: CustomIterator,
            // 上传文件插件
            InputFile: InputFile
        },
        // 静态变量
        constant: {
            // BASEURL: settings.base_rap_url
            BASEURL: settings.ajax_url
        },
        // 公用数据
        commonData: {
            cd_user: sessionStorage.getItem('cd_user') ? JSON.parse(sessionStorage.getItem('cd_user')) : {}
        },
        // 接口
        service: {
        }
    };
})();