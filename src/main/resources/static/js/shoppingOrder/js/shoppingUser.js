/**
 * Created by xiaoqiang on 2019/1/10.
 */

//新增vue组件化
Vue.component('tree', {
    name: 'treeNode',
    props: ['lists'],
    template: `
		<div>
				  <template v-for="item in lists">

                        <li v-if="item.children == null" class="list-group-item clearfix"><a :href="'/touristShop/commodityMenu?cid='+item.cid+''"><i
                                class="fa fa-angle-right"></i>
                            {{item.cname}}</a>
                        </li>
                        <li v-if="item.children != null && item.children.length >0"
                            class="list-group-item clearfix dropdown">
                            <a v-if="item.children != null && item.children.length >0" href="javascript:void(0);" style="padding: 0px;" :id="item.cid"> 
                            <i v-if="item.parentId == 0" class="fa fa-angle-right"></i>
                            <i v-if="item.parentId != 0" class="fa fa-circle" style="float: left;margin-right: 7px;font-size: 5px;position: relative;top: 7px;color: #949fae;"></i>
                            {{item.cname}}<i class="fa fa-angle-down"></i></a>
                            <ul class="dropdown-menu" v-if="item.children != null && item.children.length >0">
                                <treeNode :lists="item.children">                     
                                </treeNode>
                            </ul>

                        <li v-if="item.children != null && item.children.length ==0" class="list-group-item clearfix">
                                <a :href="'/touristShop/commodityMenu?cid='+item.cid+''" style="padding: 0px;" :id="item.cid">
                                <i v-if="item.parentId == 0" class="fa fa-angle-right"></i>
                                  <i v-if="item.parentId != 0" class="fa fa-circle" style="float: left;margin-right: 7px;font-size: 5px;position: relative;top: 7px;color: #949fae;"></i>{{item.cname}}</a></li>
                        </li>
                
                  </template>
		</div>
	`
})
Vue.component('treeHeadTwo', {
    name: 'treeNode',
    props: ['lists'],
    template: `
		<ul class="dropdown-menu">
			<template v-for="(item,index) in lists">
			     <li v-if="item.children == null"><a href="/touristShop/commodityMenu?cid={{item.cid}}"><i
                            ></i>
                        {{item.cname}}</a>
                 </li>
                    <li v-if="item.children != null && item.children.length >0"
                        class="dropdown-submenu">
                        <a v-if="item.children != null && item.children.length >0" href="javascript:void(0);"
                            :id="item.cid">
                            {{item.cname}}11<i class="fa fa-angle-right"></i></a>
                      
                            <treeNode :lists="item.children">
                            </treeNode>
                       
                    <li v-if="item.children != null && item.children.length ==0" >
                        <a :href="'/touristShop/commodityMenu?cid='+item.cid+''"  :id="item.cid">
                        {{item.cname}}22</a>
                    </li>

            </template>
		</ul>
	`
})

Vue.component('treeHead', {
    name: 'treeNode',
    props: ['lists'],
    template: `
		<div>
			<template v-for="(item,index) in lists">
			<div class="nav-content-col">
                <h3>{{item.cname}}</h3>
                   <ul >
                       <li v-for="(item1,index) in item.children"><a href="product-list.html">{{item1.cname}}</a></li>
                   </ul>  
            </div>
            </template>
		</div>
	`
})


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
        user: {},
        password: {
            oldpassword: null,
            password: null,
            newPassword: null,
        },
        order: {
            id: null,
            addressee: null,
            phone: null,
            deliveryAddr: null
        },
        addressList: {}
    }, extends: searchVue,
    created: function () {
        this.initUser();
        this.addList();
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

        //编辑个人信息
        edit: function (id) {
            _this = this;
            for (var i = 0; i < _this.addressList.length; i++) {
                if (id == _this.addressList[i].id) {
                    _this.order.id = _this.addressList[i].id
                    _this.order.addressee = _this.addressList[i].addressee
                    _this.order.deliveryAddr = _this.addressList[i].deliveryAddr
                    _this.order.phone = _this.addressList[i].phone

                }
            }

        },
        //获取地址列表
        addList: function () {
            _this = this;
            $.get("/touristShop/addressList", function (r) {
                _this.addressList = r.list
            })
        },
        //新增收货地址
        saveAddress: function () {

            _this = this;
            var url;
            if (_this.order.id == null) {
                if (_this.addressList.length > 4) {
                    layer.msg('个人地址只能保存5条', {icon: 1, time: 1000})
                    return false;

                }
                url = "/touristShop/addressSave";
            } else {
                url = "/touristShop/addressUpdate";
            }

            $.ajax({
                type: "POST",
                url: url,
                data: _this.order,
                success: function (r) {
                    if (r.Status == 0) {
                        layer.msg(r.msg, {icon: 1, time: 1000}, function () {
                            _this.addressList = r.list;
                            _this.order.id = null
                            _this.order.addressee = null
                            _this.order.deliveryAddr = null
                            _this.order.phone = null

                        });
                    } else {
                        layer.msg(r.msg, {icon: 2, time: 1000});
                    }
                }
            })


        },
        //更新个人信息
        update: function () {
            _this = this;
            $.ajax({
                type: "POST",
                url: "/touristShop/updateUserInfo",
                data: {
                    "uid": _this.user.uid,
                    "userName": _this.user.userName,
                    "phone": _this.user.phone,
                    "email": _this.user.email
                },
                success: function (r) {
                    console.log(r)
                    if (r.Status == 0) {
                        layer.msg(r.msg, {icon: 1, time: 1000}, function () {
                            _this.user = r.user;
                        });
                    } else {
                        layer.msg(r.msg, {icon: 2, time: 1000});
                    }
                }
            })
        },
        //修改密码
        updatePassword: function () {
            $.ajax({
                type: "POST",
                url: "/touristShop/updateUserPassword",
                data: {
                    "uid": _this.user.uid,
                    "userPassword": _this.password.password,
                    "newPassword": _this.password.newPassword
                },
                success: function (r) {
                    console.log(r)
                    if (r.Status == 0) {
                        layer.msg('修改成功', {icon: 1, time: 1000}, function () {
                            _this.user = r.user;
                        });
                    } else {
                        layer.msg(r.msg, {icon: 2, time: 1000});
                    }
                }
            })
        },
        initUser: function () {
            _this = this;
            $.get("/touristShop/userInfo", function (r) {
                _this.user = r.user;
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