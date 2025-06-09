package com.library.web.vo;

import java.util.Date;

public class MemberVO {
    private String memberId;  // camelCase로 변경
    private String password;
    private String name;
    private String email;
    private String role;
    private Date regDate;
    private String status;
    private String phone;
    private String address;

    public MemberVO() {
    }

    public MemberVO(String memberId, String password, String name, String email, String role,
                    Date regDate, String status, String phone, String address) {
        this.memberId = memberId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.regDate = regDate;
        this.status = status;
        this.phone = phone;
        this.address = address;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "MemberVO{" +
               "memberId='" + memberId + '\'' +
               ", password='[PROTECTED]'" +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", role='" + role + '\'' +
               ", regDate=" + regDate +
               ", status='" + status + '\'' +
               ", phone='" + phone + '\'' +
               ", address='" + address + '\'' +
               '}';
    }
}
