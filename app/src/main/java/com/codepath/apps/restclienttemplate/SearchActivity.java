package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.codepath.apps.restclienttemplate.fragments.SearchTweetsFragment;

public class SearchActivity extends AppCompatActivity {

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        client = TwitterApplication.getRestClient();

        Intent i = getIntent();
        String query = i.getStringExtra("q");

        if (savedInstanceState == null) {
            SearchTweetsFragment searchFragment = SearchTweetsFragment.newInstance(query);
            // display the user timeline fragment inside the container
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            // make change
            ft.replace(R.id.flContainer, searchFragment);

            // commit
            ft.commit();
        }

    }

//    private void fetchSearches(String query) {
//        client.searchTweets(query, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                JSONArray tweet;
//                //
//                tweet = response.getJSONArray("statuses");
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                super.onFailure(statusCode, headers, responseString, throwable);
//            }
//        });
//    }

}
