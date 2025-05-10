create table mall_books
(
    book_id           bigint auto_increment comment '书籍唯一ID',
    title             varchar(255)                     null comment '书籍标题',
    authors           varchar(255)   default 'Unknown' null comment '作者列表',
    pub_year          varchar(255)   default '2025'    null comment '出版年份',
    book_avg_rating   decimal(3, 2)  default 0.00      null comment '平均评分',
    book_rating_std   decimal(5, 4)  default 0.0000    null comment '评分标准差',
    book_rating_count int            default 10        null comment '评分人数',
    page_count        bigint         default 0         null comment '页数',
    price             decimal(10, 2) default 0.00      null comment '定价',
    img               text                             null comment '封面图片URL',
    description       text                             null comment '详细描述',
    units             varchar(50)    default '本'      null comment '销售单位',
    inventory         int            default 0         null comment '当前库存量',
    merchant          varchar(100)                     null comment '归属商家名称',
    PRIMARY KEY (`book_id`) USING BTREE,
    check (`book_avg_rating` between 0.00 and 5.00),
    check (`book_rating_count` >= 0)
)
    comment '商品信息-书籍核心数据表';

create table mall_categories
(
    category_id   tinyint auto_increment comment '分类唯一标识',
    category_name varchar(50) not null comment '分类名称',
    img           text        null comment '分类图片URL',
    PRIMARY KEY (`category_id`) USING BTREE,
    constraint idx_category_name
        unique (category_name),
    check (char_length(`category_name`) between 2 and 50)
)
    comment '书籍分类目录表';

create table mall_category_tags
(
    category_id   tinyint      not null comment '分类ID',
    category_name varchar(50)  not null comment '分类名称',
    tag_id        mediumint    not null comment '标签ID',
    tag_name      varchar(100) not null comment '标签名称',
    primary key (category_id, tag_id),
    CONSTRAINT `fk_categorytags_category`
        FOREIGN KEY (`category_id`)
            REFERENCES `mall_categories` (`category_id`)
            ON DELETE CASCADE,

    CONSTRAINT `fk_categorytags_tag`
        FOREIGN KEY (`tag_id`)
            REFERENCES `mall_tags` (`tag_id`)
            ON DELETE CASCADE
)
    comment '分类与标签的关联关系表';

create table mall_tags
(
    tag_id   bigint auto_increment comment '标签ID',
    tag_name varchar(50) not null comment '标签名称',
    PRIMARY KEY (`tag_id`) USING BTREE,
    constraint uniq_tag_name
        unique (tag_name)
)
    comment '存储系统标签信息表' collate = utf8mb4_general_ci;

create table mall_book_tags
(
    book_id bigint not null comment '书籍ID',
    tag_id  bigint not null comment '标签ID',
    primary key (book_id, tag_id),
    constraint fk_booktags_book
        foreign key (book_id) references mall_books (book_id)
            on delete cascade,
    constraint fk_booktags_tag
        foreign key (tag_id) references mall_tags (tag_id)
            on delete cascade
)
    comment '书籍与标签的关联关系表';

create table sys_menu
(
    menu_id     bigint auto_increment comment '菜单ID',
    menu_name   varchar(50)              not null comment '菜单名称',
    parent_id   bigint       default 0   null comment '父菜单ID',
    order_num   int          default 0   null comment '显示顺序',
    path        varchar(200) default ''  null comment '路由地址',
    component   varchar(255)             null comment '组件路径',
    query       varchar(255)             null comment '路由参数',
    route_name  varchar(50)  default ''  null comment '路由名称',
    is_frame    int          default 1   null comment '是否为外链（0是 1否）',
    is_cache    int          default 0   null comment '是否缓存（0缓存 1不缓存）',
    menu_type   char         default ''  null comment '菜单类型（M目录 C菜单 F按钮）',
    visible     char         default '0' null comment '菜单状态（0显示 1隐藏）',
    status      char         default '0' null comment '菜单状态（0正常 1停用）',
    perms       varchar(100)             null comment '权限标识',
    icon        varchar(100) default '#' null comment '菜单图标',
    create_by   varchar(64)  default ''  null comment '创建者',
    create_time datetime                 null comment '创建时间',
    update_by   varchar(64)  default ''  null comment '更新者',
    update_time datetime                 null comment '更新时间',
    remark      varchar(500) default ''  null comment '备注',
    PRIMARY KEY (`menu_id`) USING BTREE
)
    comment '菜单权限表';

