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
        }

    },
    methods: {
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

    }
});

var User = {
    id: "deptTable",
    table: null,
    layerIndex: -1
};







$(function () {



});
