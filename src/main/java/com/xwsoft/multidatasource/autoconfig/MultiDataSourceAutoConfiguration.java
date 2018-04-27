package com.xwsoft.multidatasource.autoconfig;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.xwsoft.multidatasource.annotation.SwitchDataSourceBeanPostProcessor;
import com.xwsoft.multidatasource.datasource.SwitchableDataSource;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
 */
@Configuration
@ConditionalOnClass(DataSource.class)
@EnableConfigurationProperties(MultiDataSourceProperties.class)
public class MultiDataSourceAutoConfiguration {


    @Bean
    public static BeanPostProcessor switchDataSourceBeanPostProcessor() {
        return new SwitchDataSourceBeanPostProcessor();
    }

    @Bean
    @Primary
    public Map<Object, Object> switchableDataSources(Environment environment, MultiDataSourceProperties multiDataSourceProperties) throws Exception {
        HashMap<Object, Object> dataSourceMap = new HashMap<>();
        Properties properties;
        List<String> dataSourceNames = multiDataSourceProperties.getDataSourceNames();
        for (String dataSourceName : dataSourceNames) {
            properties = new Properties();
            properties.put("driverClassName", environment.getRequiredProperty("xwsoft.multi." + dataSourceName + ".driverClassName"));
            properties.put("url", environment.getRequiredProperty("xwsoft.multi." + dataSourceName + ".url"));
            properties.put("username", environment.getRequiredProperty("xwsoft.multi." + dataSourceName + ".username"));
            properties.put("password", environment.getRequiredProperty("xwsoft.multi." + dataSourceName + ".password"));
            properties.put("initialSize", "5");
            properties.put("maxActive", "10");
            properties.put("minIdle", "1");
            dataSourceMap.put(dataSourceName, DruidDataSourceFactory.createDataSource(properties));
        }
        return dataSourceMap;
    }

    @Bean
    @Primary
    public DataSource dataSource(Map<Object, Object> switchableDataSources) {
        return new SwitchableDataSource(switchableDataSources);
    }

}

