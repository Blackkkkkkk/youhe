/**
 * Created by xiaoqiang on 2019/1/10.
 */

//新增vue组件化

var vm = new Vue({
    el: '#wrapper',
    data: {
        shopList: [],
        carData: {
            items: 0,
            prices: 0
        },
        addcar: {
            cartNum: 0,
            remark: "",
        },  // view 详情的
        productQuantity: 1,
        menList: [],
        lists: [],
        shopDetails: {},
        view: {},
        stocknum: 0,
        stocktotal: 0,
        minpic: []
    },
    created: function () {

    }
    ,
    mounted: function () {

    }
    ,
    methods: {

        detail: function () {
            console.log("1")
        },
        // 购物车数量增加
        addCardNum: function (event, index) {
            this.productQuantity += 1;
            _this.addcar.cartNum++;


        }

        ,
        //立即购买
        purchase: function () {
            id = vm.view.id;
            var cartNum = $("input[name='text']").val();
            // id=vm.id;
            debugger
            if (id && id != 'null') {
                window.location.href = "/order/shoppingPurchase?id=" + id + "&cartNum=" + cartNum;
            }
            // url = "/touristShop/addCart?id=" + el.id + "&remark=" + _this.addcar.remark + "&cartNum=" + _this.addcar.cartNum;
        }
        ,

        initcart: function (r) {
            console.log(r)
            var _this = this;
            if (r == null || r == undefined || r == '') {
                $.ajax({
                    type: "POST",
                    url: "/touristShop/initCart",
                    dataType: "json",
                    async: false,//取消异步
                    success: function (data) {
                        r = data;
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
        },
        delCart: function (event,item) {

            var x = event.clientX;
            var y = event.clientY;

            var el = event.currentTarget;
            var _this = this;

            for (var i = 0; i < _this.shopList.length; i++) {
                if (item.shopId != null && _this.shopList[i].shopId == item.shopId) {
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
    }
})

jQuery(document).ready(function () {
    vm.initcart();
})