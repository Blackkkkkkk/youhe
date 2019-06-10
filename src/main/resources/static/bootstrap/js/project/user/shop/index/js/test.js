/**
 * Created by liumeixiang 2019/2/22.
 */

//新增vue组件化
Vue.component('tree', {
    name: 'treeNode',
    props: ['lists'],
    template: `
		
		<ul class="list-group margin-bottom-25 sidebar-menu">
				  <template v-for="item in lists">

                        <li v-if="item.children == null" class="list-group-item clearfix"><a :href="'/touristShop/commodityMenu?cid='+item.cid+''"><i
                                class="fa fa-angle-right"></i>
                            {{item.cname}}</a>
                        </li>
                        <li v-if="item.children != null && item.children.length >0"
                            class="list-group-item clearfix dropdown  ">
                            <a v-if="item.children != null && item.children.length >0"  style="padding: 0px;" :id="item.cid">
                            <i v-if="item.parentId == 0" class="fa fa-angle-right"></i>
                            <i v-if="item.parentId != 0" class="fa fa-circle" style="float: left;margin-right: 7px;font-size: 5px;position: relative;top: 7px;color: #949fae;"></i>
                            {{item.cname}}<i class="fa fa-angle-down"></i></a>
                            <ul class="dropdown-menu" v-if="item.children != null && item.children.length >0">
                                <treeNode :lists="item.children">                     
                                </treeNode>
                            </ul>
                        <li v-if="item.children != null && item.children.length ==0" class="list-group-item clearfix"><a
                                :href="'/touristShop/commodityMenu?cid='+item.cid+''" style="padding: 0px;" :id="item.cid">
                                <i v-if="item.parentId == 0" class="fa fa-angle-right"></i>
                                  <i v-if="item.parentId != 0" class="fa fa-circle" style="float: left;margin-right: 7px;font-size: 5px;position: relative;top: 7px;color: #949fae;"></i>{{item.cname}}</a></li>
                        </li>
                    </template>
		</ul>
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
    }, extends: searchVue,
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
        //   console.log(data)
        _this.lists = _this.toTreeData(data, attributes)
        //   console.log(_this.lists)
        $.ajaxSettings.async = false;

        //  console.log(_this.menList)

    }
    ,
    methods: {
        getShopList: function () {

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

    }
})

jQuery(document).ready(function () {
    vm.initcart();
})