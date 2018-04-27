package com.dover.multidatasource.annotation;

import com.dover.multidatasource.datasource.SwitchableDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * process class annotated by @SwitchDataSource, then generate their proxy that switch dataSource via dataSourceName
 *
 * @author Dover
 */
public class SwitchDataSourceBeanPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(SwitchDataSource.class)) {
            SwitchDataSource annotation = beanClass.getAnnotation(SwitchDataSource.class);
            final String dataSourceName = annotation.value();
            return Proxy.newProxyInstance(this.getClass().getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    SwitchableDataSource.switching(dataSourceName);
                    return method.invoke(bean, args);
                }
            });
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
