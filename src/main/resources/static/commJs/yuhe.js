/**
 * js通用工具
 * @type {{isOnLogin: (function(): boolean), checkLogin: yuheUtils.checkLogin}}
 */
var yuheUtils = {
    /**
     * 判断当前用户是否已登录
     * @returns {*}
     */
    isOnLogin: function () {
        var url = '/touristShop/checkLogin';
        var isLogin = false;
        $.ajax({
            type: 'GET',
            url: url,
            async: false,
            success: function (r) {
                isLogin = r.isLogin;
            }
        });
        return isLogin;
    },
    /**
     * 在必须要登录的情况下前调用
     */
    checkLogin: function (callback) {
        if (!yuheUtils.isOnLogin()) {
            layer.open({
                type: 2,
                title: '',
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

/**
 * 初始化
 */
$(function () {
    bindGotoMyOrder();
    bindAdd2Cart();
});