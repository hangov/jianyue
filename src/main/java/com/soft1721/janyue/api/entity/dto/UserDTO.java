package com.soft1721.janyue.api.entity.dto;


public class UserDTO {
    private String mobile;
    private String password;

    public UserDTO() {
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public UserDTO(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}