create table sys_role
(
    role_id             bigint auto_increment comment '角色ID',
    role_name           varchar(30)             not null comment '角色名称',
    role_key            varchar(100)            not null comment '角色权限字符串',
    role_sort           int                     not null comment '显示顺序',
    data_scope          char        default '1' null comment '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    menu_check_strictly tinyint(1)  default 1   null comment '菜单树选择项是否关联显示',
    dept_check_strictly tinyint(1)  default 1   null comment '部门树选择项是否关联显示',
    status              char                    not null comment '角色状态（0正常 1停用）',
    del_flag            char        default '0' null comment '删除标志（0代表存在 2代表删除）',
    create_by           varchar(64) default ''  null comment '创建者',
    create_time         datetime                null comment '创建时间',
    update_by           varchar(64) default ''  null comment '更新者',
    update_time         datetime                null comment '更新时间',
    remark              varchar(500)            null comment '备注',
    PRIMARY KEY (`role_id`) USING BTREE
)
    comment '角色信息表';

create table sys_role_menu
(
    role_id bigint not null comment '角色ID',
    menu_id bigint not null comment '菜单ID',
    primary key (role_id, menu_id),
    constraint fk_rolemenu_menu
        foreign key (menu_id) references sys_menu (menu_id)
            on delete cascade,
    constraint fk_rolemenu_role
        foreign key (role_id) references sys_role (role_id)
            on delete cascade
)
    comment '角色和菜单关联表';

create table sys_user
(
    user_id     bigint auto_increment comment '用户ID',
    dept_id     bigint                    null comment '部门ID',
    user_name   varchar(30)               not null comment '用户账号',
    nick_name   varchar(30)               not null comment '用户昵称',
    user_type   varchar(2)   default '00' null comment '用户类型（00系统用户）',
    email       varchar(50)  default ''   null comment '用户邮箱',
    phonenumber varchar(11)  default ''   null comment '手机号码',
    sex         char         default '0'  null comment '用户性别（0男 1女 2未知）',
    avatar      varchar(100) default ''   null comment '头像地址',
    password    varchar(100) default ''   null comment '密码',
    status      char         default '0'  null comment '帐号状态（0正常 1停用）',
    del_flag    char         default '0'  null comment '删除标志（0代表存在 2代表删除）',
    login_ip    varchar(128) default ''   null comment '最后登录IP',
    login_date  datetime                  null comment '最后登录时间',
    create_by   varchar(64)  default ''   null comment '创建者',
    create_time datetime                  null comment '创建时间',
    update_by   varchar(64)  default ''   null comment '更新者',
    update_time datetime                  null comment '更新时间',
    remark      varchar(500)              null comment '备注',
    PRIMARY KEY (`user_id`) USING BTREE

)
    comment '用户信息表' collate = utf8mb4_unicode_ci;

create table mall_address
(
    id           int auto_increment comment '主键ID',
    user_id      bigint       null comment '用户ID',
    username     varchar(255) null comment '收货人',
    user_address varchar(255) null comment '收货地址',
    phone        varchar(255) null comment '联系电话',
    PRIMARY KEY (`id`) USING BTREE,
    constraint fk_address_user
        foreign key (user_id) references sys_user (user_id)
            on update cascade on delete set null
)
    comment '地址信息表' collate = utf8mb4_unicode_ci;

create table mall_cart
(
    id          int auto_increment comment '主键ID',
    user_id     bigint       null comment '用户ID',
    goods_id    bigint       null comment '商品ID',
    merchant_id varchar(255) null comment '店铺ID',
    num         int          null comment '数量',
    time        varchar(255) null comment '添加时间',
    PRIMARY KEY (`id`) USING BTREE,
    constraint fk_cart_goods
        foreign key (goods_id) references mall_books (book_id)
            on delete cascade,
    constraint fk_cart_user
        foreign key (user_id) references sys_user (user_id)
            on delete cascade
)
    comment '购物车表' collate = utf8mb4_unicode_ci;

