package com.example.planning.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.planning.Model.Cursus;
import com.example.planning.R;
import com.google.gson.Gson;


public class WelcomeActivity extends AppCompatActivity {

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
        findByYourself.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MainActivity.isOnline()) {
                    Intent intent = new Intent(mContext, CampusActivity.class);
                    mContext.startActivity(intent);
                } else {
                    if (cursus != null) {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.putExtra("cursus", cursus);
                        Toast.makeText(mContext, "Ni internet", Toast.LENGTH_SHORT).show();
                        mContext.startActivity(intent);
                    } else {
                        Toast.makeText(mContext, "Ni internet", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });


    }

}
