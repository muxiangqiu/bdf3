package com.bstek.bdf3.dbconsole.utils;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

public class SpringJdbcUtils {
	public static TransactionTemplate getTransactionTemplate(DataSource dataSource) {  
        PlatformTransactionManager txManager = new DataSourceTransactionManager(  
        		dataSource);  
        TransactionTemplate txTemplate = new TransactionTemplate(txManager);  
        return txTemplate;  
    }  
  
    public static JdbcTemplate getJdbcTemplate(TransactionTemplate txTemplate) {  
        DataSourceTransactionManager txManager = (DataSourceTransactionManager) txTemplate  
                .getTransactionManager();  
        DataSource dataSource = txManager.getDataSource();  
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
        return jdbcTemplate;  
    }  
  
    public static JdbcTemplate getJdbcTemplate(DataSource dataSource) {  
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
        return jdbcTemplate;  
    }  
  

}
