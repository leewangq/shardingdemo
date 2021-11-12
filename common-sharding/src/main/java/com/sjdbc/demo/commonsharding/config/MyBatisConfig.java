package com.sjdbc.demo.commonsharding.config;

import com.sjdbc.demo.commonsharding.thread.PersonManager;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionManager;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author xiang2.liu
 * @version 1.0
 * @description: mybatis配置
 * SqlSessionManager里面的localSqlSession内存溢出的担忧
 * LocalMap使用弱引用会解决内存溢出问题，当无强引用指向value内存区域是，此时进行gc，弱引用会被释放，对象将会执行回收流程。
 * 参考链接:https://www.jianshu.com/p/cdb2ea3792b5
 * @date 2021/9/9 11:26
 */
@Configuration
@MapperScan(value = "com.sjdbc.demo.commonsharding.mapper.order",sqlSessionFactoryRef = "shardingSqlSessionFactory")
public class MyBatisConfig {

    @Autowired
    private DataSource dataSource;

    private String mapper = "classpath:mapper/order/*.xml";

    @Bean(name="shardingSqlSessionFactory")
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

    @Bean
    public PersonManager createPersonManager() {
        return new PersonManager();
    }
}
