package com.trendcote.web.dto.system;

import javax.validation.constraints.NotBlank;

public class LoginDto {

    @NotBlank(message = "用户名不能为空，请您先填写用户名")
    private String username;
    @NotBlank(message = "密码不能为空，请您先填写密码")
    private String password;
  /*  @NotBlank(message = "图片验证码不能为空，请您先填写验证码")
    private String verifycode;*/

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   /* public String getVerifycode() {
        return verifycode;
    }

    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode;
    }*/
}