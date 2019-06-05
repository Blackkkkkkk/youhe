package com.youhe.controller.sys.opinion;
import com.youhe.entity.opinion.Opinion;
import com.youhe.entity.shop.Commodity;
import com.youhe.mapper.opinion.OpinionMapper;
import com.youhe.service.sys.opinion.OpinionService;
import com.youhe.utils.R;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Sara
 * @since 2019-06-05
 */
@RestController
@RequestMapping("/opinion")
public class OpinionController {

    @Autowired
    private OpinionMapper opinionMapper;
    @Autowired
    private OpinionService opinionService;


    /**
     * @param opinion 获取角色
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Opinion> list(Opinion opinion) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Long uid=shiroUser.getUid();
        String uids=String.valueOf(uid);
        opinion.setUserId(uids);
        List<Opinion> opinionList = opinionMapper.findOpinionList(opinion);

        return opinionList;
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    public R save(@RequestBody Opinion opinion) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Long uid=shiroUser.getUid();
        String uids=String.valueOf(uid);
        opinion.setUserId(uids);
        opinionService.save(opinion);
            return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public R update(@RequestBody Opinion opinion) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Long uid=shiroUser.getUid();
        String uids=String.valueOf(uid);
        opinion.setUserId(uids);
        opinionMapper.update(opinion);
//            opinionService.update(opinion);
            return R.ok(0, "更新成功");

    }

    /**
     * 信息
     */
    @RequestMapping("/info/{deptId}")
    @ResponseBody
    public R info(@PathVariable("deptId") Integer deptId) {
        Opinion opinion = new Opinion();
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Long uid=shiroUser.getUid();
        String uids=String.valueOf(uid);
        opinion.setUserId(uids);
        opinion.setId(deptId);
            opinion = opinionMapper.findOpinionList(opinion).get(0);
        return R.ok().put("dept", opinion);
    }


    /**
     * 删除
     */
    @RequestMapping("/del")
    @ResponseBody
    public R del(Opinion opinion) {

        try {
            opinionMapper.del(opinion);

        } catch (Exception e) {

//            log.info("/user/del: 用户删除失败:" + e.toString());
            return R.error("删除用户失败");
        }

        return R.ok();
    }
}

