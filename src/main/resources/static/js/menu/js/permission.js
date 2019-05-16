
var setting = {

    data: {
        simpleData: {
            enable: true,
            idKey: "pid",
            pIdKey: "parentid",
            rootPId: -1
        },
        key: {
           name:"pname"
        }
    }
};
var ztree;
var vm = new Vue({
    el: '#wrapper',
    data: {
        showList: true,
        title: null,
        dept: {
            parentName: null,
            parentid: 0,
            sortstring: 0,
        }
    },
    methods: {
        add: function () {
            $("span.selected-icon i:first-child").removeClass();
            vm.showList = false;
            vm.title = "新增";
            vm.dept = {parentName: null, parentid: 0, sortstring: 0};
            vm.getDept();
        },
        reload: function () {
            vm.showList = true;
            Dept.table.refresh();
        },

        getDept: function () {
            //加载部门树
            $.get("/permission/select", function (r) {
                ztree = $.fn.zTree.init($("#deptTree"), setting, r.permissionList);
                var node = ztree.getNodeByParam("pid", vm.dept.parentid);
                ztree.selectNode(node);
                vm.dept.parentName = node.pname;
            })
        },
        deptTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                maxmin: true,
                area: ['380px', '380px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    console.log(index);
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.dept.parentid = node[0].pid;

                    vm.dept.parentName = node[0].pname;

                    layer.close(index);
                }
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.dept.pid == null ? "/permission/save" : "/permission/update";
            vm.dept.icon=$('#picker').val();
            console.log(JSON.stringify(vm.dept));
            console.log(url);
            $.ajax({
                type: "POST",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(vm.dept),
                success: function (r) {
                    console.log(r)
                    if (r.Status===0) {

                        layer.msg('操作成功', {icon: 1, time: 1000}, function () {
                            location.reload();
                        });
                    } else {

                        layer.msg(r.msg, {icon: 2, time: 1000});
                    }
                }
            });
        },
        update: function () {
            var pid = getDeptId();
            if (pid == null) {
                return;
            }
            $.get("/permission/info/" + pid, function (r) {
                console.log(r.dept.icon)
                $("span.selected-icon i:first-child").removeClass();
                $("span.selected-icon i:first-child").addClass(r.dept.icon);
                vm.showList = false;
                vm.title = "修改";
                vm.dept = r.dept;
                vm.getDept();

            });

        },
        del: function () {
            var pid = getDeptId();
            if (pid == null) {
                return;
            }

            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定', '取消'] //可以无限个按钮
            }, function (index, layero) {

                console.log({"\"pid\"": pid})

                $.ajax({
                    type: "POST",
                    url: "/permission/del",
                    data: {"pid": pid},
                    success: function (r) {

                        if (r.Status===0 ) {
                            layer.msg('操作成功', {icon: 1, time: 1000}, function () {
                            location.reload();
                            });

                        } else {
                            layer.msg(r.msg, {icon: 2, time: 1000});
                        }
                    }
                });
            }, function (index) {

            });

        }
    }
});

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
        {title: '菜单名称', field: 'pname', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '菜单类型', field: 'types', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '访问路径', field: 'url', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '权限代码', field: 'percode', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '上级菜单', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '排序号', field: 'sortstring', align: 'center', valign: 'middle', sortable: true, width: '100px'}
        ]
    return columns;
};


function getDeptId() {
    var selected = $('#deptTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        return selected[0].id;
    }
}


$(function () {
           var trs ;
    $.get("/permission/info", function (r) {
        var colunms = Dept.initColumn();
        var table = new TreeTable(Dept.id, "/permission/list", colunms);
        table.setRootCodeValue(r.pid);
        table.setExpandColumn(2);
        table.setIdField("pid");
        table.setCodeField("pid");
        table.setParentCodeField("parentid");
        table.setExpandAll(false);
        table.init();

        Dept.table = table;


    });



});
