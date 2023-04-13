DROP TABLE IF EXISTS "public"."jelly_import_rule";
CREATE TABLE "public"."jelly_import_rule"  (
    "id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    "company_id" BIGINT NOT NULL,
    "code" varchar(30) NOT NULL,
    "name" varchar(100) NOT NULL,
    "data_source" bigint NOT NULL,
    "verify_rule_id" bigint DEFAULT NULL,
    "handle_rule_id" bigint DEFAULT NULL,
    "template_source" text,
    "create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."jelly_import_rule"."id" IS '主键';
COMMENT ON COLUMN "public"."jelly_import_rule"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."jelly_import_rule"."code" IS '编码';
COMMENT ON COLUMN "public"."jelly_import_rule"."name" IS '名称';
COMMENT ON COLUMN "public"."jelly_import_rule"."data_source" IS '数据源';
COMMENT ON COLUMN "public"."jelly_import_rule"."verify_rule_id" IS '验证规则';
COMMENT ON COLUMN "public"."jelly_import_rule"."handle_rule_id" IS '处理规则';
COMMENT ON COLUMN "public"."jelly_import_rule"."template_source" IS '模板源';
COMMENT ON COLUMN "public"."jelly_import_rule"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."jelly_import_rule"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."jelly_import_rule" IS '导入规则';

DROP TABLE IF EXISTS "public"."jelly_import_rule_detail";
CREATE TABLE "public"."jelly_import_rule_detail"  (
    "id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    "rule_id" BIGINT NOT NULL,
    "field" BIGINT NOT NULL,
    "col" varchar(30) NOT NULL,
    "rule" text,
    "create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN "public"."jelly_import_rule_detail"."id" IS '主键';
COMMENT ON COLUMN "public"."jelly_import_rule_detail"."rule_id" IS '导入规则id';
COMMENT ON COLUMN "public"."jelly_import_rule_detail"."field" IS '对应字段';
COMMENT ON COLUMN "public"."jelly_import_rule_detail"."col" IS '对应列';
COMMENT ON COLUMN "public"."jelly_import_rule_detail"."rule" IS '规则';
COMMENT ON COLUMN "public"."jelly_import_rule_detail"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."jelly_import_rule_detail"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."jelly_import_rule_detail" IS '导入规则明细';


DROP TABLE IF EXISTS "public"."jelly_print";
CREATE TABLE "public"."jelly_print" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"name" VARCHAR(100) NOT NULL,
	"data_source" BIGINT NOT NULL,
  	"template_source" text NOT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."jelly_print"."id" IS '主键';
COMMENT ON COLUMN "public"."jelly_print"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."jelly_print"."name" IS '名称';
COMMENT ON COLUMN "public"."jelly_print"."data_source" IS '数据源';
COMMENT ON COLUMN "public"."jelly_print"."template_source" IS '模板源';
COMMENT ON COLUMN "public"."jelly_print"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."jelly_print"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."jelly_print" IS '打印模版';


DROP TABLE IF EXISTS "public"."jelly_form";
CREATE TABLE "public"."jelly_form" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"design" TEXT NOT NULL,
	"name" VARCHAR(100) NOT NULL,
	"icon" TEXT NOT NULL,
	"color" VARCHAR(30) NOT NULL,
	"flow_id" BIGINT,
	"data_source" TEXT,
	"search_json" TEXT,
	"table_header" TEXT,
	"options" TEXT,
	"data_title" VARCHAR(800),
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."jelly_form"."id" IS '主键';
COMMENT ON COLUMN "public"."jelly_form"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."jelly_form"."design" IS '设计';
COMMENT ON COLUMN "public"."jelly_form"."name" IS '名称';
COMMENT ON COLUMN "public"."jelly_form"."icon" IS '图标';
COMMENT ON COLUMN "public"."jelly_form"."color" IS '颜色';
COMMENT ON COLUMN "public"."jelly_form"."flow_id" IS '流程id';
COMMENT ON COLUMN "public"."jelly_form"."data_source" IS '数据源配置';
COMMENT ON COLUMN "public"."jelly_form"."search_json" IS '搜索配置';
COMMENT ON COLUMN "public"."jelly_form"."table_header" IS '表格表头';
COMMENT ON COLUMN "public"."jelly_form"."options" IS '其他参数';
COMMENT ON COLUMN "public"."jelly_form"."data_title" IS '数据标题';
COMMENT ON COLUMN "public"."jelly_form"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."jelly_form"."update_time" IS '更新时间';

COMMENT ON COLUMN "public"."jelly_form"."authority" IS '权限';
COMMENT ON TABLE "public"."jelly_form" IS '表单管理';

DROP TABLE IF EXISTS "public"."jelly_report";
CREATE TABLE "public"."jelly_report"  (
    "id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
    "name" VARCHAR(100) NOT NULL,
	"icon" TEXT NOT NULL,
	"color" VARCHAR(30) NOT NULL,
    "data_source" BIGINT NOT NULL,
    "template_source" TEXT NOT NULL,
    "search_json" TEXT DEFAULT NULL,
	"export_path" TEXT DEFAULT NULL,
    "create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."jelly_report"."id" IS '主键';
COMMENT ON COLUMN "public"."jelly_report"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."jelly_report"."name" IS '名称';
COMMENT ON COLUMN "public"."jelly_report"."icon" IS '图标';
COMMENT ON COLUMN "public"."jelly_report"."color" IS '颜色';
COMMENT ON COLUMN "public"."jelly_report"."data_source" IS '数据源';
COMMENT ON COLUMN "public"."jelly_report"."template_source" IS '模板源';
COMMENT ON COLUMN "public"."jelly_report"."search_json" IS '搜索配置';
COMMENT ON COLUMN "public"."jelly_report"."export_path" IS '导出路径';
COMMENT ON TABLE "public"."jelly_report" IS '报表管理';


DROP TABLE IF EXISTS "public"."jelly_table_column_config";
CREATE TABLE "public"."jelly_table_column_config" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"user_id" BIGINT NOT NULL,
	"form_id" BIGINT NOT NULL,
	"config" TEXT,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."jelly_table_column_config"."id" IS '主键';
COMMENT ON COLUMN "public"."jelly_table_column_config"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."jelly_table_column_config"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."jelly_table_column_config"."form_id" IS '表单id';
COMMENT ON COLUMN "public"."jelly_table_column_config"."config" IS '配置';
COMMENT ON COLUMN "public"."jelly_table_column_config"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."jelly_table_column_config"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."jelly_table_column_config" IS '表头配置';


DROP TABLE IF EXISTS "public"."jelly_business_table";
CREATE TABLE "public"."jelly_business_table" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"name" VARCHAR(200) NOT NULL,
	"remark" VARCHAR(200) NOT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."jelly_business_table"."id" IS '主键';
COMMENT ON COLUMN "public"."jelly_business_table"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."jelly_business_table"."name" IS '名称';
COMMENT ON COLUMN "public"."jelly_business_table"."remark" IS '注释';
COMMENT ON COLUMN "public"."jelly_business_table"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."jelly_business_table"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."jelly_business_table" IS '业务表';


DROP TABLE IF EXISTS "public"."jelly_business_field";
CREATE TABLE "public"."jelly_business_field" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"business_table_id" BIGINT NOT NULL,
	"name" VARCHAR(200) NOT NULL,
	"remark" VARCHAR(200) NOT NULL,
	"type" VARCHAR(60) NOT NULL,
	"kind" INTEGER DEFAULT 1,
	"length" INTEGER DEFAULT 0,
	"decimals" INTEGER DEFAULT 0,
	"not_null" INTEGER DEFAULT 0,
	"default_value" VARCHAR(200),
	"target_table_id" BIGINT DEFAULT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."jelly_business_field"."id" IS '主键';
COMMENT ON COLUMN "public"."jelly_business_field"."business_table_id" IS '业务表id';
COMMENT ON COLUMN "public"."jelly_business_field"."name" IS '名称';
COMMENT ON COLUMN "public"."jelly_business_field"."remark" IS '注释';
COMMENT ON COLUMN "public"."jelly_business_field"."type" IS '类型(1:部门;2:数字;3:日期;4:字符串;5:其他;)';
COMMENT ON COLUMN "public"."jelly_business_field"."kind" IS '种类(1:基本类型;2:数据字典;3:单位;4:部门;5:用户;6:省市区;)';
COMMENT ON COLUMN "public"."jelly_business_field"."length" IS '长度';
COMMENT ON COLUMN "public"."jelly_business_field"."decimals" IS '小数';
COMMENT ON COLUMN "public"."jelly_business_field"."not_null" IS '不为空(1:是;0:否;)';
COMMENT ON COLUMN "public"."jelly_business_field"."default_value" IS '默认值';
COMMENT ON COLUMN "public"."jelly_business_field"."target_table_id" IS '目标模型';
COMMENT ON COLUMN "public"."jelly_business_field"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."jelly_business_field"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."jelly_business_field" IS '业务字段';


DROP TABLE IF EXISTS "public"."jelly_dic_classify";
CREATE TABLE "public"."jelly_dic_classify" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"name" VARCHAR(100) NOT NULL,
	"sort" INTEGER DEFAULT 1,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."jelly_dic_classify"."id" IS '主键';
COMMENT ON COLUMN "public"."jelly_dic_classify"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."jelly_dic_classify"."name" IS '名称';
COMMENT ON COLUMN "public"."jelly_dic_classify"."sort" IS '排序';
COMMENT ON COLUMN "public"."jelly_dic_classify"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."jelly_dic_classify"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."jelly_dic_classify" IS '字典分类';


DROP TABLE IF EXISTS "public"."jelly_dic_detail";
CREATE TABLE "public"."jelly_dic_detail" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"classify_id" BIGINT NOT NULL,
	"code" VARCHAR(100) NOT NULL,
	"name" VARCHAR(100) NOT NULL,
	"sort" INTEGER DEFAULT 1,
	"status" INTEGER DEFAULT 1,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."jelly_dic_detail"."id" IS '主键';
COMMENT ON COLUMN "public"."jelly_dic_detail"."classify_id" IS '字典分类id';
COMMENT ON COLUMN "public"."jelly_dic_detail"."code" IS '编码';
COMMENT ON COLUMN "public"."jelly_dic_detail"."name" IS '名称';
COMMENT ON COLUMN "public"."jelly_dic_detail"."sort" IS '排序';
COMMENT ON COLUMN "public"."jelly_dic_detail"."status" IS '状态(0:禁用：1:启用)';
COMMENT ON COLUMN "public"."jelly_dic_detail"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."jelly_dic_detail"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."jelly_dic_detail" IS '字典详情';


DROP TABLE IF EXISTS "public"."jelly_regions";
CREATE TABLE "public"."jelly_regions" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"code" VARCHAR(100) NOT NULL,
	"grade" INTEGER NOT NULL,
	"name" VARCHAR(150) NOT NULL
);
COMMENT ON COLUMN "public"."jelly_regions"."id" IS '主键';
COMMENT ON COLUMN "public"."jelly_regions"."code" IS '编码';
COMMENT ON COLUMN "public"."jelly_regions"."grade" IS '等级';
COMMENT ON COLUMN "public"."jelly_regions"."name" IS '名称';
COMMENT ON TABLE "public"."jelly_regions" IS '区域数据';


DROP TABLE IF EXISTS "public"."jelly_job";
CREATE TABLE "public"."jelly_job" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"name" VARCHAR(100) NOT NULL,
	"cron" VARCHAR(100) NOT NULL,
	"rule_id" BIGINT NOT NULL,
	"status" INTEGER DEFAULT 0,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."jelly_job"."id" IS '主键';
COMMENT ON COLUMN "public"."jelly_job"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."jelly_job"."name" IS '名称';
COMMENT ON COLUMN "public"."jelly_job"."cron" IS '表达式';
COMMENT ON COLUMN "public"."jelly_job"."rule_id" IS '规则';
COMMENT ON COLUMN "public"."jelly_job"."status" IS '状态(0:未启动;1:已启动;)';
COMMENT ON COLUMN "public"."jelly_job"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."jelly_job"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."jelly_job" IS '任务调度';


DROP TABLE IF EXISTS "public"."jelly_gauge";
CREATE TABLE "public"."jelly_gauge" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"name" VARCHAR(100) NOT NULL,
	"config" TEXT,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."jelly_gauge"."id" IS '主键';
COMMENT ON COLUMN "public"."jelly_gauge"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."jelly_gauge"."name" IS '名称';
COMMENT ON COLUMN "public"."jelly_gauge"."config" IS '配置';
COMMENT ON COLUMN "public"."jelly_gauge"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."jelly_gauge"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."jelly_gauge" IS '仪表板';


DROP TABLE IF EXISTS "public"."jelly_door";
CREATE TABLE "public"."jelly_door" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"name" VARCHAR(100) NOT NULL,
	"config" TEXT,
	"authority" TEXT,
	"path" BIGINT,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."jelly_door"."id" IS '主键';
COMMENT ON COLUMN "public"."jelly_door"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."jelly_door"."name" IS '名称';
COMMENT ON COLUMN "public"."jelly_door"."config" IS '配置';
COMMENT ON COLUMN "public"."jelly_door"."authority" IS '权限';
COMMENT ON COLUMN "public"."jelly_door"."path" IS '路径';
COMMENT ON COLUMN "public"."jelly_door"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."jelly_door"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."jelly_door" IS '门户管理';


DROP TABLE IF EXISTS "public"."jelly_meta_page";
CREATE TABLE "public"."jelly_meta_page"  (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    "company_id" BIGINT NOT NULL,
	"name" VARCHAR(30) NOT NULL,
	"path" VARCHAR(30) NOT NULL,
	"html" TEXT DEFAULT NULL,
	"js" TEXT DEFAULT NULL,
	"css" TEXT DEFAULT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN "public"."jelly_meta_page"."id" IS '主键';
COMMENT ON COLUMN "public"."jelly_meta_page"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."jelly_meta_page"."name" IS '名称';
COMMENT ON COLUMN "public"."jelly_meta_page"."path" IS '路径';
COMMENT ON COLUMN "public"."jelly_meta_page"."html" IS 'html脚本';
COMMENT ON COLUMN "public"."jelly_meta_page"."js" IS 'js脚本';
COMMENT ON COLUMN "public"."jelly_meta_page"."css" IS 'css脚本';
COMMENT ON COLUMN "public"."jelly_meta_page"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."jelly_meta_page"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."jelly_meta_page" IS '元页面';


DROP TABLE IF EXISTS "public"."jelly_meta_function";
CREATE TABLE "public"."jelly_meta_function"  (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    "company_id" BIGINT NOT NULL,
    "type" INTEGER DEFAULT 1,
	"name" VARCHAR(30) NOT NULL,
	"path" VARCHAR(30) NOT NULL,
	"script" TEXT DEFAULT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN "public"."jelly_meta_function"."id" IS '主键';
COMMENT ON COLUMN "public"."jelly_meta_function"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."jelly_meta_function"."type" IS '类型(1:元函数;2:规则引擎;3:导入验证规则;4:导入处理规则;5:定时任务;6:打印规则;7:下载规则;)';
COMMENT ON COLUMN "public"."jelly_meta_function"."name" IS '名称';
COMMENT ON COLUMN "public"."jelly_meta_function"."path" IS '路径';
COMMENT ON COLUMN "public"."jelly_meta_function"."script" IS '脚本';
COMMENT ON COLUMN "public"."jelly_meta_function"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."jelly_meta_function"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."jelly_meta_function" IS '元函数';


DROP TABLE IF EXISTS "public"."jelly_template_engine";
CREATE TABLE "public"."jelly_template_engine"  (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    "company_id" BIGINT NOT NULL,
	"name" VARCHAR(30) NOT NULL,
	"path" VARCHAR(30) NOT NULL,
	"script" TEXT DEFAULT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN "public"."jelly_template_engine"."id" IS '主键';
COMMENT ON COLUMN "public"."jelly_template_engine"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."jelly_template_engine"."name" IS '名称';
COMMENT ON COLUMN "public"."jelly_template_engine"."path" IS '路径';
COMMENT ON COLUMN "public"."jelly_template_engine"."script" IS '脚本';
COMMENT ON COLUMN "public"."jelly_template_engine"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."jelly_template_engine"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."jelly_template_engine" IS '模版引擎';

DROP TABLE IF EXISTS "public"."sea_definition";
CREATE TABLE "public"."sea_definition"  (
    "id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"name" VARCHAR(30) NOT NULL,
    "resources" TEXT DEFAULT NULL,
    "data_source" VARCHAR(255) DEFAULT NULL,
    "create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN "public"."sea_definition"."id" IS '主键';
COMMENT ON COLUMN "public"."sea_definition"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."sea_definition"."name" IS '名称';
COMMENT ON COLUMN "public"."sea_definition"."resources" IS '流程文件';
COMMENT ON COLUMN "public"."sea_definition"."data_source" IS '数据源配置';
COMMENT ON COLUMN "public"."sea_definition"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sea_definition"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."sea_definition" IS '流程定义';

DROP TABLE IF EXISTS "public"."sea_instance";
CREATE TABLE "public"."sea_instance" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"user_id" BIGINT NOT NULL,
	"version" INTEGER DEFAULT 1,
	"name" VARCHAR(150) NOT NULL,
	"business_type" VARCHAR(150) NOT NULL,
	"business_key" VARCHAR(150) NOT NULL,
	"resources" TEXT NOT NULL,
	"status" INTEGER DEFAULT 0,
	"start_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"end_time" TIMESTAMP,
	"current_agent" TEXT,
	"close_status" INTEGER DEFAULT NULL,
	"other_json" TEXT DEFAULT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"return_number" INTEGER DEFAULT 0
);
COMMENT ON COLUMN "public"."sea_instance"."id" IS '主键';
COMMENT ON COLUMN "public"."sea_instance"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."sea_instance"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."sea_instance"."version" IS '版本';
COMMENT ON COLUMN "public"."sea_instance"."name" IS '名称';
COMMENT ON COLUMN "public"."sea_instance"."business_type" IS '业务类型';
COMMENT ON COLUMN "public"."sea_instance"."business_key" IS '业务key';
COMMENT ON COLUMN "public"."sea_instance"."resources" IS '流程文件';
COMMENT ON COLUMN "public"."sea_instance"."status" IS '状态(0:待审;1:通过;2:驳回;3:撤回;4:关闭)';
COMMENT ON COLUMN "public"."sea_instance"."start_time" IS '开始时间';
COMMENT ON COLUMN "public"."sea_instance"."end_time" IS '结束时间';
COMMENT ON COLUMN "public"."sea_instance"."current_agent" IS '当前代办人';
COMMENT ON COLUMN "public"."sea_instance"."close_status" IS '关闭时流程状态';
COMMENT ON COLUMN "public"."sea_instance"."other_json" IS '其它json值';
COMMENT ON COLUMN "public"."sea_instance"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sea_instance"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."sea_instance"."return_number" IS '退回次数';
COMMENT ON TABLE "public"."sea_instance" IS '流程实例';


DROP TABLE IF EXISTS "public"."sea_node";
CREATE TABLE "public"."sea_node" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"def_id" BIGINT NOT NULL,
	"version" INTEGER DEFAULT 1,
	"soure" VARCHAR(150) NOT NULL,
	"target" VARCHAR(150) NOT NULL,
	"name" VARCHAR(150) NOT NULL,
	"type" INTEGER DEFAULT 1,
	"status" INTEGER DEFAULT 0,
	"start_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"end_time" TIMESTAMP,
	"is_concurrent" INTEGER DEFAULT 0,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."sea_node"."id" IS '主键';
COMMENT ON COLUMN "public"."sea_node"."def_id" IS '流程实例id';
COMMENT ON COLUMN "public"."sea_node"."version" IS '版本';
COMMENT ON COLUMN "public"."sea_node"."soure" IS '源节点';
COMMENT ON COLUMN "public"."sea_node"."target" IS '目标节点';
COMMENT ON COLUMN "public"."sea_node"."name" IS '名称';
COMMENT ON COLUMN "public"."sea_node"."type" IS '类型(1:审批;2:抄送;3:撤回;4:驳回;5:重新发起;)';
COMMENT ON COLUMN "public"."sea_node"."status" IS '状态(0:待办;1:通过;2:不通过;3:作废;)';
COMMENT ON COLUMN "public"."sea_node"."start_time" IS '开始时间';
COMMENT ON COLUMN "public"."sea_node"."end_time" IS '结束时间';
COMMENT ON COLUMN "public"."sea_node"."is_concurrent" IS '并行网关(0:否;1:是;)';
COMMENT ON COLUMN "public"."sea_node"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sea_node"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."sea_node" IS '流程节点';


DROP TABLE IF EXISTS "public"."sea_node_detail";
CREATE TABLE "public"."sea_node_detail" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"node_id" BIGINT NOT NULL,
	"name" VARCHAR(100) NOT NULL,
	"assignee" VARCHAR(100) NOT NULL,
	"status" INTEGER DEFAULT 0,
	"remark" VARCHAR(800),
	"start_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"end_time" TIMESTAMP,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."sea_node_detail"."id" IS '主键';
COMMENT ON COLUMN "public"."sea_node_detail"."node_id" IS '流程节点id';
COMMENT ON COLUMN "public"."sea_node_detail"."name" IS '名称';
COMMENT ON COLUMN "public"."sea_node_detail"."assignee" IS '签收人或被委托id';
COMMENT ON COLUMN "public"."sea_node_detail"."status" IS '状态(0:待办;1:同意;2:不同意;3:已阅;4:撤回;5:重新发起;6:弃审;)';
COMMENT ON COLUMN "public"."sea_node_detail"."remark" IS '评论';
COMMENT ON COLUMN "public"."sea_node_detail"."start_time" IS '开始时间';
COMMENT ON COLUMN "public"."sea_node_detail"."end_time" IS '结束时间';
COMMENT ON COLUMN "public"."sea_node_detail"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sea_node_detail"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."sea_node_detail" IS '流程节点详情';


DROP TABLE IF EXISTS "public"."sys_company";
CREATE TABLE "public"."sys_company" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"parent_id" BIGINT,
	"mark" VARCHAR(100) NOT NULL,
	"code" VARCHAR(100) NOT NULL,
	"name" VARCHAR(100) NOT NULL,
	"alias" VARCHAR(100) NOT NULL,
	"logo" VARCHAR(300) NOT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."sys_company"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_company"."parent_id" IS '上级id';
COMMENT ON COLUMN "public"."sys_company"."mark" IS '标识';
COMMENT ON COLUMN "public"."sys_company"."code" IS '编码';
COMMENT ON COLUMN "public"."sys_company"."name" IS '名称';
COMMENT ON COLUMN "public"."sys_company"."alias" IS '简称';
COMMENT ON COLUMN "public"."sys_company"."logo" IS 'logo';
COMMENT ON COLUMN "public"."sys_company"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_company"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."sys_company" IS '公司';


DROP TABLE IF EXISTS "public"."sys_department";
CREATE TABLE "public"."sys_department" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"parent_id" BIGINT,
	"code" VARCHAR(100) NOT NULL,
	"name" VARCHAR(100) NOT NULL,
	"director" VARCHAR(1500),
	"charge_leader" VARCHAR(1500),
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."sys_department"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_department"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."sys_department"."parent_id" IS '上级id';
COMMENT ON COLUMN "public"."sys_department"."code" IS '编码';
COMMENT ON COLUMN "public"."sys_department"."name" IS '名称';
COMMENT ON COLUMN "public"."sys_department"."director" IS '直接主管';
COMMENT ON COLUMN "public"."sys_department"."charge_leader" IS '分管领导';
COMMENT ON COLUMN "public"."sys_department"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_department"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."sys_department" IS '部门';


DROP TABLE IF EXISTS "public"."sys_user_relate";
CREATE TABLE "public"."sys_user_relate" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"user_id" BIGINT NOT NULL,
	"company_id" BIGINT NOT NULL,
	"department_id" BIGINT,
	"role_ids" VARCHAR(150),
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."sys_user_relate"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_user_relate"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."sys_user_relate"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."sys_user_relate"."department_id" IS '部门id';
COMMENT ON COLUMN "public"."sys_user_relate"."role_ids" IS '角色ids';
COMMENT ON COLUMN "public"."sys_user_relate"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_user_relate"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."sys_user_relate" IS '用户关联';


DROP TABLE IF EXISTS "public"."sys_account";
CREATE TABLE "public"."sys_account" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"avatar" VARCHAR(800) DEFAULT NULL,
	"account" VARCHAR(150) NOT NULL,
	"email" VARCHAR(150),
	"phone" VARCHAR(100),
	"name" VARCHAR(150) NOT NULL,
	"sex" INTEGER DEFAULT 1,
	"password" VARCHAR(800) NOT NULL,
	"position" VARCHAR(150),
	"status" INTEGER DEFAULT 1 NOT NULL,
	"type" INTEGER DEFAULT 1,
	"openid" VARCHAR(100),
	"sort" INTEGER DEFAULT 0,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."sys_account"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_account"."avatar" IS '头像';
COMMENT ON COLUMN "public"."sys_account"."account" IS '账号';
COMMENT ON COLUMN "public"."sys_account"."name" IS '姓名';
COMMENT ON COLUMN "public"."sys_account"."sex" IS '性别(1:男;2:女;)';
COMMENT ON COLUMN "public"."sys_account"."password" IS '密码';
COMMENT ON COLUMN "public"."sys_account"."position" IS '职位';
COMMENT ON COLUMN "public"."sys_account"."status" IS '状态(1:启用;2:禁用;)';
COMMENT ON COLUMN "public"."sys_account"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_account"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."sys_account"."type" IS '类型(1:普通成员;2:管理员;3:超级管理员;)';
COMMENT ON COLUMN "public"."sys_account"."email" IS '邮箱';
COMMENT ON COLUMN "public"."sys_account"."phone" IS '手机号';
COMMENT ON COLUMN "public"."sys_account"."openid" IS 'openid';
COMMENT ON COLUMN "public"."sys_account"."sort" IS '排序';
COMMENT ON TABLE "public"."sys_account" IS '用户';


DROP TABLE IF EXISTS "public"."sys_role";
CREATE TABLE "public"."sys_role" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"name" VARCHAR(100) NOT NULL,
	"path" TEXT NOT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."sys_role"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_role"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."sys_role"."name" IS '名称';
COMMENT ON COLUMN "public"."sys_role"."path" IS '菜单权限(以,隔开)';
COMMENT ON COLUMN "public"."sys_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_role"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."sys_role" IS '系统角色';


DROP TABLE IF EXISTS "public"."sys_menu";
CREATE TABLE "public"."sys_menu" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"parent_id" BIGINT,
	"type" INTEGER DEFAULT 1,
	"name" VARCHAR(100) NOT NULL,
	"icon" TEXT NOT NULL,
	"path" VARCHAR(150),
	"status" INTEGER DEFAULT 1,
	"sort" INTEGER DEFAULT 1,
	"quick_name" VARCHAR(30) DEFAULT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."sys_menu"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_menu"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."sys_menu"."parent_id" IS '上级id';
COMMENT ON COLUMN "public"."sys_menu"."type" IS '类型(1:列表;2:按钮;3:新增页面;4:系统菜单;5:目录;6:仪表板;7:自定义页面;8:报表;9:单页面;)';
COMMENT ON COLUMN "public"."sys_menu"."name" IS '名称';
COMMENT ON COLUMN "public"."sys_menu"."icon" IS '图标';
COMMENT ON COLUMN "public"."sys_menu"."path" IS '路径';
COMMENT ON COLUMN "public"."sys_menu"."status" IS '状态(1:启用;2:禁用;)';
COMMENT ON COLUMN "public"."sys_menu"."sort" IS '排序';
COMMENT ON COLUMN "public"."sys_menu"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_menu"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."sys_menu"."quick_name" IS '简称';
COMMENT ON TABLE "public"."sys_menu" IS '菜单';


DROP TABLE IF EXISTS "public"."sys_notice";
CREATE TABLE "public"."sys_notice" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"user_id" BIGINT NOT NULL,
	"title" VARCHAR(150) NOT NULL,
	"content" TEXT,
	"resources" TEXT,
	"to_user_ids" VARCHAR(1500) NOT NULL,
    "status" INTEGER DEFAULT 1,
    "classify" varchar(30) DEFAULT '1',
    "relation" TEXT,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."sys_notice"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_notice"."title" IS '标题';
COMMENT ON COLUMN "public"."sys_notice"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."sys_notice"."user_id" IS '发送公告人id';
COMMENT ON COLUMN "public"."sys_notice"."content" IS '正文';
COMMENT ON COLUMN "public"."sys_notice"."resources" IS '附件json';
COMMENT ON COLUMN "public"."sys_notice"."to_user_ids" IS '接收公告人ids';
COMMENT ON COLUMN "public"."sys_notice"."status" IS '状态(0:暂存;1:已发布;)';
COMMENT ON COLUMN "public"."sys_notice"."classify" IS '通知类型';
COMMENT ON COLUMN "public"."sys_notice"."relation" IS '关联通知';
COMMENT ON COLUMN "public"."sys_notice"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_notice"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."sys_notice" IS '公告';


DROP TABLE IF EXISTS "public"."sys_message";
CREATE TABLE "public"."sys_message" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"type" INTEGER DEFAULT 1,
	"from_user_id" BIGINT NOT NULL,
	"to_user_id" BIGINT NOT NULL,
	"title" VARCHAR(150) NOT NULL,
	"business_type" BIGINT NOT NULL,
	"business_key" BIGINT NOT NULL,
	"status" INTEGER DEFAULT 0,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."sys_message"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_message"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."sys_message"."type" IS '类型(1:流程消息;2:公告通知;3:数据预警;4:暂存数据;9:其它消息;)';
COMMENT ON COLUMN "public"."sys_message"."from_user_id" IS '用户id(来自)';
COMMENT ON COLUMN "public"."sys_message"."to_user_id" IS '用户id(给谁)';
COMMENT ON COLUMN "public"."sys_message"."title" IS '标题';
COMMENT ON COLUMN "public"."sys_message"."business_type" IS '业务类型';
COMMENT ON COLUMN "public"."sys_message"."business_key" IS '业务key';
COMMENT ON COLUMN "public"."sys_message"."status" IS '状态(0:未读;1:已读;)';
COMMENT ON COLUMN "public"."sys_message"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_message"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."sys_message" IS '消息表';


DROP TABLE IF EXISTS "public"."sys_log";
CREATE TABLE "public"."sys_log" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"user_id" BIGINT NOT NULL,
	"name" VARCHAR(150) NOT NULL,
	"ip" VARCHAR(150) NOT NULL,
	"uri" VARCHAR(150) NOT NULL,
	"method" VARCHAR(800) NOT NULL,
	"params" TEXT,
	"status" INTEGER DEFAULT 1,
	"cost_time" INTEGER NOT NULL,
	"result" TEXT,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"ua" VARCHAR(1000)
);
COMMENT ON COLUMN "public"."sys_log"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_log"."company_id" IS '公司id';
COMMENT ON COLUMN "public"."sys_log"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."sys_log"."name" IS '操作名称';
COMMENT ON COLUMN "public"."sys_log"."ip" IS 'ip';
COMMENT ON COLUMN "public"."sys_log"."uri" IS 'uri';
COMMENT ON COLUMN "public"."sys_log"."method" IS '方法';
COMMENT ON COLUMN "public"."sys_log"."params" IS '请求参数';
COMMENT ON COLUMN "public"."sys_log"."status" IS '状态(1:成功;2:失败;)';
COMMENT ON COLUMN "public"."sys_log"."cost_time" IS '花费时间';
COMMENT ON COLUMN "public"."sys_log"."result" IS '返回结果';
COMMENT ON COLUMN "public"."sys_log"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_log"."ua" IS '浏览器信息';
COMMENT ON TABLE "public"."sys_log" IS '操作日记';


DROP TABLE IF EXISTS "public"."sys_theme";
CREATE TABLE "public"."sys_theme" (
	"id" BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	"user_id" BIGINT NOT NULL,
	"color" VARCHAR(100) NOT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "public"."sys_theme"."id" IS '主键';
COMMENT ON COLUMN "public"."sys_theme"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."sys_theme"."color" IS '颜色值';
COMMENT ON COLUMN "public"."sys_theme"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_theme"."update_time" IS '更新时间';
COMMENT ON TABLE "public"."sys_theme" IS '用户主题';


INSERT INTO "public"."sys_company" VALUES (NULL, 'seagox', '1001', '水母', '水母', 'avatar', '2021-06-29 09:22:42', '2021-06-29 09:22:42');
INSERT INTO "public"."sys_department" VALUES (1, NULL, '1001', '默认部门', NULL, NULL, '2021-07-01 11:30:43', '2021-07-01 11:30:43');
INSERT INTO "public"."sys_account" VALUES (NULL, 'superAdmin', NULL, NULL, '超级管理员', 1, 'e10adc3949ba59abbe56e057f20f883e', NULL, 1, 3, NULL, 0, '2021-07-01 11:51:02', '2021-07-01 11:51:02');
INSERT INTO "public"."sys_user_relate" VALUES (1, 1, 1, 1, '2021-06-29 09:23:33', '2021-06-29 09:23:33');
INSERT INTO "public"."sys_account" VALUES (NULL, 'admin', NULL, NULL, '管理员', 1, 'e10adc3949ba59abbe56e057f20f883e', NULL, 1, 2, NULL, 0, '2021-07-01 11:51:02', '2021-07-01 11:51:02');
INSERT INTO "public"."sys_user_relate" VALUES (2, 1, 1, 1, '2021-06-29 09:23:33', '2021-06-29 09:23:33');

SET IDENTITY_INSERT "public"."sys_role" ON;
INSERT INTO "public"."sys_role"("id", "company_id", "name", "path", "create_time", "update_time") 
VALUES(1, 1, '默认角色','1,2,3', '2021-07-01 11:31:22', '2021-07-01 11:31:22');
SET IDENTITY_INSERT "public"."sys_role" OFF;

INSERT INTO "public"."sys_menu" VALUES (1, NULL, 5, '组织架构', '<svg t=\"1677030380655\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"2355\" width=\"16\" height=\"16\"><path d=\"M789.922409 305.422945h111.583022c30.756491 0 55.791511-24.958229 55.791512-55.649192V138.475371c0-30.690963-25.03502-55.649191-55.791512-55.649192h-111.583022c-30.756491 0-55.791511 24.958229-55.791512 55.649192v27.824083H559.31886c-15.418689 0-27.895756 12.458637-27.895755 27.825108v297.253364h-123.705825V380.079543c0-30.690963-25.03502-55.649191-55.791512-55.649191H120.191851c-30.769802 0-55.791511 24.958229-55.791512 55.649191v278.245957c0 30.690963 25.02171 55.649191 55.791512 55.649191h231.733917c30.756491 0 55.791511-24.958229 55.791512-55.649191V547.027117h123.705825v297.266674c0 15.366471 12.477067 27.825108 27.895755 27.825108h174.348217c0.155631 0 0.308189-0.009215 0.462796-0.011263v27.83637c0 30.690963 25.03502 55.649191 55.791512 55.649192h111.583023c30.756491 0 55.791511-24.958229 55.791511-55.649192V788.6446c0-30.690963-25.03502-55.649191-55.791511-55.649191h-111.583023c-30.756491 0-55.791511 24.958229-55.791512 55.649191v27.83637c-0.154607-0.003072-0.308189-0.011263-0.462796-0.011263H587.214616V547.027117h146.916281v32.933271c0 30.690963 25.03502 55.649191 55.791512 55.649192h111.583022c30.756491 0 55.791511-24.958229 55.791512-55.649192V468.662006c0-30.690963-25.03502-55.649191-55.791512-55.649191h-111.583022c-30.756491 0-55.791511 24.958229-55.791512 55.649191v22.71592H587.214616V221.94967h146.916281v27.824083c0 30.691986 25.03502 55.649191 55.791512 55.649192z m0-166.947574h111.583022l0.027645 111.298382H789.922409V138.475371zM120.191851 658.3255V380.079543h231.733917l0.027645 278.245957H120.191851z m669.730558 130.3191h111.583022l0.027645 111.298382H789.922409V788.6446z m0-319.982594h111.583022l0.027645 111.298382H789.922409V468.662006z\" fill=\"#000000\" p-id=\"2356\"></path></svg>', NULL, 1, 1, NULL, '2021-07-01 11:33:27', '2021-07-01 11:33:27');
INSERT INTO "public"."sys_menu" VALUES (1, 1, 4, '通讯录', '<svg t=\"1677046363544\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"10588\" width=\"16\" height=\"16\"><path d=\"M822.982642 62.633113h-578.423537c-35.221655 0-63.891468 28.656502-63.891468 63.891468v65.960741h-36.098101c-15.51391 0-28.067768 12.567168-28.067768 28.067768 0 15.5006 12.553858 28.067768 28.067768 28.067768h36.098101v139.829972h-36.098101c-15.51391 0-28.067768 12.567168-28.067768 28.067768 0 15.5006 12.553858 28.067768 28.067768 28.067768h36.098101v139.816661h-36.098101c-15.51391 0-28.067768 12.567168-28.067768 28.067768 0 15.5006 12.553858 28.067768 28.067768 28.067769h36.098101v139.829971h-36.098101c-15.51391 0-28.067768 12.567168-28.067768 28.067768 0 15.5006 12.553858 28.067768 28.067768 28.067769h36.098101v59.218455c0 35.234966 28.670837 63.891468 63.891468 63.891468h578.424561c35.221655 0 63.891468-28.656502 63.891468-63.891468V126.524581c-0.001024-35.234966-28.670837-63.891468-63.892492-63.891468zM236.80215 895.719455v-59.218455h50.159118c15.51391 0 28.067768-12.567168 28.067769-28.067769 0-15.499576-12.553858-28.067768-28.067769-28.067768h-50.159118V640.536516h50.159118c15.51391 0 28.067768-12.567168 28.067769-28.067768 0-15.499576-12.553858-28.067768-28.067769-28.067769h-50.159118V444.585342h50.159118c15.51391 0 28.067768-12.567168 28.067769-28.067768 0-15.499576-12.553858-28.067768-28.067769-28.067768h-50.159118V248.620858h50.159118c15.51391 0 28.067768-12.567168 28.067769-28.067768 0-15.5006-12.553858-28.067768-28.067769-28.067768h-50.159118V126.524581c0-4.275745 3.48121-7.756955 7.756955-7.756955h434.879934v784.708785H244.559105c-4.276769 0-7.756955-3.48121-7.756955-7.756956z m593.937447 0c0 4.275745-3.48121 7.756955-7.756955 7.756956h-87.40909V118.767626h87.40909c4.275745 0 7.756955 3.48121 7.756955 7.756955v769.194874z\" fill=\"#000000\" p-id=\"10589\"></path></svg>', 'contact', 1, 1, NULL, '2021-07-01 11:34:04', '2021-07-01 11:34:04');
INSERT INTO "public"."sys_menu" VALUES (1, 1, 4, '角色管理', '<svg t=\"1677030493356\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"2605\" width=\"16\" height=\"16\"><path d=\"M755.301774 722.134033L534.133329 611.604588l66.820804-97.226102a28.05753 28.05753 0 0 0 4.916697-15.844626V343.338442c0-101.186491-81.080455-180.464908-184.616762-180.464908-103.536308 0-184.616763 79.277393-184.616763 180.464908v155.195418a27.852752 27.852752 0 0 0 5.176764 16.199914l68.732398 96.651703L79.74838 721.914922a28.013502 28.013502 0 0 0-15.885581 25.241844v98.510055c0 15.462716 12.525189 27.973571 27.973571 27.973571h650.953526c15.462716 0 27.973571-12.511879 27.97357-27.973571v-98.510055c0.001024-10.599261-5.981538-20.296479-15.461692-25.022733z m-40.485449 95.559217H119.810965v-52.915417l244.867295-117.277873a27.920329 27.920329 0 0 0 14.997872-18.248708 27.808725 27.808725 0 0 0-4.289055-23.193051l-82.801607-116.45774v-146.262019c0-69.825907 56.521542-124.516742 128.668598-124.516742 72.148079 0 128.669621 54.690835 128.669621 124.516742v146.507751l-80.124147 116.567297a27.966404 27.966404 0 0 0-4.043323 22.837762c2.048795 7.83989 7.348425 14.424496 14.588318 18.029597l234.472811 117.168317v53.244084z\" fill=\"#000000\" p-id=\"2606\"></path><path d=\"M942.432176 653.128258L721.291375 542.597789l66.820804-97.226103a28.060601 28.060601 0 0 0 4.917722-15.844625V274.332667c0-101.186491-81.080455-180.464908-184.616763-180.464908-20.161326 0-39.912074 3.059369-58.706513 9.097221l17.100933 53.270706c13.276721-4.261411 27.264018-6.419761 41.60558-6.419761 72.147056 0 128.668597 54.690835 128.668597 124.516742v146.507751l-80.124146 116.567296a27.966404 27.966404 0 0 0-4.043323 22.837763c2.048795 7.840914 7.348425 14.424496 14.588318 18.029596l234.445166 117.168317v53.243061h-69.743997v55.948166h97.717568c15.461692 0 27.973571-12.511879 27.973571-27.973571v-98.510055c0-10.599261-5.983586-20.296479-15.462716-25.022733z\" fill=\"#000000\" p-id=\"2607\"></path></svg>', 'role', 1, 2, NULL, '2021-07-01 11:34:38', '2021-07-01 11:34:38');