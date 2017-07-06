package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.fragments.TweetsListFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;

import org.parceler.Parcels;

public class TimelineActivity extends AppCompatActivity implements TweetsListFragment.TweetSelectedListener {

//    TwitterClient client;

//    TweetAdapter tweetAdapter;
//    ArrayList<Tweet> tweets;
//    RecyclerView rvTweets;
    // a numeric code to identify the edit activity
    static final int EDIT_REQUEST_CODE = 20;
    static final int REQUEST_CODE_REPLY = 30;
//    private SwipeRefreshLayout swipeContainer;
//    MenuItem miActionProgressitem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
//        client = TwitterApplication.getRestClient();

        // get the view pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);

        // set the adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(), this));

        // setup the TabLayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
                startActivityForResult(i, REQUEST_CODE_REPLY);
            }
        });


//        });//        // lookup the swipe container view
//        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
//        // set up refresh listener which triggers new data loading
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Your code to refresh the list here.
//                // Make sure you call swipeContainer.setRefreshing(false)
//                // once the network request has completed successfully.
//                showProgressBar();
//                fetchTimelineAsync(0);
//            }
//        // configure the refreshing colors
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);

//        // find the RecyclerView
//        rvTweets = (RecyclerView) findViewById(R.id.rvTweet);
//
//        // init the arraylist (data source)
//        tweets = new ArrayList<>();
//
//        // construct the adapter from this data source
//        tweetAdapter = new TweetAdapter(tweets);
//
//        // RecyclerView setup (layout manager, use adapter)
//        rvTweets.setLayoutManager(new LinearLayoutManager(this));
//
//        // set the adapter
//        rvTweets.setAdapter(tweetAdapter);
//
//        populateTimeline();
    }

//    private void populateTimeline() {
//        client.getHomeTimeline(0, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                Log.d("TwitterClient", response.toString());
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
////                Log.d("TwitterClient", response.toString());
//                // iterate through the JSON array
//                // for each entry, deserialize the JSON object
//
//                fragmentTweetsList.addItems(response);
//
//                //tweetAdapter.clear();
////                for (int i = 0; i < response.length(); i++) {
////                    // convert each object to a TWeet model
////                    // add that Tweet model to our data source
////                    // notify the adapter that we've added an item
////                    try {
////                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
////                        tweets.add(tweet);
////                        tweetAdapter. notifyItemInserted(tweets.size() - 1);
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                }
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu and add items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    public void onProfileView(MenuItem item) {
        // launch the profile view
        Intent i = new Intent(this, ProfileActivity.class);
//        i.putExtra("screen_name", user.screenName);
        startActivity(i);
    }

    @Override
    public void onTweetSelected(Tweet tweet) {
        Toast.makeText(this, tweet.body, Toast.LENGTH_SHORT).show();
    }

    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // handle presses on the action bar items
//        switch (item.getItemId()) {
//            case R.id.miCompose:
//                composeMessage();
//                return true;
//            case R.id.miProfile:
//                showProfileView();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // store instance of the menu item containing progress
//        miActionProgressitem = menu.findItem(R.id.miActionProgress);
//        // extract the action-view from the menu item
//        ProgressBar v = (ProgressBar) MenuItemCompat.getActionView(miActionProgressitem);
//        // return to finish
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    public void showProgressBar() {
//        // Show progress item
//        miActionProgressitem.setVisible(true);
//    }
//
//    public void hideProgressBar() {
//        // Hide progress item
//        miActionProgressitem.setVisible(false);
//    }




//    // TODO fix this
//    @Override
//    protected void onResume() {
//        super.onResume();
//        populateTimeline();
//    }

//    public void fetchTimelineAsync(int page) {
//        // Send the network request to fetch the updated data
//        // `client` here is an instance of Android Async HTTP
//        // getHomeTimeline is an example endpoint.
//
//        client.getHomeTimeline(0, new JsonHttpResponseHandler() {
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                // Remember to CLEAR OUT old items before appending in the new ones
//                // TODO fix this
////                tweetAdapter.clear();
////                try {
////                    for (int i = 0; i < response.length(); i++) {
////                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
////                        tweets.add(tweet);
////                        tweetAdapter. notifyItemInserted(tweets.size() - 1);
////                    }
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
//
////                // ...the data has come back, add new items to your adapter...
////                tweetAdapter.addAll(tweets);
////                // Now we call setRefreshing(false) to signal refresh has finished
////                swipeContainer.setRefreshing(false);
////                hideProgressBar();
//            }
//            public void onFailure(Throwable e) {
//                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
//            }
//        });
//    }

    // launch subactivity
    public void composeMessage() {
        // create the new activity
        Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
        startActivityForResult(i, EDIT_REQUEST_CODE);
    }

//    public void showProfileView() {
//        Toast.makeText(this, "PROFILE!!!", Toast.LENGTH_SHORT).show();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            // extract name value from result extras
            String sTweet = data.getExtras().getString("tweet");
            int code = data.getExtras().getInt("code", 0);
            // insert new tweet into arraylist
            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));
            int position = getIntent().getIntExtra("POSITION", 0);
//            tweets.set(position, tweet);
//            tweetAdapter.notifyItemChanged(position);
//            rvTweets.scrollToPosition(position);
            // Toast the tweet to display temporarily on screen
//            Toast.makeText(this, sTweet, Toast.LENGTH_SHORT).show();
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_REPLY) {
            // extract name value from result extras
            String sTweet = data.getExtras().getString("tweet");
            int code = data.getExtras().getInt("code", 0);
            // insert new tweet into arraylist
            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));
//            tweets.add(0, tweet);
//            tweetAdapter.notifyItemInserted(0);
//            rvTweets.scrollToPosition(0);
            // Toast the tweet to display temporarily on screen
//            Toast.makeText(this, sTweet, Toast.LENGTH_SHORT).show();
        }
    }


}
