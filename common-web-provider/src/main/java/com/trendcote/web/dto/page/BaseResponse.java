package com.trendcote.web.dto.page;

/**
 * 返回信息
 */
public class BaseResponse {

    private Head head = new Head();

    private Object body = new Object();

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public BaseResponse() {
        super();
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public BaseResponse(Head head) {
        super();
        this.head = head;
    }


}
