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

                        <li v-if="item.children == null" class="list-group-item clearfix"><a href="/touristShop/commodityMenu?cid={{item.cid}}"><i
                                class="fa fa-angle-right"></i>
                            {{item.cname}}</a>
                        </li>
                        <li v-if="item.children != null && item.children.length >0"
                            class="list-group-item clearfix dropdown">
                            <a v-if="item.children != null && item.children.length >0" href="javascript:void(0);" style="padding: 0px;" :id="item.cid"> 
                            <i v-if="item.parentId == 0" class="fa fa-angle-right"></i>
                            <i v-if="item.parentId != 0" class="fa fa-circle" style="float: left;margin-right: 7px;font-size: 5px;position: relative;top: 7px;color: #949fae;"></i>
                            {{item.cname}}33333<i class="fa fa-angle-down"></i></a>
                            <ul class="dropdown-menu" v-if="item.children != null && item.children.length >0">
                                <treeNode :lists="item.children">                     
                                </treeNode>
                            </ul>

                        <li v-if="item.children != null && item.children.length ==0" class="list-group-item clearfix">
                                <a :href="'/touristShop/commodityMenu?cid='+item.cid+''" style="padding: 0px;" :id="item.cid">
                                <i v-if="item.parentId == 0" class="fa fa-angle-right"></i>
                                  <i v-if="item.parentId != 0" class="fa fa-circle" style="float: left;margin-right: 7px;font-size: 5px;position: relative;top: 7px;color: #949fae;"></i>{{item.cname}}1111</a></li>
                        </li>
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
        menList: [],
        lists: []
    },
    created: function () {

    }
    ,
    mounted: function () {

        _this = this;
        $.ajaxSettings.async = false;

        $.get("/commodity/list", function (r) {


            _this.menList = r
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
        console.log(_this.lists)
        $.ajaxSettings.async = false;

        console.log(_this.menList)

    }
    ,
    methods: {

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
        }
    }
})

jQuery(document).ready(function () {
    vm.initcart();
})