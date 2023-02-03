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

DROP TABLE IF EXISTS `jelly_inform`;
CREATE TABLE `jelly_inform`  (
    `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `type` int(4) NOT NULL COMMENT '类型(1:word;2:excel;)',
    `code` varchar(30) NOT NULL COMMENT '编码',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `data_source` bigint(20) NOT NULL COMMENT '数据源',
    `template_source` text NOT NULL COMMENT '模板据源',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '报告模板';

DROP TABLE IF EXISTS `jelly_export_rule`;
CREATE TABLE `jelly_export_rule`  (
    `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `code` varchar(30) NOT NULL COMMENT '编码',
    `name` varchar(100) NOT NULL COMMENT '名称',
    `sz_code` varchar(20) NOT NULL COMMENT '收支编码（收入01；支出02）',
    `sc_code` varchar(20) NOT NULL COMMENT '资金性质',
    `data_source` bigint(20) NOT NULL COMMENT '数据源',
    `business_rule_id` bigint(20) DEFAULT NULL COMMENT '业务校验规则id',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '导入规则';

DROP TABLE IF EXISTS `jelly_export_rule_detail`;
CREATE TABLE `jelly_export_rule_detail`  (
    `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
    `export_rule_id` bigint(20) NOT NULL COMMENT '导入规则id',
    `field` bigint(20) NOT NULL COMMENT '对应字段',
    `col` varchar(30) NOT NULL COMMENT '对应列',
    `type` int(2) DEFAULT 1 COMMENT '字段转换类型(1无;2字典;3用户;4部门;5唯一字段;6地址)',
    `source` bigint(20) DEFAULT NULL COMMENT '字段转换来源',
    `sql_source` varchar(500) DEFAULT NULL COMMENT 'sql语句',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '导入规则明细';

DROP TABLE IF EXISTS `jelly_export_data`;
CREATE TABLE `jelly_export_data`  (
    `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(120) NOT NULL COMMENT '名称',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `user_id` bigint(20) NOT NULL COMMENT '用户id',
    `config` text NOT NULL COMMENT '配置',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '导入数据';

DROP TABLE IF EXISTS `jelly_export_dimension`;
CREATE TABLE `jelly_export_dimension`  (
    `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `user_id` bigint(20) NOT NULL COMMENT '用户id',
    `name` varchar(30) NOT NULL COMMENT '维度名称',
    `odm_source` bigint(20) NOT NULL COMMENT 'ODM表',
    `odm_code_field` bigint(20) NOT NULL COMMENT 'ODM表字段编码',
    `odm_name_field` bigint(20) NOT NULL COMMENT 'ODM表字段名称',
    `dim_source` bigint(20) NOT NULL COMMENT 'DIM表',
    `dim_code_field` bigint(20) NOT NULL COMMENT 'DIM表字段编码',
    `dim_name_field` bigint(20) NOT NULL COMMENT 'DIM表字段名称',
    `dim_year_field` bigint(20) NOT NULL COMMENT 'DIM表字段年段',
    `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '维度管理';

DROP TABLE IF EXISTS `jelly_form_design`;
CREATE TABLE `jelly_form_design`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `type` int(4) NOT NULL COMMENT '类型(1:简化版;2:高级版;)',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `excel_json` text DEFAULT NULL COMMENT 'excel配置',
    `data_source` varchar(255) NOT NULL COMMENT '数据源配置',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '表单设计';

DROP TABLE IF EXISTS `jelly_print`;
CREATE TABLE `jelly_print`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `excel_json` text DEFAULT NULL COMMENT 'excel配置',
    `data_source` varchar(255) NOT NULL COMMENT '数据源配置',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '打印模版';

DROP TABLE IF EXISTS `jelly_form`;
CREATE TABLE `jelly_form`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `design_ids` varchar(255) NOT NULL COMMENT '设计ids',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `icon` varchar(30) NOT NULL COMMENT '图标',
    `color` varchar(30) NOT NULL COMMENT '颜色',
    `data_source` text DEFAULT NULL COMMENT '数据源配置',
    `search_json` text DEFAULT NULL COMMENT '搜索配置',
    `table_header` bigint(20) NOT NULL COMMENT '表格表头',
    `list_export_path` text DEFAULT NULL COMMENT '列表导出文件路径',
    `detail_export_path` text DEFAULT NULL COMMENT '详情导出文件路径',
    `flow_id` bigint(20) DEFAULT NULL COMMENT '流程id',
    `insert_before_rule` bigint(20) DEFAULT NULL COMMENT '新增前规则',
    `insert_after_rule` bigint(20) DEFAULT NULL COMMENT '新增后规则',
    `update_before_rule` bigint(20) DEFAULT NULL COMMENT '更新前规则',
    `update_after_rule` bigint(20) DEFAULT NULL COMMENT '更新后规则',
    `delete_before_rule` bigint(20) DEFAULT NULL COMMENT '删除前规则',
    `delete_after_rule` bigint(20) DEFAULT NULL COMMENT '删除后规则',
    `process_end_rule` bigint(20) DEFAULT NULL COMMENT '流程结束后规则',
    `abandon_rule` bigint(20) DEFAULT NULL COMMENT '弃审规则',
	`export_rule` bigint(20) DEFAULT NULL COMMENT '导出规则',
    `options` text DEFAULT NULL COMMENT '其他参数json',
    `relate_search_json` text DEFAULT NULL COMMENT '联查json',
    `data_title` varchar(255) DEFAULT NULL COMMENT '数据标题',
    `authority` text DEFAULT NULL COMMENT '权限',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '表单管理';

DROP TABLE IF EXISTS `jelly_report`;
CREATE TABLE `jelly_report`  (
    `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `icon` varchar(30) NOT NULL COMMENT '图标',
    `color` varchar(30) NOT NULL COMMENT '颜色',
    `data_source` bigint(20) NOT NULL COMMENT '数据源',
    `template_source` text NOT NULL COMMENT '模板据源',
    `search_json` text DEFAULT NULL COMMENT '搜索配置',
	`export_path` text DEFAULT NULL COMMENT '导出路径',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '报表管理';

DROP TABLE IF EXISTS `jelly_data_sheet`;
CREATE TABLE `jelly_data_sheet`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `form_id` bigint(20) NOT NULL COMMENT '表单id',
    `single_flag` int(4) DEFAULT 1 COMMENT '是否单条(1:是;2:否;)',
    `table_name` varchar(50) NOT NULL COMMENT '数据表名',
    `sort` int(4) DEFAULT 1 COMMENT '排序',
    `relate_table` varchar(50) DEFAULT NULL COMMENT '关联表',
    `relate_field` varchar(50) DEFAULT NULL COMMENT '关联字段',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '数据表设置';

DROP TABLE IF EXISTS `jelly_table_classify`;
CREATE TABLE `jelly_table_classify`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '表头分类';


DROP TABLE IF EXISTS `jelly_table_column`;
CREATE TABLE `jelly_table_column` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `classify_id` bigint(20) NOT NULL COMMENT '分类id',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级id',
  `prop` varchar(30) NOT NULL COMMENT '字段名',
  `label` varchar(30) NOT NULL COMMENT '标题',
  `width` int(4) DEFAULT NULL COMMENT '宽度',
  `locking` int(4) DEFAULT 3 COMMENT '锁定(1:左;2:右;3:无)',
  `summary` int(4) DEFAULT 2 COMMENT '汇总(1:是;2:否;)',
  `total` int(4) DEFAULT 2 COMMENT '合计(1:是;2:否;)',
  `target` int(4) DEFAULT 0 COMMENT '格式对象(0:无;1:数据字典;2:区域数据;)',
  `formatter` bigint(20) DEFAULT NULL COMMENT '格式化',
  `options` text DEFAULT NULL COMMENT '格式化数据源',
  `sort` int(4) DEFAULT 1 COMMENT '排序',
  `formula` text DEFAULT NULL COMMENT '公式',
  `router_json` TEXT DEFAULT NULL COMMENT '路由json',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='表格表头';

DROP TABLE IF EXISTS `jelly_table_column_config`;
CREATE TABLE `jelly_table_column_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `table_column_id` bigint(20) NOT NULL COMMENT '表头id',
  `display` int(4) DEFAULT 1 COMMENT '显示(1:显示;2:隐藏;)',
  `sort` int(4) DEFAULT 1 COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='表头配置';

DROP TABLE IF EXISTS `jelly_business_table`;
CREATE TABLE `jelly_business_table`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `name` varchar(64) NOT NULL COMMENT '名称',
    `remark` varchar(64) NOT NULL COMMENT '注释',
    `is_virtual` int(4) DEFAULT 0 COMMENT '虚拟(1:是;0:否;)',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '业务表';

DROP TABLE IF EXISTS `jelly_business_field`;
CREATE TABLE `jelly_business_field`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `business_table_id` bigint(20) NOT NULL COMMENT '业务表id',
    `name` varchar(64) NOT NULL COMMENT '名称',
    `remark` varchar(64) NOT NULL COMMENT '注释',
    `type` varchar(20) NOT NULL COMMENT '类型',
    `kind` int(4) DEFAULT 1 COMMENT '种类(1:基本类型;2:数据字典;3:单位;4:部门;5:用户;6:省市区;)',
    `length` int(4) DEFAULT 0 COMMENT '长度',
    `decimals` int(4) DEFAULT 0 COMMENT '小数',
    `not_null` int(4) DEFAULT 0 COMMENT '不为空(0:否;1:是;)',
    `default_value` varchar(200) DEFAULT NULL COMMENT '默认值',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '业务字段';

DROP TABLE IF EXISTS `jelly_dic_classify`;
CREATE TABLE `jelly_dic_classify`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `sort` int(4) DEFAULT 1 COMMENT '排序',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '字典分类';

DROP TABLE IF EXISTS `jelly_dic_detail`;
CREATE TABLE `jelly_dic_detail`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `classify_id` bigint(20) NOT NULL COMMENT '字典分类id',
    `code` varchar(30) NOT NULL COMMENT '编码',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `sort` int(4) DEFAULT 1 COMMENT '排序',
    `status` int(4) DEFAULT 1 COMMENT '状态((0:禁用：1:启用)',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '字典详情';


DROP TABLE IF EXISTS `jelly_business_rule`;
CREATE TABLE `jelly_business_rule`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `script` text DEFAULT NULL COMMENT '规则脚本',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '业务规则';


DROP TABLE IF EXISTS `jelly_regions`;
CREATE TABLE `jelly_regions`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code` varchar(30) NOT NULL COMMENT '编码',
    `grade` int(4) DEFAULT 1 NOT NULL COMMENT '等级',
    `name` varchar(50) NOT NULL COMMENT '名称',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '区域数据';

DROP TABLE IF EXISTS `jelly_job`;
CREATE TABLE `jelly_job`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `cron` varchar(30) NOT NULL COMMENT '表达式',
    `script` text NOT NULL COMMENT '规则',
    `status` int(4) DEFAULT 0 COMMENT '状态(0:未启动;1:已启动;)',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '任务调度';

DROP TABLE IF EXISTS `jelly_gauge`;
CREATE TABLE `jelly_gauge` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `config` text DEFAULT NULL COMMENT '配置',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '仪表板';


DROP TABLE IF EXISTS `jelly_door`;
CREATE TABLE `jelly_door` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `config` text DEFAULT NULL COMMENT '配置',
	`authority` text DEFAULT NULL COMMENT '权限',
	`path` bigint(20) DEFAULT NULL COMMENT '路径',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '门户管理';


DROP TABLE IF EXISTS `jelly_disk`;
CREATE TABLE `jelly_disk`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `user_id` bigint(20) NOT NULL COMMENT '用户id',
    `parent_id` bigint(20) DEFAULT 0 COMMENT '上级id',
    `type` int(4) DEFAULT 1 COMMENT '类型(1:文件夹;2:图片;3:word;4:excel;5:ppt;6:pdf;7:压缩文件;8:txt;9:文档(富文本);10:视频;11:音乐;12:其他;13:文档(markdown);)',
    `name` varchar(225) NOT NULL COMMENT '名称',
    `path` text NOT NULL COMMENT '路径(根目录:/)',
    `link` varchar(225) DEFAULT NULL COMMENT '链接',
    `text` text DEFAULT NULL COMMENT '文本',
    `capacity` int(4) DEFAULT NULL COMMENT '大小',
    `status` int(4) DEFAULT 1 COMMENT '状态(1:正常;2;回收站;3:删除;)',
    `to_user_ids` text DEFAULT NULL COMMENT '分享模板用户ids',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '网盘';

DROP TABLE IF EXISTS `jelly_disk_recycle`;
CREATE TABLE `jelly_disk_recycle`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `disk_id` bigint(20) NOT NULL COMMENT '文件id',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '回收站';

DROP TABLE IF EXISTS `jelly_meta_page`;
CREATE TABLE `jelly_meta_page`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `path` varchar(30) NOT NULL COMMENT '路径',
    `html` longtext DEFAULT NULL COMMENT 'html脚本',
    `js` longtext DEFAULT NULL COMMENT 'js脚本',
    `css` longtext DEFAULT NULL COMMENT 'css脚本',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '元页面';


DROP TABLE IF EXISTS `jelly_meta_function`;
CREATE TABLE `jelly_meta_function`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `parent_id` bigint(20) DEFAULT NULL COMMENT '父节点',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `path` varchar(30) NOT NULL COMMENT '路径',
    `script` text DEFAULT NULL COMMENT '脚本',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '元函数';

DROP TABLE IF EXISTS `jelly_template_engine`;
CREATE TABLE `jelly_template_engine`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `path` varchar(30) NOT NULL COMMENT '路径',
    `script` text DEFAULT NULL COMMENT '脚本',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '模版引擎';

DROP TABLE IF EXISTS `jelly_file_chunk`;
CREATE TABLE `jelly_file_chunk`  (
   	`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   	`chunk_number` int DEFAULT NULL COMMENT '当前分片，从1开始',
   	`chunk_size` float DEFAULT NULL COMMENT '分片大小',
   	`current_chunk_size` float DEFAULT NULL COMMENT '当前分片大小',
   	`total_chunk` int DEFAULT NULL COMMENT '总分片数',
   	`identifier` varchar(45) DEFAULT NULL COMMENT '文件标识',
   	`file_name` varchar(255) DEFAULT NULL COMMENT '文件名',
   	`file_type` int DEFAULT NULL COMMENT '文件类型(1:minio;2:阿里云)',
   	`relative_path` varchar(255) DEFAULT NULL COMMENT '相对路径',
   	`part_e_tag` varchar(500) DEFAULT NULL COMMENT '分片信息',
	`create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   	`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   	PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '文件分片';

DROP TABLE IF EXISTS `sea_definition`;
CREATE TABLE `sea_definition`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `resources` text DEFAULT NULL comment '流程文件',
    `data_source` varchar(255) DEFAULT NULL COMMENT '数据源配置',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '流程定义';

DROP TABLE IF EXISTS `sea_instance`;
CREATE TABLE `sea_instance`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `user_id` bigint(20) NOT NULL COMMENT '用户id',
    `version` int(4) DEFAULT 1 COMMENT '版本',
    `name` varchar(50) NOT NULL COMMENT '名称',
    `business_type` varchar(50) NOT NULL COMMENT '业务类型',
    `business_key` varchar(50) NOT NULL COMMENT '业务key',
    `resources` text NOT NULL comment '流程文件',
    `status` int(4) DEFAULT 0 COMMENT '状态(0:待审;1:通过;2:驳回;3:撤回;4:关闭)',
    `start_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
    `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
    `current_agent` text DEFAULT NULL COMMENT '当前代办人',
    `return_number` int(4) DEFAULT 0 COMMENT '退回次数',
    `close_status` int(4) DEFAULT NUll COMMENT '关闭时流程状态',
    `other_json` TEXT DEFAULT NULL COMMENT '其它json值',
    `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '流程实例';

DROP TABLE IF EXISTS `sea_node`;
CREATE TABLE `sea_node`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `def_id` bigint(20) NOT NULL COMMENT '流程实例id',
    `version` int(4) DEFAULT 1 COMMENT '版本',
    `source` varchar(50) NOT NULL COMMENT '源节点',
    `target` varchar(50) NOT NULL COMMENT '目标节点',
    `name` varchar(50) NOT NULL COMMENT '名称',
    `type` int(4) DEFAULT 1 COMMENT '类型(1:审批;2:抄送;3:撤回;4:驳回;)',
    `status` int(4) DEFAULT 0 COMMENT '状态(0:待办;1:通过;2:不通过;3:作废;)',
    `start_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
    `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
    `is_concurrent` int(4) DEFAULT 0 COMMENT '并行网关(0:否;1:是;)',
    `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '流程节点';

DROP TABLE IF EXISTS `sea_node_detail`;
CREATE TABLE `sea_node_detail`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `node_id` bigint(20) NOT NULL COMMENT '流程节点id',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `assignee` varchar(30) NOT NULL COMMENT '签收人或被委托id',
    `status` int(4) DEFAULT 0 COMMENT '状态(0:待办;1:同意;2:不同意;3:已阅;4:撤回;5:重新发起;6:弃审;7:关闭;8:激活;)',
    `remark` varchar(255) DEFAULT NULL COMMENT '评论',
    `start_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
    `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
    `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '流程节点详情';


DROP TABLE IF EXISTS `sys_company`;
CREATE TABLE `sys_company`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `parent_id` bigint(20) DEFAULT NULL COMMENT '上级id',
    `mark` varchar(30) NOT NULL COMMENT '标识',
    `code` varchar(30) NOT NULL COMMENT '编码',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `alias` varchar(30) NOT NULL COMMENT '简称',
    `logo` varchar(100) NOT NULL COMMENT 'logo',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '公司';

DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `parent_id` bigint(20) DEFAULT NULL COMMENT '上级id',
    `code` varchar(30) NOT NULL COMMENT '编码',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `director` varchar(500) DEFAULT NULL COMMENT '直接主管',
    `charge_leader` varchar(500) DEFAULT NULL COMMENT '分管领导',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '部门';

DROP TABLE IF EXISTS `sys_user_relate`;
CREATE TABLE `sys_user_relate` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` bigint(20) NOT NULL COMMENT '用户id',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `department_id` bigint(20) DEFAULT NULL COMMENT '部门id',
    `role_ids` varchar(50) DEFAULT NULL COMMENT '角色ids',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '用户关联';

DROP TABLE IF EXISTS `sys_account`;
CREATE TABLE `sys_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `account` varchar(50) NOT NULL COMMENT '账号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(30) DEFAULT NULL COMMENT '手机号',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `sex` int(4) DEFAULT 1 COMMENT '性别(1:男;2:女;)',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `position` varchar(50) DEFAULT NULL COMMENT '职位',
  `status` int(4) DEFAULT 1 COMMENT '状态(1:启用;2:禁用;)',
  `type` int(4) DEFAULT 1 COMMENT '类型(1:普通成员;2:管理员;3:超级管理员;)',
  `openid` varchar(50) DEFAULT NULL COMMENT 'openid',
	`sort` int(4) DEFAULT 0 COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户';

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_id` bigint(20) NOT NULL COMMENT '公司id',
  `name` varchar(30) NOT NULL COMMENT '名称',
  `path` text NOT NULL COMMENT '菜单权限(以,隔开)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '系统角色';

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_id` bigint(20) NOT NULL COMMENT '公司id',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级id',
  `type` int(4) DEFAULT 1 COMMENT '类型(1:列表;2:按钮;3:新增页面;4:系统菜单;5:目录;6:仪表板;7:自定义页面;8:报表;9:单页面;)',
  `name` varchar(30) NOT NULL COMMENT '名称',
  `icon` varchar(30) NOT NULL COMMENT '图标',
  `path` varchar(50) DEFAULT NULL COMMENT '路径',
  `status` int(4) DEFAULT 1 COMMENT '状态(1:启用;2:禁用;)',
  `sort` int(4) DEFAULT 1 COMMENT '排序',
  `quick_name` varchar(30) DEFAULT NULL COMMENT '简称',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='菜单';

DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`company_id` bigint(20) NOT NULL COMMENT '公司id',
	`user_id` bigint(20) NOT NULL COMMENT '发送公告人id',
  `title` varchar(50) NOT NULL COMMENT '标题',
  `content` text DEFAULT NULL COMMENT '正文',
  `resources` text DEFAULT NULL COMMENT '附件json',
  `to_user_ids` text NOT NULL COMMENT '接收公告人ids',
  `status` int(4) DEFAULT 1 COMMENT '状态(0:暂存;1:已发布;)',
  `classify` varchar(30) DEFAULT '1' COMMENT '通知类型',
  `relation` text DEFAULT NULL COMMENT '关联通知',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='公告';

DROP TABLE IF EXISTS `sys_message`;
CREATE TABLE `sys_message`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `type` int(4) DEFAULT 1 COMMENT '类型(1:流程消息;2:公告通知;3:数据预警;4:暂存数据;9:其它消息;)',
    `from_user_id` bigint(20) NOT NULL COMMENT '用户id(来自)',
    `to_user_id` bigint(20) NOT NULL COMMENT '用户id(给谁)',
    `title` varchar(50) NOT NULL COMMENT '标题',
    `business_type` bigint(20) DEFAULT NUll COMMENT '业务类型',
    `business_key` bigint(20) DEFAULT NUll COMMENT '业务key',
    `status` int(4) DEFAULT 0 COMMENT '状态(0:未读;1:已读;)',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '消息表';

DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_id` bigint(20) NOT NULL COMMENT '公司id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `name` varchar(50) NOT NULL COMMENT '操作名称',
  `ip` varchar(50) NOT NULL COMMENT 'ip',
  `uri` varchar(50) NOT NULL COMMENT 'uri',
  `method` varchar(255) NOT NULL COMMENT '方法',
  `params` longtext DEFAULT NULL COMMENT '请求参数',
  `ua` varchar(500) DEFAULT NULL COMMENT '浏览器信息',
  `status` int(4) DEFAULT 1 COMMENT '状态(1:成功;2:失败;)',
  `cost_time` int(4) NOT NULL COMMENT '花费时间',
  `result` longtext DEFAULT NULL COMMENT '返回结果',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='操作日记';


DROP TABLE IF EXISTS `sys_theme`;
CREATE TABLE `sys_theme` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `color` varchar(30) NOT NULL COMMENT '颜色值',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户主题';

DROP TABLE IF EXISTS `jelly_common_words`;
CREATE TABLE `jelly_common_words`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
		`user_id` bigint(20) NOT NULL COMMENT '用户id',
    `name` varchar(50) NOT NULL COMMENT '名称',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '常用语';

DROP TABLE IF EXISTS `jelly_disk_share`;
CREATE TABLE `jelly_disk_share` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `disk_id` bigint(20) NOT NULL COMMENT '文件id',
    `user_id` bigint(20) NOT NULL COMMENT '所属用户id',
    `share_user_id` bigint(20) NOT NULL COMMENT '分享用户id',
    `share_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '分享时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci  COMMENT='他人分享';

BEGIN;
INSERT INTO sys_menu VALUES (1, 1, NULL, 5, '组织架构', 'el-icon-office-building', NULL, 1, 1, NULL, '2021-07-01 11:33:27', '2021-07-01 11:33:27');
INSERT INTO sys_menu VALUES (2, 1, 1, 4, '通讯录', 'el-icon-collection', 'contact', 1, 1, NULL, '2021-07-01 11:34:04', '2021-07-01 11:34:04');
INSERT INTO sys_menu VALUES (3, 1, 1, 4, '角色管理', 'el-icon-coin', 'roleManager', 1, 2, NULL, '2021-07-01 11:34:38', '2021-07-01 11:34:38');
INSERT INTO sys_company VALUES (1, NULL, 'default', '1001', '默认单位', '默认单位', 'avatar', '2021-06-29 09:22:42', '2021-06-29 09:22:42');
INSERT INTO sys_department VALUES (1, 1, NULL, '1001', '默认部门', NULL, NULL, '2021-07-01 11:30:43', '2021-07-01 11:30:43');
INSERT INTO sys_role VALUES (1, 1, '默认角色', '1,2,3', '2021-07-01 11:31:22.25', '2021-07-01 11:31:22');
INSERT INTO sys_account VALUES (1, NULL, 'fjctAdmin', NULL, NULL, '超级管理员', 1, '3bf784f6c81c39142ec0cb2327fc90cc', NULL, 1, 3, NULL, 0, '2021-07-01 11:51:02', '2021-07-01 11:51:02');
INSERT INTO sys_user_relate VALUES (1, 1, 1, 1, 1, '2021-06-29 09:23:33', '2021-06-29 09:23:33');
INSERT INTO sys_account VALUES (2, NULL, 'sysAdmin', NULL, NULL, '管理员', 1, '3bf784f6c81c39142ec0cb2327fc90cc', NULL, 1, 2, NULL, 0, '2021-07-01 11:51:02', '2021-07-01 11:51:02');
INSERT INTO sys_user_relate VALUES (2, 2, 1, 1, 1, '2021-06-29 09:23:33', '2021-06-29 09:23:33');
COMMIT;