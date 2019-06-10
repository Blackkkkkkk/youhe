var wageNowTable;

var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "cid",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl",
            name: "cname"
        }
    }
};
var vm = new Vue({
        el: '#wrapper',
        data: {
            showList: true,
            title: "新增商品",
            dept: {
                parentName: null,
                parentId: 0,
                orderNum: 0,
                cid: null,
                cname: null
            },
            permission: {
                pid: null,
                pname: null,
                type: null,
                url: null,
                parentid: null,
                parentids: null,
                sortstring: null
            },
            shop: {
                id: null,
                name: null,
                pirce: null,
                num: null,
                detail_picture: null,
                thumbnail: null,
                top: null,
                status: null,
                register: null,
                orderNum: null,
                hotSale: null,
                isNewProductOrderNum: null,
                cid: '',
                shopExplain: null,
                shopExplainList: [],
                specification: null,
                specificationList: [],
                sukParameter: null,
                sukParameterList: []
            }
        },
        extends: vueSon// 接收对象和函数
        ,
        methods: {
            add: function () {
                vm.showList = false;
                vm.shop = {
                    id: null,
                    name: null,
                    pirce: null,
                    num: null,
                    detail_picture: null,
                    thumbnail: null,
                    top: 1,
                    status: 1,
                    register: null,
                    orderNum: 1,
                    hotSale: 1,
                    isNewProductOrderNum: 1,
                    cid: '',
                    cname: "",
                    shopExplain: null,
                    shopExplainList: [],
                    specification: null,
                    specificationList: [],
                    sukParameter: null,
                    sukParameterList: []
                };
                vm.dept = {parentName: null, parentId: 0, orderNum: 0, cid: null, cname: null};

                vm.getDept();


                $("#reportFile").fileinput('destroy'); // 先销毁在初始化上传框
                //初始化fileinput
                var oFileInput = new FileInput();

                //参数1:控件id、参数2:上传地址
                oFileInput.Init("reportFile", "/shop/uploadReport", '', '');

            },
            reload: function () {
                vm.showList = true;

                if (typeof (wageNowTable) == "undefined") {
                    wageNowTable = $('.dataTables-example').DataTable();
                    wageNowTable.ajax.reload();//初始化
                } else {
                    $('.dataTables-example').dataTable().fnDraw(false);  //不会跳转到第一页，保留页面的页码和显示条数

                }
            },
            save: function () {
                var bootstrapValidator = $("#myForm").data('bootstrapValidator');
                bootstrapValidator.validate();
                if (bootstrapValidator.isValid()) {
                    var picturename = "";//获取上传的文件的后缀名，如果不是jpg,或者png的话不出发上传，弹出提示，表单里面的其他内容也不上传。
                    // picturename = $("#reportFile").val().substring($("#reportFile").val().indexOf('.'), $("#reportFile").val().length).toUpperCase(); //商品轮播图
                    //  introducePicturename = $("#introduceReportFile").val().substring($("#introduceReportFile").val().indexOf('.'), $("#introduceReportFile").val().length).toUpperCase(); //商品介绍图片


                    /*当上传的文件的格式是.png .jpg .PNG .JPG时 先将表单内的除图片以外的东西提交到后天，然后在触发插件，将图片上传，保存。
                     */

                    var url = vm.shop.id == null ? "/shop/save" : "/shop/update";


                    $.ajax({
                        type: 'post',
                        url: url,
                        data: $("#myForm").serialize(),
                        success: function (data) {

                            vm.id = data.id;
                            //不上传图片时，不触发bootstrap 上传插件的初始化方法。仅将表单里面的（除图片以外的）内容提交，
                            if ($("#reportFile").val() != "" || $("#introduceReportFile").val() != "" || $("#sukreportFile").val() != "") {

                                if ($("#reportFile").val() != "") {
                                    $('#reportFile').fileinput('upload'); //触发插件开始上传。

                                } else if ($("#introduceReportFile").val() != "") {
                                    $('#introduceReportFile').fileinput('upload'); //触发插件开始上传。

                                } else if ($("#sukreportFile").val() != "") {
                                    $('#sukreportFile').fileinput('upload'); //触发插件开始上传。
                                } else {
                                    layer.msg('操作成功', {icon: 1, time: 1000}, function () {
                                        $("#myForm").bootstrapValidator('resetForm');
                                        var table = $('.dataTables-example').DataTable();
                                        table.ajax.reload();
                                        vm.reload();
                                    });
                                }
                            } else {
                                layer.msg('操作成功', {icon: 1, time: 1000}, function () {
                                    $("#myForm").bootstrapValidator('resetForm');
                                    var table = $('.dataTables-example').DataTable();
                                    table.ajax.reload();
                                    vm.reload();
                                });
                            }

                        }
                    });
                }
            },
            update: function () {
                var id = vm.getRowDate();

                if (id.length > 0) {

                    vm.getDetail(id);

                    vm.showList = false;
                    vm.getDept();
                }
            },
            del: function (id) {

                var id = vm.getRowDate();
                if (id.length > 0) {

                    $.get("/shop/del?id=" + id, function (r) {
                        if (r.state) {
                            layer.msg(r.msg, {icon: 1, time: 1000});
                            // 刷新表格
                            var table = $('.dataTables-example').DataTable();
                            table.ajax.reload();
                        } else {
                            layer.msg(r.msg, {icon: 2, time: 1000});
                        }
                    })
                }

            },   //根据选中行获取选择的Id
            getRowDate: function () {
                var radios = document.getElementsByName("radio");
                var tag = false;
                var val;
                for (radio in radios) {
                    if (radios[radio].checked) {
                        tag = true;
                        val = radios[radio].value;
                        break;
                    }
                }
                if (tag) {
                    return val;
                } else {
                    layer.msg('请选中行后在进行操作！', {icon: 2, time: 1000});
                    return '';
                }
            },
            getDetail: function (id) {
                $.get("/shop/ShopPiclist?id=" + id + "&type=1", function (r) {

                    if (r.shopList.length > 0) {
                        vm.shop = r.shopList[0]
                        //商品文字介绍


                        if (vm.shop.shopExplain != null) {
                            var list = vm.shop.shopExplain.split(";")
                            list.pop()
                            vm.shop.shopExplainList = [];
                            for (var i = 0; i < list.length; i++) {
                                vm.shop.shopExplainList.push(list[i])

                            }
                        }

                        //规格与包装
                        if (vm.shop.specification != null) {
                            var listspecification = vm.shop.specification.split(";")
                            listspecification.pop()
                            vm.shop.specificationList = [];
                            for (var i = 0; i < listspecification.length; i++) {
                                vm.shop.specificationList.push(listspecification[i])
                            }

                        }

                        //suk
                        if (vm.shop.sukParameter != null) {
                            var list = vm.shop.sukParameter.split(";")
                            list.pop()
                            vm.shop.sukParameterList = [];
                            for (var i = 0; i < list.length; i++) {
                                vm.shop.sukParameterList.push(list[i])
                            }
                        }
                    }

                    // 图片轮播图
                    if (r.pictureList.length > 0) {
                        $("#reportFile").fileinput('destroy'); // 先销毁在初始化上传框
                        // 照片地址初始化
                        var initPictureAddress = new Array();
                        //初始配置项
                        var initialPreviewCfg = new Array();
                        r.pictureList.forEach(function (v, index) {
                            initPictureAddress.push(v.pageaddr + "/" + v.saveFileName)
                            initialPreviewCfg.push({
                                caption: v.fileName,
                                size: v.pictureSize,
                                width: "120px",
                                url: "/shop/delReport?id=" + v.id + "&reportaddr=" + v.reportaddr + "&vernier=" + index,
                                key: index
                            })
                        });

                        //初始化fileinput
                        var oFileInput = new FileInput();
                        //参数1:控件id、参数2:上传地址
                        oFileInput.Init("reportFile", "/shop/uploadReport", initPictureAddress, initialPreviewCfg, 1, 'reportFile', null, null);

                    } else {
                        //初始化fileinput
                        var oFileInput = new FileInput();
                        //参数1:控件id、参数2:上传地址
                        oFileInput.Init("reportFile", "/shop/uploadReport", '', '', 1, 'reportFile', null, null);
                    }

                })


                //商品suk
                $.get("/shop/ShopPiclist?id=" + id + "&type=4", function (r) {

                    if (r.shopList.length > 0) {
                        vm.shop = r.shopList[0]
                        if (vm.shop.shopExplain != null) {
                            var list = vm.shop.shopExplain.split(";")
                            list.pop()
                            vm.shop.shopExplainList = [];
                            for (var i = 0; i < list.length; i++) {
                                vm.shop.shopExplainList.push(list[i])
                            }
                        }
                        //规格与包装
                        if (vm.shop.specification != null) {
                            var listspecification = vm.shop.specification.split(";")
                            listspecification.pop()
                            vm.shop.specificationList = [];
                            console.log(vm.shop.specificationList)
                            for (var i = 0; i < listspecification.length; i++) {
                                vm.shop.specificationList.push(listspecification[i])
                            }

                        }
                        //suk
                        if (vm.shop.sukParameter != null) {
                            var list = vm.shop.sukParameter.split(";")
                            list.pop()
                            vm.shop.sukParameterList = [];
                            for (var i = 0; i < list.length; i++) {
                                vm.shop.sukParameterList.push(list[i])
                            }
                        }

                    }
                    if (r.pictureList.length > 0) {
                        $("#sukreportFile").fileinput('destroy'); // 先销毁在初始化上传框
                        // 照片地址初始化
                        var initPictureAddress = new Array();
                        //初始配置项
                        var initialPreviewCfg = new Array();
                        r.pictureList.forEach(function (v, index) {
                            initPictureAddress.push(v.pageaddr + "/" + v.saveFileName)
                            initialPreviewCfg.push({
                                caption: v.fileName,
                                size: v.pictureSize,
                                width: "120px",
                                url: "/shop/delReport?id=" + v.id + "&reportaddr=" + v.reportaddr + "&vernier=" + index,
                                key: index
                            })
                        });

                        //初始化fileinput
                        var oFileInput = new FileInput();
                        //参数1:控件id、参数2:上传地址
                        oFileInput.Init("sukreportFile", "/shop/uploadReport", initPictureAddress, initialPreviewCfg, 4, 'sukreportFile', 750, null);

                    } else {
                        //初始化fileinput
                        var oFileInput = new FileInput();
                        //参数1:控件id、参数2:上传地址
                        oFileInput.Init("sukreportFile", "/shop/uploadReport", '', '', 4, 'sukreportFile', 750, null);
                    }
                })


                //商品介绍详细图
                $.get("/shop/ShopPiclist?id=" + id + "&type=3", function (r) {

                    if (r.shopList.length > 0) {
                        vm.shop = r.shopList[0]
                        if (vm.shop.shopExplain != null) {
                            var list = vm.shop.shopExplain.split(";")
                            list.pop()
                            vm.shop.shopExplainList = [];
                            for (var i = 0; i < list.length; i++) {
                                vm.shop.shopExplainList.push(list[i])
                            }
                            //规格与包装
                            if (vm.shop.specification != null) {
                                var listspecification = vm.shop.specification.split(";")
                                listspecification.pop()
                                vm.shop.specificationList = [];
                                console.log(vm.shop.specificationList)
                                for (var i = 0; i < listspecification.length; i++) {
                                    vm.shop.specificationList.push(listspecification[i])
                                }

                            }
                            //suk
                            if (vm.shop.sukParameter != null) {
                                var list = vm.shop.sukParameter.split(";")
                                list.pop()
                                vm.shop.sukParameterList = [];
                                for (var i = 0; i < list.length; i++) {
                                    vm.shop.sukParameterList.push(list[i])
                                }
                            }
                        }

                    }
                    if (r.pictureList.length > 0) {
                        $("#introduceReportFile").fileinput('destroy'); // 先销毁在初始化上传框
                        // 照片地址初始化
                        var initPictureAddress = new Array();
                        //初始配置项
                        var initialPreviewCfg = new Array();
                        r.pictureList.forEach(function (v, index) {
                            initPictureAddress.push(v.pageaddr + "/" + v.saveFileName)
                            initialPreviewCfg.push({
                                caption: v.fileName,
                                size: v.pictureSize,
                                width: "120px",
                                url: "/shop/delReport?id=" + v.id + "&reportaddr=" + v.reportaddr + "&vernier=" + index,
                                key: index
                            })
                        });

                        //初始化fileinput
                        var oFileInput = new FileInput();
                        //参数1:控件id、参数2:上传地址
                        oFileInput.Init("introduceReportFile", "/shop/uploadReport", initPictureAddress, initialPreviewCfg, 3, 'introduceReportFile', 750, null);

                    } else {
                        //初始化fileinput
                        var oFileInput = new FileInput();
                        //参数1:控件id、参数2:上传地址
                        oFileInput.Init("introduceReportFile", "/shop/uploadReport", '', '', 3, 'introduceReportFile', 750, null);
                    }


                })
            },
            getDept: function () {
                //加载分类树
                $.get("/commodity/select", function (c) {
                    ztree = $.fn.zTree.init($("#deptTree"), setting, c.deptList);

                    var node = ztree.getNodeByParam("cid", vm.dept.cid);

                    ztree.selectNode(node);

                    if (node != null) {
                        vm.dept.parentName = node.parentName;
                    }

                })
            }
            ,
            deptTree: function () {
                layer.open({
                    type: 1,
                    offset: '50px',
                    skin: 'layui-layer-molv',
                    title: "选择分类",
                    maxmin: true,
                    area: ['380px', '380px'],
                    shade: 0,
                    shadeClose: false,
                    content: jQuery("#deptLayer"),
                    btn: ['确定', '取消'],
                    btn1: function (index) {
                        var node = ztree.getSelectedNodes();
                        //选择上级部门
                        //  vm.dept.parentId = node[0].cid;
                        //  vm.dept.parentName = node[0].cname;
                        vm.shop.cid = node[0].cid;
                        vm.shop.cname = node[0].cname;
                        layer.close(index);
                    }
                });
            }
            ,
        }
    })
