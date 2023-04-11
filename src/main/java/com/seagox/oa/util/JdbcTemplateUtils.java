package com.seagox.oa.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class JdbcTemplateUtils {

    public static long insert(JdbcTemplate jdbcTemplate, String tableName, Map<String, Object> params) {
        boolean oracleDbFlag = false;
        Number idKeyNum = null;
        if ("oracle".equals(params.get("_databaseId"))){
            oracleDbFlag = true;
            idKeyNum = jdbcTemplate.queryForObject("select " + tableName + "_seq.nextval as id from dual", Number.class);
            if (null == idKeyNum){
                throw new RuntimeException("获取序列值为空");
            }
        }
        if (null != params.get("_databaseId")){
            params.remove("_databaseId");
        }
        List<Object> valueArray = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO ");
        sql.append(tableName);
        if (oracleDbFlag){
            sql.append("(id,");
        } else {
            sql.append("(");
        }

        StringBuffer fieldSql = new StringBuffer();
        StringBuffer valueSql = new StringBuffer();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (!StringUtils.isEmpty(entry.getValue())) {
                fieldSql.append(entry.getKey());
                fieldSql.append(",");
                valueSql.append("?");
                valueSql.append(",");
                valueArray.add(entry.getValue());
            }
        }
        sql.append(fieldSql.substring(0, fieldSql.length() - 1));
        if (oracleDbFlag){
            sql.append(") values(").append(idKeyNum.longValue()).append(",");
        } else {
            sql.append(") values(");
        }

        sql.append(valueSql.substring(0, valueSql.length() - 1));
        sql.append(")");

        if (oracleDbFlag) {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql.toString(), new String[]{"id"});
                    for (int k = 0; k < valueArray.size(); k++) {
                        ps.setObject(k + 1, valueArray.get(k));
                    }
                    return ps;
                }
            });
            return idKeyNum.longValue();
        } else {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql.toString(), new String[]{"id"});
                    for (int k = 0; k < valueArray.size(); k++) {
                        ps.setString(k + 1, String.valueOf(valueArray.get(k)));
                        //valueArray.get(k).getClass().getTypeName();
                    }
                    return ps;
                }
            }, keyHolder);
            return keyHolder.getKey().longValue();
        }
    }

    public static int updateById(JdbcTemplate jdbcTemplate, String tableName, Map<String, Object> params) {
        JSONObject columnType = new JSONObject();
        SqlRowSet sqlRowSet = null;
        if ("oracle".equals(params.get("_databaseId"))){
            sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM " + tableName + " WHERE ROWNUM = 0");
        } else {
            sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM " + tableName + " LIMIT 0");
        }
        if (null != params.get("_databaseId")){
            params.remove("_databaseId");
        }
    	SqlRowSetMetaData sqlRowSetMetaData = sqlRowSet.getMetaData();
    	int columnCount = sqlRowSetMetaData.getColumnCount();
    	for (int i = 1; i <= columnCount; i++) {
    		columnType.put(sqlRowSetMetaData.getColumnName(i), sqlRowSetMetaData.getColumnTypeName(i));
    	}

        List<String> fieldArray = new ArrayList<>();
        List<Object> valueArray = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE ");
        sql.append(tableName);
        sql.append(" SET ");

        StringBuffer fieldSql = new StringBuffer();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (!entry.getKey().equals("id")) {
                fieldSql.append(entry.getKey());
                fieldSql.append("=");
                fieldSql.append("?");
                fieldSql.append(",");
                fieldArray.add(entry.getKey());
                valueArray.add(entry.getValue());
            }
        }

        sql.append(fieldSql.substring(0, fieldSql.length() - 1));
        sql.append(" WHERE id=");
        sql.append(params.get("id"));
        int res = jdbcTemplate.update(sql.toString(), new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                String fieldType;
                for (int k = 0; k < valueArray.size(); k++) {
                    if(StringUtils.isEmpty(valueArray.get(k))) {
                        fieldType = columnType.get(fieldArray.get(k)).toString();
                        if ("INT".equals(fieldType)) {
                            ps.setNull(k + 1, Types.INTEGER);
                        } else if ("BIGINT".equals(fieldType)) {
                            ps.setNull(k + 1, Types.BIGINT);
                        } else if ("DECIMAL".equals(fieldType)) {
                            ps.setNull(k + 1, Types.DECIMAL);
                        } else if ("DATETIME".equals(fieldType) || "DATE".equals(fieldType)) {
                            ps.setNull(k + 1, Types.DATE);
                        } else {
                            ps.setNull(k + 1, Types.VARCHAR);
                        }
                    } else {
                        ps.setString(k + 1, String.valueOf(valueArray.get(k)));
                    }
                }
            }
        });
        return res;
    }


    public static void batchInsert(JdbcTemplate jdbcTemplate, String tableName, List<Map<String, Object>> list) {
        Map<String, Object> params = list.get(0);
        List<Object> valueArray = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO ");
        sql.append(tableName);
        sql.append("(");

        StringBuffer fieldSql = new StringBuffer();
        StringBuffer valueSql = new StringBuffer();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            fieldSql.append(entry.getKey());
            fieldSql.append(",");
            valueSql.append("?");
            valueSql.append(",");
            valueArray.add(entry.getKey());
        }
        sql.append(fieldSql.substring(0, fieldSql.length() - 1));
        sql.append(") values(");

        sql.append(valueSql.substring(0, valueSql.length() - 1));
        sql.append(")");
        jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Map<String, Object> map = list.get(i);
                for (int k = 0; k < valueArray.size(); k++) {
                    ps.setString(k + 1, String.valueOf(map.get(valueArray.get(k))));
                }
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
    }

    public static void batchInsert(JdbcTemplate jdbcTemplate, String tableName, List<Map<String, Object>> list, String dbType) {
        Map<String, Object> params = list.get(0);
        List<Object> valueArray = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO ");
        sql.append(tableName);
        sql.append("(");

        StringBuffer fieldSql = new StringBuffer();
        StringBuffer valueSql = new StringBuffer();
        if ("oracle".equalsIgnoreCase(dbType)) {
        	fieldSql.append("id");
            fieldSql.append(",");
            valueSql.append(tableName + "_SEQ.NEXTVAL");
            valueSql.append(",");
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            fieldSql.append(entry.getKey());
            fieldSql.append(",");
            valueSql.append("?");
            valueSql.append(",");
            valueArray.add(entry.getKey());
        }
        sql.append(fieldSql.substring(0, fieldSql.length() - 1));
        sql.append(") values(");

        sql.append(valueSql.substring(0, valueSql.length() - 1));
        sql.append(")");
        jdbcTemplate.batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Map<String, Object> map = list.get(i);
                for (int k = 0; k < valueArray.size(); k++) {
                    ps.setObject(k + 1, map.get(valueArray.get(k)));
                }
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
    }
}
