package br.com.a2ts_mobile.User_Manager;

import java.io.Serializable;

/**
 * Created by Enan on 6/17/2017.
 */

public class UserModel implements Serializable {
    public static Integer ID;
    public static Integer PERMISSION;
    public static String TOKEN;

    private Integer id;
    private String name;
    private String email;
    private String password;
    private Integer permission;
    private String token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
