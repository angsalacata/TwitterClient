package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {


    EditText composeText;
    Button sendTweetButton;
    TwitterClient twitterClient;
    Long replyId;
    String replyName;
    public static String TAG = "ComposeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        composeText = (EditText) findViewById(R.id.txtvTwitterBody);
        sendTweetButton = (Button) findViewById(R.id.tweetButton);
        twitterClient = TwitterApp.getRestClient(this);


        Intent receivedIntent = getIntent();
      replyId = receivedIntent.getLongExtra("ID", 0);
      replyName = receivedIntent.getStringExtra("username");


        getSupportActionBar().setTitle("Compose a Tweet");

        //send the tweet
        //ask for permission to send the new tweet
        sendTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the text that the user types in
                 String tweetBody = composeText.getText().toString();
                if (replyId != 0){
                    tweetBody = "@" + replyName+ " " + composeText.getText().toString(); }

            Log.d(TAG,"tapped");
                twitterClient.sendNewTweet(tweetBody, new JsonHttpResponseHandler(){
                   @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                        Log.d(TAG, "Request to send new tweet successful");
                       Tweet sendbackTweet = null;
                       try {
                            sendbackTweet = Tweet.fromJSONObject(response);
                           Intent i = new Intent();
                           i.putExtra("Tweet", Parcels.wrap(sendbackTweet));
                          setResult(RESULT_OK,i);
                          finish(); }

                          catch (JSONException e) {
                           e.printStackTrace(); }
                   }
                   @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Log.d(TAG, response.toString());}

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                       Log.d(TAG, errorResponse.toString());
                       //prints the exception
                        throwable.printStackTrace(); }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
                        Log.d(TAG, "Request to send new tweet unsuccessful"); }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d(TAG, errorResponse.toString());
                        throwable.printStackTrace(); }
                });

            }
        });
    }
}