package com.codepath.apps.simpletweets.network;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "qozxF0QyFS5ivAPiEvWGm98d1";       // Change this
	public static final String REST_CONSUMER_SECRET = "pa0HhVeAT7pY8uwjP9dEIU6brURSaNY9yY0XcX9mAfZ3pVXqh4"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}


	/**
	 * Get Home Timeline
	 * @param handler
	 */
	public void getHomeTimeline(AsyncHttpResponseHandler handler, long maxId){
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Specify query string
		RequestParams params = new RequestParams();
		params.put("count", 25);
		if(maxId == 0){
			params.put("since_id", 1);
		}else {
			params.put("max_id", maxId);
		}

		getClient().get(apiUrl, params, handler);

		//Log.d("DEBUG", "AccessToken:" + getClient().getAccessToken().toString());
		//Log.d("DEBUG", "Raw AccessToken:" + getClient().getAccessToken().getRawResponse());
		//Log.d("DEBUG", "Time:" + new Date().getTime());
	}

	/**
	 * Post Tweet
	 * @param handler
	 */
	public void postTweet(AsyncHttpResponseHandler handler, String status){
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", status);

		getClient().post(apiUrl, params, handler);
	}

	public void getTweet(AsyncHttpResponseHandler handler, long tweetId){
		String apiUrl = getApiUrl("statuses/show.json");
		RequestParams params = new RequestParams();
		params.put("id", tweetId);

		getClient().get(apiUrl, params, handler);
	}


	/**
	 * Get Logged in User Profile Info
	 * @param handler
	 */
	public void getProfileInfo(AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("account/verify_credentials.json");
		RequestParams params = new RequestParams();

		getClient().get(apiUrl, params, handler);
	}


	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}