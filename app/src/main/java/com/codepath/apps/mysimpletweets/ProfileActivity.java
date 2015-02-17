package com.codepath.apps.mysimpletweets;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.fragments.UserProfileFragment;
import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends ActionBarActivity {

    User currentUser;
    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        client = TwitterApplication.getRestClient();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentUser = (User) getIntent().getSerializableExtra("currentUser");

        String screenName = "";
        if(currentUser == null) {

            // means it is for other than the current user
            screenName = getIntent().getStringExtra("raw_screen_name");
            client.getUserProfileOf(screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                    try {
                        currentUser = new User(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    populateProfileHeader(currentUser);
                }
            });
        }
        else {
            screenName = currentUser.getRawScreenName();
            populateProfileHeader(currentUser);
        }

        getSupportActionBar().setTitle("@" + screenName);

        if (savedInstanceState == null) {
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flUserTimelineContainer, fragmentUserTimeline);
            ft.commit();
        }
    }

    private void populateProfileHeader(User currentUser) {
        UserProfileFragment fragmentUserProfile = UserProfileFragment.newInstance(currentUser);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flUserProfileContainer, fragmentUserProfile);

        ft.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
