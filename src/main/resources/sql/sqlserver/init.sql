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
    verify_rule_id bigint DEFAULT NULL,
    handle_rule_id bigint DEFAULT NULL,
    template_source nvarchar(max),
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
'MS_Description', N'验证规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule',
'COLUMN', N'verify_rule_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'处理规则',
'SCHEMA', N'dbo',
'TABLE', N'jelly_import_rule',
'COLUMN', N'handle_rule_id'
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
    rule nvarchar(max),
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
-- Table structure for jelly_report
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_report;
CREATE TABLE dbo.jelly_report  (
    id bigint PRIMARY KEY IDENTITY(1,1),
	company_id bigint NOT NULL,
    name nvarchar(100) NOT NULL,
	icon nvarchar(max) NOT NULL,
	color nvarchar(30) NOT NULL,
    data_source bigint NOT NULL,
    template_source nvarchar(max) NOT NULL,
    search_json nvarchar(max) DEFAULT NULL,
	export_path nvarchar(max) DEFAULT NULL,
	create_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP,
  	update_time datetime2(7)  DEFAULT CURRENT_TIMESTAMP
)
GO

ALTER TABLE dbo.jelly_report SET (LOCK_ESCALATION = TABLE)
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
  target_table_id bigint  DEFAULT NULL,
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
'MS_Description', N'目标模型 ',
'SCHEMA', N'dbo',
'TABLE', N'jelly_business_field',
'COLUMN', N'target_table_id'
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
-- Table structure for jelly_business_table
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_business_table;
CREATE TABLE dbo.jelly_business_table (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  name nvarchar(64)  NOT NULL,
  remark nvarchar(64)  NOT NULL,
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
'MS_Description', N'状态(0:禁用：1:启用)',
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
-- Table structure for jelly_door
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_door;
CREATE TABLE dbo.jelly_door (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  name nvarchar(30)  NOT NULL,
  type int  NULL,
  authority nvarchar(max)  NULL,
  view_id bigint NOT NULL,
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
'MS_Description', N'类型(1:仪表盘;2:云页面;)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_door',
'COLUMN', N'type'
GO

EXEC sp_addextendedproperty
'MS_Description', N'权限',
'SCHEMA', N'dbo',
'TABLE', N'jelly_door',
'COLUMN', N'authority'
GO

EXEC sp_addextendedproperty
'MS_Description', N'页面id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_door',
'COLUMN', N'view_id'
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
-- Table structure for jelly_form
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_form;
CREATE TABLE dbo.jelly_form (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  table_id bigint  NOT NULL,
  design nvarchar(max)  NOT NULL,
  name nvarchar(30)  NOT NULL,
  icon nvarchar(max)  NOT NULL,
  color nvarchar(30)  NOT NULL,
  flow_id bigint  NULL,
  data_source nvarchar(max)  NULL,
  search_json nvarchar(max)  NULL,
  table_header nvarchar(max)  NOT NULL,
  options nvarchar(max)  NULL,
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
'MS_Description', N'目标表',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'table_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'设计',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'design'
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
'MS_Description', N'流程id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'flow_id'
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
'MS_Description', N'其他参数json',
'SCHEMA', N'dbo',
'TABLE', N'jelly_form',
'COLUMN', N'options'
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
-- Table structure for jelly_gauge
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_gauge;
CREATE TABLE dbo.jelly_gauge (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  name nvarchar(30)  NOT NULL,
  config nvarchar(max)  NULL,
  script nvarchar(max)  NULL,
  template_engine nvarchar(max)  NULL,
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
'MS_Description', N'脚本',
'SCHEMA', N'dbo',
'TABLE', N'jelly_gauge',
'COLUMN', N'script'
GO

EXEC sp_addextendedproperty
'MS_Description', N'模板引擎',
'SCHEMA', N'dbo',
'TABLE', N'jelly_gauge',
'COLUMN', N'template_engine'
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
  rule_id bigint  NOT NULL,
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
'COLUMN', N'rule_id'
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
  type int DEFAULT 1,
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
'MS_Description', N'类型(1:元函数;2:规则引擎;3:导入验证规则;4:导入处理规则;5:定时任务;6:打印规则;7:下载规则;)',
'SCHEMA', N'dbo',
'TABLE', N'jelly_meta_function',
'COLUMN', N'type'
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
  data_source bigint  NOT NULL,
  template_source nvarchar(max) NOT NULL,
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
'MS_Description', N'数据源',
'SCHEMA', N'dbo',
'TABLE', N'jelly_print',
'COLUMN', N'data_source'
GO

EXEC sp_addextendedproperty
'MS_Description', N'模板源',
'SCHEMA', N'dbo',
'TABLE', N'jelly_print',
'COLUMN', N'template_source'
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
-- Table structure for jelly_table_column_config
-- ----------------------------
DROP TABLE IF EXISTS dbo.jelly_table_column_config;
CREATE TABLE dbo.jelly_table_column_config (
  id bigint PRIMARY KEY IDENTITY(1,1),
  company_id bigint  NOT NULL,
  user_id bigint  NOT NULL,
  form_id bigint  NOT NULL,
  config nvarchar(max),
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
'MS_Description', N'公司id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column_config',
'COLUMN', N'company_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column_config',
'COLUMN', N'user_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'表单id',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column_config',
'COLUMN', N'form_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'配置',
'SCHEMA', N'dbo',
'TABLE', N'jelly_table_column_config',
'COLUMN', N'config'
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
  icon nvarchar(max)  NOT NULL,
  path nvarchar(50)  NULL,
  status int  NULL,
  sort int  NULL,
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

INSERT INTO dbo.sys_company (parent_id, mark, code, name, alias, logo, create_time, update_time) VALUES (NULL, N'seagox', N'1001', N'水母', N'水母', N'avatar', N'2021-06-29 09:22:42.0000000', N'2021-06-29 09:22:42.0000000')
GO


-- ----------------------------
-- Records of sys_department
-- ----------------------------

INSERT INTO dbo.sys_department (company_id, parent_id, code, name, director, charge_leader, create_time, update_time) VALUES ( N'1', NULL, N'1001', N'默认部门', NULL, NULL, N'2021-07-01 11:30:43.0000000', N'2021-07-01 11:30:43.0000000')
GO

-- ----------------------------
-- Records of sys_account
-- ----------------------------

INSERT INTO dbo.sys_account (avatar, account, email, phone, name, sex, password, position, status, type, openid, sort, create_time, update_time) VALUES (NULL, N'superAdmin', NULL, NULL, N'超级管理员', N'1', N'e10adc3949ba59abbe56e057f20f883e', NULL, N'1', N'3', NULL, N'0', N'2021-07-01 11:51:02.0000000', N'2021-07-01 11:51:02.0000000')
GO

INSERT INTO dbo.sys_account (avatar, account, email, phone, name, sex, password, position, status, type, openid, sort, create_time, update_time) VALUES (NULL, N'sysAdmin', NULL, NULL, N'管理员', N'1', N'e10adc3949ba59abbe56e057f20f883e', NULL, N'1', N'2', NULL, N'0', N'2021-07-01 11:51:02.0000000', N'2021-07-01 11:51:02.0000000')
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

INSERT INTO dbo.sys_menu (company_id, parent_id, type, name, icon, path, status, sort, create_time, update_time) VALUES (N'1', NULL, N'5', N'组织架构', N'<svg t=\"1677030380655\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"2355\" width=\"16\" height=\"16\"><path d=\"M789.922409 305.422945h111.583022c30.756491 0 55.791511-24.958229 55.791512-55.649192V138.475371c0-30.690963-25.03502-55.649191-55.791512-55.649192h-111.583022c-30.756491 0-55.791511 24.958229-55.791512 55.649192v27.824083H559.31886c-15.418689 0-27.895756 12.458637-27.895755 27.825108v297.253364h-123.705825V380.079543c0-30.690963-25.03502-55.649191-55.791512-55.649191H120.191851c-30.769802 0-55.791511 24.958229-55.791512 55.649191v278.245957c0 30.690963 25.02171 55.649191 55.791512 55.649191h231.733917c30.756491 0 55.791511-24.958229 55.791512-55.649191V547.027117h123.705825v297.266674c0 15.366471 12.477067 27.825108 27.895755 27.825108h174.348217c0.155631 0 0.308189-0.009215 0.462796-0.011263v27.83637c0 30.690963 25.03502 55.649191 55.791512 55.649192h111.583023c30.756491 0 55.791511-24.958229 55.791511-55.649192V788.6446c0-30.690963-25.03502-55.649191-55.791511-55.649191h-111.583023c-30.756491 0-55.791511 24.958229-55.791512 55.649191v27.83637c-0.154607-0.003072-0.308189-0.011263-0.462796-0.011263H587.214616V547.027117h146.916281v32.933271c0 30.690963 25.03502 55.649191 55.791512 55.649192h111.583022c30.756491 0 55.791511-24.958229 55.791512-55.649192V468.662006c0-30.690963-25.03502-55.649191-55.791512-55.649191h-111.583022c-30.756491 0-55.791511 24.958229-55.791512 55.649191v22.71592H587.214616V221.94967h146.916281v27.824083c0 30.691986 25.03502 55.649191 55.791512 55.649192z m0-166.947574h111.583022l0.027645 111.298382H789.922409V138.475371zM120.191851 658.3255V380.079543h231.733917l0.027645 278.245957H120.191851z m669.730558 130.3191h111.583022l0.027645 111.298382H789.922409V788.6446z m0-319.982594h111.583022l0.027645 111.298382H789.922409V468.662006z\" fill=\"#000000\" p-id=\"2356\"></path></svg>', NULL, N'1', N'1', N'2021-07-01 11:33:27.0000000', N'2021-07-01 11:33:27.0000000')
GO

INSERT INTO dbo.sys_menu (company_id, parent_id, type, name, icon, path, status, sort, create_time, update_time) VALUES (N'1', N'1', N'4', N'通讯录', N'<svg t=\"1677046363544\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"10588\" width=\"16\" height=\"16\"><path d=\"M822.982642 62.633113h-578.423537c-35.221655 0-63.891468 28.656502-63.891468 63.891468v65.960741h-36.098101c-15.51391 0-28.067768 12.567168-28.067768 28.067768 0 15.5006 12.553858 28.067768 28.067768 28.067768h36.098101v139.829972h-36.098101c-15.51391 0-28.067768 12.567168-28.067768 28.067768 0 15.5006 12.553858 28.067768 28.067768 28.067768h36.098101v139.816661h-36.098101c-15.51391 0-28.067768 12.567168-28.067768 28.067768 0 15.5006 12.553858 28.067768 28.067768 28.067769h36.098101v139.829971h-36.098101c-15.51391 0-28.067768 12.567168-28.067768 28.067768 0 15.5006 12.553858 28.067768 28.067768 28.067769h36.098101v59.218455c0 35.234966 28.670837 63.891468 63.891468 63.891468h578.424561c35.221655 0 63.891468-28.656502 63.891468-63.891468V126.524581c-0.001024-35.234966-28.670837-63.891468-63.892492-63.891468zM236.80215 895.719455v-59.218455h50.159118c15.51391 0 28.067768-12.567168 28.067769-28.067769 0-15.499576-12.553858-28.067768-28.067769-28.067768h-50.159118V640.536516h50.159118c15.51391 0 28.067768-12.567168 28.067769-28.067768 0-15.499576-12.553858-28.067768-28.067769-28.067769h-50.159118V444.585342h50.159118c15.51391 0 28.067768-12.567168 28.067769-28.067768 0-15.499576-12.553858-28.067768-28.067769-28.067768h-50.159118V248.620858h50.159118c15.51391 0 28.067768-12.567168 28.067769-28.067768 0-15.5006-12.553858-28.067768-28.067769-28.067768h-50.159118V126.524581c0-4.275745 3.48121-7.756955 7.756955-7.756955h434.879934v784.708785H244.559105c-4.276769 0-7.756955-3.48121-7.756955-7.756956z m593.937447 0c0 4.275745-3.48121 7.756955-7.756955 7.756956h-87.40909V118.767626h87.40909c4.275745 0 7.756955 3.48121 7.756955 7.756955v769.194874z\" fill=\"#000000\" p-id=\"10589\"></path></svg>', N'contact', N'1', N'1', N'2021-07-01 11:34:04.0000000', N'2021-07-01 11:34:04.0000000')
GO

INSERT INTO dbo.sys_menu (company_id, parent_id, type, name, icon, path, status, sort, create_time, update_time) VALUES (N'1', N'1', N'4', N'角色管理', N'<svg t=\"1677030493356\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"2605\" width=\"16\" height=\"16\"><path d=\"M755.301774 722.134033L534.133329 611.604588l66.820804-97.226102a28.05753 28.05753 0 0 0 4.916697-15.844626V343.338442c0-101.186491-81.080455-180.464908-184.616762-180.464908-103.536308 0-184.616763 79.277393-184.616763 180.464908v155.195418a27.852752 27.852752 0 0 0 5.176764 16.199914l68.732398 96.651703L79.74838 721.914922a28.013502 28.013502 0 0 0-15.885581 25.241844v98.510055c0 15.462716 12.525189 27.973571 27.973571 27.973571h650.953526c15.462716 0 27.973571-12.511879 27.97357-27.973571v-98.510055c0.001024-10.599261-5.981538-20.296479-15.461692-25.022733z m-40.485449 95.559217H119.810965v-52.915417l244.867295-117.277873a27.920329 27.920329 0 0 0 14.997872-18.248708 27.808725 27.808725 0 0 0-4.289055-23.193051l-82.801607-116.45774v-146.262019c0-69.825907 56.521542-124.516742 128.668598-124.516742 72.148079 0 128.669621 54.690835 128.669621 124.516742v146.507751l-80.124147 116.567297a27.966404 27.966404 0 0 0-4.043323 22.837762c2.048795 7.83989 7.348425 14.424496 14.588318 18.029597l234.472811 117.168317v53.244084z\" fill=\"#000000\" p-id=\"2606\"></path><path d=\"M942.432176 653.128258L721.291375 542.597789l66.820804-97.226103a28.060601 28.060601 0 0 0 4.917722-15.844625V274.332667c0-101.186491-81.080455-180.464908-184.616763-180.464908-20.161326 0-39.912074 3.059369-58.706513 9.097221l17.100933 53.270706c13.276721-4.261411 27.264018-6.419761 41.60558-6.419761 72.147056 0 128.668597 54.690835 128.668597 124.516742v146.507751l-80.124146 116.567296a27.966404 27.966404 0 0 0-4.043323 22.837763c2.048795 7.840914 7.348425 14.424496 14.588318 18.029596l234.445166 117.168317v53.243061h-69.743997v55.948166h97.717568c15.461692 0 27.973571-12.511879 27.973571-27.973571v-98.510055c0-10.599261-5.983586-20.296479-15.462716-25.022733z\" fill=\"#000000\" p-id=\"2607\"></path></svg>', N'roleManager', N'1', N'2', N'2021-07-01 11:34:38.0000000', N'2021-07-01 11:34:38.0000000')
GO