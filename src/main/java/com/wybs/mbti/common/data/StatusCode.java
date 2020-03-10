package com.wybs.mbti.common.data;

/**
 * <p>接口响应码</p>
 *
 * <p>Date：2018-04-02</p>
 *
 * @author Mumus
 */
public enum StatusCode {
    /**
     * 100000 操作成功 http 200
     */
    OK(100000, "成功"),
    /**
     * 200101 接口不存在 http 404
     */
    METHOD_NOT_FOUND(200101, "接口不存在"),
    /**
     * 200102 接口不支持 http 405
     */
    METHOD_NOT_SUPPORTED(200102, "接口不支持"),
    /**
     * 200201 缺少参数 http 400
     */
    MISS_PARAM(200201, "缺少参数"),
    /**
     * 200202 错误参数类型 http 400
     */
    MISMATCH_PARAM(200202, "错误参数类型"),
    /**
     * 200203 参数校验错误 http 400
     */
    PARAM_ERROR(200203, "错误参数"),
    /**
     * 200301 接口未授权 http 401
     */
    UNAUTHORIZED(200301, "接口未授权"),
    /**
     * 200302 接口授权过期 http 401
     */
    AUTHORIZED_EXPIRED(200302, "接口授权过期"),
    /**
     * 200303 接口签名错误 http 403
     */
    SIGNATURE_ERROR(200303, "接口签名错误"),

    /**
     * 200304 接口禁止访问 http 403
     */
    FORBIDDEN(200304, "接口禁止访问"),
    /**
     * 300401 对象不存在 http 200
     */
    @Deprecated
    OJBECT_NOT_FOUND(300401, "对象不存在"),
    /**
     * 300402 对象已存在 http 200
     */
    @Deprecated
    OJBECT_EXISTED(300402, "对象已存在"),
    /**
     * 900000 未知错误 http 500
     */
    UNKOWN_ERROR(900000, "未知错误"),

    /**
     * 310201 测试结果不存在 http 200
     */
    RESULT_NOT_FOUND(310201, "测试结果不存在"),
    /**
     * 310202 测试结果保存失败 http 200
     */
    RESULT_SAVE_FAILED(310202, "测试结果保存失败"),

    /**
     * 501101 账号网络异常 http 500
     */
    USER_NETWORK_ERROR(501101, "账号网络异常"),
    /**
     * 501203 用户错误参数 http 400
     */
    USER_PARAM_ERROR(501203, "用户错误参数"),
    /**
     * 501210 用户密码不符合规则 http 200
     */
    USER_PASSWORD_INVALID(501210, "用户密码不符合规则"),
    /**
     * 501303 用户签名有误 http 403
     */
    USER_SIGN_ERROR(501303, "用户签名有误"),
    /**
     * 501304 账号无权限访问/token失效 http 403
     */
    USER_FORBIDDEN(501304, "账号无权限访问/token失效"),
    /**
     * 501401 账号不存在 http 200
     */
    USER_NOT_FOUND(501401, "账号不存在"),
    /**
     * 501402 账号已存在 http 200
     */
    USER_EXISTED(501402, "账号已存在"),
    /**
     * 501501 用户验证码不正确 http 200
     */
    USER_VERIFY_CODE_ERROR(501501, "用户验证码不正确"),
    /**
     * 501502 帐号密码不正确 http 200
     */
    USER_LOGIN_ERROR(501502, "帐号密码不正确"),
    /**
     * 501503 账号已冻结 http 200
     */
    USER_IS_FROZEN(501503, "账号已冻结"),
    /**
     * 501504 账号未激活 http 200
     */
    USER_IS_UNACTIVE(501504, "账号未激活"),
    /**
     * 501900 账号系统异常 http 500
     */
    USER_UNKOWN_ERROR(501900, "账号系统异常");

    private final int value;
    private final String message;

    private StatusCode(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public static StatusCode valueOf(int statusCode) {
        for (StatusCode status : values()) {
            if (status.value == statusCode) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
    }

    /**
     * 返回状态码
     * 
     * @return status code
     */
    public int value() {
        return value;
    }

    /**
     * 返回状态码信息
     * 
     * @return message
     */
    public String message() {
        return message;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    public String toFullString() {
        return value + ":" + message;
    }

}
