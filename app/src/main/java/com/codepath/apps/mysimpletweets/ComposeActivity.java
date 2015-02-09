package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class ComposeActivity extends ActionBarActivity {

    private EditText etTweetBody;
    private TextView tvUserName;

    private TwitterClient client;

    Tweet tweet;

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etTweetBody = (EditText) findViewById(R.id.etTweetBody);
        tweet = new Tweet();

        client = TwitterApplication.getRestClient();

        currentUser = (User) getIntent().getSerializableExtra("currentUser");


        TextView tvUserName = (TextView) findViewById(R.id.tvCUsername);
        tvUserName.setText(currentUser.getScreenName());

        TextView tvName = (TextView) findViewById(R.id.tvCName);
        tvName.setText(currentUser.getName());

        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivCProfileImage);
        Picasso.with(this).load(currentUser.getProfileImageUrl()).into(ivProfileImage);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            onTweet();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    /*
    public void onCancel(View view) {
        setResult(RESULT_CANCELED, null);
        finish();
    }
*/
    public void onTweet() {
        final Intent i = new Intent();
        String tweetBody = etTweetBody.getText().toString();

        client.composeTweet(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Toast.makeText(ComposeActivity.this, "Tweet posted", Toast.LENGTH_SHORT).show();

                tweet = new Tweet(response);

                i.putExtra("tweetBody", etTweetBody.getText().toString());
                i.putExtra("tweet", tweet);
                setResult(RESULT_OK, i);

                Log.i("INFO: Inside", tweet.toString());
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(ComposeActivity.this, "Failed to post. Try again.", Toast.LENGTH_SHORT).show();
            }
        }, tweetBody);

        Log.i("INFO: outside", tweet.toString());
        //finish();

    }
}
