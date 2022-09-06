package com.fastcampus.MyWeb1.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DataBaseConfig.class})
@ComponentScan(basePackages = {"com.fastcampus.MyWeb1"})
public class RootConfig {
}
