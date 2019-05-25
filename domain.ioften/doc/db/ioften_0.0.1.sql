/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018-12-25 09:35:43                          */
/*==============================================================*/


drop table if exists adv_adv;

drop index idx_cms_article_sub_categoryid on cms_article;

drop index idx_cms_article_seriesid on cms_article;

drop index idx_cms_article_uid on cms_article;

drop table if exists cms_article;

drop index idx_cms_browse_aid on cms_browse;

drop index idx_cms_browse_uid on cms_browse;

drop table if exists cms_browse;

drop index idx_cms_category_parentid on cms_category;

drop table if exists cms_category;

drop index idx_cms_comment_articleid on cms_comment;

drop index idx_cms_comment_uid on cms_comment;

drop table if exists cms_comment;

drop index idx_cms_comment_enjoy_commentid on cms_comment_enjoy;

drop index idx_cms_comment_enjoy_uid on cms_comment_enjoy;

drop table if exists cms_comment_enjoy;

drop index idx_cms_favorite_articleId on cms_favorite;

drop index idx_cms_favorite_userId on cms_favorite;

drop table if exists cms_favorite;

drop table if exists cms_keyword;

drop index idx_cms_article_uid on cms_series;

drop table if exists cms_series;

drop index idx_cms_share_aid on cms_share;

drop index idx_cms_share_uid on cms_share;

drop table if exists cms_share;

drop index idx_crm_care_cared on crm_care;

drop index idx_crm_care_uid on crm_care;

drop table if exists crm_care;

drop index idx_crm_identity_phone on crm_identity;

drop table if exists crm_identity;

drop index idx_crm_invite_phone on crm_invite;

drop index idx_crm_invite_fuid on crm_invite;

drop table if exists crm_invite;

drop index idx_crm_mesg_uid on crm_mesg;

drop table if exists crm_mesg;

drop index idx_crm_invite_uid on crm_oauth;

drop table if exists crm_oauth;

drop index idx_crm_setting_uid_code on crm_setting;

drop table if exists crm_setting;

drop index idx_crm_shield_side on crm_shield;

drop index idx_crm_shield_uid on crm_shield;

drop table if exists crm_shield;

drop index idx_crm_user_phone on crm_user;

drop index idx_crm_user_invite on crm_user;

drop table if exists crm_user;

drop index idx_pay_flows_way on pay_flows;

drop index idx_pay_flows_uid on pay_flows;

drop table if exists pay_flows;

drop table if exists pay_rank;

drop table if exists pay_wallet;

drop table if exists pro_category;

drop table if exists pro_product;

drop index idx_sys_admin_account on sys_admin;

drop table if exists sys_admin;

drop index idx_sys_config_code on sys_config;

drop table if exists sys_config;

drop table if exists sys_issue;

