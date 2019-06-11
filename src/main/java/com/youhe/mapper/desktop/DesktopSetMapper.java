package com.youhe.mapper.desktop;

import com.youhe.entity.desktop.Desktop;
import com.youhe.entity.desktop.DesktopSet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Sara
 * @since 2019-06-05
 */
public interface DesktopSetMapper extends BaseMapper<DesktopSet> {
    //查询角色权限表
    List<DesktopSet> find_desktop_list(DesktopSet desktopSet);

//    List<Desktop> findDesktop(Desktop desktop);

    List<DesktopSet> findDesktop(DesktopSet desktopSet);

    void delPer (DesktopSet dept);

    //新增
    void save(DesktopSet desktopSet);

}
