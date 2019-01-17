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
        }
    },
    methods: {
        addCar: function (msg, event) {
            //获取点击对象
            var _this = this;
            var el = event.currentTarget;
            $.get("/touristShop/addCart?id=" + el.id, function (r) {
                var num = 0;
                var prices = 0;
                for (var index in r.shopList) {
                    num++;
                    //  num +=  r.shopList[index].pirce
                    prices += r.shopList[index].pirce * r.shopList[index].cartNum;
                    _this.shopList.push(r.shopList[index])
                }
                _this.carData.items = num;
                _this.carData.prices = prices;
                console.log(_this.shopList)

                if (r.Status == 1) {

                    layer.msg('加入购物车成功', {icon: 1, time: 1000});
                } else if (r.Status == 2) {

                    layer.msg('购物车已存在，数量自动加1', {icon: 1, time: 1000});

                } else if (r.Status == 3) {
                    layer.msg('未登录，请登录后再添加到购物车', {icon: 2, time: 1000});
                } else if (r.Status == 4) {
                    layer.msg('添加到购物车失败，请联系管理员', {icon: 2, time: 1000});
                }

            })


        }
    }
})