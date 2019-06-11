package com.youhe.mapper.desktop;

import com.youhe.entity.desktop.Desktop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Sara
 * @since 2019-06-03
 */
public interface DesktopMapper extends BaseMapper<Desktop> {

    List<Desktop> findDesktopList(Desktop desktop);

    //修改角色表
    void update(Desktop dept);
    void delPer (Desktop dept);

    //新增
    void save(Desktop desktop);
}
