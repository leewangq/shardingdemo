package com.sjdbc.demo.commonsharding.algorithm;

import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingValue;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Descirption 使用SPI，实现自定义Hint分片算法
 * @Author lx
 * @Date 2021.10.26
 */
public final class CustomHintShardingAlgorithm implements HintShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> collection, HintShardingValue<Long> hintShardingValue) {
        Collection<String> result = new ArrayList<>();
        for (String each : collection) {
            for (Long value : hintShardingValue.getValues()) {
                if (each.endsWith(String.valueOf(value % 2))) {
                    result.add(each);
                }
            }
        }
        return result;
    }

    @Override
    public void init() {

    }

    @Override
    public String getType() {
        return "HINT_TEST";
    }
}
