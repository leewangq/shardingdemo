package com.sjdbc.demo.commonsharding.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.keygen.KeyGenerateStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.HintShardingStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.apache.shardingsphere.sharding.route.strategy.type.hint.HintShardingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author xiang2.liu
 * @version 1.0
 * @description: java api方式配置数据源
 * @date 2021/9/7 18:03
 */
@Component
public class DataSourceConfig {
    @Bean
    public DataSource createDataSource() throws SQLException {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        //数据源配置
        DruidDataSource dataSource1 = new DruidDataSource();
        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://localhost:3306/ts_0?characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        dataSource1.setUsername("root");
        dataSource1.setPassword("123456");
        dataSourceMap.put("ts_0", dataSource1);

        DruidDataSource dataSource2 = new DruidDataSource();
        dataSource2.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource2.setUrl("jdbc:mysql://localhost:3306/ts_1?characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        dataSource2.setUsername("root");
        dataSource2.setPassword("123456");
        dataSourceMap.put("ts_1", dataSource2);

        //配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        ShardingTableRuleConfiguration orderTableRuleConfig = new ShardingTableRuleConfiguration("t_order", "ts_0.t_order${0..1},ts_1.t_order${2..5}");

        // 分库策略
        orderTableRuleConfig.setDatabaseShardingStrategy(new StandardShardingStrategyConfiguration("user_id", "dbShardingAlgorithm"));

        // 分表策略
        orderTableRuleConfig.setTableShardingStrategy(new StandardShardingStrategyConfiguration("user_id", "tableShardingAlgorithm"));

        /*// hint强制路由分库策略
        orderTableRuleConfig.setDatabaseShardingStrategy(new HintShardingStrategyConfiguration("hint_test"));

        // hint强制路由分表策略
        orderTableRuleConfig.setTableShardingStrategy(new HintShardingStrategyConfiguration("hint_test"));*/

        orderTableRuleConfig.setKeyGenerateStrategy(new KeyGenerateStrategyConfiguration("id", "snowflake"));
        shardingRuleConfig.getTables().add(orderTableRuleConfig);

        //数据库分片逻辑
        Properties dbShardingAlgorithmProps = new Properties();
        //dbShardingAlgorithmProps.setProperty("algorithm-expression", "ds_${user_id % 2}");
        shardingRuleConfig.getShardingAlgorithms().put("dbShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("CUSTOM_SHARDING_DATABASE", dbShardingAlgorithmProps));

        //表分片逻辑
        Properties tableShardingAlgorithmProps = new Properties();
        //tableShardingAlgorithmProps.setProperty("algorithm-expression", "t_order${user_id % 2}");
        shardingRuleConfig.getShardingAlgorithms().put("tableShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("CUSTOM_SHARDING_TABLE", tableShardingAlgorithmProps));

        //配置hint分片算法，根据Type匹配
        //shardingRuleConfig.getShardingAlgorithms().put("hint_test", new ShardingSphereAlgorithmConfiguration("HINT_TEST",  new Properties()));

        shardingRuleConfig.getKeyGenerators().put("snowflake", new ShardingSphereAlgorithmConfiguration("SNOWFLAKE", getKeyGeneratorsProperties()));
        //创建分片数据源
        Properties properties = new Properties();
        properties.setProperty("sql-show", "true");//之前版本是sql.show
        DataSource dataSource = ShardingSphereDataSourceFactory.createDataSource(dataSourceMap, Collections.singleton(shardingRuleConfig), properties);
        return dataSource;
    }

    private static Properties getKeyGeneratorsProperties() {
        Properties result = new Properties();
        result.setProperty("worker-id", "123");//工作进程id
        return result;
    }
}
