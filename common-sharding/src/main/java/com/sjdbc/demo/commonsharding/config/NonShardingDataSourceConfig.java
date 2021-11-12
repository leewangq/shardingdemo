package com.sjdbc.demo.commonsharding.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionManager;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @Descirption
 * @Author lx
 * @Date 2021/11/12
 */
@Configuration
@MapperScan(value = "com.sjdbc.demo.commonsharding.mapper.member",sqlSessionFactoryRef = "nonShardingSqlSessionFactory")
public class NonShardingDataSourceConfig {

    private String mapper = "classpath:mapper/member/*.xml";

    @Bean
    @Primary
    public DataSource memberDataSource() throws SQLException {
        DruidDataSource dataSource7 = new DruidDataSource();
        dataSource7.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource7.setUrl("jdbc:mysql://localhost:3306/member?characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        dataSource7.setUsername("root");
        dataSource7.setPassword("123456");
        dataSource7.setFilters("stat,wall");
        dataSource7.setInitialSize(5);
        dataSource7.setMinIdle(10);
        dataSource7.setMaxActive(20);
        dataSource7.setMaxWait(5000);
        return dataSource7;
    }

    @Bean(name="nonShardingSqlSessionFactory")
    @Primary
    public SqlSessionManager createSqlSessionManager(@Qualifier("memberDataSource")DataSource dataSource) {
        try {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapper));
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
            SqlSessionManager sqlSessionManager = SqlSessionManager.newInstance(sqlSessionFactory);
            return sqlSessionManager;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }
}
