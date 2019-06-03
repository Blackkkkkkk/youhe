package com.youhe.dto.activiti;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 作用：报销报表数据封装类<br>
 * 说明：(无)
 *
 * @author Kalvin
 * @Date 2019年05月31日 10:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class ReimburseReportDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String subtext;
    private List<String> months;
    private List<Integer> prices;
    private List<HashMap<String, Object>> mps;
}
