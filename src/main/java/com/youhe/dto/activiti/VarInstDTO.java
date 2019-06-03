package com.youhe.dto.activiti;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 作用：流程流转变量封装类<br>
 * 说明：(无)
 *
 * @author Kalvin
 * @Date 2019年05月30日 17:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class VarInstDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String processInstId;
    private String varName;
    private String varType;
    private String value;
    private String byteId;
}
