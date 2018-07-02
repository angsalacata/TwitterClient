package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {

    //list the attributes we want to store, look the the endpoint docs for what these exactly are and how to find them
    public String body;
    public Long uiID;
    public String createdAt;

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
        //also could have done another way where we parse it more in the user class
    return tweet;
    }

}
