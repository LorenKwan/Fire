package com.bamboo.controller;

import com.bamboo.helper.ConfigHelper;
import com.bamboo.model.User;
import com.bamboo.service.UserService;
import com.bamboo.util.CacheUtil;
import com.bamboo.util.CommonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Bob Guan on 2015/12/11.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public Object Login(HttpServletRequest request, HttpServletResponse response, @RequestParam String username, @RequestParam String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        out:
        if (!CommonUtil.isNullOrEmpty(username, password)) {
            User user = userService.getUserInfoByName(username);
            if (user == null) {
                map.put("code", 10010);
                map.put("message", "该用户不存在");
                break out;
            }
            if (!user.getPassword().equals(CommonUtil.Md5_16(password))) {
                map.put("code", 10010);
                map.put("message", "密码错误");
                break out;
            }
            map.put("code", 0);
            map.put("message", "登录成功");
            map.put("data", user);
            String ss = CommonUtil.serialize(user);
            HttpServletResponse res = response;
            HttpServletRequest req = request;
            //User user1=CommonUtil.deserialize(ss,(new User()).getClass());
            String fireKey = CommonUtil.Md5_16("fire");
            CommonUtil.setCookie(response, "fire", fireKey, 60 * 60 * 60);
            CacheUtil.getInstance().put(fireKey, CommonUtil.serialize(user));
           // CommonUtil.getLog().error("55");
        }
        return map;
    }
    /*
     * @param request
     * @param model
     * @return
     */

    //@RequestMapping(method=RequestMethod.POST, params = "method=upload")
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public String upload(HttpServletRequest request, ModelMap model) throws IOException {

        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/HH");
            /**构建图片保存的目录**/
            String logoPathDir = "/files" + dateFormat.format(new Date());
            /**得到图片保存目录的真实路径**/
            //String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);

            /**根据真实路径创建目录**/
        /*File logoSaveFile = new File(logoRealPathDir);
        if(!logoSaveFile.exists())
            logoSaveFile.mkdirs();*/
            /**页面控件的文件流**/
            MultipartFile multipartFile = multipartRequest.getFile("file");
            /**获取文件的后缀**/
            String suffix = multipartFile.getOriginalFilename().substring
                    (multipartFile.getOriginalFilename().lastIndexOf("."));
//          /**使用UUID生成文件名称**/
//         String logImageName = UUID.randomUUID().toString()+ suffix;//构建文件名称
            //String logImageName = multipartFile.getOriginalFilename();

            String picDir = ConfigHelper.uploadImgPath;
            // 新的图片文件名 = 获取时间戳+"."图片扩展名
            String newFileName = String.valueOf(System.currentTimeMillis())
                    + "." + suffix;

            /**拼成完整的文件保存路径加文件**/
            //String fileName = logoRealPathDir + File.separator   + logImageName;
            String fileName = picDir + File.separator + newFileName;
            //ImageUtil.gray(multipartFile.getInputStream(), fileName);
            //File file = new File(fileName);
            // multipartFile.transferTo(file);
            model.put("fileName", fileName);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return "index";
    }
}
