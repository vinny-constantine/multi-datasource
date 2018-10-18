package com.dover.multidatasource.autoconfig;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.dover.multidatasource.datasource.SwitchableDataSource;
import com.dover.multidatasource.annotation.SwitchDataSourceBeanPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * auto register mandatory bean
 *
 * @author Dover
 */
@Configuration
@ConditionalOnClass(DataSource.class)
@EnableConfigurationProperties(MultiDataSourceProperties.class)
public class MultiDataSourceAutoConfiguration {
    private Logger logger = LoggerFactory.getLogger(MultiDataSourceAutoConfiguration.class);

    @Bean
    public static BeanPostProcessor switchDataSourceBeanPostProcessor() {
        return new SwitchDataSourceBeanPostProcessor();
    }

    @Bean
    @Primary
    public Map<Object, Object> switchableDataSources(MultiDataSourceProperties multiDataSourceProperties) {
        HashMap<Object, Object> dataSourceMap = new HashMap<>();
        List<MultiDataSourceProperties.DataSourceProperties> dataSourcePropertiesList = multiDataSourceProperties.getDataSourcePropertiesList();
        dataSourcePropertiesList.forEach(dataSourceProperties -> {
            Properties properties = new Properties();
            properties.put("driverClassName", dataSourceProperties.getDriverClassName());
            properties.put("url", dataSourceProperties.getUrl());
            properties.put("username", dataSourceProperties.getUsername());
            properties.put("password", dataSourceProperties.getPassword());
            properties.put("initialSize", dataSourceProperties.getInitialSize());
            properties.put("maxActive", dataSourceProperties.getMaxActive());
            properties.put("minIdle", dataSourceProperties.getMinIdle());
            try {
                dataSourceMap.put(dataSourceProperties.getDataSourceName(), DruidDataSourceFactory.createDataSource(properties));
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("init druid datasource exception", e);
                }
            }
        });
        return dataSourceMap;
    }

    @Bean
    @Primary
    public DataSource dataSource(Map<Object, Object> switchableDataSources) {
        return new SwitchableDataSource(switchableDataSources);
    }

}

