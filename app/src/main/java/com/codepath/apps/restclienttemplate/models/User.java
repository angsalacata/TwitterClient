package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    //list user attributes
    public String name; //user name
    public Long uiID;
    public String screenName; //twitter @handle
    public String profileURL;

    //deserialize the JSONobject called user that is INSIDE the JSONobject that is passed in from tweet
    public static User fromJSON(JSONObject jsonObject) throws JSONException {
        //this will get the user JSONobject that is inside the JSONobject passed i
       User user = new User();
       user.name = jsonObject.getString("name");
        user.uiID = jsonObject.getLong("id");
        user.screenName = jsonObject.getString("screen_name");
        user.profileURL = jsonObject.getString("profile_image_url");

        return user;
    }

}
