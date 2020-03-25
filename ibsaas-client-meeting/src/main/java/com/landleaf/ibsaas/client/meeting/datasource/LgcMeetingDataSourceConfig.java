package com.landleaf.ibsaas.client.meeting.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 *
 */
@Configuration
@MapperScan(basePackages = LgcMeetingDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "meetingSqlSessionFactory")
public class LgcMeetingDataSourceConfig {

    static final String PACKAGE = "com.landleaf.ibsaas.client.meeting.dal.dao.meeting";
    static final String MAPPER_LOCATION = "classpath:com/landleaf/ibsaas/client/meeting/dal/mappers/meeting/**/*.xml";

    @Value("${lgc.meeting.datasource.driverClassName}")
    private String driverClass;

    @Value("${lgc.meeting.datasource.url}")
    private String url;

    @Value("${lgc.meeting.datasource.username}")
    private String username;

    @Value("${lgc.meeting.datasource.password}")
    private String password;

    @Value("${lgc.meeting.druid.initialSize}")
    private int initialSize;

    @Value("${lgc.meeting.druid.minIdle}")
    private int minIdle;

    @Value("${lgc.meeting.druid.maxActive}")
    private int maxActive;

    @Value("${lgc.meeting.druid.maxWait}")
    private int maxWait;

    @Value("${lgc.meeting.druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${lgc.meeting.druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${lgc.meeting.druid.validationQuery}")
    private String validationQuery;

    @Value("${lgc.meeting.druid.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${lgc.meeting.druid.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${lgc.meeting.druid.testOnReturn}")
    private boolean testOnReturn;

    @Value("${lgc.meeting.druid.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${lgc.meeting.druid.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${lgc.meeting.druid.filters}")
    private String filters;

    @Value("${lgc.meeting.druid.connectionProperties}")
    private String connectionProperties;

    @Bean(name = "meetingDataSource")
    public DataSource meetingDataSource() {
        DruidDataSource meetingDataSource = new DruidDataSource();
        meetingDataSource.setDriverClassName(driverClass);
        meetingDataSource.setUrl(url);
        meetingDataSource.setUsername(username);
        meetingDataSource.setPassword(password);
        meetingDataSource.setInitialSize(initialSize);
        meetingDataSource.setMinIdle(minIdle);
        meetingDataSource.setMaxActive(maxActive);
        meetingDataSource.setMaxWait(maxWait);
        meetingDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        meetingDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        meetingDataSource.setValidationQuery(validationQuery);
        meetingDataSource.setTestWhileIdle(testWhileIdle);
        meetingDataSource.setTestOnBorrow(testOnBorrow);
        meetingDataSource.setTestOnReturn(testOnReturn);
        meetingDataSource.setPoolPreparedStatements(poolPreparedStatements);
        meetingDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            meetingDataSource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meetingDataSource;
    }

    /**
     * 配置事务
     * @return
     */
    @Bean(name = "meetingTransactionManager")
    @Primary
    public DataSourceTransactionManager meetingTransactionManager() {
        return new DataSourceTransactionManager(meetingDataSource());
    }

    @Bean(name = "meetingSqlSessionFactory")
    @Primary
    public SqlSessionFactory meetingSqlSessionFactory(@Qualifier("meetingDataSource") DataSource meetingDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(meetingDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(LgcMeetingDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }


}
