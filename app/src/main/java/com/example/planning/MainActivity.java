package com.example.planning;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
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
        mEventViewModel.deleteall();
        mEventViewModel.getAllEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable final List<Event> events) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setEvents(events);
            }
        });
        Toast.makeText(getApplicationContext(),
                "Updating...",
                Toast.LENGTH_LONG).show();
        start("ANNECY", "IUT", "INFO", "INFO2S4", "G22");

    }

    public void start(String campus, String school, String department, String training, String group){
        Bundle queryBundle = new Bundle();
        queryBundle.putString("campus", campus);
        queryBundle.putString("school", school);
        queryBundle.putString("department", department);
        queryBundle.putString("training", training);
        queryBundle.putString("group", group);


        getSupportLoaderManager().restartLoader(0, queryBundle, this);
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
         String campus = "";
         String school = "";
         String department = "";
         String training = "";
         String group = "";

        if (args != null) {
            campus = args.getString("campus");
            school = args.getString("school");
            department = args.getString("department");
            training = args.getString("training");
            group = args.getString("group");
        }

        return new EventLoader(this, campus, school, department, training, group);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        ArrayList<Event> mEventData = getEvents(data);
        for (Event event:mEventData) {
            mEventViewModel.insert(event);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public static ArrayList<Event> getEvents(String data) {
        ArrayList<Event> listEvents = new ArrayList<>();
        Log.e("data", data);

        try {
            JSONObject jsonObj = new JSONObject(data);

            // Getting JSON Array node
            JSONArray chars = jsonObj.getJSONArray("events");

            // looping through All Contacts
            for (int i = 0; i < chars.length(); i++) {
                JSONObject c = chars.getJSONObject(i);
                // adding contact to contact list
                listEvents.add(new Event(i, c.getString("description"), c.getString("summary"), c.getString("location"), c.getString("end"), c.getString("start"), c.getString("day")));
            }

        } catch (final JSONException e) {
            Log.e("JSON", "Json parsing error: " + e.getMessage());
        }
        return listEvents;


    }


}
