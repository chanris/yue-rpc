package com.chenyue.yuerpc.spi;

import cn.hutool.core.io.resource.ResourceUtil;
import com.chenyue.yuerpc.serializer.JdkSerializer;
import com.chenyue.yuerpc.serializer.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenyue7@foxmail.com
 * @date 14/5/2024
 * @description 自定义SPI
 */
@Slf4j
public class SpiLoader {
    /**
     * 存储已加载的类：接口名 => (key => 实现类)
     */
    private static final Map<String, Map<String, Class<?>>> loaderMap = new ConcurrentHashMap<>();

    /**
     * 对象实例缓存（避免重复 new）,类路径 => 对象实例，单例模式
     */
    private static final Map<String, Object> instanceCache = new ConcurrentHashMap<>();

    /**
     * 系统 SPI 目录
     */
    private static final String RPC_SYSTEM_SPI_DIR = "META-INF/rpc/system/";

    /**
     * 用户自定义 SPI 目录
     */
    private static final String RPC_CUSTOM_SPI_DIR = "META-INF/rpc/custom/";

    /**
     * 扫描路径
     */
    private static final String[] SCAN_DIRS = new String[]{RPC_SYSTEM_SPI_DIR, RPC_CUSTOM_SPI_DIR};

    /**
     * 动态加载的类列表
     */
    private static final List<Class<?>> LOAD_CLASS_LIST = Arrays.asList(Serializer.class);

    /**
     * 加载所有类型
     */
    public static void loadAll() {
        log.info("加载所有 SPI");
        for (Class<?> aClass : LOAD_CLASS_LIST) {
            load(aClass);
        }
    }

    public static <T> T getInstance(Class<?> tClass, String key) {
        String tClassName = tClass.getName();
        Map<String, Class<?>> keyClassMap = loaderMap.get(tClassName);
        if (keyClassMap == null) {
            throw new RuntimeException(String.format("SpiLoader 未加载 %s 类型", tClassName));
        }
        if (!keyClassMap.containsKey(key)) {
            throw new RuntimeException(String.format("SpiLoader 的 %s 不存在 key=%s 的类型", tClassName, key));
        }

        //获得需要加载的实现类型
        Class<?> implClass = keyClassMap.get(key);
        String implClassName = implClass.getName();
        if(!instanceCache.containsKey(implClassName)) {
            try {
                // 如果实例缓存没有包含这个Class对象的实例，那么通过Class对象创建一个实例，放入缓存
                instanceCache.put(implClassName, implClass.newInstance());
            }catch (InstantiationException | IllegalAccessException e) {
                String errorMsg = String.format("%s 类实例化失败", implClassName);
                throw new RuntimeException(errorMsg, e);
            }
        }
        return (T) instanceCache.get(implClassName);
    }

    /**
     * 加载某个类型
     * @param loadClass
     * @return
     */
    public static Map<String, Class<?>> load(Class<?> loadClass) {
        log.info("加载类型为 {} 的 SPI", loadClass.getName());
        // 扫描路径，用户自定义的 SPI 有限级高于系统 SPI
        Map<String, Class<?>> keyClassMap = new HashMap<>();
        for (String scanDir : SCAN_DIRS) {
            List<URL> resources = ResourceUtil.getResources(scanDir + loadClass.getName());
            // 读取每个资源文件
            for (URL resource: resources) {
                try {
                    // 字节流 转换为 字符流
                    InputStreamReader inputStreamReader = new InputStreamReader(resource.openStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] strArray = line.split("="); // 形如key=value; jdk=com.chenyue.yuerpc.serializer.JdkSerializer
                        if (strArray.length > 1) {
                            String key = strArray[0];
                            String className = strArray[1];
                            // 动态加载并获得 指定类的Class对象
                            keyClassMap.put(key, Class.forName(className));
                        }
                    }
                } catch (Exception e) {
                    log.error("spi resource load error", e);
                }
            }
        }
        loaderMap.put(loadClass.getName(), keyClassMap);
        return keyClassMap;
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        SpiLoader.loadAll();
        for (Map.Entry<String, Map<String, Class<?>>> entry : SpiLoader.loaderMap.entrySet()) {
            System.out.println("接口类：" + entry.getKey());
            System.out.print("实现类：");
            for (Map.Entry<String, Class<?>> valEntr : entry.getValue().entrySet()) {
                System.out.println(valEntr.getKey() + "=" + valEntr.getValue().getName());
            }
        }

        Map<String, Class<?>> stringClassMap = loaderMap.get(Serializer.class.getName());
        Class<?> jdk = stringClassMap.get("jdk");
        System.out.println(jdk.getName());
        Constructor<?> constructor = jdk.getConstructor(); // 获得对象的无参构造器
        JdkSerializer jdkSerializer = (JdkSerializer) constructor.newInstance();
        System.out.println(jdkSerializer != null);
    }
}
