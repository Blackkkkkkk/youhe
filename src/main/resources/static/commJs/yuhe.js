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
        var result;
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
     * 创建文件上传实例
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

/**
 * 初始化
 */
$(function () {
    bindGotoMyOrder();
    bindAdd2Cart();
    bindLogin();
    bindLoginOut();

});

