package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimeLineActivity extends ActionBarActivity {

    private static final int REQUEST_RESULT_CODE = 50;
    private TwitterClient client;
    ArrayList<Tweet> tweets;
    ListView lvTweets;
    TweetsArrayAdapter tweetAdapter;

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<>();
        tweetAdapter = new TweetsArrayAdapter(this, tweets);
        lvTweets.setAdapter(tweetAdapter);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                long max_id = tweets.get(totalItemsCount-1).getTweetId();
                populateTimeLine(max_id);
            }
        });
        /*
        lvTweets.setOnScrollListener(new AbsListView.OnScrollListener() {

            int previousTotalItemCount = 0;
            boolean loading = true;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

               // populateTimeLine(0L);
                if(totalItemCount < previousTotalItemCount) {
                    this.previousTotalItemCount = totalItemCount;
                }

                if(loading && (totalItemCount > previousTotalItemCount)) {
                    loading = false;
                    previousTotalItemCount = totalItemCount;
                }

                if(!loading && ( totalItemCount-visibleItemCount < firstVisibleItem+7 ) ) {
                    long max_id = tweets.get(totalItemCount-1).getTweetId();
                    populateTimeLine(max_id);
                }
            }
        });
*/
        client = TwitterApplication.getRestClient();

        client.getCurrentUserProfile(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                currentUser = new User(response);
            }
        });

        populateTimeLine(0L);

    }

    private void populateTimeLine(long max_id) {
        client.getHomeTimeLine(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //Log.d("DEBUG", response.toString());
                tweetAdapter.addAll(Tweet.fromJSONArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        }, max_id);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_line, menu);
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
            Intent i = new Intent(this, ComposeActivity.class);
            i.putExtra("currentUser", currentUser);
            startActivityForResult(i, REQUEST_RESULT_CODE);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_RESULT_CODE) {
            // if this is back from compose activity
            if(resultCode == RESULT_OK) {
                String tweetBody = data.getStringExtra("tweetBody");
                //Toast.makeText(this, tweet, Toast.LENGTH_SHORT).show();

                //Tweet tweetObj = new Tweet(System.currentTimeMillis(), tweet, "Abhi Dhar", "abhidhar");
                Tweet tweet = (Tweet) data.getSerializableExtra("tweet");

                tweets.add(0, tweet);
                tweetAdapter.notifyDataSetChanged();
            }
        }
    }
}
