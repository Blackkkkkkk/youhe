package com.youhe.utils.activiti;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassUtil;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

/**
 * 流程引擎工具类
 */
public class ProcessUtils {

    public static String getStartUserId() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = defaultProcessEngine.getHistoryService();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId("15001").singleResult();
        return historicProcessInstance.getStartUserId();
    }

    public static void parse() {
        String s = FileUtil.readString("D:\\JavaProgramme\\IdeaProjects\\youhe\\src\\main\\resources\\templates\\activiti\\form\\test.html", "UTF-8");
        Document doc = Jsoup.parse(s);
        Elements input = doc.select("table .form-control");
        Elements script = doc.select("script");
        String scriptStr = script.html();
        script.remove();
        System.out.println(scriptStr);
//        System.out.println(s);
        System.out.println("input.size() = " + input.size());
        input.forEach(ip -> {
            Element element = new Element("span");
            element.attr("name", ip.attr("name"));
            element.text(ip.val());
            System.out.println("element.toString() = " + element.toString());
            ip.replaceWith(element);
            System.out.println("ip.toString() = " + ip.toString());
        });
        Elements table = doc.select("table");

        System.out.println("doc.toString() = " + table.toString());
    }

    public static void main(String[] args) {
        parse();
    }

}
