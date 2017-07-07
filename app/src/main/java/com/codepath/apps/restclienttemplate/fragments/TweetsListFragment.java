package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.EndlessRecyclerViewScrollListener;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.Tweet;
import com.codepath.apps.restclienttemplate.TweetAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by ahkallison on 7/3/17.
 */

public abstract class TweetsListFragment extends Fragment implements TweetAdapter.TweetAdapterListener {

    // TODO added
    private EndlessRecyclerViewScrollListener scrollListener;

    public interface TweetSelectedListener {
        // handle tweet selection
        void onTweetSelected(Tweet tweet);
    }

    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;
    SwipeRefreshLayout swipeContainer;
    MenuItem miActionProgressitem;

    // inflation happens inside onCreateView
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout
        View v = inflater.inflate(R.layout.fragments_tweets_list, container, false);

        // find the RecyclerView
        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweet);
        // init the arraylist (data source)
        tweets = new ArrayList<>();
        // construct the adapter from this data source
        tweetAdapter = new TweetAdapter(tweets, this);

        // TODO
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        // RecyclerView setup (layout manager, use adapter)
        rvTweets.setLayoutManager(linearLayoutManager);
        // set the adapter
        rvTweets.setAdapter(tweetAdapter);

        // lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        // set up refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
//                showProgressBar();
                fetchTimelineAsync(0);
            }


            // configure the refreshing colors
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light)

//        });
        });

        // TODO
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                Tweet lastTweet = tweets.get(tweets.size() - 1);
                getNextPage(lastTweet.uid - 1);
            }
        };

        // TODO
        // Adds the scroll listener to RecyclerView
        rvTweets.addOnScrollListener(scrollListener);

        return v;
    }

    public void getNextPage(long id) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }

    public void addItems(JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                tweets.add(tweet);
                tweetAdapter.notifyItemInserted(tweets.size() - 1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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

    public void fetchTimelineAsync(int page) {
    }

//        public void fetchTimelineAsync(int page) {
//        // Send the network request to fetch the updated data
//        // `client` here is an instance of Android Async HTTP
//        // getHomeTimeline is an example endpoint.
//
//            client.getHomeTimeline(0, new JsonHttpResponseHandler() {
//                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                    // Remember to CLEAR OUT old items before appending in the new ones
//                    // TODO fix this
//    //                tweetAdapter.clear();
//    //                try {
//    //                    for (int i = 0; i < response.length(); i++) {
//    //                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
//    //                        tweets.add(tweet);
//    //                        tweetAdapter. notifyItemInserted(tweets.size() - 1);
//    //                    }
//    //                } catch (JSONException e) {
//    //                    e.printStackTrace();
//    //                }
//
//    //                // ...the data has come back, add new items to your adapter...
//    //                tweetAdapter.addAll(tweets);
//    //                // Now we call setRefreshing(false) to signal refresh has finished
//    //                swipeContainer.setRefreshing(false);
//    //                hideProgressBar();
//                }
//                    public void onFailure(Throwable e) {
//                        Log.d("DEBUG", "Fetch timeline error: " + e.toString());
//                    }
//            });
//        }

    @Override
    public void onItemSelected(View view, int position) {
        Tweet tweet = tweets.get(position);
        // Toast.makeText(getContext(), tweet.body, Toast.LENGTH_SHORT).show();
        ((TweetSelectedListener) getActivity()).onTweetSelected(tweet);
    }

    public void composeReply(Tweet tweet) {
        tweets.add(0, tweet);
        tweetAdapter.notifyItemInserted(0);
        rvTweets.scrollToPosition(0);
    }

    public void editTweet(Tweet tweet, int position) {
        tweets.set(position, tweet);
        tweetAdapter.notifyItemChanged(position);
        rvTweets.scrollToPosition(position);
    }

}
