package com.seawind.indicham;

/**
 * Created by admin on 07-Jun-18.
 */

public class Session {

    private String user_id;
    private String user_name;
    private String user_email;
    private String user_mobile_number;

    private static Session instance = null;

    public static Session getInstance() {
        if(instance == null)
            instance = new Session();
        return instance;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_mobile_number() {
        return user_mobile_number;
    }

    public void setUser_mobile_number(String user_mobile_number) {
        this.user_mobile_number = user_mobile_number;
    }


}