create table mall_collect
(
    id          int auto_increment comment '商品ID',
    user_id     bigint       null comment '用户ID',
    goods_id    bigint       null comment '商品ID',
    merchant_id varchar(255) null comment '店铺ID',
    time        varchar(255) null comment '收藏时间',
    PRIMARY KEY (`id`) USING BTREE,
    constraint fk_collect_goods
        foreign key (goods_id) references mall_books (book_id)
            on delete cascade,
    constraint fk_collect_user
        foreign key (user_id) references sys_user (user_id)
            on delete cascade
)
    comment '收藏信息表' collate = utf8mb4_unicode_ci;

create table mall_comment
(
    id          int auto_increment comment 'ID',
    user_id     bigint       null comment '用户ID',
    goods_id    bigint       null comment '商品ID',
    merchant_id varchar(255) null comment '店铺ID',
    content     varchar(255) null comment '评论内容',
    scores      int          null comment '评分',
    time        varchar(255) null comment '评论时间',
    PRIMARY KEY (`id`) USING BTREE,
    constraint fk_comment_goods
        foreign key (goods_id) references mall_books (book_id)
            on delete set null,
    constraint fk_comment_user
        foreign key (user_id) references sys_user (user_id)
            on delete set null
)
    comment '评论信息表' collate = utf8mb4_unicode_ci;

create table mall_orders
(
    id          int auto_increment comment '主键ID',
    order_id    varchar(255)  null comment '订单ID',
    goods_id    bigint        null comment '商品ID',
    merchant_id varchar(255)  null comment '店铺ID',
    num         int           null comment '商品数量',
    user_id     bigint        null comment '用户ID',
    price       double(10, 2) null comment '订单价格',
    address_id  int           null comment '地址ID',
    status      varchar(255)  null comment '订单状态',
    time        varchar(255)  null comment '下单时间',
    PRIMARY KEY (`id`) USING BTREE,
    constraint fk_orders_address
        foreign key (address_id) references mall_address (id)
            on delete set null,
    constraint fk_orders_goods
        foreign key (goods_id) references mall_books (book_id)
            on delete set null,
    constraint fk_orders_user
        foreign key (user_id) references sys_user (user_id)
            on delete set null
)
    comment '订单信息表' collate = utf8mb4_unicode_ci;

create table mall_user_profiles
(
    user_id           bigint auto_increment comment '用户ID',
    user_avg_rating   decimal(5, 4) default 0.0000 not null comment '平均评分',
    user_rating_std   decimal(6, 4)                null comment '评分标准差',
    user_rating_count mediumint     default 0      not null comment '有效评分次数',
    user_tag1         varchar(50)                  null comment '一级兴趣标签',
    user_tag2         varchar(50)                  null comment '二级兴趣标签',
    user_tag3         varchar(50)                  null comment '三级兴趣标签',
    user_tag4         varchar(50)                  null comment '四级兴趣标签',
    user_tag5         varchar(50)                  null comment '五级兴趣标签',
    PRIMARY KEY (`user_id`) USING BTREE,
    constraint fk_profile_user
        foreign key (user_id) references sys_user (user_id)
            on delete cascade,
    constraint chk_rating_count
        check (`user_rating_count` <= 10000000),
    constraint chk_rating_range
        check (`user_avg_rating` between 0.0000 and 5.0000)
)
    comment '用户画像及评分数据表' collate = utf8mb4_unicode_ci;

create table sys_user_role
(
    user_id bigint not null comment '用户ID',
    role_id bigint not null comment '角色ID',
    primary key (user_id, role_id),
    constraint fk_userrole_role
        foreign key (role_id) references sys_role (role_id)
            on delete cascade,
    constraint fk_userrole_user
        foreign key (user_id) references sys_user (user_id)
            on delete cascade
)
    comment '用户和角色关联表';

