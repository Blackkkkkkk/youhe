package com.youhe.initBean.redis;

public interface RedisService {

    /**
     * 检查key是否已经存在
     *
     * @param key
     * @return
     */
    boolean existsKey(String key);

}
