package com.dover.multidatasource.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * switch to specified dataSource via dataSouceName
 *
 * @author Dover
 */
public class SwitchableDataSource extends AbstractRoutingDataSource {

    private static ThreadLocal<String> keyHolder = new ThreadLocal<>();

    public SwitchableDataSource(Map<Object, Object> switchableDataSources) {
        super.setTargetDataSources(switchableDataSources);
        super.setDefaultTargetDataSource(switchableDataSources.values().iterator().next());
    }

    public static void switching(String key) {
        keyHolder.set(key);
    }

    @Override
    protected Object determineCurrentLookupKey() {

        return keyHolder.get();
    }

}
