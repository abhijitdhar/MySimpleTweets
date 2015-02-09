package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by abhidhar on 2/5/15.
 */
public class User implements Serializable{

    private static final long serialVersionUID = -8681656166564569171L;

    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;

    public User(String name, String screenName) {
        this.name = name;
        this.screenName = screenName;
    }

    public User(JSONObject object) {
        try {
            name = object.getString("name");
            uid = object.getLong("id");
            screenName = object.getString("screen_name");
            profileImageUrl = object.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return "@" + screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
