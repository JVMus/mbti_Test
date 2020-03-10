package com.wybs.mbti.common.uud;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Date;

/**
 * 创建时间：2017年11月11日
 * <p>修改时间：2017年11月11日
 * <p>类说明：UUD用户信息
 * 
 * @author Mumus
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class UudUser {
    private Integer id;

    private String email;

    private String passwd;

    private String userName;

    private Integer stat;

    private String mobile;

    private Date regTime;

    private Date lastLoginTime;

    private String headIcon;

    private Integer emailVerify;

    private Integer mobileVerify;

    private Integer sex;

    private String userType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public Integer getEmailVerify() {
        return emailVerify;
    }

    public void setEmailVerify(Integer emailVerify) {
        this.emailVerify = emailVerify;
    }

    public Integer getMobileVerify() {
        return mobileVerify;
    }

    public void setMobileVerify(Integer mobileVerify) {
        this.mobileVerify = mobileVerify;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UudUser [id=" + id + ", email=" + email + ", passwd=" + passwd + ", userName=" + userName + ", stat=" + stat + ", mobile=" + mobile
                + ", regTime=" + regTime + ", lastLoginTime=" + lastLoginTime + ", headIcon=" + headIcon + ", emailVerify=" + emailVerify + ", mobileVerify="
                + mobileVerify + ", sex=" + sex + ", userType=" + userType + "]";
    }
}
