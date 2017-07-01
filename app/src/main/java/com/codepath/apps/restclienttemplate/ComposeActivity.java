package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

//    EditText etTweet;
//    ImageView ivProfile;
//    private TextView tvCount;

    Tweet tweet;


    // resolve the view objects
    @BindView(R.id.etTweet) EditText etTweet;
    @BindView(R.id.tvCount) TextView tvCount;
    @BindView(R.id.ivProfile) ImageView ivProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
//        etTweet = (EditText) findViewById(R.id.etTweet);
//        tvCount = (TextView) findViewById(R.id.tvCount);
//        ivProfile = (ImageView) findViewById(R.id.ivProfile);

        // applying ButterKnife
        ButterKnife.bind(this);

        final TextWatcher txwatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvCount.setText(String.valueOf(140 - s.length()));
            }
            public void afterTextChanged(Editable s) {
            }
        };
        etTweet.addTextChangedListener(txwatcher);

//        Glide.with(this)
//                .load(tweet.user.profileImageUrl)
//                .bitmapTransform(new RoundedCornersTransformation(this, 25, 0))
//                .into(ivProfile);
    }

    public void onSubmit(View view) {
        TwitterApplication.getRestClient().sendTweet(etTweet.getText().toString(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    tweet = new Tweet();
                    tweet = Tweet.fromJSON(response);
                    // prepare data intent
                    Intent data = new Intent();
                    // pass relevant data back as a result
                    data.putExtra("tweet", Parcels.wrap(tweet));
                    data.putExtra("code", 200);
                    setResult(RESULT_OK, data);
                    Intent i = new Intent(ComposeActivity.this, TimelineActivity.class);
                    startActivity(i);
                    finish();

                } catch (JSONException e) {
                    // log the error
                    e.printStackTrace();
                    Log.e("ComposeActivity", "Error submitting composed tweet");
                }
            }
        });
    }
}
