var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
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
            parentId: 0,
            orderNum: 0
        }
    },
    methods: {
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.dept = {parentName: null, parentId: 0, orderNum: 0};
            vm.getDept();
        },
        reload: function () {
            vm.showList = true;
            Dept.table.refresh();
        },
        getDept: function () {
            //加载部门树
            $.get("/department/select", function (r) {
                ztree = $.fn.zTree.init($("#deptTree"), setting, r.deptList);
                var node = ztree.getNodeByParam("deptId", vm.dept.parentId);

                ztree.selectNode(node);
                vm.dept.parentName = node.name;
            })
        },
        deptTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择部门",
                maxmin: true,
                area: ['380px', '380px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.dept.parentId = node[0].deptId;
                    vm.dept.parentName = node[0].name;

                    layer.close(index);
                }
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.dept.deptId == null ? "/department/save" : "/department/update";
            console.log(JSON.stringify(vm.dept));
            $.ajax({
                type: "POST",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(vm.dept),
                success: function (r) {
                    if (r.Status=== 0) {
                        layer.msg('操作成功', {icon: 1, time: 1000}, function () {
                            vm.reload();
                        });
                    } else {
                        layer.msg(r.msg, {icon: 2, time: 1000});
                    }
                }
            });
        },
        update: function () {
            var deptId = getDeptId();
            if (deptId == null) {
                return;
            }
            $.get("/department/info/" + deptId, function (r) {
                vm.showList = false;
                vm.title = "修改";
                vm.dept = r.dept;

                vm.getDept();
            });
        },
        del: function () {
            var deptId = getDeptId();
            if (deptId == null) {
                return;
            }

            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定', '取消'] //可以无限个按钮
            }, function (index, layero) {

                console.log({"\"deptId\"": deptId})

                $.ajax({
                    type: "POST",
                    url: "/department/del",
                    data: {"deptId": deptId},
                    success: function (r) {

                        if (r.code === 0) {
                            layer.msg('操作成功', {icon: 1, time: 1000}, function () {
                                vm.reload();
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
        {title: '部门ID', field: 'deptId', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '部门名称', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '上级部门', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '100px'}]
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
    $.get("/department/info", function (r) {
        var colunms = Dept.initColumn();
        var table = new TreeTable(Dept.id, "/department/list", colunms);
        table.setRootCodeValue(r.deptId);
        table.setExpandColumn(2);
        table.setIdField("deptId");
        table.setCodeField("deptId");
        table.setParentCodeField("parentId");
        table.setExpandAll(false);
        table.init();
        Dept.table = table;
    });


});
