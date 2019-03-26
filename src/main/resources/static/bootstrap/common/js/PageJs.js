layui.use(['layer', 'form'], function () {
    var layer = layui.layer
        , form = layui.form;

    // layer.msg('Hello World');
});


function openDes() {

    layer.open({
        type: 2,
        shade: false,
        area: ['95%', '98%'],

        maxmin: true,
        content: '/create',
        zIndex: layer.zIndex, //重点1
        success: function (layero) {
            layer.setTop(layero); //重点2
        }
    });
}

