package com.sjdbc.demo.commonsharding.algorithm;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @Descirption 自定义分表算法
 * @Author lx
 * @Date 2021/10/29
 */
public class CustomShardingTableAlgorithm implements StandardShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        for(String each:availableTargetNames){
           /* if(each.endsWith(String.valueOf((shardingValue.getValue()-1)%6))){
                return each;
            }*/
            Long shardingTableValue=shardingValue.getValue();
            if(shardingTableValue%2==0){
                if(shardingTableValue%4==0){
                    if(each.endsWith("0")){
                        return each;
                    }
                }
                else{
                    if(each.endsWith("1")){
                        return each;
                    }
                }
            }
            else{
                if(shardingTableValue%4==1){
                    if(each.endsWith("0")){
                        return each;
                    }
                }
                else if(shardingTableValue%4==3){
                    if(each.endsWith("1")){
                        return each;
                    }
                }
            }
        }
        throw new UnsupportedOperationException("");
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        for (Long i = shardingValue.getValueRange().lowerEndpoint(); i <= shardingValue.getValueRange().upperEndpoint(); i++) {
            for (String each : availableTargetNames) {
                if (each.endsWith(String.valueOf(i % 2))) {
                    result.add(each);
                }
            }
        }
        return result;
    }

    @Override
    public void init() {
        System.out.println("自定义分表算法初始化");
    }

    @Override
    public String getType() {
        return "CUSTOM_SHARDING_TABLE";
    }
}
