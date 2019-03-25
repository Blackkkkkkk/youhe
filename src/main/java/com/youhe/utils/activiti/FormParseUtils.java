package com.youhe.utils.activiti;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.youhe.common.Constant;
import com.youhe.entity.activiti.FlowVariable;
import com.youhe.entity.activiti.FormCodeData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * 【作用】表单解析工具<br>
 * 【说明】（无）
 *
 * @author kalvin
 * @Date 2019年03月25日 14:54
 */
public class FormParseUtils {

    /**
     * 获取任务表单代码
     * @param formKey
     * @param map
     * @return
     */
    public static FormCodeData getTaskFormCode(String formKey, Map<String, Object> map) {
        String formUrl = ClassUtil.getClassPath() + "templates/activiti/form/" + formKey + ".html";
//        formUrl = "D:\\JavaProgramme\\IdeaProjects\\youhe\\src\\main\\resources\\templates\\activiti\\form\\test.html";
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
            if (!flowVariable.isFirstNode()) {  // 不是首环节
                Element element = new Element("span");

                // 设置属性
                String name = control.attr("name");
                element.attr("name", control.attr("name"));
                String id = control.attr("id");
                if (StrUtil.isNotBlank(id)) {
                    element.attr("id", control.attr("id"));
                }
                // 设置值
                String val = map.get(name) == null ? "" : map.get(name).toString();
                if ("select".equals(control.nodeName())) {
                    String valShow = map.get(name) == null ? "" : map.get(name + "_show").toString();
                    element.text(valShow);
                } else {
                    element.text(val);
                }

                String type = control.attr("type");
                if (!type.equals("hidden")) {   // 非隐藏的字段都需要处理
                    if (StrUtil.isBlank(edit)) {    // 没有配置data-edit属性，设置只读
                        control.replaceWith(element);
                    } else {    // 配置了data-edit属性，根据节点key判断
                        String currentTaskKey = flowVariable.getCurrentNodeKey();
                        if (!edit.contains(currentTaskKey)) {    // 当前节点没有设置可编辑，依然设置只读
                            control.replaceWith(element);
                        } else {   // 可编辑
                            if ("select".equals(control.nodeName())) {
                                Elements options = control.select("option");
                                options.forEach(option -> {
                                    if (val.equals(option.val())) {
                                        option.attr("selected", true);
                                    }
                                });
                            } else {
                                control.val(val);
                            }
                        }
                    }
                }
            }
        });

        Elements table = doc.select("table");

        return new FormCodeData(table.toString(), script);
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        FlowVariable flowVariable = new FlowVariable();
        flowVariable.setFirstNodeKey("2");
        map.put(Constant.FLOW_VARIABLE_KEY, flowVariable);
        FormCodeData taskFormCode = FormParseUtils.getTaskFormCode("", map);
        System.out.println("taskFormCode.toString() = " + taskFormCode.toString());
    }
}
