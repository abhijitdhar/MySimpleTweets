package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
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
        ivProfileImage.setTag(R.id.tvCUsername, tweet.getUser().getRawScreenName());
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();


                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("raw_screen_name", v.getTag(R.id.tvCUsername).toString());
                getContext().startActivity(i);

            }
        });

        return  convertView;
    }

}
