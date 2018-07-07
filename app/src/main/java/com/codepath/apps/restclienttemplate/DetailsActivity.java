package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class DetailsActivity extends AppCompatActivity {

TextView txtvUserhandle;
TextView txtvUserName;
TextView txtvDetailBody;
Button buttonLike;
Button buttonRetweet;
ImageView imvProfilePicture;
Long tweetId;
Boolean rt;
TwitterClient clientLikeRetweet;
String detailsImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setTitle("Details");

        txtvUserhandle = (TextView) findViewById(R.id.txtvDetailsUserhandle);
        txtvDetailBody = (TextView) findViewById(R.id.txtvDetailBody);
        txtvUserName = (TextView) findViewById(R.id.txtvDetailName);
        imvProfilePicture = (ImageView) findViewById(R.id.imvProfilePicture);
        buttonLike = (Button) findViewById(R.id.likeButton);
        buttonRetweet = (Button) findViewById(R.id.retweetButton);

        final Tweet receivedTweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));

        detailsImageUrl = receivedTweet.user.profileURL;
        txtvUserhandle.setText("@"+receivedTweet.user.screenName);
        txtvDetailBody.setText(receivedTweet.body);
        txtvUserName.setText(receivedTweet.user.name);

        clientLikeRetweet = TwitterApp.getRestClient(this);

        tweetId = receivedTweet.uiID;
        rt = receivedTweet.retweeted;


        GlideApp.with(this)
                .load(detailsImageUrl)
                .into(imvProfilePicture);

        //implement the like button
        buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clientLikeRetweet.favoriteTweet(tweetId, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess (int statusCode, Header[] headers, JSONObject response){
                        Log.d("DetailsActivity", "favorited tweet"); }

                    @Override
                    public void onSuccess (int statusCode, Header[] headers, JSONArray response){
                        Log.d("DetailsActivity", "favorited tweet"); }

                   @Override
                    public void onFailure (int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse){
                       Log.d("DetailsActivity", "DID NOT favorite tweet");
                       throwable.printStackTrace();}
                    @Override
                    public void onFailure (int statusCode, Header[] headers, String responseString, Throwable throwable){
                        Log.d("DetailsActivity", "DID NOT favorite tweet");
                        throwable.printStackTrace();}

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                        Log.d("DetailsActivity", "DID NOT favorite tweet");
                        throwable.printStackTrace();}

                });

            }
        });


    if(receivedTweet.favorited){
        buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientLikeRetweet.unFavoriteTweet(tweetId, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess (int statusCode, Header[] headers, JSONObject response){
                        Log.d("DetailsActivity", "unfavorited tweet"); }

                    @Override
                    public void onSuccess (int statusCode, Header[] headers, JSONArray response){
                        Log.d("DetailsActivity", "unfavorited tweet"); }

                    @Override
                    public void onFailure (int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse){
                        Log.d("DetailsActivity", "DID NOT unfavorite tweet");
                        throwable.printStackTrace();}
                    @Override
                    public void onFailure (int statusCode, Header[] headers, String responseString, Throwable throwable){
                        Log.d("DetailsActivity", "DID NOT unfavorite tweet");
                        throwable.printStackTrace();}

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                        Log.d("DetailsActivity", "DID NOT unfavorite tweet");
                        throwable.printStackTrace();}
                });
            }
        });
    }

    if (!receivedTweet.retweeted){
        buttonRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientLikeRetweet.retweetTweet(tweetId, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess (int statusCode, Header[] headers, JSONObject response){
                        Log.d("DetailsActivity", "retweeted tweet"); }

                    @Override
                    public void onSuccess (int statusCode, Header[] headers, JSONArray response){
                        Log.d("DetailsActivity", "retweeted tweet"); }

                    @Override
                    public void onFailure (int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse){
                        Log.d("DetailsActivity", "DID NOT retweet tweet");
                        throwable.printStackTrace();}
                    @Override
                    public void onFailure (int statusCode, Header[] headers, String responseString, Throwable throwable){
                        Log.d("DetailsActivity", "DID NOT retweet tweet");
                        throwable.printStackTrace();}

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                        Log.d("DetailsActivity", "DID NOT retweet tweet");
                        throwable.printStackTrace();}
                });
            }
        });
    }

    }
}
