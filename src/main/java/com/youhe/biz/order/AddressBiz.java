package com.youhe.biz.order;


import com.youhe.entity.order.Address;
import com.youhe.entity.order.Order;
import com.youhe.mapper.order.AddressMapper;
import com.youhe.mapper.order.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AddressBiz {

    @Autowired
    private AddressMapper addressMapper;

    private Logger log = LoggerFactory.getLogger(AddressBiz.class);


    public void save(Address address) {
        addressMapper.save(address);
    }

    public void del(Address address) {
        addressMapper.save(address);
    }

    public List<Address> addreddList(Address address) {
        return addressMapper.addreddList(address);
    }

    public void updates(Address address) {
        addressMapper.updates(address);
    }


}
