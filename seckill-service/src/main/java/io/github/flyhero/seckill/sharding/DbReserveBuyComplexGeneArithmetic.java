package io.github.flyhero.seckill.sharding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import cn.hutool.core.collection.CollectionUtil;


import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

public class DbReserveBuyComplexGeneArithmetic implements ComplexKeysShardingAlgorithm<Long> {
    private static final String SHARDING_COUNT_KEY_NAME = "sharding-count";
    
    private static final String TABLE_SHARDING_COUNT_KEY_NAME = "table-sharding-count";
    
    private int shardingCount;
    
    private int tableShardingCount;
    
    @Override
    public void init(Properties props) {
        this.shardingCount = Integer.parseInt(props.getProperty(SHARDING_COUNT_KEY_NAME));
        this.tableShardingCount = Integer.parseInt(props.getProperty(TABLE_SHARDING_COUNT_KEY_NAME));
    }
    @Override
    public Collection<String> doSharding(Collection<String> allActualSplitDatabaseNames, ComplexKeysShardingValue<Long> complexKeysShardingValue) {
        List<String> actualDatabaseNames = new ArrayList<>(allActualSplitDatabaseNames.size());
        Map<String, Collection<Long>> columnNameAndShardingValuesMap = complexKeysShardingValue.getColumnNameAndShardingValuesMap();
        if (CollectionUtil.isEmpty(columnNameAndShardingValuesMap)) {
            return allActualSplitDatabaseNames;
        }
        
        Collection<Long> orderNumberValues = columnNameAndShardingValuesMap.get("order_number");
        Collection<Long> userIdValues = columnNameAndShardingValuesMap.get("user_id");
        
        Long value = null;
        if (CollectionUtil.isNotEmpty(orderNumberValues)) {
            value = orderNumberValues.stream().findFirst().orElseThrow(() -> new DaMaiFrameException(BaseCode.ORDER_NUMBER_NOT_EXIST));
        } else if (CollectionUtil.isNotEmpty(userIdValues)) {
            value = userIdValues.stream().findFirst().orElseThrow(() -> new DaMaiFrameException(BaseCode.USER_ID_NOT_EXIST));
        }
        if (Objects.nonNull(value)) {
            long databaseIndex = calculateDatabaseIndex(shardingCount,value,tableShardingCount);
            String databaseIndexStr = String.valueOf(databaseIndex);
            for (String actualSplitDatabaseName : allActualSplitDatabaseNames) {
                if (actualSplitDatabaseName.contains(databaseIndexStr)) {
                    actualDatabaseNames.add(actualSplitDatabaseName);
                    break;
                }
            }
            return actualDatabaseNames;
        }else {
            return allActualSplitDatabaseNames;
        }
    }
    
    /**
     * 计算给定表索引应分配到的数据库编号。
     *
     * @param databaseCount 数据库总数
     * @param splicingKey    分片键
     * @param tableCount    表总数
     * @return 分配到的数据库编号
     */
    public long calculateDatabaseIndex(Integer databaseCount, Long splicingKey, Integer tableCount) {
        String splicingKeyBinary = Long.toBinaryString(splicingKey);
        long replacementLength = log2N(tableCount);
        String geneBinaryStr = splicingKeyBinary.substring(splicingKeyBinary.length() - (int) replacementLength);
        
        if (StringUtil.isNotEmpty(geneBinaryStr)) {
            int h;
            int geneOptimizeHashCode = (h = geneBinaryStr.hashCode()) ^ (h >>> 16);
            return (databaseCount - 1) & geneOptimizeHashCode;
        }
        throw new DaMaiFrameException(BaseCode.NOT_FOUND_GENE);
    }
    
    public long log2N(long count) {
        return (long)(Math.log(count)/ Math.log(2));
    }
}
