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
        addcar: {
            cartNum: 0,
            remark: "",
        },  // view 详情的
        menList: [],
        lists: [],
        shopDetails: {},
        view: {},
        stocknum: 0,
        stocktotal: 0,
        minpic: [],
        subtotal: 0,
        freight: 0,
        total: 0,
        deliveryAddr: "",
        addressee: "",
        phone: "",
        actionType: 1,
        name: "",
        fileName: "",
        id: null

    }, extends: searchVue,
    created: function () {

    }
    ,
    mounted: function () {

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
        console.log(data)
        _this.lists = _this.toTreeData(data, attributes)
        $.ajaxSettings.async = false;


    }
    ,
    methods: {

        update: function () {


            $.ajax({
                type: "POST",
                url: "/order/updateOrder",
                data: {
                    "id": _this.id,
                    "deliveryAddr": _this.deliveryAddr,
                    "addressee": _this.addressee,
                    "phone": _this.phone
                },
                dataType: "json",
                success: function (r) {
                    if (r.Status === 0) {
                        layer.msg(r.msg, {icon: 1, time: 1000}, function () {
                            window.reload.href = '/order/myOrder';
                        });

                    } else {
                        layer.msg(r.msg, {icon: 2, time: 1000});
                    }
                }
            });

        },
        re: function () {
            console.log("1")
            window.location.href = "/order/myOrder";
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
        ,   // 购物车数量增加
        addCardNum: function (shopId, id) {
            _this = this;
            var num = 0;

            for (var i = 0; i < _this.shopList.length; i++) {
                if (_this.shopList[i].shopId == shopId) {
                    _this.shopList[i].cartNum += 1;
                    _this.shopList[i].amount = parseInt(_this.shopList[i].cartNum) * parseInt(_this.shopList[i].pirce)
                    console.log("/touristShop/addCartNum?id=" + id + "&shopId=" + shopId)
                    $.get("/touristShop/addCartNum?id=" + id + "&shopStyleId=" + shopId + "&carNumUD=1", function (r) {
                        _this.initcart(r.shopList);
                        if (r.Status == 1) {
                            top.layer.msg('数量添加成功！', {icon: 1, time: 1000, offset: [y + 'px', x + 'px']});
                        } else {
                        }
                        top.layer.msg('添加失败，请联系管理员！', {icon: 1, time: 1000, offset: [y + 'px', x + 'px']});
                    })

                }
                num += _this.shopList[i].amount;
            }
            _this.subtotal = num;
            _this.total = num + _this.freight;
            _this.carData.prices = _this.total


        }
        ,// 购物车数量减少
        delCardNum: function (shopId, id) {
            _this = this;
            var num = 0;
            for (var i = 0; i < _this.shopList.length; i++) {
                if (_this.shopList[i].shopId == shopId) {
                    console.log(shopId)
                    if (_this.shopList[i].cartNum > 0) {
                        _this.shopList[i].cartNum -= 1;
                        _this.shopList[i].amount = parseInt(_this.shopList[i].cartNum) * parseInt(_this.shopList[i].pirce)
                        $.get("/touristShop/addCartNum?id=" + id + "&shopStyleId=" + shopId + "&carNumUD=-1", function (r) {
                            _this.initcart(r.shopList);
                            if (r.Status == 1) {
                                top.layer.msg('数量添加成功！', {icon: 1, time: 1000, offset: [y + 'px', x + 'px']});
                            } else {
                                top.layer.msg('添加失败，请联系管理员！', {icon: 1, time: 1000, offset: [y + 'px', x + 'px']});
                            }

                        })

                    }
                }
                num += _this.shopList[i].amount;
            }
            _this.subtotal = num;
            _this.total = num + _this.freight;
            _this.carData.prices = _this.total
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
        addCar: function (event, type) {

            _this = this;
            //获取点击对象
            x = event.clientX  // 获取点击对象的x 轴
            y = event.clientY  // 获取点击对象的y 轴
            console.log(event)
            var _this = this;
            var el = event.currentTarget;


            //type == 2  是view 那边添加
            var url = "/touristShop/addCart?id=" + el.id;

            if (type == 2) {
                url = "/touristShop/addCart?id=" + el.id + "&remark=" + _this.addcar.remark + "&cartNum=" + _this.addcar.cartNum;
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
            _this.subtotal = prices;
            _this.total = prices + _this.freight;


            console.log(_this.shopList)
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
            /*
            if (e.stopPropagation)
                e.stopPropagation();
            else
                window.event.cancelBubble = true;*/
        },
    }
})

jQuery(document).ready(function () {
    vm.initcart();
})