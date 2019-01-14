/**
 * Created by xiaoqiang on 2019/1/10.
 */
var vm = new Vue({
    el: '#wrapper',
    data: {
        showList: true,
        title: "轮播图第一张详情设置",
        carousel: {
            slideshow_1_id: 1,
            text_1_1: null,
            text_1_2: null,
            text_1_3: null,
            text_1_4: null,
            text_1_5: null,
            text_1_6: null,
            type: null
        }
    },
    methods: {
        initCarousel: function () {
            $.get("/touristShop/CarouselList", function (r) {
                r.carouselList.forEach(function (v, index) {
                    if (v.type == 1) {   // 为1的时候是轮播图第一张
                        vm.carousel.slideshow_1_id = v.id;
                        vm.carousel.text_1_1 = v.text_1;
                        vm.carousel.text_1_2 = v.text_2;
                        vm.carousel.text_1_3 = v.text_3;
                        vm.carousel.text_1_4 = v.text_4;
                        vm.carousel.text_1_5 = v.text_5;
                        vm.carousel.text_1_6 = v.text_6;
                        vm.carousel.type = v.type;
                    }
                })
            })

        },
        Update: function (type) {

            var url; // 保存的地址
            if (type == 1) {
                var data = {  // 轮播图第一张
                    "id": vm.carousel.slideshow_1_id,
                    "type": vm.carousel.type,
                    "text_1": vm.carousel.text_1_1,
                    "text_2": vm.carousel.text_1_2,
                    "text_3": vm.carousel.text_1_3,
                    "text_4": vm.carousel.text_1_4,
                    "text_5": vm.carousel.text_1_5,
                    "text_6": vm.carousel.text_1_6
                }
                url = '/touristShop/CarouselSave'
            }

            $.ajax({
                type: "POST",
                url: url,
                dataType: "json",
                data: data,
                success: function (r) {
                    if (r.code === 0) {
                        layer.msg('操作成功', {icon: 1, time: 1000}, function () {
                            $('#reportFile').fileinput('upload'); //触发插件开始上传。
                        });
                    } else {
                        layer.msg(r.msg, {icon: 2, time: 1000});

                        $("#myForm").bootstrapValidator('resetForm');
                    }
                }
            })

        }
    }
})


//初始化fileinput
var FileInput = function () {
    var oFile = new Object();

    //初始化fileinput控件（第一次初始化）
    // 照片地址初始化  initPictureAddress
    //初始配置项 initialPreviewCfg =
    // dataId:传入参数指定的id   dataVuale:dataId的值  type:照片的类型
    oFile.Init = function (ctrlName, uploadUrl, initPictureAddress,
                           initialPreviewCfg, dataId, type,
                           minImageWidth, minImageHeight, maxImageWidth, maxImageHeight) {
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
            minImageWidth: minImageWidth,                                    //图片的最小宽度
            minImageHeight: minImageHeight,                                   //图片的最小高度
            maxImageWidth: maxImageWidth,                                  //图片的最大宽度
            maxImageHeight: maxImageHeight,                                 //图片的最大高度
            maxFileSize: 0,                                       //单位为kb，如果为0表示不限制文件大小
            minFileCount: 0,
            maxFileCount: 1,                                       //表示允许同时上传的最大文件个数
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


                // 判断是哪个类型的上传，然后获取对应的id值
                var idVale;
                if (dataId == 'slideshow_1') {
                    idVale = vm.carousel.slideshow_1_id;
                }


                var data = {
                    "previewId": previewId,      //此处自定义传参
                    "type": type,
                    "id": idVale,
                    "pictureSize": picSize,
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

            delete(($('#' + previewId).attr('fileid')));
        }).on('fileclear', function (event, data, previewId, index) {
            // console.log(event, data);
            // console.log("fileclear");
        })
    }
    return oFile;
};


$(function () {
    vm.initCarousel();  // 初始化数据


    //根据点击对应的标签，显示对应的form
    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        // 获取已激活的标签页的名称
        var activeTab = $(e.target).text();
        // 获取前一个激活的标签页的名称
        var previousTab = $(e.relatedTarget).text();
        $(".active-tab span").html(activeTab);
        $(".previous-tab span").html(previousTab);

        console.log($(e.target).text())

        if ($(e.target).text() == '第一张板排') {
            vm.showList = false;
        } else {
            vm.showList = true;
        }
    });

    //初始化fileinput
    var oFileInput = new FileInput();

    // dataId:传入参数指定的id   dataVuale:dataId的值  type:照片的类型
    //参数1:控件id、参数2:上传地址
    oFileInput.Init("reportFile", "/shop/uploadReport", '', '', 'slideshow_1', '2',
        "1500", "495", "1500", "495");


});