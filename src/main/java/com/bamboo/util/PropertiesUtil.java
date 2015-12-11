package com.bamboo.util;

/**
 * Created by Bob Guan on 2015/12/11.
 */

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取Properties综合类
 * @author lijn
 * 2014-02-25
 */
public class PropertiesUtil {
    /**
     * 得到某一个类的路径
     *
     * @param name
     * @return
     */
    public static String getPath(Class name) {
        String strResult = null;
        if (System.getProperty("os.name").toLowerCase().indexOf("window") > -1) {
            strResult = name.getResource("/").toString().replace("file:/", "")
                    .replace("%20", " ");
        } else {
            strResult = name.getResource("/").toString().replace("file:", "")
                    .replace("%20", " ");
        }
        return strResult;
    }

    /**
     * 读取所有的property
     *
     * @param filename properties文件路径
     * @return 所有的property的集合(map形式)
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> getProperties(String filename) {
        if (null == filename) {
            return null;
        }
        String filePath = getPath(PropertiesUtil.class) + filename;
        Properties props = new Properties();
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
            Map<String, String> map = new HashMap<String, String>();
            Enumeration en = props.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                String Property = props.getProperty(key);
                map.put(key, Property);
            }
            return map;
            // 关闭资源
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取某个property的值
     *
     * @param filename 文件名
     * @param key      property的key
     * @return property的value
     */
    public static String getProp(String filename, String key) {
        if (null == filename || null == key) {
            return null;
        }
        String filePath = getPath(PropertiesUtil.class) + filename;
        Properties props = new Properties();
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
            return props.getProperty(key);
            // 关闭资源
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
}