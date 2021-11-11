package com.sjdbc.demo.commonsharding.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.ImmutableMap;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.RuleConfiguration;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.readwritesplitting.api.ReadwriteSplittingRuleConfiguration;
import org.apache.shardingsphere.readwritesplitting.api.rule.ReadwriteSplittingDataSourceRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.keygen.KeyGenerateStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

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

        Map<String, DataSource> dataSourceMap = createDataSourceMap();
        ShardingRuleConfiguration shardingRuleConfig = createShardingRuleConfig();
        ReadwriteSplittingRuleConfiguration readwriteSplittingRuleConfig1 = createReadwriteSplittingRuleConfig("ts_0");
        ReadwriteSplittingRuleConfiguration readwriteSplittingRuleConfig2 = createReadwriteSplittingRuleConfig("ts_1");
        List<RuleConfiguration> configurations = Arrays.asList(shardingRuleConfig,readwriteSplittingRuleConfig1,readwriteSplittingRuleConfig2);
        Properties properties = new Properties();
        properties.setProperty("sql-show", "true");//之前版本是sql.show
        DataSource dataSource = ShardingSphereDataSourceFactory.createDataSource(dataSourceMap, configurations, properties);
        return dataSource;
    }

    /**
     * 分片规则配置
     *
     * @return
     */
    private static ShardingRuleConfiguration createShardingRuleConfig() {
        //配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        ShardingTableRuleConfiguration orderTableRuleConfig = new ShardingTableRuleConfiguration("t_order", "ts_${0..1}.t_order${0..1}");

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
        return shardingRuleConfig;
    }

    /**
     * 创建数据源Map
     *
     * @return
     */
    private static Map<String, DataSource> createDataSourceMap() throws SQLException {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        //数据源配置
        DruidDataSource dataSource1 = new DruidDataSource();
        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://localhost:3306/ts_0?characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        dataSource1.setUsername("root");
        dataSource1.setPassword("123456");
        dataSourceMap.put("write_ts_0", dataSource1);
        dataSource1.setFilters("stat,wall,log4j");
        dataSource1.setInitialSize(5);
        dataSource1.setMinIdle(10);
        dataSource1.setMaxActive(20);
        dataSource1.setMaxWait(5000);


        DruidDataSource dataSource2 = new DruidDataSource();
        dataSource2.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource2.setUrl("jdbc:mysql://localhost:3306/ts_1?characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        dataSource2.setUsername("root");
        dataSource2.setPassword("123456");
        dataSourceMap.put("write_ts_1", dataSource2);

        dataSource2.setFilters("stat,wall,log4j");
        dataSource2.setInitialSize(5);
        dataSource2.setMinIdle(10);
        dataSource2.setMaxActive(20);
        dataSource2.setMaxWait(5000);

        DruidDataSource dataSource3 = new DruidDataSource();
        dataSource3.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource3.setUrl("jdbc:mysql://localhost:3307/ts_0?characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        dataSource3.setUsername("root");
        dataSource3.setPassword("123456");
        dataSourceMap.put("read_ts_0", dataSource3);

       dataSource3.setFilters("stat,wall,log4j");
       dataSource3.setInitialSize(5);
       dataSource3.setMinIdle(10);
       dataSource3.setMaxActive(20);
       dataSource3.setMaxWait(5000);

        DruidDataSource dataSource4 = new DruidDataSource();
        dataSource4.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource4.setUrl("jdbc:mysql://localhost:3307/ts_1?characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        dataSource4.setUsername("root");
        dataSource4.setPassword("123456");
        dataSourceMap.put("read_ts_1", dataSource4);

        dataSource4.setFilters("stat,wall,log4j");
        dataSource4.setInitialSize(5);
        dataSource4.setMinIdle(10);
        dataSource4.setMaxActive(20);
        dataSource4.setMaxWait(5000);

        return dataSourceMap;
    }

    private static Properties getKeyGeneratorsProperties() {
        Properties result = new Properties();
        result.setProperty("worker-id", "123");//工作进程id
        return result;
    }

    private static ReadwriteSplittingRuleConfiguration createReadwriteSplittingRuleConfig(String dbname) {
        String writeDataSourceName=String.format("write_%s",dbname);
        List<String> readSourceNames=Arrays.asList(String.format("read_%s",dbname));
        ReadwriteSplittingDataSourceRuleConfiguration readwriteSplittingDataSourceRuleConfig =
                new ReadwriteSplittingDataSourceRuleConfiguration(dbname, "",writeDataSourceName,readSourceNames,"random");
        Map<String, ShardingSphereAlgorithmConfiguration> loadBalancers= ImmutableMap.of("random", new ShardingSphereAlgorithmConfiguration("RANDOM", new Properties()));
        ReadwriteSplittingRuleConfiguration readwriteSplittingRuleConfig = new ReadwriteSplittingRuleConfiguration(Collections.singletonList(readwriteSplittingDataSourceRuleConfig), loadBalancers);
        return readwriteSplittingRuleConfig;
    }
}
