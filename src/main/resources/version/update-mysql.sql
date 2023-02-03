-- 2022-08-10
DROP TABLE IF EXISTS `jelly_export_rule`;
CREATE TABLE `jelly_export_rule`  (
    `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `code` varchar(30) NOT NULL COMMENT '编码',
    `name` varchar(100) NOT NULL COMMENT '名称',
    `data_source` bigint(20) NOT NULL COMMENT '数据源',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '导入规则';

DROP TABLE IF EXISTS `jelly_export_rule_detail`;
CREATE TABLE `jelly_export_rule_detail`  (
    `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
    `export_rule_id` bigint(20) NOT NULL COMMENT '导入规则id',
    `field` bigint(20) NOT NULL COMMENT '对应字段',
    `col` varchar(30) NOT NULL COMMENT '对应列',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '导入规则明细';

DROP TABLE IF EXISTS `jelly_export_data`;
CREATE TABLE `jelly_export_data`  (
    `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `config` text NOT NULL COMMENT '配置',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '导入数据';

-- 2022-08-15
ALTER TABLE jelly_door ADD COLUMN path bigint(20) DEFAULT NULL COMMENT '路径';

-- 2022-08-16
ALTER TABLE jelly_export_data ADD COLUMN user_id bigint(20) DEFAULT NULL COMMENT '用户id';

-- 2022-08-17
ALTER TABLE jelly_meta_function ADD COLUMN parent_id bigint(20) DEFAULT NULL COMMENT '父节点';

-- 2022-08-17
DROP TABLE IF EXISTS `jelly_export_dimension`;
CREATE TABLE `jelly_export_dimension`  (
    `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `user_id` bigint(20) NOT NULL COMMENT '用户id',
    `odm_source` bigint(20) NOT NULL COMMENT 'ODM表',
    `odm_code_field` bigint(20) NOT NULL COMMENT 'ODM表字段编码',
    `odm_name_field` bigint(20) NOT NULL COMMENT 'ODM表字段名称',
    `dim_source` bigint(20) NOT NULL COMMENT 'DIM表',
    `dim_code_field` bigint(20) NOT NULL COMMENT 'DIM表字段编码',
    `dim_name_field` bigint(20) NOT NULL COMMENT 'DIM表字段名称',
    `dim_year_field` bigint(20) DEFAULT NULL COMMENT 'DIM表字段年段',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '维度管理';

-- 2022-08-18
ALTER TABLE jelly_export_dimension ADD COLUMN name varchar(20) DEFAULT NULL COMMENT '维度名称';

-- 2022-08-22
DROP TABLE IF EXISTS `jelly_inform`;
CREATE TABLE `jelly_inform`  (
    `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `icon` varchar(30) NOT NULL COMMENT '图标',
    `color` varchar(30) NOT NULL COMMENT '颜色',
    `data_source` bigint(20) NOT NULL COMMENT '数据源',
    `template_source` text NOT NULL COMMENT '模板据源',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '报告模板';

-- 2022-08-22
ALTER TABLE jelly_export_data ADD COLUMN name varchar(120) DEFAULT NULL COMMENT '名称';


-- 2022-08-26
DROP TABLE IF EXISTS `jelly_procedure`;
CREATE TABLE `jelly_procedure`  (
    `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `remark` varchar(200) NOT NULL COMMENT '备注',
    `config` text COMMENT '配置',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '存储过程';

-- 2022-09-07
DROP TABLE IF EXISTS `jelly_open_api`;
CREATE TABLE `jelly_open_api`  (
    `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `appid` varchar(30) NOT NULL COMMENT 'appid',
    `secret` varchar(50) NOT NULL COMMENT 'secret',
    `remark` varchar(200) NOT NULL COMMENT '备注',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = 'openApi';

ALTER TABLE `jelly_inform` ADD COLUMN type int(4) NOT NULL COMMENT '类型(1:word;2:excel;)';
ALTER TABLE `jelly_inform` ADD COLUMN code varchar(30) NOT NULL COMMENT '编码';

ALTER TABLE `jelly_inform` DROP `color`;
ALTER TABLE `jelly_inform` DROP `icon`;

-- 2022-09-26
ALTER TABLE `sys_menu` ADD COLUMN quick_name varchar(30) DEFAULT NULL COMMENT '快捷方式名称';


-- 2022-10-18
ALTER TABLE jelly_export_rule_detail ADD COLUMN type int(2) DEFAULT 1 COMMENT '字段转换类型(1无;2字典;3用户;4部门;5唯一字段;6地址)';
ALTER TABLE jelly_export_rule_detail ADD COLUMN source bigint(20) DEFAULT NULL COMMENT '字段转换来源';
ALTER TABLE jelly_export_rule ADD COLUMN business_rule_id bigint(20) DEFAULT NULL COMMENT '业务校验规则id';

-- 2022-10-20
ALTER TABLE `sys_notice` ADD COLUMN `status`int DEFAULT '1' COMMENT '状态(0:暂存;1:已发布;)';
ALTER TABLE `sys_notice` ADD COLUMN `classify` varchar(30) DEFAULT '1' COMMENT '通知类型';
ALTER TABLE `sys_notice` ADD COLUMN `relation` TEXT DEFAULT NULL COMMENT '关联通知';

-- 2022-11-02
ALTER TABLE jelly_export_rule_detail ADD COLUMN sql_source varchar(500) DEFAULT NULL COMMENT 'sql语句';


-- 2022-11-25
ALTER TABLE jelly_export_rule ADD COLUMN sz_code varchar(20) DEFAULT NULL COMMENT '收支编码（收入01；支出02）';
ALTER TABLE jelly_export_rule ADD COLUMN sc_code varchar(20) DEFAULT NULL COMMENT '资金性质';

-- 2022-12-16
DROP TABLE IF EXISTS `jelly_import_rule`;
CREATE TABLE `jelly_import_rule`  (
    `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `code` varchar(30) NOT NULL COMMENT '编码',
    `name` varchar(100) NOT NULL COMMENT '名称',
    `data_source` bigint(20) NOT NULL COMMENT '数据源',
    `before_rule_id` bigint(20) COMMENT '导入前规则id',
    `verify_rule_id` bigint(20) COMMENT '校验规则id',
    `after_rule_id` bigint(20) COMMENT '导入后规则id',
    `template_source` text NOT NULL COMMENT '模板据源',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '导入规则';

DROP TABLE IF EXISTS `jelly_import_rule_detail`;
CREATE TABLE `jelly_import_rule_detail`  (
    `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
    `rule_id` bigint(20) NOT NULL COMMENT '导入规则id',
    `field` bigint(20) NOT NULL COMMENT '对应字段',
    `col` varchar(30) NOT NULL COMMENT '对应列',
    `rule` text COMMENT '规则',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '导入规则明细';

-- 2023-02-01
ALTER TABLE jelly_business_table change comment remark varchar(64) NOT NULL COMMENT '注释';
ALTER TABLE jelly_business_field change comment remark varchar(64) NOT NULL COMMENT '注释';
ALTER TABLE sea_definition change resource resources text DEFAULT NULL COMMENT '流程文件';
ALTER TABLE sea_instance change resource resources text DEFAULT NULL COMMENT '流程文件';
ALTER TABLE sea_node_detail change comment remark varchar(255) DEFAULT NULL COMMENT '评论';