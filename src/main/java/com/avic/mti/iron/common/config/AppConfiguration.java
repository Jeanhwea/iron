package com.avic.mti.iron.common.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 微服务的主要配置类
 *
 * @author Jinghui Hu
 * @since 2020-03-08, JDK1.8
 */
@Configuration
@RefreshScope
@EnableEurekaClient
@EnableCaching
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
public class AppConfiguration {}
