package com.youhe.controller.user.shop.index;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by xiaoqiang on 2018/12/19.
 */
@Controller
@RequestMapping(value = "/touristShop")
public class IndexController {


    @RequestMapping(value = "/index")
    public String index() {

        return "user/shop/index/index";
    }

}
