var layer;

$(document).ready(function () {
    layui.use("layer", function () {
        layer = layui.layer;  //layer初始化

    })
});


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
                            <a v-if="item.children != null && item.children.length >0"  style="padding: 0px;" :id="item.cid" :href="'/touristShop/commodityMenu?cid='+item.cid+''">
                            <i v-if="item.parentId == 0" class="fa fa-angle-right" ></i>
                            <i v-if="item.parentId != 0" class="fa fa-circle" style="float: left;margin-right: 7px;font-size: 5px;position: relative;top: 7px;color: #949fae;"></i>
                            {{item.cname}}</a><a><i class="fa fa-angle-down"></i></a>
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
                <h3 :id="item.cid" ><a :href="'/touristShop/commodityMenu?cid='+item.cid">{{item.cname}}</a></h3>
                   <ul >
                       <li v-for="(item1,index) in item.children"><a :href="'/touristShop/commodityMenu?cid='+item1.cid">{{item1.cname}}</a></li>
                   </ul>  
            </div>
            </template>
		</div>
	`
})


var searchVue = {
    data: {
        searcnName: ""
    },
    created() {
        console.log("1212")
    },
    methods: { //把数据类型转成树格式
        toTreeData: function (data, attributes) {
            console.log(data)
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
        },
        searchShop: function () {

            window.location.href = "/touristShop/commodityMenuSearch?searcnName=" + this.searcnName
        },
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


        }, addCar: function (event, type) {

            _this = this;
            //获取点击对象
            x = event.clientX  // 获取点击对象的x 轴
            y = event.clientY  // 获取点击对象的y 轴
            console.log(event)
            var _this = this;
            var el = event.currentTarget;


            var url;

            url = "/touristShop/addCart?id=" + el.id;

            console.log(url)

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
    }
}
