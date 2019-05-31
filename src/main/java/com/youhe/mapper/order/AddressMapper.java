package com.youhe.mapper.order;


import com.youhe.entity.order.Address;


import java.util.List;

/**
 * <p>
 * 商品订单表 Mapper 接口
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-16
 */
public interface AddressMapper {

    void save(Address address);

    void del(Address address);

    List<Address> addreddList(Address address);

    void updates(Address address);
}
