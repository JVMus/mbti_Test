package com.wybs.mbti.common.uud;

/**
 * 创建时间：2017年11月11日
 * <p>修改时间：2017年11月11日
 * <p>类说明：UUD客户端配置
 * 
 * @author Mumus
 * @version 1.0
 */
public class UudConfig {
    private long accessId;
    private String secretKey;
    private String host;

    public long getAccessId() {
        return accessId;
    }

    public void setAccessId(long accessId) {
        this.accessId = accessId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "UudConfig [accessId=" + accessId + ", secretKey=" + secretKey + ", host=" + host + "]";
    }

}
