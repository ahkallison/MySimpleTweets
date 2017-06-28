package com.codepath.apps.restclienttemplate;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by ahkallison on 6/26/17.
 */

@Parcel
public class Tweet {

    // list out the attributes
    public String body;
    public long uid; // database ID for the tweet
    public User user;
    public String createdAt;
    public Integer favoriteCount;
    public Integer retweetCount;

    // deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
//        tweet.favoriteCount = jsonObject.getInt("favourites_count");
//        tweet.retweetCount = jsonObject.getInt("retweet_count");
        return tweet;
    }
//
//    public String getBody() { return body; }
//
//    public long getUid() { return uid; }
//
//    public User getUser() { return user; }
//
//    public String getCreatedAt() { return createdAt; }
}
