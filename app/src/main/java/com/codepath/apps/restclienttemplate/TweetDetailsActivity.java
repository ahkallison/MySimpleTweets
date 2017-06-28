package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetDetailsActivity extends AppCompatActivity {

    // the tweet to display
    Tweet tweet;

    ImageView ivProfileImage;
    TextView tvUserName;
    TextView tvBody;
    TextView tvTimeStamp;
    TextView tvScreenName;
    TextView tvRetweets;
    TextView tvLikes;

    // declare client

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvBody = (TextView) findViewById(R.id.tvBody);
        tvTimeStamp = (TextView) findViewById(R.id.tvTimeStamp);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        tvRetweets = (TextView) findViewById(R.id.tvRetweets);
        tvLikes = (TextView) findViewById(R.id.tvLikes);

        // initialize the client?

        // unwrap the movie passed in via intent, using its simple name as a key
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));

        // set the username, body, etc.
        tvUserName.setText(tweet.user.name);
        tvBody.setText(tweet.body);
        tvTimeStamp.setText(TimeFormatter.getTimeStamp(tweet.createdAt));
        tvScreenName.setText("@" + tweet.user.screenName);
        tvLikes.setText(String.valueOf(tweet.favoriteCount) + " Likes");
        tvRetweets.setText(String.valueOf(tweet.retweetCount) + " Retweets");

        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .bitmapTransform(new RoundedCornersTransformation(this, 25, 0))
                .into(ivProfileImage);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

}
