package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;
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
    TextView tvReplies;
    ImageButton btnFavorited;
    ImageButton btnRetweeted;
    ImageButton btnReply;
    ImageView ivMedia;

    // resolve the view objects
//    @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
//    @BindView(R.id.tvUserName) TextView tvUserName;
//    @BindView(R.id.tvBody) TextView tvBody;
//    @BindView(R.id.tvTimeStamp) TextView tvTimeStamp;
//    @BindView(R.id.tvScreenName) TextView tvScreenName;
//    @BindView(R.id.tvRetweets) TextView tvRetweets;
//    @BindView(R.id.tvLikes) TextView tvLikes;
//    @BindView(R.id.btnFavorited) ImageButton btnFavorited;
//    @BindView(R.id.btnRetweeted) ImageButton btnRetweeted;
//    @BindView(R.id.btnReply) ImageButton btnReply;
//    @BindView(R.id.ivMedia) ImageView ivMedia;

    Integer position;

    static final int EDIT_REQUEST_CODE = 20;
    static final int REQUEST_CODE_REPLY = 30;

    // declare client
    TwitterClient client;

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
        tvReplies = (TextView) findViewById(R.id.tvReplies);
        btnFavorited = (ImageButton) findViewById(R.id.btnFavorited);
        btnRetweeted = (ImageButton) findViewById(R.id.btnRetweeted);
        btnReply = (ImageButton) findViewById(R.id.btnReply);
        ivMedia = (ImageView) findViewById(R.id.ivMedia);

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
        if (tweet.favorited) {
            btnFavorited.setImageResource(R.drawable.ic_vector_heart);
        } else {
            btnFavorited.setImageResource(R.drawable.ic_vector_heart_stroke);
        }
        if (tweet.retweeted) {
            btnRetweeted.setImageResource(R.drawable.ic_vector_retweet);
        } else {
            btnRetweeted.setImageResource(R.drawable.ic_vector_retweet_stroke);
        }

        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .bitmapTransform(new RoundedCornersTransformation(this, 150, 0))
                .into(ivProfileImage);

        Glide.with(this)
                .load(tweet.media_url_https)
                .bitmapTransform(new RoundedCornersTransformation(this, 20, 0))
                .into(ivMedia);


        btnFavorited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // deal with favorites
                if(tweet.favorited) {
                    tweet.favorited = false;
                    btnFavorited.setImageResource(R.drawable.ic_vector_heart_stroke);
                    TwitterApplication.getRestClient().postFavorite(false, tweet.uid, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                            Tweet tweet = null;
                            try {
                                tweet = Tweet.fromJSON(response);
                            } catch (JSONException e) {
                                Log.e("TAG", "tweet not taken from json correctly");
                            }

                            tvLikes.setText(String.valueOf(tweet.favoriteCount) + " Likes");
                        }
                    });
                } else {
                    tweet.favorited = true;
                    btnFavorited.setImageResource(R.drawable.ic_vector_heart);
                    TwitterApplication.getRestClient().postFavorite(true, tweet.uid, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                            Tweet tweet = null;
                            try {
                                tweet = Tweet.fromJSON(response);
                            } catch (JSONException e) {
                                Log.e("TAG", "tweet not taken from json correctly");
                            }

                            tvLikes.setText(String.valueOf(tweet.favoriteCount) + " Likes");
                        }
                    });
                }

                // create intent for the new activity
                Intent intent = new Intent(getApplicationContext(), TweetDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
//                // TODO
//                ((AppCompatActivity)).startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });



        btnRetweeted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // deal with favorites
                if(tweet.retweeted) {
                    tweet.retweeted = false;
                    btnRetweeted.setImageResource(R.drawable.ic_vector_retweet_stroke);
                    TwitterApplication.getRestClient().postRetweet(false, tweet.uid, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                            Tweet tweet = null;
                            try {
                                tweet = Tweet.fromJSON(response);
                            } catch (JSONException e) {
                                Log.e("TAG", "tweet not taken from json correctly");
                            }
                            tvRetweets.setText(String.valueOf(tweet.retweetCount - 1) + " Retweets");
                        }
                    });
                } else {
                    tweet.retweeted = true;
                    btnRetweeted.setImageResource(R.drawable.ic_vector_retweet);
                    TwitterApplication.getRestClient().postRetweet(true, tweet.uid, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                            Tweet tweet = null;
                            try {
                                tweet = Tweet.fromJSON(response);
                            } catch (JSONException e) {
                                Log.e("TAG", "tweet not taken from json correctly");
                            }
                            tvRetweets.setText(String.valueOf(tweet.retweetCount) + " Retweets");
                        }
                    });
                }

                // create intent for the new activity
                Intent intent = new Intent(getApplicationContext(), TweetDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
//                // TODO
//                ((AppCompatActivity)getApplicationContext()).startActivityForResult(intent, EDIT_REQUEST_CODE);
            }

        });

        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TweetDetailsActivity.this, ReplyActivity.class);
                i.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                startActivityForResult(i, REQUEST_CODE_REPLY);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TweetDetailsActivity.this, ComposeActivity.class);
                startActivityForResult(i, REQUEST_CODE_REPLY);
            }
        });
    }

    public String numFormatter(int n) {
        DecimalFormat df = new DecimalFormat("#,###,##0.0");
        if (n >= 1000000) {
            return df.format(n / 1000000.0) + "M";
        } else if (n >= 1000) {
            return df.format(n / 1000.0) + "K";
        } else {
            return Integer.toString(n);
        }
    }

    // TODO override backbutton
    @Override
    public void onBackPressed() {
        Log.d("OBP", "onBackPressed called");
        // create intent for the new activity
        Intent intent = new Intent(getApplicationContext(), TweetDetailsActivity.class);
        // serialize the movie using parceler, use its short name as a key
        intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
        intent.putExtra("POSITION", position);
        setResult(RESULT_OK, intent);
//        // TODO
//        ((AppCompatActivity)getApplicationContext()).startActivityForResult(intent, EDIT_REQUEST_CODE);
        // unwrap the movie passed in via intent, using its simple name as a key
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        finish();
    }
}





