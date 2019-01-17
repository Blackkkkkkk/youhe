package com.youhe.controller.sys.commodity;

import com.youhe.biz.shop.CommodityBiz;
import com.youhe.controller.loginController.LoginController;
import com.youhe.controller.sys.department.DepartmentController;
import com.youhe.entity.role.Role;
import com.youhe.entity.shop.Commodity;
import com.youhe.serviceImpl.Controller.roleController.RoleControllerImpl;
import com.youhe.serviceImpl.Controller.shopController.CommodityControllerImpl;
import com.youhe.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoqiang on 2018/12/19.
 */
@Controller
@RequestMapping(value = "/commodity")
public class CommodityController {

    @Autowired
    private CommodityBiz commodityBiz;

    @Autowired
    private CommodityControllerImpl commodityControllerImpl;



    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/index")
    public String index() {

        return "sys/commodity/commodity";
    }

    /**
     * @param commodity 获取角色
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Commodity> list(Commodity commodity) {
        List<Commodity> commodityList = commodityBiz.findCommodityList(commodity);

        return commodityList;
    }

    /**
     * 选择部门(添加、修改菜单)
     */
    @RequestMapping("/select")
    @ResponseBody
    public Map<String, List<Commodity>> select(Commodity commodity) {
        Map<String, List<Commodity>> map = new HashMap<String, List<Commodity>>();
        List<Commodity> deptList = commodityBiz.findCommodityList(commodity);
        map.put("deptList", deptList);
        return map;
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    public R save(@RequestBody Commodity commodity) {
        try {
            commodityControllerImpl.save(commodity);
            return R.ok();
        }catch (Exception e){
            log.info("/role/update: 角色保存失败:" + e.toString());
            return R.error("保存失败！");
        }
    }


    /**
     * 删除
     */
    @RequestMapping("/del")
    @ResponseBody
    public R del(Commodity commodity) {

        try {
            commodityControllerImpl.del(commodity);

        } catch (Exception e) {

            log.info("/user/del: 用户删除失败:" + e.toString());
            return R.error("删除用户失败");
        }

        return R.ok();
    }


//    /**
//     * 上级角色Id(管理员则为0)
//     */
//    @RequestMapping("/info")
//    @ResponseBody
//    //@RequiresPermissions("sys:dept:list")
//    public Map<String, Long> info() {
//
//        DepartmentController departmentController = new DepartmentController();
//
//        return departmentController.info();
//    }

    /**
     * 信息
     */
    @RequestMapping("/info/{deptId}")
    @ResponseBody
    public R info(@PathVariable("deptId") Long deptId) {
        Commodity commodity = new Commodity();
        commodity.setCid(deptId + "");

        try {
            commodity = commodityBiz.findCommodityList(commodity).get(0);
        } catch (Exception e) {
            log.info("/info/{deptId}: 查询为空");
        }

        return R.ok().put("dept", commodity);
    }



    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public R update(@RequestBody Commodity commodity) {

        try {
            commodityControllerImpl.update(commodity);
            return R.ok();
        }catch (Exception e){

            log.info("/role/update: 角色更新失败:" + e.toString());
            return R.error("更新失败！");
        }
    }



}
