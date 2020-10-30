package com.example.practice.bean;

import java.util.List;

/**
 * Created By 大苏打
 * on 2020/10/28
 */
public class LoginBean{

    
    
    /**
     * admin : false
     * chapterTops : []
     * coinCount : 0
     * collectIds : []
     * email :
     * icon :
     * id : 80619
     * nickname : login1
     * password :
     * publicName : login1
     * token :
     * type : 0
     * username : login1
     */

    private boolean admin;
    private int coinCount;
    private String email;
    private String icon;
    private int id;
    private String nickname;
    private String password;
    private String publicName;
    private String token;
    private int type;
    private String username;
    private List<?> chapterTops;
    private List<?> collectIds;

    public boolean isAdmin(){
        return admin;
    }

    public void setAdmin(boolean admin){
        this.admin = admin;
    }

    public int getCoinCount(){
        return coinCount;
    }

    public void setCoinCount(int coinCount){
        this.coinCount = coinCount;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getIcon(){
        return icon;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPublicName(){
        return publicName;
    }

    public void setPublicName(String publicName){
        this.publicName = publicName;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public int getType(){
        return type;
    }

    public void setType(int type){
        this.type = type;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public List<?> getChapterTops(){
        return chapterTops;
    }

    public void setChapterTops(List<?> chapterTops){
        this.chapterTops = chapterTops;
    }

    public List<?> getCollectIds(){
        return collectIds;
    }

    public void setCollectIds(List<?> collectIds){
        this.collectIds = collectIds;
    }
}
