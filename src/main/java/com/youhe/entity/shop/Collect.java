package com.youhe.entity.shop;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Collect implements Serializable {
    private static final long serialVersionUID = -2011706346401156252L;

    private Integer id;
    private Integer shopId;//商品表id
    private Integer userId;//收藏者
    private Date collectTime;//收藏时间

}
