/**
 * Created by xiaoqiang on 2019/1/10.
 */

var vm = new Vue({
    el: '#wrapper',
    data: {
        shop: {
            id: null,
            pirce: 0,
            num: 0,
            pictureList: {},
            name: null,
            shopExplainList: [],
            sukList: [],
            sukChangeName: null,
            shopStyleId: null,  // 选中商品下的类型id
            cartNum: 1  // 购物车数量
        },
        lists: {},
        view: {},
        shopList: [],
        carData: {
            items: 0,
            prices: 0
        },
        // view 详情的
        productQuantity: 1, //详情页商品数量字段
        stocknum: 0,
        stocktotal: 0,
        minpic: []
    }, extends: vmSon,// 接收对象和函数
    created: function () {
    }
    ,
    mounted: function () {
    }
    ,
    methods: {
        changePrice: function (price, name, changeId) {

            $('#product div ').each(function (i) {
                    $(this).css({'border': ''});
                }
            )
            $("#" + changeId).css("border", "1px solid #e03232");

            this.shop.pirce = price
            this.shop.sukChangeName = name
            this.shop.shopStyleId = changeId
        },
        // 购物车数量增加
        addCardNum:
            function (event, index) {
                this.shop.cartNum += 1;
            }

        ,// 购物车数量减少
        delCardNum: function (event, index) {
            if (this.shop.cartNum > 0) {
                this.shop.cartNum -= 1;
            }
        }
        ,
        //立即购买
        purchase: function () {
            id = vm.shop.id;
            var cartNum = $("input[name='text']").val();
            // id=vm.id;

            var url = "/order/shoppingPurchase?id=" + id + "&cartNum=" + cartNum;

            console.log(url)
            if (id && id != 'null') {
                window.location.href = "/order/shoppingPurchase?id=" + id + "&cartNum=" + cartNum;
            }
        }
        ,
        addCar: function (event, type) {

            x = event.clientX  // 获取点击对象的x 轴
            y = event.clientY  // 获取点击对象的y 轴


            var url;
            console.log(vm.shop.shopStyleId == null)
            if (vm.shop.shopStyleId == null) {
                url = "/touristShop/addCart?id=" + vm.shop.id;
            } else {
                url = "/touristShop/addCart?id=" + vm.shop.id + "&shopStyleId=" + vm.shop.shopStyleId + "&pirce=" + vm.shop.pirce + "&sukChangeName="
                    + vm.shop.sukChangeName + "&cartNum=" + vm.shop.cartNum;
            }

            $.get(url, function (r) {
                _this.initcart(r.shopList);
                if (r.Status == 1) {
                    top.layer.msg('加入购物车成功', {icon: 1, time: 1000, offset: [y + 'px', x + 'px']});
                } else if (r.Status == 2) {
                    layer.msg('购物车已存在，数量自动加1', {icon: 1, time: 1000, offset: [y + 'px', x + 'px']});
                } else if (r.Status == 3) {
                    layer.msg('未登录，请登录后再添加到购物车', {icon: 2, time: 1000, offset: [y + 'px', x + 'px']});
                } else if (r.Status == 4) {
                    layer.msg('添加到购物车失败，请联系管理员', {icon: 2, time: 1000, offset: [y + 'px', x + 'px']});
                }
            })
        }
        ,

        initcart: function (r) {
            var _this = this;
            if (r == null || r == undefined || r == '') {
                $.ajax({
                    type: "POST",
                    url: "/touristShop/initCart",
                    dataType: "json",
                    async: false,//取消异步
                    success: function (data) {
                        r = data;
                        console.log(r)
                    }
                });
            }
            var num = 0;
            var prices = 0;
            _this.shopList = [];
            for (var index in r) {
                num++;
                //  num +=  r.shopList[index].pirce
                prices += r[index].pirce * r[index].cartNum;
                _this.shopList.push(r[index])
            }
            _this.carData.items = num;
            _this.carData.prices = prices;
        }
        ,
        delCart: function (event, item) {


            var x = event.clientX;
            var y = event.clientY;

            var el = event.currentTarget;
            var _this = this;


            for (var i = 0; i < _this.shopList.length; i++) {

                if (item.shopId != null && _this.shopList[i].shopId == item.shopId) {

                    console.log(_this.shopList[i].id + ":" + _this.shopList[i].shopId)
                    console.log(item.id + ":" + item.shopId)


                    $.get("/touristShop/delCart?id=" + item.id + "&shopId=" + _this.shopList[i].shopId, function (r) {
                        if (r == 1) {
                            top.layer.msg('物品删除成功', {icon: 1, time: 1000, offset: [y + 'px', x + 'px']});
                            _this.initcart();
                        } else {
                            top.layer.msg('物品删除失败，请重新删除', {icon: 2, time: 1000, offset: [y + 'px', x + 'px']});
                        }
                    })
                }
            }
        }
        ,
        showView: function (id) {//这个只是给基本方法   还没用上ajax请求

            _this = this;
            $.ajax({
                type: "POST",
                url: "/touristShop/viewList",
                data: {"id": id},

                success: function (r) {
                    if (r.Status == 1) {

                        _this.view = r.shopList;
                        _this.stocktotal = _this.view.num;
                        _this.minpic = _this.view.thumbnail.split(',');
                        console.log(_this.view);
                    }
                }
            })
            layer.open({
                title: false,
                type: 1,
                area: ['747px', '580px'],
                content: $('#product-pop-up'),//这可以写弹出框的html内容
            });
        }
        ,

    }
})
jQuery(document).ready(function () {
    vm.initcart();
})