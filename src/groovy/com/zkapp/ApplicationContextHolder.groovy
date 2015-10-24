package com.zkapp

import grails.util.Holders
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.plugins.GrailsPluginManager
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

import javax.servlet.ServletContext

@Singleton
class ApplicationContextHolder implements ApplicationContextAware {

    private ApplicationContext ctx
    private static final Map<String, Object> TEST_BEANS = [:]

    void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext
    }

    ApplicationContext getApplicationContext() {
        if (!getInstance().ctx) {
            getInstance().ctx = Holders.applicationContext
        }
        getInstance().ctx
    }

    Object getBean(String name) {
        TEST_BEANS[name] ?: getApplicationContext().getBean(name)
    }

    GrailsApplication getGrailsApplication() {
        getBean('grailsApplication') as GrailsApplication
    }

    ConfigObject getConfig() {
        getGrailsApplication().config
    }

    ServletContext getServletContext() {
        getBean('servletContext') as ServletContext
    }

    GrailsPluginManager getPluginManager() {
        getBean('pluginManager') as GrailsPluginManager
    }

    public static Object resolveBean(String name) {
        getInstance().getBean(name)
    }

    static void registerTestBean(String name, bean) {
        TEST_BEANS[name] = bean
    }

    static void unregisterTestBeans() {
        TEST_BEANS.clear()
    }
}
