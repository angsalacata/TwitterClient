package com.codepath.apps.restclienttemplate;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class TwitterClient extends OAuthBaseClient {
	public static final BaseApi REST_API_INSTANCE = TwitterApi.instance(); // Change this, erased the .write method
	public static final String REST_URL = "https://api.twitter.com/1.1/"; // Base twitter API url
	public static final String REST_CONSUMER_KEY = "MGL56wj2LHRwhzhUeiY24hnui";       // API key
	public static final String REST_CONSUMER_SECRET = "ImIiZHrgHLz7YHgyp6WPaCPzA5P1lOmJ495bU8I9d48X42Bdyw";

	// Landing page to indicate the OAuth flow worked in case Chrome for Android 25+ blocks navigation back to the app.
	public static final String FALLBACK_URL = "https://codepath.github.io/android-rest-client-template/success.html";

	// See https://developer.chrome.com/multidevice/android/intents
	public static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

	public TwitterClient(Context context) {
		super(context, REST_API_INSTANCE,
				REST_URL,
				REST_CONSUMER_KEY,
				REST_CONSUMER_SECRET,
				String.format(REST_CALLBACK_URL_TEMPLATE, context.getString(R.string.intent_host),
						context.getString(R.string.intent_scheme), context.getPackageName(), FALLBACK_URL));
	}
	// this method will get to statuses/home_timeline endpoint from twitter API
	public void getHomeTimeline(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		//you can view the parameters for this endpoint under the endpoint page in the api
		//determine how many tweets should be retrieved, 25 by default
		params.put("count", 25);
		//this will retrieve basically all the tweets until the oldest
		params.put("since_id", 1);
		//we have this client from the extends OAuthBaseClient
		client.get(apiUrl, params, handler);
		//this will make the request, now must populate the data in TimelineActivity
	}

	//this method will get to the statuses/update endpoint from twitter API
	public void sendNewTweet(String message, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/update.json");
		//can specify the query string params directly of with request params

		RequestParams params = new RequestParams();
		params.put("status", message);
		client.post(apiUrl, params, handler);
	}

	public void favoriteTweet(Long idNum, AsyncHttpResponseHandler handler){
	    String apiUrl = getApiUrl("favorites/create.json");
	    RequestParams params = new RequestParams();
	    params.put("id", idNum);
	    client.post(apiUrl, params, handler);
    }

    public void unFavoriteTweet(Long idNum, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("favorites/destroy.json");
		RequestParams params = new RequestParams();
		params.put("id", idNum);
		client.post(apiUrl, params, handler);
	}

	public void retweetTweet(Long idNum, AsyncHttpResponseHandler handler){
	    String apiUrl = getApiUrl("statuses/retweet/"+idNum+".json");
	    RequestParams params = new RequestParams();
	    params.put("id", idNum);
	    client.post(apiUrl, params, handler);
    }


    public String getApiUrl(String endpoint_path){
		return REST_URL + endpoint_path;
	}


}
