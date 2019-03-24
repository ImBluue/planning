package com.example.planning.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.planning.Services.GoogleMaps;
import com.example.planning.Model.Cursus;
import com.example.planning.Services.NotifyService;
import com.example.planning.R;
import com.google.gson.Gson;


public class WelcomeActivity extends AppCompatActivity {
    private GoogleMaps gpsTracker;
    private Button findByYourself;
    private Button localiseMe;
    private Context mContext;
    public static final String PREFS_NAME = "cursus";
    private Cursus cursus;
    private boolean change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        startService(new Intent(this, NotifyService.class));


        mContext = getApplicationContext();
        SharedPreferences mPrefs = getSharedPreferences(PREFS_NAME, 0);
        Gson gson = new Gson();
        String json = mPrefs.getString("cursus", "");
        cursus = gson.fromJson(json, Cursus.class);

        Intent in = getIntent();
        if(in == null)
            change = false;
        else
            change = in.getBooleanExtra("change", false);
        if (!change)
            if (cursus != null) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("cursus", cursus);
                mContext.startActivity(intent);
            }


        findByYourself = findViewById(R.id.findByYourself_button);
        localiseMe = findViewById(R.id.localisation_button);
        localiseMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                        ActivityCompat.requestPermissions((Activity)mContext, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
                Intent intentSchool = new Intent(mContext, DepartmentActivity.class);
                intentSchool.putExtra("cursus", getLocation());
                mContext.startActivity(intentSchool);
            }
        });
        findByYourself.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MainActivity.isOnline()) {
                    Intent intent = new Intent(mContext, CampusActivity.class);
                    mContext.startActivity(intent);
                } else {
                    if (cursus != null) {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.putExtra("cursus", cursus);
                        Toast.makeText(mContext, "No internet", Toast.LENGTH_SHORT).show();
                        mContext.startActivity(intent);
                    } else {
                        Toast.makeText(mContext, "No internet", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });


    }
    public Cursus getLocation(){
        gpsTracker = new GoogleMaps(WelcomeActivity.this);
        Cursus cursusLocation = new Cursus();
        if(gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            if ((latitude <= 45.921672 && latitude >= 45.919231) && (longitude >= 6.151719 && longitude <= 6.154836)) {
                    cursusLocation.setCampus("ANNECY");
                    cursusLocation.setSchool("IUT");
            }
            else if((latitude <= 45.920336 && latitude >= 45.919249) && ( longitude >= 6.157658 && longitude <= 6.158301)){
                cursusLocation.setCampus("ANNECY");
                cursusLocation.setSchool("POLYTECH");
            }
            else if((latitude <= 45.918629 && latitude >= 45.918856) && ( longitude >= 6.156891 && longitude <= 6.159186)){
                cursusLocation.setCampus("ANNECY");
                cursusLocation.setSchool("IAE");
            }
        }else{
            gpsTracker.showSettingsAlert();
        }
        return cursusLocation;
    }

}
