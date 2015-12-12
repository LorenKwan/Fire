package com.bamboo.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Bob Guan on 2015/12/12.
 */
public class CacheUtil {

    /**
     * 缓存Map
     */
    private static Map<String, String> cacheMap;

    private CacheUtil() {
        if (cacheMap == null) {

            cacheMap = new ConcurrentHashMap<String, String>();
        }
    }

    private static class CacheUtilHandler {
        private static CacheUtil instance = new CacheUtil();
    }

    public static CacheUtil getInstance() {
        return CacheUtilHandler.instance;
    }

    /*设置缓存*/
    public void put(String key, String value) {
        cacheMap.put(key, value);
    }

    /*读取缓存*/
    public String get(String key) {
        return cacheMap.get(key);
    }
}
