package com.lzmhc.silkdiagrampro.domain;

public class WeiXinUser {
    private int id;
    private String user_id;
    private String avatar;
    private String session_key;
    private String openid;
    private String user_name;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "WeiXinUser{" +
                "id=" + id +
                ", user_id='" + user_id + '\'' +
                ", avatar='" + avatar + '\'' +
                ", session_key='" + session_key + '\'' +
                ", openid='" + openid + '\'' +
                ", user_name='" + user_name + '\'' +
                '}';
    }
}
