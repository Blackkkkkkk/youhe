package com.youhe.entity.activiti;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 作用：节点实体<br>
 * 说明：(无)
 *
 * @author Kalvin
 * @Date 2019年05月09日 17:33
 */
@Data
@Accessors(chain = true)
public class Node implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String assignee;
    private String candidateUserIds;
    private String candidateGroupIds;

    public Node(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
