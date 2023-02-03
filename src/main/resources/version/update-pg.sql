-- 2022-08-10
DROP TABLE IF EXISTS "jelly_export_rule";
CREATE TABLE "jelly_export_rule"  (
    "id" BIGSERIAL PRIMARY KEY NOT NULL,
    "company_id" BIGINT NOT NULL,
    "code" varchar(30) NOT NULL,
    "name" varchar(100) NOT NULL,
    "data_source" bigint NOT NULL,
    "create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN "jelly_export_rule"."id" IS '主键';
COMMENT ON COLUMN "jelly_export_rule"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_export_rule"."code" IS '编码';
COMMENT ON COLUMN "jelly_export_rule"."name" IS '名称';
COMMENT ON COLUMN "jelly_export_rule"."data_source" IS '数据源';
COMMENT ON COLUMN "jelly_export_rule"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_export_rule"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_export_rule" IS '导入规则';

DROP TABLE IF EXISTS "jelly_export_rule_detail";
CREATE TABLE "jelly_export_rule_detail"  (
    "id" BIGSERIAL PRIMARY KEY NOT NULL,
    "export_rule_id" BIGINT NOT NULL,
    "field" BIGINT NOT NULL,
    "col" varchar(30) NOT NULL,
    "create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN "jelly_export_rule_detail"."id" IS '主键';
COMMENT ON COLUMN "jelly_export_rule_detail"."export_rule_id" IS '导入规则id';
COMMENT ON COLUMN "jelly_export_rule_detail"."field" IS '对应字段';
COMMENT ON COLUMN "jelly_export_rule_detail"."col" IS '对应列';
COMMENT ON COLUMN "jelly_export_rule_detail"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_export_rule_detail"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_export_rule_detail" IS '导入规则明细';


DROP TABLE IF EXISTS "jelly_export_data";
CREATE TABLE "jelly_export_data"  (
    "id" BIGSERIAL PRIMARY KEY NOT NULL,
    "company_id" BIGINT NOT NULL,
    "config" text NOT NULL,
    "create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN "jelly_export_data"."id" IS '主键';
COMMENT ON COLUMN "jelly_export_data"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_export_data"."config" IS '配置';
COMMENT ON COLUMN "jelly_export_data"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_export_data"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_export_data" IS '导入数据';

-- 2022-08-16
ALTER TABLE "jelly_export_data" ADD COLUMN user_id bigint DEFAULT NULL;
COMMENT ON COLUMN "jelly_export_data"."user_id" IS '用户id';

-- 2022-08-17
ALTER TABLE "jelly_meta_function" ADD COLUMN parent_id bigint DEFAULT NULL;
COMMENT ON COLUMN "jelly_meta_function"."parent_id" IS '父节点';

-- 2022-08-17
DROP TABLE IF EXISTS "jelly_export_dimension";
CREATE TABLE "jelly_export_dimension"  (
    "id" BIGSERIAL PRIMARY KEY NOT NULL,
    "odm_source" BIGINT NOT NULL,
    "odm_code_field" BIGINT NOT NULL,
    "odm_name_field" BIGINT NOT NULL,
    "dim_source" BIGINT NOT NULL,
    "dim_code_field" BIGINT NOT NULL,
    "dim_name_field" BIGINT NOT NULL,
    "dim_year_field" BIGINT DEFAULT NULL,
    "user_id" BIGINT NOT NULL,
    "company_id" BIGINT NOT NULL,
    "create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "jelly_export_dimension"."id" IS '主键';
COMMENT ON COLUMN "jelly_export_dimension"."odm_source" IS 'ODM表';
COMMENT ON COLUMN "jelly_export_dimension"."odm_code_field" IS 'ODM表字段编码';
COMMENT ON COLUMN "jelly_export_dimension"."odm_name_field" IS 'ODM表字段名称';
COMMENT ON COLUMN "jelly_export_dimension"."dim_source" IS 'DIM表';
COMMENT ON COLUMN "jelly_export_dimension"."dim_code_field" IS 'DIM表字段编码';
COMMENT ON COLUMN "jelly_export_dimension"."dim_name_field" IS 'DIM表字段名称';
COMMENT ON COLUMN "jelly_export_dimension"."dim_year_field" IS 'DIM表字段年段';
COMMENT ON COLUMN "jelly_export_dimension"."user_id" IS '用户id';
COMMENT ON COLUMN "jelly_export_dimension"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_export_dimension"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_export_dimension"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_export_dimension" IS '维度管理';

-- 2022-08-17
ALTER TABLE "jelly_export_dimension" ADD COLUMN name varchar(30) DEFAULT NULL;
COMMENT ON COLUMN "jelly_export_dimension"."name" IS '维度名称';


-- 2022-08-22
DROP TABLE IF EXISTS "jelly_inform";
CREATE TABLE "jelly_inform"  (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
  	"name" VARCHAR(100) NOT NULL,
	"icon" VARCHAR(30) NOT NULL,
	"color" VARCHAR(30) NOT NULL,
  	"data_source" BIGINT NOT NULL,
  	"template_source" text NOT NULL,
  	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "jelly_inform"."id" IS '主键';
COMMENT ON COLUMN "jelly_inform"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_inform"."name" IS '名称';
COMMENT ON COLUMN "jelly_inform"."icon" IS '图标';
COMMENT ON COLUMN "jelly_inform"."color" IS '颜色';
COMMENT ON COLUMN "jelly_inform"."data_source" IS '数据源';
COMMENT ON COLUMN "jelly_inform"."template_source" IS '模板源';
COMMENT ON COLUMN "jelly_inform"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_inform"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_inform" IS '报告模板';


-- 2022-08-22
ALTER TABLE "jelly_export_data" ADD COLUMN name VARCHAR(120) DEFAULT NULL;
COMMENT ON COLUMN "jelly_export_data"."name" IS '名称';


-- 2022-08-26
DROP TABLE IF EXISTS "jelly_procedure";
CREATE TABLE "jelly_procedure"  (
	"id" BIGSERIAL PRIMARY KEY NOT NULL,
	"company_id" BIGINT NOT NULL,
  	"name" VARCHAR(100) NOT NULL,
  	"remark" VARCHAR(200) NOT NULL,
  	"config" text,
  	"create_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"update_time" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN "jelly_procedure"."id" IS '主键';
COMMENT ON COLUMN "jelly_procedure"."company_id" IS '公司id';
COMMENT ON COLUMN "jelly_procedure"."name" IS '名称';
COMMENT ON COLUMN "jelly_procedure"."remark" IS '备注';
COMMENT ON COLUMN "jelly_procedure"."config" IS '配置';
COMMENT ON COLUMN "jelly_procedure"."create_time" IS '创建时间';
COMMENT ON COLUMN "jelly_procedure"."update_time" IS '更新时间';
COMMENT ON TABLE "jelly_procedure" IS '存储过程';

-- 2022-09-07
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

ALTER TABLE "jelly_inform" ADD COLUMN type INTEGER NOT NULL;
COMMENT ON COLUMN "jelly_inform"."type" IS '类型(1:word;2:excel;)';
ALTER TABLE "jelly_inform" ADD COLUMN code varchar(30) NOT NULL;
COMMENT ON COLUMN "jelly_inform"."code" IS '编码';

ALTER TABLE "jelly_inform" DROP icon;
ALTER TABLE "jelly_inform" DROP color;

-- 2022-09-26
ALTER TABLE "sys_menu" ADD COLUMN quick_name VARCHAR(30) DEFAULT NULL;
COMMENT ON COLUMN "sys_menu"."quick_name" IS '快捷方式名称';


-- 2022-10-18
ALTER TABLE jelly_export_rule_detail ADD COLUMN type INTEGER DEFAULT 1;
COMMENT ON COLUMN "jelly_export_rule_detail"."type" IS '字段转换类型(1无;2字典;3用户;4部门;5唯一字段;6地址)';
ALTER TABLE jelly_export_rule_detail ADD COLUMN source BIGINT DEFAULT NULL;
COMMENT ON COLUMN "jelly_export_rule_detail"."source" IS '字段转换来源';
ALTER TABLE jelly_export_rule ADD COLUMN business_rule_id BIGINT DEFAULT NULL;
COMMENT ON COLUMN "jelly_export_rule"."business_rule_id" IS '业务校验规则id';

-- 2022-10-20
ALTER TABLE "sys_notice" ADD COLUMN status int4 DEFAULT '1';
COMMENT ON COLUMN sys_notice.status IS '状态(0:暂存;1:已发布;)';
ALTER TABLE "sys_notice" ADD COLUMN classify varchar(30) DEFAULT '1';
COMMENT ON COLUMN sys_notice.classify IS '通知类型';
ALTER TABLE "sys_notice" ADD COLUMN relation TEXT DEFAULT NULL;
COMMENT ON COLUMN sys_notice.relation IS '关联通知';


-- 2022-11-02
ALTER TABLE "jelly_export_rule_detail" ADD COLUMN sql_source varchar(500) DEFAULT NULL;
COMMENT ON COLUMN "jelly_export_rule_detail"."sql_source" IS 'sql语句';

-- 2022-11-25
ALTER TABLE "jelly_export_rule" ADD COLUMN sz_code varchar(20) DEFAULT NULL;
COMMENT ON COLUMN "jelly_export_rule"."sz_code" IS '收支编码（收入01；支出02）';
ALTER TABLE "jelly_export_rule" ADD COLUMN sc_code varchar(20) DEFAULT NULL;
COMMENT ON COLUMN "jelly_export_rule"."sc_code" IS '资金性质';

-- 2022-12-16
DROP TABLE IF EXISTS "jelly_import_rule";
CREATE TABLE "jelly_import_rule"  (
    "id" BIGSERIAL PRIMARY KEY NOT NULL,
    "company_id" BIGINT NOT NULL,
    "code" varchar(30) NOT NULL,
    "name" varchar(100) NOT NULL,
    "data_source" bigint NOT NULL,
    "before_rule_id" bigint DEFAULT NULL,
    "after_rule_id" bigint DEFAULT NULL,
    "verify_rule_id" bigint DEFAULT NULL,
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

-- 2023-02-01
ALTER TABLE jelly_business_table rename comment to remark;
ALTER TABLE jelly_business_field rename comment to remark;
ALTER TABLE sea_definition rename resource to resources;
ALTER TABLE sea_definition rename resource to resources;
ALTER TABLE sea_node_detail rename comment to remark;