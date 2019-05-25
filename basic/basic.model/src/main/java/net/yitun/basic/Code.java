package net.yitun.basic;

/**
 * <pre>
 * <b>结果代码</b>
 * <b>Description:</b>
 *    用作API接口响应时反馈通用的业务执行结果代码。
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018-07-31 17:30:41.490
 * <b>Copyright:</b> Copyright ©2017 tb.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018-07-31 17:30:41.490 LH
 *         new file.
 * </pre>
 */
public class Code {

    /** 200 成功。 */
    public static final int OK = 200; // GET -- 幂等

    /** 400 （Bad Request）请求错误，例如：“JSON 不合法 ”。 */
    public static final int BAD_REQ = 400; // POST/PUT/PATCH -- 幂等

    /** 401 （Unauthorized）要求用户身份认证。 */
    public static final int UNAUTHED = 401; // * -- 幂等

    /** 403 （Forbidden）拒绝执行此请求（与401错误相对）。 */
    public static final int FORBIDDEN = 403; // * -- 幂等

    /** 404（Not Found）无法找到请求的资源。 */
    public static final int NOT_FOUND = 404; // * -- 幂等

    /** 500（Internal Server Error）服务器内部错误。 */
    public static final int SERVER_ERROR = 500; // *

    /** 503（Service Unavailable）超载限流或系统维护。 */
    public static final int SERVICE_UNAVAILABLE = 503;

    /** 550（Biz Error）业务异常 */
    public static final int BIZ_ERROR = 550; // * -- 幂等

    /** 570（API Error）接口异常 */
    public static final int API_ERROR = 570; // *

    /** 590（Unknown）不可预知的错误 */
    public static final int UNKNOWN = 590; // *

}
