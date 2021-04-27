package com.ovopark.config;

import com.ovopark.interceptor.PagerPluginInterceptor;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author 余海
 * @version 1.0
 * @description 数据库连接配置
 * @create 2018-06-28 上午10:12
 */
@Configuration
@EnableTransactionManagement
@MapperScan(value = "com.ovopark.mapper", sqlSessionFactoryRef = "sqlSessionFactoryBean")
public class DatasourceConfig implements TransactionManagementConfigurer {

    private final static Logger logger = LoggerFactory.getLogger(DatasourceConfig.class);

    @Value(value = "classpath:mapping/*.xml")
    private Resource[] mapperLocations;

    @Value(value = "classpath:mybatis-config.xml")
    private Resource configLocation;


//    @Value("${spring.datasourceType}")
//    private Class<? extends DataSource> datasourceType;


    @Primary
    @Bean(name = "sqlSessionFactoryBean")
    public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) {
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        ssfb.setDataSource(dataSource);
        ssfb.setMapperLocations(mapperLocations);
        ssfb.setConfigLocation(configLocation);
        ssfb.setTypeAliasesPackage("com.ovopark.po");

        //拦截器
//        PagerPluginInterceptor pageInterceptor = new PagerPluginInterceptor();
//        Properties properties=new Properties();
//        pageInterceptor.setProperties(properties);
//        ssfb.setPlugins(new Interceptor[]{pageInterceptor});

        return ssfb;
    }

    @Primary
    @Bean(name = "dataSource", destroyMethod = "close") //initMethod = "init",
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource dataSource() {

        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}