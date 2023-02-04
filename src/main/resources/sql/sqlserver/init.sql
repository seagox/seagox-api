-- ----------------------------
-- Table structure for jelly_import_rule
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_import_rule;
CREATE TABLE dbo.jelly_import_rule  (
    id bigint PRIMARY KEY IDENTITY(1,1),
    company_id bigint NOT NULL,
    code nvarchar(30) NOT NULL,
    name nvarchar(100) NOT NULL,
    data_source bigint NOT NULL,
    before_rule_id bigint DEFAULT NULL,
    after_rule_id bigint DEFAULT NULL,
    verify_rule_id bigint DEFAULT NULL,
    template_source text,
    create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  	update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'编码',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule',
'COLUMN', N'code'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'数据源',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule',
'COLUMN', N'data_source'
GO

EXEC sp_addextendedproperty
'MS_Description', N'导入之前规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule',
'COLUMN', N'before_rule_id'
GO


EXEC sp_addextendedproperty
'MS_Description', N'导入之后 规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule',
'COLUMN', N'after_rule_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'验证规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule',
'COLUMN', N'verify_rule_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'模板源',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule',
'COLUMN', N'template_source'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'导入规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule'
GO




-- ----------------------------
-- Table structure for jelly_import_rule_detail
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_import_rule_detail;
CREATE TABLE dbo.jelly_import_rule_detail  (
    id bigint PRIMARY KEY IDENTITY(1,1),
    rule_id bigint NOT NULL,
    field bigint NOT NULL,
    col nvarchar(30) NOT NULL,
    rule text,
    create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  	update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule_detail',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'导入规则id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule_detail',
'COLUMN', N'rule_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'对应字段',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule_detail',
'COLUMN', N'field'
GO

EXEC sp_addextendedproperty
'MS_Description', N'对应列',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule_detail',
'COLUMN', N'col'
GO

EXEC sp_addextendedproperty
'MS_Description', N'规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule_detail',
'COLUMN', N'rule'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule_detail',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule_detail',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'导入规则明细',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule_detail'
GO

-- ----------------------------
-- Table structure for jelly_open_api
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_open_api;
CREATE TABLE dbo.jelly_open_api  (
	id bigint PRIMARY KEY IDENTITY(1,1),
	company_id bigint NOT NULL,
  	appid nvarchar(30) NOT NULL,
  	secret nvarchar(50) NOT NULL,
  	remark nvarchar(200) NOT NULL,
  	create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  	update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO


EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_open_api',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_open_api',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'appid',
'SCHEMA', N'dbo',
'TABLE', N'jelly_open_api',
'COLUMN', N'appid'
GO

EXEC sp_addextendedproperty
'MS_Description', N'secret',
'SCHEMA', N'dbo',
'TABLE', N'jelly_open_api',
'COLUMN', N'secret'
GO

EXEC sp_addextendedproperty
'MS_Description', N'备注',
'SCHEMA', N'dbo',
'TABLE', N'jelly_open_api',
'COLUMN', N'remark'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_open_api',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_open_api',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'openApi',
'SCHEMA', N'dbo',
'TABLE', N'jelly_open_api'
GO


-- ----------------------------
-- Table structure for jelly_procedure
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_procedure;
CREATE TABLE dbo.jelly_procedure  (
	id bigint PRIMARY KEY IDENTITY(1,1),
	company_id bigint NOT NULL,
  	name nvarchar(100) NOT NULL,
  	remark nvarchar(200) NOT NULL,
  	config text,
  	create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  	update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO


EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_procedure',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_procedure',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_procedure',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'备注',
'SCHEMA', N'dbo',
'TABLE', N'jelly_procedure',
'COLUMN', N'remark'
GO

EXEC sp_addextendedproperty
'MS_Description', N'配置',
'SCHEMA', N'dbo',
'TABLE', N'jelly_procedure',
'COLUMN', N'config'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_procedure',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_procedure',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'存储过程',
'SCHEMA', N'dbo',
'TABLE', N'jelly_procedure'
GO

-- ----------------------------
-- Table structure for jelly_inform
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_inform;
CREATE TABLE dbo.jelly_inform  (
    id bigint PRIMARY KEY IDENTITY(1,1),
	company_id bigint NOT NULL,
	type int  NULL,
	code nvarchar(30) NOT NULL,
    name nvarchar(100) NOT NULL,
  	data_source bigint NOT NULL,
  	template_source text NOT NULL,
	create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  	update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_inform SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_inform',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_inform',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'类型(1:word;2:excel;)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_inform',
'COLUMN', N'type'
GO

EXEC sp_addextendedproperty
'MS_Description', N'编码',
'SCHEMA', N'dbo',
'TABLE', N'jelly_inform',
'COLUMN', N'code'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_inform',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'模板源',
'SCHEMA', N'dbo',
'TABLE', N'jelly_inform',
'COLUMN', N'template_source'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_inform',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_inform',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'报告模板',
'SCHEMA', N'dbo',
'TABLE', N'jelly_inform'
GO


-- ----------------------------
-- Table structure for jelly_export_data
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_export_data;
CREATE TABLE dbo.jelly_export_data  (
    id bigint PRIMARY KEY IDENTITY(1,1),
	company_id bigint NOT NULL,
	user_id bigint NOT NULL,
    config text NOT NULL,
	create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  	update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_export_data SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_data',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_data',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_data',
'COLUMN', N'user_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'配置',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_data',
'COLUMN', N'config'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_data',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_data',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'导入数据',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_data'
GO

-- ----------------------------
-- Table structure for jelly_export_rule_detail
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_export_rule_detail;
CREATE TABLE dbo.jelly_export_rule_detail  (
    id bigint PRIMARY KEY IDENTITY(1,1),
	company_id bigint NOT NULL,
	export_rule_id bigint NOT NULL,
    field bigint NOT NULL,
    col nvarchar(100) NOT NULL,
    type int DEFAULT 1,
    source bigint DEFAULT NULL,
    sql_source nvarchar(500) DEFAULT NULL,
	create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  	update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_export_rule_detail SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule_detail',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'导入规则id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule_detail',
'COLUMN', N'export_rule_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'编码',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule_detail',
'COLUMN', N'code'
GO

EXEC sp_addextendedproperty
'MS_Description', N'对应字段',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule_detail',
'COLUMN', N'field'
GO

EXEC sp_addextendedproperty
'MS_Description', N'对应列',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule_detail',
'COLUMN', N'col'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字段转换类型(1无;2字典;3用户;4部门;5唯一字段;6地址)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule_detail',
'COLUMN', N'type'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字段转换来源',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule_detail',
'COLUMN', N'source'
GO

EXEC sp_addextendedproperty
'MS_Description', N'sql语句',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule_detail',
'COLUMN', N'sql_source'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule_detail',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule_detail',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'导入规则明细',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule_detail'
GO

-- ----------------------------
-- Table structure for jelly_export_rule
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_export_rule;
CREATE TABLE dbo.jelly_export_rule  (
    id bigint PRIMARY KEY IDENTITY(1,1),
	company_id bigint NOT NULL,
	code bigint NOT NULL,
    name nvarchar(100) NOT NULL,
    sz_code nvarchar(20) NOT NULL,
    sc_code nvarchar(20) NOT NULL,
    data_source bigint NOT NULL,
    "business_rule_id" bigint DEFAULT NULL,
	create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  	update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_export_rule SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'编码',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule',
'COLUMN', N'code'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'收支编码（收入01；支出02）',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule',
'COLUMN', N'sz_code'
GO

EXEC sp_addextendedproperty
'MS_Description', N'资金性质',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule',
'COLUMN', N'sc_code'
GO

EXEC sp_addextendedproperty
'MS_Description', N'数据源',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule',
'COLUMN', N'data_source'
GO

EXEC sp_addextendedproperty
'MS_Description', N'业务校验规则id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule',
'COLUMN', N'business_rule_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'导入规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_export_rule'
GO

-- ----------------------------
-- Table structure for jelly_report
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_report;
CREATE TABLE dbo.jelly_report  (
    id bigint PRIMARY KEY IDENTITY(1,1),
	company_id bigint NOT NULL,
    name nvarchar(100) NOT NULL,
	icon nvarchar(30) NOT NULL,
	color nvarchar(30) NOT NULL,
    data_source bigint NOT NULL,
    template_source text NOT NULL,
    search_json text DEFAULT NULL,
	export_path text DEFAULT NULL,
	create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  	update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_business_field SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_report',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_report',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_report',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'图标',
'SCHEMA', N'dbo',
'TABLE', N'jelly_report',
'COLUMN', N'icon'
GO

EXEC sp_addextendedproperty
'MS_Description', N'颜色',
'SCHEMA', N'dbo',
'TABLE', N'jelly_report',
'COLUMN', N'color'
GO

EXEC sp_addextendedproperty
'MS_Description', N'数据源',
'SCHEMA', N'dbo',
'TABLE', N'jelly_report',
'COLUMN', N'data_source'
GO

EXEC sp_addextendedproperty
'MS_Description', N'模板源',
'SCHEMA', N'dbo',
'TABLE', N'jelly_report',
'COLUMN', N'template_source'
GO

EXEC sp_addextendedproperty
'MS_Description', N'搜索配置',
'SCHEMA', N'dbo',
'TABLE', N'jelly_report',
'COLUMN', N'search_json'
GO

EXEC sp_addextendedproperty
'MS_Description', N'导出路径',
'SCHEMA', N'dbo',
'TABLE', N'jelly_report',
'COLUMN', N'export_path'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_report',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_report',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'报表管理',
'SCHEMA', N'dbo',
'TABLE', N'jelly_report'
GO

-- ----------------------------
-- Table structure for jelly_business_field
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_business_field;
CREATE TABLE dbo.jelly_business_field (
  id bigint PRIMARY KEY IDENTITY(1,1),
  business_table_id bigint  NOT NULL,
  name nvarchar(64)  NOT NULL,
  remark nvarchar(64)  NOT NULL,
  type nvarchar(20)  NOT NULL,
  kind int  NULL,
  length int  NULL,
  decimals int  NULL,
  not_null int  NULL,
  default_value nvarchar(200)  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_business_field SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_field',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'业务表id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_field',
'COLUMN', N'business_table_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_field',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'注释',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_field',
'COLUMN', N'remark'
GO

EXEC sp_addextendedproperty
'MS_Description', N'类型',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_field',
'COLUMN', N'type'
GO

EXEC sp_addextendedproperty
'MS_Description', N'种类(1:基本类型;2:数据字典;3:单位;4:部门;5:用户;6:省市区;)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_field',
'COLUMN', N'kind'
GO

EXEC sp_addextendedproperty
'MS_Description', N'长度',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_field',
'COLUMN', N'length'
GO

EXEC sp_addextendedproperty
'MS_Description', N'小数',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_field',
'COLUMN', N'decimals'
GO

EXEC sp_addextendedproperty
'MS_Description', N'不为空(0:否;1:是;)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_field',
'COLUMN', N'not_null'
GO

EXEC sp_addextendedproperty
'MS_Description', N'默认值',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_field',
'COLUMN', N'default_value'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_field',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_field',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'业务字段',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_field'
GO


-- ----------------------------
-- Records of jelly_business_field
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_business_rule
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_business_rule;
CREATE TABLE dbo.jelly_business_rule (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  name nvarchar(30)  NOT NULL,
  script nvarchar(max)  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_business_rule SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_rule',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_rule',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_rule',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'规则脚本',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_rule',
'COLUMN', N'script'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_rule',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_rule',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'业务规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_rule'
GO


-- ----------------------------
-- Records of jelly_business_rule
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_business_table
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_business_table;
CREATE TABLE dbo.jelly_business_table (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  name nvarchar(64)  NOT NULL,
  remark nvarchar(64)  NOT NULL,
  is_virtual int  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_business_table SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_table',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_table',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_table',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'注释',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_table',
'COLUMN', N'remark'
GO

EXEC sp_addextendedproperty
'MS_Description', N'虚拟(1:是;0:否;)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_table',
'COLUMN', N'is_virtual'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_table',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_table',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'业务表',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_table'
GO


-- ----------------------------
-- Records of jelly_business_table
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_common_words
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_common_words;
CREATE TABLE dbo.jelly_common_words (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  user_id bigint  NOT NULL,
  name nvarchar(50)  NOT NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_common_words SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_common_words',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_common_words',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_common_words',
'COLUMN', N'user_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_common_words',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_common_words',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_common_words',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'常用语',
'SCHEMA', N'dbo',
'TABLE', N'jelly_common_words'
GO


-- ----------------------------
-- Records of jelly_common_words
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_data_sheet
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_data_sheet;
CREATE TABLE dbo.jelly_data_sheet (
  id bigint PRIMARY KEY IDENTITY(1,1),
  form_id bigint  NOT NULL,
  single_flag int  NULL,
  table_name nvarchar(50)  NOT NULL,
  sort int  NULL,
  relate_table nvarchar(50)  NULL,
  relate_field nvarchar(50)  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_data_sheet SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_data_sheet',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'表单id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_data_sheet',
'COLUMN', N'form_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'是否单条(1:是;2:否;)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_data_sheet',
'COLUMN', N'single_flag'
GO

EXEC sp_addextendedproperty
'MS_Description', N'数据表名',
'SCHEMA', N'dbo',
'TABLE', N'jelly_data_sheet',
'COLUMN', N'table_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'排序',
'SCHEMA', N'dbo',
'TABLE', N'jelly_data_sheet',
'COLUMN', N'sort'
GO

EXEC sp_addextendedproperty
'MS_Description', N'关联表',
'SCHEMA', N'dbo',
'TABLE', N'jelly_data_sheet',
'COLUMN', N'relate_table'
GO

EXEC sp_addextendedproperty
'MS_Description', N'关联字段',
'SCHEMA', N'dbo',
'TABLE', N'jelly_data_sheet',
'COLUMN', N'relate_field'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_data_sheet',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_data_sheet',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'数据表设置',
'SCHEMA', N'dbo',
'TABLE', N'jelly_data_sheet'
GO


-- ----------------------------
-- Records of jelly_data_sheet
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_dic_classify
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_dic_classify;
CREATE TABLE dbo.jelly_dic_classify (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  name nvarchar(30)  NOT NULL,
  sort int  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_dic_classify SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_dic_classify',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_dic_classify',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_dic_classify',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'排序',
'SCHEMA', N'dbo',
'TABLE', N'jelly_dic_classify',
'COLUMN', N'sort'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_dic_classify',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_dic_classify',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字典分类',
'SCHEMA', N'dbo',
'TABLE', N'jelly_dic_classify'
GO


-- ----------------------------
-- Records of jelly_dic_classify
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_dic_detail
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_dic_detail;
CREATE TABLE dbo.jelly_dic_detail (
  id bigint PRIMARY KEY IDENTITY(1,1),
  classify_id bigint  NOT NULL,
  code nvarchar(30)  NOT NULL,
  name nvarchar(30)  NOT NULL,
  sort int  NULL,
  status int  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_dic_detail SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_dic_detail',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字典分类id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_dic_detail',
'COLUMN', N'classify_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'编码',
'SCHEMA', N'dbo',
'TABLE', N'jelly_dic_detail',
'COLUMN', N'code'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_dic_detail',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'排序',
'SCHEMA', N'dbo',
'TABLE', N'jelly_dic_detail',
'COLUMN', N'sort'
GO

EXEC sp_addextendedproperty
'MS_Description', N'状态((0:禁用：1:启用)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_dic_detail',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_dic_detail',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_dic_detail',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字典详情',
'SCHEMA', N'dbo',
'TABLE', N'jelly_dic_detail'
GO


-- ----------------------------
-- Records of jelly_dic_detail
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_disk
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_disk;
CREATE TABLE dbo.jelly_disk (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  user_id bigint  NOT NULL,
  parent_id bigint  NULL,
  type int  NULL,
  name nvarchar(225)  NOT NULL,
  path nvarchar(max)  NOT NULL,
  link nvarchar(225)  NULL,
  text nvarchar(max)  NULL,
  capacity int  NULL,
  status int  NULL,
  to_user_ids nvarchar(max)  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_disk SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk',
'COLUMN', N'user_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'上级id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk',
'COLUMN', N'parent_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'类型(1:文件夹;2:图片;3:word;4:excel;5:ppt;6:pdf;7:压缩文件;8:txt;9:文档(富文本);10:视频;11:音乐;12:其他;13:文档(markdown);)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk',
'COLUMN', N'type'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'路径(根目录:/)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk',
'COLUMN', N'path'
GO

EXEC sp_addextendedproperty
'MS_Description', N'链接',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk',
'COLUMN', N'link'
GO

EXEC sp_addextendedproperty
'MS_Description', N'文本',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk',
'COLUMN', N'text'
GO

EXEC sp_addextendedproperty
'MS_Description', N'大小',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk',
'COLUMN', N'capacity'
GO

EXEC sp_addextendedproperty
'MS_Description', N'状态(1:正常;2;回收站;3:删除;)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'分享模板用户ids',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk',
'COLUMN', N'to_user_ids'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'网盘',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk'
GO


-- ----------------------------
-- Records of jelly_disk
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_disk_recycle
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_disk_recycle;
CREATE TABLE dbo.jelly_disk_recycle (
  id bigint PRIMARY KEY IDENTITY(1,1),
  disk_id bigint  NOT NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_disk_recycle SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk_recycle',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'文件id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk_recycle',
'COLUMN', N'disk_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk_recycle',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'回收站',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk_recycle'
GO


-- ----------------------------
-- Records of jelly_disk_recycle
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_disk_share
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_disk_share;
CREATE TABLE dbo.jelly_disk_share (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  disk_id bigint  NOT NULL,
  user_id bigint  NOT NULL,
  share_user_id bigint  NOT NULL,
  share_time datetime2(7)  NULL
)
GO

ALTER TABLE dbo.jelly_disk_share SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk_share',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk_share',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'文件id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk_share',
'COLUMN', N'disk_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'所属用户id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk_share',
'COLUMN', N'user_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'分享用户id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk_share',
'COLUMN', N'share_user_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'分享时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk_share',
'COLUMN', N'share_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'他人分享',
'SCHEMA', N'dbo',
'TABLE', N'jelly_disk_share'
GO


-- ----------------------------
-- Records of jelly_disk_share
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_door
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_door;
CREATE TABLE dbo.jelly_door (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  name nvarchar(30)  NOT NULL,
  config nvarchar(max)  NULL,
  authority nvarchar(max)  NULL,
  path bigint,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_door SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_door',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_door',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_door',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'配置',
'SCHEMA', N'dbo',
'TABLE', N'jelly_door',
'COLUMN', N'config'
GO

EXEC sp_addextendedproperty
'MS_Description', N'权限',
'SCHEMA', N'dbo',
'TABLE', N'jelly_door',
'COLUMN', N'authority'
GO

EXEC sp_addextendedproperty
'MS_Description', N'路径',
'SCHEMA', N'dbo',
'TABLE', N'jelly_door',
'COLUMN', N'path'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_door',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_door',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'门户管理',
'SCHEMA', N'dbo',
'TABLE', N'jelly_door'
GO


-- ----------------------------
-- Records of jelly_door
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_file_chunk
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_file_chunk;
CREATE TABLE dbo.jelly_file_chunk (
  id bigint PRIMARY KEY IDENTITY(1,1),
  chunk_number int  NULL,
  chunk_size real  NULL,
  current_chunk_size real  NULL,
  total_chunk int  NULL,
  identifier nvarchar(45)  NULL,
  file_name nvarchar(255)  NULL,
  file_type int  NULL,
  relative_path nvarchar(255)  NULL,
  part_e_tag nvarchar(500)  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_file_chunk SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_file_chunk',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'当前分片，从1开始',
'SCHEMA', N'dbo',
'TABLE', N'jelly_file_chunk',
'COLUMN', N'chunk_number'
GO

EXEC sp_addextendedproperty
'MS_Description', N'分片大小',
'SCHEMA', N'dbo',
'TABLE', N'jelly_file_chunk',
'COLUMN', N'chunk_size'
GO

EXEC sp_addextendedproperty
'MS_Description', N'当前分片大小',
'SCHEMA', N'dbo',
'TABLE', N'jelly_file_chunk',
'COLUMN', N'current_chunk_size'
GO

EXEC sp_addextendedproperty
'MS_Description', N'总分片数',
'SCHEMA', N'dbo',
'TABLE', N'jelly_file_chunk',
'COLUMN', N'total_chunk'
GO

EXEC sp_addextendedproperty
'MS_Description', N'文件标识',
'SCHEMA', N'dbo',
'TABLE', N'jelly_file_chunk',
'COLUMN', N'identifier'
GO

EXEC sp_addextendedproperty
'MS_Description', N'文件名',
'SCHEMA', N'dbo',
'TABLE', N'jelly_file_chunk',
'COLUMN', N'file_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'文件类型(1:minio;2:阿里云)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_file_chunk',
'COLUMN', N'file_type'
GO

EXEC sp_addextendedproperty
'MS_Description', N'相对路径',
'SCHEMA', N'dbo',
'TABLE', N'jelly_file_chunk',
'COLUMN', N'relative_path'
GO

EXEC sp_addextendedproperty
'MS_Description', N'分片信息',
'SCHEMA', N'dbo',
'TABLE', N'jelly_file_chunk',
'COLUMN', N'part_e_tag'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_file_chunk',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_file_chunk',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'文件分片',
'SCHEMA', N'dbo',
'TABLE', N'jelly_file_chunk'
GO


-- ----------------------------
-- Records of jelly_file_chunk
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_form
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_form;
CREATE TABLE dbo.jelly_form (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  design_ids nvarchar(255)  NOT NULL,
  name nvarchar(30)  NOT NULL,
  icon nvarchar(30)  NOT NULL,
  color nvarchar(30)  NOT NULL,
  data_source nvarchar(max)  NULL,
  search_json nvarchar(max)  NULL,
  table_header bigint  NOT NULL,
  list_export_path nvarchar(max)  NULL,
  detail_export_path nvarchar(max)  NULL,
  flow_id bigint  NULL,
  insert_before_rule bigint  NULL,
  insert_after_rule bigint  NULL,
  update_before_rule bigint  NULL,
  update_after_rule bigint  NULL,
  delete_before_rule bigint  NULL,
  delete_after_rule bigint  NULL,
  process_end_rule bigint  NULL,
  abandon_rule bigint  NULL,
	export_rule bigint  NULL,
  options nvarchar(max)  NULL,
  relate_search_json nvarchar(max)  NULL,
  data_title nvarchar(255)  NULL,
  authority nvarchar(max)  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_form SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'设计ids',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'design_ids'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'图标',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'icon'
GO

EXEC sp_addextendedproperty
'MS_Description', N'颜色',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'color'
GO

EXEC sp_addextendedproperty
'MS_Description', N'数据源配置',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'data_source'
GO

EXEC sp_addextendedproperty
'MS_Description', N'搜索配置',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'search_json'
GO

EXEC sp_addextendedproperty
'MS_Description', N'表格表头',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'table_header'
GO

EXEC sp_addextendedproperty
'MS_Description', N'列表导出文件路径',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'list_export_path'
GO

EXEC sp_addextendedproperty
'MS_Description', N'详情导出文件路径',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'detail_export_path'
GO

EXEC sp_addextendedproperty
'MS_Description', N'流程id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'flow_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'新增前规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'insert_before_rule'
GO

EXEC sp_addextendedproperty
'MS_Description', N'新增后规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'insert_after_rule'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新前规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'update_before_rule'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新后规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'update_after_rule'
GO

EXEC sp_addextendedproperty
'MS_Description', N'删除前规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'delete_before_rule'
GO

EXEC sp_addextendedproperty
'MS_Description', N'删除后规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'delete_after_rule'
GO

EXEC sp_addextendedproperty
'MS_Description', N'流程结束后规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'process_end_rule'
GO

EXEC sp_addextendedproperty
'MS_Description', N'弃审规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'abandon_rule'
GO

EXEC sp_addextendedproperty
'MS_Description', N'导出规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'export_rule'
GO

EXEC sp_addextendedproperty
'MS_Description', N'其他参数json',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'options'
GO

EXEC sp_addextendedproperty
'MS_Description', N'联查json',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'relate_search_json'
GO

EXEC sp_addextendedproperty
'MS_Description', N'数据标题',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'data_title'
GO

EXEC sp_addextendedproperty
'MS_Description', N'权限',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'authority'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'表单管理',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form'
GO


-- ----------------------------
-- Records of jelly_form
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_form_design
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_form_design;
CREATE TABLE dbo.jelly_form_design (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  type int  NOT NULL,
  name nvarchar(30)  NOT NULL,
  excel_json nvarchar(max)  NULL,
  data_source nvarchar(255)  NOT NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_form_design SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form_design',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form_design',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'类型(1:简化版;2:高级版;)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form_design',
'COLUMN', N'type'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form_design',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'excel配置',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form_design',
'COLUMN', N'excel_json'
GO

EXEC sp_addextendedproperty
'MS_Description', N'数据源配置',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form_design',
'COLUMN', N'data_source'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form_design',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form_design',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'表单设计',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form_design'
GO


-- ----------------------------
-- Records of jelly_form_design
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_gauge
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_gauge;
CREATE TABLE dbo.jelly_gauge (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  name nvarchar(30)  NOT NULL,
  config nvarchar(max)  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_gauge SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_gauge',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_gauge',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_gauge',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'配置',
'SCHEMA', N'dbo',
'TABLE', N'jelly_gauge',
'COLUMN', N'config'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_gauge',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_gauge',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'仪表板',
'SCHEMA', N'dbo',
'TABLE', N'jelly_gauge'
GO


-- ----------------------------
-- Records of jelly_gauge
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_job
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_job;
CREATE TABLE dbo.jelly_job (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  name nvarchar(30)  NOT NULL,
  cron nvarchar(30)  NOT NULL,
  script nvarchar(max)  NOT NULL,
  status int  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_job SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_job',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_job',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_job',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'表达式',
'SCHEMA', N'dbo',
'TABLE', N'jelly_job',
'COLUMN', N'cron'
GO

EXEC sp_addextendedproperty
'MS_Description', N'规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_job',
'COLUMN', N'script'
GO

EXEC sp_addextendedproperty
'MS_Description', N'状态(0:未启动;1:已启动;)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_job',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_job',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_job',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'任务调度',
'SCHEMA', N'dbo',
'TABLE', N'jelly_job'
GO


-- ----------------------------
-- Records of jelly_job
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_meta_function
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_meta_function;
CREATE TABLE dbo.jelly_meta_function (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  parent_id bigint  NULL,
  name nvarchar(30)  NOT NULL,
  path nvarchar(30)  NOT NULL,
  script nvarchar(max)  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_meta_function SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_function',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_function',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'父节点',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_function',
'COLUMN', N'parent_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_function',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'路径',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_function',
'COLUMN', N'path'
GO

EXEC sp_addextendedproperty
'MS_Description', N'脚本',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_function',
'COLUMN', N'script'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_function',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_function',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'元函数',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_function'
GO


-- ----------------------------
-- Records of jelly_meta_function
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_meta_page
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_meta_page;
CREATE TABLE dbo.jelly_meta_page (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  name nvarchar(30)  NOT NULL,
  path nvarchar(30)  NOT NULL,
  html nvarchar(max)  NULL,
  js nvarchar(max)  NULL,
  css nvarchar(max)  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_meta_page SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_page',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_page',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_page',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'路径',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_page',
'COLUMN', N'path'
GO

EXEC sp_addextendedproperty
'MS_Description', N'html脚本',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_page',
'COLUMN', N'html'
GO

EXEC sp_addextendedproperty
'MS_Description', N'js脚本',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_page',
'COLUMN', N'js'
GO

EXEC sp_addextendedproperty
'MS_Description', N'css脚本',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_page',
'COLUMN', N'css'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_page',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_page',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'元页面',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_page'
GO


-- ----------------------------
-- Records of jelly_meta_page
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_print
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_print;
CREATE TABLE dbo.jelly_print (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  name nvarchar(30)  NOT NULL,
  excel_json nvarchar(max)  NULL,
  data_source nvarchar(255)  NOT NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_print SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_print',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_print',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_print',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'excel配置',
'SCHEMA', N'dbo',
'TABLE', N'jelly_print',
'COLUMN', N'excel_json'
GO

EXEC sp_addextendedproperty
'MS_Description', N'数据源配置',
'SCHEMA', N'dbo',
'TABLE', N'jelly_print',
'COLUMN', N'data_source'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_print',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_print',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'打印模版',
'SCHEMA', N'dbo',
'TABLE', N'jelly_print'
GO


-- ----------------------------
-- Records of jelly_print
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_regions
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_regions;
CREATE TABLE dbo.jelly_regions (
  id bigint PRIMARY KEY IDENTITY(1,1),
  code nvarchar(30)  NOT NULL,
  grade int  NOT NULL,
  name nvarchar(50)  NOT NULL
)
GO

ALTER TABLE dbo.jelly_regions SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_regions',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'编码',
'SCHEMA', N'dbo',
'TABLE', N'jelly_regions',
'COLUMN', N'code'
GO

EXEC sp_addextendedproperty
'MS_Description', N'等级',
'SCHEMA', N'dbo',
'TABLE', N'jelly_regions',
'COLUMN', N'grade'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_regions',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'区域数据',
'SCHEMA', N'dbo',
'TABLE', N'jelly_regions'
GO


-- ----------------------------
-- Records of jelly_regions
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_table_classify
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_table_classify;
CREATE TABLE dbo.jelly_table_classify (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  name nvarchar(30)  NOT NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_table_classify SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_classify',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_classify',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_classify',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_classify',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_classify',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'表头分类',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_classify'
GO


-- ----------------------------
-- Records of jelly_table_classify
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_table_column
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_table_column;
CREATE TABLE dbo.jelly_table_column (
  id bigint PRIMARY KEY IDENTITY(1,1),
  classify_id bigint  NOT NULL,
  parent_id bigint  NULL,
  prop nvarchar(30)  NOT NULL,
  label nvarchar(30)  NOT NULL,
  width int  NULL,
  locking int  NULL,
  summary int  NULL,
  total int  NULL,
  target int  NULL,
  formatter bigint  NULL,
  options nvarchar(max)  NULL,
  sort int  NULL,
  formula nvarchar(max)  NULL,
  router_json nvarchar(max)  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_table_column SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'分类id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column',
'COLUMN', N'classify_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'上级id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column',
'COLUMN', N'parent_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字段名',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column',
'COLUMN', N'prop'
GO

EXEC sp_addextendedproperty
'MS_Description', N'标题',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column',
'COLUMN', N'label'
GO

EXEC sp_addextendedproperty
'MS_Description', N'宽度',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column',
'COLUMN', N'width'
GO

EXEC sp_addextendedproperty
'MS_Description', N'锁定(1:左;2:右;3:无)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column',
'COLUMN', N'locking'
GO

EXEC sp_addextendedproperty
'MS_Description', N'汇总(1:是;2:否;)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column',
'COLUMN', N'summary'
GO

EXEC sp_addextendedproperty
'MS_Description', N'汇总(1:是;2:否;)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column',
'COLUMN', N'total'
GO

EXEC sp_addextendedproperty
'MS_Description', N'格式对象(0:无;1:数据字典;2:区域数据;)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column',
'COLUMN', N'target'
GO

EXEC sp_addextendedproperty
'MS_Description', N'格式化',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column',
'COLUMN', N'formatter'
GO

EXEC sp_addextendedproperty
'MS_Description', N'格式化数据源',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column',
'COLUMN', N'options'
GO

EXEC sp_addextendedproperty
'MS_Description', N'排序',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column',
'COLUMN', N'sort'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公式',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column',
'COLUMN', N'formula'
GO

EXEC sp_addextendedproperty
'MS_Description', N'路由json',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column',
'COLUMN', N'router_json'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'表格表头',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column'
GO


-- ----------------------------
-- Records of jelly_table_column
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_table_column_config
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_table_column_config;
CREATE TABLE dbo.jelly_table_column_config (
  id bigint PRIMARY KEY IDENTITY(1,1),
  user_id bigint  NOT NULL,
  table_column_id bigint  NOT NULL,
  display int  NULL,
  sort int  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_table_column_config SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column_config',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column_config',
'COLUMN', N'user_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'表头id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column_config',
'COLUMN', N'table_column_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'显示(1:显示;2:隐藏;)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column_config',
'COLUMN', N'display'
GO

EXEC sp_addextendedproperty
'MS_Description', N'排序',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column_config',
'COLUMN', N'sort'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column_config',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column_config',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'表头配置',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column_config'
GO


-- ----------------------------
-- Records of jelly_table_column_config
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for jelly_template_engine
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_template_engine;
CREATE TABLE dbo.jelly_template_engine (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  name nvarchar(30)  NOT NULL,
  path nvarchar(30)  NOT NULL,
  script nvarchar(max)  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_template_engine SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'jelly_template_engine',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_template_engine',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'jelly_template_engine',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'路径',
'SCHEMA', N'dbo',
'TABLE', N'jelly_template_engine',
'COLUMN', N'path'
GO

EXEC sp_addextendedproperty
'MS_Description', N'脚本',
'SCHEMA', N'dbo',
'TABLE', N'jelly_template_engine',
'COLUMN', N'script'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_template_engine',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'jelly_template_engine',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'模版引擎',
'SCHEMA', N'dbo',
'TABLE', N'jelly_template_engine'
GO


-- ----------------------------
-- Records of jelly_template_engine
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for sea_definition
-- ----------------------------
DROP TABLE IF EXISTS dbo.sea_definition;
CREATE TABLE dbo.sea_definition (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  name nvarchar(30)  NOT NULL,
  resources nvarchar(max)  NULL,
  data_source nvarchar(255)  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.sea_definition SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'sea_definition',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'sea_definition',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'sea_definition',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'流程文件',
'SCHEMA', N'dbo',
'TABLE', N'sea_definition',
'COLUMN', N'resources'
GO

EXEC sp_addextendedproperty
'MS_Description', N'数据源配置',
'SCHEMA', N'dbo',
'TABLE', N'sea_definition',
'COLUMN', N'data_source'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'sea_definition',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'sea_definition',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'流程定义',
'SCHEMA', N'dbo',
'TABLE', N'sea_definition'
GO


-- ----------------------------
-- Records of sea_definition
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for sea_instance
-- ----------------------------
DROP TABLE IF EXISTS dbo.sea_instance;
CREATE TABLE dbo.sea_instance (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  user_id bigint  NOT NULL,
  version int  NULL,
  name nvarchar(50)  NOT NULL,
  business_type nvarchar(50)  NOT NULL,
  business_key nvarchar(50)  NOT NULL,
  resources nvarchar(max)  NOT NULL,
  status int  NULL,
  start_time datetime2(7)  NULL,
  end_time datetime2(7)  NULL,
  current_agent nvarchar(max)  NULL,
  return_number int  NULL,
  close_status int  NULL,
  other_json nvarchar(max)  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.sea_instance SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'sea_instance',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'sea_instance',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户id',
'SCHEMA', N'dbo',
'TABLE', N'sea_instance',
'COLUMN', N'user_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'版本',
'SCHEMA', N'dbo',
'TABLE', N'sea_instance',
'COLUMN', N'version'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'sea_instance',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'业务类型',
'SCHEMA', N'dbo',
'TABLE', N'sea_instance',
'COLUMN', N'business_type'
GO

EXEC sp_addextendedproperty
'MS_Description', N'业务key',
'SCHEMA', N'dbo',
'TABLE', N'sea_instance',
'COLUMN', N'business_key'
GO

EXEC sp_addextendedproperty
'MS_Description', N'流程文件',
'SCHEMA', N'dbo',
'TABLE', N'sea_instance',
'COLUMN', N'resources'
GO

EXEC sp_addextendedproperty
'MS_Description', N'状态(0:待审;1:通过;2:驳回;3:撤回;4:关闭)',
'SCHEMA', N'dbo',
'TABLE', N'sea_instance',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'开始时间',
'SCHEMA', N'dbo',
'TABLE', N'sea_instance',
'COLUMN', N'start_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'结束时间',
'SCHEMA', N'dbo',
'TABLE', N'sea_instance',
'COLUMN', N'end_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'当前代办人',
'SCHEMA', N'dbo',
'TABLE', N'sea_instance',
'COLUMN', N'current_agent'
GO

EXEC sp_addextendedproperty
'MS_Description', N'退回次数',
'SCHEMA', N'dbo',
'TABLE', N'sea_instance',
'COLUMN', N'return_number'
GO

EXEC sp_addextendedproperty
'MS_Description', N'关闭时流程状态',
'SCHEMA', N'dbo',
'TABLE', N'sea_instance',
'COLUMN', N'close_status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'其它json值',
'SCHEMA', N'dbo',
'TABLE', N'sea_instance',
'COLUMN', N'other_json'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'sea_instance',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'sea_instance',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'流程实例',
'SCHEMA', N'dbo',
'TABLE', N'sea_instance'
GO


-- ----------------------------
-- Records of sea_instance
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for sea_node
-- ----------------------------
DROP TABLE IF EXISTS dbo.sea_node;
CREATE TABLE dbo.sea_node (
  id bigint PRIMARY KEY IDENTITY(1,1),
  def_id bigint  NOT NULL,
  version int  NULL,
  source nvarchar(50)  NOT NULL,
  target nvarchar(50)  NOT NULL,
  name nvarchar(50)  NOT NULL,
  type int  NULL,
  status int  NULL,
  start_time datetime2(7)  NULL,
  end_time datetime2(7)  NULL,
  is_concurrent int  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.sea_node SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'sea_node',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'流程实例id',
'SCHEMA', N'dbo',
'TABLE', N'sea_node',
'COLUMN', N'def_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'版本',
'SCHEMA', N'dbo',
'TABLE', N'sea_node',
'COLUMN', N'version'
GO

EXEC sp_addextendedproperty
'MS_Description', N'源节点',
'SCHEMA', N'dbo',
'TABLE', N'sea_node',
'COLUMN', N'source'
GO

EXEC sp_addextendedproperty
'MS_Description', N'目标节点',
'SCHEMA', N'dbo',
'TABLE', N'sea_node',
'COLUMN', N'target'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'sea_node',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'类型(1:审批;2:抄送;3:撤回;4:驳回;)',
'SCHEMA', N'dbo',
'TABLE', N'sea_node',
'COLUMN', N'type'
GO

EXEC sp_addextendedproperty
'MS_Description', N'状态(0:待办;1:通过;2:不通过;3:作废;)',
'SCHEMA', N'dbo',
'TABLE', N'sea_node',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'开始时间',
'SCHEMA', N'dbo',
'TABLE', N'sea_node',
'COLUMN', N'start_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'结束时间',
'SCHEMA', N'dbo',
'TABLE', N'sea_node',
'COLUMN', N'end_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'并行网关(0:否;1:是;)',
'SCHEMA', N'dbo',
'TABLE', N'sea_node',
'COLUMN', N'is_concurrent'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'sea_node',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'sea_node',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'流程节点',
'SCHEMA', N'dbo',
'TABLE', N'sea_node'
GO


-- ----------------------------
-- Records of sea_node
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for sea_node_detail
-- ----------------------------
DROP TABLE IF EXISTS dbo.sea_node_detail;
CREATE TABLE dbo.sea_node_detail (
  id bigint PRIMARY KEY IDENTITY(1,1),
  node_id bigint  NOT NULL,
  name nvarchar(30)  NOT NULL,
  assignee nvarchar(30)  NOT NULL,
  status int  NULL,
  remark nvarchar(255)  NULL,
  start_time datetime2(7)  NULL,
  end_time datetime2(7)  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.sea_node_detail SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'sea_node_detail',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'流程节点id',
'SCHEMA', N'dbo',
'TABLE', N'sea_node_detail',
'COLUMN', N'node_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'sea_node_detail',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'签收人或被委托id',
'SCHEMA', N'dbo',
'TABLE', N'sea_node_detail',
'COLUMN', N'assignee'
GO

EXEC sp_addextendedproperty
'MS_Description', N'状态(0:待办;1:同意;2:不同意;3:已阅;4:撤回;5:重新发起;6:弃审;7:关闭;8:激活;)',
'SCHEMA', N'dbo',
'TABLE', N'sea_node_detail',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'评论',
'SCHEMA', N'dbo',
'TABLE', N'sea_node_detail',
'COLUMN', N'remark'
GO

EXEC sp_addextendedproperty
'MS_Description', N'开始时间',
'SCHEMA', N'dbo',
'TABLE', N'sea_node_detail',
'COLUMN', N'start_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'结束时间',
'SCHEMA', N'dbo',
'TABLE', N'sea_node_detail',
'COLUMN', N'end_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'sea_node_detail',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'sea_node_detail',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'流程节点详情',
'SCHEMA', N'dbo',
'TABLE', N'sea_node_detail'
GO


-- ----------------------------
-- Records of sea_node_detail
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for sys_account
-- ----------------------------
DROP TABLE IF EXISTS dbo.sys_account;
CREATE TABLE dbo.sys_account (
  id bigint PRIMARY KEY IDENTITY(1,1),
  avatar nvarchar(255)  NULL,
  account nvarchar(50)  NOT NULL,
  email nvarchar(50)  NULL,
  phone nvarchar(30)  NULL,
  name nvarchar(50)  NOT NULL,
  sex int  NULL,
  password nvarchar(255)  NOT NULL,
  position nvarchar(50)  NULL,
  status int  NULL,
  type int  NULL,
  openid nvarchar(50)  NULL,
  sort int  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.sys_account SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'sys_account',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'头像',
'SCHEMA', N'dbo',
'TABLE', N'sys_account',
'COLUMN', N'avatar'
GO

EXEC sp_addextendedproperty
'MS_Description', N'账号',
'SCHEMA', N'dbo',
'TABLE', N'sys_account',
'COLUMN', N'account'
GO

EXEC sp_addextendedproperty
'MS_Description', N'邮箱',
'SCHEMA', N'dbo',
'TABLE', N'sys_account',
'COLUMN', N'email'
GO

EXEC sp_addextendedproperty
'MS_Description', N'手机号',
'SCHEMA', N'dbo',
'TABLE', N'sys_account',
'COLUMN', N'phone'
GO

EXEC sp_addextendedproperty
'MS_Description', N'姓名',
'SCHEMA', N'dbo',
'TABLE', N'sys_account',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'性别(1:男;2:女;)',
'SCHEMA', N'dbo',
'TABLE', N'sys_account',
'COLUMN', N'sex'
GO

EXEC sp_addextendedproperty
'MS_Description', N'密码',
'SCHEMA', N'dbo',
'TABLE', N'sys_account',
'COLUMN', N'password'
GO

EXEC sp_addextendedproperty
'MS_Description', N'职位',
'SCHEMA', N'dbo',
'TABLE', N'sys_account',
'COLUMN', N'position'
GO

EXEC sp_addextendedproperty
'MS_Description', N'状态(1:启用;2:禁用;)',
'SCHEMA', N'dbo',
'TABLE', N'sys_account',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'类型(1:普通成员;2:管理员;3:超级管理员;)',
'SCHEMA', N'dbo',
'TABLE', N'sys_account',
'COLUMN', N'type'
GO

EXEC sp_addextendedproperty
'MS_Description', N'openid',
'SCHEMA', N'dbo',
'TABLE', N'sys_account',
'COLUMN', N'openid'
GO

EXEC sp_addextendedproperty
'MS_Description', N'排序',
'SCHEMA', N'dbo',
'TABLE', N'sys_account',
'COLUMN', N'sort'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_account',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_account',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户',
'SCHEMA', N'dbo',
'TABLE', N'sys_account'
GO


-- ----------------------------
-- Table structure for sys_company
-- ----------------------------
DROP TABLE IF EXISTS dbo.sys_company;
CREATE TABLE dbo.sys_company (
  id bigint PRIMARY KEY IDENTITY(1,1),
  parent_id bigint  NULL,
  mark nvarchar(30)  NOT NULL,
  code nvarchar(30)  NOT NULL,
  name nvarchar(30)  NOT NULL,
  alias nvarchar(30)  NOT NULL,
  logo nvarchar(100)  NOT NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.sys_company SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'sys_company',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'上级id',
'SCHEMA', N'dbo',
'TABLE', N'sys_company',
'COLUMN', N'parent_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'标识',
'SCHEMA', N'dbo',
'TABLE', N'sys_company',
'COLUMN', N'mark'
GO

EXEC sp_addextendedproperty
'MS_Description', N'编码',
'SCHEMA', N'dbo',
'TABLE', N'sys_company',
'COLUMN', N'code'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'sys_company',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'简称',
'SCHEMA', N'dbo',
'TABLE', N'sys_company',
'COLUMN', N'alias'
GO

EXEC sp_addextendedproperty
'MS_Description', N'logo',
'SCHEMA', N'dbo',
'TABLE', N'sys_company',
'COLUMN', N'logo'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_company',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_company',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司',
'SCHEMA', N'dbo',
'TABLE', N'sys_company'
GO


-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS dbo.sys_department;
CREATE TABLE dbo.sys_department (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  parent_id bigint  NULL,
  code nvarchar(30)  NOT NULL,
  name nvarchar(30)  NOT NULL,
  director nvarchar(500)  NULL,
  charge_leader nvarchar(500)  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.sys_department SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'sys_department',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'sys_department',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'上级id',
'SCHEMA', N'dbo',
'TABLE', N'sys_department',
'COLUMN', N'parent_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'编码',
'SCHEMA', N'dbo',
'TABLE', N'sys_department',
'COLUMN', N'code'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'sys_department',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'直接主管',
'SCHEMA', N'dbo',
'TABLE', N'sys_department',
'COLUMN', N'director'
GO

EXEC sp_addextendedproperty
'MS_Description', N'分管领导',
'SCHEMA', N'dbo',
'TABLE', N'sys_department',
'COLUMN', N'charge_leader'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_department',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_department',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'部门',
'SCHEMA', N'dbo',
'TABLE', N'sys_department'
GO


-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS dbo.sys_log;
CREATE TABLE dbo.sys_log (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  user_id bigint  NOT NULL,
  name nvarchar(50)  NOT NULL,
  ip nvarchar(50)  NOT NULL,
  uri nvarchar(50)  NOT NULL,
  method nvarchar(255)  NOT NULL,
  params nvarchar(max)  NULL,
  ua nvarchar(500)  NULL,
  status int  NULL,
  cost_time int  NOT NULL,
  result nvarchar(max)  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.sys_log SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'sys_log',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'sys_log',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户id',
'SCHEMA', N'dbo',
'TABLE', N'sys_log',
'COLUMN', N'user_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'操作名称',
'SCHEMA', N'dbo',
'TABLE', N'sys_log',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'ip',
'SCHEMA', N'dbo',
'TABLE', N'sys_log',
'COLUMN', N'ip'
GO

EXEC sp_addextendedproperty
'MS_Description', N'uri',
'SCHEMA', N'dbo',
'TABLE', N'sys_log',
'COLUMN', N'uri'
GO

EXEC sp_addextendedproperty
'MS_Description', N'方法',
'SCHEMA', N'dbo',
'TABLE', N'sys_log',
'COLUMN', N'method'
GO

EXEC sp_addextendedproperty
'MS_Description', N'请求参数',
'SCHEMA', N'dbo',
'TABLE', N'sys_log',
'COLUMN', N'params'
GO

EXEC sp_addextendedproperty
'MS_Description', N'浏览器信息',
'SCHEMA', N'dbo',
'TABLE', N'sys_log',
'COLUMN', N'ua'
GO

EXEC sp_addextendedproperty
'MS_Description', N'状态(1:成功;2:失败;)',
'SCHEMA', N'dbo',
'TABLE', N'sys_log',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'花费时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_log',
'COLUMN', N'cost_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'返回结果',
'SCHEMA', N'dbo',
'TABLE', N'sys_log',
'COLUMN', N'result'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_log',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'操作日记',
'SCHEMA', N'dbo',
'TABLE', N'sys_log'
GO


-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS dbo.sys_menu;
CREATE TABLE dbo.sys_menu (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  parent_id bigint  NULL,
  type int  NULL,
  name nvarchar(30)  NOT NULL,
  icon nvarchar(30)  NOT NULL,
  path nvarchar(50)  NULL,
  status int  NULL,
  sort int  NULL,
  quick_name nvarchar(30)  NOT NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.sys_menu SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'sys_menu',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'sys_menu',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'上级id',
'SCHEMA', N'dbo',
'TABLE', N'sys_menu',
'COLUMN', N'parent_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'类型(1:列表;2:按钮;3:新增页面;4:系统菜单;5:目录;6:仪表板;7:自定义页面;8:报表;9:单页面;)',
'SCHEMA', N'dbo',
'TABLE', N'sys_menu',
'COLUMN', N'type'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'sys_menu',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'图标',
'SCHEMA', N'dbo',
'TABLE', N'sys_menu',
'COLUMN', N'icon'
GO

EXEC sp_addextendedproperty
'MS_Description', N'路径',
'SCHEMA', N'dbo',
'TABLE', N'sys_menu',
'COLUMN', N'path'
GO

EXEC sp_addextendedproperty
'MS_Description', N'状态(1:启用;2:禁用;)',
'SCHEMA', N'dbo',
'TABLE', N'sys_menu',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'排序',
'SCHEMA', N'dbo',
'TABLE', N'sys_menu',
'COLUMN', N'sort'
GO

EXEC sp_addextendedproperty
'MS_Description', N'简称',
'SCHEMA', N'dbo',
'TABLE', N'sys_menu',
'COLUMN', N'quick_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_menu',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_menu',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'菜单',
'SCHEMA', N'dbo',
'TABLE', N'sys_menu'
GO


-- ----------------------------
-- Table structure for sys_message
-- ----------------------------
DROP TABLE IF EXISTS dbo.sys_message;
CREATE TABLE dbo.sys_message (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  type int  NULL,
  from_user_id bigint  NOT NULL,
  to_user_id bigint  NOT NULL,
  title nvarchar(50)  NOT NULL,
  business_type bigint  NULL,
  business_key bigint  NULL,
  status int  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.sys_message SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'sys_message',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'sys_message',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'类型(1:流程消息;2:公告通知;3:数据预警;4:暂存数据;9:其它消息;)',
'SCHEMA', N'dbo',
'TABLE', N'sys_message',
'COLUMN', N'type'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户id(来自)',
'SCHEMA', N'dbo',
'TABLE', N'sys_message',
'COLUMN', N'from_user_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户id(给谁)',
'SCHEMA', N'dbo',
'TABLE', N'sys_message',
'COLUMN', N'to_user_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'标题',
'SCHEMA', N'dbo',
'TABLE', N'sys_message',
'COLUMN', N'title'
GO

EXEC sp_addextendedproperty
'MS_Description', N'业务类型',
'SCHEMA', N'dbo',
'TABLE', N'sys_message',
'COLUMN', N'business_type'
GO

EXEC sp_addextendedproperty
'MS_Description', N'业务key',
'SCHEMA', N'dbo',
'TABLE', N'sys_message',
'COLUMN', N'business_key'
GO

EXEC sp_addextendedproperty
'MS_Description', N'状态(0:未读;1:已读;)',
'SCHEMA', N'dbo',
'TABLE', N'sys_message',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_message',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_message',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'消息表',
'SCHEMA', N'dbo',
'TABLE', N'sys_message'
GO


-- ----------------------------
-- Records of sys_message
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS dbo.sys_notice;
CREATE TABLE dbo.sys_notice (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  user_id bigint  NOT NULL,
  title nvarchar(50)  NOT NULL,
  content nvarchar(max)  NULL,
  resources nvarchar(max)  NULL,
  to_user_ids nvarchar(max)  NOT NULL,
  status int DEFAULT 1,
  classify nvarchar(30) DEFAULT '1',
  relation nvarchar(max),
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.sys_notice SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'sys_notice',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'sys_notice',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'发送公告人id',
'SCHEMA', N'dbo',
'TABLE', N'sys_notice',
'COLUMN', N'user_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'标题',
'SCHEMA', N'dbo',
'TABLE', N'sys_notice',
'COLUMN', N'title'
GO

EXEC sp_addextendedproperty
'MS_Description', N'正文',
'SCHEMA', N'dbo',
'TABLE', N'sys_notice',
'COLUMN', N'content'
GO

EXEC sp_addextendedproperty
'MS_Description', N'附件json',
'SCHEMA', N'dbo',
'TABLE', N'sys_notice',
'COLUMN', N'resources'
GO

EXEC sp_addextendedproperty
'MS_Description', N'接收公告人ids',
'SCHEMA', N'dbo',
'TABLE', N'sys_notice',
'COLUMN', N'to_user_ids'
GO

EXEC sp_addextendedproperty
'MS_Description', N'状态(0:暂存;1:已发布;)',
'SCHEMA', N'dbo',
'TABLE', N'sys_notice',
'COLUMN', N'status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'通知类型',
'SCHEMA', N'dbo',
'TABLE', N'sys_notice',
'COLUMN', N'classify'
GO

EXEC sp_addextendedproperty
'MS_Description', N'关联通知',
'SCHEMA', N'dbo',
'TABLE', N'sys_notice',
'COLUMN', N'relation'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_notice',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_notice',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公告',
'SCHEMA', N'dbo',
'TABLE', N'sys_notice'
GO


-- ----------------------------
-- Records of sys_notice
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS dbo.sys_role;
CREATE TABLE dbo.sys_role (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  name nvarchar(30)  NOT NULL,
  path nvarchar(max)  NOT NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.sys_role SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'sys_role',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'sys_role',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'sys_role',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'菜单权限(以,隔开)',
'SCHEMA', N'dbo',
'TABLE', N'sys_role',
'COLUMN', N'path'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_role',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_role',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'系统角色',
'SCHEMA', N'dbo',
'TABLE', N'sys_role'
GO


-- ----------------------------
-- Table structure for sys_theme
-- ----------------------------
DROP TABLE IF EXISTS dbo.sys_theme;
CREATE TABLE dbo.sys_theme (
  id bigint PRIMARY KEY IDENTITY(1,1),
  user_id bigint  NOT NULL,
  color nvarchar(30)  NOT NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.sys_theme SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'sys_theme',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户id',
'SCHEMA', N'dbo',
'TABLE', N'sys_theme',
'COLUMN', N'user_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'颜色值',
'SCHEMA', N'dbo',
'TABLE', N'sys_theme',
'COLUMN', N'color'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_theme',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_theme',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户主题',
'SCHEMA', N'dbo',
'TABLE', N'sys_theme'
GO


-- ----------------------------
-- Records of sys_theme
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for sys_user_relate
-- ----------------------------
DROP TABLE IF EXISTS dbo.sys_user_relate;
CREATE TABLE dbo.sys_user_relate (
  id bigint PRIMARY KEY IDENTITY(1,1),
  user_id bigint  NOT NULL,
  company_id bigint  NOT NULL,
  department_id bigint  NULL,
  role_ids nvarchar(50)  NULL,
  create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.sys_user_relate SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键',
'SCHEMA', N'dbo',
'TABLE', N'sys_user_relate',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户id',
'SCHEMA', N'dbo',
'TABLE', N'sys_user_relate',
'COLUMN', N'user_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'sys_user_relate',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'部门id',
'SCHEMA', N'dbo',
'TABLE', N'sys_user_relate',
'COLUMN', N'department_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'角色ids',
'SCHEMA', N'dbo',
'TABLE', N'sys_user_relate',
'COLUMN', N'role_ids'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_user_relate',
'COLUMN', N'create_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'更新时间',
'SCHEMA', N'dbo',
'TABLE', N'sys_user_relate',
'COLUMN', N'update_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户关联',
'SCHEMA', N'dbo',
'TABLE', N'sys_user_relate'
GO


-- ----------------------------
-- Records of sys_company
-- ----------------------------

INSERT INTO dbo.sys_company (parent_id, mark, code, name, alias, logo, create_time, update_time) VALUES (NULL, N'default', N'1001', N'默认单位', N'默认单位', N'avatar', N'2021-06-29 09:22:42.0000000', N'2021-06-29 09:22:42.0000000')
GO


-- ----------------------------
-- Records of sys_department
-- ----------------------------

INSERT INTO dbo.sys_department (company_id, parent_id, code, name, director, charge_leader, create_time, update_time) VALUES ( N'1', NULL, N'1001', N'默认部门', NULL, NULL, N'2021-07-01 11:30:43.0000000', N'2021-07-01 11:30:43.0000000')
GO

-- ----------------------------
-- Records of sys_account
-- ----------------------------

INSERT INTO dbo.sys_account (avatar, account, email, phone, name, sex, password, position, status, type, openid, sort, create_time, update_time) VALUES (NULL, N'superAdmin', NULL, NULL, N'超级管理员', N'1', N'3bf784f6c81c39142ec0cb2327fc90cc', NULL, N'1', N'3', NULL, N'0', N'2021-07-01 11:51:02.0000000', N'2021-07-01 11:51:02.0000000')
GO

INSERT INTO dbo.sys_account (avatar, account, email, phone, name, sex, password, position, status, type, openid, sort, create_time, update_time) VALUES (NULL, N'admin', NULL, NULL, N'管理员', N'1', N'3bf784f6c81c39142ec0cb2327fc90cc', NULL, N'1', N'2', NULL, N'0', N'2021-07-01 11:51:02.0000000', N'2021-07-01 11:51:02.0000000')
GO

-- ----------------------------
-- Records of sys_role
-- ----------------------------

INSERT INTO dbo.sys_role (company_id, name, path, create_time, update_time) VALUES (N'1', N'默认角色', N'1,2,3', N'2021-07-01 11:31:22.0000000', N'2021-07-01 11:31:22.0000000')
GO

-- ----------------------------
-- Records of sys_user_relate
-- ----------------------------

INSERT INTO dbo.sys_user_relate (user_id, company_id, department_id, role_ids, create_time, update_time) VALUES (N'1', N'1', N'1', N'1', N'2021-06-29 09:23:33.0000000', N'2021-06-29 09:23:33.0000000')
GO

INSERT INTO dbo.sys_user_relate (user_id, company_id, department_id, role_ids, create_time, update_time) VALUES (N'2', N'1', N'1', N'1', N'2021-06-29 09:23:33.0000000', N'2021-06-29 09:23:33.0000000')
GO

-- ----------------------------
-- Records of sys_menu
-- ----------------------------

INSERT INTO dbo.sys_menu (company_id, parent_id, type, name, icon, path, status, sort, create_time, update_time) VALUES (N'1', NULL, N'5', N'组织架构', N'el-icon-office-building', NULL, N'1', N'1', N'2021-07-01 11:33:27.0000000', N'2021-07-01 11:33:27.0000000')
GO

INSERT INTO dbo.sys_menu (company_id, parent_id, type, name, icon, path, status, sort, create_time, update_time) VALUES (N'1', N'1', N'4', N'通讯录', N'el-icon-collection', N'contact', N'1', N'1', N'2021-07-01 11:34:04.0000000', N'2021-07-01 11:34:04.0000000')
GO

INSERT INTO dbo.sys_menu (company_id, parent_id, type, name, icon, path, status, sort, create_time, update_time) VALUES (N'1', N'1', N'4', N'角色管理', N'el-icon-coin', N'roleManager', N'1', N'2', N'2021-07-01 11:34:38.0000000', N'2021-07-01 11:34:38.0000000')
GO