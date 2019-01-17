package com.youhe.mapper.shop.car;

import com.youhe.entity.shop.Picture;
import com.youhe.entity.shop.Shop;

import java.util.List;


public interface ShopCarMapper {


    int addCart(String userId, String productId, int num);

    /**
     * 展示购物车
     *
     * @param userId
     * @return
     */
    List<Shop> getCartList(String userId);

    /**
     * 更新数量
     *
     * @param userId
     * @param productId
     * @param num
     * @return
     */
    int updateCartNum(String userId, String productId, int num);


    /**
     * 全选商品
     *
     * @param userId
     * @param checked
     * @return
     */
    int checkAll(String userId, String checked);

    /**
     * 删除商品
     *
     * @param userId
     * @param productId
     * @return
     */
    int delCartProduct(String userId, String productId);


    /**
     * 清空购物车
     * @param userId
     * @return
     */
    public int delCart(String userId);
}
