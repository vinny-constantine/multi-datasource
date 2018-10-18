package com.dover.multidatasource.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


/**
 * all available dataSourceProperties
 */
@ConfigurationProperties("spring.datasource.multi")
public class MultiDataSourceProperties {

    private List<DataSourceProperties> dataSourcePropertiesList;


    public List<DataSourceProperties> getDataSourcePropertiesList() {
        return dataSourcePropertiesList;
    }

    public void setDataSourcePropertiesList(List<DataSourceProperties> dataSourcePropertiesList) {
        this.dataSourcePropertiesList = dataSourcePropertiesList;
    }

    public static class DataSourceProperties extends org.springframework.boot.autoconfigure.jdbc.DataSourceProperties {
        /**
         * 数据源名称，非空且唯一，用于@SwitchDataSource(dataSourceName)
         */
        private String dataSourceName;
        /**
         * 初始化连接池大小
         */
        private String initialSize = "5";
        /**
         * 最大激活连接数
         */
        private String maxActive = "10";
        /**
         * 最小空闲连接数
         */
        private String minIdle = "1";

        public String getDataSourceName() {
            return dataSourceName;
        }

        public void setDataSourceName(String dataSourceName) {
            this.dataSourceName = dataSourceName;
        }

        public String getInitialSize() {
            return initialSize;
        }

        public void setInitialSize(String initialSize) {
            this.initialSize = initialSize;
        }

        public String getMaxActive() {
            return maxActive;
        }

        public void setMaxActive(String maxActive) {
            this.maxActive = maxActive;
        }

        public String getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(String minIdle) {
            this.minIdle = minIdle;
        }
    }
}
