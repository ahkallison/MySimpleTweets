package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by ahkallison on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

//    // a numeric code to identify the edit activity
    static final int EDIT_REQUEST_CODE = 20;
    static final int REQUEST_CODE_REPLY = 30;


    public interface TweetSelectedListener {
        // handle tweet selection
        public void onTweetSelected(Tweet tweet);
    }

    // TODO make this public??
    private List<Tweet> mTweets;
    Context context;
    private TweetAdapterListener mListener;

    // define an interface required by the ViewHolder
    public interface TweetAdapterListener {
        public void onItemSelected(View view, int position);
    }

    // pass in the Tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets, TweetAdapterListener listener) {
        mTweets = tweets;
        this.mListener = listener;
    }

    // for each row, inflate the layout and cache references into ViewHolder

    @Override
    public TweetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }


    // bind the values based on the position of the element

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the data acccording to position
        Tweet tweet = mTweets.get(position);

        // populate the views according to this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvTimeStamp.setText(" · " + TimeFormatter.getTimeDifference(tweet.createdAt));
        holder.tvScreenName.setText(" @" + tweet.user.screenName);
        if (tweet.favorited) {
            holder.btnFavorited.setImageResource(R.drawable.ic_vector_heart);
        } else {
            holder.btnFavorited.setImageResource(R.drawable.ic_vector_heart_stroke);
        }
        if (tweet.retweeted) {
            holder.btnRetweeted.setImageResource(R.drawable.ic_vector_retweet);
        } else {
            holder.btnRetweeted.setImageResource(R.drawable.ic_vector_retweet_stroke);
        }

        holder.tvRetweets.setText(String.valueOf(tweet.retweetCount));
        holder.tvLikes.setText(String.valueOf(tweet.favoriteCount));
        // TODO TEMPORARY
        holder.tvReplies.setText("");

        Glide.with(context)
                .load(tweet.user.profileImageUrl)
                .bitmapTransform(new RoundedCornersTransformation(context, 150, 0))
                .into(holder.ivProfileImage);

        Glide.with(context)
                .load(tweet.media_url_https)
                .bitmapTransform(new RoundedCornersTransformation(context, 20, 0))
                .into(holder.ivMedia);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvTimeStamp;
        public TextView tvScreenName;
        public ImageButton btnFavorited;
        public ImageButton btnRetweeted;
        public ImageButton btnReply;
        public TextView tvReplies;
        public TextView tvRetweets;
        public TextView tvLikes;
        public ImageView ivMedia;

        public ViewHolder(View itemView) {
            super(itemView);

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTimeStamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);
            tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenName);
            btnFavorited = (ImageButton) itemView.findViewById(R.id.btnFavorited);
            btnRetweeted = (ImageButton) itemView.findViewById(R.id.btnRetweeted);
            btnReply = (ImageButton) itemView.findViewById(R.id.btnReply);
            tvReplies = (TextView) itemView.findViewById(R.id.tvReplies);
            tvRetweets = (TextView) itemView.findViewById(R.id.tvRetweets);
            tvLikes = (TextView) itemView.findViewById(R.id.tvLikes);
            ivMedia = (ImageView) itemView.findViewById(R.id.ivMedia);

            // handle row click event
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        // get the position of the row element
                        int position = getAdapterPosition();

                        // fire the listener callback
                        mListener.onItemSelected(view, position);
                    }
                }
            });


            itemView.setOnClickListener(this);


            // on click listener for profile image
            ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // gets item position
                    final int position = getAdapterPosition();

                    // make sure the position is valid
                    if (position != RecyclerView.NO_POSITION) {
                        Tweet tweet = mTweets.get(position);

                        Intent i = new Intent(context, OtherProfileActivity.class);

                        // serialize the movie using parceler, use its short name as a key
                        i.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                        i.putExtra("screen_name", tweet.user.screenName);

                        context.startActivity(i);
                    }
                }
            });

            btnReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tweet tweet = mTweets.get(getAdapterPosition());
                    Intent i = new Intent(context, ReplyActivity.class);
                    i.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                    ((Activity) context).startActivityForResult(i, REQUEST_CODE_REPLY);
                }
            });


            btnFavorited.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                // gets item position
                final int position = getAdapterPosition();

                // make sure the position is valid
                if (position != RecyclerView.NO_POSITION) {
                    Tweet tweet = mTweets.get(position);

                    // deal with favorites
                    if(tweet.favorited) {
//                        Toast.makeText(context, "Unfavorite!", Toast.LENGTH_SHORT).show();
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
                                tvLikes.setText(String.valueOf(tweet.favoriteCount));
                            }
                        });
                    } else {
//                        Toast.makeText(context, "Favorite!", Toast.LENGTH_SHORT).show();
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
                                tvLikes.setText(String.valueOf(tweet.favoriteCount));
                            }
                        });
                    }

                    // create intent for the new activity
                    Intent intent = new Intent(context, TweetDetailsActivity.class);
                    // serialize the movie using parceler, use its short name as a key
                    intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                    intent.putExtra("POSITION", position);
                    // TODO
//                    ((AppCompatActivity)context).startActivityForResult(intent, EDIT_REQUEST_CODE);
                }
                }
            });

            btnRetweeted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // gets item position
                    final int position = getAdapterPosition();

                    // make sure the position is valid
                    if (position != RecyclerView.NO_POSITION) {
                        Tweet tweet = mTweets.get(position);

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
                                    tvRetweets.setText(String.valueOf(tweet.retweetCount - 1));
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
                                    tvRetweets.setText(String.valueOf(tweet.retweetCount));
                                }
                            });
                        }

                        // create intent for the new activity
                        Intent intent = new Intent(context, TweetDetailsActivity.class);
                        // serialize the movie using parceler, use its short name as a key
                        intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                        intent.putExtra("POSITION", position);
                        // TODO
//                        ((AppCompatActivity)context).startActivityForResult(intent, EDIT_REQUEST_CODE);

                    }
                }
            });

        }



        @Override
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();

            // make sure the position is valid
            if (position != RecyclerView.NO_POSITION) {
                Tweet tweet = mTweets.get(position);

                // create intent for the new activity
                Intent intent = new Intent(context, TweetDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                // show activity
                context.startActivity(intent);
            }
        }
    }



    // clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }

}
