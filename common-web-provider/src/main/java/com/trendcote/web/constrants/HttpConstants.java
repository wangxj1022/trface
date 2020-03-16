package com.trendcote.web.constrants;

/**
 * 网络请求错误码
 *
 * @author 岩
 * @date 2018/6/15/015
 */
public enum HttpConstants {

    // 设备返回成功
    RESPFLG_SUCCESS("0000", "请求成功"),

    // 系统之间通信，9000表示请求成功
    SUCCESS("9000", "成功"),
    SYSTEM_EXCEPTION("9001", "系统异常"),
    FAIL("9002", "请求失败"),
    NET_FAIL("9003", "网络请求失败"),
    DEVICE_FAIL("9004", "设备返回错误信息"),


    // 权限和认证相关 92开头
    NOT_LOGIN("9201", "用户未登录"),
    NO_PERMISSION("9203", "用户没有权限"),

    // 用户相关错误码 93开头
    USER_NOT_EXIST("9302", "用户不存在"),
    PASSWORD_ERROR("9303", "密码错误"),
    TOKEN_ISSUE_FAIL("9304", "token签发失败"),

    // 同步模块相关错误码 94开头
    STAFF_SYNC_ERROR("9401","员工同步失败"),
    VISITOR_SYNC_ERROR("9402","访客同步失败");

    private String errorCode;
    private String errorMsg;

    HttpConstants(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "errorCode=" + errorCode + ",errorMsg=" + errorMsg;
    }

}
