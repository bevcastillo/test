package com.example.androidtest.model;

public class Users {

    int user_id;
    String user_type;
    String user_code;
    String contact_number;
    String fname;
    String lname;
    String mname;
    String suffix;
    String with_id;
    String age;
    String civil_status;
    String name;
    String username;
    String email;
    String residence_address;
    String brgy;
    String district;

    public Users() {
    }

    public Users(int user_id, String user_type, String user_code, String contact_number, String fname,
                 String lname, String mname, String suffix, String with_id, String age,
                 String civil_status, String name, String username, String email,
                 String residence_address, String brgy, String district) {
        this.user_id = user_id;
        this.user_type = user_type;
        this.user_code = user_code;
        this.contact_number = contact_number;
        this.fname = fname;
        this.lname = lname;
        this.mname = mname;
        this.suffix = suffix;
        this.with_id = with_id;
        this.age = age;
        this.civil_status = civil_status;
        this.name = name;
        this.username = username;
        this.email = email;
        this.residence_address = residence_address;
        this.brgy = brgy;
        this.district = district;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getWith_id() {
        return with_id;
    }

    public void setWith_id(String with_id) {
        this.with_id = with_id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCivil_status() {
        return civil_status;
    }

    public void setCivil_status(String civil_status) {
        this.civil_status = civil_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResidence_address() {
        return residence_address;
    }

    public void setResidence_address(String residence_address) {
        this.residence_address = residence_address;
    }

    public String getBrgy() {
        return brgy;
    }

    public void setBrgy(String brgy) {
        this.brgy = brgy;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
