package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by abhidhar on 2/5/15.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {


    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }


    //TODO: viewHolder pattern

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tweet tweet = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);


        }

        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);

        tvBody.setText(Html.fromHtml(tweet.getBody()));
        tvUserName.setText(tweet.getUser().getScreenName());
        tvName.setText(tweet.getUser().getName());
        tvTimestamp.setText(tweet.getCreatedAt());

        ivProfileImage.setImageResource(android.R.color.transparent); // clear out old image
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        return  convertView;
    }
}
