package com.example.project.models;

import java.util.HashMap;

public class User {
    String name,email,role,address,city,gender,phoneno,password;
    String pic,userid;
    String profilepic;
    HashMap<String, Object> updateprofile;

    public HashMap<String, Object> getUpdateprofile() {
        return updateprofile;
    }

    public void setUpdateprofile(HashMap<String, Object> updateprofile) {
        this.updateprofile = updateprofile;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public User(){}

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public User(String uid, String name, String email, String role, String password) {
        this.userid=uid;
        this.name = name;
        this.email = email;
        this.role = role;
        this.password=password;
    }
    public User(String address,String city,String gender,String phoneno) {
        this.address=address;
        this.city=city;
        this.gender=gender;
        this.phoneno=phoneno;
    }
public User(String pic)
{
    this.pic=pic;
}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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
}
