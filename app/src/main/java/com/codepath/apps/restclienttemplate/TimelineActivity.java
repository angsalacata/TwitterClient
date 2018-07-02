package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    public final String TAG = "TwitterClient";
    private TwitterClient client;
    ArrayList<Tweet> tweets;
    TweetAdapter tweetAdapter;
    RecyclerView rvViewTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApp.getRestClient(this);
        //initialize tweet array list
        tweets = new ArrayList<>();
        //initialize tweetAdapter
        tweetAdapter = new TweetAdapter(tweets);
        //find Recycler view in XML
        rvViewTweets = (RecyclerView) findViewById(R.id.rvTweet);
        //setup rvViewTweet's layout manager
        rvViewTweets.setLayoutManager(new LinearLayoutManager(this));
        rvViewTweets.setAdapter(tweetAdapter);
        populateTimeline();
    }


    public void populateTimeline(){
    client.getHomeTimeline(new JsonHttpResponseHandler(){
        //we will use the models to populate a tweet with data from the api
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            Log.d(TAG, response.toString());
        }
        //will likely use this one, as the endpoint json is an array
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

            Log.d(TAG, response.toString());
            //iterate through the JSON array and deserialize aka parse by index
            for(int i = 0; i < response.length(); i++){
                try {
                    Tweet tweet = Tweet.fromJSONObject(response.getJSONObject(i));// this is like a constructor, but since we didn't have a constructor you can use this anonymous Tweet.fromJSONObject
                    tweets.add(tweet);
                    tweetAdapter.notifyItemInserted(tweets.size() - 1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
            Log.d(TAG, errorResponse.toString());
            //prints the exception
            throwable.printStackTrace();
        }
         @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
             Log.d(TAG, responseString);
             //prints the exception
             throwable.printStackTrace();
        }
        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Log.d(TAG, errorResponse.toString());
            //prints the exception
            throwable.printStackTrace();
        }

    });

    }
}
