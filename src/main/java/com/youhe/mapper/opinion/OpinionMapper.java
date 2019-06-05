package com.youhe.mapper.opinion;

import com.youhe.entity.opinion.Opinion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youhe.entity.shop.Commodity;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Sara
 * @since 2019-06-05
 */
public interface OpinionMapper extends BaseMapper<Opinion> {

    //查找
    List<Opinion> findOpinionList(Opinion opinion);
    //新增意见表
    void save(Opinion opinion);

    //修改意见表
    void update(Opinion opinion);

    //删除意见
    void del(Opinion opinion);


}
