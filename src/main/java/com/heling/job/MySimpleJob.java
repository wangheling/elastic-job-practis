package com.heling.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author heling
 * @desc
 * @date 2019/12/14 16:28
 */
@Component
@Slf4j
public class MySimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {

        int shardingItem = shardingContext.getShardingItem();

        switch (shardingItem) {
            case 0:
                log.info(shardingContext.getJobName() + "处理数据select * from xx where mod(id,2) = 0");
                break;
            case 1:
                log.info(shardingContext.getJobName() + "处理数据select * from xx where mod(id,2) = 1");
                break;
        }
    }
}
