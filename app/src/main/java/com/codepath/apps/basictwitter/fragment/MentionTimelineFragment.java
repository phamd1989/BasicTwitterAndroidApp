package com.codepath.apps.basictwitter.fragment;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;

import com.activeandroid.util.Log;
import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.widget.Toast;

public class MentionTimelineFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (!Tweet.getMentionedTweets().isEmpty()) {
			Toast.makeText(getActivity(), "LOADING FROM DATABASE", Toast.LENGTH_LONG).show();
			addAll(Tweet.getMentionedTweets());
		}
	}
	
	@Override
	public void populateTimeline(String s) {
		long maxId = 0;
		if (!aTweets.isEmpty()) {
			maxId = aTweets.getItem(aTweets.getCount() - 1).getUid() - 1;
		}
		
		client.getMentionTimelineList(maxId, new JsonHttpResponseHandler() {

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
//	public EndlessScrollListener populateEndlessScrollListener(String screen_name) {
//		return new EndlessScrollListener() {
//				@Override
//				public void onLoadMore(int totalItemsCount) {
//					populateTimeline(null);
//				}
//			};
//	}
}

