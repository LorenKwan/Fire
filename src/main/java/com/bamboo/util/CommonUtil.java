package com.bamboo.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bob Guan on 2015/12/11.
 */
public class CommonUtil {

    /* 检查字符串是否为空或者null*/
    public static boolean isNullOrEmpty(String... params) {
        for (String param : params) {
            if (param == null && "".equals(param)) {
                return true;
            }
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
     * @param response
     * @param name  cookie名字
     * @param value cookie值
     * @param maxAge cookie生命周期  以秒为单位
     */
    public static void setCookie(HttpServletResponse response,String name,String value,int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        if(maxAge>0)  cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     */
    public static String getCookie(HttpServletRequest request, String name){
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie.getValue();
        }else{
            return null;
        }
    }
    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
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
            //logger.error("JsonGenerationException when serialize object to json", e);
        } catch (JsonMappingException e) {
            //logger.error("JsonMappingException when serialize object to json", e);
        } catch (IOException e) {
            //logger.error("IOException when serialize object to json", e);
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
            // logger.error("JsonParseException when serialize object to json", e);
        } catch (JsonMappingException e) {
            // logger.error("JsonMappingException when serialize object to json", e);
        } catch (IOException e) {
            //logger.error("IOException when serialize object to json", e);
        }
        return (T) object;
    }
}


