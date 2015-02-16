package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhidhar on 2/15/15.
 */
public abstract class TweetsListFragment extends Fragment {

    private TwitterClient client;

    ArrayList<Tweet> tweets;
    ListView lvTweets;
    TweetsArrayAdapter tweetAdapter;

    // inflation logic
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(tweetAdapter);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                long max_id = tweets.get(totalItemsCount-1).getTweetId();
                populateTimeLine(max_id);
            }
        });
        return v;
    }

    protected abstract void populateTimeLine(long max_id);


    //creationg lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();

        tweets = new ArrayList<>();
        tweetAdapter = new TweetsArrayAdapter(getActivity(), tweets);

    }

    public void add(Tweet atweet) {
        tweets.add(0, atweet);
        tweetAdapter.notifyDataSetChanged();
    }

    public void addAll(List<Tweet> tweetsList) {
        tweetAdapter.addAll(tweetsList);
    }

    public void clear() {
        tweetAdapter.clear();
    }

}
