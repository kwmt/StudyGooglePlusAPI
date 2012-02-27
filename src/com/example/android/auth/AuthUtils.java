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

package com.example.android.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import com.google.api.client.googleapis.extensions.android2.auth.GoogleAccountManager;

/**
 * A simple collection of authorization utilities
 *
 * @author Chirag Shah
 */
public class AuthUtils {
  private static final String TAG = AuthUtils.class.getName();

  public static final String PREFS_NAME = "Google+ Android Example";
  public static final String PREF_ACCOUNT_NAME = "accountName";
  public static final String PREF_TOKEN = "accessToken";

  // This scope lets you use the user id 'me' in API requests.
  // The Android AccountManager has experimental support for OAuth 2.0. You simply need to
  // add oauth2:https://www.googleapis.com/auth/plus.me when setting the AUTH_TOKEN_TYPE.
  // This isn't ideal because that string will be displayed in the authorization dialog.
  // For this reason, you can use the human-readable alias assigned below instead.
  // For more information, see http://code.google.com/p/google-api-java-client/wiki/Android
  public static final String PLUS_ME_SCOPE = "View your Google+ id\nView your Public Google+ data";

  public static void refreshAuthToken(final Context context) {
    final SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
    final String accessToken = settings.getString(PREF_TOKEN, "");
    final String accountName = settings.getString(PREF_ACCOUNT_NAME, "");
    final GoogleAccountManager manager = new GoogleAccountManager(context);
    final Account account = manager.getAccountByName(accountName);
    final Intent callback = new Intent(context, context.getClass());

    if (null == account) {
      Intent accountListIntent = new Intent(context, AccountListActivity.class);
      accountListIntent.putExtra("callback", callback);
      context.startActivity(accountListIntent);
    } else {
      final AccountManagerCallback<Bundle> cb = new AccountManagerCallback<Bundle>() {
        public void run(AccountManagerFuture<Bundle> future) {
          try {
            final Bundle result = future.getResult();
            final String accountName = result.getString(AccountManager.KEY_ACCOUNT_NAME);
            final String authToken = result.getString(AccountManager.KEY_AUTHTOKEN);
            final Intent authIntent = result.getParcelable(AccountManager.KEY_INTENT);

            if (accountName != null && authToken != null) {
              final SharedPreferences.Editor editor = settings.edit();
              editor.putString(PREF_TOKEN, authToken);
              editor.commit();
              callback.putExtra("token", authToken);
              context.startActivity(callback);
            } else if (authIntent != null) {
              context.startActivity(authIntent);
            } else {
              Log.e(TAG, "AccountManager was unable to obtain an authToken.");
            }
          } catch (Exception e) {
            Log.e(TAG, "Auth Error", e);
          }
        }
      };
      manager.invalidateAuthToken(accessToken);
      AccountManager.get(context).getAuthToken(account, PLUS_ME_SCOPE, true, cb, null);
    }
  }
}
