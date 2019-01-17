package com.youhe.biz.redis;


import com.youhe.biz.shop.ShopBiz;
import com.youhe.entity.shop.Shop;
import com.youhe.initBean.redis.KeyPrefix;
import com.youhe.initBean.redis.RedisService;
import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service
public class RedisBiz implements RedisService {
    private Logger log = LoggerFactory.getLogger(RedisBiz.class);

    /**
     * 默认编码
     */
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ShopBiz shopBiz;

    /**
     * 判断某个主键是否存在
     *
     * @param key the key
     * @return the boolean
     */
    public boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }


    /**
     * 返回指定字段的值
     *
     * @param prefix
     * @param key
     * @param filed
     * @param <T>
     * @return
     */
    public <T> String hget(KeyPrefix prefix, String key, String filed) {

        String realKey = prefix.getPrefix() + key;
        return redisTemplate.opsForHash().get(realKey, filed) + "";

    }


    /**
     * 根据Map 存储到redis 的Hash 值里面(判断是否有该值)
     *
     * @param prefix
     * @param key
     * @param field
     * @return
     */
    public boolean hhasKey(KeyPrefix prefix, String key, String field) {
        try {
            String realkey = prefix.getPrefix() + key;
            Boolean result = redisTemplate.opsForHash().hasKey(realkey, field);

            return result;
        } catch (Exception e) {
            log.info("existsValue :erro =" + e.toString());
            return false;
        }
    }



    /*
      根据Map 存储到redis 的Hash 值里面(设置散列hashKey的值,仅当hashKey不存在时才设置散列hashKey的值。)
     */

    public Boolean hputIfAbsent(KeyPrefix prefix, String key, String filed) {

        return redisTemplate.opsForHash().putIfAbsent(prefix.getPrefix() + key, filed, 1);
    }

    /*
     根据Map 存储到redis 的Hash 值里面(设置散列hashKey的值)
    */
    public void hput(KeyPrefix prefix, String key, String filed) {

        redisTemplate.opsForHash().put(prefix.getPrefix() + key, filed, 1);
    }

    /*
      根据Map 存储到redis 的Hash 值里面(给某个hash 值的键值加1)
    */
    public Long hincrement(KeyPrefix prefix, String key, String filed) {

        return redisTemplate.opsForHash().increment(prefix.getPrefix() + key, filed, 1);
    }

      /*
      根据Map 存储到redis 的Hash 值里面(根据id 去遍历所有的值)
      */

    public List<Shop> hscan(KeyPrefix prefix, String key, String filed) {
        List<Shop> shopList = new ArrayList<Shop>();

        //遍历出该ID 所有的购物车商品
        Cursor<Map.Entry<Object, Object>> curosr = redisTemplate.opsForHash().scan(prefix.getPrefix() + key, ScanOptions.NONE);
        Shop shop = new Shop();
        while (curosr.hasNext()) {
            Map.Entry<Object, Object> entry = curosr.next();
            shop.setId(Integer.parseInt(entry.getKey() + ""));
            if (!CollectionUtils.isEmpty(shopBiz.findShopList(shop))) {  // 去查数据库该ID1的商品信息
                shop = shopBiz.findShopList(shop).get(0);
                shop.setCartNum(Integer.parseInt(entry.getValue() + ""));
                shopList.add(shop);
                shop = new Shop();
            }
        }
        return shopList;
    }

}
