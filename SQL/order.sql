
CREATE TABLE `shop_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `b_order_num` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '大订单号',
  `total_price` int(11) NOT NULL DEFAULT '0' COMMENT '订单总金额：单位分',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '订单状态：0-已取消；30-待支付；60-已支付待发货；90-已发货',
  `end_time` datetime DEFAULT NULL COMMENT '完成时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品订单表';

CREATE TABLE `shop_order_detail` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `b_order_num` varchar(100) NOT NULL COMMENT '大订单号',
  `commodity_id` bigint(20) NOT NULL COMMENT '商品ID',
  `s_order_num` varchar(100) NOT NULL COMMENT '小订单号',
  `commodity_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `price` int(11) NOT NULL DEFAULT '0' COMMENT '订单总金额：单位分',
  `num` int(6) NOT NULL DEFAULT '0' COMMENT '商品数量',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单明细表';