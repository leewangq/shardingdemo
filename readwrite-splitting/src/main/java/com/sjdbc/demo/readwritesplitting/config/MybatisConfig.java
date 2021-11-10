package com.sjdbc.demo.readwritesplitting.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionManager;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @Descirption
 * @Author lx
 * @Date 2021/11/5
 */
@Configuration
@MapperScan("com.sjdbc.demo.readwritesplitting.mapper")
public class MybatisConfig {

    @Autowired
    private DataSource dataSource;

    @Value("${mybatis.mapper-locations}")
    private String mapper ;

    @Bean
    public SqlSessionManager createSqlSessionManager() {
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
