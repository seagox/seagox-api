package com.seagox.oa.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class DataSourceUtils {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getDynamicJdbcTemplate(String url, String userName, String password, String driverClassName) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(driverClassName);
		jdbcTemplate.setDataSource(dataSource);
		return jdbcTemplate;
	}
}
