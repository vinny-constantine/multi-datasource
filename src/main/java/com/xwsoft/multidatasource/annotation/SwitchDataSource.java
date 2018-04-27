package com.xwsoft.multidatasource.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * for switching dataSource
 *
 * @Author Dover
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface SwitchDataSource {

    /**
     * 指定dataSource的name
     *
     * @return 数据源name
     */
    @AliasFor("name")
    String value() default "dataSource";

    @AliasFor("value")
    String name() default "dataSource";


}
