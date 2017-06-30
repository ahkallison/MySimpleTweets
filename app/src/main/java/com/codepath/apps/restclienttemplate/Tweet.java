package com.codepath.apps.restclienttemplate;

import org.json.JSONArray;
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
    public Boolean favorited;
    public Boolean retweeted;
    public String media_url_https;
    public String type;

    // deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        try {
            tweet.favoriteCount = jsonObject.getInt("favorite_count");
        } catch (JSONException e) {
            tweet.favoriteCount = 0;
        }
        try {
            tweet.retweetCount = jsonObject.getInt("retweet_count");
        } catch (JSONException e) {
            tweet.retweetCount = 0;
        }
        tweet.favorited = jsonObject.getBoolean("favorited");
        tweet.retweeted = jsonObject.getBoolean("retweeted");
        JSONArray mediaArray = jsonObject.getJSONObject("entities").getJSONArray("media");
        try {
            tweet.media_url_https = mediaArray.getJSONObject(0).getString("media_url_https");
        } catch (JSONException e) {
            tweet.media_url_https = "";
        }
        try {
            tweet.type = mediaArray.getJSONObject(0).getString("type");
        } catch (JSONException e) {
            tweet.type = "";
        }


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
