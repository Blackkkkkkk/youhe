var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            rootPId: -1
        },
        key: {
            url: "nourl",
            name: "name"
        }
    }
};
var ztree;



var menu_ztree;
var menu_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            rootPId: -1
        },
        key: {
            url: "nourl",
            name: "divName"

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
        showList: false,
        title: null,
        dept: {
            id: null,
            userId: 0,
            divId: 0,
            divName: null
        },
        desktop: {
            id: null,
            userId: null,
            sysDivId: null
        }

    },
    methods: {
        reload: function () {

            vm.showList = false;
            Dept.table.refresh();
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
                    // vm.dept.parentId = node[0].rid;
                    // vm.dept.parentName = node[0].rname;

                    layer.close(index);
                }
            });
        },
        getPermission: function () {
            // var flag=2;
            $.get("/desktopSet/list" , function (r) {
                console.info(r);
                if (r.length > 0) {
                    // console.log(r)
                    //  vm.permission = r.role;
                    //勾选角色所拥有的菜单
                    var menuIds = r;
                    console.log(menuIds)
                    for (var i = 0; i < menuIds.length; i++) {
                        // console.log((menuIds.index(i)).get)
                       var  node = menu_ztree.getNodeByParam("divId",menuIds[i]);

                        menu_ztree.checkNode(node, true, false);

                    }
                    //    vm.getDept();
                }
            });
        },
        saveOrUpdate: function (event) {
            // var url = vm.dept.id == null ? "/role/save" : "/role/update";
            var url ="/desktopSet/update";
            // hh=vm.dept;
            console.log(url)
            //获取选择的权限菜单
            var nodes = menu_ztree.getCheckedNodes(true);
            debugger
            var desktopList = new Array();
            for (var i = 0; i < nodes.length; i++) {
                desktopList.push(nodes[i].divId);
            }
            vm.dept.desktopList = desktopList;
            vm.dept;
            $.ajax({
                type: "POST",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(vm.dept),
                success: function (r) {
                    if (r.Status === 0) {
                        layer.msg('操作成功', {icon: 1, time: 1000}, function () {
                            vm.reload();
                        });
                    } else {
                        layer.msg(r.msg, {icon: 2, time: 1000});
                    }
                }
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
        {title: 'ID', field: 'id', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '用户ID', field: 'userId', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '模块ID', field: 'divId', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '模块名称', field: 'divName', align: 'center', valign: 'middle', sortable: true, width: '100px'}]
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


$(function (id) {
    // var flag=2;
    $.get("/desktop/list", function (r) {
        menu_ztree = $.fn.zTree.init($("#menuTree"), menu_setting, r);

        //展开所有节点
        menu_ztree.expandAll(true);
            vm.getPermission();
        list=r;
        // =vm.dept;
        var desktopList = new Array();
        var menuIds = r;

        // for (var i = 0; i < list.length; i++) {
        //    desktopList.push(list[i].id);
        //    // var id=desktopList[i].id;
        // }
        desktopList;



        // hh=vm.dept[0].id;
        // vm.getPermission(id);

        // if (id != null) {
        //     vm.getPermission(id);
        // }
        // var name=r[0].divName;
        // var hh=r;
        // $.get("desktop/list", function (r) {
        // var colunms = Dept.initColumn();
        // var table = new TreeTable(Dept.id, "/desktop/list", colunms);
        // // table.setRootCodeValue(r.rid);
        // table.setExpandColumn(1);
        // // table.setIdField("");
        // table.setCodeField("id");
        // table.setParentCodeField("userId");
        // table.setExpandAll(false);
        // table.init();
        // Dept.table = table;
        // debugger
        // });
        // });
    });

});
