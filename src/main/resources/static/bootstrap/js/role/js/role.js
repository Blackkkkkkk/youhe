var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "rid",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl",
            name: "rname"
        }
    }
};
var ztree;


//权限树
var menu_ztree;
var menu_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "pid",
            pIdKey: "parentid",
            rootPId: -1
        },
        key: {
            url: "nourl",
            name: "pname"

        }
    },
    check: {
        enable: true,
        nocheckInherit: true
    }
};


var vm = new Vue({
    el: '#wrapper',
    data: {
        showList: true,
        title: null,
        dept: {
            parentName: null,
            parentId: 0,
            orderNum: 0,
            rid: null,
            rname: null
        },
        permission: {
            pid: null,
            pname: null,
            type: null,
            url: null,
            parentid: null,
            parentids: null,
            sortstring: null
        }

    },
    methods: {
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.dept = {parentName: null, parentId: 0, orderNum: 0, rid: null, rname: null};
            vm.getDept();
            vm.getMenuTree(null);
        },
        reload: function () {
            vm.showList = true;
            Dept.table.refresh();
        },
        getDept: function () {
            //加载部门树
            $.get("/role/select", function (r) {
                ztree = $.fn.zTree.init($("#deptTree"), setting, r.deptList);

                var node = ztree.getNodeByParam("rid", vm.dept.rid);

                ztree.selectNode(node);

                if (node != null) {
                    vm.dept.parentName = node.parentName;
                }

            })
        },
        deptTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择角色",
                maxmin: true,
                area: ['380px', '380px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级部门
                    vm.dept.parentId = node[0].rid;
                    vm.dept.parentName = node[0].rname;

                    layer.close(index);
                }
            });
        },
        getMenuTree: function (rid) {
            //加载权限树
            var url = "/permission/list"
            $.get(url, function (r) {

                menu_ztree = $.fn.zTree.init($("#menuTree"), menu_setting, r);
                //展开所有节点
                menu_ztree.expandAll(true);

                if (rid != null) {
                    vm.getPermission(rid);
                }
            });
        },
        getPermission: function (rid) {
            $.get("/permission/permission_Rolelist?sys_role_id=" + rid, function (r) {


                if (r.length > 0) {


                   // console.log(r)

                  //  vm.permission = r.role;

                    //勾选角色所拥有的菜单
                    var menuIds = r;

                    console.log(menuIds)

                    for (var i = 0; i < menuIds.length; i++) {
                       // console.log((menuIds.index(i)).get)
                        var node = menu_ztree.getNodeByParam("pid", menuIds[i]);


                        menu_ztree.checkNode(node, true, false);
                    }
                    //    vm.getDept();
                }
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.dept.rid == null ? "/role/save" : "/role/update";


            console.log(url)

            //获取选择的权限菜单
            var nodes = menu_ztree.getCheckedNodes(true);
            var permissiondList = new Array();
            for (var i = 0; i < nodes.length; i++) {
                permissiondList.push(nodes[i].pid);
            }
            vm.dept.permissiondList = permissiondList;

            $.ajax({
                type: "POST",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(vm.dept),
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
        },
        update: function () {
            var deptId = getDeptId();
            if (deptId == null) {
                return;
            }
            $.get("/role/info/" + deptId, function (r) {
                vm.showList = false;
                vm.title = "修改";

                vm.dept = r.dept;

                vm.getDept();

                vm.getMenuTree(vm.dept.rid);
            });
        },
        del: function () {
            var rid = getDeptId();
            if (rid == null) {
                return;
            }

            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定', '取消'] //可以无限个按钮
            }, function (index, layero) {


                $.ajax({
                    type: "POST",
                    url: "/role/del",
                    data: {"rid": rid},
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
        {title: '角色ID', field: 'rid', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '角色名称', field: 'rname', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '上级角色', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
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
        var table = new TreeTable(Dept.id, "/role/list", colunms);
        table.setRootCodeValue(r.rid);
        table.setExpandColumn(2);
        table.setIdField("rid");
        table.setCodeField("rid");
        table.setParentCodeField("parentId");
        table.setExpandAll(false);
        table.init();
        Dept.table = table;
    });


});
