//roleTree setting
var Rolesetting = {
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


var Deptsetting = {
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


var Roleztree;
var Deptztree;

var vm = new Vue({
    el: '#wrapper',
    data: {
        showList: true,
        title: null,
        User: {
            parentName: null,
            parentId: 0,
            orderNum: 0
        },
        dept: {
            parentName: null,
            parentId: 0,
            orderNum: 0,
            deptId: null,
            name: null
        }
    },
    methods: {
        add: function () {
            $("#myForm").bootstrapValidator('resetForm');
            vm.showList = false;
            $('#userAccount').removeAttr("disabled");

            vm.title = "新增";
            vm.User = {
                parentName: null,
                parentId: 0,
                orderNum: 0,
                roleId: null,
                roleName: null,
                uid: null,
                departmentId: null,
                departmentName: null,
                userName: null
            };
            vm.dept = {parentName: null, parentId: 0, orderNum: 0, deptId: null, name: null};
            vm.getRole();
            vm.getDept();

        },
        reload: function () {
            vm.showList = true;
            Shop.table.refresh();
            $("#myForm").bootstrapValidator('resetForm');
            $('#myForm').bootstrapTable('destroy');


        },
        getRole: function () {
            //加载部门树
            $.get("/user/select", function (r) {
                Roleztree = $.fn.zTree.init($("#roleTree"), Rolesetting, r.roleList);

                var data;

                if (vm.User.roleId == null) {
                    data = 0;
                } else {
                    data = vm.User.roleId
                }

                var node = Roleztree.getNodeByParam("rid", data);

                var node1 = Roleztree.getNodes(); //可以获取所有的父节点


                Roleztree.selectNode(node);

                vm.User.roleId = node.rid;
            })

        },
        getDept: function () {

            //加载部门树
            $.get("/department/select", function (r) {
                Deptztree = $.fn.zTree.init($("#deptTree"), Deptsetting, r.deptList);
                var node = Deptztree.getNodeByParam("deptId", vm.dept.parentId);

                Deptztree.selectNode(node);
                vm.dept.parentName = node.name;
            })
        },
        roleTree: function () {

            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择角色",
                maxmin: true,
                area: ['380px', '380px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#roleLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = Roleztree.getSelectedNodes();
                    //选择上级部门
                    vm.User.roleId = node[0].rid;
                    vm.User.roleName = node[0].rname;
                    layer.close(index);
                }
            });
        }, deptTree: function () {
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
                    var node = Deptztree.getSelectedNodes();
                    //选择上级部门

                    vm.dept.departmentId = node[0].deptId;
                    vm.User.departmentId = node[0].deptId;
                    vm.dept.name = node[0].name;
                    vm.User.departmentName = node[0].name;

                    layer.close(index);
                }
            });
        },
        saveOrUpdate: function (event) {

            var bootstrapValidator = $("#myForm").data('bootstrapValidator');
            bootstrapValidator.validate();
            if (bootstrapValidator.isValid()) {
                var url = vm.User.uid == null ? "/user/save" : "/user/update";
                $.ajax({
                    type: "POST",
                    url: url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.User),
                    success: function (r) {
                        if (r.code === 0) {
                            layer.msg('操作成功', {icon: 1, time: 1000}, function () {
                                vm.reload();
                                $("#myForm").bootstrapValidator('resetForm');
                            });
                        } else {
                            layer.msg(r.msg, {icon: 2, time: 1000});
                            vm.reload();
                            $("#myForm").bootstrapValidator('resetForm');
                        }
                    }
                })
            }
            else {
                return;
            }
        },
        update: function () {
            var deptId = getDeptId();
            if (deptId == null) {
                return;
            }


            $.get("/user/info/" + deptId, function (r) {
                vm.showList = false;
                vm.title = "修改";
                vm.User = r.user;
                vm.User.userPassword = null;

                $('#userAccount').attr("disabled", "disabled");


                vm.getRole();
                vm.getDept();
            });
        },
        del: function () {
            var uid = getDeptId();
            if (uid == null) {
                return;
            }

            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定', '取消'] //可以无限个按钮
            }, function (index, layero) {

                $.ajax({
                    type: "POST",
                    url: "/user/del",
                    data: {"uid": uid},
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

var Shop = {
    id: "deptTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Shop.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'ID', field: 'id', index: 'id', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '商品名称', field: 'name',index: 'name', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '商品价格', field: 'pirce',index: 'pirce', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '商品数量', field: 'num', index: 'num',align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '详情图', field: 'detail_picture', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '缩略图', field: 'thumbnail', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '是否置顶', field: 'top',index: 'top', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '是否激活', field: 'status', index: 'status',align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '添加时间', field: 'register',index: 'register', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '排序号', field: 'orderNum',index: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '100px'}]


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

    var colunms = Shop.initColumn();
    var table = new TreeTable(Shop.id, "/shop/list", colunms);

    console.log(table)

    table.setIdField("id");
    table.setCodeField("id");
    table.init();

    Shop.table = table;


});
