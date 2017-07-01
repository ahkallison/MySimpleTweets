package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ReplyActivity extends AppCompatActivity {

//    TextView tvReplyingTo;
//    EditText etReply;
    TextView tvCount;
    Tweet tweet;

    // resolve the view objects
    @BindView(R.id.tvReplyingTo) TextView tvReplyingTo;
    @BindView(R.id.etReply) EditText etReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        // applying Butterknife
        ButterKnife.bind(this);

//        tvReplyingTo = (TextView) findViewById(R.id.tvReplyingTo);
//        etReply = (EditText) findViewById(R.id.etReply);

//        final TextWatcher txwatcher = new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                tvCount.setText(String.valueOf(140 - s.length()));
//            }
//            public void afterTextChanged(Editable s) {
//            }
//        };
//        etReply.addTextChangedListener(txwatcher);

        // unwrap the movie passed in via intent, using its simple name as a key
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));

        tvReplyingTo.setText("Replying to @" + tweet.user.screenName);
        etReply.setText("@" + tweet.user.screenName);

    }

    public void onSubmit(View view) {
        TwitterApplication.getRestClient().replyTweet(etReply.getText().toString(), tweet.uid, new JsonHttpResponseHandler() {
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
                    finish();

                } catch (JSONException e) {
                    // log the error
                    Log.e("ReplyActivity", "Error submitting reply tweet");
                }
            }
        });
    }

}
