package com.youhe.entity.activiti;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 【作用】封装业务表单代码数据<br>
 * 【说明】（无）
 *
 * @author kalvin
 * @Date 2019年03月25日 15:04
 */
@Setter
@Getter
@ToString
public class FormCodeData {

    /**
     * 表格html代码
     */
    private String tableHtml;
    /**
     * 脚本代码
     */
    private String script;

    public FormCodeData() {

    }

    public FormCodeData(String tableHtml, String script) {
        this.tableHtml = tableHtml;
        this.script = script;
    }
}
