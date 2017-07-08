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

import com.codepath.apps.restclienttemplate.fragments.HomeTimelineFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsListFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;

import org.parceler.Parcels;

public class TimelineActivity extends AppCompatActivity implements TweetsListFragment.TweetSelectedListener {

    static final int EDIT_REQUEST_CODE = 20;
    static final int REQUEST_CODE_REPLY = 30;
    public TweetsPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
//        client = TwitterApplication.getRestClient();

        // get the view pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);

        pagerAdapter = new TweetsPagerAdapter(getSupportFragmentManager(), this);

        // set the adapter for the pager
        vpPager.setAdapter(pagerAdapter);

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
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu and add items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//
//        // expand the search view and request focus
//        searchItem.expandActionView();
//        searchView.requestFocus();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // perform query here
//                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
//                searchView.clearFocus();
//                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
//                i.putExtra("q", query);
//                startActivity(i);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
        return super.onCreateOptionsMenu(menu);
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
            HomeTimelineFragment current = (HomeTimelineFragment) pagerAdapter.getRegisteredFragment(0);
            current.onCreateNewTweet(tweet);
//            tweets.add(0, tweet);
//            tweetAdapter.notifyItemInserted(0);
//            rvTweets.scrollToPosition(0);
            // Toast the tweet to display temporarily on screen
//            Toast.makeText(this, sTweet, Toast.LENGTH_SHORT).show();
        }
    }


}
