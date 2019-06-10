/**
 * Created by liumeixiang 2019/2/22.
 */


var vm = new Vue({
    el: '#wrapper',
    data: {
        shopList: [],
        carData: {
            items: 0,
            prices: 0
        },
        menList: [],
        lists: [],
        shopVieList: [],
        shop: [],
        cname: "",
        /*
        shopPage: {
            pageNum: 1,
            page: 0,
            pageSize: 10,
        }*/

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



    }
    ,
    methods: {
        initPageLimit: function (data) {
            _this = this;
            $('#pageLimit').bootstrapPaginator({
                currentPage: data.pageNum,//当前的请求页面。
                totalPages: data.page,//一共多少页。
                size: "normal",//应该是页眉的大小。
                bootstrapMajorVersion: 3,//bootstrap的版本要求。
                alignment: "right",
                numberOfPages: data.pageSize,//一页列出多少数据。
                itemTexts: function (type, page, current) {//如下的代码是将页眉显示的中文显示我们自定义的中文。
                    switch (type) {
                        case "first":
                            return "首页";
                        case "prev":
                            return "上一页";
                        case "next":
                            return "下一页";
                        case "last":
                            return "末页";
                        case "page":
                            return page;
                    }
                },
                onPageClicked: function (event, originalEvent, type, page) {//给每个页眉绑定一个事件，其实就是ajax请求，其中page变量为当前点击的页上的数字。
                    _this.shopPage.pageNum = page;
                    console.log(_this.shopPage)
                    $.ajax({
                        url: '/touristShop/commodityMenuData',
                        type: 'POST',
                        data: {
                            "cid": _this.shopPage.cid,
                            "pageNum": _this.shopPage.pageNum,
                            "pageSize": _this.shopPage.pageSize,
                            "page": _this.shopPage.page
                        },
                        contentType: "application/x-www-form-urlencoded",
                        dataType: "json",
                        success: function (callback) {
                            console.log(callback)
                            _this.shopVieList = callback.list.list
                            _this.shopPage = callback.shop
                            _this.cname = callback.cname
                            _this.initPageLimit(_this.shopPage);
                        }
                    })
                }
            });

        },
        getShopList: function () {

        },
        showView: function (id) {
            window.open('/shopDetail/detail?id=' + id)
        },


    }
})

jQuery(document).ready(function () {
    vm.initcart();
})