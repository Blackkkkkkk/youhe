package com.youhe.entity.collection;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Sara
 * @since 2019-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_collection")
public class Collections implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer cid;

    /**
     * 收藏地址保存的名字
     */
    private String cname;

    /**
     * 收藏地址
     */
    private String url;


}
