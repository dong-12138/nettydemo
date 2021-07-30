package com.briup.dto;

public class User {
    private String userName;
    private String pwd;
    private int age;
    private String type;

    public User() {
    }

    public User(String userName,String pwd,int age,String type) {
        this.userName = userName;
        this.pwd = pwd;
        this.age = age;
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", pwd='" + pwd + '\'' +
                ", age=" + age +
                ", type='" + type + '\'' +
                '}';
    }
}
