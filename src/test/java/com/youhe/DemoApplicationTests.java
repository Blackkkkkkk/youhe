package com.youhe;

import com.github.pagehelper.PageInfo;
import com.youhe.biz.shop.ShopBiz;
import com.youhe.entity.shop.Shop;
import org.activiti.engine.ProcessEngine;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.InputStream;
import java.util.List;
//测试
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ShopBiz shopBiz;

    @Test
    public void contextLoads() {
    }

    @Test
    public void set() {

        redisTemplate.opsForValue().set("name","小明");

    }

    @Test
    public void Export() {

        String deploymentId = "4";

        List<String> names = processEngine.getRepositoryService()
                .getDeploymentResourceNames(deploymentId);
        for (String name : names) {
            System.out.println(name);
            InputStream in = processEngine.getRepositoryService()
                    .getResourceAsStream(deploymentId, name);
            // 将文件保存到本地磁盘
            /*
             * OutputStream out = new FileOutputStream(new File("d:\\" + name));
             * byte[] b = new byte[1024]; int len = 0; while((len =
             * in.read(b))!=-1) { out.write(b, 0, len); } out.close();
             */
            try {
//                FileUtils.copyInputStreamToFile(in, new File("d:\\" + name));
//                in.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        }
    }

    @Test
    public void te() {
        File file = new File("./upload/stencilset.json");
        if (!file.exists()) {
            System.out.println("1");
        } else {
            System.out.println("2");
        }
    }

    @Test
    public void findShopList() {
        Shop shop = new Shop();
//        shop.setPage(1);
        shop.setPageNum(1);
        shop.setPageSize(4);
//        shop.setIsIndex(1);
        shop.setStatus(1).
                setRegister_Sort(1).
                setTop_Sort(1).
                setHotSale_Sort(1).setIsNewProductOrderNum_Sort(1);
//        PageInfo<Shop> list = shopBiz.list(shop);
        /*PageBean<Shop> page = shopBiz.findShopListByPage(shop);
//        System.out.println("shopListByPage.size() = " + shopListByPage.size());
        System.out.println("page.toString() = " + page.getCurrentPage());
        System.out.println("page.toString() = " + page.getTotalPage());
        System.out.println("page.toString() = " + page.getTotalNum());*/

        PageInfo<Shop> list = shopBiz.findCommodityByPage(shop);
        System.out.println("list.getTotal() = " + list.getTotal());

    }
}
