/**
 * js通用工具
 * @type {{isOnLogin: (function(): boolean), checkLogin: yuheUtils.checkLogin}}
 */
var yuheUtils = {

    /**
     * 同步GET
     * @param url
     * @param params
     */
    get: function (url, params) {
        var result = '';
        if (!params) {
            params = {};
        }
        $.ajax({
            type: 'GET',
            url: url,
            async: false,
            data: params,
            success: function (r) {
                result = r;
            }
        });
        return result;
    },

    /**
     * 判断当前用户是否已登录
     * @returns {*}
     */
    isOnLogin: function () {
        return yuheUtils.get('/touristShop/checkLogin').isLogin;
    },

    /**
     * 在必须要登录的情况下前调用
     */
    checkLogin: function (callback) {
        if (!yuheUtils.isOnLogin()) {
            layer.open({
                type: 2,
                title: '',
                resize: true,
                fixed: true,
                shadeClose: true,
                shade: 0.1,
                scrollbar: false,
                area: ['650px', '400px'],
                content: '/touristShop/login',
                end: callback
            });
            return false;
        }
        return true;
    },
    getFormJson: function (formId) {
        var json = {};
        var sArr = $('#' + formId).serializeArray();
        sArr.forEach(function (item) {
            if (item.value) {
                if (json.hasOwnProperty(item.name)) {
                    json[item.name] += ',' + item.value;
                } else {
                    json[item.name] = item.value;
                }
            } else {
                if (json.hasOwnProperty(item.name)) {
                    json[item.name] += ',';
                } else {
                    json[item.name] = '';
                }
            }
        });
        return json;
    },
    /**
     * 创建文件上传实例(使用webUploader插件)
     * @param pickId 选择上传文件按钮ID
     * @param params 需要提交到服务器的参数
     * @param fileNum 限制上传文件个数
     * @param fileSizeLimit 限制单个上传图片的大小
     * @returns {*}
     */
    createWebUploader: function (pickId, params, fileNum, fileSizeLimit) {
        if (!pickId) {
            throw new Error('选择上传文件按钮ID不允许为空');
        }
        if (!params) {
            params = {};
        }
        if (!fileNum) {
            fileNum = 8;
        }
        if (!fileSizeLimit) {
            fileSizeLimit = 2048000;
        }
        var uploader = WebUploader.create({

            // swf文件路径
            swf: '/static/web-upload/Uploader.swf',

            auto: true, // 选完文件后，是否自动上传

            // 文件接收服务端。
            server: '/file/upload',

            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#' + pickId,

            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false,
            fileNumLimit: fileNum, //限制上传个数
            fileSingleSizeLimit: fileSizeLimit, //限制单个上传图片的大小
            formData: params    // 文件上传请求的参数表，每次发送都会发送此对象中的参数。
        });
        return uploader;
    },
    /**
     * 使用fileUpload插件创建
     * @param id
     * @param uploadUrl
     * @param allowFile
     * @param params
     * @param customParams
     * @returns {void|jQuery|HTMLElement}
     */
    initFileUploadControl: function(id, uploadUrl, allowFile, params, customParams) {
        var control = $('#' + id);
        if (!allowFile) {
            allowFile = ['jpg', 'png', 'jpeg']
        }
        if (!params) {
            params = {};
        }
        var allParams = {
            language: 'zh', // 设置语言
            uploadUrl: uploadUrl, // 上传的地址
            enctype: 'multipart/form-data',
            allowedFileExtensions: allowFile,// 接收的文件后缀
            showUpload: false, // 是否显示上传按钮
            showCaption: false,// 是否显示标题
            showRemove: false,
            browseClass: "btn btn-primary", // 按钮样式
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
            autoReplace: true,
            uploadAsync: true,
            //dropZoneEnabled: false,//是否显示拖拽区域
            //minImageWidth: 50, //图片的最小宽度
            //minImageHeight: 50,//图片的最小高度
            //maxImageWidth: 1000,//图片的最大宽度
            //maxImageHeight: 1000,//图片的最大高度
            maxFileSize: 10240, //单位为kb，如果为0表示不限制文件大小
            minFileCount: 1,
            maxFileCount: 1, //表示允许同时上传的最大文件个数
            validateInitialCount: true,
            msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
            uploadExtraData: function () {
                return params;
            }, // 向后台传递id作为额外参数，是后台可以根据id修改对应的图片地址。
            layoutTemplates: {
                // actionDelete:'', //去除上传预览的缩略图中的删除图标
                actionUpload: '',//去除上传预览缩略图中的上传图片；
                actionZoom: ''   //去除上传预览缩略图中的查看详情预览的缩略图标。
            }
        };
        if (customParams) {
            var keys = Object.keys(customParams);
            keys.forEach(function (key) {
                allParams[key] = customParams[key]
            })
        }
        control.fileinput(allParams);

        // $('.file-drop-zone-title').text("拖拽文件到这里");

        return control;
    },
    /**
     * 表格实例化 这个只是前端分页，没有整合服务端分页，若要服务端分页请参数 app_delegate.html里面
     * @param dom
     * @param params
     */
    initDataTable: function (dom, params) {
        $(dom).DataTable({
            pageLength: 10,
            responsive: true,
            dom: '<"html5buttons"B>f<"top">rt<"bottom"lp>i<"clear">',
            pagingType: "full_numbers",
            destroy: true,
            colReorder: true,
            language: CONSTANT.DATA_TABLES.DEFAULT_OPTION.language,
            autoWidth: false,   //禁用自动调整列宽
            stripeClasses: ["odd", "even"],//为奇偶行加上样式，兼容不支持CSS伪类的场合
            order: [],          //取消默认排序查询,否则复选框一列会出现小箭头
            processing: false,  //隐藏加载提示,自行处理
            ajax: {
                url: params.ajax.url,
                dataSrc: params.ajax.dataSrc,
                dataType: 'json',
                type: "get"
            },
            columns: params.columns,
            aoColumnDefs: params.aoColumnDefs,
            columnDefs:
                [
                    {orderable: false, targets: 0}
                ]
        });
    },
    /**
     * 选中左侧菜单
     * @param url 菜单url
     */
    leftMenuSelected : function (url) {
        var $thisA = $('a[href="' + url + '"]');
        $thisA.parent().attr("class", "active");
        $thisA.parent().parent().attr("class", "nav nav-second-level collapse in");
    },
    bindPersonalSetting: function () {
        $('#setting').on('click', function () {
            var isExpanded = $(this).attr('aria-expanded');
            var $drodownEl = $(this).parent('.profile-element');
            if (isExpanded === 'true') {
                $drodownEl.removeClass('open');
                $(this).attr('aria-expanded', false);
            } else {
                $drodownEl.addClass('open');
                $(this).attr('aria-expanded', true);
            }
        });
    },
    getUserName: function (userId) {
        var r = yuheUtils.get('/user/info/' + userId);
        return r.user['userName'];
    },
    /**
     * 获取url参数值
     * @param name
     * @returns {string|null}
     */
    getUrlParam: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    },
    renderlayTpl: function (tplId, data) {
        var html = '';
        layui.use('laytpl', function (laytpl) {
            html = laytpl($('#' + tplId).html()).render(data);
        });
        return html;
    }
};

