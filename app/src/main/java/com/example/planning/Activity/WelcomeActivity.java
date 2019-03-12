package com.example.planning.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.planning.R;

public class WelcomeActivity extends AppCompatActivity {

    private Button findByYourself;
    private Button localiseMe;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mContext = getApplicationContext();
        findByYourself = findViewById(R.id.findByYourself_button);
        localiseMe = findViewById(R.id.localisation_button);
        findByYourself.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CampusActivity.class);
                mContext.startActivity(intent);

            }
        });


    }

}
