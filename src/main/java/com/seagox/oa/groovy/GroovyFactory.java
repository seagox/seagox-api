package com.seagox.oa.groovy;

import groovy.lang.GroovyClassLoader;
import org.quartz.Job;

import com.seagox.oa.groovy.IGroovyImportHandle;
import com.seagox.oa.groovy.IGroovyImportVerifyRule;

public class GroovyFactory {

    private static GroovyFactory groovyFactory = new GroovyFactory();

    private GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    public static GroovyFactory getInstance() {
        return groovyFactory;
    }

    /**
     * 根据脚本内容生成IGroovyPrint的实现
     *
     * @param code
     * @return
     * @throws Exception
     */
    public IGroovyPrint getIPrintFromCode(String code) throws Exception {
        Class<?> clazz = groovyClassLoader.parseClass(code);
        if (clazz != null) {
            Object instance = clazz.newInstance();
            if (instance != null) {
                if (instance instanceof IGroovyPrint) {
                    return (IGroovyPrint) instance;
                }
            }
        }
        groovyClassLoader.clearCache();
        throw new IllegalArgumentException("读取groovy脚本异常");
    }
    
    /**
     * 根据脚本内容生成IGroovyRule的实现
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
     * 根据脚本内容生成IGroovyDownload的实现
     *
     * @param code
     * @return
     * @throws Exception
     */
    public IGroovyDownload getIDownloadFromCode(String code) throws Exception {
        Class<?> clazz = groovyClassLoader.parseClass(code);
        if (clazz != null) {
            Object instance = clazz.newInstance();
            if (instance != null) {
                if (instance instanceof IGroovyDownload) {
                    return (IGroovyDownload) instance;
                }
            }
        }
        groovyClassLoader.clearCache();
        throw new IllegalArgumentException("读取groovy脚本异常");
    }
    
    /**
     * 根据脚本内容生成IGroovyImportVerifyRule的实现
     *
     * @param code
     * @return
     * @throws Exception
     */
    public IGroovyImportVerifyRule getIGroovyImportVerifyRuleFromCode(String code) throws Exception {
        Class<?> clazz = groovyClassLoader.parseClass(code);
        if (clazz != null) {
            Object instance = clazz.newInstance();
            if (instance != null) {
                if (instance instanceof IGroovyImportVerifyRule) {
                    return (IGroovyImportVerifyRule) instance;
                }
            }
        }
        groovyClassLoader.clearCache();
        throw new IllegalArgumentException("读取groovy脚本异常");
    }
    
    /**
     * 根据脚本内容生成IGroovyImportHandle的实现
     *
     * @param code
     * @return
     * @throws Exception
     */
    public IGroovyImportHandle getIImportHandleFromCode(String code) throws Exception {
        Class<?> clazz = groovyClassLoader.parseClass(code);
        if (clazz != null) {
            Object instance = clazz.newInstance();
            if (instance != null) {
                if (instance instanceof IGroovyImportHandle) {
                    return (IGroovyImportHandle) instance;
                }
            }
        }
        groovyClassLoader.clearCache();
        throw new IllegalArgumentException("读取groovy脚本异常");
    }

}
