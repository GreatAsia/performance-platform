package com.okay.testcenter.common.constant;


/**
 * @author XieYangYang
 * @date 2019/11/20 16:21
 */
public enum ReturnCode {

    /**
     * 请求成功
     */
    SUCCESS(1000, "请求成功"),
    /**
     * 参数校验不合法
     */
    PARAM_ERROR(1001, "参数错误"),
    /**
     * 保存数据失败
     */
    DATA_ERROR(1101, "保存数据失败"),
    /**
     * 保存失败
     */
    SAVE_DATA_ERROR(1102, "保存数据失败"),
    /**
     * 保存失败
     */
    QUERY_DATA_ERROR(1103, "查询数据失败"),
    /**
     * 缓存出现问题
     */
    CACHE_ERROR(1201, "缓存出现异常"),
    /**
     * 保存缓存失败
     */
    SAVE_CACHE_ERROR(1202, "保存缓存失败"),
    /**
     * 查询缓存失败
     */
    QUERY_CACHE_ERROR(1203, "查询缓存失败"),
    /**
     * redis出现异常
     */
    REDIS_ERROR(1301, "redis出现异常"),
    /**
     * redis缓存保存失败
     */
    SAVE_REDIS_ERROR(1302, "redis缓存保存失败"),
    /**
     * redis缓存查询失败
     */
    QUERY_REDIS_ERROR(1303, "redis缓存查询失败"),
    /**
     * 类型转换错误
     */
    CONVERSION_ERROR(1401, "类型转换错误"),
    //类型转换失败
    TYPE_JUDGE_ERROR(1501, "类型不符合预期"),
    /**
     * 方法不支持
     */
    METHOD_NOT_ALLOWED_ERROR(405, "方法不支持"),
    /**
     * http请求媒体不支持
     */
    HTTP_MEDIA_TYPE_NOTSSUPPERT_ERROR(415, "http请求媒体不支持,检查contentType "),
    /**
     * 请求和响应媒体类型不一致
     */
    HTTP_MEDIA_TYPE_NOTACCEPTABLE_ERROR(406, "请求和响应媒体类型不一致 "),
    /**
     * 缺少请求路径参数
     */
    MISSLING_PATH_VARIABLE_ERROR(500, "缺少请求路径参数"),
    /**
     * 参数绑定错误
     */
    SERVLET_REQUEST_BINDING_ERROR(400, "参数绑定错误"),
    /**
     * 转换不支持
     */
    CONVERSION_NOT_SUPPORTED_ERROR(3003, "转换不支持"),
    /**
     * 参数类型不匹配
     */
    TYPE_MISMATCH_ERROR(400, "参数类型不匹配"),
    /**
     * /消息/文件不可读
     */
    HTTP_MESSAGE_NOT_READABLE_ERROR(400, "消息/文件不可读"),
    /**
     * 消息/文件不可写
     */
    HTTP_MESSAGE_NOT_WRITABLE_ERROR(400, "消息/文件不可写"),
    /**
     * 缺少请求部分
     */
    MISSING_SERVLET_REQUEST_PART_ERROR(400, "缺少请求部分"),
    //没有发现
    NO_HANDLER_FOUND_ERROR(404, "没有发现"),
    /**
     * 超时
     */
    ASYNC_REQUEST_TIMEOUT_ERROR(503, "超时"),
    /**
     * 没有发现类
     */
    CLASS_NOT_FOUND_ERROR(404, "没有发现类"),
    /**
     * 空指针异常
     */
    NULL_ERROR(2003, "空指针异常"),
    /**
     * 未知的域名
     */
    UNKNOWN_HOSTE_ERROR(2004, "未知的域名"),
    /**
     * 响应超时
     */
    TIME_OUT_ERROR(2005, "响应超时"),

    /**
     * 程序为排查到的错误,需要优化的地方
     */
    UNKNOWN_ERROR(2001, "未知错误"),
    /**
     * 系统处理问题
     */
    SYSTEM_ERROR(2002, "系统错误");

    /**
     * code
     */
    private Integer code;
    /**
     * msg
     */

    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ReturnCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getByCode(Integer code) {
        for (ReturnCode returnCode : ReturnCode.values()) {
            if (returnCode.getCode().equals(code)) {
                return returnCode.getMsg();
            }
        }
        return "";
    }

    public static Integer getByMsg(String msg) {
        for (ReturnCode returnCode : ReturnCode.values()) {
            if (returnCode.getMsg().equals(msg)) {
                return returnCode.getCode();
            }
        }
        return null;
    }
}
