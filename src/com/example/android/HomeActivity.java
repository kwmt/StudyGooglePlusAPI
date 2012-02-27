/*
 * Copyright 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.TwitterException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.android.auth.AuthUtils;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Activity;

/**
 * Creates a tab for each Google+ activity
 * 
 * @author Chirag Shah
 */
public class HomeActivity extends android.app.Activity {
	public static final String TAG = HomeActivity.class.getName();
	private ListView mListView;
	private ListView mTweetListView;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Intent intent = getIntent();
		if (intent == null || !intent.hasExtra("token")) {
			AuthUtils.refreshAuthToken(this);
			return;
		}

	setContentView(R.layout.activity_list);

		mContext = getApplicationContext();

		// Twitter
		mTweetListView = (ListView) findViewById(R.id.tweetList);
		AsyncTask<String, Void, List<Status>> tweettask = new AsyncTask<String, Void, List<Status>>() {
			@Override
			protected List<twitter4j.Status> doInBackground(String... params) {
				List<twitter4j.Status> status;
				try {
					status = TwitterHelper.getStatuses();
					return status;

				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(List<twitter4j.Status> feed) {
				if (feed != null) {
					mTweetListView.setAdapter(new ActivityTweetArrayAdapter(mContext, feed));
					//mTweetListView.setBackgroundColor(Color.BLACK);

					for (int i = 0; i < feed.size(); i++) {
						Log.d(TAG, feed.get(i).getText());
					}
				}
			}
		};
		tweettask.execute("");

		// Google+
		mListView = (ListView) findViewById(R.id.activityList);

		AsyncTask<String, Void, List<Activity>> task = new AsyncTask<String, Void, List<Activity>>() {
			@Override
			protected List<Activity> doInBackground(String... params) {
				try {
					Plus plus = new PlusWrap(HomeActivity.this).get();
					List<Activity> listActivity = plus.activities().list("me", "public").execute().getItems();
					return listActivity;
				} catch (IOException e) {
					Log.e(TAG, "Unable to list recommended people for user: " + params[0], e);
				}
				return null;
			}

			@Override
			protected void onPostExecute(List<Activity> feed) {
				if (feed != null) {
					mListView.setAdapter(new ActivityArrayAdapter(mContext, feed));
				}
			}
		};
		task.execute("me");

		Plus plus = new PlusWrap(HomeActivity.this).get();
		Activity activity;
		String activityID = "z12gtjhq3qn2xxl2o224exwiqruvtda0i";
		try {
			activity = plus.activities().get(activityID).execute();
			Log.d(TAG,"Used get to fetch activity with ID " + activity.getId());
			Log.d(TAG," Activity content:\t" + activity.getPlusObject().getContent());
			Log.d(TAG," +1's: \t" + activity.getPlusObject().getPlusoners().getTotalItems());
			Log.d(TAG," Reply comments: \t" + activity.getPlusObject().getReplies().getTotalItems());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		//		AsyncTask<String, Void, List<String>> testtask = new AsyncTask<String, Void, List<String>>() {
		//			@Override
		//			protected List<String> doInBackground(String... params) {
		//				List<String> mlist = new ArrayList<String>();
		//				mlist.add("aaa");
		//				mlist.add("bbb");
		//				mlist.add("ccc");
		//				
		//				return mlist;
		//			}
		//
		//			@Override
		//			protected void onPostExecute(List<String> feed) {
		//				if (feed != null) {
		//					mTweetListView.setAdapter(new ActivityTestArrayAdapter(mContext, feed));
		//					//mTweetListView.setBackgroundColor(Color.BLACK);
		//					
		//					for(int i=0; i < feed.size(); i++){
		//						Log.d(TAG,feed.get(i));
		//					}
		//				}
		//			}
		//		};
		//		testtask.execute("me");


	}
}
