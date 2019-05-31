package com.youhe.biz.redis;


import com.alibaba.fastjson.JSON;
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
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


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
    public void hput(KeyPrefix prefix, String key, String filed, Object value) {

        redisTemplate.opsForHash().put(prefix.getPrefix() + key, filed, value);
    }

    /*
      根据Map 存储到redis 的Hash 值里面(给某个hash 值的键值加1)
    */
    public Long hincrement(KeyPrefix prefix, String key, String filed, int num) {

        return redisTemplate.opsForHash().increment(prefix.getPrefix() + key, filed, num);
    }

      /*
      根据Map 存储到redis 的Hash 值里面(根据id 去遍历所有的值)
      */

    public List<Shop> hscan(KeyPrefix prefix, String key) {
        List<Shop> shopList = new ArrayList<Shop>();
        Shop shop = new Shop();
        //遍历出该ID 所有的购物车商品
        Cursor<Map.Entry<Object, Object>> curosr = redisTemplate.opsForHash().scan(prefix.getPrefix() + key, ScanOptions.NONE);


        while (curosr.hasNext()) {
            Map.Entry<Object, Object> entry = curosr.next();
            Map<String, Map> gson = (Map<String, Map>) JSON.parse(entry.getValue() + "");
            if (gson != null && !gson.isEmpty()) {
                for (String key1 : gson.keySet()) {
                    Map<String, Object> mapValue = new HashMap<String, Object>();
                    System.out.println(key1);
                    mapValue = gson.get(key1);

                    shop.setId(Integer.parseInt(entry.getKey() + ""));
                    shop.setShopId(Integer.parseInt(key1));
                    shopBiz.findCarList(shop);
                    if (!CollectionUtils.isEmpty(shopBiz.findCarList(shop))) {  // 去查数据库该ID1的商品信息
                        shop = shopBiz.findCarList(shop).get(0);
                        shop.setCartNum(mapValue.get("num") == null ? 0 : Integer.parseInt(mapValue.get("num") + ""));
                        shop.setPirce(mapValue.get("pirce") == null ? 0 : Integer.parseInt(mapValue.get("pirce") + ""));
                        shop.setAmount(mapValue.get("amount") == null ? 0 : Integer.parseInt(mapValue.get("amount") + ""));
                        shop.setSukChangeName(mapValue.get("sukName") + "");
                        shop.setName(shop.getName() + "(" + shop.getSukChangeName() + ")");
                        shop.setShopId(Integer.parseInt(key1));
                        shopList.add(shop);
                        shop = new Shop();
                    }
                }
            }
        }
        return shopList;
    }


    //遍历出所有值添加到数据库
    public List<Shop> hscanSave(KeyPrefix prefix, String key) {
        List<Shop> shopList = new ArrayList<Shop>();

        //遍历出该ID 所有的购物车商品
        Cursor<Map.Entry<Object, Object>> curosr = redisTemplate.opsForHash().scan(prefix.getPrefix() + key, ScanOptions.NONE);
        Shop shop = new Shop();
        while (curosr.hasNext()) {
            Map.Entry<Object, Object> entry = curosr.next();
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            if (pattern.matcher(entry.getKey() + "").matches()) {  //判断是值，还是存储的备注信息
                shop.setId(Integer.parseInt(entry.getKey() + ""));
                if (!CollectionUtils.isEmpty(shopBiz.findShopList(shop))) {  // 去查数据库该ID1的商品信息
                    shop = shopBiz.findShopList(shop).get(0);
                    shop.setCartNum(Integer.parseInt(entry.getValue() + ""));
                    System.out.println(prefix.getPrefix() + key);
                    System.out.println(entry.getKey() + "@@remark" + "");
                    shop.setRemark(redisTemplate.opsForHash().get(prefix.getPrefix() + key, entry.getKey() + "@@remark") + "" == null ? ""
                            : redisTemplate.opsForHash().get(prefix.getPrefix() + key, entry.getKey() + "@@remark") + "");
                    shopList.add(shop);
                    shop = new Shop();
                }
            }
        }
        return shopList;
    }

    /*
    删除hash结构中的某个键
    */
    public Long hdel(KeyPrefix prefix, String key, String filed) {
        return redisTemplate.opsForHash().delete(prefix.getPrefix() + key, filed);
    }


    /*
      设置 键过期时间   单位为秒
     */
    public void expire(KeyPrefix prefix, String key) {
        System.out.println(redisTemplate.expire(prefix.getPrefix() + key, prefix.expireSeconds(), TimeUnit.SECONDS));
    }

    /*
      删除某个has键
     */
    public void hdelKey(KeyPrefix prefix, String key) {
        redisTemplate.delete(prefix.getPrefix() + key);
    }
}
