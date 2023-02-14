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

COMMENT ON COLUMN jelly_import_rule_detail.id IS '主键';
COMMENT ON COLUMN jelly_import_rule_detail.rule_id IS '导入规则id';
COMMENT ON COLUMN jelly_import_rule_detail.field IS '对应字段';
COMMENT ON COLUMN jelly_import_rule_detail.col IS '对应列';
COMMENT ON COLUMN jelly_import_rule_detail.rule IS '规则';
COMMENT ON COLUMN jelly_import_rule_detail.create_time IS '创建时间';
COMMENT ON COLUMN jelly_import_rule_detail.update_time IS '更新时间';
COMMENT ON TABLE jelly_import_rule_detail IS '导入规则明细';

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
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_print_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;

COMMENT ON COLUMN jelly_print.id IS '主键';
COMMENT ON COLUMN jelly_print.company_id IS '公司id';
COMMENT ON COLUMN jelly_print.name IS '名称';
COMMENT ON COLUMN jelly_print.excel_json IS 'excel配置';
COMMENT ON COLUMN jelly_print.create_time IS '创建时间';
COMMENT ON COLUMN jelly_print.update_time IS '更新时间';
COMMENT ON TABLE jelly_print IS '打印模版';


create table jelly_form (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    design_id NUMBER(20) NOT NULL,
    name VARCHAR2(30) NOT NULL,
    icon VARCHAR2(30) NOT NULL,
	color VARCHAR2(30) NOT NULL,
	flow_id NUMBER(20) DEFAULT NULL,
	data_source clob,
	search_json clob,
    table_header clob,
    options clob,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_form_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;

COMMENT ON COLUMN jelly_form.id IS '主键';
COMMENT ON COLUMN jelly_form.company_id IS '公司id';
COMMENT ON COLUMN jelly_form.design_id IS '设计id';
COMMENT ON COLUMN jelly_form.name IS '名称';
COMMENT ON COLUMN jelly_form.icon IS '图标';
COMMENT ON COLUMN jelly_form.color IS '颜色';
COMMENT ON COLUMN jelly_form.flow_id IS '流程id';
COMMENT ON COLUMN jelly_form.data_source IS '数据源配置';
COMMENT ON COLUMN jelly_form.search_json IS '搜索配置';
COMMENT ON COLUMN jelly_form.table_header IS '表格表头';
COMMENT ON COLUMN jelly_form.options IS '其他参数';
COMMENT ON COLUMN jelly_form.create_time IS '创建时间';
COMMENT ON COLUMN jelly_form.update_time IS '更新时间';
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


create table jelly_table_column_config (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    user_id NUMBER(20) NOT NULL,
    form_id NUMBER(20) NOT NULL,
    config clob,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_table_column_config_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;

COMMENT ON COLUMN jelly_table_column_config.id IS '主键';
COMMENT ON COLUMN jelly_table_column_config.company_id IS '公司id';
COMMENT ON COLUMN jelly_table_column_config.user_id IS '用户id';
COMMENT ON COLUMN jelly_table_column_config.form_id IS '表单id';
COMMENT ON COLUMN jelly_table_column_config.config IS '配置';
COMMENT ON COLUMN jelly_table_column_config.create_time IS '创建时间';
COMMENT ON COLUMN jelly_table_column_config.update_time IS '更新时间';
COMMENT ON TABLE jelly_table_column_config IS '表头配置';


create table jelly_business_table (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    company_id NUMBER(20) NOT NULL,
    name VARCHAR2(64) NOT NULL,
    remark VARCHAR2(64) NOT NULL,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_business_table_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;

COMMENT ON COLUMN jelly_business_table.id IS '主键';
COMMENT ON COLUMN jelly_business_table.company_id IS '公司id';
COMMENT ON COLUMN jelly_business_table.name IS '名称';
COMMENT ON COLUMN jelly_business_table.remark IS '注释';
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
    target_table_id NUMBER(20) DEFAULT NULL,
    create_time date DEFAULT CURRENT_TIMESTAMP,
    update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_business_field_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;

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
COMMENT ON COLUMN jelly_business_field.target_table_id IS '目标模型';
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

COMMENT ON COLUMN jelly_dic_detail.id IS '主键';
COMMENT ON COLUMN jelly_dic_detail.classify_id IS '字典分类id';
COMMENT ON COLUMN jelly_dic_detail.code IS '编码';
COMMENT ON COLUMN jelly_dic_detail.name IS '名称';
COMMENT ON COLUMN jelly_dic_detail.sort IS '排序';
COMMENT ON COLUMN jelly_dic_detail.status IS '状态(0:禁用：1:启用)';
COMMENT ON COLUMN jelly_dic_detail.create_time IS '创建时间';
COMMENT ON COLUMN jelly_dic_detail.update_time IS '更新时间';
COMMENT ON TABLE jelly_dic_detail IS '字典详情';


create table jelly_regions (
    id NUMBER(20) PRIMARY KEY NOT NULL,
    code VARCHAR2(30) NOT NULL,
    grade NUMBER(4) NOT NULL,
    name VARCHAR2(100) NOT NULL
);
-- 创建序列
create sequence jelly_regions_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;

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
	name VARCHAR2(30) NOT NULL,
	path VARCHAR2(30) NOT NULL,
	script clob DEFAULT NULL,
	create_time date DEFAULT CURRENT_TIMESTAMP,
	update_time date DEFAULT CURRENT_TIMESTAMP
);
-- 创建序列
create sequence jelly_meta_function_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle;

COMMENT ON COLUMN jelly_meta_function.id IS '主键';
COMMENT ON COLUMN jelly_meta_function.company_id IS '公司id';
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

COMMENT ON COLUMN jelly_template_engine.id IS '主键';
COMMENT ON COLUMN jelly_template_engine.company_id IS '公司id';
COMMENT ON COLUMN jelly_template_engine.name IS '名称';
COMMENT ON COLUMN jelly_template_engine.path IS '路径';
COMMENT ON COLUMN jelly_template_engine.script IS '脚本';
COMMENT ON COLUMN jelly_template_engine.create_time IS '创建时间';
COMMENT ON COLUMN jelly_template_engine.update_time IS '更新时间';
COMMENT ON TABLE jelly_template_engine IS '模版引擎';

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

COMMENT ON COLUMN sys_theme.id IS '主键';
COMMENT ON COLUMN sys_theme.user_id IS '用户id';
COMMENT ON COLUMN sys_theme.color IS '颜色值';
COMMENT ON COLUMN sys_theme.create_time IS '创建时间';
COMMENT ON COLUMN sys_theme.update_time IS '更新时间';
COMMENT ON TABLE sys_theme IS '用户主题';


INSERT INTO sys_company VALUES (1, NULL, 'seagox', '1001', '水母', '水母', 'avatar', TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'), TO_DATE('2021-07-06 10:23:34', 'SYYYY-MM-DD HH24:MI:SS'));
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

