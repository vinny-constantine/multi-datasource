package com.dover.multidatasource.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * all available dataSourceNames
 *
 * @author Dover
 */
@ConfigurationProperties("dover.multi")
public class MultiDataSourceProperties {

    private List<String> dataSourceNames;

    public List<String> getDataSourceNames() {
        return dataSourceNames;
    }

    public void setDataSourceNames(List<String> dataSourceNames) {
        this.dataSourceNames = dataSourceNames;
    }
}
