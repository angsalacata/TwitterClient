package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    public final String TAG = "TimelineActivityTAG";
    private TwitterClient client;
    ArrayList<Tweet> tweets;
    TweetAdapter tweetAdapter;
    RecyclerView rvViewTweets;
    MenuItem actionProgressSwirl;

    //field to take care of the object to refresh Tweet list
    private SwipeRefreshLayout swipeRefreshLayout;

    //this will CREATE the composetweet option on the right hand side of the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.composetweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //handle the pressing on the action bar item
        //we inflated the menu item selection in the above method onCreateOptions menu
        switch (item.getItemId()) {
            case R.id.miCompose:
                Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
              //  i.putExtra()
                startActivityForResult(i, REQUEST_CODE);
                return true;

            default:
                return super.onOptionsItemSelected(item); }
    }

     //method to prepare the progress bar
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        //Store instance of the menu item containing progress bar
        actionProgressSwirl = menu.findItem(R.id.miActionProgress);
        populateTimeline();
        return super.onPrepareOptionsMenu(menu); }

    public void showProgressBar()   {
        // Show progress item
        actionProgressSwirl.setVisible(true); }

    public void hideProgressBar() {
        // Hide progress item
        actionProgressSwirl.setVisible(false); }


//generally, this method is called everytime this timeline activity is brought up
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


        //swiping for refresh, inflates view by looking at SwipeRefreshLayout in the activity_timeline
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //code needed to make refresh
                swipeRefreshLayout.setRefreshing(false);
                fetchTimelineAsync(0);
                }});

    }


    public void populateTimeline(){
        //pretend to load tweets
        showProgressBar();
    client.getHomeTimeline(new JsonHttpResponseHandler(){

        //we will use the models to populate a tweet with data from the api
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            Log.d(TAG, response.toString());
        }
        //will likely use this one, as the endpoint json is an array
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d(TAG, response.toString());
            for(int i = 0; i < response.length(); i++){
                try {
                    Tweet tweet = Tweet.fromJSONObject(response.getJSONObject(i));
                    tweets.add(tweet);
                    tweetAdapter.notifyItemInserted(tweets.size() - 1);

                } catch (JSONException e) {
                    e.printStackTrace(); }
                    }
                    hideProgressBar();
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

        @Override
        public void onFinish(){
            hideProgressBar();
        }
        });
    }
    //handle the result from the compose tweet activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //unwrap the parcel from the compose activity
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Tweet returnedTweet = (Tweet) Parcels.unwrap(data.getParcelableExtra(Tweet.class.getSimpleName()));
            tweets.add(0, returnedTweet);
            tweetAdapter.notifyItemInserted(0);
            rvViewTweets.scrollToPosition(0);
        }

    }

    //method needed for requesting to fetch updated data. Client that is here is instance of Async HTTP.
    public void fetchTimelineAsync (int page){
        //clear what the tweet array already had
        tweetAdapter.clear();
        //put the new data in the tweets Array
               populateTimeline();
               //put this all in the tweetAdapter, will notify the adapter that the data set has changed!
            tweetAdapter.addAll(tweets);
            //after all of this, do not refresh forever
                swipeRefreshLayout.setRefreshing(false); }

}

