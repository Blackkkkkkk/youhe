var vm = new Vue({
    el: '#wrapper',
    data: {
        shopList: [],
        carData: {
            items: 0,
            prices: 0,
        },
        cartPrices: {
            shipping: 0, //运费

            allPrice: 0 // 总费用
        },
        menList: [],
        lists: []
    }, extends: searchVue,
    created: function () {

    },
    mounted: function () {


        this.initcart();

        _this = this;
        $.ajaxSettings.async = false;

        $.get("/commodity/list", function (r) {
            _this.menList = r;
        })


        let data = _this.menList;
        // 属性配置信息
        let attributes = {
            cid: 'cid',
            parentId: 'parentId',
            cname: 'cname',
            rootId: 0
        }
        _this.lists = _this.toTreeData(data, attributes)
        $.ajaxSettings.async = false;


    },
    methods: {
        pay: function () {
            // price =vm.showList.bigOrderCode;
            debugger;

            // $.get("/activiti/deploy?modelId=" + id, function (data,status) {
            $.get("/order/payment", function (r) {
                if (r.Status == 0) {
                    window.location.href = (r.url)
                } else {
                    layer.msg(r.msg, {icon: 2, time: 500});
                }
            })
        },
        //把数据类型转成树格式
        toTreeData: function (data, attributes) {
            let resData = data;
            let tree = [];

            for (let i = 0; i < resData.length; i++) {
                if (resData[i].parentId === attributes.rootId) {
                    let obj = {
                        cid: resData[i][attributes.cid],
                        cname: resData[i][attributes.cname],
                        parentId: resData[i][attributes.parentId],
                        children: []
                    };
                    tree.push(obj);
                    resData.splice(i, 1);
                    i--;
                }
            }
            run(tree);

            function run(chiArr) {
                if (resData.length !== 0) {
                    for (let i = 0; i < chiArr.length; i++) {
                        for (let j = 0; j < resData.length; j++) {
                            if (chiArr[i].cid == resData[j][attributes.parentId]) {
                                let obj = {
                                    cid: resData[j][attributes.cid],
                                    cname: resData[j][attributes.cname],
                                    parentId: resData[j][attributes.parentId],
                                    children: []
                                };
                                chiArr[i].children.push(obj);
                                resData.splice(j, 1);
                                j--;
                            }
                        }
                        run(chiArr[i].children);
                    }
                }
            }

            return tree;
        }
        ,
        cartView: function () {

        }
        ,
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
        }
        ,
        delCart: function (event, item) {

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
        ,   // 购物车数量增加
        addCardNum: function (event, index) {

            var x = event.clientX;
            var y = event.clientY;

            _this = this;

            $.get("/touristShop/carNumUD?id=" + this.shopList[index].id + "&carNumUD=1", function (r) {
                if (r.Status == 1) {
                    top.layer.msg(r.msg, {icon: 1, time: 1000, offset: [y + 'px', x + 'px']}, function () {
                        _this.initcart(r.shopList)
                    });
                }
            })

        }
        ,// 购物车数量减少
        delCardNum: function (event, index) {
            var x = event.clientX;
            var y = event.clientY;

            _this = this;

            if (this.shopList[index].cartNum != 0) {
                $.get("/touristShop/carNumUD?id=" + this.shopList[index].id + "&carNumUD=-1", function (r) {
                    if (r.Status == 1) {
                        top.layer.msg(r.msg, {icon: 1, time: 400, offset: [y + 'px', x + 'px']}, function () {
                            _this.initcart(r.shopList)
                        });
                    }
                })
            }
        }
        ,
        goShop: function () {
            window.location.href = ('/touristShop/index')
        }
    }
})

jQuery(document).ready(function () {

    //  vm.initcart();

})