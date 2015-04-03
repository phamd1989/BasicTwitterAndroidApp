package com.codepath.apps.basictwitter.fragment;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;

import android.os.Bundle;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment{
	
	private String screenName;
	
	public static UserTimelineFragment newInstance(String screenName) {
		UserTimelineFragment fragmentUserTimeline = new UserTimelineFragment();
		Bundle args = new Bundle();
		args.putString("screen_name", screenName);
		fragmentUserTimeline.setArguments(args);
		return fragmentUserTimeline;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		ArrayList<Tweet> myTweets = new ArrayList<Tweet>();
//		aTweets = new TweetArrayAdapter(getActivity(), myTweets);
		screenName = getArguments().getString("screen_name");
		
		if (screenName != null && !Tweet.getTweetsForScreenName(screenName).isEmpty()) {
			Toast.makeText(getActivity(), "LOADING FROM DATABASE", Toast.LENGTH_LONG).show();
			addAll(Tweet.getTweetsForScreenName(screenName));
		}
	}
	
	@Override
	public void populateTimeline(String screen_name) {
		long maxId = 0;
		if (!aTweets.isEmpty()) {
			maxId = aTweets.getItem(aTweets.getCount() - 1).getUid() - 1;
		}
		client.getUserTimeline(screenName, maxId, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
				addAll(Tweet.fromJsonArray(response));
			}

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				Log.d("debug", throwable.toString());
			}
		});
	}
	
//	@Override
//	public EndlessScrollListener populateEndlessScrollListener(final String screen_name) {
//		return new EndlessScrollListener() {
//				@Override
//				public void onLoadMore(int totalItemsCount) {
//					populateTimeline(screen_name);
//				}
//			};
//	}
}
