# week06

> 6.（必做）基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交 DDL 的 SQL 文件到 Github（后面 2 周的作业依然要是用到这个表结构）。

- 用户表

```sql
CREATE TABLE `users`
(
    `id`           varchar(64)   NOT NULL COMMENT '主键id 用户id',
    `username`     varchar(32)   NOT NULL COMMENT '用户名 用户名',
    `password`     varchar(64)   NOT NULL COMMENT '密码 密码',
    `nickname`     varchar(32)  DEFAULT NULL COMMENT '昵称 昵称',
    `realname`     varchar(128) DEFAULT NULL COMMENT '真实姓名',
    `face`         varchar(1024) NOT NULL COMMENT '头像',
    `mobile`       varchar(32)  DEFAULT NULL COMMENT '手机号 手机号',
    `email`        varchar(32)  DEFAULT NULL COMMENT '邮箱地址 邮箱地址',
    `sex`          int(11) DEFAULT NULL COMMENT '性别 性别 1:男  0:女  2:保密',
    `birthday`     date         DEFAULT NULL COMMENT '生日 生日',
    `created_time` datetime      NOT NULL COMMENT '创建时间 创建时间',
    `updated_time` datetime      NOT NULL COMMENT '更新时间 更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表 ';

CREATE TABLE `user_address`
(
    `id`           varchar(64)  NOT NULL COMMENT '地址主键id',
    `user_id`      varchar(64)  NOT NULL COMMENT '关联用户id',
    `receiver`     varchar(32)  NOT NULL COMMENT '收件人姓名',
    `mobile`       varchar(32)  NOT NULL COMMENT '收件人手机号',
    `province`     varchar(32)  NOT NULL COMMENT '省份',
    `city`         varchar(32)  NOT NULL COMMENT '城市',
    `district`     varchar(32)  NOT NULL COMMENT '区县',
    `detail`       varchar(128) NOT NULL COMMENT '详细地址',
    `extand`       varchar(128) DEFAULT NULL COMMENT '扩展字段',
    `is_default`   int(11) DEFAULT NULL COMMENT '是否默认地址',
    `created_time` datetime     NOT NULL COMMENT '创建时间',
    `updated_time` datetime     NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户地址表 ';
```

- 商品表

```sql
CREATE TABLE `items`
(
    `id`            varchar(64) NOT NULL COMMENT '商品主键id',
    `item_name`     varchar(32) NOT NULL COMMENT '商品名称 商品名称',
    `cat_id`        int(11) NOT NULL COMMENT '分类外键id 分类id',
    `root_cat_id`   int(11) NOT NULL COMMENT '一级分类外键id',
    `sell_counts`   int(11) NOT NULL COMMENT '累计销售 累计销售',
    `on_off_status` int(11) NOT NULL COMMENT '上下架状态 上下架状态,1:上架 2:下架',
    `content`       text        NOT NULL COMMENT '商品内容 商品内容',
    `created_time`  datetime    NOT NULL COMMENT '创建时间',
    `updated_time`  datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表 商品信息相关表：分类表，商品图片表，商品规格表，商品参数表';

CREATE TABLE `category`
(
    `id`        int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`      varchar(32) NOT NULL COMMENT '分类名称',
    `type`      int(11) NOT NULL COMMENT '分类类型',
    `father_id` int(11) NOT NULL COMMENT '父id',
    `logo`      varchar(64) DEFAULT NULL COMMENT '图标',
    `slogan`    varchar(64) DEFAULT NULL COMMENT '口号',
    `cat_image` varchar(64) DEFAULT NULL COMMENT '分类图',
    `bg_color`  varchar(32) DEFAULT NULL COMMENT '背景颜色',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8mb4 COMMENT='商品分类';

