package com.trendcote.web.dto.page;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author yan
 */
public class BaseRequest<T> {
    @Valid
    @NotNull
    private Head head;
    @Valid
    @NotNull
    private T body;

    public BaseRequest() {
    }

    public BaseRequest(Head head) {
        this.head = head;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
