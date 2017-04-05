package com.yushilei.im.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * @auther by yushilei.
 * @time 2017/4/5-13:52
 * @desc
 */

@Table(name = "User")
public class User implements Serializable {
    public User() {
    }

    @Column(name = "name", isId = true)
    private String name;
    @Column(name = "psw")
    private String psw;

    public User(String name, String psw) {
        this.name = name;
        this.psw = psw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }
}