CREATE TABLE `items_spec`
(
    `id`             varchar(64)   NOT NULL COMMENT '商品规格id',
    `item_id`        varchar(64)   NOT NULL COMMENT '商品外键id',
    `name`           varchar(32)   NOT NULL COMMENT '规格名称',
    `stock`          int(11) NOT NULL COMMENT '库存',
    `discounts`      decimal(4, 2) NOT NULL COMMENT '折扣力度',
    `price_discount` int(11) NOT NULL COMMENT '优惠价',
    `price_normal`   int(11) NOT NULL COMMENT '原价',
    `created_time`   datetime      NOT NULL COMMENT '创建时间',
    `updated_time`   datetime      NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品规格 每一件商品都有不同的规格，不同的规格又有不同的价格和优惠力度，规格表为此设计';

CREATE TABLE `items_img`
(
    `id`           varchar(64)  NOT NULL COMMENT '图片主键',
    `item_id`      varchar(64)  NOT NULL COMMENT '商品外键id 商品外键id',
    `url`          varchar(128) NOT NULL COMMENT '图片地址 图片地址',
    `sort`         int(11) NOT NULL COMMENT '顺序 图片顺序，从小到大',
    `is_main`      int(11) NOT NULL COMMENT '是否主图 是否主图，1：是，0：否',
    `created_time` datetime     NOT NULL COMMENT '创建时间',
    `updated_time` datetime     NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品图片';

CREATE TABLE `items_param`
(
    `id`               varchar(64) NOT NULL COMMENT '商品参数id',
    `item_id`          varchar(32) NOT NULL COMMENT '商品外键id',
    `produc_place`     varchar(32) NOT NULL COMMENT '产地 产地，例：中国江苏',
    `foot_period`      varchar(32) NOT NULL COMMENT '保质期 保质期，例：180天',
    `brand`            varchar(32) NOT NULL COMMENT '品牌名 品牌名，例：三只大灰狼',
    `factory_name`     varchar(32) NOT NULL COMMENT '生产厂名 生产厂名，例：大灰狼工厂',
    `factory_address`  varchar(32) NOT NULL COMMENT '生产厂址 生产厂址，例：大灰狼生产基地',
    `packaging_method` varchar(32) NOT NULL COMMENT '包装方式 包装方式，例：袋装',
    `weight`           varchar(32) NOT NULL COMMENT '规格重量 规格重量，例：35g',
    `storage_method`   varchar(32) NOT NULL COMMENT '存储方法 存储方法，例：常温5~25°',
    `eat_method`       varchar(32) NOT NULL COMMENT '食用方式 食用方式，例：开袋即食',
    `created_time`     datetime    NOT NULL COMMENT '创建时间',
    `updated_time`     datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品参数 ';
```

- 订单表

```sql
CREATE TABLE `orders`
(
    `id`               varchar(64)  NOT NULL COMMENT '订单主键;同时也是订单编号',
    `user_id`          varchar(64)  NOT NULL COMMENT '用户id',
    `receiver_name`    varchar(32)  NOT NULL COMMENT '收货人快照',
    `receiver_mobile`  varchar(32)  NOT NULL COMMENT '收货人手机号快照',
    `receiver_address` varchar(128) NOT NULL COMMENT '收货地址快照',
    `total_amount`     int(11) NOT NULL COMMENT '订单总价格',
    `real_pay_amount`  int(11) NOT NULL COMMENT '实际支付总价格',
    `post_amount`      int(11) NOT NULL COMMENT '邮费;默认可以为零，代表包邮',
    `pay_method`       int(11) NOT NULL COMMENT '支付方式',
    `left_msg`         varchar(128) DEFAULT NULL COMMENT '买家留言',
    `extand`           varchar(32)  DEFAULT NULL COMMENT '扩展字段',
    `is_comment`       int(11) NOT NULL COMMENT '买家是否评价;1：已评价，0：未评价',
    `is_delete`        int(11) NOT NULL COMMENT '逻辑删除状态;1: 删除 0:未删除',
    `created_time`     datetime     NOT NULL COMMENT '创建时间（成交时间）',
    `updated_time`     datetime     NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表;';

CREATE TABLE `order_items`
(
    `id`             varchar(64)  NOT NULL COMMENT '主键id',
    `order_id`       varchar(64)  NOT NULL COMMENT '归属订单id',
    `item_id`        varchar(64)  NOT NULL COMMENT '商品id',
    `item_img`       varchar(128) NOT NULL COMMENT '商品图片',
    `item_name`      varchar(32)  NOT NULL COMMENT '商品名称',
    `item_spec_id`   varchar(32)  NOT NULL COMMENT '规格id',
    `item_spec_name` varchar(32)  NOT NULL COMMENT '规格名称',
    `price`          int(11) NOT NULL COMMENT '成交价格',
    `buy_counts`     int(11) NOT NULL COMMENT '购买数量',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单商品关联表 ';

CREATE TABLE `order_status`
(
    `order_id`     varchar(64) NOT NULL COMMENT '订单ID;对应订单表的主键id',
    `order_status` int(11) NOT NULL COMMENT '订单状态',
    `created_time` datetime DEFAULT NULL COMMENT '订单创建时间;对应[10:待付款]状态',
    `pay_time`     datetime DEFAULT NULL COMMENT '支付成功时间;对应[20:已付款，待发货]状态',
    `deliver_time` datetime DEFAULT NULL COMMENT '发货时间;对应[30：已发货，待收货]状态',
    `success_time` datetime DEFAULT NULL COMMENT '交易成功时间;对应[40：交易成功]状态',
    `close_time`   datetime DEFAULT NULL COMMENT '交易关闭时间;对应[50：交易关闭]状态',
    `comment_time` datetime DEFAULT NULL COMMENT '留言时间;用户在交易成功后的留言时间',
    PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单状态表;订单的每个状态更改都需要进行记录\n10：待付款  20：已付款，待发货  30：已发货，待收货（7天自动确认）  40：交易成功（此时可以评价）50：交易关闭（待付款时，用户取消 或 长时间未付款，系统识别后自动关闭）\n退货/退货，此分支流程不做，所以不加入';
```
