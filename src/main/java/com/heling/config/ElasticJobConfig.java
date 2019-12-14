package com.heling.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.heling.job.MySimpleJob;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author heling
 * @desc job配置
 * @date 2019/12/14 16:16
 */
@Configuration
public class ElasticJobConfig {

    @Resource
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    @Resource
    private BasicDataSource elasticJobDataSource;


    // 定义调度器
    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final MySimpleJob simpleJob,
                                           @Value("${heling.simpleJob.cron}") final String cron,
                                           @Value("${heling.simpleJob.shardingTotalCount}") final int shardingTotalCount,
                                           @Value("${heling.simpleJob.shardingItemParameters}") final String shardingItemParameters,
                                           @Value("${heling.simpleJob.jobParameter}") final String jobParameter) {
        return new SpringJobScheduler(simpleJob, zookeeperRegistryCenter,
                getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters, jobParameter),
                new JobEventRdbConfiguration(elasticJobDataSource));
    }

    // 配置任务详细信息
    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass,
                                                         final String cron,
                                                         final int shardingTotalCount,
                                                         final String shardingItemParameters,
                                                         final String jobParameter) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.
                        newBuilder(jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).
                        jobParameter(jobParameter).build(), jobClass.getCanonicalName())).
                overwrite(true).build();
    }


}
