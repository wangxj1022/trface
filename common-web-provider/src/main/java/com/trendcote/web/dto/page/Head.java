package com.trendcote.web.dto.page;

import com.trendcote.web.constrants.HttpConstants;

public class Head {
    /**
     * 版本
     */
    private String version;

    /**
     * 响应码
     */
    private String respCode;

    /**
     * 响应描述
     */
    private String respMsg;
    /**
     * 时间戳
     */
    private Long timestamp = System.currentTimeMillis();

    public Head() {
        super();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    /**
     * 设置返回错误码和错误信息
     *
     * @param httpConstants
     */
    public void setCodeAndMsg(HttpConstants httpConstants) {
        this.respCode = httpConstants.getErrorCode();
        this.respMsg = httpConstants.getErrorMsg();
    }
}
