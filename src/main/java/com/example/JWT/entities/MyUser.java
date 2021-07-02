package com.example.JWT.entities;

import javax.persistence.*;

@Entity
@Table(name = "my_user")
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "username", unique=true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;

    public MyUser(){
    }

    public MyUser(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Integer getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
