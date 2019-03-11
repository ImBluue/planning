package com.example.planning;

import android.net.Uri;
import android.util.Log;

import com.example.planning.Model.Cursus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EventNetworkUtils {
    private static final String TAG =
            EventNetworkUtils.class.getSimpleName();

    // Base URL for Books API.
    private static final String EVENT_BASE_URL =  "http://api.valentin-baud.fr/planning/?";



    static String getEventsInfo(Cursus cursus){
        String eventJSONString = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            Uri builtURI = Uri.parse(EVENT_BASE_URL).buildUpon()
                    .appendQueryParameter("campus", cursus.getCampus())
                    .appendQueryParameter("school", cursus.getSchool())
                    .appendQueryParameter("department", cursus.getDepartment())
                    .appendQueryParameter("training", cursus.getTraining())
                    .appendQueryParameter("group", cursus.getGroup())
                    .build();
            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();

// Create a buffered reader from that input stream.
            reader = new BufferedReader(new InputStreamReader(inputStream));

// Use a StringBuilder to hold the incoming response.
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                // Since it's JSON, adding a newline isn't necessary (it won't
                // affect parsing) but it does make debugging a *lot* easier
                // if you print out the completed buffer for debugging.
                builder.append("\n");
            }
            if (builder.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }
            eventJSONString = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(TAG, eventJSONString);
        return eventJSONString;
    }




}
