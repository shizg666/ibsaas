package com.landleaf.ibsaas.datasource.autoconfig;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * @author
 * @version V1.0
 * @title: MybatisSessionfactoryAutoConfiguration
 * @description ${TO_DO}
 * @date 2017/8/6
 */
@Configuration
@EnableTransactionManagement
@AutoConfigureAfter(DynamicDatasourceAutoConfiguration.class)
@ConditionalOnProperty(prefix = "ibsaas.mybatis", name = "enable")
public class MybatisSessionfactoryAutoConfiguration implements TransactionManagementConfigurer {

    @Autowired(required = false)
    private DataSource dataSource;

    /**
     * 创建sqlSessionFactoryBean 实例
     *
     * @param
     * @author
     * @date 2017/8/6
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 设置datasource
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
