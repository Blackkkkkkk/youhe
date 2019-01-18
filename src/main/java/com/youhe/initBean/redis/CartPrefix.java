package com.youhe.initBean.redis;

public class CartPrefix extends BasePrefix{

    public CartPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 设置购物车缓存
     */
    public static CartPrefix getCartList= new CartPrefix(300,"cart");
}
