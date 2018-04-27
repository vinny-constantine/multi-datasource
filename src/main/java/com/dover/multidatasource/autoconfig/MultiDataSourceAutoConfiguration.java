package com.dover.multidatasource.autoconfig;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.dover.multidatasource.datasource.SwitchableDataSource;
import com.dover.multidatasource.annotation.SwitchDataSourceBeanPostProcessor;
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
            properties.put("driverClassName", environment.getRequiredProperty("dover.multi." + dataSourceName + ".driverClassName"));
            properties.put("url", environment.getRequiredProperty("dover.multi." + dataSourceName + ".url"));
            properties.put("username", environment.getRequiredProperty("dover.multi." + dataSourceName + ".username"));
            properties.put("password", environment.getRequiredProperty("dover.multi." + dataSourceName + ".password"));
            properties.put("initialSize", environment.getRequiredProperty("dover.multi." + dataSourceName + ".initialSize"));
            properties.put("maxActive", environment.getRequiredProperty("dover.multi." + dataSourceName + ".maxActive"));
            properties.put("minIdle", environment.getRequiredProperty("dover.multi." + dataSourceName + ".minIdle"));
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

