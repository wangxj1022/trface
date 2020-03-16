package com.trendcote.web.dto.Business;

/**
 * @author 莹
 * @date 2018/6/4
 */
public class EmailDto
{
    private String emailAddress;   // 邮箱地址

    private String emailName;      // 邮箱使用者

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailName() {
        return emailName;
    }

    public void setEmailName(String emailName) {
        this.emailName = emailName;
    }
}
