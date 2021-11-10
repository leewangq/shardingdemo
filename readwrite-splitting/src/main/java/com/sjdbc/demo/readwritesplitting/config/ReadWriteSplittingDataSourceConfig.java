package com.sjdbc.demo.readwritesplitting.config;

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
 * @Descirption
 * @Author lx
 * @Date 2021/11/5
 */
@Component
public class ReadWriteSplittingDataSourceConfig {
    @Bean
    public DataSource createDataSource() throws SQLException {

        Map<String, DataSource> dataSourceMap = createDataSourceMap();
        //ShardingRuleConfiguration shardingRuleConfig = createShardingRuleConfig();
        ReadwriteSplittingRuleConfiguration readwriteSplittingRuleConfig = createReadwriteSplittingRuleConfig();
        List<RuleConfiguration> configurations = Arrays.asList(readwriteSplittingRuleConfig);
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

        ShardingTableRuleConfiguration orderTableRuleConfig = new ShardingTableRuleConfiguration("t_order", "t_order");

        // 分库策略
        //orderTableRuleConfig.setDatabaseShardingStrategy(new StandardShardingStrategyConfiguration("user_id", "dbShardingAlgorithm"));

        // 分表策略
        //orderTableRuleConfig.setTableShardingStrategy(new StandardShardingStrategyConfiguration("user_id", "tableShardingAlgorithm"));

        /*// hint强制路由分库策略
        orderTableRuleConfig.setDatabaseShardingStrategy(new HintShardingStrategyConfiguration("hint_test"));

        // hint强制路由分表策略
        orderTableRuleConfig.setTableShardingStrategy(new HintShardingStrategyConfiguration("hint_test"));*/

        orderTableRuleConfig.setKeyGenerateStrategy(new KeyGenerateStrategyConfiguration("id", "snowflake"));


        //shardingRuleConfig.getTables().add(orderTableRuleConfig);

        //数据库分片逻辑
       /* Properties dbShardingAlgorithmProps = new Properties();
        dbShardingAlgorithmProps.setProperty("algorithm-expression", "ds_${user_id % 2}");
        shardingRuleConfig.getShardingAlgorithms().put("dbShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", dbShardingAlgorithmProps));

        //表分片逻辑
        Properties tableShardingAlgorithmProps = new Properties();
        tableShardingAlgorithmProps.setProperty("algorithm-expression", "t_order${user_id % 2}");
        shardingRuleConfig.getShardingAlgorithms().put("tableShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", tableShardingAlgorithmProps));*/

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
    private static Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        //数据源配置
        DruidDataSource dataSource1 = new DruidDataSource();
        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://localhost:3306/write_0?characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        dataSource1.setUsername("root");
        dataSource1.setPassword("123456");
        dataSourceMap.put("write_0", dataSource1);

        DruidDataSource dataSource2 = new DruidDataSource();
        dataSource2.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource2.setUrl("jdbc:mysql://localhost:3306/read_0?characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        dataSource2.setUsername("root");
        dataSource2.setPassword("123456");
        dataSourceMap.put("read_0", dataSource2);

        DruidDataSource dataSource3 = new DruidDataSource();
        dataSource3.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource3.setUrl("jdbc:mysql://localhost:3306/read_1?characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        dataSource3.setUsername("root");
        dataSource3.setPassword("123456");
        dataSourceMap.put("read_1", dataSource3);

        return dataSourceMap;
    }

    private static Properties getKeyGeneratorsProperties() {
        Properties result = new Properties();
        result.setProperty("worker-id", "123");//工作进程id
        return result;
    }

    //读写分离目前只支持一主多从
    private static ReadwriteSplittingRuleConfiguration createReadwriteSplittingRuleConfig() {
        ReadwriteSplittingDataSourceRuleConfiguration readwriteSplittingDataSourceRuleConfig =
                new ReadwriteSplittingDataSourceRuleConfiguration("readwritedb", "","write_0",Arrays.asList("read_0","read_1"),"random");
        Map<String, ShardingSphereAlgorithmConfiguration> loadBalancers= ImmutableMap.of("random", new ShardingSphereAlgorithmConfiguration("RANDOM", new Properties()));
        ReadwriteSplittingRuleConfiguration readwriteSplittingRuleConfig = new ReadwriteSplittingRuleConfiguration(Collections.singletonList(readwriteSplittingDataSourceRuleConfig), loadBalancers);
        return readwriteSplittingRuleConfig;
    }
}
