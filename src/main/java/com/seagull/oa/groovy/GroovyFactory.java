package com.seagull.oa.groovy;

import groovy.lang.GroovyClassLoader;
import org.quartz.Job;

public class GroovyFactory {

    private static GroovyFactory groovyFactory = new GroovyFactory();

    private GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    public static GroovyFactory getInstance() {
        return groovyFactory;
    }

    /**
     * 根据脚本内容生成IRule的实现
     *
     * @param code
     * @return
     * @throws Exception
     */
    public IGroovyRule getIRuleFromCode(String code) throws Exception {
        Class<?> clazz = groovyClassLoader.parseClass(code);
        if (clazz != null) {
            Object instance = clazz.newInstance();
            if (instance != null) {
                if (instance instanceof IGroovyRule) {
                    return (IGroovyRule) instance;
                }
            }
        }
        groovyClassLoader.clearCache();
        throw new IllegalArgumentException("读取groovy脚本异常");
    }

    /**
     * 根据脚本内容生成Job的实现
     *
     * @param code
     * @return
     * @throws Exception
     */
    public Job getIJobFromCode(String code) throws Exception {
        Class<?> clazz = groovyClassLoader.parseClass(code);
        if (clazz != null) {
            Object instance = clazz.newInstance();
            if (instance != null) {
                if (instance instanceof Job) {
                    return (Job) instance;
                }
            }
        }
        groovyClassLoader.clearCache();
        throw new IllegalArgumentException("读取groovy脚本异常");
    }

    /**
     * 根据脚本内容生成ICloud的实现
     *
     * @param code
     * @return
     * @throws Exception
     */
    public IGroovyCloud getICloudFromCode(String code) throws Exception {
        Class<?> clazz = groovyClassLoader.parseClass(code);
        if (clazz != null) {
            Object instance = clazz.newInstance();
            if (instance != null) {
                if (instance instanceof IGroovyCloud) {
                    return (IGroovyCloud) instance;
                }
            }
        }
        groovyClassLoader.clearCache();
        throw new IllegalArgumentException("读取groovy脚本异常");
    }

    /**
     * 根据脚本内容生成IFlow的实现
     *
     * @param code
     * @return
     * @throws Exception
     */
    public IGroovyFlow getIFlowFromCode(String code) throws Exception {
        Class<?> clazz = groovyClassLoader.parseClass(code);
        if (clazz != null) {
            Object instance = clazz.newInstance();
            if (instance != null) {
                if (instance instanceof IGroovyFlow) {
                    return (IGroovyFlow) instance;
                }
            }
        }
        groovyClassLoader.clearCache();
        throw new IllegalArgumentException("读取groovy脚本异常");
    }


    /**
     * 根据脚本内容生成ICloud的实现
     *
     * @param code
     * @return
     * @throws Exception
     */
    public IGroovyUpload getIUploadFromCode(String code) throws Exception {
        Class<?> clazz = groovyClassLoader.parseClass(code);
        if (clazz != null) {
            Object instance = clazz.newInstance();
            if (instance != null) {
                if (instance instanceof IGroovyUpload) {
                    return (IGroovyUpload) instance;
                }
            }
        }
        groovyClassLoader.clearCache();
        throw new IllegalArgumentException("读取groovy脚本异常");
    }

}
