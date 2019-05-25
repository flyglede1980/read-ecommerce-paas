package net.yitun.basic.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <b>Web 工具</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-15 10:34:16.508
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2017-09-15 10:34:16.508 LH
 *         new file.
 * </pre>
 */
@Slf4j
public abstract class WebUtil {

    /**
     * 受保护的构造方法, 防止外部构建对象实例.
     */
    protected WebUtil() {
        super();
    }

    /**
     * 获取终端IP
     * 
     * @param request
     * @return String
     */
    public static String getIp(HttpServletRequest request) {
        if (null == request)
            return null;

        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unKnown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");

            if (StringUtils.isEmpty(ip) || "unKnown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");

                if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
                    int index = -1;
                    // 经过多层代理后会有多个代理，取第一个ip地址
                    if (-1 == (index = ip.indexOf(",")))
                        return ip;

                    return ip.substring(0, index);
                }
            }
        }

        ip = request.getHeader("HTTP_CLIENT_IP");
        if (StringUtils.isEmpty(ip) || "unKnown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");

            if (StringUtils.isEmpty(ip) || "unKnown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");

                if (StringUtils.isEmpty(ip) || "unKnown".equalsIgnoreCase(ip))
                    ip = request.getRemoteAddr();
            }
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 获取请求参数
     * 
     * @param request
     * @param defaultValue 默认值
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAttr(HttpServletRequest request, String key, T defaultValue) {
        Object value = request.getAttribute(key);

        return null != value ? (T) value : defaultValue;
    }

    /**
     * 代开Cors跨域支持
     * 
     * @param origin   允许跨域来源地址
     * @param response HttpServletResponse
     */
    public static void corsOn(String origin, HttpServletResponse response) {
        response.setHeader("Access-Control-Max-Age", "300"); // 5分钟

        if (null != origin)
            // 必须。要么是请求时Origin值(发起Cors请求的域名)，要么是一个*，表示服务器接受任意域名的请求。
            response.setHeader("Access-Control-Allow-Origin", origin);

        // 可选。它的值是一个布尔值，表示是否允许发送Cookie。默认情况下，Cookie不包括在CORS请求之中。设为true，即表示服务器明确许可，Cookie可以包含在请求中，一起发给服务器。这个值也只能设为true，如果服务器不要浏览器发送Cookie，删除该字段即可。
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // 可选。CORS请求时，XMLHttpRequest对象的getResponseHeader()方法只能拿到6个基本字段：Cache-Control、Content-Language、Content-Type、Expires、Last-Modified、Pragma。如果想拿到其他字段，就必须在Access-Control-Expose-Headers里面指定。上面的例子指定，getResponseHeader('FooBar')可以返回FooBar字段的值。
        response.setHeader("Access-Control-Expose-Headers", "<Cors Token>"); // 主要携带特殊Response中Header的信息

        response.setHeader("Access-Control-Allow-Headers", "Origin,Accept,Content-Type,X-Requested-With");

        response.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE");

        // 参考 http://www.ruanyifeng.com/blog/2016/04/cors.html
    }

    /**
     * 添加Cookie
     * 
     * @param name     名字
     * @param value    参数
     * @param expiry   过期时间
     * @param response HttpServletResponse
     */
    public static void addCookie(String name, String value, Integer expiry, HttpServletResponse response) {
        addCookie(name, value, "/", null, expiry, response);
    }

    /**
     * 添加Cookie
     * 
     * @param name     名字
     * @param value    参数
     * @param path     路径
     * @param domain   域名
     * @param expiry   过期时间
     * @param response HttpServletResponse
     */
    public static void addCookie(String name, String value, String path, String domain, Integer expiry,
            HttpServletResponse response) {
        Cookie cookie;
        response.addCookie(cookie = new Cookie(name, value));

        if (null != path)
            cookie.setPath(path);

        if (null != domain)
            cookie.setDomain(domain);

        if (null != expiry)
            cookie.setMaxAge(expiry);
    }

    /**
     * 响应输出提示错误码
     * 
     * @param code     错误码
     * @param msg      错误消息
     * @param response HTTP响应
     * @return boolean 设置是否成功
     */
    public static Object tipError(int code, String msg, HttpServletResponse response) {
        try {
            response.sendError(code, msg);
        } catch (IOException e) {
            log.error("<<< WebUtil.tipError: " + e.getMessage(), e.toString());
            return null;
        }

        return null;
    }

    /**
     * 往终端端输出JSON数据
     * 
     * @param data     响应输出的数据
     * @param response HttpServletResponse
     */
    public static void outJson(Object data, HttpServletResponse response) {
        outJson(JsonUtil.toJson(data), response);
    }

    /**
     * 往终端端输出JSON数据
     * 
     * @param json     响应输出的JSON数据
     * @param response HttpServletResponse
     */
    public static void outJson(String json, HttpServletResponse response) {
        outJson(200, json, response);
    }

    /**
     * 往终端端输出JSON数据
     * 
     * @param code     HTTP响应状态码
     * @param data     响应输出的数据
     * @param response HttpServletResponse
     */
    public static void outJson(int code, Object data, HttpServletResponse response) {
        outJson(code, JsonUtil.toJson(data), response);
    }

    /**
     * 往终端端输出JSON数据
     * 
     * @param code     HTTP响应状态码
     * @param json     响应输出的JSON数据
     * @param response HttpServletResponse
     */
    public static void outJson(int code, String json, HttpServletResponse response) {
        response.setStatus(code);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            log.error("WebUtil.outJson failed, " + e.toString(), e);

        } finally {
            try {
                writer.flush();
            } catch (Exception e) {

            } finally {
                try {
                    writer.close();
                } catch (Throwable e) {
                }
            }
        }
    }

    /**
     * 输出下载文件
     * 
     * @param filePath 全路径
     * @param fileName 下载保存的名称
     * @param isOnline 是否在线打开下载文件
     * @param response
     */
    public static void down(String filePath, String fileName, boolean isOnline, HttpServletResponse response) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            String error = "down file:" + fileName + " unexists";
            response.sendError(404, error);
            return;
        }

        response.reset(); // 清空response
        if (isOnline) { // 在线打开方式
            try {
                URL url = new URL("file:///" + filePath);
                response.setContentType(url.openConnection().getContentType());
            } catch (Exception e) {
            }
            response.setHeader("Content-Disposition", "inline; filename=" + fileName); // 文件名应该编码成UTF-8

        } else { // 纯下载方式
            // 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("application/x-msdownload");
            // 2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
            response.setHeader("Content-Disposition", "attachment; fileName=" + fileName);
        }

        int len = 0;
        byte[] buffer = new byte[1024];

        BufferedInputStream in = null;
        ServletOutputStream out = null;

        try {
            // 3.通过response获取ServletOutputStream对象(out)
            out = response.getOutputStream();
            in = new BufferedInputStream(new FileInputStream(file));

            while ((len = in.read(buffer)) != -1)
                out.write(buffer, 0, len);

        } finally {
            try {
                out.flush();
            } catch (Exception e) {

            } finally {
                try {
                    out.close();
                } catch (Throwable e) {
                }
            }

            try {
                in.close();
            } catch (Exception e) {

            }
        }
    }

}
