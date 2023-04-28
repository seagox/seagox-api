package com.seagox.oa.excel.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyBusinessField;
import com.seagox.oa.excel.entity.JellyBusinessTable;
import com.seagox.oa.excel.mapper.JellyBusinessFieldMapper;
import com.seagox.oa.excel.mapper.JellyBusinessTableMapper;
import com.seagox.oa.excel.service.IJellyBusinessTableService;
import com.seagox.oa.sys.entity.SysCompany;
import com.seagox.oa.sys.mapper.CompanyMapper;
import com.seagox.oa.util.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class JellyBusinessTableService implements IJellyBusinessTableService {

    @Autowired
    private JellyBusinessTableMapper businessTableMapper;

    @Autowired
    private JellyBusinessFieldMapper businessFieldMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CompanyMapper companyMapper;

    @Value(value = "${spring.datasource.url}")
    private String datasourceUrl;

    @Override
    public ResultData queryAll(Long companyId, String name) {
    	LambdaQueryWrapper<JellyBusinessTable> qw = new LambdaQueryWrapper<>();
    	qw.eq(JellyBusinessTable::getCompanyId, companyId)
    	.eq(!StringUtils.isEmpty(name), JellyBusinessTable::getName, name);
        List<JellyBusinessTable> list = businessTableMapper.selectList(qw);
		return ResultData.success(list);
    }

    @Transactional
    @Override
    public ResultData insert(JellyBusinessTable businessTable) {
        SysCompany company = companyMapper.selectById(businessTable.getCompanyId());
        businessTable.setName(company.getMark() + "_" + businessTable.getName());
        LambdaQueryWrapper<JellyBusinessTable> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(JellyBusinessTable::getName, businessTable.getName());
        int count = businessTableMapper.selectCount(queryWrapper);
        if (count == 0) {
            businessTableMapper.insert(businessTable);

            StringBuffer sql = new StringBuffer();
            if (datasourceUrl.contains("mysql")) {
                sql.append("CREATE TABLE ");
                sql.append(businessTable.getName());
                sql.append(" (");
                sql.append("id bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键',");
            } else if (datasourceUrl.contains("postgresql") || datasourceUrl.contains("kingbase8")) {
                sql.append("CREATE TABLE \"");
                sql.append(businessTable.getName());
                sql.append("\" (");
                sql.append("\"id\" bigserial NOT NULL PRIMARY KEY,");
            } else if (datasourceUrl.contains("oracle")) {
                sql.append("CREATE TABLE ");
                sql.append(businessTable.getName());
                sql.append(" (");
                sql.append("id number(20) NOT NULL PRIMARY KEY,");
            } else if (datasourceUrl.contains("dm")) {
                sql.append("CREATE TABLE \"");
                sql.append(businessTable.getName());
                sql.append("\" (");
                sql.append("\"id\" BIGINT(20) IDENTITY(1,1) PRIMARY KEY NOT NULL,");
            } else if (datasourceUrl.contains("sqlserver")) {
                sql.append("CREATE TABLE \"");
                sql.append(businessTable.getName());
                sql.append("\" (");
                sql.append("\"id\" bigint IDENTITY(1,1) PRIMARY KEY NOT NULL,");
            }
            if (!StringUtils.isEmpty(businessTable.getInitialize())) {
                String[] initializeArray = businessTable.getInitialize().split(",");
                for (String str : initializeArray) {
                    JellyBusinessField businessField = new JellyBusinessField();
                    businessField.setBusinessTableId(businessTable.getId());
                    businessField.setName(str);
                    businessField.setNotNull(1);
                    if (str.equals("company_id")) {
                        if (datasourceUrl.contains("mysql")) {
                            sql.append("company_id bigint(20) NOT NULL COMMENT '公司id',");
                        } else if (datasourceUrl.contains("postgresql")
                                || datasourceUrl.contains("kingbase8")
                                || datasourceUrl.contains("sqlserver")) {
                            sql.append("\"company_id\" bigint NOT NULL,");
                        } else if (datasourceUrl.contains("oracle")) {
                            sql.append("company_id number(20) NOT NULL,");
                        }
                        businessField.setRemark("公司id");
                        businessField.setType("bigint");
                        businessField.setLength(20);
                        businessField.setKind(3);
                    } else if (str.equals("user_id")) {
                        if (datasourceUrl.contains("mysql")) {
                            sql.append("user_id bigint(20) NOT NULL COMMENT '用户id',");
                        } else if (datasourceUrl.contains("postgresql")
                                || datasourceUrl.contains("kingbase8")
                                || datasourceUrl.contains("dm")
                                || datasourceUrl.contains("sqlserver")) {
                            sql.append("\"user_id\" bigint NOT NULL,");
                        } else if (datasourceUrl.contains("oracle")) {
                            sql.append("user_id number(20) NOT NULL,");
                        }
                        businessField.setRemark("用户id");
                        businessField.setType("bigint");
                        businessField.setLength(20);
                        businessField.setKind(5);
                    } else if (str.equals("is_valid")) {
                        if (datasourceUrl.contains("mysql")) {
                            sql.append("is_valid int(4) NOT NULL COMMENT '是否有效(1:是;2:否;)',");
                        } else if (datasourceUrl.contains("postgresql")
                                || datasourceUrl.contains("kingbase8")
                                || datasourceUrl.contains("dm")
                                || datasourceUrl.contains("sqlserver")) {
                            sql.append("\"is_valid\" int NOT NULL,");
                        } else if (datasourceUrl.contains("oracle")) {
                            sql.append("is_valid number(4) NOT NULL,");
                        }
                        businessField.setRemark("是否有效(1:是;2:否;)");
                        businessField.setType("int");
                        businessField.setLength(4);
                    } else if (str.equals("is_submit")) {
                        if (datasourceUrl.contains("mysql")) {
                            sql.append("is_submit int(4) NOT NULL COMMENT '是否提交(1:是;2:否;)',");
                        } else if (datasourceUrl.contains("postgresql")
                                || datasourceUrl.contains("kingbase8")
                                || datasourceUrl.contains("dm")
                                || datasourceUrl.contains("sqlserver")) {
                            sql.append("\"is_submit\" int NOT NULL,");
                        } else if (datasourceUrl.contains("oracle")) {
                            sql.append("is_submit number(4) NOT NULL,");
                        }
                        businessField.setRemark("是否提交(1:是;2:否;)");
                        businessField.setType("int");
                        businessField.setLength(4);
                    }
                    businessFieldMapper.insert(businessField);
                }
            }
            if (datasourceUrl.contains("mysql")) {
                sql.append("create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',");
                sql.append("update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'");
                sql.append(" ) ");
                sql.append("ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '");
                sql.append(businessTable.getRemark());
                sql.append("'");
            } else if (datasourceUrl.contains("postgresql")
                    || datasourceUrl.contains("kingbase8")
                    || datasourceUrl.contains("dm")) {
                sql.append("\"create_time\" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,");
                sql.append("\"update_time\" TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
                sql.append(" );");
                //添加表注释
                sql.append("COMMENT ON TABLE \"");
                sql.append(businessTable.getName());
                sql.append("\" IS ");
                sql.append("'");
                sql.append(businessTable.getRemark());
                sql.append("'");
                sql.append(";");
                //添加列id注释
                sql.append("COMMENT ON COLUMN \"");
                sql.append(businessTable.getName());
                sql.append("\".\"id\" IS ");
                sql.append("'主键';");
                //添加列companyId注释
                sql.append("COMMENT ON COLUMN \"");
                sql.append(businessTable.getName());
                sql.append("\".\"company_id\" IS ");
                sql.append("'公司id';");
                //添加列userId注释
                sql.append("COMMENT ON COLUMN \"");
                sql.append(businessTable.getName());
                sql.append("\".\"user_id\" IS ");
                sql.append("'用户id';");
                //添加列create_time注释
                sql.append("COMMENT ON COLUMN \"");
                sql.append(businessTable.getName());
                sql.append("\".\"create_time\" IS ");
                sql.append("'创建时间';");
                //添加列update_time注释
                sql.append("COMMENT ON COLUMN \"");
                sql.append(businessTable.getName());
                sql.append("\".\"update_time\" IS ");
                sql.append("'更新时间';");
            } else if (datasourceUrl.contains("oracle")) {
                sql.append("create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,");
                sql.append("update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
                sql.append(" );");
                //添加表注释
                sql.append("COMMENT ON TABLE ");
                sql.append(businessTable.getName());
                sql.append(" IS ");
                sql.append("'");
                sql.append(businessTable.getRemark());
                sql.append("'");
                sql.append(";");
                //添加列id注释
                sql.append("COMMENT ON COLUMN ");
                sql.append(businessTable.getName());
                sql.append(".id IS ");
                sql.append("'主键';");
                //添加列companyId注释
                sql.append("COMMENT ON COLUMN ");
                sql.append(businessTable.getName());
                sql.append(".company_id IS ");
                sql.append("'公司id';");
                //添加列userId注释
                sql.append("COMMENT ON COLUMN ");
                sql.append(businessTable.getName());
                sql.append(".user_id IS ");
                sql.append("'用户id';");
                //添加列create_time注释
                sql.append("COMMENT ON COLUMN ");
                sql.append(businessTable.getName());
                sql.append(".create_time IS ");
                sql.append("'创建时间';");
                //添加列update_time注释
                sql.append("COMMENT ON COLUMN ");
                sql.append(businessTable.getName());
                sql.append(".update_time IS ");
                sql.append("'更新时间'");
            } else if (datasourceUrl.contains("sqlserver")) {
                sql.append("\"create_time\" datetime2(7) DEFAULT CURRENT_TIMESTAMP,");
                sql.append("\"update_time\" datetime2(7) DEFAULT CURRENT_TIMESTAMP");
                sql.append(" );");
                //添加表注释
                sql.append("EXEC sp_addextendedproperty ");
                sql.append("'MS_Description', N'");
                sql.append(businessTable.getRemark());
                sql.append("',");
                sql.append("'SCHEMA', N'dbo',");
                sql.append("'TABLE', N'");
                sql.append(businessTable.getName());
                sql.append("';");
                //添加列id注释
                sql.append("EXEC sp_addextendedproperty ");
                sql.append("'MS_Description', N'");
                sql.append("主键");
                sql.append("',");
                sql.append("'SCHEMA', N'dbo',");
                sql.append("'TABLE', N'");
                sql.append(businessTable.getName());
                sql.append("',");
                sql.append("'COLUMN', N'");
                sql.append("id';");
                //添加列companyId注释
                sql.append("EXEC sp_addextendedproperty ");
                sql.append("'MS_Description', N'");
                sql.append("公司id");
                sql.append("',");
                sql.append("'SCHEMA', N'dbo',");
                sql.append("'TABLE', N'");
                sql.append(businessTable.getName());
                sql.append("',");
                sql.append("'COLUMN', N'");
                sql.append("company_id';");
                //添加列userId注释
                sql.append("EXEC sp_addextendedproperty ");
                sql.append("'MS_Description', N'");
                sql.append("用户id");
                sql.append("',");
                sql.append("'SCHEMA', N'dbo',");
                sql.append("'TABLE', N'");
                sql.append(businessTable.getName());
                sql.append("',");
                sql.append("'COLUMN', N'");
                sql.append("user_id';");
                //添加列create_time注释
                sql.append("EXEC sp_addextendedproperty ");
                sql.append("'MS_Description', N'");
                sql.append("创建时间");
                sql.append("',");
                sql.append("'SCHEMA', N'dbo',");
                sql.append("'TABLE', N'");
                sql.append(businessTable.getName());
                sql.append("',");
                sql.append("'COLUMN', N'");
                sql.append("create_time';");
                //添加列update_time注释
                sql.append("EXEC sp_addextendedproperty ");
                sql.append("'MS_Description', N'");
                sql.append("更新时间");
                sql.append("',");
                sql.append("'SCHEMA', N'dbo',");
                sql.append("'TABLE', N'");
                sql.append(businessTable.getName());
                sql.append("',");
                sql.append("'COLUMN', N'");
                sql.append("update_time';");
            }
            if (datasourceUrl.contains("oracle")){
                String[] splitSql = sql.toString().split(";");
                for (String oracleSql : splitSql) {
                    jdbcTemplate.execute(oracleSql);
                }
                // 创建序列
                String sequenceSql = "create sequence "+ businessTable.getName().trim() +"_seq increment by 1 start with 1 nomaxvalue minvalue 1 nocycle";
                jdbcTemplate.execute(sequenceSql);
                // 创建触发器
//                String triggerSql = "create or replace trigger "+ businessTable.getName().trim() +"_trigger " +
//                        "before insert on " + businessTable.getName().trim() +
//                        " for each row " +
//                        "begin " +
//                        "select "+ businessTable.getName().trim() +"_seq.nextval into :new.id from dual;" +
//                        "end;";
//                jdbcTemplate.execute(triggerSql);
            }else{
                jdbcTemplate.execute(sql.toString());
            }
            return ResultData.success(null);
        } else {
            return ResultData.warn(ResultCode.OTHER_ERROR, "表名已经存在");
        }
    }

    @Transactional
    @Override
    public ResultData update(JellyBusinessTable businessTable) {
        JellyBusinessTable originalBusinessTable = businessTableMapper.selectById(businessTable.getId());
        if (!originalBusinessTable.getName().equals(businessTable.getName())) {
            //更改表名
            StringBuffer sql = new StringBuffer();
            if (datasourceUrl.contains("mysql")
                    || datasourceUrl.contains("postgresql")
                    || datasourceUrl.contains("kingbase8")
                    || datasourceUrl.contains("dm")) {
                sql.append("ALTER TABLE ");
                sql.append(originalBusinessTable.getName());
                sql.append(" rename to ");
                sql.append(businessTable.getName());
            } else if (datasourceUrl.contains("sqlserver")) {
                sql.append("sp_rename ");
                sql.append(originalBusinessTable.getName());
                sql.append(",");
                sql.append(businessTable.getName());
            } else if (datasourceUrl.contains("oracle")){
                sql.append("ALTER TABLE ");
                sql.append(originalBusinessTable.getName());
                sql.append(" rename to ");
                sql.append(businessTable.getName());
                // 更新原表名序列
                String updateSequenceSql = "rename " + originalBusinessTable.getName().trim() + "_seq" + " to " + businessTable.getName().trim() + "_seq";
                jdbcTemplate.execute(updateSequenceSql);
                // 删除原表名触发器
//                String deleteTriggerSql = "drop trigger " + originalBusinessTable.getName().trim() + "_trigger";
//                jdbcTemplate.execute(deleteTriggerSql);
            }
            jdbcTemplate.execute(sql.toString());
//            if (datasourceUrl.contains("oracle")){
//                // 新建触发器
//                String triggerSql = "create or replace trigger "+ businessTable.getName().trim() +"_trigger " +
//                        "before insert on " + businessTable.getName().trim() +
//                        " for each row " +
//                        "begin " +
//                        "select "+ businessTable.getName().trim() +"_seq.nextval into :new.id from dual;" +
//                        "end;";
//                jdbcTemplate.execute(triggerSql);
//            }
        }
        if (!originalBusinessTable.getRemark().equals(businessTable.getRemark())) {
            //更新表注释
            StringBuffer sql = new StringBuffer();
            if (datasourceUrl.contains("mysql")) {
                sql.append("ALTER TABLE ");
                sql.append(businessTable.getName());
                sql.append(" comment ");
                sql.append("'");
                sql.append(businessTable.getRemark());
                sql.append("'");
            } else if (datasourceUrl.contains("postgresql")
                    || datasourceUrl.contains("kingbase8")
                    || datasourceUrl.contains("dm")) {
                sql.append("COMMENT ON TABLE ");
                sql.append(businessTable.getName());
                sql.append(" IS ");
                sql.append("'");
                sql.append(businessTable.getRemark());
                sql.append("'");
            } else if (datasourceUrl.contains("sqlserver")) {
                sql.append("EXEC SP_UPDATEEXTENDEDPROPERTY ");
                sql.append("'MS_Description', N'");
                sql.append(businessTable.getRemark());
                sql.append("',");
                sql.append("'SCHEMA', N'dbo',");
                sql.append("'TABLE', N'");
                sql.append(businessTable.getName());
                sql.append("'");
            } else if (datasourceUrl.contains("oracle")) {
                sql.append("COMMENT ON TABLE ");
                sql.append(businessTable.getName());
                sql.append(" IS ");
                sql.append("'");
                sql.append(businessTable.getRemark());
                sql.append("'");
            }
            jdbcTemplate.execute(sql.toString());
        }
        businessTableMapper.updateById(businessTable);
        return ResultData.success(null);
    }

    @Transactional
    @Override
    public ResultData delete(Long id) {
        LambdaQueryWrapper<JellyBusinessField> queryWrapper = new LambdaQueryWrapper<JellyBusinessField>();
        queryWrapper.eq(JellyBusinessField::getBusinessTableId, id);
        int businessTableCount = businessFieldMapper.selectCount(queryWrapper);
        if (businessTableCount > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "有关联业务字段，不可删除");
        } else {
            JellyBusinessTable businessTable = businessTableMapper.selectById(id);
            StringBuffer sql = new StringBuffer();
            if (datasourceUrl.contains("oracle")){
                sql.append("DROP TABLE ");
                // 删除原表名序列
                String deleteSequenceSql = "drop sequence " + businessTable.getName().trim() + "_seq";
                jdbcTemplate.execute(deleteSequenceSql);
//                // 删除原表名触发器
//                String deleteTriggerSql = "drop trigger " + businessTable.getName().trim() + "_trigger";
//                jdbcTemplate.execute(deleteTriggerSql);
            } else {
                sql.append("DROP TABLE IF EXISTS ");
            }
            sql.append(businessTable.getName());
            jdbcTemplate.execute(sql.toString());
            businessTableMapper.deleteById(id);
            return ResultData.success(null);
        }
    }

    @Override
    public ResultData queryById(Long id) {
        return ResultData.success(businessTableMapper.selectById(id));
    }

    @Override
    public ResultData queryByTableIds(String tableIds) {
        LambdaQueryWrapper<JellyBusinessTable> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(!StringUtils.isEmpty(tableIds), JellyBusinessTable::getId, Arrays.asList(tableIds.split(",")));
        List<JellyBusinessTable> list = businessTableMapper.selectList(queryWrapper);
        return ResultData.success(list);
    }

    @Transactional
    @Override
    public ResultData copyTable(Long id) {
        JellyBusinessTable businessTable = businessTableMapper.selectById(id);
        StringBuffer sql = new StringBuffer();
        if (datasourceUrl.contains("mysql")) {
            sql.append("create table ");
            sql.append(businessTable.getName() + "_copy");
            sql.append(" like ");
            sql.append(businessTable.getName());
        } else if (datasourceUrl.contains("postgresql")) {
            sql.append("create table ");
            sql.append(businessTable.getName() + "_copy");
            sql.append(" (like ");
            sql.append(businessTable.getName());
            sql.append(" INCLUDING comments including constraints including indexes)");
        }
        jdbcTemplate.execute(sql.toString());
        businessTable.setName(businessTable.getName() + "_copy");
        businessTableMapper.insert(businessTable);

        LambdaQueryWrapper<JellyBusinessField> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyBusinessField::getBusinessTableId, id);
        List<JellyBusinessField> businessFieldList = businessFieldMapper.selectList(qw);
        for (int i = 0; i < businessFieldList.size(); i++) {
            JellyBusinessField businessField = businessFieldList.get(i);
            businessField.setBusinessTableId(businessTable.getId());
            businessFieldMapper.insert(businessField);
        }
        return ResultData.success(null);
    }

    @Override
    public ResultData queryByType(String type) {
        LambdaQueryWrapper<JellyBusinessTable> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(JellyBusinessTable::getName, "_" + type + "_");
        List<JellyBusinessTable> tableList = businessTableMapper.selectList(queryWrapper);
        return ResultData.success(tableList);
    }

    @Override
    public ResultData queryCascader(Long id, String rule) {
        JellyBusinessTable businessTable = businessTableMapper.selectById(id);
        if (businessTable != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from ");
            sql.append(businessTable.getName());
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
            JSONArray result = TreeUtils.listToTreeByRule(JSONArray.parseArray(JSON.toJSONString(list)),
                    "code", "children", rule);
            return ResultData.success(result);
        }
        return ResultData.success(null);
    }

}
