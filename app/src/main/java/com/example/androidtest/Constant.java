package com.example.androidtest;

public class Constant {

    //authentication
    public static final String BASE_URL = "http://internal.cebu.cityserv.ph";

    public static final String USER_REGISTER_API_URL = BASE_URL+"/api/citizen/register.json";
    public static final String USER_LOGIN_API_URL = BASE_URL+"/api/citizen/login.json";
    public static final String USER_LOGOUT_API = BASE_URL+"/api/citizen/logout.json";

    public static final String USER_PROFILE_API_URL = BASE_URL+"/api/citizen/profile/show.json";
    public static final String EDIT_USER_PROFILE_API_URL = BASE_URL+"/api/citizen/profile/edit-profile.json";
    public static final String EDIT_USER_PASSWORD_API = BASE_URL+"api/citizen/profile/edit-password.json";

    public static final String ARTICLE_DETAILS_API = BASE_URL+"api/article/show.json";
    public static final String ALL_PUBLISHED_ARTICLES_API = BASE_URL+"api/article/all.json";

}