;
var Dept = {
    id: "deptTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Dept.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '角色ID', field: 'cid', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '角色名称', field: 'cname', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '上级角色', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '100px'}]
    return columns;
};


var list = "";
//初始化fileinput
var FileInput = function () {
    var oFile = new Object();

    //初始化fileinput控件（第一次初始化）
    // 照片地址初始化  initPictureAddress
    //初始配置项 initialPreviewCfg =
    oFile.Init = function (ctrlName, uploadUrl, initPictureAddress, initialPreviewCfg, type, fileid, photoWidth, photoHeight) {
        var control = $('#' + ctrlName);


        //初始化变量
        var overwriteInit = true, PreviewInit = false, PreviewTypeInit = '';

        //判断是否有历史图片记录
        if (initPictureAddress.length > 0) {
            overwriteInit = false, PreviewInit = true, PreviewTypeInit = 'image';
        }

        //初始化上传控件的样式
        control.fileinput({
            language: 'zh',                                         //设置语言
            uploadUrl: uploadUrl,                                   //上传的地址
            allowedFileExtensions: ['jpg', 'png', 'PNG', 'JPG', 'jpeg'],    //接收的文件后缀
            showCaption: false,                                     //是否显示标题
            showRemove: false,                                       //显示移除按钮,跟随文本框的那个
            showUpload: false,                                       //显示上传按钮
            showCancel: false,                                      //是否显示取消按钮
            autoReplace: true,
            browseClass: "btn btn-primary",                         //按钮样式
            //dropZoneEnabled: false,                               //是否显示拖拽区域
            //minImageWidth: 50,                                    //图片的最小宽度
            //minImageHeight: 50,                                   //图片的最小高度
            maxImageWidth: photoWidth,                                  //图片的最大宽度
            maxImageHeight: photoHeight,                                 //图片的最大高度
            //maxFileSize: 0,                                       //单位为kb，如果为0表示不限制文件大小
            //minFileCount: 0,
            maxFileCount: 10,                                       //表示允许同时上传的最大文件个数
            enctype: 'multipart/form-data',
            validateInitialCount: true,
            dropZoneEnabled: true,
            overwriteInitial: overwriteInit,
            initialPreview: initPictureAddress,
            initialPreviewAsData: PreviewInit, // identify if you are sending preview data only and not the raw markup
            initialPreviewFileType: PreviewTypeInit, // image is the default and can be overridden in config below
            initialPreviewConfig: initialPreviewCfg,
            fileActionSettings: {
                dragSettings: {}
                // dragTitle: '',
                //  showDrag: true,// 显示拖拽

            },

            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
            msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
            uploadExtraData: function (previewId, index, event, data) {           //传参

                // 获取之div下子标签的元素值
                var htmvaul = $("#" + previewId).find("div").eq(1).find("div").find("div").eq(1).html();

                var picSize;//照片大小
                //格式固定，所以按长度截取
                if (htmvaul != undefined && htmvaul != 'undefined' && htmvaul.length > 0) {
                    picSize = htmvaul.substring(8, htmvaul.length - 11);
                }
                var data = {
                    "previewId": previewId,      //此处自定义传参
                    "type": type,
                    "id": vm.id,
                    "pictureSize": picSize,
                    "dstFileName": ctrlName,
                    "orderNum": index,
                };
                return data;
            },
            layoutTemplates: {
                actionUpload: '',
            }
        }).on('filesuccessremove', function (previewId, event, data, index, e) {

        });
        //导入文件上传完成之后的事件
        $("#" + fileid).on("fileuploaded", function (event, data, previewId, index) {
            $('#' + previewId).attr('fileid', data.response.result.previewId);

            alert("1")
            layer.msg('操作成功', {icon: 1, time: 1000}, function () {


                $("#myForm").bootstrapValidator('resetForm');

                var table = $('.dataTables-example').DataTable();
                table.ajax.reload();

                vm.reload();


                $(event.target)
                    .fileinput('clear')
                    .fileinput('unlock')
                $(event.target)
                    .parent()
                    .siblings('.fileinput-remove')
                    .hide()

                $("#reportFile").fileinput('destroy');

            })
        }).on('filesuccessremove', function (event, previewId, extra) {
            delete (($('#' + previewId).attr('fileid')));
        }).on('fileclear', function (event, data, previewId, index) {

        }).on('filepredelete', function (event, key, jqXHR, data) {
        }).on('filedeleted', function (event, key) {

            if (key == 0) {
                layer.msg("必须存在一张照片供展示作用！")
                vm.getDetail(vm.shop.id)

            }

        }).on('filesorted', function (event, params) {
            var newIndex;
            var oldIndex;
            var newIndexId;
            var oldIndexId;

            for (var i = 0; i < params.stack.length; i++) {
                if (params.stack[i].key == params.newIndex) {
                    newIndex = i;
                    newIndexId = params.stack[i].url.substring((params.stack[i].url).indexOf("id=") + 3, (params.stack[i].url).indexOf("&"))
                }
                if (params.stack[i].key == params.oldIndex) {
                    oldIndex = i;
                    oldIndexId = params.stack[i].url.substring((params.stack[i].url).indexOf("id=") + 3, (params.stack[i].url).indexOf("&"))
                }
            }
            var data = {"newIndex": newIndex, "newIndexId": newIndexId, "oldIndex": oldIndex, "oldIndexId": oldIndexId}

            $.ajax({
                type: "POST",
                url: "/shop/updateOrderNum",
                contentType: "application/x-www-form-urlencoded",
                dataType: "json",
                data: data,
                success: function (r) {
                    if (r.code === 0) {
                        vm.getDetail(vm.shop.id)
                    } else {
                    }
                }
            });
        });


    }


    return oFile;
};
