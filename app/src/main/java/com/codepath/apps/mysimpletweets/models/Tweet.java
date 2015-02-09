package com.codepath.apps.mysimpletweets.models;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.codepath.apps.mysimpletweets.Util;

/*
 * This is a temporary, sample model that demonstrates the basic structure
 * of a SQLite persisted Model object. Check out the ActiveAndroid wiki for more details:
 * https://github.com/pardom/ActiveAndroid/wiki/Creating-your-database-model
 * 
 */
//@Table(name = "items")
public class Tweet extends Model implements Serializable{
    private static final long serialVersionUID = -8681656166565609171L;


	// Define table fields
	//@Column(name = "name")
	//private String name;

    private long tweetId;
    private String body;
    private long uid;
    private String createdAt;



    private User user;

    public Tweet(long createTime, String body, String name, String screenName) {
        setCreatedAt(createTime);
        this.body = body;
        this.user = new User(name, screenName);
    }

	public Tweet() {
		super();
	}

	// Parse model from JSON
	public Tweet(JSONObject object){
		super();

		try {
            this.tweetId = object.getLong("id");
			this.body = object.getString("text");
            this.uid = object.getLong("id");
            this.createdAt = object.getString("created_at");

            this.user = new User(object.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

    public String getBody() {
        return body;
    }

    public long getTweetId() {
        return tweetId;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return Util.getRelativeTimeAgo(createdAt);
    }

    public void setCreatedAt(long currentTime) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat df = new SimpleDateFormat(twitterFormat);

        Date date = new Date(currentTime);
        String dateStr = df.format(date);

        createdAt = dateStr;
    }

    // Record Finders
	public static Tweet byId(long id) {
		return new Select().from(Tweet.class).where("id = ?", id).executeSingle();
	}

	public static List<Tweet> recentItems() {
		return new Select().from(Tweet.class).orderBy("id DESC").limit("300").execute();
	}

    public static ArrayList<Tweet> fromJSONArray(JSONArray response) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for(int i = 0; i < response.length(); i++) {
            try {
                JSONObject obj = response.getJSONObject(i);
                Tweet tweet = new Tweet(obj);
                tweets.add(tweet);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }

        return tweets;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "body='" + body + '\'' +
                ", uid=" + uid +
                ", createdAt='" + createdAt + '\'' +
                ", user=" + user +
                '}';
    }
}
