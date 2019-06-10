package com.youhe.mapper.collection;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youhe.entity.collection.Collections;
import com.youhe.entity.user.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Sara
 * @since 2019-05-29
 */
public interface CollectionsMapper extends BaseMapper<Collections> {
    //查找详情
//    Collections saveCollection(@Param(value ="url") String url);


    List<Collections> findCollections(String uids);

    //查找详情
    Integer finds(@Param(value="url") String url,String uids);

    //删除
    Integer del(@Param(value="url") String url);
}
