var wageNowTable;

var vm = new Vue({
    el: '#wrapper',
    data: {
        showList: true,
        title: "新增商品",
        modelList: {
            id: null,
            revision: null,
            name: null,
            deploymentId: null,
            createTime: null,
            editorSourceValueId: null,
        }
    },
    methods: {
        reload: function () {
            vm.showList = true;

            if (typeof (wageNowTable) == "undefined") {
                wageNowTable = $('.dataTables-example').DataTable();
                wageNowTable.ajax.reload();//初始化
            } else {
                $('.dataTables-example').dataTable().fnDraw(false);  //不会跳转到第一页，保留页面的页码和显示条数

            }
        },
        update: function (id) {
            var id = vm.getRowDate();
            if (id.length > 0) {
                debugger
                $.get("/activiti/deploy?modelId=" + id, function (r) {
                    console.log(r);
                    if (r.Status == 0) {
                        debugger
                        // layer.msg('操作成功', {icon: 1, time: 1000}, function ()
                        layer.msg('操作成功', r.msg, {icon: 1, time: 1000});
                        // 刷新表格
                        var table = $('.dataTables-example').DataTable();
                        table.ajax.reload();
                    } else {
                        layer.msg('操作失败', r.msg, {icon: 2, time: 1000});
                    }
                })
            }

        },

        // start: function (id) {
        //     debugger
        //     var id = vm.getRowDate();
        //
        //     if (id.length > 0) {
        //         $.get("/activiti/start/" + deploymentId, function (r) {
        //             console.log(r);
        //             if (r.Status == 0) {
        //                 debugger
        //                 // layer.msg('操作成功', {icon: 1, time: 1000}, function ()
        //                 layer.msg('操作成功', r.msg, {icon: 1, time: 1000});
        //                 // 刷新表格
        //                 var table = $('.dataTables-example').DataTable();
        //                 table.ajax.reload();
        //             } else {
        //                 layer.msg(r.msg, {icon: 2, time: 1000});
        //             }
        //         })
        //     }
        // },



//根据选中行获取选择的Id
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
    }
})


var list = "";
//初始化fileinput
var FileInput = function () {
    var oFile = new Object();

    //初始化fileinput控件（第一次初始化）
    // 照片地址初始化  initPictureAddress
    //初始配置项 initialPreviewCfg =
    oFile.Init = function (ctrlName, uploadUrl, initPictureAddress, initialPreviewCfg) {
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
            browseClass: "btn btn-primary",                         //按钮样式
            //dropZoneEnabled: false,                               //是否显示拖拽区域
            //minImageWidth: 50,                                    //图片的最小宽度
            //minImageHeight: 50,                                   //图片的最小高度
            //maxImageWidth: 1000,                                  //图片的最大宽度
            //maxImageHeight: 1000,                                 //图片的最大高度
            //maxFileSize: 0,                                       //单位为kb，如果为0表示不限制文件大小
            //minFileCount: 0,
            maxFileCount: 10,                                       //表示允许同时上传的最大文件个数
            enctype: 'multipart/form-data',
            validateInitialCount: true,

            overwriteInitial: overwriteInit,
            initialPreview: initPictureAddress,
            initialPreviewAsData: PreviewInit, // identify if you are sending preview data only and not the raw markup
            initialPreviewFileType: PreviewTypeInit, // image is the default and can be overridden in config below
            initialPreviewConfig: initialPreviewCfg,


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
                    "type": 1,
                    "id": vm.id,
                    "pictureSize": picSize,
                    "dstFileName": ctrlName,
                };
                return data;
            },
            layoutTemplates: {
                actionUpload: '',
            }
        }).on('filesuccessremove', function (previewId, event, data, index, e) {
        });
        //导入文件上传完成之后的事件
        $("#reportFile").on("fileuploaded", function (event, data, previewId, index) {
            $('#' + previewId).attr('fileid', data.response.result.previewId);

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
            //在移除事件里取出所需数据，并执行相应的删除指令
            //  console.log(($('#' + previewId).attr('fileid')))

            delete (($('#' + previewId).attr('fileid')));
        }).on('fileclear', function (event, data, previewId, index) {
            // console.log(event, data);
            // console.log("fileclear");
        })
    }
    return oFile;
};
