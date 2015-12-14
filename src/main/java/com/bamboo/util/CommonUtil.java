package com.bamboo.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Bob Guan on 2015/12/11.
 */
public class CommonUtil {
    private static Log log;

    /* 检查字符串是否为空或者null,只要有一个参数符合即返回true*/
    public static boolean isNullOrEmpty(String... params) {
        for (String param : params) {
            if (param == null || "".equals(param)) {
                return true;
            }
        }
        return false;
    }

    /* 检查所有的参数字符串是否为空或者null,只有全部符合返回true*/
    public static boolean isAllNullOrEmpty(String... params) {
        for (String param : params) {
            if (param != null && !"".equals(param)) {
                return false;
            }
        }
        return true;
    }

    /* 检查字符串是否为null*/
    public static boolean isNull(String param) {
        if (null == param) {
            return true;
        }
        return false;
    }

    /* 检查字符串是否为空*/
    public static boolean isEmpty(String param) {
        if (param.equals("")) {
            return true;
        }
        return false;
    }

    /*MD5 16位加密*/
    public static String Md5_16(String plainText) {
        return _md5(plainText).substring(8, 24).toLowerCase();
    }

    /*MD5 32位加密*/
    public static String Md5_32(String plainText) {
        return _md5(plainText).toLowerCase();
    }

    private static String _md5(String plainText) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置cookie
     *
     * @param response
     * @param name     cookie名字
     * @param value    cookie值
     * @param maxAge   cookie生命周期  以秒为单位
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 根据名字获取cookie
     *
     * @param request
     * @param name    cookie名字
     * @return
     */
    public static String getCookie(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie.getValue();
        } else {
            return null;
        }
    }

    /**
     * 将cookie封装到Map里面
     *
     * @param request
     * @return
     */
    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 将对象序列化为JSON字符串
     *
     * @param object
     * @return JSON字符串
     */
    public static String serialize(Object object) {
        Writer write = new StringWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(write, object);
        } catch (JsonGenerationException e) {
            getLog().error("JsonGenerationException when serialize object to json", e);
        } catch (JsonMappingException e) {
            getLog().error("JsonMappingException when serialize object to json", e);
        } catch (IOException e) {
            getLog().error("IOException when serialize object to json", e);
        }
        return write.toString();
    }

    /**
     * 将JSON字符串反序列化为对象
     * <p>
     * //* @param object
     *
     * @return JSON字符串
     */
    public static <T> T deserialize(String json, Class<T> clazz) {
        Object object = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            object = objectMapper.readValue(json, TypeFactory.rawClass(clazz));
        } catch (JsonParseException e) {
            getLog().error("JsonParseException when serialize object to json", e);
        } catch (JsonMappingException e) {
            getLog().error("JsonMappingException when serialize object to json", e);
        } catch (IOException e) {
            getLog().error("IOException when serialize object to json", e);
        }
        return (T) object;
    }

    /*获取日志Log4j实例*/
    public static Log getLog() {
        if (null == log) {
            log = LogFactory.getLog(new Object() {
                //静态方法中获取当前类名 本文来自织梦
                public String getClassName() {
                    String className = this.getClass().getName();
                    return className.substring(0, className.lastIndexOf('$'));
                }
            }.getClassName());
        }
        return log;
    }
}


