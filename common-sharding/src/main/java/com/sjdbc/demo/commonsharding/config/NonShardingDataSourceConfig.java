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
        DruidDataSource member = new DruidDataSource();
        member.setDriverClassName("com.mysql.cj.jdbc.Driver");
        member.setUrl("jdbc:mysql://localhost:3306/member?characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        member.setUsername("root");
        member.setPassword("123456");
        member.setFilters("stat,wall");
        member.setInitialSize(5);
        member.setMinIdle(10);
        member.setMaxActive(20);
        member.setMaxWait(5000);
        return member;
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
