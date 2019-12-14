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

        /**
         * 启动两台服务，id被2整除的的执行case 0，id被2不能整除的执行case 1，如果停止一台，则任务全部在另一台上执行，失效转移
         */
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