/**
 * 绑定我的订单事件
 */
function bindGotoMyOrder() {
    console.log('binding event');
    $('#myOrder_1').on('click', function () {
        var isLogin = yuheUtils.checkLogin(function () {
            window.location.href = '/touristShop/orderList';
        });
        if (isLogin) {
            window.location.href = '/touristShop/orderList';
        }
    });
}

/**
 * 绑定加入购物车事件
 */
function bindAdd2Cart() {
    $('.add2cart').on('click', function () {

        var isLogin = yuheUtils.checkLogin(function () {
            // todo 这里写加入购物车逻辑
            var shopId = $(this).attr("id");
        });
        if (isLogin) {
            // todo 这里写加入购物车逻辑
            var shopId = $(this).attr("id");
        }
    });
}

function bindLogin() {
    $('#login_1').on('click', function () {
        var isLogin = yuheUtils.checkLogin(function () {
            window.location.href = '/touristShop/orderList';
        });

        if (isLogin) {
            alert("已登录，请不要重新登录");
        }
    });
}

function bindLoginOut() {
    $('#loginOut_1').on('click', function () {
        $.ajax({
            type: 'POST',
            url: 'touristShop/loginOut',
            success: function () {
                alert('已注销');
            }
        });
    });
}


//对Date 类进行扩展
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

/**
 * 初始化
 */
$(function () {
    bindGotoMyOrder();
    bindAdd2Cart();
    bindLogin();
    bindLoginOut();
});


/* 常量 */
var CONSTANT = {
    DATA_TABLES : {
        DEFAULT_OPTION : { //DataTables初始化选项
            language: {
                "sProcessing":   "处理中...",
                "sLengthMenu":   "每页 _MENU_ 项",
                "sZeroRecords":  "没有匹配结果",
                "sInfo":         "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
                "sInfoEmpty":    "当前显示第 0 至 0 项，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix":  "",
                "sSearch":       "搜索:",
                "sUrl":          "",
                "sEmptyTable":     "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands":  ",",
                "oPaginate": {
                    "sFirst":    "首页",
                    "sPrevious": "上页",
                    "sNext":     "下页",
                    "sLast":     "末页",
                    "sJump":     "跳转"
                },
                "oAria": {
                    "sSortAscending":  ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                },
                "buttons": {
                    "colvis": "列可见性",
                    "print": "打印",
                    "copy": "复制"
                }
            },
            dom: '<"html5buttons"B>f<"top">rt<"bottom"lp>i<"clear">',
            pagingType: "full_numbers",
            responsive: true,
            destroy: true,
            colReorder: true,
            autoWidth: false,   //禁用自动调整列宽
            stripeClasses: ["odd", "even"],//为奇偶行加上样式，兼容不支持CSS伪类的场合
            order: [],          //取消默认排序查询,否则复选框一列会出现小箭头
            processing: false,  //隐藏加载提示,自行处理
            serverSide: true,   //启用服务器端分页
            searching: true    //禁用原生搜索
        },
        COLUMN: {
            CHECKBOX: { //复选框单元格
                className: "td-checkbox",
                orderable: false,
                width: "30px",
                data: null,
                render: function (data, type, row, meta) {
                    return '<input type="checkbox" class="iCheck">';
                }
            }
        },
        RENDER: {   // 常用render可以抽取出来，如日期时间、头像等
            ELLIPSIS: function (data, type, row, meta) {
                data = data || "";
                return '<span title="' + data + '">' + data + '</span>';
            },
            DATE: function (data, type, row, meta) {
                data = data || "";
                return data.Format('yyyy-MM-dd');
            },
            DATE_TIME: function (data, type, row, meta) {
                data = data || "";
                return data.Format('yyyy-MM-dd hh:mm:ss');
            },
            USER_NAME: function (data, type, row, meta) {
                try {
                    data = data || "";
                    return yuheUtils.getUserName(data);
                } catch (e) {
                    return e.msg;
                }

            }
        }
    }
};
