CREATE TABLE jelly_import_rule  (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    code VARCHAR2(30) NOT NULL,
    name VARCHAR2(100) NOT NULL,
    data_source NUMBER(20) NOT NULL,
    before_rule_id NUMBER(20) DEFAULT NULL,
    after_rule_id NUMBER(20) DEFAULT NULL,
    verify_rule_id NUMBER(20) DEFAULT NULL,
    template_source clob,
    create_time date DEFAULT CURRENT_TIMESTAMP,
	update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_import_rule_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_import_rule_trigger
before insert on jelly_import_rule
for each row
begin
	select jelly_import_rule_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_import_rule.id IS '主键';
COMMENT ON COLUMN jelly_import_rule.company_id IS '公司id';
COMMENT ON COLUMN jelly_import_rule.code IS '编码';
COMMENT ON COLUMN jelly_import_rule.name IS '名称';
COMMENT ON COLUMN jelly_import_rule.data_source IS '数据源';
COMMENT ON COLUMN jelly_import_rule.before_rule_id IS '导入之前规则';
COMMENT ON COLUMN jelly_import_rule.after_rule_id IS '导入之后 规则';
COMMENT ON COLUMN jelly_import_rule.verify_rule_id IS '验证规则';
COMMENT ON COLUMN jelly_import_rule.template_source IS '模板源';
COMMENT ON COLUMN jelly_import_rule.create_time IS '创建时间';
COMMENT ON COLUMN jelly_import_rule.update_time IS '更新时间';
COMMENT ON TABLE jelly_import_rule IS '导入规则';

CREATE TABLE jelly_import_rule_detail  (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    rule_id NUMBER(20) NOT NULL,
    field NUMBER(20) NOT NULL,
    col VARCHAR2(30) NOT NULL,
    rule clob,
    create_time date DEFAULT CURRENT_TIMESTAMP,
	update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_import_rule_detail_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_import_rule_detail
before insert on jelly_import_rule_detail
for each row
begin
	select jelly_import_rule_detail_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_import_rule_detail.id IS '主键';
COMMENT ON COLUMN jelly_import_rule_detail.rule_id IS '导入规则id';
COMMENT ON COLUMN jelly_import_rule_detail.field IS '对应字段';
COMMENT ON COLUMN jelly_import_rule_detail.col IS '对应列';
COMMENT ON COLUMN jelly_import_rule_detail.rule IS '规则';
COMMENT ON COLUMN jelly_import_rule_detail.create_time IS '创建时间';
COMMENT ON COLUMN jelly_import_rule_detail.update_time IS '更新时间';
COMMENT ON TABLE jelly_import_rule_detail IS '导入规则明细';

CREATE TABLE jelly_open_api  (
	id NUMBER(20) PRIMARY KEY NOT NULL,
	company_id NUMBER(20) NOT NULL,
  	appid VARCHAR2(30) NOT NULL,
  	secret VARCHAR2(50) NOT NULL,
  	remark VARCHAR2(200) NOT NULL,
  	create_time date DEFAULT CURRENT_TIMESTAMP,
	update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_open_api_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_open_api_trigger
before insert on jelly_open_api
for each row
begin
	select jelly_open_api_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_open_api.id IS '主键';
COMMENT ON COLUMN jelly_open_api.company_id IS '公司id';
COMMENT ON COLUMN jelly_open_api.appid IS 'appid';
COMMENT ON COLUMN jelly_open_api.secret IS 'secret';
COMMENT ON COLUMN jelly_open_api.remark IS '备注';
COMMENT ON COLUMN jelly_open_api.create_time IS '创建时间';
COMMENT ON COLUMN jelly_open_api.update_time IS '更新时间';
COMMENT ON TABLE jelly_open_api IS 'openApi';


CREATE TABLE jelly_procedure  (
	id NUMBER(20) PRIMARY KEY NOT NULL,
	company_id NUMBER(20) NOT NULL,
  	name VARCHAR2(100) NOT NULL,
  	remark VARCHAR2(200) NOT NULL,
  	config clob,
  	create_time date DEFAULT CURRENT_TIMESTAMP,
	update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_procedure_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_procedure_trigger
before insert on jelly_procedure
for each row
begin
	select jelly_procedure_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_procedure.id IS '主键';
COMMENT ON COLUMN jelly_procedure.company_id IS '公司id';
COMMENT ON COLUMN jelly_procedure.name IS '名称';
COMMENT ON COLUMN jelly_procedure.remark IS '备注';
COMMENT ON COLUMN jelly_procedure.config IS '配置';
COMMENT ON COLUMN jelly_procedure.create_time IS '创建时间';
COMMENT ON COLUMN jelly_procedure.update_time IS '更新时间';
COMMENT ON TABLE jelly_procedure IS '存储过程';


CREATE TABLE jelly_inform  (
	id NUMBER(20) PRIMARY KEY NOT NULL,
	company_id NUMBER(20) NOT NULL,
	type NUMBER(4) NOT NULL,
    code VARCHAR2(30) NOT NULL,
  	name VARCHAR2(100) NOT NULL,
  	data_source NUMBER(20) NOT NULL,
  	template_source clob NOT NULL,
  	create_time date DEFAULT CURRENT_TIMESTAMP,
	update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_inform_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_inform_trigger
before insert on jelly_inform
for each row
begin
	select jelly_inform_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_inform.id IS '主键';
COMMENT ON COLUMN jelly_inform.company_id IS '公司id';
COMMENT ON COLUMN jelly_inform.type IS '类型(1:word;2:excel;)';
COMMENT ON COLUMN jelly_inform.code IS '编码';
COMMENT ON COLUMN jelly_inform.name IS '名称';
COMMENT ON COLUMN jelly_inform.data_source IS '数据源';
COMMENT ON COLUMN jelly_inform.template_source IS '模板源';
COMMENT ON COLUMN jelly_inform.create_time IS '创建时间';
COMMENT ON COLUMN jelly_inform.update_time IS '更新时间';
COMMENT ON TABLE jelly_inform IS '报告模板';

CREATE TABLE jelly_export_rule  (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    code VARCHAR2(30) NOT NULL,
    name VARCHAR2(100) NOT NULL,
    sz_code VARCHAR2(20) NOT NULL,
    sc_code VARCHAR2(20) NOT NULL,
    data_source NUMBER(20) NOT NULL,
    business_rule_id NUMBER(20) DEFAULT NULL,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_export_rule_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_export_rule_trigger
before insert on jelly_export_rule
for each row
begin
	select jelly_export_rule_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_export_rule.id IS '主键';
COMMENT ON COLUMN jelly_export_rule.company_id IS '公司id';
COMMENT ON COLUMN jelly_export_rule.code IS '编码';
COMMENT ON COLUMN jelly_export_rule.name IS '名称';
COMMENT ON COLUMN jelly_export_rule.sz_code IS '收支编码（收入01；支出02）';
COMMENT ON COLUMN jelly_export_rule.sc_code IS '资金性质';
COMMENT ON COLUMN jelly_export_rule.data_source IS '数据源';
COMMENT ON COLUMN jelly_export_rule.business_rule_id IS '业务校验规则id';
COMMENT ON COLUMN jelly_export_rule.create_time IS '创建时间';
COMMENT ON COLUMN jelly_export_rule.update_time IS '更新时间';
COMMENT ON TABLE jelly_export_rule IS '导入规则';

CREATE TABLE jelly_export_rule_detail  (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    export_rule_id NUMBER(20) NOT NULL,
    field NUMBER(20) NOT NULL,
    col VARCHAR2(30) NOT NULL,
    type NUMBER(2) DEFAULT 1,
    source  NUMBER(20) DEFAULT NULL,
    sql_source VARCHAR2(500) DEFAULT NULL,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_export_rule_detail_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_export_rule_detail
before insert on jelly_export_rule_detail
for each row
begin
	select jelly_export_rule_detail_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_export_rule_detail.id IS '主键';
COMMENT ON COLUMN jelly_export_rule_detail.export_rule_id IS '导入规则id';
COMMENT ON COLUMN jelly_export_rule_detail.field IS '对应字段';
COMMENT ON COLUMN jelly_export_rule_detail.col IS '对应列';
COMMENT ON COLUMN jelly_export_rule_detail.type IS '字段转换类型(1无;2字典;3用户;4部门;5唯一字段;6地址)';
COMMENT ON COLUMN jelly_export_rule_detail.source IS '字段转换来源';
COMMENT ON COLUMN jelly_export_rule_detail.sql_source IS 'sql语句';
COMMENT ON COLUMN jelly_export_rule_detail.create_time IS '创建时间';
COMMENT ON COLUMN jelly_export_rule_detail.update_time IS '更新时间';
COMMENT ON TABLE jelly_export_rule_detail IS '导入规则明细';

CREATE TABLE jelly_export_data  (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    name VARCHAR2(120) NOT NULL,
    company_id NUMBER(20) NOT NULL,
    user_id NUMBER(20) NOT NULL,
    config clob NOT NULL,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_export_data_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_export_data_trigger
before insert on jelly_export_data
for each row
begin
	select jelly_export_data_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_export_data.id IS '主键';
COMMENT ON COLUMN jelly_export_data.name IS '名称';
COMMENT ON COLUMN jelly_export_data.company_id IS '公司id';
COMMENT ON COLUMN jelly_export_data.user_id IS '用户id';
COMMENT ON COLUMN jelly_export_data.config IS '配置';
COMMENT ON COLUMN jelly_export_data.create_time IS '创建时间';
COMMENT ON COLUMN jelly_export_data.update_time IS '更新时间';
COMMENT ON TABLE jelly_export_data IS '导入数据';

CREATE TABLE jelly_export_dimension  (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    name VARCHAR2(30) NOT NULL,
    odm_source NUMBER(20) NOT NULL,
    odm_code_field NUMBER(20) NOT NULL,
    odm_name_field NUMBER(20) NOT NULL,
    dim_source NUMBER(20) NOT NULL,
    dim_code_field NUMBER(20) NOT NULL,
    dim_name_field NUMBER(20) NOT NULL,
    dim_year_field NUMBER(20) DEFAULT NULL,
    user_id NUMBER(20) NOT NULL,
    company_id NUMBER(20) NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_export_dimension_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_export_dimension_trigger
before insert on jelly_export_dimension
for each row
begin
	select jelly_export_dimension_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_export_dimension.id IS '主键';
COMMENT ON COLUMN jelly_export_dimension.name IS '维度名称';
COMMENT ON COLUMN jelly_export_dimension.odm_source IS 'ODM表';
COMMENT ON COLUMN jelly_export_dimension.odm_code_field IS 'ODM表字段编码';
COMMENT ON COLUMN jelly_export_dimension.odm_name_field IS 'ODM表字段名称';
COMMENT ON COLUMN jelly_export_dimension.dim_source IS 'DIM表';
COMMENT ON COLUMN jelly_export_dimension.dim_code_field IS 'DIM表字段编码';
COMMENT ON COLUMN jelly_export_dimension.dim_name_field IS 'DIM表字段名称';
COMMENT ON COLUMN jelly_export_dimension.dim_year_field IS 'DIM表字段年段';
COMMENT ON COLUMN jelly_export_dimension.user_id IS '用户id';
COMMENT ON COLUMN jelly_export_dimension.company_id IS '公司id';
COMMENT ON COLUMN jelly_export_dimension.create_time IS '创建时间';
COMMENT ON COLUMN jelly_export_dimension.update_time IS '更新时间';
COMMENT ON TABLE jelly_export_dimension IS '维度管理';

create table jelly_form_design (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    type NUMBER(4) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    excel_json clob,
    data_source VARCHAR2(255) NOT NULL,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_form_design_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_form_design_trigger
before insert on jelly_form_design
for each row
begin
	select jelly_form_design_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_form_design.id IS '主键';
COMMENT ON COLUMN jelly_form_design.company_id IS '公司id';
COMMENT ON COLUMN jelly_form_design.type IS '类型(1:简化版;2:高级版;)';
COMMENT ON COLUMN jelly_form_design.name IS '名称';
COMMENT ON COLUMN jelly_form_design.excel_json IS 'excel配置';
COMMENT ON COLUMN jelly_form_design.data_source IS '数据源配置';
COMMENT ON COLUMN jelly_form_design.create_time IS '创建时间';
COMMENT ON COLUMN jelly_form_design.update_time IS '更新时间';
COMMENT ON TABLE jelly_form_design IS '表单设计';


create table jelly_print (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    excel_json clob,
    data_source VARCHAR2(255) NOT NULL,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_print_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_print_trigger
before insert on jelly_print
for each row
begin
	select jelly_print_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_print.id IS '主键';
COMMENT ON COLUMN jelly_print.company_id IS '公司id';
COMMENT ON COLUMN jelly_print.name IS '名称';
COMMENT ON COLUMN jelly_print.excel_json IS 'excel配置';
COMMENT ON COLUMN jelly_print.data_source IS '数据源配置';
COMMENT ON COLUMN jelly_print.create_time IS '创建时间';
COMMENT ON COLUMN jelly_print.update_time IS '更新时间';
COMMENT ON TABLE jelly_print IS '打印模版';


create table jelly_form (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    design_ids VARCHAR2(255) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    icon VARCHAR2(30) NOT NULL,
	color VARCHAR2(30) NOT NULL,
    table_header NUMBER(20),
    list_export_path clob,
    detail_export_path clob,
    flow_id NUMBER(20) DEFAULT NULL,
    data_source clob,
    search_json clob,
    insert_before_rule NUMBER(20),
    insert_after_rule NUMBER(20),
    update_before_rule NUMBER(20),
    update_after_rule NUMBER(20),
    delete_before_rule NUMBER(20),
    delete_after_rule NUMBER(20),
    process_end_rule NUMBER(20),
    options clob,
    data_title VARCHAR2(255),
    abandon_rule NUMBER(20),
	export_rule NUMBER(20),
    relate_search_json clob,
    authority clob,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_form_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_form_trigger
before insert on jelly_form
for each row
begin
	select jelly_form_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_form.id IS '主键';
COMMENT ON COLUMN jelly_form.company_id IS '公司id';
COMMENT ON COLUMN jelly_form.design_ids IS '设计ids';
COMMENT ON COLUMN jelly_form.name IS '名称';
COMMENT ON COLUMN jelly_form.icon IS '图标';
COMMENT ON COLUMN jelly_form.color IS '颜色';
COMMENT ON COLUMN jelly_form.table_header IS '表格表头';
COMMENT ON COLUMN jelly_form.list_export_path IS '列表导出文件路径';
COMMENT ON COLUMN jelly_form.detail_export_path IS '详情导出文件路径';
COMMENT ON COLUMN jelly_form.flow_id IS '流程id';
COMMENT ON COLUMN jelly_form.data_source IS '数据源配置';
COMMENT ON COLUMN jelly_form.search_json IS '搜索配置';
COMMENT ON COLUMN jelly_form.insert_before_rule IS '新增前规则';
COMMENT ON COLUMN jelly_form.insert_after_rule IS '新增后规则';
COMMENT ON COLUMN jelly_form.update_before_rule IS '更新前规则';
COMMENT ON COLUMN jelly_form.update_after_rule IS '更新后规则';
COMMENT ON COLUMN jelly_form.delete_before_rule IS '删除前规则';
COMMENT ON COLUMN jelly_form.delete_after_rule IS '删除后规则';
COMMENT ON COLUMN jelly_form.process_end_rule IS '流程结束后规则';
COMMENT ON COLUMN jelly_form.options IS '其他参数';
COMMENT ON COLUMN jelly_form.data_title IS '数据标题';
COMMENT ON COLUMN jelly_form.create_time IS '创建时间';
COMMENT ON COLUMN jelly_form.update_time IS '更新时间';
COMMENT ON COLUMN jelly_form.abandon_rule IS '弃审规则';
COMMENT ON COLUMN jelly_form.export_rule IS '导出规则';
COMMENT ON COLUMN jelly_form.relate_search_json IS '联查json';
COMMENT ON COLUMN jelly_form.authority IS '权限';
COMMENT ON TABLE jelly_form IS '表单管理';

CREATE TABLE jelly_report  (
    id NUMBER(20) PRIMARY KEY NOT NULL,
	company_id NUMBER(20) NOT NULL,
    name VARCHAR2(100) NOT NULL,
	icon VARCHAR2(30) NOT NULL,
	color VARCHAR2(30) NOT NULL,
    data_source NUMBER(20) NOT NULL,
    template_source clob NOT NULL,
    search_json clob DEFAULT NULL,
	export_path clob DEFAULT NULL,
    create_time date DEFAULT CURRENT_TIMESTAMP,
	update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_report_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_report_trigger
before insert on jelly_report
for each row
begin
	select jelly_report_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_report.id IS '主键';
COMMENT ON COLUMN jelly_report.company_id IS '公司id';
COMMENT ON COLUMN jelly_report.name IS '名称';
COMMENT ON COLUMN jelly_report.icon IS '图标';
COMMENT ON COLUMN jelly_report.color IS '颜色';
COMMENT ON COLUMN jelly_report.data_source IS '数据源';
COMMENT ON COLUMN jelly_report.template_source IS '模板源';
COMMENT ON COLUMN jelly_report.search_json IS '搜索配置';
COMMENT ON COLUMN jelly_report.export_path IS '导出路径';
COMMENT ON TABLE jelly_report IS '报表管理';

create table jelly_data_sheet (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    form_id NUMBER(20) NOT NULL,
    single_flag NUMBER(4) DEFAULT 1,
    table_name VARCHAR2(50) NOT NULL,
    sort NUMBER(4) DEFAULT 1,
    relate_table VARCHAR2(50),
    relate_field VARCHAR2(50),
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_data_sheet_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_data_sheet_trigger
before insert on jelly_data_sheet
for each row
begin
	select jelly_data_sheet_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_data_sheet.id IS '主键';
COMMENT ON COLUMN jelly_data_sheet.form_id IS '表单id';
COMMENT ON COLUMN jelly_data_sheet.single_flag IS '是否单条(1:是;2:否;)';
COMMENT ON COLUMN jelly_data_sheet.table_name IS '数据表名';
COMMENT ON COLUMN jelly_data_sheet.sort IS '排序';
COMMENT ON COLUMN jelly_data_sheet.relate_table IS '关联表';
COMMENT ON COLUMN jelly_data_sheet.relate_field IS '关联字段';
COMMENT ON COLUMN jelly_data_sheet.create_time IS '创建时间';
COMMENT ON COLUMN jelly_data_sheet.update_time IS '更新时间';
COMMENT ON TABLE jelly_data_sheet IS '数据表设置';


create table jelly_table_classify (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_table_classify_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_table_classify_trigger
before insert on jelly_table_classify
for each row
begin
	select jelly_table_classify_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_table_classify.id IS '主键';
COMMENT ON COLUMN jelly_table_classify.company_id IS '公司id';
COMMENT ON COLUMN jelly_table_classify.name IS '名称';
COMMENT ON COLUMN jelly_table_classify.create_time IS '创建时间';
COMMENT ON COLUMN jelly_table_classify.update_time IS '更新时间';
COMMENT ON TABLE jelly_table_classify IS '表头分类';


create table jelly_table_column (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    classify_id NUMBER(20) NOT NULL,
    parent_id NUMBER(20),
    prop VARCHAR2(30) NOT NULL,
    label VARCHAR2(30) NOT NULL,
    width NUMBER(4),
    locking NUMBER(4) DEFAULT 3,
    summary NUMBER(4) DEFAULT 2,
	total NUMBER(4) DEFAULT 2,
    target NUMBER(4) DEFAULT 0 NOT NULL,
    formatter NUMBER(20),
    options clob,
    sort NUMBER(4) DEFAULT 1,
    formula clob,
    router_json clob,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_table_column_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_table_column_trigger
before insert on jelly_table_column
for each row
begin
	select jelly_table_column_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_table_column.id IS '主键';
COMMENT ON COLUMN jelly_table_column.classify_id IS '分类id';
COMMENT ON COLUMN jelly_table_column.parent_id IS '上级id';
COMMENT ON COLUMN jelly_table_column.prop IS '字段名';
COMMENT ON COLUMN jelly_table_column.label IS '标题';
COMMENT ON COLUMN jelly_table_column.width IS '宽度';
COMMENT ON COLUMN jelly_table_column.locking IS '锁定(1:左;2:右;3:无)';
COMMENT ON COLUMN jelly_table_column.summary IS '汇总(1:是;2:否;)';
COMMENT ON COLUMN jelly_table_column.total IS '合计(1:是;2:否;)';
COMMENT ON COLUMN jelly_table_column.target IS '格式对象(0:无;1:数据字典;2:区域数据;)';
COMMENT ON COLUMN jelly_table_column.formatter IS '格式化';
COMMENT ON COLUMN jelly_table_column.options IS '格式化数据源';
COMMENT ON COLUMN jelly_table_column.sort IS '排序';
COMMENT ON COLUMN jelly_table_column.create_time IS '创建时间';
COMMENT ON COLUMN jelly_table_column.update_time IS '更新时间';
COMMENT ON COLUMN jelly_table_column.formula IS '公式';
COMMENT ON COLUMN jelly_table_column.router_json IS '路由json';
COMMENT ON TABLE jelly_table_column IS '表格表头';


create table jelly_table_column_config (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    user_id NUMBER(20) NOT NULL,
    table_column_id NUMBER(20) NOT NULL,
    display NUMBER(4) DEFAULT 1,
    sort NUMBER(4) DEFAULT 1,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_table_column_config_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jtc_config_trigger
before insert on jelly_table_column_config
for each row
begin
	select jelly_table_column_config_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_table_column_config.id IS '主键';
COMMENT ON COLUMN jelly_table_column_config.user_id IS '用户id';
COMMENT ON COLUMN jelly_table_column_config.table_column_id IS '表头id';
COMMENT ON COLUMN jelly_table_column_config.display IS '显示(1:显示;2:隐藏;)';
COMMENT ON COLUMN jelly_table_column_config.sort IS '排序';
COMMENT ON COLUMN jelly_table_column_config.create_time IS '创建时间';
COMMENT ON COLUMN jelly_table_column_config.update_time IS '更新时间';
COMMENT ON TABLE jelly_table_column_config IS '表头配置';


create table jelly_business_table (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    name VARCHAR2(64) NOT NULL,
    remark VARCHAR2(64) NOT NULL,
    is_virtual NUMBER(4) DEFAULT 0,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_business_table_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_business_table_trigger
before insert on jelly_business_table
for each row
begin
	select jelly_business_table_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_business_table.id IS '主键';
COMMENT ON COLUMN jelly_business_table.company_id IS '公司id';
COMMENT ON COLUMN jelly_business_table.name IS '名称';
COMMENT ON COLUMN jelly_business_table.remark IS '注释';
COMMENT ON COLUMN jelly_business_table.is_virtual IS '虚拟(1:是;0:否;)';
COMMENT ON COLUMN jelly_business_table.create_time IS '创建时间';
COMMENT ON COLUMN jelly_business_table.update_time IS '更新时间';
COMMENT ON TABLE jelly_business_table IS '业务表';


create table jelly_business_field (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    business_table_id NUMBER(20) NOT NULL,
    name VARCHAR2(64) NOT NULL,
    remark VARCHAR2(64) NOT NULL,
    type VARCHAR2(20) NOT NULL,
    kind NUMBER(4) DEFAULT 1,
    length NUMBER(4) DEFAULT 0,
    decimals NUMBER(4) DEFAULT 0,
    not_null NUMBER(4) DEFAULT 0,
    default_value VARCHAR2(200),
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_business_field_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_business_field_trigger
before insert on jelly_business_field
for each row
begin
	select jelly_business_field_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_business_field.id IS '主键';
COMMENT ON COLUMN jelly_business_field.business_table_id IS '业务表id';
COMMENT ON COLUMN jelly_business_field.name IS '名称';
COMMENT ON COLUMN jelly_business_field.remark IS '注释';
COMMENT ON COLUMN jelly_business_field.type IS '类型(1:部门;2:数字;3:日期;4:字符串;5:其他;)';
COMMENT ON COLUMN jelly_business_field.kind IS '种类(1:基本类型;2:数据字典;3:单位;4:部门;5:用户;6:省市区;)';
COMMENT ON COLUMN jelly_business_field.length IS '长度';
COMMENT ON COLUMN jelly_business_field.decimals IS '小数';
COMMENT ON COLUMN jelly_business_field.not_null IS '不为空(1:是;0:否;)';
COMMENT ON COLUMN jelly_business_field.default_value IS '默认值';
COMMENT ON COLUMN jelly_business_field.create_time IS '创建时间';
COMMENT ON COLUMN jelly_business_field.update_time IS '更新时间';
COMMENT ON TABLE jelly_business_field IS '业务字段';


create table jelly_dic_classify (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    sort NUMBER(4) DEFAULT 1,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_dic_classify_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_dic_classify_trigger
before insert on jelly_dic_classify
for each row
begin
	select jelly_dic_classify_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_dic_classify.id IS '主键';
COMMENT ON COLUMN jelly_dic_classify.company_id IS '公司id';
COMMENT ON COLUMN jelly_dic_classify.name IS '名称';
COMMENT ON COLUMN jelly_dic_classify.sort IS '排序';
COMMENT ON COLUMN jelly_dic_classify.create_time IS '创建时间';
COMMENT ON COLUMN jelly_dic_classify.update_time IS '更新时间';
COMMENT ON TABLE jelly_dic_classify IS '字典分类';


create table jelly_dic_detail (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    classify_id NUMBER(20) NOT NULL,
    code VARCHAR2(30) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    sort NUMBER(4) DEFAULT 1,
    status NUMBER(4) DEFAULT 1,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_dic_detail_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_dic_detail_trigger
before insert on jelly_dic_detail
for each row
begin
	select jelly_dic_detail_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_dic_detail.id IS '主键';
COMMENT ON COLUMN jelly_dic_detail.classify_id IS '字典分类id';
COMMENT ON COLUMN jelly_dic_detail.code IS '编码';
COMMENT ON COLUMN jelly_dic_detail.name IS '名称';
COMMENT ON COLUMN jelly_dic_detail.sort IS '排序';
COMMENT ON COLUMN jelly_dic_detail.status IS '状态((0:禁用：1:启用)';
COMMENT ON COLUMN jelly_dic_detail.create_time IS '创建时间';
COMMENT ON COLUMN jelly_dic_detail.update_time IS '更新时间';
COMMENT ON TABLE jelly_dic_detail IS '字典详情';


create table jelly_business_rule (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    script clob,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_business_rule_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_business_rule_trigger
before insert on jelly_business_rule
for each row
begin
	select jelly_business_rule_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_business_rule.id IS '主键';
COMMENT ON COLUMN jelly_business_rule.company_id IS '公司id';
COMMENT ON COLUMN jelly_business_rule.name IS '名称';
COMMENT ON COLUMN jelly_business_rule.script IS '规则脚本';
COMMENT ON COLUMN jelly_business_rule.create_time IS '创建时间';
COMMENT ON COLUMN jelly_business_rule.update_time IS '更新时间';
COMMENT ON TABLE jelly_business_rule IS '业务规则';


create table jelly_regions (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    code VARCHAR2(30) NOT NULL,
    grade NUMBER(4) NOT NULL,
    name VARCHAR2(100) NOT NULL
);
-- 创建序列
create sequence jelly_regions_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_regions_trigger
before insert on jelly_regions
for each row
begin
	select jelly_regions_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_regions.id IS '主键';
COMMENT ON COLUMN jelly_regions.code IS '编码';
COMMENT ON COLUMN jelly_regions.grade IS '等级';
COMMENT ON COLUMN jelly_regions.name IS '名称';
COMMENT ON TABLE jelly_regions IS '区域数据';


create table jelly_job (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    cron VARCHAR2(30) NOT NULL,
    script clob NOT NULL,
    status NUMBER(4) DEFAULT 0,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_job_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_job_trigger
before insert on jelly_job
for each row
begin
	select jelly_job_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_job.id IS '主键';
COMMENT ON COLUMN jelly_job.company_id IS '公司id';
COMMENT ON COLUMN jelly_job.name IS '名称';
COMMENT ON COLUMN jelly_job.cron IS '表达式';
COMMENT ON COLUMN jelly_job.script IS '规则';
COMMENT ON COLUMN jelly_job.status IS '状态(0:未启动;1:已启动;)';
COMMENT ON COLUMN jelly_job.create_time IS '创建时间';
COMMENT ON COLUMN jelly_job.update_time IS '更新时间';
COMMENT ON TABLE jelly_job IS '任务调度';


create table jelly_gauge (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    config clob,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_gauge_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_gauge_trigger
before insert on jelly_gauge
for each row
begin
	select jelly_gauge_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_gauge.id IS '主键';
COMMENT ON COLUMN jelly_gauge.company_id IS '公司id';
COMMENT ON COLUMN jelly_gauge.name IS '名称';
COMMENT ON COLUMN jelly_gauge.config IS '配置';
COMMENT ON COLUMN jelly_gauge.create_time IS '创建时间';
COMMENT ON COLUMN jelly_gauge.update_time IS '更新时间';
COMMENT ON TABLE jelly_gauge IS '仪表板';


create table jelly_door (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    config clob,
	authority clob,
	path NUMBER(20),
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_door_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_door_trigger
before insert on jelly_door
for each row
begin
	select jelly_door_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_door.id IS '主键';
COMMENT ON COLUMN jelly_door.company_id IS '公司id';
COMMENT ON COLUMN jelly_door.name IS '名称';
COMMENT ON COLUMN jelly_door.config IS '配置';
COMMENT ON COLUMN jelly_door.authority IS '权限';
COMMENT ON COLUMN jelly_door.path IS '路径';
COMMENT ON COLUMN jelly_door.create_time IS '创建时间';
COMMENT ON COLUMN jelly_door.update_time IS '更新时间';
COMMENT ON TABLE jelly_door IS '门户管理';


CREATE TABLE jelly_meta_page  (
	id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
	name VARCHAR2(30) NOT NULL,
	path VARCHAR2(30) NOT NULL,
	html clob DEFAULT NULL,
	js clob DEFAULT NULL,
	css clob DEFAULT NULL,
	create_time date DEFAULT CURRENT_TIMESTAMP,
	update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_meta_page_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_meta_page_trigger
before insert on jelly_meta_page
for each row
begin
	select jelly_meta_page_seq.nextval into :new.id from dual;
end;
/

COMMENT ON COLUMN jelly_meta_page.id IS '主键';
COMMENT ON COLUMN jelly_meta_page.company_id IS '公司id';
COMMENT ON COLUMN jelly_meta_page.name IS '名称';
COMMENT ON COLUMN jelly_meta_page.path IS '路径';
COMMENT ON COLUMN jelly_meta_page.html IS 'html脚本';
COMMENT ON COLUMN jelly_meta_page.js IS 'js脚本';
COMMENT ON COLUMN jelly_meta_page.css IS 'css脚本';
COMMENT ON COLUMN jelly_meta_page.create_time IS '创建时间';
COMMENT ON COLUMN jelly_meta_page.update_time IS '更新时间';
COMMENT ON TABLE jelly_meta_page IS '元页面';

CREATE TABLE jelly_meta_function  (
	id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    parent_id NUMBER(20) DEFAULT NULL,
	name VARCHAR2(30) NOT NULL,
	path VARCHAR2(30) NOT NULL,
	script clob DEFAULT NULL,
	create_time date DEFAULT CURRENT_TIMESTAMP,
	update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_meta_function_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_meta_function_trigger
before insert on jelly_meta_function
for each row
begin
	select jelly_meta_function_seq.nextval into :new.id from dual;
end;
/

COMMENT ON COLUMN jelly_meta_function.id IS '主键';
COMMENT ON COLUMN jelly_meta_function.company_id IS '公司id';
COMMENT ON COLUMN jelly_meta_function.parent_id IS '父节点';
COMMENT ON COLUMN jelly_meta_function.name IS '名称';
COMMENT ON COLUMN jelly_meta_function.path IS '路径';
COMMENT ON COLUMN jelly_meta_function.script IS '脚本';
COMMENT ON COLUMN jelly_meta_function.create_time IS '创建时间';
COMMENT ON COLUMN jelly_meta_function.update_time IS '更新时间';
COMMENT ON TABLE jelly_meta_function IS '元函数';

CREATE TABLE jelly_template_engine  (
	id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
	name VARCHAR2(30) NOT NULL,
	path VARCHAR2(30) NOT NULL,
	script clob DEFAULT NULL,
	create_time date DEFAULT CURRENT_TIMESTAMP,
	update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_template_engine_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_template_engine_trigger
before insert on jelly_template_engine
for each row
begin
	select jelly_template_engine_seq.nextval into :new.id from dual;
end;
/

COMMENT ON COLUMN jelly_template_engine.id IS '主键';
COMMENT ON COLUMN jelly_template_engine.company_id IS '公司id';
COMMENT ON COLUMN jelly_template_engine.name IS '名称';
COMMENT ON COLUMN jelly_template_engine.path IS '路径';
COMMENT ON COLUMN jelly_template_engine.script IS '脚本';
COMMENT ON COLUMN jelly_template_engine.create_time IS '创建时间';
COMMENT ON COLUMN jelly_template_engine.update_time IS '更新时间';
COMMENT ON TABLE jelly_template_engine IS '模版引擎';


CREATE TABLE jelly_file_chunk  (
   id NUMBER(20) PRIMARY KEY NOT NULL,
   chunk_number NUMBER(4) DEFAULT NULL,
   chunk_size NUMBER DEFAULT NULL,
   current_chunk_size NUMBER DEFAULT NULL,
   total_chunk NUMBER DEFAULT NULL,
   identifier VARCHAR2(45) DEFAULT NULL,
   file_name VARCHAR2(255) DEFAULT NULL,
   file_type NUMBER(4) DEFAULT NULL,
   relative_path VARCHAR2(255) DEFAULT NULL,
   part_e_tag VARCHAR2(500) DEFAULT NULL,
   create_time date DEFAULT CURRENT_TIMESTAMP,
   update_time date DEFAULT CURRENT_TIMESTAMP
);

-- 创建序列
create sequence jelly_file_chunk_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_file_chunk_trigger
before insert on jelly_file_chunk
for each row
begin
	select jelly_file_chunk_seq.nextval into :new.id from dual;
end;
/

COMMENT ON COLUMN jelly_file_chunk.id IS '主键';
COMMENT ON COLUMN jelly_file_chunk.chunk_number IS '前分片，从1开始';
COMMENT ON COLUMN jelly_file_chunk.chunk_size IS '分片大小';
COMMENT ON COLUMN jelly_file_chunk.current_chunk_size IS '当前分片大小';
COMMENT ON COLUMN jelly_file_chunk.total_chunk IS '总分片数';
COMMENT ON COLUMN jelly_file_chunk.identifier IS '文件标识';
COMMENT ON COLUMN jelly_file_chunk.file_name IS '文件名';
COMMENT ON COLUMN jelly_file_chunk.file_type IS '文件类型(1:minio;2:阿里云)';
COMMENT ON COLUMN jelly_file_chunk.relative_path IS '相对路径';
COMMENT ON COLUMN jelly_file_chunk.part_e_tag IS '分片信息';
COMMENT ON COLUMN jelly_file_chunk.create_time IS '创建时间';
COMMENT ON COLUMN jelly_file_chunk.update_time IS '更新时间';
COMMENT ON TABLE jelly_file_chunk IS '文件分片';

CREATE TABLE sea_definition  (
    id NUMBER(20) PRIMARY KEY NOT NULL,
	company_id NUMBER(20) NOT NULL,
	name VARCHAR2(30) NOT NULL,
    resources clob DEFAULT NULL,
    data_source VARCHAR2(255) DEFAULT NULL,
    create_time date DEFAULT CURRENT_TIMESTAMP,
	update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence sea_definition_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger sea_definition_trigger
before insert on sea_definition
for each row
begin
	select sea_definition_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN sea_definition.id IS '主键';
COMMENT ON COLUMN sea_definition.company_id IS '公司id';
COMMENT ON COLUMN sea_definition.name IS '名称';
COMMENT ON COLUMN sea_definition.resources IS '流程文件';
COMMENT ON COLUMN sea_definition.data_source IS '数据源配置';
COMMENT ON COLUMN sea_definition.create_time IS '创建时间';
COMMENT ON COLUMN sea_definition.update_time IS '更新时间';
COMMENT ON TABLE sea_definition IS '流程定义';

create table sea_instance (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    user_id NUMBER(20) NOT NULL,
    version NUMBER(4) DEFAULT 1,
    name VARCHAR2(50) NOT NULL,
    business_type VARCHAR2(50) NOT NULL,
    business_key VARCHAR2(50) NOT NULL,
    resources clob NOT NULL,
    status NUMBER(4) DEFAULT 0,
    start_time date DEFAULT CURRENT_TIMESTAMP,
    end_time date,
    current_agent clob,
    close_status NUMBER(4) DEFAULT NULL,
    other_json clob DEFAULT NULL,
    return_number NUMBER(4) DEFAULT 0,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence sea_instance_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger sea_instance_trigger
before insert on sea_instance
for each row
begin
	select sea_instance_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN sea_instance.id IS '主键';
COMMENT ON COLUMN sea_instance.company_id IS '公司id';
COMMENT ON COLUMN sea_instance.user_id IS '用户id';
COMMENT ON COLUMN sea_instance.version IS '版本';
COMMENT ON COLUMN sea_instance.name IS '名称';
COMMENT ON COLUMN sea_instance.business_type IS '业务类型';
COMMENT ON COLUMN sea_instance.business_key IS '业务key';
COMMENT ON COLUMN sea_instance.resources IS '流程文件';
COMMENT ON COLUMN sea_instance.status IS '状态(0:待审;1:通过;2:驳回;3:撤回;4:关闭)';
COMMENT ON COLUMN sea_instance.start_time IS '开始时间';
COMMENT ON COLUMN sea_instance.end_time IS '结束时间';
COMMENT ON COLUMN sea_instance.current_agent IS '当前代办人';
COMMENT ON COLUMN sea_instance.close_status IS '关闭时流程状态';
COMMENT ON COLUMN sea_instance.other_json IS '其它json值';
COMMENT ON COLUMN sea_instance.create_time IS '创建时间';
COMMENT ON COLUMN sea_instance.update_time IS '更新时间';
COMMENT ON COLUMN sea_instance.return_number IS '退回次数';
COMMENT ON TABLE sea_instance IS '流程实例';


create table sea_node (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    def_id NUMBER(20) NOT NULL,
    version NUMBER(4) DEFAULT 1,
    source VARCHAR2(50) NOT NULL,
    target VARCHAR2(50) NOT NULL,
    name VARCHAR2(50) NOT NULL,
    type NUMBER(4) DEFAULT 1,
    status NUMBER(4) DEFAULT 0,
    start_time date DEFAULT CURRENT_TIMESTAMP,
    end_time date,
    is_concurrent NUMBER(4) DEFAULT 0,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence sea_node_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger sea_node_trigger
before insert on sea_node
for each row
begin
	select sea_node_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN sea_node.id IS '主键';
COMMENT ON COLUMN sea_node.def_id IS '流程实例id';
COMMENT ON COLUMN sea_node.version IS '版本';
COMMENT ON COLUMN sea_node.source IS '源节点';
COMMENT ON COLUMN sea_node.target IS '目标节点';
COMMENT ON COLUMN sea_node.name IS '名称';
COMMENT ON COLUMN sea_node.type IS '类型(1:审批;2:抄送;3:撤回;4:驳回;5:重新发起;)';
COMMENT ON COLUMN sea_node.status IS '状态(0:待办;1:通过;2:不通过;3:作废;)';
COMMENT ON COLUMN sea_node.start_time IS '开始时间';
COMMENT ON COLUMN sea_node.end_time IS '结束时间';
COMMENT ON COLUMN sea_node.is_concurrent IS '并行网关(0:否;1:是;)';
COMMENT ON COLUMN sea_node.create_time IS '创建时间';
COMMENT ON COLUMN sea_node.update_time IS '更新时间';
COMMENT ON TABLE sea_node IS '流程节点';


create table sea_node_detail (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    node_id NUMBER(20) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    assignee VARCHAR2(30) NOT NULL,
    status NUMBER(4) DEFAULT 0,
    remark VARCHAR2(255),
    start_time date DEFAULT CURRENT_TIMESTAMP,
    end_time date,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence sea_node_detail_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger sea_node_detail_trigger
before insert on sea_node_detail
for each row
begin
	select sea_node_detail_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN sea_node_detail.id IS '主键';
COMMENT ON COLUMN sea_node_detail.node_id IS '流程节点id';
COMMENT ON COLUMN sea_node_detail.name IS '名称';
COMMENT ON COLUMN sea_node_detail.assignee IS '签收人或被委托id';
COMMENT ON COLUMN sea_node_detail.status IS '状态(0:待办;1:同意;2:不同意;3:已阅;4:撤回;5:重新发起;6:弃审;)';
COMMENT ON COLUMN sea_node_detail.remark IS '评论';
COMMENT ON COLUMN sea_node_detail.start_time IS '开始时间';
COMMENT ON COLUMN sea_node_detail.end_time IS '结束时间';
COMMENT ON COLUMN sea_node_detail.create_time IS '创建时间';
COMMENT ON COLUMN sea_node_detail.update_time IS '更新时间';
COMMENT ON TABLE sea_node_detail IS '流程节点详情';

create table sys_company (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    parent_id NUMBER(20),
    mark VARCHAR2(30) NOT NULL,
    code VARCHAR2(30) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    alias VARCHAR2(30) NOT NULL,
    logo VARCHAR2(100) NOT NULL,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence sys_company_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger sys_company_trigger
before insert on sys_company
for each row
begin
	select sys_company_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN sys_company.id IS '主键';
COMMENT ON COLUMN sys_company.parent_id IS '上级id';
COMMENT ON COLUMN sys_company.mark IS '标识';
COMMENT ON COLUMN sys_company.code IS '编码';
COMMENT ON COLUMN sys_company.name IS '名称';
COMMENT ON COLUMN sys_company.alias IS '简称';
COMMENT ON COLUMN sys_company.logo IS 'logo';
COMMENT ON COLUMN sys_company.create_time IS '创建时间';
COMMENT ON COLUMN sys_company.update_time IS '更新时间';
COMMENT ON TABLE sys_company IS '公司';


create table sys_department (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    parent_id NUMBER(20),
    code VARCHAR2(30) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    director VARCHAR2(500),
    charge_leader VARCHAR2(500),
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence sys_department_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger sys_department_trigger
before insert on sys_department
for each row
begin
	select sys_department_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN sys_department.id IS '主键';
COMMENT ON COLUMN sys_department.company_id IS '公司id';
COMMENT ON COLUMN sys_department.parent_id IS '上级id';
COMMENT ON COLUMN sys_department.code IS '编码';
COMMENT ON COLUMN sys_department.name IS '名称';
COMMENT ON COLUMN sys_department.director IS '直接主管';
COMMENT ON COLUMN sys_department.charge_leader IS '分管领导';
COMMENT ON COLUMN sys_department.create_time IS '创建时间';
COMMENT ON COLUMN sys_department.update_time IS '更新时间';
COMMENT ON TABLE sys_department IS '部门';


create table sys_user_relate (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    user_id NUMBER(20) NOT NULL,
    company_id NUMBER(20) NOT NULL,
    department_id NUMBER(20),
    role_ids VARCHAR2(50),
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence sys_user_relate_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger sys_user_relate_trigger
before insert on sys_user_relate
for each row
begin
	select sys_user_relate_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN sys_user_relate.id IS '主键';
COMMENT ON COLUMN sys_user_relate.user_id IS '用户id';
COMMENT ON COLUMN sys_user_relate.company_id IS '公司id';
COMMENT ON COLUMN sys_user_relate.department_id IS '部门id';
COMMENT ON COLUMN sys_user_relate.role_ids IS '角色ids';
COMMENT ON COLUMN sys_user_relate.create_time IS '创建时间';
COMMENT ON COLUMN sys_user_relate.update_time IS '更新时间';
COMMENT ON TABLE sys_user_relate IS '用户关联';


create table sys_account (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    avatar VARCHAR2(255) DEFAULT NULL,
    account VARCHAR2(50) NOT NULL,
    email VARCHAR2(50),
    phone VARCHAR2(30),
    name VARCHAR2(50) NOT NULL,
    sex NUMBER(4) DEFAULT 1,
    password VARCHAR2(255) NOT NULL,
    position VARCHAR2(50),
    status NUMBER(4) DEFAULT 1 NOT NULL,
    type NUMBER(4) DEFAULT 1,
    openid VARCHAR2(100),
	sort NUMBER(4) DEFAULT 0,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence sys_account_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger sys_account_trigger
before insert on sys_account
for each row
begin
	select sys_account_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN sys_account.id IS '主键';
COMMENT ON COLUMN sys_account.avatar IS '头像';
COMMENT ON COLUMN sys_account.account IS '账号';
COMMENT ON COLUMN sys_account.name IS '姓名';
COMMENT ON COLUMN sys_account.sex IS '性别(1:男;2:女;)';
COMMENT ON COLUMN sys_account.password IS '密码';
COMMENT ON COLUMN sys_account.position IS '职位';
COMMENT ON COLUMN sys_account.status IS '状态(1:启用;2:禁用;)';
COMMENT ON COLUMN sys_account.create_time IS '创建时间';
COMMENT ON COLUMN sys_account.update_time IS '更新时间';
COMMENT ON COLUMN sys_account.type IS '类型(1:普通成员;2:管理员;3:超级管理员;)';
COMMENT ON COLUMN sys_account.email IS '邮箱';
COMMENT ON COLUMN sys_account.phone IS '手机号';
COMMENT ON COLUMN sys_account.openid IS 'openid';
COMMENT ON COLUMN sys_account.sort IS '排序';
COMMENT ON TABLE sys_account IS '用户';


create table sys_role (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    path VARCHAR2(4000) NOT NULL,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence sys_role_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger sys_role_trigger
before insert on sys_role
for each row
begin
	select sys_role_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN sys_role.id IS '主键';
COMMENT ON COLUMN sys_role.company_id IS '公司id';
COMMENT ON COLUMN sys_role.name IS '名称';
COMMENT ON COLUMN sys_role.path IS '菜单权限(以,隔开)';
COMMENT ON COLUMN sys_role.create_time IS '创建时间';
COMMENT ON COLUMN sys_role.update_time IS '更新时间';
COMMENT ON TABLE sys_role IS '系统角色';


create table sys_menu (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    parent_id NUMBER(20),
    type NUMBER(4) DEFAULT 1,
    name VARCHAR2(30) NOT NULL,
    icon VARCHAR2(30) NOT NULL,
    path VARCHAR2(50),
    status NUMBER(4) DEFAULT 1,
    sort NUMBER(4) DEFAULT 1,
    quick_name VARCHAR2(30) DEFAULT NULL,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence sys_menu_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger sys_menu_trigger
before insert on sys_menu
for each row
begin
	select sys_menu_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN sys_menu.id IS '主键';
COMMENT ON COLUMN sys_menu.company_id IS '公司id';
COMMENT ON COLUMN sys_menu.parent_id IS '上级id';
COMMENT ON COLUMN sys_menu.type IS '类型(1:列表;2:按钮;3:新增页面;4:系统菜单;5:目录;6:仪表板;7:自定义页面;8:报表;9:单页面;)';
COMMENT ON COLUMN sys_menu.name IS '名称';
COMMENT ON COLUMN sys_menu.icon IS '图标';
COMMENT ON COLUMN sys_menu.path IS '路径';
COMMENT ON COLUMN sys_menu.status IS '状态(1:启用;2:禁用;)';
COMMENT ON COLUMN sys_menu.sort IS '排序';
COMMENT ON COLUMN sys_menu.quick_name IS '简称';
COMMENT ON COLUMN sys_menu.create_time IS '创建时间';
COMMENT ON COLUMN sys_menu.update_time IS '更新时间';
COMMENT ON TABLE sys_menu IS '菜单';


create table sys_notice (
    id NUMBER(20) PRIMARY KEY NOT NULL,
	company_id NUMBER(20) NOT NULL,
    title VARCHAR2(50) NOT NULL,
    content clob,
    resources clob,
    user_id NUMBER(20) NOT NULL,
    to_user_ids VARCHAR2(500) NOT NULL,
    status NUMBER(4) DEFAULT 1,
    classify VARCHAR2(30) DEFAULT '1',
    relation VARCHAR2(500),
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence sys_notice_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger sys_notice_trigger
before insert on sys_notice
for each row
begin
	select sys_notice_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN sys_notice.id IS '主键';
COMMENT ON COLUMN sys_notice.company_id IS '公司id';
COMMENT ON COLUMN sys_notice.user_id IS '发送公告人id';
COMMENT ON COLUMN sys_notice.title IS '标题';
COMMENT ON COLUMN sys_notice.content IS '正文';
COMMENT ON COLUMN sys_notice.resources IS '附件json';
COMMENT ON COLUMN sys_notice.to_user_ids IS '接收公告人ids';
COMMENT ON COLUMN sys_notice.status IS '状态(0:暂存;1:已发布;)';
COMMENT ON COLUMN sys_notice.classify IS '通知类型';
COMMENT ON COLUMN sys_notice.relation IS '关联通知';
COMMENT ON COLUMN sys_notice.create_time IS '创建时间';
COMMENT ON COLUMN sys_notice.update_time IS '更新时间';
COMMENT ON TABLE sys_notice IS '公告';


create table sys_message (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    type NUMBER(4) DEFAULT 1,
    from_user_id NUMBER(20) NOT NULL,
    to_user_id NUMBER(20) NOT NULL,
    title VARCHAR2(50) NOT NULL,
    business_type NUMBER(20) NOT NULL,
    business_key NUMBER(20) NOT NULL,
    status NUMBER(4) DEFAULT 0,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence sys_message_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger sys_message_trigger
before insert on sys_message
for each row
begin
	select sys_message_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN sys_message.id IS '主键';
COMMENT ON COLUMN sys_message.company_id IS '公司id';
COMMENT ON COLUMN sys_message.type IS '类型(1:流程消息;2:公告通知;3:数据预警;4:暂存数据;9:其它消息;)';
COMMENT ON COLUMN sys_message.from_user_id IS '用户id(来自)';
COMMENT ON COLUMN sys_message.to_user_id IS '用户id(给谁)';
COMMENT ON COLUMN sys_message.title IS '标题';
COMMENT ON COLUMN sys_message.business_type IS '业务类型';
COMMENT ON COLUMN sys_message.business_key IS '业务key';
COMMENT ON COLUMN sys_message.status IS '状态(0:未读;1:已读;)';
COMMENT ON COLUMN sys_message.create_time IS '创建时间';
COMMENT ON COLUMN sys_message.update_time IS '更新时间';
COMMENT ON TABLE sys_message IS '消息表';


create table sys_log (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    user_id NUMBER(20) NOT NULL,
    name VARCHAR2(100) NOT NULL,
    ip VARCHAR2(100) NOT NULL,
    uri VARCHAR2(100) NOT NULL,
    method VARCHAR2(500) NOT NULL,
    params clob,
    status NUMBER(4) DEFAULT 1,
    cost_time NUMBER(4) NOT NULL,
    result clob,
    ua VARCHAR2(1000),
    create_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence sys_log_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger sys_log_trigger
before insert on sys_log
for each row
begin
	select sys_log_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN sys_log.id IS '主键';
COMMENT ON COLUMN sys_log.company_id IS '公司id';
COMMENT ON COLUMN sys_log.user_id IS '用户id';
COMMENT ON COLUMN sys_log.name IS '操作名称';
COMMENT ON COLUMN sys_log.ip IS 'ip';
COMMENT ON COLUMN sys_log.uri IS 'uri';
COMMENT ON COLUMN sys_log.method IS '方法';
COMMENT ON COLUMN sys_log.params IS '请求参数';
COMMENT ON COLUMN sys_log.status IS '状态(1:成功;2:失败;)';
COMMENT ON COLUMN sys_log.cost_time IS '花费时间';
COMMENT ON COLUMN sys_log.result IS '返回结果';
COMMENT ON COLUMN sys_log.ua IS '浏览器信息';
COMMENT ON COLUMN sys_log.create_time IS '创建时间';
COMMENT ON TABLE sys_log IS '操作日记';

create table sys_theme (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    user_id NUMBER(20) NOT NULL,
    color VARCHAR2(30) NOT NULL,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence sys_theme_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger sys_theme_trigger
before insert on sys_theme
for each row
begin
	select sys_theme_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN sys_theme.id IS '主键';
COMMENT ON COLUMN sys_theme.user_id IS '用户id';
COMMENT ON COLUMN sys_theme.color IS '颜色值';
COMMENT ON COLUMN sys_theme.create_time IS '创建时间';
COMMENT ON COLUMN sys_theme.update_time IS '更新时间';
COMMENT ON TABLE sys_theme IS '用户主题';

create table jelly_common_words (
    id NUMBER(20) PRIMARY KEY NOT NULL,
	company_id NUMBER(20) NOT NULL,
    user_id NUMBER(20) NOT NULL,
    name VARCHAR2(50) NOT NULL,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence sjelly_common_words_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;
-- 创建触发器
create or replace trigger jelly_common_words_trigger
before insert on jelly_common_words
for each row
begin
	select jelly_common_words_seq.nextval into :new.id from dual;
end;
/
COMMENT ON COLUMN jelly_common_words.id IS '主键';
COMMENT ON COLUMN jelly_common_words.company_id IS '公司id';
COMMENT ON COLUMN jelly_common_words.user_id IS '用户id';
COMMENT ON COLUMN jelly_common_words.name IS '名称';
COMMENT ON COLUMN jelly_common_words.create_time IS '创建时间';
COMMENT ON COLUMN jelly_common_words.update_time IS '更新时间';
COMMENT ON TABLE jelly_common_words IS '常用语';


INSERT INTO sys_company VALUES (1, NULL, 'default', '1001', '默认单位', '默认单位', 'avatar', TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO sys_department VALUES (1, 1, NULL, '1001', '默认部门', NULL, NULL, TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO sys_account VALUES (1, NULL, 'superAdmin', NULL, NULL, '超级管理员', 1, 'e10adc3949ba59abbe56e057f20f883e', NULL, 1, 3, NULL, 0, TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO sys_user_relate VALUES (1, 1, 1, 1, 1, TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO sys_account VALUES (2, NULL, 'admin', NULL, NULL, '管理员', 1, 'e10adc3949ba59abbe56e057f20f883e', NULL, 1, 2, NULL, 0, TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO sys_user_relate VALUES (2, 2, 1, 1, 1, TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO sys_role VALUES (1, 1, '默认角色', '1,2,3', TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO sys_menu VALUES (1, 1, NULL, 5, '组织架构', 'el-icon-office-building', NULL, 1, 1, NULL, TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO sys_menu VALUES (2, 1, 1, 4, '通讯录', 'el-icon-collection', 'contact', 1, 1, NULL, TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO sys_menu VALUES (3, 1, 1, 4, '角色管理', 'el-icon-coin', 'role', 1, 2, NULL, TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'));


CREATE OR REPLACE FUNCTION FIND_IN_SET(piv_str1 varchar2, piv_str2 varchar2, p_sep varchar2 := ',')

RETURN NUMBER IS      

  l_idx    number:=0; -- 用于计算piv_str2中分隔符的位置  

  str      varchar2(500);  -- 根据分隔符截取的子字符串  

  piv_str  varchar2(500) := piv_str2; -- 将piv_str2赋值给piv_str  

  res      number:=0; -- 返回结果  

  loopIndex number:=0;

BEGIN  

-- 如果piv_str中没有分割符，直接判断piv_str1和piv_str是否相等，相等 res=1  

IF instr(piv_str, p_sep, 1) = 0 THEN  

   IF piv_str = piv_str1 THEN   

      res:= 1;  

   END IF;  

ELSE  

-- 循环按分隔符截取piv_str  

LOOP  

    l_idx := instr(piv_str,p_sep);  

     loopIndex:=loopIndex+1;

-- 当piv_str中还有分隔符时  

      IF l_idx > 0 THEN  



   -- 截取第一个分隔符前的字段str  

         str:= substr(piv_str,1,l_idx-1);  

   -- 判断 str 和piv_str1 是否相等，相等 res=1 并结束循环判断  

         IF str = piv_str1 THEN   

           res:= loopIndex;  

           EXIT;  

         END IF;  

        piv_str := substr(piv_str,l_idx+length(p_sep));  

      ELSE  

   -- 当截取后的piv_str 中不存在分割符时，判断piv_str和piv_str1是否相等，相等 res=1  

        IF piv_str = piv_str1 THEN   

           res:= loopIndex;  

        END IF;  

        -- 无论最后是否相等，都跳出循环  

        EXIT;  

      END IF;  

END LOOP;  

-- 结束循环  

END IF;  

-- 返回res  

RETURN res;  

END FIND_IN_SET;  

