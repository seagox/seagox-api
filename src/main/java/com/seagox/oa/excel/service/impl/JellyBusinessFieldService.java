package com.seagox.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyBusinessField;
import com.seagox.oa.excel.entity.JellyBusinessTable;
import com.seagox.oa.excel.mapper.JellyBusinessFieldMapper;
import com.seagox.oa.excel.mapper.JellyBusinessTableMapper;
import com.seagox.oa.excel.service.IJellyBusinessFieldService;
import com.seagox.oa.template.FieldModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class JellyBusinessFieldService implements IJellyBusinessFieldService {

    @Autowired
    private JellyBusinessFieldMapper businessFieldMapper;

    @Autowired
    private JellyBusinessTableMapper businessTableMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value(value = "${spring.datasource.url}")
    private String datasourceUrl;

    @Override
    public ResultData queryAll(String tableName) {
        LambdaQueryWrapper<JellyBusinessTable> tableQw = new LambdaQueryWrapper<>();
        tableQw.eq(JellyBusinessTable::getName, tableName);
        JellyBusinessTable businessTable = businessTableMapper.selectOne(tableQw);
        if (businessTable != null) {
            LambdaQueryWrapper<JellyBusinessField> fieldQw = new LambdaQueryWrapper<>();
            fieldQw.eq(JellyBusinessField::getBusinessTableId, businessTable.getId());
            List<JellyBusinessField> list = businessFieldMapper.selectList(fieldQw);
            return ResultData.success(list);
        } else {
            return ResultData.success(null);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long businessTableId, String name, String remark) {
        PageHelper.startPage(pageNo, pageSize);
        LambdaQueryWrapper<JellyBusinessField> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyBusinessField::getBusinessTableId, businessTableId)
                .like(!StringUtils.isEmpty(name), JellyBusinessField::getName, name)
                .like(!StringUtils.isEmpty(remark), JellyBusinessField::getRemark, remark)
                .orderByDesc(JellyBusinessField::getCreateTime);
        List<JellyBusinessField> list = businessFieldMapper.selectList(qw);
        PageInfo<JellyBusinessField> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Transactional
    @Override
    public ResultData insert(JellyBusinessField businessField) {
        LambdaQueryWrapper<JellyBusinessField> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyBusinessField::getBusinessTableId, businessField.getBusinessTableId())
                .eq(JellyBusinessField::getName, businessField.getName());

        Long count = businessFieldMapper.selectCount(qw);
        if (count > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "字段已经存在");
        }
        addField(businessField);
        return ResultData.success(null);
    }

    @Transactional
    @Override
    public ResultData update(JellyBusinessField businessField) {
        JellyBusinessField originalBusinessField = businessFieldMapper.selectById(businessField.getId());
        if (originalBusinessField.getName().equals(businessField.getName())) {
            JellyBusinessTable businessTable = businessTableMapper.selectById(businessField.getBusinessTableId());
            if (datasourceUrl.contains("mysql")) {
                String sql = "ALTER TABLE table_name MODIFY COLUMN column_name column_type column_default COMMENT 'comment_name'";
                sql = sql.replaceAll("table_name", businessTable.getName());
                sql = sql.replaceAll("column_name", businessField.getName());
                if (businessField.getType().equals("decimal")) {
                    sql = "ALTER TABLE " + businessTable.getName() + " ALTER COLUMN " + businessField.getName() + " SET DEFAULT 0;" + sql;
                    sql = sql.replaceAll("column_type", businessField.getType() + "(" + 18 + "," + businessField.getDecimals() + ")");
                    businessField.setLength(18);
                } else if (businessField.getType().equals("varchar")) {
                    sql = sql.replaceAll("column_type", businessField.getType() + "(" + businessField.getLength() + ")");
                } else if (businessField.getType().equals("timestamp")) {
                    sql = sql.replaceAll("column_type", "datetime");
                } else if (businessField.getType().equals("text")) {
                    sql = sql.replaceAll("column_type", "longtext");
                } else {
                    sql = sql.replaceAll("column_type", businessField.getType());
                }
                if (businessField.getNotNull() == 1) {
                    //不为空
                    String columnDefault = "NOT NULL";
                    if (!StringUtils.isEmpty(businessField.getDefaultValue())) {
                        columnDefault = columnDefault + " DEFAULT " + businessField.getDefaultValue();
                    }
                    sql = sql.replaceAll("column_default", columnDefault);
                } else {
                    //为空
                    String columnDefault = "";
                    if (!StringUtils.isEmpty(businessField.getDefaultValue())) {
                        columnDefault = "DEFAULT " + businessField.getDefaultValue();
                    } else {
                        columnDefault = "DEFAULT NULL";
                    }
                    sql = sql.replaceAll("column_default", columnDefault);
                }
                sql = sql.replaceAll("comment_name", businessField.getRemark());
                jdbcTemplate.execute(sql);
            } else if (datasourceUrl.contains("postgresql")
                    || datasourceUrl.contains("kingbase8")
                    || datasourceUrl.contains("dm")) {
                String sql = "ALTER TABLE table_name ALTER COLUMN column_name TYPE column_type USING column_name::column_type;COMMENT ON COLUMN table_name.column_name IS 'comment_name';";
                sql = sql.replaceAll("table_name", businessTable.getName());
                sql = sql.replaceAll("column_name", businessField.getName());
                if (businessField.getType().equals("decimal")) {
                    sql = "ALTER TABLE " + businessTable.getName() + " ALTER COLUMN " + businessField.getName() + " SET DEFAULT 0;" + sql;
                    sql = sql.replaceAll("column_type", businessField.getType() + "(" + 18 + "," + businessField.getDecimals() + ")");
                    businessField.setLength(18);
                } else if (businessField.getType().equals("varchar")) {
                    sql = sql.replaceAll("column_type", businessField.getType() + "(" + businessField.getLength() + ")");
                } else {
                    sql = sql.replaceAll("column_type", businessField.getType());
                }
                sql = sql.replaceAll("comment_name", businessField.getRemark());
                jdbcTemplate.execute(sql);
            } else if (datasourceUrl.contains("oracle")) {
                String sql = "ALTER TABLE table_name MODIFY (column_name column_type);COMMENT ON COLUMN table_name.column_name IS 'comment_name'";
                sql = sql.replaceAll("table_name", businessTable.getName());
                sql = sql.replaceAll("column_name", businessField.getName());
                if (businessField.getType().equals("number")) {
                    sql = sql.replaceAll("column_type", businessField.getType() + "(" + businessField.getLength() + "," + businessField.getDecimals() + ")");
                } else if (businessField.getType().equals("varchar")) {
                    sql = sql.replaceAll("column_type", "varchar2" + "(" + businessField.getLength() + ")");
                } else if (businessField.getType().equals("text") || businessField.getType().equals("json")) {
                    sql = sql.replaceAll("column_type", "clob");
                } else {
                    sql = sql.replaceAll("column_type", businessField.getType());
                }
                sql = sql.replaceAll("comment_name", businessField.getRemark());
                String[] splitSql = sql.split(";");
                for (String oracleSql : splitSql) {
                    jdbcTemplate.execute(oracleSql);
                }
            }
        } else {
            LambdaQueryWrapper<JellyBusinessField> qw = new LambdaQueryWrapper<>();
            qw.eq(JellyBusinessField::getBusinessTableId, businessField.getBusinessTableId())
                    .eq(JellyBusinessField::getName, businessField.getName());

            Long count = businessFieldMapper.selectCount(qw);
            if (count > 0) {
                return ResultData.warn(ResultCode.OTHER_ERROR, "字段已经存在");
            } else {
                JellyBusinessTable businessTable = businessTableMapper.selectById(businessField.getBusinessTableId());
                if (datasourceUrl.contains("mysql")) {
                    String sql = "ALTER TABLE table_name CHANGE old_column_name new_column_name column_type column_default COMMENT 'comment_name'";
                    sql = sql.replaceAll("table_name", businessTable.getName());
                    sql = sql.replaceAll("old_column_name", originalBusinessField.getName());
                    sql = sql.replaceAll("new_column_name", businessField.getName());
                    if (businessField.getType().equals("decimal")) {
                        sql = sql.replaceAll("column_type", businessField.getType() + "(" + businessField.getLength() + "," + businessField.getDecimals() + ")");
                    } else if (businessField.getType().equals("varchar")) {
                        sql = sql.replaceAll("column_type", businessField.getType() + "(" + businessField.getLength() + ")");
                    } else {
                        sql = sql.replaceAll("column_type", businessField.getType());
                    }
                    if (businessField.getNotNull() == 1) {
                        //不为空
                        String columnDefault = "NOT NULL";
                        if (!StringUtils.isEmpty(businessField.getDefaultValue())) {
                            columnDefault = columnDefault + " DEFAULT " + businessField.getDefaultValue();
                        }
                        sql = sql.replaceAll("column_default", columnDefault);
                    } else {
                        //为空
                        String columnDefault = "";
                        if (!StringUtils.isEmpty(businessField.getDefaultValue())) {
                            columnDefault = "DEFAULT " + businessField.getDefaultValue();
                        } else {
                            columnDefault = "DEFAULT NULL";
                        }
                        sql = sql.replaceAll("column_default", columnDefault);
                    }
                    sql = sql.replaceAll("comment_name", businessField.getRemark());
                    jdbcTemplate.execute(sql);
                } else if (datasourceUrl.contains("postgresql")
                        || datasourceUrl.contains("kingbase8")
                        || datasourceUrl.contains("dm")) {
                    String sql = "ALTER TABLE table_name RENAME old_column_name TO new_column_name;COMMENT ON COLUMN table_name.new_column_name IS 'comment_name';";
                    sql = sql.replaceAll("table_name", businessTable.getName());
                    sql = sql.replaceAll("old_column_name", originalBusinessField.getName());
                    sql = sql.replaceAll("new_column_name", businessField.getName());
                    sql = sql.replaceAll("comment_name", businessField.getRemark());
                    jdbcTemplate.execute(sql);
                } else if (datasourceUrl.contains("oracle")) {
                    String sql = "ALTER TABLE table_name RENAME COLUMN old_column_name TO new_column_name;COMMENT ON COLUMN table_name.new_column_name IS 'comment_name'";
                    sql = sql.replaceAll("table_name", businessTable.getName());
                    sql = sql.replaceAll("old_column_name", originalBusinessField.getName());
                    sql = sql.replaceAll("new_column_name", businessField.getName());
                    sql = sql.replaceAll("comment_name", businessField.getRemark());
                    String[] splitSql = sql.split(";");
                    for (String oracleSql : splitSql) {
                        jdbcTemplate.execute(oracleSql);
                    }
                }
            }
        }
        businessFieldMapper.updateById(businessField);
        return ResultData.success(null);
    }

    @Transactional
    @Override
    public ResultData delete(Long id) {
        JellyBusinessField businessField = businessFieldMapper.selectById(id);
        JellyBusinessTable businessTable = businessTableMapper.selectById(businessField.getBusinessTableId());
        StringBuffer sql = new StringBuffer();
        sql.append("ALTER TABLE ");
        sql.append(businessTable.getName());
        sql.append(" DROP ");
        if (datasourceUrl.contains("oracle")
                || datasourceUrl.contains("sqlserver")) {
            sql.append(" column ");
        }
        sql.append(businessField.getName());
        jdbcTemplate.execute(sql.toString());
        businessFieldMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Override
    public ResultData queryByTableId(Long tableId) {
        LambdaQueryWrapper<JellyBusinessField> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyBusinessField::getBusinessTableId, tableId);
        List<JellyBusinessField> list = businessFieldMapper.selectList(qw);
        return ResultData.success(list);
    }

	@Override
	public ResultData importHandle(Long tableId, List<FieldModel> resultList) {
        for(int i=0;i<resultList.size();i++) {
        	FieldModel fieldModel = resultList.get(i);
        	LambdaQueryWrapper<JellyBusinessField> qw = new LambdaQueryWrapper<>();
        	qw.eq(JellyBusinessField::getBusinessTableId, tableId)
                    .eq(JellyBusinessField::getName, fieldModel.getName());
            Long count = businessFieldMapper.selectCount(qw);
            if (count > 0) {
                return ResultData.warn(ResultCode.OTHER_ERROR, "第" + (i+2) + "行的错误是：" + "字段名" + fieldModel.getName() +"已经存在");
            }
        }
        for(int i=0;i<resultList.size();i++) {
        	FieldModel fieldModel = resultList.get(i);
        	JellyBusinessField businessField = new JellyBusinessField();
        	businessField.setBusinessTableId(tableId);
        	businessField.setName(fieldModel.getName());
        	businessField.setRemark(fieldModel.getRemark());
        	businessField.setType(fieldModel.getType());
        	businessField.setKind(1);
        	if(fieldModel.getLength() == null) {
        		businessField.setLength(30);
        	} else {
        		businessField.setLength(fieldModel.getLength());
        	}
        	if(fieldModel.getDecimals() == null) {
        		businessField.setDecimals(0);
        	} else {
        		businessField.setDecimals(fieldModel.getDecimals());
        	}
        	if(fieldModel.getNotNull() == null) {
        		businessField.setNotNull(0);
        	} else {
        		businessField.setNotNull(fieldModel.getNotNull());
        	}
        	businessField.setDefaultValue(fieldModel.getDefaultValue());
        	addField(businessField);
        }
        return ResultData.success(null);
	}
	
	public void addField(JellyBusinessField businessField) {
		JellyBusinessTable businessTable = businessTableMapper.selectById(businessField.getBusinessTableId());
        if (datasourceUrl.contains("mysql")) {
            String sql = "ALTER TABLE table_name ADD COLUMN column_name column_type column_default COMMENT 'comment_name'";
            sql = sql.replaceAll("table_name", businessTable.getName());
            sql = sql.replaceAll("column_name", businessField.getName());
            if (businessField.getType().equals("decimal")) {
                sql = sql.replaceAll("column_type", businessField.getType() + "(" + 18 + "," + businessField.getDecimals() + ")");
            } else if (businessField.getType().equals("varchar")) {
                sql = sql.replaceAll("column_type", businessField.getType() + "(" + businessField.getLength() + ")");
            } else if (businessField.getType().equals("timestamp")) {
                sql = sql.replaceAll("column_type", "datetime");
            } else if (businessField.getType().equals("text")) {
                sql = sql.replaceAll("column_type", "longtext");
            } else {
                sql = sql.replaceAll("column_type", businessField.getType());
            }
            if (businessField.getNotNull() == 1) {
                //不为空
                String columnDefault = "NOT NULL";
                if (!StringUtils.isEmpty(businessField.getDefaultValue())) {
                    columnDefault = columnDefault + " DEFAULT " + businessField.getDefaultValue();
                }
                sql = sql.replaceAll("column_default", columnDefault);
            } else {
                //为空
                String columnDefault = "";
                if (!StringUtils.isEmpty(businessField.getDefaultValue())) {
                    columnDefault = "DEFAULT " + businessField.getDefaultValue();
                } else {
                    columnDefault = "DEFAULT NULL";
                }
                sql = sql.replaceAll("column_default", columnDefault);
            }
            sql = sql.replaceAll("comment_name", businessField.getRemark());
            jdbcTemplate.execute(sql);
        } else if (datasourceUrl.contains("postgresql")
                || datasourceUrl.contains("kingbase8")
                || datasourceUrl.contains("dm")) {
            String sql = "ALTER TABLE table_name ADD COLUMN column_name column_type column_default;COMMENT ON COLUMN table_name.column_name IS 'comment_name';";
            sql = sql.replaceAll("table_name", businessTable.getName());
            sql = sql.replaceAll("column_name", businessField.getName());
            if (businessField.getNotNull() == 1) {
                //不为空
                String columnDefault = "NOT NULL";
                if (!StringUtils.isEmpty(businessField.getDefaultValue())) {
                    columnDefault = columnDefault + " DEFAULT " + businessField.getDefaultValue();
                }
                sql = sql.replaceAll("column_default", columnDefault);
            } else {
                //为空
                String columnDefault = "";
                if (!StringUtils.isEmpty(businessField.getDefaultValue())) {
                    columnDefault = "DEFAULT " + businessField.getDefaultValue();
                } else {
                    columnDefault = "DEFAULT NULL";
                }
                sql = sql.replaceAll("column_default", columnDefault);
            }

            if (businessField.getType().equals("decimal")) {
                sql = sql.replaceAll("column_type", businessField.getType() + "(" + 18 + "," + businessField.getDecimals() + ")");
            } else if (businessField.getType().equals("varchar")) {
                sql = sql.replaceAll("column_type", businessField.getType() + "(" + businessField.getLength() + ")");
            } else {
                sql = sql.replaceAll("column_type", businessField.getType());
            }
            sql = sql.replaceAll("comment_name", businessField.getRemark());
            jdbcTemplate.execute(sql);
        } else if (datasourceUrl.contains("oracle")) {
            String sql = "ALTER TABLE table_name ADD column_name column_type column_default;COMMENT ON COLUMN table_name.column_name IS 'comment_name'";
            sql = sql.replaceAll("table_name", businessTable.getName());
            sql = sql.replaceAll("column_name", businessField.getName());
            if (businessField.getNotNull() == 1) {
                //不为空
                String columnDefault = "NOT NULL";
                if (!StringUtils.isEmpty(businessField.getDefaultValue())) {
                    columnDefault = columnDefault + " DEFAULT '" + businessField.getDefaultValue() + "'";
                }
                sql = sql.replaceAll("column_default", columnDefault);
            } else {
                //为空
                String columnDefault = "";
                if (!StringUtils.isEmpty(businessField.getDefaultValue())) {
                    columnDefault = "DEFAULT '" + businessField.getDefaultValue() + "'";
                } else {
                    columnDefault = "DEFAULT NULL";
                }
                sql = sql.replaceAll("column_default", columnDefault);
            }

            if (businessField.getType().equals("number")) {
                sql = sql.replaceAll("column_type", businessField.getType() + "(" + businessField.getLength() + "," + businessField.getDecimals() + ")");
            } else if (businessField.getType().equals("varchar")) {
                sql = sql.replaceAll("column_type", "varchar2" + "(" + businessField.getLength() + ")");
            } else if (businessField.getType().equals("text") || businessField.getType().equals("json")) {
                sql = sql.replaceAll("column_type", "clob");
            } else {
                sql = sql.replaceAll("column_type", businessField.getType());
            }
            sql = sql.replaceAll("comment_name", businessField.getRemark());
            String[] splitSql = sql.split(";");
            for (String oracleSql : splitSql) {
                jdbcTemplate.execute(oracleSql);
            }
        }

        businessFieldMapper.insert(businessField);
	}

}
