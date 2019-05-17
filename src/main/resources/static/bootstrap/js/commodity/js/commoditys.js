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
        }

    },

    methods: {
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.dept = {parentName: null, parentId: 0, orderNum: 0, cid: null, cname: null};
            vm.getDept();
            vm.getMenuTree(null);
        },
        reload: function () {
            vm.showList = true;
            Dept.table.refresh();
        },
        getDept: function () {
            //加载部门树
            $.get("/commodity/select", function (c) {
                ztree = $.fn.zTree.init($("#deptTree"), setting, c.deptList);
                console.log(ztree)

                var node = ztree.getNodeByParam("cid", vm.dept.cid);

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
                    vm.dept.parentId = node[0].cid;
                    vm.dept.parentName = node[0].cname;

                    layer.close(index);
                }
            });
        },
        getMenuTree: function (cid) {
            //加载权限树
            var url = "/permission/list"
            $.get(url, function (c) {

                menu_ztree = $.fn.zTree.init($("#menuTree"), menu_setting, c);
                //展开所有节点
                menu_ztree.expandAll(true);

                if (cid != null) {
                    vm.getPermission(cid);
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
            var url = vm.dept.cid == null ? "/commodity/save" : "/commodity/update";

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
                success: function (c) {

                    if (c.Status == 0) {
                        layer.msg(c.msg, {icon: 1, time: 1000}, function () {
                            vm.reload();
                        });
                    } else {
                        layer.msg(c.msg, {icon: 2, time: 1000});
                    }
                }
            });
        },
        update: function () {
            var deptId = getDeptId();
            if (deptId == null) {
                return;
            }
            $.get("/commodity/info/" + deptId, function (c) {
                vm.showList = false;
                vm.title = "修改";

                vm.dept = c.dept;

                vm.getDept();

                vm.getMenuTree(vm.dept.cid);
            });
        },
        del: function () {
            var cid = getDeptId();
            if (cid == null) {
                return;
            }

            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定', '取消'] //可以无限个按钮
            }, function (index, layero) {
                $.ajax({
                    type: "POST",
                    url: "/commodity/del",
                    data: {"cid": cid},
                    success: function (c) {

                        if (c.code === 0) {
                            layer.msg('操作成功', {icon: 1, time: 1000}, function () {
                                vm.reload();
                            });
                        } else {
                            layer.msg(c.msg, {icon: 2, time: 1000});
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
        {title: '商品ID', field: 'cid', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '商品名称', field: 'cname', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '上级商品', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
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
        var table = new TreeTable(Dept.id, "/commodity/list", colunms);
        table.setRootCodeValue(r.rid);
        table.setExpandColumn(2);
        table.setIdField("cid");
        table.setCodeField("cid");
        table.setParentCodeField("parentId");
        table.setExpandAll(false);
        table.init();
        Dept.table = table;
    });


});
