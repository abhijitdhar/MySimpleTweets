package com.codepath.apps.mysimpletweets.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by abhidhar on 2/15/15.
 */
public class HomeTimeLineFragment extends TweetsListFragment {

    private TwitterClient client;

    private static final int REQUEST_COMPOSE_RESULT_CODE = 50;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient();

        populateTimeLine(0L);
    }

    protected void populateTimeLine(final long max_id) {
        client.getHomeTimeLine(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //Log.d("DEBUG", response.toString());
                if(max_id == 0L) {
                    clear();
                }
                addAll(Tweet.fromJSONArray(response));


                //TODO Swipe refresh  swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        }, max_id);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_COMPOSE_RESULT_CODE) {
            // if this is back from compose activity
            if(resultCode == Activity.RESULT_OK) {
                Tweet tweet = (Tweet) data.getSerializableExtra("tweet");
                add(tweet);
            }
        }
    }
}
