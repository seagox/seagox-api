DROP TABLE IF EXISTS `jelly_import_rule`;
CREATE TABLE `jelly_import_rule`  (
    `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `code` varchar(30) NOT NULL COMMENT '编码',
    `name` varchar(100) NOT NULL COMMENT '名称',
    `data_source` bigint(20) NOT NULL COMMENT '数据源',
    `verify_rule_id` bigint(20) COMMENT '校验规则id',
    `handle_rule_id` bigint(20) COMMENT '处理规则',
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
    `data_source` bigint(20) NOT NULL COMMENT '数据源',
    `template_source` text NOT NULL COMMENT '模板源',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '打印模版';;

DROP TABLE IF EXISTS `jelly_form`;
CREATE TABLE `jelly_form`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `design_id` bigint(20) NOT NULL COMMENT '设计id',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `icon` text NOT NULL COMMENT '图标',
    `color` varchar(30) NOT NULL COMMENT '颜色',
    `flow_id` bigint(20) DEFAULT NULL COMMENT '流程id',
    `data_source` text DEFAULT NULL COMMENT '数据源配置',
    `search_json` text DEFAULT NULL COMMENT '搜索配置',
    `table_header` text NOT NULL COMMENT '表格表头',
    `options` text DEFAULT NULL COMMENT '其他参数json',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '表单管理';

DROP TABLE IF EXISTS `jelly_report`;
CREATE TABLE `jelly_report`  (
    `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
    `company_id` bigint(20) NOT NULL COMMENT '公司id',
    `name` varchar(30) NOT NULL COMMENT '名称',
    `icon` text NOT NULL COMMENT '图标',
    `color` varchar(30) NOT NULL COMMENT '颜色',
    `data_source` bigint(20) NOT NULL COMMENT '数据源',
    `template_source` text NOT NULL COMMENT '模板据源',
    `search_json` text DEFAULT NULL COMMENT '搜索配置',
	`export_path` text DEFAULT NULL COMMENT '导出路径',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '报表管理';


DROP TABLE IF EXISTS `jelly_table_column_config`;
CREATE TABLE `jelly_table_column_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_id` bigint(20) NOT NULL COMMENT '公司id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `form_id` bigint(20) NOT NULL COMMENT '表单id',
  `config` text COMMENT '配置',
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
    `target_table_id` bigint(20) DEFAULT NULL COMMENT '目标模型',
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
    `status` int(4) DEFAULT 1 COMMENT '状态(0:禁用：1:启用)',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '字典详情';


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
    `rule_id` bigint(20) NOT NULL COMMENT '规则',
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
    `type` int(4) DEFAULT 1 COMMENT '类型(1:元函数;2:规则引擎;3:导入验证规则;4:导入处理规则;5:定时任务;6:打印规则;)',
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
  `icon` text NOT NULL COMMENT '图标',
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


BEGIN;
INSERT INTO sys_menu VALUES (1, 1, NULL, 5, '组织架构', '<svg t=\"1677030380655\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"2355\" width=\"16\" height=\"16\"><path d=\"M789.922409 305.422945h111.583022c30.756491 0 55.791511-24.958229 55.791512-55.649192V138.475371c0-30.690963-25.03502-55.649191-55.791512-55.649192h-111.583022c-30.756491 0-55.791511 24.958229-55.791512 55.649192v27.824083H559.31886c-15.418689 0-27.895756 12.458637-27.895755 27.825108v297.253364h-123.705825V380.079543c0-30.690963-25.03502-55.649191-55.791512-55.649191H120.191851c-30.769802 0-55.791511 24.958229-55.791512 55.649191v278.245957c0 30.690963 25.02171 55.649191 55.791512 55.649191h231.733917c30.756491 0 55.791511-24.958229 55.791512-55.649191V547.027117h123.705825v297.266674c0 15.366471 12.477067 27.825108 27.895755 27.825108h174.348217c0.155631 0 0.308189-0.009215 0.462796-0.011263v27.83637c0 30.690963 25.03502 55.649191 55.791512 55.649192h111.583023c30.756491 0 55.791511-24.958229 55.791511-55.649192V788.6446c0-30.690963-25.03502-55.649191-55.791511-55.649191h-111.583023c-30.756491 0-55.791511 24.958229-55.791512 55.649191v27.83637c-0.154607-0.003072-0.308189-0.011263-0.462796-0.011263H587.214616V547.027117h146.916281v32.933271c0 30.690963 25.03502 55.649191 55.791512 55.649192h111.583022c30.756491 0 55.791511-24.958229 55.791512-55.649192V468.662006c0-30.690963-25.03502-55.649191-55.791512-55.649191h-111.583022c-30.756491 0-55.791511 24.958229-55.791512 55.649191v22.71592H587.214616V221.94967h146.916281v27.824083c0 30.691986 25.03502 55.649191 55.791512 55.649192z m0-166.947574h111.583022l0.027645 111.298382H789.922409V138.475371zM120.191851 658.3255V380.079543h231.733917l0.027645 278.245957H120.191851z m669.730558 130.3191h111.583022l0.027645 111.298382H789.922409V788.6446z m0-319.982594h111.583022l0.027645 111.298382H789.922409V468.662006z\" fill=\"#000000\" p-id=\"2356\"></path></svg>', NULL, 1, 1, NULL, '2021-07-01 11:33:27', '2021-07-01 11:33:27');
INSERT INTO sys_menu VALUES (2, 1, 1, 4, '通讯录', '<svg t=\"1677046363544\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"10588\" width=\"16\" height=\"16\"><path d=\"M822.982642 62.633113h-578.423537c-35.221655 0-63.891468 28.656502-63.891468 63.891468v65.960741h-36.098101c-15.51391 0-28.067768 12.567168-28.067768 28.067768 0 15.5006 12.553858 28.067768 28.067768 28.067768h36.098101v139.829972h-36.098101c-15.51391 0-28.067768 12.567168-28.067768 28.067768 0 15.5006 12.553858 28.067768 28.067768 28.067768h36.098101v139.816661h-36.098101c-15.51391 0-28.067768 12.567168-28.067768 28.067768 0 15.5006 12.553858 28.067768 28.067768 28.067769h36.098101v139.829971h-36.098101c-15.51391 0-28.067768 12.567168-28.067768 28.067768 0 15.5006 12.553858 28.067768 28.067768 28.067769h36.098101v59.218455c0 35.234966 28.670837 63.891468 63.891468 63.891468h578.424561c35.221655 0 63.891468-28.656502 63.891468-63.891468V126.524581c-0.001024-35.234966-28.670837-63.891468-63.892492-63.891468zM236.80215 895.719455v-59.218455h50.159118c15.51391 0 28.067768-12.567168 28.067769-28.067769 0-15.499576-12.553858-28.067768-28.067769-28.067768h-50.159118V640.536516h50.159118c15.51391 0 28.067768-12.567168 28.067769-28.067768 0-15.499576-12.553858-28.067768-28.067769-28.067769h-50.159118V444.585342h50.159118c15.51391 0 28.067768-12.567168 28.067769-28.067768 0-15.499576-12.553858-28.067768-28.067769-28.067768h-50.159118V248.620858h50.159118c15.51391 0 28.067768-12.567168 28.067769-28.067768 0-15.5006-12.553858-28.067768-28.067769-28.067768h-50.159118V126.524581c0-4.275745 3.48121-7.756955 7.756955-7.756955h434.879934v784.708785H244.559105c-4.276769 0-7.756955-3.48121-7.756955-7.756956z m593.937447 0c0 4.275745-3.48121 7.756955-7.756955 7.756956h-87.40909V118.767626h87.40909c4.275745 0 7.756955 3.48121 7.756955 7.756955v769.194874z\" fill=\"#000000\" p-id=\"10589\"></path></svg>', 'contact', 1, 1, NULL, '2021-07-01 11:34:04', '2021-07-01 11:34:04');
INSERT INTO sys_menu VALUES (3, 1, 1, 4, '角色管理', '<svg t=\"1677030493356\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"2605\" width=\"16\" height=\"16\"><path d=\"M755.301774 722.134033L534.133329 611.604588l66.820804-97.226102a28.05753 28.05753 0 0 0 4.916697-15.844626V343.338442c0-101.186491-81.080455-180.464908-184.616762-180.464908-103.536308 0-184.616763 79.277393-184.616763 180.464908v155.195418a27.852752 27.852752 0 0 0 5.176764 16.199914l68.732398 96.651703L79.74838 721.914922a28.013502 28.013502 0 0 0-15.885581 25.241844v98.510055c0 15.462716 12.525189 27.973571 27.973571 27.973571h650.953526c15.462716 0 27.973571-12.511879 27.97357-27.973571v-98.510055c0.001024-10.599261-5.981538-20.296479-15.461692-25.022733z m-40.485449 95.559217H119.810965v-52.915417l244.867295-117.277873a27.920329 27.920329 0 0 0 14.997872-18.248708 27.808725 27.808725 0 0 0-4.289055-23.193051l-82.801607-116.45774v-146.262019c0-69.825907 56.521542-124.516742 128.668598-124.516742 72.148079 0 128.669621 54.690835 128.669621 124.516742v146.507751l-80.124147 116.567297a27.966404 27.966404 0 0 0-4.043323 22.837762c2.048795 7.83989 7.348425 14.424496 14.588318 18.029597l234.472811 117.168317v53.244084z\" fill=\"#000000\" p-id=\"2606\"></path><path d=\"M942.432176 653.128258L721.291375 542.597789l66.820804-97.226103a28.060601 28.060601 0 0 0 4.917722-15.844625V274.332667c0-101.186491-81.080455-180.464908-184.616763-180.464908-20.161326 0-39.912074 3.059369-58.706513 9.097221l17.100933 53.270706c13.276721-4.261411 27.264018-6.419761 41.60558-6.419761 72.147056 0 128.668597 54.690835 128.668597 124.516742v146.507751l-80.124146 116.567296a27.966404 27.966404 0 0 0-4.043323 22.837763c2.048795 7.840914 7.348425 14.424496 14.588318 18.029596l234.445166 117.168317v53.243061h-69.743997v55.948166h97.717568c15.461692 0 27.973571-12.511879 27.973571-27.973571v-98.510055c0-10.599261-5.983586-20.296479-15.462716-25.022733z\" fill=\"#000000\" p-id=\"2607\"></path></svg>', 'role', 1, 2, NULL, '2021-07-01 11:34:38', '2021-07-01 11:34:38');
INSERT INTO sys_company VALUES (1, NULL, 'seagox', '1001', '水母', '水母', 'avatar', '2021-06-29 09:22:42', '2021-06-29 09:22:42');
INSERT INTO sys_department VALUES (1, 1, NULL, '1001', '默认部门', NULL, NULL, '2021-07-01 11:30:43', '2021-07-01 11:30:43');
INSERT INTO sys_role VALUES (1, 1, '默认角色', '1,2,3', '2021-07-01 11:31:22.25', '2021-07-01 11:31:22');
INSERT INTO sys_account VALUES (1, NULL, 'superAdmin', NULL, NULL, '超级管理员', 1, 'e10adc3949ba59abbe56e057f20f883e', NULL, 1, 3, NULL, 0, '2021-07-01 11:51:02', '2021-07-01 11:51:02');
INSERT INTO sys_user_relate VALUES (1, 1, 1, 1, 1, '2021-06-29 09:23:33', '2021-06-29 09:23:33');
INSERT INTO sys_account VALUES (2, NULL, 'admin', NULL, NULL, '管理员', 1, 'e10adc3949ba59abbe56e057f20f883e', NULL, 1, 2, NULL, 0, '2021-07-01 11:51:02', '2021-07-01 11:51:02');
INSERT INTO sys_user_relate VALUES (2, 2, 1, 1, 1, '2021-06-29 09:23:33', '2021-06-29 09:23:33');
COMMIT;