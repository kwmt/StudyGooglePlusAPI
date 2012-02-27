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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.R;
import com.google.api.services.plus.model.Activity;

import java.util.List;

/**
 * Creates an ArrayAdapter that displays your activity stream
 *
 * @author Chirag Shah
 */
public class ActivityTestArrayAdapter extends ArrayAdapter<String> {
  public ActivityTestArrayAdapter(Context context, List<String> people) {
    super(context, android.R.layout.simple_spinner_item, people);
    setDropDownViewResource(R.layout.activity_list);
  }
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    return getDropDownView(position, convertView, parent);
  }

  @Override
  public View getDropDownView(int position, View view, ViewGroup parent) {
    if (view == null) {
      final LayoutInflater inflater = (LayoutInflater) getContext()
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      view = inflater.inflate(R.layout.activity_list, parent, false);
    }

    return view;
  }


}