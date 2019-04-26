package com.youhe.utils.activiti;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.youhe.activiti.engine.MyProcessEngine;
import com.youhe.common.Constant;
import com.youhe.common.ControlEnum;
import com.youhe.entity.activiti.FlowVariable;
import com.youhe.entity.activiti.FormCodeData;
import com.youhe.utils.spring.SpringContextUtils;
import org.activiti.engine.task.Attachment;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 【作用】表单解析工具<br>
 * 【说明】（无）
 *
 * @author kalvin
 * @Date 2019年03月25日 14:54
 */
public class FormParseUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(FormParseUtils.class);
    private static MyProcessEngine myProcessEngine = SpringContextUtils.bean(MyProcessEngine.class);

    /**
     * 获取任务表单代码
     * @param formKey 业务表单key
     * @param map 业务数据
     * @return
     */
    public static FormCodeData getTaskFormCode(String formKey, Map<String, Object> map) {
        String formUrl = ClassUtil.getClassPath() + "templates/activiti/form/" + formKey + ".html";
        String formHtml = FileUtil.readString(formUrl, "UTF-8");

        Document doc = Jsoup.parse(formHtml);
        // 获取业务表单中所有组件
        Elements controls = doc.select("table .form-control");
        Elements scriptEle = doc.select("script");
        String script = scriptEle.html();   // js脚本
        script = StrUtil.isAllBlank(script) ? "" : script;
//        scriptEle.remove();
        // 流程流转变量
        FlowVariable flowVariable = (FlowVariable) map.get(Constant.FLOW_VARIABLE_KEY);

        /*
         * 1.默认首环节全部可编辑
         * 2.组件配置data-edit属性：不配置-默认第一节点可编辑，其它所有节点只读  配置节点后，相应节点会可编辑
         * 3.根据不同组件进行不同处理
         **/
        controls.forEach(control -> {
            String edit = control.attr("data-edit");
            /*Element element = new Element("span");

            // 设置属性
            String name = control.attr("name");
            element.attr("name", control.attr("name"));
            String id = control.attr("id");
            if (StrUtil.isNotBlank(id)) {
                element.attr("id", control.attr("id"));
            }
            // 设置值
            String val = map.get(name) == null ? "" : map.get(name).toString();
            if (ControlEnum.SELECT.getType().equals(control.nodeName())) {
                String valShow = map.get(name) == null ? "" : map.get(name + "_show").toString();
                element.text(valShow);
            } else {
                element.text(val);
            }*/

            String type = control.attr("type");
            if (!flowVariable.isFirstNode()) {  // 不是首环节

                if (!"hidden".equals(type)) {   // 非隐藏的字段都需要处理
                    if (StrUtil.isBlank(edit)) {    // 没有配置data-edit属性，设置只读
                        parseReadOnlyEl(control, map, flowVariable);
                    } else {    // 配置了data-edit属性，根据节点key判断
                        String currentTaskKey = flowVariable.getCurrentNodeKey();
                        if (!edit.contains(currentTaskKey)) {    // 当前节点没有设置可编辑，依然设置只读
                            parseReadOnlyEl(control, map, flowVariable);
                        } else {   // 可编辑
                            parseEditEl(control, map, flowVariable);
                        }
                    }
                }
            } else if (!flowVariable.isFirstSubmit()) { // 首环节 回退情况
                    /*
                    <div id="uploader" class="wu-example">
                        <!--用来存放文件信息-->
                        <div id="thelist" class="uploader-list">
                            <input type="hidden" name="filePath" value="">
                            <input type="hidden" name="fileName" value="">
                            <div id="WU_FILE_0" class="item" data-field-id="">
                                <h4 class="info">流程.xml
                                    <span onclick="delAttachment('WU_FILE_0')" style="margin-left: 3px;color: #e6131377;font-size: 14px;" class="glyphicon glyphicon-remove">
                                    </span>
                                </h4>
                            </div>
                        </div>
                        <div class="btns">
                            <div id="picker">选择文件</div>
                        </div>
                    </div>
                    * */
                parseEditEl(control, map, flowVariable);
            } else {    // 首环节
                if (ControlEnum.FILE.getType().equals(type)) {   // 解析附件控件
                    createAttachmentEl(control, map, flowVariable, true);
                }
            }
        });

        Elements table = doc.select("table");

        return new FormCodeData(table.toString(), script);
    }

    /**
     * 解析可编辑控件
     * @param control 控制对象
     * @param map   业务数据
     * @param flowVariable  流程流转数据
     */
    private static void parseEditEl(Element control, Map<String, Object> map, FlowVariable flowVariable) {
        String name = control.attr("name");
        String val = map.get(name) == null ? "" : map.get(name).toString();
        String type = control.attr("type");
        if (ControlEnum.SELECT.getType().equals(control.nodeName())) {
            Elements options = control.select("option");
            options.forEach(option -> {
                if (val.equals(option.val())) {
                    option.attr("selected", true);
                }
            });
        } else if (ControlEnum.FILE.getType().equals(type)) {   // 解析附件控件
            createAttachmentEl(control, map, flowVariable, true);
        } else if (ControlEnum.CHECKBOX.getType().equals(type) || ControlEnum.RADIO.getType().equals(type)) {
            if (val.equals(control.val())) {
                control.attr("checked", true);
            }
        } else {
            control.val(val);
        }

    }

    /**
     * 控件解析成不可编辑或只读
     * @param control 控制对象
     * @param map   业务数据
     * @param flowVariable  流程流转数据
     */
    private static void parseReadOnlyEl(Element control, Map<String, Object> map, FlowVariable flowVariable) {
        String name = control.attr("name");
        String val = map.get(name) == null ? "" : map.get(name).toString();
        String type = control.attr("type");

        if (ControlEnum.FILE.getType().equals(type)) {  // 附件
            createAttachmentEl(control, map, flowVariable, false);
        } else if (ControlEnum.CHECKBOX.getType().equals(type)
                || ControlEnum.RADIO.getType().equals(type)) {
            control.attr("disabled", true);
            if (val.equals(control.val())) {
                control.attr("checked", true);
            }
        } else {    // 替换成span标签
            Element element = new Element("span");
            element.attr("name", control.attr("name"));
            String id = control.attr("id");
            if (StrUtil.isNotBlank(id)) {
                element.attr("id", control.attr("id"));
            }
            // 设置值
            if (ControlEnum.SELECT.getType().equals(control.nodeName())) {
                String valShow = map.get(name) == null ? "" : map.get(name + "_show").toString();
                element.text(valShow);
            } else {
                element.text(val);
            }
            control.replaceWith(element);
        }
    }


    /**
     * 创建附件控件
     * @param control 控制对象
     * @param map   业务数据
     * @param flowVariable  流程流转数据
     * @param isEdit    是否可编辑
     */
    private static void createAttachmentEl(Element control, Map<String, Object> map, FlowVariable flowVariable, boolean isEdit) {
        List<Attachment> taskAttachments = myProcessEngine.getInstanceAttachments(flowVariable.getProcessInstanceId());
        Element uploaderDivEl = new Element("div").attr("class", "wu-example");
        Element uploaderListEl = new Element("div")
                .attr("id", "thelist")  // todo 多个附件控件的情况
                .attr("class", "uploader-list");
        String filePath = map.get("filePath") == null ? "" : map.get("filePath").toString();
        String fileName = map.get("fileName") == null ? "" : map.get("fileName").toString();
        Element filePathInputEL = new Element("input")
                .attr("name", "filePath")
                .attr("type", "hidden")
                .attr("value", filePath);
        Element fileNameInputEL = new Element("input")
                .attr("name", "fileName")
                .attr("type", "hidden")
                .attr("value", fileName);

        Element btnDivEl = new Element("div").attr("class", "btns");

        // 元素节点组装
        if (isEdit) { // 可编辑，加上选择文件按钮
            Element pickerDivEl = new Element("div")
                    .attr("id", control.attr("name"))    // todo
                    .text("选择文件");
            btnDivEl.appendChild(pickerDivEl);
        }

        uploaderListEl.appendChild(filePathInputEL).appendChild(fileNameInputEL);
        int i = 0;
        for (Attachment attachment : taskAttachments) {
            i++;
            Element itemDivEl = new Element("div")
                    .attr("class", "item")
                    .attr("id", attachment.getId());    // 附件ID
            // <a href="http://www.baidu.com" target="_blank" style="color: #0d6aad">
            Element downloadAEl = new Element("a")
                    .attr("href", "/file/download?attachmentId=" + attachment.getId())       // 文件下载url
                    .attr("target", "_blank")
                    .attr("style", "color: #0d6aad");
            Element infoH4El = new Element("h4")
                    .attr("class", "info")
                    .text(i + "." + attachment.getName());  // 附件名称

            if (isEdit) {   // 可编辑，加上删除附件按钮
                Element removeSpanEl = new Element("span")
                        .attr("onclick", "delAttachment('" + attachment.getId() + "')")
                        .attr("style", "margin-left: 3px;color: #e6131377;font-size: 14px;")
                        .attr("class", "glyphicon glyphicon-remove");
                infoH4El.appendChild(removeSpanEl);
            }
            downloadAEl.appendChild(infoH4El);
            itemDivEl.appendChild(downloadAEl);
            uploaderListEl.appendChild(itemDivEl);
        }

        uploaderDivEl.appendChild(uploaderListEl).appendChild(btnDivEl);
        LOGGER.info("uploaderHtml={}", uploaderDivEl.toString());
        control.replaceWith(uploaderDivEl);
    }

    // todo 测试用，以后再删除
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        FlowVariable flowVariable = new FlowVariable();
        flowVariable.setFirstNodeKey("2");
        map.put(Constant.FLOW_VARIABLE_KEY, flowVariable);
        FormCodeData taskFormCode = FormParseUtils.getTaskFormCode("", map);
        System.out.println("taskFormCode.toString() = " + taskFormCode.toString());
    }
}
