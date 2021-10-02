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
@MapperScan(basePackages="com.kkk.kspring2.datasource.secondmaps",sqlSessionFactoryRef="secondSqlSessionFactory")/*멀티DB사용시 mapper클래스파일 스켄용 basePackages를 DB별로 따로설정*/
@EnableTransactionManagement
public class SecondDsConfig {
    @Bean(name="secondDs")
    @ConfigurationProperties(prefix="datasources.second-ds")
    public DataSource secondDs() {
        return DataSourceBuilder.create().build();

    }

    @Bean(name="secondSqlSessionFactory")
    public SqlSessionFactory secondSqlSessionFactory(@Qualifier("secondDs") DataSource secondDs, ApplicationContext applicationContext) throws Exception{
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(secondDs);
        sessionFactory.setMapperLocations(applicationContext.getResources("classpath:mappers/second-maps/*.xml")); //쿼리작성용 mapper.xml위치 설정.
        return sessionFactory.getObject();
    }

    @Bean(name="secondSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("secondSqlSessionFactory") SqlSessionFactory secondSqlSessionFactory) throws Exception{
        return new SqlSessionTemplate(secondSqlSessionFactory);
    }

    @Bean(name = "secondTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("secondDs") DataSource secondDs) {
        return new DataSourceTransactionManager(secondDs);
    }
}