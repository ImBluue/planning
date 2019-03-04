package com.example.edt;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private String TAG = MainActivity.class.getSimpleName();
    private ArrayList<Event> mEventData;
    private EventListAdapter mAdapter;
    private EventViewModel mEventViewModel;
    private Observer obs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList that will contain the data.
        mEventData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new EventListAdapter(this, mEventData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        if(isOnline()) {
            mEventViewModel.deleteall();
            Toast.makeText(getApplicationContext(),
                    "Updating...",
                    Toast.LENGTH_LONG).show();

        }else
        {
            Toast.makeText(getApplicationContext(),
                    "No internet connection, loaded the last version",
                    Toast.LENGTH_LONG).show();
        }
        mEventViewModel.getAllEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable final List<Event> events) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setEvents(events);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.update) {
            if(isOnline()) {
                // Add a toast just for confirmation
                Toast.makeText(this, "Updating...",
                        Toast.LENGTH_SHORT).show();

                new GetAllEvents().execute();
            }else
                Toast.makeText(this, "No internet connection",
                        Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }

    public static ArrayList<Event> GetDataHttp(){
        ArrayList<Event> listEvents = new ArrayList<>();
        String TAG = MainActivity.class.getSimpleName();
        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response
        String url = "http://edt.valentin-baud.fr/api/";
        String jsonStr = sh.makeServiceCall(url);
        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray chars = jsonObj.getJSONArray("events");

                // looping through All Contacts
                for (int i = 0; i < chars.length(); i++) {
                    JSONObject c = chars.getJSONObject(i);
                    // adding contact to contact list
                    listEvents.add(new Event(i,c.getString("description"), c.getString("summary"), c.getString("group"), c.getString("location"), c.getString("end"), c.getString("start"), c.getString("day")));
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());


            }

        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
        return listEvents;
    }

    private class GetAllEvents extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            mEventData = GetDataHttp();
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            mEventViewModel.deleteall();
            for (Event event:mEventData) {
                mEventViewModel.insert(event);
            }

        }
    }
}
