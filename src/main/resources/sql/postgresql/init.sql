DROP TABLE IF EXISTS "jelly_import_rule";
CREATE TABLE "jelly_import_rule"  (
    "id" BIGSERIAL PRIMARY KEY NOT NULL,
    "company_id" BIGINT NOT NULL,
    "code" varchar(30) NOT NULL,
    "name" varchar(100) NOT NULL,
    "data_source" BIGINT NOT NULL,
    "before_rule_id" BIGINT DEFAULT NULL,
    "after_rule_id" BIGINT DEFAULT NULL,
    "verify_rule_id" BIGINT DEFAULT NULL,
    "template_source" text,
    "create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN "jelly_import_rule"."id" IS '主键';
COMMENT ON COLUMN "jelly_import_rule"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_import_rule"."code" IS '编码';
COMMENT ON COLUMN "jelly_import_rule"."name" IS '名称';
COMMENT ON COLUMN "jelly_import_rule"."data_source" IS '数据源';
COMMENT ON COLUMN "jelly_import_rule"."before_rule_id" IS '导入之前规则';
COMMENT ON COLUMN "jelly_import_rule"."after_rule_id" IS '导入之后 规则';
COMMENT ON COLUMN "jelly_import_rule"."verify_rule_id" IS '验证规则';
COMMENT ON COLUMN "jelly_import_rule"."template_source" IS '模板源';
COMMENT ON COLUMN "jelly_import_rule"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_import_rule"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_import_rule" IS '导入规则';

DROP TABLE IF EXISTS "jelly_import_rule_detail";
CREATE TABLE "jelly_import_rule_detail"  (
    "id" BIGSERIAL PRIMARY KEY NOT NULL,
    "rule_id" BIGINT NOT NULL,
    "field" BIGINT NOT NULL,
    "col" varchar(30) NOT NULL,
    "rule" text,
    "create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN "jelly_import_rule_detail"."id" IS '主键';
COMMENT ON COLUMN "jelly_import_rule_detail"."rule_id" IS '导入规则id';
COMMENT ON COLUMN "jelly_import_rule_detail"."field" IS '对应字段';
COMMENT ON COLUMN "jelly_import_rule_detail"."col" IS '对应列';
COMMENT ON COLUMN "jelly_import_rule_detail"."rule" IS '规则';
COMMENT ON COLUMN "jelly_import_rule_detail"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_import_rule_detail"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_import_rule_detail" IS '导入规则明细';

DROP TABLE IF EXISTS "jelly_open_api";
CREATE TABLE "jelly_open_api"  (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
  	"appid" VARCHAR(30) NOT NULL,
  	"secret" VARCHAR(50) NOT NULL,
  	"remark" VARCHAR(200) NOT NULL,
  	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "jelly_open_api"."id" IS '主键';
COMMENT ON COLUMN "jelly_open_api"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_open_api"."appid" IS 'appid';
COMMENT ON COLUMN "jelly_open_api"."secret" IS 'secret';
COMMENT ON COLUMN "jelly_open_api"."remark" IS '备注';
COMMENT ON COLUMN "jelly_open_api"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_open_api"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_open_api" IS 'openApi';


DROP TABLE IF EXISTS "jelly_form_design";
CREATE TABLE "jelly_form_design" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"type" INTEGER NOT NULL,
	"name" VARCHAR(30) NOT NULL,
	"excel_json" TEXT,
	"data_source" VARCHAR(255) NOT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "jelly_form_design"."id" IS '主键';
COMMENT ON COLUMN "jelly_form_design"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_form_design"."type" IS '类型(1:简化版;2:高级版;)';
COMMENT ON COLUMN "jelly_form_design"."name" IS '名称';
COMMENT ON COLUMN "jelly_form_design"."excel_json" IS 'excel配置';
COMMENT ON COLUMN "jelly_form_design"."data_source" IS '数据源配置';
COMMENT ON COLUMN "jelly_form_design"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_form_design"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_form_design" IS '表单设计';


DROP TABLE IF EXISTS "jelly_print";
CREATE TABLE "jelly_print" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"name" VARCHAR(30) NOT NULL,
	"excel_json" TEXT,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "jelly_print"."id" IS '主键';
COMMENT ON COLUMN "jelly_print"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_print"."name" IS '名称';
COMMENT ON COLUMN "jelly_print"."excel_json" IS 'excel配置';
COMMENT ON COLUMN "jelly_print"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_print"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_print" IS '打印模版';


DROP TABLE IF EXISTS "jelly_form";
CREATE TABLE "jelly_form" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"design_ids" VARCHAR(255) NOT NULL,
	"name" VARCHAR(30) NOT NULL,
	"icon" VARCHAR(30) NOT NULL,
	"color" VARCHAR(30) NOT NULL,
	"table_header" BIGINT,
	"list_export_path" TEXT,
	"detail_export_path" TEXT,
	"flow_id" BIGINT DEFAULT NULL,
	"data_source" TEXT,
	"search_json" TEXT,
	"insert_before_rule" BIGINT,
	"insert_after_rule" BIGINT,
	"update_before_rule" BIGINT,
	"update_after_rule" BIGINT,
	"delete_before_rule" BIGINT,
	"delete_after_rule" BIGINT,
	"process_end_rule" BIGINT,
	"options" TEXT,
	"data_title" VARCHAR(255),
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"abandon_rule" BIGINT,
	"export_rule" BIGINT,
	"relate_search_json" TEXT,
	"authority" TEXT
);
COMMENT ON COLUMN "jelly_form"."id" IS '主键';
COMMENT ON COLUMN "jelly_form"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_form"."design_ids" IS '设计ids';
COMMENT ON COLUMN "jelly_form"."name" IS '名称';
COMMENT ON COLUMN "jelly_form"."icon" IS '图标';
COMMENT ON COLUMN "jelly_form"."color" IS '颜色';
COMMENT ON COLUMN "jelly_form"."table_header" IS '表格表头';
COMMENT ON COLUMN "jelly_form"."list_export_path" IS '列表导出文件路径';
COMMENT ON COLUMN "jelly_form"."detail_export_path" IS '详情导出文件路径';
COMMENT ON COLUMN "jelly_form"."flow_id" IS '流程id';
COMMENT ON COLUMN "jelly_form"."data_source" IS '数据源配置';
COMMENT ON COLUMN "jelly_form"."search_json" IS '搜索配置';
COMMENT ON COLUMN "jelly_form"."insert_before_rule" IS '新增前规则';
COMMENT ON COLUMN "jelly_form"."insert_after_rule" IS '新增后规则';
COMMENT ON COLUMN "jelly_form"."update_before_rule" IS '更新前规则';
COMMENT ON COLUMN "jelly_form"."update_after_rule" IS '更新后规则';
COMMENT ON COLUMN "jelly_form"."delete_before_rule" IS '删除前规则';
COMMENT ON COLUMN "jelly_form"."delete_after_rule" IS '删除后规则';
COMMENT ON COLUMN "jelly_form"."process_end_rule" IS '流程结束后规则';
COMMENT ON COLUMN "jelly_form"."options" IS '其他参数';
COMMENT ON COLUMN "jelly_form"."data_title" IS '数据标题';
COMMENT ON COLUMN "jelly_form"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_form"."update_time" IS '更新时间';
COMMENT ON COLUMN "jelly_form"."abandon_rule" IS '弃审规则';
COMMENT ON COLUMN "jelly_form"."export_rule" IS '导出规则';
COMMENT ON COLUMN "jelly_form"."relate_search_json" IS '联查json';
COMMENT ON COLUMN "jelly_form"."authority" IS '权限';
COMMENT ON TABLE "jelly_form" IS '表单管理';


DROP TABLE IF EXISTS "jelly_report";
CREATE TABLE "jelly_report"  (
    "id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
    "name" VARCHAR(100) NOT NULL,
	"icon" VARCHAR(30) NOT NULL,
	"color" VARCHAR(30) NOT NULL,
    "data_source" BIGINT NOT NULL,
    "template_source" text NOT NULL,
    "search_json" text DEFAULT NULL,
	"export_path" text DEFAULT NULL,
    "create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "jelly_report"."id" IS '主键';
COMMENT ON COLUMN "jelly_report"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_report"."name" IS '名称';
COMMENT ON COLUMN "jelly_report"."icon" IS '图标';
COMMENT ON COLUMN "jelly_report"."color" IS '颜色';
COMMENT ON COLUMN "jelly_report"."data_source" IS '数据源';
COMMENT ON COLUMN "jelly_report"."template_source" IS '模板源';
COMMENT ON COLUMN "jelly_report"."search_json" IS '搜索配置';
COMMENT ON COLUMN "jelly_report"."export_path" IS '导出路径';
COMMENT ON TABLE "jelly_report" IS '报表管理';


DROP TABLE IF EXISTS "jelly_data_sheet";
CREATE TABLE "jelly_data_sheet" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"form_id" BIGINT NOT NULL,
	"single_flag" INTEGER DEFAULT 1,
	"table_name" VARCHAR(50) NOT NULL,
	"sort" INTEGER DEFAULT 1,
	"relate_table" VARCHAR(50),
	"relate_field" VARCHAR(50),
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "jelly_data_sheet"."id" IS '主键';
COMMENT ON COLUMN "jelly_data_sheet"."form_id" IS '表单id';
COMMENT ON COLUMN "jelly_data_sheet"."single_flag" IS '是否单条(1:是;2:否;)';
COMMENT ON COLUMN "jelly_data_sheet"."table_name" IS '数据表名';
COMMENT ON COLUMN "jelly_data_sheet"."sort" IS '排序';
COMMENT ON COLUMN "jelly_data_sheet"."relate_table" IS '关联表';
COMMENT ON COLUMN "jelly_data_sheet"."relate_field" IS '关联字段';
COMMENT ON COLUMN "jelly_data_sheet"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_data_sheet"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_data_sheet" IS '数据表设置';


DROP TABLE IF EXISTS "jelly_table_classify";
CREATE TABLE "jelly_table_classify" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"name" VARCHAR(30) NOT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "jelly_table_classify"."id" IS '主键';
COMMENT ON COLUMN "jelly_table_classify"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_table_classify"."name" IS '名称';
COMMENT ON COLUMN "jelly_table_classify"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_table_classify"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_table_classify" IS '表头分类';


DROP TABLE IF EXISTS "jelly_table_column";
CREATE TABLE "jelly_table_column" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"classify_id" BIGINT NOT NULL,
	"parent_id" BIGINT,
	"prop" VARCHAR(30) NOT NULL,
	"label" VARCHAR(30) NOT NULL,
	"width" INTEGER,
	"locking" INTEGER DEFAULT 3,
	"summary" INTEGER DEFAULT 2,
	"total" INTEGER DEFAULT 2,
	"target" INTEGER DEFAULT 0 NOT NULL,
	"formatter" BIGINT,
	"options" TEXT,
	"sort" INTEGER DEFAULT 1,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"formula" TEXT,
	"router_json" TEXT
);
COMMENT ON COLUMN "jelly_table_column"."id" IS '主键';
COMMENT ON COLUMN "jelly_table_column"."classify_id" IS '分类id';
COMMENT ON COLUMN "jelly_table_column"."parent_id" IS '上级id';
COMMENT ON COLUMN "jelly_table_column"."prop" IS '字段名';
COMMENT ON COLUMN "jelly_table_column"."label" IS '标题';
COMMENT ON COLUMN "jelly_table_column"."width" IS '宽度';
COMMENT ON COLUMN "jelly_table_column"."locking" IS '锁定(1:左;2:右;3:无)';
COMMENT ON COLUMN "jelly_table_column"."summary" IS '汇总(1:是;2:否;)';
COMMENT ON COLUMN "jelly_table_column"."total" IS '合计(1:是;2:否;)';
COMMENT ON COLUMN "jelly_table_column"."target" IS '格式对象(0:无;1:数据字典;2:区域数据;)';
COMMENT ON COLUMN "jelly_table_column"."formatter" IS '格式化';
COMMENT ON COLUMN "jelly_table_column"."options" IS '格式化数据源';
COMMENT ON COLUMN "jelly_table_column"."sort" IS '排序';
COMMENT ON COLUMN "jelly_table_column"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_table_column"."update_time" IS '更新时间';
COMMENT ON COLUMN "jelly_table_column"."formula" IS '公式';
COMMENT ON COLUMN "jelly_table_column"."router_json" IS '路由json';
COMMENT ON TABLE "jelly_table_column" IS '表格表头';


DROP TABLE IF EXISTS "jelly_table_column_config";
CREATE TABLE "jelly_table_column_config" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"user_id" BIGINT NOT NULL,
	"table_column_id" BIGINT NOT NULL,
	"display" INTEGER DEFAULT 1,
	"sort" INTEGER DEFAULT 1,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "jelly_table_column_config"."id" IS '主键';
COMMENT ON COLUMN "jelly_table_column_config"."user_id" IS '用户id';
COMMENT ON COLUMN "jelly_table_column_config"."table_column_id" IS '表头id';
COMMENT ON COLUMN "jelly_table_column_config"."display" IS '显示(1:显示;2:隐藏;)';
COMMENT ON COLUMN "jelly_table_column_config"."sort" IS '排序';
COMMENT ON COLUMN "jelly_table_column_config"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_table_column_config"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_table_column_config" IS '表头配置';


DROP TABLE IF EXISTS "jelly_business_table";
CREATE TABLE "jelly_business_table" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"name" VARCHAR(64) NOT NULL,
	"remark" VARCHAR(64) NOT NULL,
	"is_virtual" INTEGER DEFAULT 0,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "jelly_business_table"."id" IS '主键';
COMMENT ON COLUMN "jelly_business_table"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_business_table"."name" IS '名称';
COMMENT ON COLUMN "jelly_business_table"."remark" IS '注释';
COMMENT ON COLUMN "jelly_business_table"."is_virtual" IS '虚拟(1:是;0:否;)';
COMMENT ON COLUMN "jelly_business_table"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_business_table"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_business_table" IS '业务表';


DROP TABLE IF EXISTS "jelly_business_field";
CREATE TABLE "jelly_business_field" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"business_table_id" BIGINT NOT NULL,
	"name" VARCHAR(64) NOT NULL,
	"remark" VARCHAR(64) NOT NULL,
	"type" VARCHAR(20) NOT NULL,
	"kind" INTEGER DEFAULT 1,
	"length" INTEGER DEFAULT 0,
	"decimals" INTEGER DEFAULT 0,
	"not_null" INTEGER DEFAULT 0,
	"default_value" VARCHAR(200),
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "jelly_business_field"."id" IS '主键';
COMMENT ON COLUMN "jelly_business_field"."business_table_id" IS '业务表id';
COMMENT ON COLUMN "jelly_business_field"."name" IS '名称';
COMMENT ON COLUMN "jelly_business_field"."remark" IS '注释';
COMMENT ON COLUMN "jelly_business_field"."type" IS '类型(1:部门;2:数字;3:日期;4:字符串;5:其他;)';
COMMENT ON COLUMN "jelly_business_field"."kind" IS '种类(1:基本类型;2:数据字典;3:单位;4:部门;5:用户;6:省市区;)';
COMMENT ON COLUMN "jelly_business_field"."length" IS '长度';
COMMENT ON COLUMN "jelly_business_field"."decimals" IS '小数';
COMMENT ON COLUMN "jelly_business_field"."not_null" IS '不为空(1:是;0:否;)';
COMMENT ON COLUMN "jelly_business_field"."default_value" IS '默认值';
COMMENT ON COLUMN "jelly_business_field"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_business_field"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_business_field" IS '业务字段';


DROP TABLE IF EXISTS "jelly_dic_classify";
CREATE TABLE "jelly_dic_classify" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"name" VARCHAR(30) NOT NULL,
	"sort" INTEGER DEFAULT 1,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "jelly_dic_classify"."id" IS '主键';
COMMENT ON COLUMN "jelly_dic_classify"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_dic_classify"."name" IS '名称';
COMMENT ON COLUMN "jelly_dic_classify"."sort" IS '排序';
COMMENT ON COLUMN "jelly_dic_classify"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_dic_classify"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_dic_classify" IS '字典分类';


DROP TABLE IF EXISTS "jelly_dic_detail";
CREATE TABLE "jelly_dic_detail" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"classify_id" BIGINT NOT NULL,
	"code" VARCHAR(30) NOT NULL,
	"name" VARCHAR(30) NOT NULL,
	"sort" INTEGER DEFAULT 1,
	"status" INTEGER DEFAULT 1,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "jelly_dic_detail"."id" IS '主键';
COMMENT ON COLUMN "jelly_dic_detail"."classify_id" IS '字典分类id';
COMMENT ON COLUMN "jelly_dic_detail"."code" IS '编码';
COMMENT ON COLUMN "jelly_dic_detail"."name" IS '名称';
COMMENT ON COLUMN "jelly_dic_detail"."sort" IS '排序';
COMMENT ON COLUMN "jelly_dic_detail"."status" IS '状态((0:禁用：1:启用)';
COMMENT ON COLUMN "jelly_dic_detail"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_dic_detail"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_dic_detail" IS '字典详情';


DROP TABLE IF EXISTS "jelly_regions";
CREATE TABLE "jelly_regions" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"code" VARCHAR(30) NOT NULL,
	"grade" INTEGER NOT NULL,
	"name" VARCHAR(50) NOT NULL
);
COMMENT ON COLUMN "jelly_regions"."id" IS '主键';
COMMENT ON COLUMN "jelly_regions"."code" IS '编码';
COMMENT ON COLUMN "jelly_regions"."grade" IS '等级';
COMMENT ON COLUMN "jelly_regions"."name" IS '名称';
COMMENT ON TABLE "jelly_regions" IS '区域数据';


DROP TABLE IF EXISTS "jelly_job";
CREATE TABLE "jelly_job" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"name" VARCHAR(30) NOT NULL,
	"cron" VARCHAR(30) NOT NULL,
	"script" TEXT NOT NULL,
	"status" INTEGER DEFAULT 0,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "jelly_job"."id" IS '主键';
COMMENT ON COLUMN "jelly_job"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_job"."name" IS '名称';
COMMENT ON COLUMN "jelly_job"."cron" IS '表达式';
COMMENT ON COLUMN "jelly_job"."script" IS '规则';
COMMENT ON COLUMN "jelly_job"."status" IS '状态(0:未启动;1:已启动;)';
COMMENT ON COLUMN "jelly_job"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_job"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_job" IS '任务调度';


DROP TABLE IF EXISTS "jelly_gauge";
CREATE TABLE "jelly_gauge" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"name" VARCHAR(30) NOT NULL,
	"config" TEXT,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "jelly_gauge"."id" IS '主键';
COMMENT ON COLUMN "jelly_gauge"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_gauge"."name" IS '名称';
COMMENT ON COLUMN "jelly_gauge"."config" IS '配置';
COMMENT ON COLUMN "jelly_gauge"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_gauge"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_gauge" IS '仪表板';


DROP TABLE IF EXISTS "jelly_door";
CREATE TABLE "jelly_door" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"name" VARCHAR(30) NOT NULL,
	"config" TEXT,
	"authority" TEXT,
	"path" BIGINT,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "jelly_door"."id" IS '主键';
COMMENT ON COLUMN "jelly_door"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_door"."name" IS '名称';
COMMENT ON COLUMN "jelly_door"."config" IS '配置';
COMMENT ON COLUMN "jelly_door"."authority" IS '权限';
COMMENT ON COLUMN "jelly_door"."path" IS '路径';
COMMENT ON COLUMN "jelly_door"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_door"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_door" IS '门户管理';


DROP TABLE IF EXISTS "jelly_meta_page";
CREATE TABLE "jelly_meta_page"  (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
    "company_id" BIGINT NOT NULL,
	"name" VARCHAR(30) NOT NULL,
	"path" VARCHAR(30) NOT NULL,
	"html" TEXT DEFAULT NULL,
	"js" TEXT DEFAULT NULL,
	"css" TEXT DEFAULT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN "jelly_meta_page"."id" IS '主键';
COMMENT ON COLUMN "jelly_meta_page"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_meta_page"."name" IS '名称';
COMMENT ON COLUMN "jelly_meta_page"."path" IS '路径';
COMMENT ON COLUMN "jelly_meta_page"."html" IS 'html脚本';
COMMENT ON COLUMN "jelly_meta_page"."js" IS 'js脚本';
COMMENT ON COLUMN "jelly_meta_page"."css" IS 'css脚本';
COMMENT ON COLUMN "jelly_meta_page"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_meta_page"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_meta_page" IS '元页面';


DROP TABLE IF EXISTS "jelly_meta_function";
CREATE TABLE "jelly_meta_function"  (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
    "company_id" BIGINT NOT NULL,
	"name" VARCHAR(30) NOT NULL,
	"path" VARCHAR(30) NOT NULL,
	"script" TEXT DEFAULT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN "jelly_meta_function"."id" IS '主键';
COMMENT ON COLUMN "jelly_meta_function"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_meta_function"."name" IS '名称';
COMMENT ON COLUMN "jelly_meta_function"."path" IS '路径';
COMMENT ON COLUMN "jelly_meta_function"."script" IS '脚本';
COMMENT ON COLUMN "jelly_meta_function"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_meta_function"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_meta_function" IS '元函数';

DROP TABLE IF EXISTS "jelly_template_engine";
CREATE TABLE "jelly_template_engine"  (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
    "company_id" BIGINT NOT NULL,
	"name" VARCHAR(30) NOT NULL,
	"path" VARCHAR(30) NOT NULL,
	"script" TEXT DEFAULT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN "jelly_template_engine"."id" IS '主键';
COMMENT ON COLUMN "jelly_template_engine"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_template_engine"."name" IS '名称';
COMMENT ON COLUMN "jelly_template_engine"."path" IS '路径';
COMMENT ON COLUMN "jelly_template_engine"."script" IS '脚本';
COMMENT ON COLUMN "jelly_template_engine"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_template_engine"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_template_engine" IS '模版引擎';


DROP TABLE IF EXISTS "sea_definition";
CREATE TABLE "sea_definition"  (
    "id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"name" VARCHAR(30) NOT NULL,
    "resources" TEXT DEFAULT NULL,
    "data_source" VARCHAR(255) DEFAULT NULL,
    "create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN "sea_definition"."id" IS '主键';
COMMENT ON COLUMN "sea_definition"."company_id" IS '公司id';
COMMENT ON COLUMN "sea_definition"."name" IS '名称';
COMMENT ON COLUMN "sea_definition"."resources" IS '流程文件';
COMMENT ON COLUMN "sea_definition"."data_source" IS '数据源配置';
COMMENT ON COLUMN "sea_definition"."create_time" IS '创建时间';
COMMENT ON COLUMN "sea_definition"."update_time" IS '更新时间';
COMMENT ON TABLE "sea_definition" IS '流程定义';

DROP TABLE IF EXISTS "sea_instance";
CREATE TABLE "sea_instance" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"user_id" BIGINT NOT NULL,
	"version" INTEGER DEFAULT 1,
	"name" VARCHAR(50) NOT NULL,
	"business_type" VARCHAR(50) NOT NULL,
	"business_key" VARCHAR(50) NOT NULL,
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
COMMENT ON COLUMN "sea_instance"."id" IS '主键';
COMMENT ON COLUMN "sea_instance"."company_id" IS '公司id';
COMMENT ON COLUMN "sea_instance"."user_id" IS '用户id';
COMMENT ON COLUMN "sea_instance"."version" IS '版本';
COMMENT ON COLUMN "sea_instance"."name" IS '名称';
COMMENT ON COLUMN "sea_instance"."business_type" IS '业务类型';
COMMENT ON COLUMN "sea_instance"."business_key" IS '业务key';
COMMENT ON COLUMN "sea_instance"."resources" IS '流程文件';
COMMENT ON COLUMN "sea_instance"."status" IS '状态(0:待审;1:通过;2:驳回;3:撤回;4:关闭)';
COMMENT ON COLUMN "sea_instance"."start_time" IS '开始时间';
COMMENT ON COLUMN "sea_instance"."end_time" IS '结束时间';
COMMENT ON COLUMN "sea_instance"."current_agent" IS '当前代办人';
COMMENT ON COLUMN "sea_instance"."close_status" IS '关闭时流程状态';
COMMENT ON COLUMN "sea_instance"."other_json" IS '其它json值';
COMMENT ON COLUMN "sea_instance"."create_time" IS '创建时间';
COMMENT ON COLUMN "sea_instance"."update_time" IS '更新时间';
COMMENT ON COLUMN "sea_instance"."return_number" IS '退回次数';
COMMENT ON TABLE "sea_instance" IS '流程实例';


DROP TABLE IF EXISTS "sea_node";
CREATE TABLE "sea_node" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"def_id" BIGINT NOT NULL,
	"version" INTEGER DEFAULT 1,
	"source" VARCHAR(50) NOT NULL,
	"target" VARCHAR(50) NOT NULL,
	"name" VARCHAR(50) NOT NULL,
	"type" INTEGER DEFAULT 1,
	"status" INTEGER DEFAULT 0,
	"start_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"end_time" TIMESTAMP,
	"is_concurrent" INTEGER DEFAULT 0,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "sea_node"."id" IS '主键';
COMMENT ON COLUMN "sea_node"."def_id" IS '流程实例id';
COMMENT ON COLUMN "sea_node"."version" IS '版本';
COMMENT ON COLUMN "sea_node"."source" IS '源节点';
COMMENT ON COLUMN "sea_node"."target" IS '目标节点';
COMMENT ON COLUMN "sea_node"."name" IS '名称';
COMMENT ON COLUMN "sea_node"."type" IS '类型(1:审批;2:抄送;3:撤回;4:驳回;5:重新发起;)';
COMMENT ON COLUMN "sea_node"."status" IS '状态(0:待办;1:通过;2:不通过;3:作废;)';
COMMENT ON COLUMN "sea_node"."start_time" IS '开始时间';
COMMENT ON COLUMN "sea_node"."end_time" IS '结束时间';
COMMENT ON COLUMN "sea_node"."is_concurrent" IS '并行网关(0:否;1:是;)';
COMMENT ON COLUMN "sea_node"."create_time" IS '创建时间';
COMMENT ON COLUMN "sea_node"."update_time" IS '更新时间';
COMMENT ON TABLE "sea_node" IS '流程节点';


DROP TABLE IF EXISTS "sea_node_detail";
CREATE TABLE "sea_node_detail" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"node_id" BIGINT NOT NULL,
	"name" VARCHAR(30) NOT NULL,
	"assignee" VARCHAR(30) NOT NULL,
	"status" INTEGER DEFAULT 0,
	"remark" VARCHAR(255),
	"start_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"end_time" TIMESTAMP,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "sea_node_detail"."id" IS '主键';
COMMENT ON COLUMN "sea_node_detail"."node_id" IS '流程节点id';
COMMENT ON COLUMN "sea_node_detail"."name" IS '名称';
COMMENT ON COLUMN "sea_node_detail"."assignee" IS '签收人或被委托id';
COMMENT ON COLUMN "sea_node_detail"."status" IS '状态(0:待办;1:同意;2:不同意;3:已阅;4:撤回;5:重新发起;6:弃审;)';
COMMENT ON COLUMN "sea_node_detail"."remark" IS '评论';
COMMENT ON COLUMN "sea_node_detail"."start_time" IS '开始时间';
COMMENT ON COLUMN "sea_node_detail"."end_time" IS '结束时间';
COMMENT ON COLUMN "sea_node_detail"."create_time" IS '创建时间';
COMMENT ON COLUMN "sea_node_detail"."update_time" IS '更新时间';
COMMENT ON TABLE "sea_node_detail" IS '流程节点详情';


DROP TABLE IF EXISTS "sys_company";
CREATE TABLE "sys_company" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"parent_id" BIGINT,
	"mark" VARCHAR(30) NOT NULL,
	"code" VARCHAR(30) NOT NULL,
	"name" VARCHAR(30) NOT NULL,
	"alias" VARCHAR(30) NOT NULL,
	"logo" VARCHAR(100) NOT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "sys_company"."id" IS '主键';
COMMENT ON COLUMN "sys_company"."parent_id" IS '上级id';
COMMENT ON COLUMN "sys_company"."mark" IS '标识';
COMMENT ON COLUMN "sys_company"."code" IS '编码';
COMMENT ON COLUMN "sys_company"."name" IS '名称';
COMMENT ON COLUMN "sys_company"."alias" IS '简称';
COMMENT ON COLUMN "sys_company"."logo" IS 'logo';
COMMENT ON COLUMN "sys_company"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_company"."update_time" IS '更新时间';
COMMENT ON TABLE "sys_company" IS '公司';


DROP TABLE IF EXISTS "sys_department";
CREATE TABLE "sys_department" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"parent_id" BIGINT,
	"code" VARCHAR(30) NOT NULL,
	"name" VARCHAR(30) NOT NULL,
	"director" VARCHAR(500),
	"charge_leader" VARCHAR(500),
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "sys_department"."id" IS '主键';
COMMENT ON COLUMN "sys_department"."company_id" IS '公司id';
COMMENT ON COLUMN "sys_department"."parent_id" IS '上级id';
COMMENT ON COLUMN "sys_department"."code" IS '编码';
COMMENT ON COLUMN "sys_department"."name" IS '名称';
COMMENT ON COLUMN "sys_department"."director" IS '直接主管';
COMMENT ON COLUMN "sys_department"."charge_leader" IS '分管领导';
COMMENT ON COLUMN "sys_department"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_department"."update_time" IS '更新时间';
COMMENT ON TABLE "sys_department" IS '部门';


DROP TABLE IF EXISTS "sys_user_relate";
CREATE TABLE "sys_user_relate" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"user_id" BIGINT NOT NULL,
	"company_id" BIGINT NOT NULL,
	"department_id" BIGINT,
	"role_ids" VARCHAR(50),
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "sys_user_relate"."id" IS '主键';
COMMENT ON COLUMN "sys_user_relate"."user_id" IS '用户id';
COMMENT ON COLUMN "sys_user_relate"."company_id" IS '公司id';
COMMENT ON COLUMN "sys_user_relate"."department_id" IS '部门id';
COMMENT ON COLUMN "sys_user_relate"."role_ids" IS '角色ids';
COMMENT ON COLUMN "sys_user_relate"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_user_relate"."update_time" IS '更新时间';
COMMENT ON TABLE "sys_user_relate" IS '用户关联';


DROP TABLE IF EXISTS "sys_account";
CREATE TABLE "sys_account" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"avatar" VARCHAR(255) DEFAULT NULL,
	"account" VARCHAR(50) NOT NULL,
	"email" VARCHAR(50),
	"phone" VARCHAR(30),
	"name" VARCHAR(50) NOT NULL,
	"sex" INTEGER DEFAULT 1,
	"password" VARCHAR(255) NOT NULL,
	"position" VARCHAR(50),
	"status" INTEGER DEFAULT 1 NOT NULL,
	"type" INTEGER DEFAULT 1,
	"openid" VARCHAR(100),
	"sort" INTEGER DEFAULT 0,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "sys_account"."id" IS '主键';
COMMENT ON COLUMN "sys_account"."avatar" IS '头像';
COMMENT ON COLUMN "sys_account"."account" IS '账号';
COMMENT ON COLUMN "sys_account"."name" IS '姓名';
COMMENT ON COLUMN "sys_account"."sex" IS '性别(1:男;2:女;)';
COMMENT ON COLUMN "sys_account"."password" IS '密码';
COMMENT ON COLUMN "sys_account"."position" IS '职位';
COMMENT ON COLUMN "sys_account"."status" IS '状态(1:启用;2:禁用;)';
COMMENT ON COLUMN "sys_account"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_account"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_account"."type" IS '类型(1:普通成员;2:管理员;3:超级管理员;)';
COMMENT ON COLUMN "sys_account"."email" IS '邮箱';
COMMENT ON COLUMN "sys_account"."phone" IS '手机号';
COMMENT ON COLUMN "sys_account"."openid" IS 'openid';
COMMENT ON COLUMN "sys_account"."sort" IS '排序';
COMMENT ON TABLE "sys_account" IS '用户';


DROP TABLE IF EXISTS "sys_role";
CREATE TABLE "sys_role" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"name" VARCHAR(30) NOT NULL,
	"path" TEXT NOT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "sys_role"."id" IS '主键';
COMMENT ON COLUMN "sys_role"."company_id" IS '公司id';
COMMENT ON COLUMN "sys_role"."name" IS '名称';
COMMENT ON COLUMN "sys_role"."path" IS '菜单权限(以,隔开)';
COMMENT ON COLUMN "sys_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_role"."update_time" IS '更新时间';
COMMENT ON TABLE "sys_role" IS '系统角色';


DROP TABLE IF EXISTS "sys_menu";
CREATE TABLE "sys_menu" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"parent_id" BIGINT,
	"type" INTEGER DEFAULT 1,
	"name" VARCHAR(30) NOT NULL,
	"icon" VARCHAR(30) NOT NULL,
	"path" VARCHAR(50),
	"status" INTEGER DEFAULT 1,
	"sort" INTEGER DEFAULT 1,
	"quick_name" VARCHAR(30) DEFAULT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "sys_menu"."id" IS '主键';
COMMENT ON COLUMN "sys_menu"."company_id" IS '公司id';
COMMENT ON COLUMN "sys_menu"."parent_id" IS '上级id';
COMMENT ON COLUMN "sys_menu"."type" IS '类型(1:列表;2:按钮;3:新增页面;4:系统菜单;5:目录;6:仪表板;7:自定义页面;8:报表;9:单页面;)';
COMMENT ON COLUMN "sys_menu"."name" IS '名称';
COMMENT ON COLUMN "sys_menu"."icon" IS '图标';
COMMENT ON COLUMN "sys_menu"."path" IS '路径';
COMMENT ON COLUMN "sys_menu"."status" IS '状态(1:启用;2:禁用;)';
COMMENT ON COLUMN "sys_menu"."sort" IS '排序';
COMMENT ON COLUMN "sys_menu"."quick_name" IS '简称';
COMMENT ON COLUMN "sys_menu"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_menu"."update_time" IS '更新时间';
COMMENT ON TABLE "sys_menu" IS '菜单';


DROP TABLE IF EXISTS "sys_notice";
CREATE TABLE "sys_notice" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"title" VARCHAR(50) NOT NULL,
	"content" TEXT,
	"resources" TEXT,
	"user_id" BIGINT NOT NULL,
	"to_user_ids" VARCHAR(500) NOT NULL,
    "status" INTEGER DEFAULT 1,
    "classify" varchar(30) DEFAULT '1',
    "relation" VARCHAR(500),
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "sys_notice"."id" IS '主键';
COMMENT ON COLUMN "sys_notice"."company_id" IS '公司id';
COMMENT ON COLUMN "sys_notice"."title" IS '标题';
COMMENT ON COLUMN "sys_notice"."content" IS '正文';
COMMENT ON COLUMN "sys_notice"."resources" IS '附件json';
COMMENT ON COLUMN "sys_notice"."user_id" IS '发送公告人id';
COMMENT ON COLUMN "sys_notice"."to_user_ids" IS '接收公告人ids';
COMMENT ON COLUMN "sys_notice"."status" IS '状态(0:暂存;1:已发布;)';
COMMENT ON COLUMN "sys_notice"."classify" IS '通知类型';
COMMENT ON COLUMN "sys_notice"."relation" IS '关联通知';
COMMENT ON COLUMN "sys_notice"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_notice"."update_time" IS '更新时间';
COMMENT ON TABLE "sys_notice" IS '公告';


DROP TABLE IF EXISTS "sys_message";
CREATE TABLE "sys_message" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"type" INTEGER DEFAULT 1,
	"from_user_id" BIGINT NOT NULL,
	"to_user_id" BIGINT NOT NULL,
	"title" VARCHAR(50) NOT NULL,
	"business_type" BIGINT NOT NULL,
	"business_key" BIGINT NOT NULL,
	"status" INTEGER DEFAULT 0,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "sys_message"."id" IS '主键';
COMMENT ON COLUMN "sys_message"."company_id" IS '公司id';
COMMENT ON COLUMN "sys_message"."type" IS '类型(1:流程消息;2:公告通知;3:数据预警;4:暂存数据;9:其它消息;)';
COMMENT ON COLUMN "sys_message"."from_user_id" IS '用户id(来自)';
COMMENT ON COLUMN "sys_message"."to_user_id" IS '用户id(给谁)';
COMMENT ON COLUMN "sys_message"."title" IS '标题';
COMMENT ON COLUMN "sys_message"."business_type" IS '业务类型';
COMMENT ON COLUMN "sys_message"."business_key" IS '业务key';
COMMENT ON COLUMN "sys_message"."status" IS '状态(0:未读;1:已读;)';
COMMENT ON COLUMN "sys_message"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_message"."update_time" IS '更新时间';
COMMENT ON TABLE "sys_message" IS '消息表';


DROP TABLE IF EXISTS "sys_log";
CREATE TABLE "sys_log" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"user_id" BIGINT NOT NULL,
	"name" VARCHAR(50) NOT NULL,
	"ip" VARCHAR(50) NOT NULL,
	"uri" VARCHAR(50) NOT NULL,
	"method" VARCHAR(255) NOT NULL,
	"params" TEXT,
	"status" INTEGER DEFAULT 1,
	"cost_time" INTEGER NOT NULL,
	"result" TEXT,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"ua" VARCHAR(1000)
);
COMMENT ON COLUMN "sys_log"."id" IS '主键';
COMMENT ON COLUMN "sys_log"."company_id" IS '公司id';
COMMENT ON COLUMN "sys_log"."user_id" IS '用户id';
COMMENT ON COLUMN "sys_log"."name" IS '操作名称';
COMMENT ON COLUMN "sys_log"."ip" IS 'ip';
COMMENT ON COLUMN "sys_log"."uri" IS 'uri';
COMMENT ON COLUMN "sys_log"."method" IS '方法';
COMMENT ON COLUMN "sys_log"."params" IS '请求参数';
COMMENT ON COLUMN "sys_log"."status" IS '状态(1:成功;2:失败;)';
COMMENT ON COLUMN "sys_log"."cost_time" IS '花费时间';
COMMENT ON COLUMN "sys_log"."result" IS '返回结果';
COMMENT ON COLUMN "sys_log"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_log"."ua" IS '浏览器信息';
COMMENT ON TABLE "sys_log" IS '操作日记';


DROP TABLE IF EXISTS "sys_theme";
CREATE TABLE "sys_theme" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"user_id" BIGINT NOT NULL,
	"color" VARCHAR(30) NOT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "sys_theme"."id" IS '主键';
COMMENT ON COLUMN "sys_theme"."user_id" IS '用户id';
COMMENT ON COLUMN "sys_theme"."color" IS '颜色值';
COMMENT ON COLUMN "sys_theme"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_theme"."update_time" IS '更新时间';
COMMENT ON TABLE "sys_theme" IS '用户主题';

DROP TABLE IF EXISTS "jelly_common_wopublics";
CREATE TABLE "jelly_common_wopublics" (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
	"user_id" BIGINT NOT NULL,
	"name" VARCHAR(50) NOT NULL,
	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "jelly_common_wopublics"."id" IS '主键';
COMMENT ON COLUMN "jelly_common_wopublics"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_common_wopublics"."user_id" IS '用户id';
COMMENT ON COLUMN "jelly_common_wopublics"."name" IS '名称';
COMMENT ON COLUMN "jelly_common_wopublics"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_common_wopublics"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_common_wopublics" IS '常用语';


BEGIN;
INSERT INTO "sys_company" VALUES (1, NULL, 'default', '1001', '默认单位', '默认单位', 'avatar', '2021-06-29 09:22:42', '2021-06-29 09:22:42');
select setval('sys_company_id_seq',(select max(id) from "sys_company"));
INSERT INTO "sys_department" VALUES (1, 1, NULL, '1001', '默认部门', NULL, NULL, '2021-07-01 11:30:43', '2021-07-01 11:30:43');
select setval('sys_department_id_seq',(select max(id) from "sys_department"));
INSERT INTO "sys_account" VALUES (1, NULL, 'superAdmin', NULL, NULL, '超级管理员', 1, 'e10adc3949ba59abbe56e057f20f883e', NULL, 1, 3, NULL, 0, '2021-07-01 11:51:02', '2021-07-01 11:51:02');
INSERT INTO "sys_account" VALUES (2, NULL, 'admin', NULL, NULL, '管理员', 1, 'e10adc3949ba59abbe56e057f20f883e', NULL, 1, 2, NULL, 0, '2021-07-01 11:51:02', '2021-07-01 11:51:02');
select setval('sys_account_id_seq',(select max(id) from "sys_account"));
INSERT INTO "sys_user_relate" VALUES (1, 1, 1, 1, 1, '2021-06-29 09:23:33', '2021-06-29 09:23:33');
INSERT INTO "sys_user_relate" VALUES (2, 2, 1, 1, 1, '2021-06-29 09:23:33', '2021-06-29 09:23:33');
select setval('sys_user_relate_id_seq',(select max(id) from "sys_user_relate"));
INSERT INTO "sys_role" VALUES (1, 1, '默认角色', '1,2,3,4', '2021-07-01 11:31:22', '2021-07-01 11:31:22');
select setval('sys_role_id_seq',(select max(id) from "sys_role"));
INSERT INTO "sys_menu" VALUES (1, 1, NULL, 5, '组织架构', 'el-icon-office-building', NULL, 1, 1, NULL, '2021-07-01 11:33:27', '2021-07-01 11:33:27');
INSERT INTO "sys_menu" VALUES (2, 1, 1, 4, '通讯录', 'el-icon-collection', 'contact', 1, 1, NULL, '2021-07-01 11:34:04', '2021-07-01 11:34:04');
INSERT INTO "sys_menu" VALUES (3, 1, 1, 4, '角色管理', 'el-icon-coin', 'role', 1, 2, NULL, '2021-07-01 11:34:38', '2021-07-01 11:34:38');
select setval('sys_menu_id_seq',(select max(id) from "sys_menu"));
COMMIT;

