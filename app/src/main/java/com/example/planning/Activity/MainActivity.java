package com.example.planning.Activity;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planning.Adapter.EventListAdapter;
import com.example.planning.Model.DisableWeekendsDecorator;
import com.example.planning.NetworkUtils.EventLoader;
import com.example.planning.ViewModel.EventViewModel;
import com.example.planning.Model.Cursus;
import com.example.planning.Model.Event;
import com.example.planning.R;
import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.TemporalAdjusters;
import org.threeten.bp.temporal.WeekFields;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private RecyclerView mRecyclerView;
    private TextView mTextView;
    private String TAG = MainActivity.class.getSimpleName();
    private ArrayList<Event> mEventData;
    private EventListAdapter mAdapter;
    private EventViewModel mEventViewModel;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
    private ProgressBar percentBar;
    private Button btnChange;
    public static final String PREFS_NAME = "cursus";
    private Cursus cursus;

    private Button btnExport;
    private File imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        cursus = bundle.getParcelable("cursus");
        Log.i("cursus", cursus.toString());

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cursus);
        editor.putString("cursus", json);
        // Commit the edits!
        editor.apply();
        setUpView(cursus);





    }




    @Override
    public void onBackPressed() {
// dont call **super**, if u want disable back button in current screen.
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

    public void setUpView(Cursus cursus) {
        btnChange = findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), WelcomeActivity.class);
                in.putExtra("change",true);
                getApplicationContext().startActivity(in);
            }});
        percentBar = findViewById(R.id.progressBar1);
        mRecyclerView = findViewById(R.id.listRecyclerView);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initialize the cursus resume on top
        mTextView = findViewById(R.id.txtMain_askCursus);
        final String cursusString = cursus.getCampus()+" - "+cursus.getSchool()+" - "+cursus.getDepartment()+" - "+cursus.getGroup();
        mTextView.setText(cursusString);

        // Initialize the ArrayList that will contain the data.
        mEventData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new EventListAdapter(this, mEventData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        btnExport = findViewById(R.id.btnExport);
        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bitmap = takeScreenshot();
                saveBitmap(bitmap);
                shareIt();
            }
        });

        MaterialCalendarView mcv = findViewById(R.id.calendarView);
        final Calendar calendar = Calendar.getInstance();
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
        //.setFirstDayOfWeek(DayOfWeek.of(currentDay))


        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                display(date.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yy")));

                WeekFields weekFields = WeekFields.of(Locale.getDefault());
                int weekNumber = date.getDate().get(weekFields.weekOfWeekBasedYear());
                Toast.makeText(getApplicationContext(), weekNumber+"", Toast.LENGTH_SHORT).show();

            }
        });

        mcv.addDecorator(new DisableWeekendsDecorator());
        mcv.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay calendarDay) {
                LocalDate localDate = calendarDay.getDate();//For reference
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
                String formattedString = localDate.format(formatter);

                String cap = formattedString.substring(0, 1).toUpperCase() + formattedString.substring(1);
                return cap;
            }
        });

        if(isOnline()) {
            startLoader(cursus);
            Log.i("internet", "ok");
        } else
        {
            percentBar.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        if(currentDay == 6 || currentDay == 7){
            LocalDate ld = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            mcv.setDateSelected(CalendarDay.from(ld), true);
            display(ld.format(formatter));
            mcv.state().edit()
                    .setMinimumDate(CalendarDay.from(ld))
                    .setCalendarDisplayMode(CalendarMode.WEEKS)
                    .commit();
        }
        else{
            mcv.setDateSelected(CalendarDay.today(), true);
            display(LocalDate.now().format(formatter));
            mcv.state().edit()
                    .setMinimumDate(CalendarDay.today())
                    .setCalendarDisplayMode(CalendarMode.WEEKS)
                    .commit();
        }



    }

    public void startLoader(Cursus cursus) {
        Bundle queryBundle = new Bundle();
        queryBundle.putParcelable("cursus", cursus);


        getSupportLoaderManager().restartLoader(0, queryBundle, this);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        Cursus cursus = new Cursus();
        if (args != null) {
            cursus = args.getParcelable("cursus");
        }
        return new EventLoader(this, cursus);
    }

    public void display(String date) {
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
        Log.i("HOP", "Finished!");
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

    public Bitmap takeScreenshot() {
        try {
            if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) || (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED )) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {

        imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    private void shareIt() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = "My planning";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My planning");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", imagePath));

        startActivity(Intent.createChooser(sharingIntent, "Partager via"));
    }
}
