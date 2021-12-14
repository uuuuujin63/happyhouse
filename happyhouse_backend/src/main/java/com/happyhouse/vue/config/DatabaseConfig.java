package com.happyhouse.vue.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.happyhouse.vue.model.mapper")
public class DatabaseConfig {

}
