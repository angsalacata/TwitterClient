package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
@Parcel
public class Tweet {

    //list the attributes we want to store, look the the endpoint docs for what these exactly are and how to find them
    public String body;
    public Long uiID;
    public String createdAt;
    public String timeStamp;

    //will also use a custom user class, from other model
    public User user;
//deserialize object, aka, parse
    //with Tweet method
    public static Tweet fromJSONObject(JSONObject jsonObject) throws JSONException {// this object will i believe be the index in the array
        Tweet tweet = new Tweet();
//extract values from JSON object that was passed in, look up the keys in the api docs
        tweet.body = jsonObject.getString("text");
        tweet.uiID = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.timeStamp = getRelativeTimeAgo(tweet.createdAt);
        //also could have done another way where we parse it more in the user class
    return tweet;
    }


    static public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

}
