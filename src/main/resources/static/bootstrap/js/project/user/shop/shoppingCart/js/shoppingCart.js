/**
 * Created by xiaoqiang on 2019/1/10.
 */
var vm = new Vue({
    el: '#wrapper',
    data: {
        shopList: [],
        carData: {
            items: 0,
            prices: 0
        },
        cartPrices: {
            shipping: 0, //运费

            allPrice: 0 // 总费用
        }
    },
    mounted: function () {
        this.initcart();
        // this. test();

        /*
        this.$nextTick(function () {
           // console.log(this.$refs.input1[0])
        })*/
    },
    methods: {
        cartView: function () {

        },
        addCar: function (event) {

            //获取点击对象
            x = event.clientX  // 获取点击对象的x 轴
            y = event.clientY  // 获取点击对象的y 轴
            var _this = this;
            var el = event.currentTarget;
            $.get("/touristShop/addCart?id=" + el.id, function (r) {
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
        },
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
                    }
                });
            }
            var num = 0;
            var prices = 0;   // 购物车显示的价格

            _this.shopList = [];
            for (var index in r) {
                num++;
                //  num +=  r.shopList[index].pirce
                prices += r[index].pirce * r[index].cartNum;
                _this.shopList.push(r[index])
            }
            _this.carData.items = num;
            _this.carData.prices = prices;
            _this.cartPrices.allPrice = prices + _this.cartPrices.shipping
        },
        delCart: function (event) {

            var x = event.clientX;
            var y = event.clientY;

            var el = event.currentTarget;
            var _this = this;

            $.get("/touristShop/delCart?id=" + el.name, function (r) {
                if (r == 1) {
                    top.layer.msg('物品删除成功', {icon: 1, time: 1000, offset: [y + 'px', x + 'px']});
                    _this.initcart();
                } else {
                    top.layer.msg('物品删除失败，请重新删除', {icon: 2, time: 1000, offset: [y + 'px', x + 'px']});
                }
            })
        },   // 购物车数量增加
        addCard: function (index) {

            $.get("/touristShop/delCart?id=" + this.shopList[index].id + "&carNumUD=1", function (r) {

            })

            this.shopList[index].cartNum = this.shopList[index].cartNum + 1;
            this.initcart(this.shopList)
        },// 购物车数量减少
        delCard: function (index) {
            if (this.shopList[index].cartNum != 0) {
                this.shopList[index].cartNum = this.shopList[index].cartNum - 1;
                this.initcart(this.shopList)
            }
        }
    }
})

jQuery(document).ready(function () {
    //  vm.initcart();

})