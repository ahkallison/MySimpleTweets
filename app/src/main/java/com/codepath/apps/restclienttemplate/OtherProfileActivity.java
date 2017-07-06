package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;

import org.parceler.Parcels;

import java.util.ArrayList;

public class OtherProfileActivity extends AppCompatActivity implements TweetAdapter.TweetAdapterListener {

    TwitterClient client;
    Tweet tweet;
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;
    SwipeRefreshLayout swipeContainer;

    public interface TweetSelectedListener {
        // handle tweet selection
        public void onTweetSelected(Tweet tweet);
    }

    @Override
    public void onItemSelected(View view, int position) {
        Tweet tweet = tweets.get(position);
        // Toast.makeText(getContext(), tweet.body, Toast.LENGTH_SHORT).show();
        ((TweetSelectedListener) this).onTweetSelected(tweet);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);

        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        // unwrap the movie passed in via intent, using its simple name as a key
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));

        tvName.setText(tweet.user.name);
        tvTagline.setText(tweet.user.tagLine);
        tvFollowers.setText(tweet.user.followersCount + " Followers");
        tvFollowing.setText(tweet.user.followingCount + " Following");
        // load profile image with Glide
        Glide.with(this).load(tweet.user.profileImageUrl).into(ivProfileImage);

        String screenName = getIntent().getStringExtra("screen_name");

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.flContainer, UserTimelineFragment.newInstance(screenName)).commit();


//        populateUserHeadline(tweet);

//        client = TwitterApplication.getRestClient();
//        client.getOtherUserTimeline(screenName, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // deserialize the User object
//                User user = null;
//                try {
//                    user = User.fromJSON(response);
//                    // set the title of the ActionBar based on the user information
                    getSupportActionBar().setTitle(screenName);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

//    public void addItems(JSONArray response) {
//        try {
//            for (int i = 0; i < response.length(); i++) {
//                Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
//                tweets.add(tweet);
//                tweetAdapter.notifyItemInserted(tweets.size() - 1);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

//    private void populateUserTimeline(String screenName) {
//        client.getOtherUserTimeline(screenName, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                Log.d("TwitterClient", response.toString());
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
////                Log.d("TwitterClient", response.toString());
//                addItems(response);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                Log.d("TwitterClient", responseString);
//                throwable.printStackTrace();
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                Log.d("TwitterClient", errorResponse.toString());
//                throwable.printStackTrace();
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                Log.d("TwitterClient", errorResponse.toString());
//                throwable.printStackTrace();
//            }
//        });
//    }

//    public void populateUserTimeline(Tweet tweet) {
//
//        TextView tvName = (TextView) findViewById(R.id.tvName);
//        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
//        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
//        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
//        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
//
////        Toast.makeText(this, tweet.body, Toast.LENGTH_SHORT).show();
//
////        tvName.setText(tweet.user.name);
////        tvTagline.setText(tweet.user.tagLine);
////        tvFollowers.setText(tweet.user.followersCount + " Followers");
////        tvFollowing.setText(tweet.user.followingCount + " Following");
////        // load profile image with Glide
////        Glide.with(this).load(tweet.user.profileImageUrl).into(ivProfileImage);
//    }
}
