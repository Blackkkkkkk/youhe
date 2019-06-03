package com.youhe.dto.activiti;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 作用：流程报表封装类<br>
 * 说明：(无)
 *
 * @author Kalvin
 * @Date 2019年05月30日 10:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class ProcessReportDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String processDefId;
    private String processName;
    private Integer createCount;
    private Integer todoCount;
    private Integer archiveCount;
    private String percent;

}
