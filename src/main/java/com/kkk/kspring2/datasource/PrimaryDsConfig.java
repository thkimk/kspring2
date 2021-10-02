package com.kkk.kspring2.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages="com.kkk.kspring2.datasource.primarymaps",sqlSessionFactoryRef="primarySqlSessionFactory")/*멀티DB사용시 mapper클래스파일 스켄용 basePackages를 DB별로 따로설정*/
@EnableTransactionManagement
public class PrimaryDsConfig {
    @Bean(name="primaryDs")
    @Primary
    @ConfigurationProperties(prefix="datasources.primary-ds")
    public DataSource primaryDs() {
        return DataSourceBuilder.create().build();

    }

    @Bean(name="primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDs") DataSource primaryDs, ApplicationContext applicationContext) throws Exception{
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(primaryDs);
        sessionFactory.setMapperLocations(applicationContext.getResources("classpath:mappers/primary-maps/*.xml")); //쿼리작성용 mapper.xml위치 설정.
        return sessionFactory.getObject();
    }

    @Bean(name="primarySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory primarySqlSessionFactory) throws Exception{
        return new SqlSessionTemplate(primarySqlSessionFactory);
    }

    @Bean(name = "primaryTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(@Qualifier("primaryDs") DataSource primaryDs) {
        return new DataSourceTransactionManager(primaryDs);
    }
}