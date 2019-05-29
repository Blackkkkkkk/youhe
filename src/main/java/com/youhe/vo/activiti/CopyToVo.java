package com.youhe.vo.activiti;/**
 * @ClassName CopyToExpand
 * @Description TODO
 * @Author xdn
 * @Date 2019/5/2718:04
 * @Version 1.0
 */

import com.youhe.entity.activiti.Copyto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *@ClassName CopyToExpand
 *@Description 对Copyto类的扩展
 *@Author xdn
 *@Date 2019/5/2718:04
 *@Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CopyToVo extends Copyto {
        /**
         * 发送环节
         */
        private String sendNode;
        /**
         * 任务ID
         */
        private String taskId;

}
