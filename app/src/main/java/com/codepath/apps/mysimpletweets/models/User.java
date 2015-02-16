package com.codepath.apps.mysimpletweets.models;

import org.json.JSONArray;
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
    private int followersCount;
    private int friendsCount;
    private String tagLine;


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
            // replace "normal with "bigger"
            profileImageUrl = profileImageUrl.replace("normal","bigger");

            followersCount = object.getInt("followers_count");
            friendsCount = object.getInt("friends_count");

            tagLine = object.getString("description");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public User(JSONArray response) throws JSONException {
        //JSONObject jsonObject = response.getJSONObject(0);
        this(response.getJSONObject(0));
    }

    public String getFollowers() {
        return followersCount + " Following";
    }

    public String getFriends() {
        return friendsCount + " Followers";
    }

    public String getTagLine() {
        return tagLine;
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

    public String getRawScreenName() {
        return screenName;
    }
}
