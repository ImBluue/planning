package com.example.planning;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.TemporalAdjusters;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private RecyclerView mRecyclerView;
    private String TAG = MainActivity.class.getSimpleName();
    private ArrayList<Event> mEventData;
    private EventListAdapter mAdapter;
    private EventViewModel mEventViewModel;
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy", Locale.FRANCE);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
    private ProgressBar percentBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpView();




    }

    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void setUpView() {
        percentBar = findViewById(R.id.progressBar1);
        mRecyclerView = findViewById(R.id.listRecyclerView);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList that will contain the data.
        mEventData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new EventListAdapter(this, mEventData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);


        MaterialCalendarView mcv = findViewById(R.id.calendarView);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int currentDay = 0;
        switch (day) {
            case Calendar.SUNDAY:
                currentDay = 7;
                break;
            case Calendar.MONDAY:
                currentDay = 1;
                break;
            case Calendar.TUESDAY:
                currentDay = 2;
                break;
            case Calendar.FRIDAY:
                currentDay = 5;
                break;
            case Calendar.THURSDAY:
                currentDay = 4;
                break;
            case Calendar.WEDNESDAY:
                currentDay = 3;
                break;
            case Calendar.SATURDAY:
                currentDay = 6;
                break;
        }
        mcv.state().edit()
                .setFirstDayOfWeek(DayOfWeek.of(currentDay))
                .setMinimumDate(CalendarDay.today())
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();

        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                display(date.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yy")));
            }
        });
        mcv.addDecorator(new DisableWeekendsDecorator());



        if(isOnline()) {
            startLoader("ANNECY", "IUT", "INFO", "INFO2S4", "G22");
            Toast.makeText(getApplicationContext(),
                    "Updating...",
                    Toast.LENGTH_LONG).show();
        } else
        {
            percentBar.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        if(currentDay == 6 || currentDay == 7){
            LocalDate ld = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            mcv.setDateSelected(CalendarDay.from(ld), true);
            display(ld.format(formatter));
        }
        else{
            mcv.setDateSelected(CalendarDay.today(), true);
            Date date = new Date();
            display(dateFormat.format(date));
        }



    }

    public void startLoader(String campus, String school, String department, String training, String group) {
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

    private void display(String date) {
        mEventViewModel.getEventsDate(date).observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable final List<Event> events) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setEvents(events);

            }
        });
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        mEventData = getEvents(data);
        percentBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mEventViewModel.deleteall();
        for (Event event : mEventData) {
            mEventViewModel.insert(event);
        }
        Log.e("HOP", "Finished!");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public static ArrayList<Event> getEvents(String data) {
        ArrayList<Event> listEvents = new ArrayList<>();
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
