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
        productQuantity: 1,
        menList: [],
        lists: [],
        shopDetails: {},
        view: {},
        stocknum: 0,
        stocktotal: 0,
        minpic: []
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
        _this.lists = _this.toTreeData(data, attributes)
        $.ajaxSettings.async = false;


    }
    ,
    methods: {

        pay: function () {
        },
        //把数据类型转成树格式
        // 购物车数量增加
        addCardNum: function (event, index) {
            this.productQuantity += 1;
            _this.addcar.cartNum++;


        }
        ,// 购物车数量减少
        delCardNum: function (event, index) {
            if (this.productQuantity > 0) {
                this.productQuantity -= 1;
            }
            if (_this.addcar.cartNum > 0) {
                _this.addcar.cartNum--;
            }
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
        showView: function (id) {//这个只是给基本方法   还没用上ajax请求
            _this = this;
            $.ajax({
                type: "POST",
                url: "/touristShop/viewList",
                data: {"id": id},

                success: function (r) {
                    console.info(r)
                    if (r.Status == 1) {

                        _this.view = r.shopList;
                        // _this.stocktotal = _this.view.num;
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