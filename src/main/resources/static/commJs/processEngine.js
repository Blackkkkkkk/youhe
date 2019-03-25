/**
 * 初始化
 */
$(function () {
    console.log('加载了流程引擎js..');
    $('#submitTaskModal').modal('hide');
});


/**
 * 提交任务
 * @returns {boolean}
 */
function submitTask() {
    var taskData;
    var fcEl = $('#businessForm').find('select.form-control');
    var businessFormData = yuheUtils.getFormJson('businessForm');
    var taskFormData = yuheUtils.getFormJson('taskForm');

    // 保存选择的select的text值
    for (var i = 0; i < fcEl.length; i++) {
        console.log(fcEl[i]);
        var name = $(fcEl[i]).attr('name');
        var selTxt =$(fcEl[i]).find('option:selected').text();
        businessFormData[name + '_show'] = selTxt;
    }

    console.log("businessFormData= ", businessFormData);
    console.log("taskFormData=" , taskFormData);
    taskData = businessFormData;
    taskData.flowVariable = JSON.stringify(taskFormData);
    console.log("taskData=", taskData);
    // return false;
    $.ajax({
        type: 'POST',
        url: '../../submit/task',
        dataType:'json',
        // contentType : 'application/json;charset=utf-8',
        data: taskData,
        success: function (r) {
            if (r.Status == 0) {
                alert('任务已提交');
            } else {
                alert('任务提交失败');
            }

        }
    });
    return false;
}

/**
 * 提交表单前执行事件
 * 说明：
 * 1.在需要提交任务前执行一些业务逻辑的情况下在当前表单重写此方法即可。
 * 2.如果在执行此方法条件不成立不想要提交任务，可以return false;即可。
 */
function preSubmit() {
    console.log('表单提交前事件.....');
}

/**
 * 提交表单
 * @param func
 * @returns {*}
 */
function submit(func) {
    var b = func();
    if (b === false) {
        return b;
    }
    $('#busBtn').click();
}

/**
 * 业务表单提交事件
 */
$('#businessForm').submit(function () {
    submitTask();
    return false;
});

/**
 * todo 审批用户选择
 */
function selUser() {
    alert('选择用户');
}

/**
 * todo 回退到首节点
 */
function back2FirstNode() {
    alert('未实现');
}

/**
 * todo 驳回上环节
 */
function back2PreNode() {
    alert('未实现');
}

/**
 * todo 驳回任意环节
 */
function back2AnyNode() {
    alert('未实现');
}