/*==============================================================*/
/* Table: adv_adv                                               */
/*==============================================================*/
create table adv_adv
(
   id                   bigint unsigned not null  comment 'ID',
   title                varchar(64) default NULL  comment '标题',
   cover                varchar(2048) default NULL  comment '封面图片, 多张逗号分隔',
   seat                 tinyint unsigned not null default 1  comment '位置, BANNER:资讯推荐页轮播, STARTUP:闪屏广告, NEWS_LIST:资讯列表页, IMAGE_END:纯图文章图片查看结束后, ARTICLE_END:图文文章底部, VIDEO_PLAY_OVER:视频播放完毕后, MY_ADV_VIEW:我的页面广告',
   action               tinyint unsigned not null default 0  comment '动作类型, PAGE:落地页面, NEWS:站内资讯, GOODS:站内商品, APP_DOWN:下载APP',
   show_btn             bit not null default 0  comment '是否显示"查看详情"按钮',
   action_conf          varchar(4096) default NULL  comment '动作配置, 下载APP->Json格式, 其他为字符串',
   sequence             SMALLINT unsigned not null default 9999  comment '顺序, 默认: 9999',
   remark               varchar(255) default NULL  comment '备注',
   status               tinyint unsigned not null default 1  comment '状态, DISABLE:禁用; ENABLE:正常',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table adv_adv comment '广告表';

/*==============================================================*/
/* Table: cms_article                                           */
/*==============================================================*/
create table cms_article
(
   id                   bigint unsigned not null default 0  comment 'ID',
   uid                  bigint unsigned not null default 0  comment '作者(用户ID), 外键: crm_user.id',
   title                varchar(64) not null  comment '标题',
   tags                 varchar(2048) not null  comment '标签, 多个逗号分隔',
   model                tinyint unsigned not null default 0  comment '模式, 文章; 图片; 音频; 视频; 系列',
   author               varchar(32) not null  comment '作者',
   series_id            bigint unsigned not null default 0  comment '系列ID, 外键: cms_series.id',
   category_id          bigint unsigned not null default 0  comment '类目ID, 外键: cms_category.id',
   category_name        varchar(255) not null  comment '类目名称, 如: 企划,创意策划',
   sub_category_id      bigint unsigned not null default 0  comment '子类目ID, 外键: cms_category.id',
   sub_category_name    varchar(255) not null  comment '子类目名称, 如: 企划,创意策划',
   cover                varchar(2048) default NULL  comment '封面图片, 多张逗号分隔',
   content              text default NULL  comment '资讯内容, 描述',
   resource             text default NULL  comment '资源文件, 图片=[[''1.png'',''描述1''],[''2.png'',''描述2'']]; 媒体={''ext'':''swf'',''url'':''1.swf'',''times'':120, ''size'': 1024}',
   read_level           tinyint unsigned not null default 0  comment '阅读等级, N, VIP1, VIP2, .... 参考crm_user.level',
   issue_time           datetime not null  comment '发行时间, 默认当前时间',
   stock                bigint unsigned not null default 0  comment '服豆库存',
   gain_ratio           bigint unsigned not null default 0  comment '挖矿系数, 充币系数',
   stocked              bigint unsigned not null default 0  comment '充值累计库存',
   stock_status         tinyint unsigned not null default 0  comment '投放追投, 未投放; 投放中; 投放过',
   reward               bigint unsigned not null default 0  comment '打赏收益',
   rewards              int unsigned not null default 0  comment '打赏次数',
   marks                int unsigned not null default 0  comment '收藏次数',
   enjoys               int unsigned not null default 0  comment '点赞次数',
   enjoy_switch         bit not null default 0  comment '点赞挖坑, 关; 开',
   shares               int unsigned not null default 0  comment '分享次数',
   share_switch         bit not null default 0  comment '分享挖坑, 关; 开',
   browses              int unsigned not null default 0  comment '阅读次数',
   browse_switch        bit not null default 0  comment '阅读挖坑, 关; 开',
   comments             int unsigned not null default 0  comment '评论次数',
   comment_switch       bit not null default 0  comment '评论开关, 关; 开',
   audit                varchar(16) default NULL  comment '审批人',
   audit_id             bigint unsigned default NULL  comment '审批人ID',
   audit_time           datetime default NULL  comment '审批时间',
   audit_remark         varchar(1024) default NULL  comment '审批备注',
   status               tinyint unsigned not null default 0  comment '状态, DRAFT:草稿; CHECK:审核中; REFUSE:已拒绝; ISSUE:已发布; WAIT_ISSUE:待发布; OFFLINE:已下线; DELETE:已删除',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table cms_article comment '文章资讯表';

/*==============================================================*/
/* Index: idx_cms_article_uid                                   */
/*==============================================================*/
create index idx_cms_article_uid on cms_article
(
   uid
);

/*==============================================================*/
/* Index: idx_cms_article_seriesid                              */
/*==============================================================*/
create index idx_cms_article_seriesid on cms_article
(
   series_id
);

/*==============================================================*/
/* Index: idx_cms_article_sub_categoryid                        */
/*==============================================================*/
create index idx_cms_article_sub_categoryid on cms_article
(
   sub_category_id
);

/*==============================================================*/
/* Table: cms_browse                                            */
/*==============================================================*/
create table cms_browse
(
   id                   bigint unsigned not null default 0  comment 'ID',
   uid                  bigint unsigned not null default 0  comment '用户ID, 外键: crm_user.id',
   aid                  bigint unsigned not null default 0  comment '文章ID, 外键: cms_article.id',
   view                 bit not null default 0  comment '是否点赞, 0:否; 1:是',
   enjoy                bit not null default 0  comment '是否点赞, 0:否; 1:是',
   award                bigint unsigned not null default 0  comment '挖矿金额',
   view_award           bigint unsigned not null default 0  comment '阅读挖矿金额',
   enjoy_award          bigint unsigned not null default 0  comment '点赞挖矿金额',
   reward               bigint unsigned not null default 0  comment '打赏金额, 0表示未打赏',
   status               tinyint unsigned not null default 1  comment '状态, DISABLE:禁用; ENABLE:正常',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table cms_browse comment '浏览记录表';

/*==============================================================*/
/* Index: idx_cms_browse_uid                                    */
/*==============================================================*/
create index idx_cms_browse_uid on cms_browse
(
   uid
);

/*==============================================================*/
/* Index: idx_cms_browse_aid                                    */
/*==============================================================*/
create index idx_cms_browse_aid on cms_browse
(
   aid
);

/*==============================================================*/
/* Table: cms_category                                          */
/*==============================================================*/
create table cms_category
(
   id                   bigint unsigned not null  comment 'ID',
   name                 varchar(16) not null  comment '标签名称',
   level                tinyint unsigned not null default 1  comment '层级, 默认:1',
   root_id              bigint unsigned not null default 0  comment '根ID, 仅限level=1的ID, 外键: prd_categoryt.id, 默认:0',
   parent_id            bigint unsigned not null default 0  comment '父级ID, 外键: prd_categoryt.id, 默认:0',
   sequence             int unsigned not null default 9999  comment '顺序, 默认: 9999',
   remark               varchar(255) default NULL  comment '备注',
   status               tinyint unsigned not null default 1  comment '状态, DISABLE:禁用; ENABLE:正常',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table cms_category comment '分类表';

/*==============================================================*/
/* Index: idx_cms_category_parentid                             */
/*==============================================================*/
create index idx_cms_category_parentid on cms_category
(
   parent_id
);

/*==============================================================*/
/* Table: cms_comment                                           */
/*==============================================================*/
create table cms_comment
(
   id                   bigint unsigned not null default 0  comment 'ID',
   uid                  bigint unsigned not null default 0  comment '用户ID, 外键: crm_user.id',
   level                tinyint unsigned not null default 1  comment '层级, 默认:1',
   article_id           bigint unsigned not null default 0  comment '文章ID, 外键: cms_article.id',
   root_id              bigint unsigned not null default 0  comment '一级评论ID, 外键: cms_comment.id',
   parent_id            bigint unsigned not null default 0  comment '父级评论ID, 外键: cms_comment.id',
   content              varchar(1024) not null  comment '评论内容',
   enjoys               int unsigned not null default 0  comment '点赞次数',
   comments             int unsigned not null default 0  comment '评论次数',
   weight               int unsigned not null default 0  comment '权重',
   status               tinyint unsigned not null default 1  comment '状态, 1:正常;0 禁用',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table cms_comment comment '资讯评论表';

/*==============================================================*/
/* Index: idx_cms_comment_uid                                   */
/*==============================================================*/
create index idx_cms_comment_uid on cms_comment
(
   uid
);

/*==============================================================*/
/* Index: idx_cms_comment_articleid                             */
/*==============================================================*/
create index idx_cms_comment_articleid on cms_comment
(
   article_id
);

/*==============================================================*/
/* Table: cms_comment_enjoy                                     */
/*==============================================================*/
create table cms_comment_enjoy
(
   id                   bigint unsigned not null default 0  comment 'ID',
   uid                  bigint unsigned not null default 0  comment '用户ID, 外键: crm_user.id',
   comment_id           bigint unsigned not null default 0  comment '评论ID, 外键: cms_comment.id',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table cms_comment_enjoy comment '评论点赞记录表';

/*==============================================================*/
/* Index: idx_cms_comment_enjoy_uid                             */
/*==============================================================*/
create index idx_cms_comment_enjoy_uid on cms_comment_enjoy
(
   uid
);

/*==============================================================*/
/* Index: idx_cms_comment_enjoy_commentid                       */
/*==============================================================*/
create index idx_cms_comment_enjoy_commentid on cms_comment_enjoy
(
   comment_id
);

/*==============================================================*/
/* Table: cms_favorite                                          */
/*==============================================================*/
create table cms_favorite
(
   id                   bigint unsigned not null default 0  comment 'ID',
   uid                  bigint unsigned not null default 0  comment '用户ID, 外键: crm_user.id',
   aid                  bigint unsigned not null default 0  comment '文章ID, 外键: cms_article.id',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table cms_favorite comment '收藏记录表';

/*==============================================================*/
/* Index: idx_cms_favorite_userId                               */
/*==============================================================*/
create index idx_cms_favorite_userId on cms_favorite
(
   uid
);

/*==============================================================*/
/* Index: idx_cms_favorite_articleId                            */
/*==============================================================*/
create index idx_cms_favorite_articleId on cms_favorite
(
   aid
);

/*==============================================================*/
/* Table: cms_keyword                                           */
/*==============================================================*/
create table cms_keyword
(
   id                   bigint unsigned not null  comment 'ID',
   istop                bit not null default 0  comment '是否置顶',
   name                 varchar(32) not null  comment '标签名称',
   sequence             int unsigned not null default 999  comment '顺序, 默认: 999',
   remark               varchar(255) default NULL  comment '备注',
   status               tinyint unsigned not null default 1  comment '状态, 禁用; 正常(默认)',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table cms_keyword comment '关键词表';

/*==============================================================*/
/* Table: cms_series                                            */
/*==============================================================*/
create table cms_series
(
   id                   bigint unsigned not null default 0  comment 'ID',
   uid                  bigint unsigned not null default 0  comment '作者(用户ID), 外键: crm_user.id',
   title                varchar(64) not null  comment '标题',
   author               varchar(32) not null  comment '作者',
   cover                varchar(2048) default NULL  comment '封面图片, 多张逗号分隔',
   content              text default NULL  comment '资讯内容, 描述',
   status               tinyint unsigned not null default 0  comment '状态, 待审核; 审核中; 已拒绝; 已通过;',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table cms_series comment '资讯系列表';

/*==============================================================*/
/* Index: idx_cms_article_uid                                   */
/*==============================================================*/
create index idx_cms_article_uid on cms_series
(
   uid
);

/*==============================================================*/
/* Table: cms_share                                             */
/*==============================================================*/
create table cms_share
(
   id                   bigint unsigned not null default 0  comment 'ID',
   uid                  bigint unsigned not null default 0  comment '用户ID, 外键: crm_user.id',
   aid                  bigint unsigned not null default 0  comment '文章ID, 外键: cms_article.id',
   way                  tinyint unsigned not null default 0  comment '分享途径, 其他, 微博, 微信, QQ空间',
   award                bigint unsigned not null default 0  comment '挖矿金额',
   views                int unsigned not null default 0  comment '展示次数',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table cms_share comment '分享记录表';

/*==============================================================*/
/* Index: idx_cms_share_uid                                     */
/*==============================================================*/
create index idx_cms_share_uid on cms_share
(
   uid
);

/*==============================================================*/
/* Index: idx_cms_share_aid                                     */
/*==============================================================*/
create index idx_cms_share_aid on cms_share
(
   aid
);

/*==============================================================*/
/* Table: crm_care                                              */
/*==============================================================*/
create table crm_care
(
   id                   bigint unsigned not null default 0  comment 'ID',
   uid                  bigint unsigned not null default 0  comment '用户ID, 外键: crm_user.id',
   cared                bigint unsigned not null default 0  comment '被关注的用户ID, 外键: crm_user.id',
   status               tinyint unsigned not null default 1  comment '状态, 0:禁用(拉黑); 1:正常;',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table crm_care comment '关注表';

/*==============================================================*/
/* Index: idx_crm_care_uid                                      */
/*==============================================================*/
create index idx_crm_care_uid on crm_care
(
   uid
);

/*==============================================================*/
/* Index: idx_crm_care_cared                                    */
/*==============================================================*/
create index idx_crm_care_cared on crm_care
(
   cared
);

/*==============================================================*/
/* Table: crm_identity                                          */
/*==============================================================*/
create table crm_identity
(
   id                   bigint unsigned not null default 0  comment 'ID',
   uid                  bigint unsigned not null default 0  comment '用户ID, 外键: crm_user.id',
   type                 tinyint unsigned not null default 0  comment '类型, N:未知; PE:个人, EN:企业, EW:长见号',
   name                 varchar(32) not null  comment '实名',
   phone                varchar(11) not null  comment '手机',
   city                 varchar(16) default NULL  comment '城市',
   idcard               varchar(18) not null  comment '证件号',
   evidences            varchar(1024) default NULL  comment '证件号照, 多个逗号分隔',
   operator             varchar(16) default NULL  comment '运营者, 仅企业, 长见号',
   category_id          bigint unsigned default NULL  comment '类目ID, 外键: cms_category.id',
   category_name        varchar(16) default NULL  comment '类目名称, 如: 企划,创意策划',
   sub_category_id      bigint unsigned default NULL  comment '子类目ID, 外键: cms_category.id',
   sub_category_name    varchar(16) default NULL  comment '子类目名称, 如: 企划,创意策划',
   audit                varchar(16) default NULL  comment '审批人',
   audit_id             bigint unsigned default NULL  comment '审批人ID, 外键: sys_admin.id',
   audit_time           datetime default NULL  comment '审批时间',
   audit_remark         varchar(255) default NULL  comment '审批备注',
   status               tinyint unsigned not null default 1  comment '审核状态, 认证中, 已拒绝; 待完善; 已认证',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table crm_identity comment '认证表';

/*==============================================================*/
/* Index: idx_crm_identity_phone                                */
/*==============================================================*/
create index idx_crm_identity_phone on crm_identity
(
   phone
);

/*==============================================================*/
/* Table: crm_invite                                            */
/*==============================================================*/
create table crm_invite
(
   id                   bigint unsigned not null  comment 'ID',
   uid                  bigint unsigned default NULL  comment '被邀请用户ID, 外键: crm_user.id',
   fuid                 bigint unsigned not null  comment '发起邀请用户ID, 外键: crm_user.id',
   phone                varchar(11) not null  comment '被邀请用户的手机',
   award                bigint unsigned not null default 0  comment '挖矿金额',
   status               tinyint unsigned not null default 1  comment '状态, 1;仅登记; 3:已注册',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table crm_invite comment '用户邀请表';

/*==============================================================*/
/* Index: idx_crm_invite_fuid                                   */
/*==============================================================*/
create index idx_crm_invite_fuid on crm_invite
(
   fuid
);

/*==============================================================*/
/* Index: idx_crm_invite_phone                                  */
/*==============================================================*/
create index idx_crm_invite_phone on crm_invite
(
   phone
);

/*==============================================================*/
/* Table: crm_mesg                                              */
/*==============================================================*/
create table crm_mesg
(
   id                   bigint unsigned not null default 0  comment 'ID',
   uid                  bigint unsigned not null default 0  comment '用户ID, 外键: crm_user.id',
   type                 tinyint unsigned not null default 0  comment '类型, SYS:系统; ENJOY:获赞; FOCUS:关注',
   actor                bigint unsigned not null default 0  comment '参与者, 0:系统; 其他为用户ID, 外键: crm_user.id',
   target               bigint unsigned not null default 0  comment '关联对象, 如:0:未知,文章ID,订单ID,评论ID,用户ID',
   content              varchar(128) default NULL  comment '内容',
   times                int unsigned default NULL  comment '次数, 仅type=ENJOY',
   status               tinyint unsigned not null default 1  comment '状态, 未阅读, 已阅读',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table crm_mesg comment '系统消息表';

/*==============================================================*/
/* Index: idx_crm_mesg_uid                                      */
/*==============================================================*/
create index idx_crm_mesg_uid on crm_mesg
(
   uid
);

/*==============================================================*/
/* Table: crm_oauth                                             */
/*==============================================================*/
create table crm_oauth
(
   id                   bigint unsigned not null  comment 'ID',
   uid                  bigint unsigned not null default 0  comment '用户ID, 外键: crm_user.id',
   plant                tinyint unsigned not null default 0  comment '方式, 微信, 微博, 叮叮, 支付宝',
   token                varchar(32) default NULL  comment '通用授权ID',
   expire               datetime default NULL  comment '过期时间',
   oauth                varchar(128) default NULL  comment '授权信息',
   openid               varchar(32) default NULL  comment '三方授权ID',
   status               tinyint unsigned not null default 1  comment '状态, 0:禁用; 1:正常',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table crm_oauth comment '第三方授权表';

/*==============================================================*/
/* Index: idx_crm_invite_uid                                    */
/*==============================================================*/
create index idx_crm_invite_uid on crm_oauth
(
   uid
);

/*==============================================================*/
/* Table: crm_setting                                           */
/*==============================================================*/
create table crm_setting
(
   id                   bigint unsigned not null default 0  comment '主键ID',
   uid                  bigint unsigned not null default 0  comment '用户ID, 外键: crm_user.id',
   code                 varchar(16) not null default 'UN'  comment '代码, mySetting:我的设置; myTopic:我的分类(多个分类用逗号分隔)',
   value                text default NULL  comment '配置值',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table crm_setting comment '我的设置配置表';

/*==============================================================*/
/* Index: idx_crm_setting_uid_code                              */
/*==============================================================*/
create index idx_crm_setting_uid_code on crm_setting
(
   uid,
   code
);

/*==============================================================*/
/* Table: crm_shield                                            */
/*==============================================================*/
create table crm_shield
(
   id                   bigint unsigned not null default 0  comment 'ID',
   uid                  bigint unsigned not null default 0  comment '用户ID, 外键: crm_user.id',
   side                 bigint unsigned not null default 0  comment '对方ID, 外键: crm_user.id',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table crm_shield comment '屏蔽名单表';

/*==============================================================*/
/* Index: idx_crm_shield_uid                                    */
/*==============================================================*/
create index idx_crm_shield_uid on crm_shield
(
   uid
);

/*==============================================================*/
/* Index: idx_crm_shield_side                                   */
/*==============================================================*/
create index idx_crm_shield_side on crm_shield
(
   side
);

/*==============================================================*/
/* Table: crm_user                                              */
/*==============================================================*/
create table crm_user
(
   id                   bigint unsigned not null  comment 'ID',
   nick                 varchar(16) default NULL  comment '昵称',
   name                 varchar(32) default NULL  comment '实名',
   phone                varchar(11) not null  comment '手机',
   passwd               varchar(64) default NULL  comment '密码',
   pay_passwd           varchar(64) default NULL  comment '支付密码',
   sex                  tinyint unsigned not null default 0  comment '性别, N:未知; M:男士; F:女士',
   type                 tinyint unsigned not null default 0  comment '类型, N:未知; PE:个人, EN:企业, EW:长见号',
   level                tinyint unsigned not null default 0  comment '等级, N, VIP1, VIP2, ....',
   city                 varchar(16) default NULL  comment '城市',
   birthday             date default NULL  comment '生日',
   idcard               varchar(18) default NULL  comment '证件号',
   operator             varchar(16) default NULL  comment '运营者, 仅企业, 长见号',
   intro                varchar(255) default NULL  comment '简介',
   invite               varchar(6) default NULL  comment '邀请码',
   device               varchar(32) default NULL  comment '终端ID',
   category_id          bigint unsigned default NULL  comment '类目ID, 外键: cms_category.id',
   category_name        varchar(16) default NULL  comment '类目名称, 如: 企划,创意策划',
   sub_category_id      bigint unsigned default NULL  comment '子类目ID, 外键: cms_category.id',
   sub_category_name    varchar(16) default NULL  comment '子类目名称, 如: 企划,创意策划',
   remark               varchar(128) default NULL  comment '备注',
   status               tinyint unsigned not null default 1  comment '状态, 禁用; 正常',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table crm_user comment '用户表';

/*==============================================================*/
/* Index: idx_crm_user_invite                                   */
/*==============================================================*/
create index idx_crm_user_invite on crm_user
(
   invite
);

/*==============================================================*/
/* Index: idx_crm_user_phone                                    */
/*==============================================================*/
create index idx_crm_user_phone on crm_user
(
   phone
);

/*==============================================================*/
/* Table: pay_flows                                             */
/*==============================================================*/
create table pay_flows
(
   id                   bigint unsigned not null default 0  comment 'ID',
   uid                  bigint unsigned not null default 0  comment 'ID, 外键: crm_user.id, pay_wallet.id',
   way                  tinyint unsigned not null default 0  comment '用途: N:其他; READ:阅读; SHARE:分享; LIKES:点赞; INVITE:邀请; GIVEIN:获得打赏; GIVEOUT:打赏他人; ADVIN:广告收入; AWARDOUT:设置阅读奖励 SHOP:购物; FETCH:提币; RECHARGE:充值',
   amount               bigint not null default 0  comment '金额, 存储时将其小数点往后移8位, 用整数表示, 支持±',
   currency             tinyint not null default 0  comment '币种类型, N:其他, RMB, SDC, SDT',
   target               bigint default 0  comment '交易对象, 如文章ID,订单ID, 无交易对象默认:0',
   channel              tinyint default 0  comment '支付通道, N:其他; APP:站内; BANK:银联; BLOCK:区块; ALIPAY:支付宝; WXPAY:微信支付',
   out_flows            varchar(64) default NULL  comment '外部流水号',
   new_give             bigint unsigned not null default 0  comment '积分赠送部分结余',
   new_income           bigint unsigned not null default 0  comment '积分收益部分结余',
   new_balance          bigint unsigned not null default 0  comment '积分预存部分结余',
   remark               varchar(64)  comment '备注信息, 文章名, 用户名, 商品清单',
   status               tinyint unsigned not null default 1  comment '状态, 交易中; 交易失败; 交易取消; 交易成功',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table pay_flows comment '钱包帐户表';

/*==============================================================*/
/* Index: idx_pay_flows_uid                                     */
/*==============================================================*/
create index idx_pay_flows_uid on pay_flows
(
   uid
);

/*==============================================================*/
/* Index: idx_pay_flows_way                                     */
/*==============================================================*/
create index idx_pay_flows_way on pay_flows
(
   way
);

/*==============================================================*/
/* Table: pay_rank                                              */
/*==============================================================*/
create table pay_rank
(
   id                   bigint unsigned not null default 0  comment 'ID',
   no                   int unsigned not null default 99999999  comment '排名',
   uid                  bigint unsigned not null  comment '用户ID, 外键: crm_user.id',
   amount               bigint unsigned not null default 0  comment '累计收益',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)'
);

alter table pay_rank comment '资金排行榜表';

/*==============================================================*/
/* Table: pay_wallet                                            */
/*==============================================================*/
create table pay_wallet
(
   id                   bigint unsigned not null default 0  comment 'ID, 外键: crm_user.id',
   give                 bigint unsigned not null default 0  comment '积分赠送部',
   income               bigint unsigned not null default 0  comment '积分收益部分',
   balance              bigint unsigned not null default 0  comment '积分预存部分',
   total_income         bigint unsigned not null default 0  comment '累计积分收益, 范围: 0~1844 6744 0737.09551615',
   total_added_rmb      bigint unsigned not null default 0  comment '累计RMB充值, 范围: 0~1844 6744 0737.09551615',
   contribution         bigint default 0  comment '贡献值',
   block_addr           varchar(64) default NULL  comment '区块地址',
   status               tinyint unsigned not null default 1  comment '状态, 禁用; 正常',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table pay_wallet comment '钱包帐户表';

/*==============================================================*/
/* Table: pro_category                                          */
/*==============================================================*/
create table pro_category
(
   id                   bigint unsigned not null default 0  comment 'ID',
   name                 varchar(16) not null  comment '类目名称',
   level                tinyint unsigned not null default 1  comment '层级, 默认:1',
   root_id              bigint unsigned not null default 0  comment '根ID, 仅限level=1的ID',
   parent_id            bigint unsigned not null default 0  comment '父级ID, 外键:prd_categoryt.id, 默认:0',
   sequence             int unsigned not null default 999  comment '顺序, 默认: 999',
   remark               varchar(255)  comment '备注',
   status               tinyint unsigned not null default 1  comment '状态, 1:正常;0 禁用',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table pro_category comment '类目表';

/*==============================================================*/
/* Table: pro_product                                           */
/*==============================================================*/
create table pro_product
(
   id                   bigint unsigned not null default 0  comment 'ID',
   uid                  bigint unsigned not null  comment '用户ID, 外键: crm_user.id',
   category_id          bigint unsigned not null  comment '类目ID,外键:pro_category.id',
   name                 varchar(64)  comment '商品名称',
   tags                 varchar(1024)  comment '标签, 多个'''',''''分隔',
   rmb                  bigint unsigned not null default 0.00  comment 'RMB',
   price                bigint unsigned not null default 0.00  comment '单价',
   remark               text  comment '服务描述',
   status               tinyint unsigned not null default 1  comment '状态, 1:正常;0 禁用',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table pro_product comment '商品表';

/*==============================================================*/
/* Table: sys_admin                                             */
/*==============================================================*/
create table sys_admin
(
   id                   bigint unsigned not null  comment 'ID',
   name                 varchar(8) default NULL  comment '姓名',
   account              varchar(16) not null  comment '账号',
   passwd               varchar(64) not null  comment '密码',
   roles                varchar(255) default NULL  comment '角色, 多个用逗号分隔',
   phone                varchar(11) default NULL  comment '手机号',
   email                varchar(32) default NULL  comment '邮箱',
   status               tinyint unsigned not null default 1  comment '状态, 禁用; 正常(默认)',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table sys_admin comment '管理员表';

/*==============================================================*/
/* Index: idx_sys_admin_account                                 */
/*==============================================================*/
create index idx_sys_admin_account on sys_admin
(
   account
);

/*==============================================================*/
/* Table: sys_config                                            */
/*==============================================================*/
create table sys_config
(
   id                   bigint unsigned not null default 0  comment '主键ID',
   code                 varchar(16) not null  comment '代码, about:关于; community:社区',
   name                 varchar(32) not null  comment '名称',
   scope                tinyint unsigned not null default 0  comment '适用范围, all:全局; sys:系统; app:APP;',
   value                text  comment '配置值',
   remark               text  comment '描述',
   modifier             varchar(16) not null  comment '修改者',
   modifier_id          bigint unsigned not null  comment '修改者ID, 外键:sys_admin.id',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table sys_config comment '系统配置表';

/*==============================================================*/
/* Index: idx_sys_config_code                                   */
/*==============================================================*/
create index idx_sys_config_code on sys_config
(
   code
);

/*==============================================================*/
/* Table: sys_issue                                             */
/*==============================================================*/
create table sys_issue
(
   id                   bigint unsigned not null default 0  comment '主键ID',
   title                varchar(128) not null  comment '问题',
   answer               text not null  comment '答案',
   sequence             SMALLINT unsigned not null default 9999  comment '顺序, 默认: 9999',
   ctime                datetime not null  comment '创建时间',
   mtime                datetime not null  comment '修改时间',
   valid                bit not null default 1  comment '逻辑删除, 0:无效; 1:有效(默认)',
   primary key (id)
);

alter table sys_issue comment 'QA表';

