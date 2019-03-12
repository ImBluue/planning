package com.example.planning.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.planning.Adapter.CardListAdapter;
import com.example.planning.Model.Card;
import com.example.planning.Model.Cursus;
import com.example.planning.R;

import java.util.ArrayList;

public class TrainingActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CardListAdapter mAdapter;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Cursus cursus = bundle.getParcelable("cursus");
        mTextView = findViewById(R.id.txtTraining_askDepartment);
        mTextView.setText(cursus.getDepartment());
        mRecyclerView = findViewById(R.id.recyclerViewTraining);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Card> training = new ArrayList<>();
        switch(cursus.getCampus()){
            case "ANNECY":
                switch (cursus.getSchool()){
                    case "IUT":
                        switch(cursus.getDepartment()){
                            case "CSSAP":
                                training.add(new Card("CSSAP1", Card.Category.TRAINING, cursus));
                                break;
                            case "GEA":
                                training.add(new Card("GEA1", Card.Category.TRAINING, cursus));
                                training.add(new Card("GEA2", Card.Category.TRAINING, cursus));
                                training.add(new Card("LP", Card.Category.TRAINING, cursus));
                                break;
                            case "GEII":
                                training.add(new Card("GEII2S3", Card.Category.TRAINING, cursus));
                                training.add(new Card("GEII2S4", Card.Category.TRAINING, cursus));
                                training.add(new Card("GEII1", Card.Category.TRAINING, cursus));
                                training.add(new Card("GESA", Card.Category.TRAINING, cursus));
                                break;
                            case "GMP":
                                training.add(new Card("GMP1", Card.Category.TRAINING, cursus));
                                training.add(new Card("GMP2", Card.Category.TRAINING, cursus));
                                training.add(new Card("TETRAS", Card.Category.TRAINING, cursus));
                                training.add(new Card("SKI_ETUDES", Card.Category.TRAINING, cursus));
                                break;
                            case "INFO":
                                training.add(new Card("INFO1", Card.Category.TRAINING, cursus));
                                training.add(new Card("INFO2S3", Card.Category.TRAINING, cursus));
                                training.add(new Card("INFO2S4", Card.Category.TRAINING, cursus));
                                training.add(new Card("LP", Card.Category.TRAINING, cursus));
                                break;
                            case "MPH":
                                training.add(new Card("MPH1", Card.Category.TRAINING, cursus));
                                training.add(new Card("MPH2", Card.Category.TRAINING, cursus));
                                training.add(new Card("LP", Card.Category.TRAINING, cursus));
                                training.add(new Card("SA", Card.Category.TRAINING, cursus));
                                break;
                            case "QLIO":
                                training.add(new Card("QLIO1", Card.Category.TRAINING, cursus));
                                training.add(new Card("QLIO2", Card.Category.TRAINING, cursus));
                                break;
                            case "TC":
                                training.add(new Card("TC1", Card.Category.TRAINING, cursus));
                                training.add(new Card("TC2", Card.Category.TRAINING, cursus));
                                training.add(new Card("PRO1", Card.Category.TRAINING, cursus));
                                training.add(new Card("PRO2", Card.Category.TRAINING, cursus));
                                training.add(new Card("TCCP", Card.Category.TRAINING, cursus));
                                training.add(new Card("LP", Card.Category.TRAINING, cursus));
                                training.add(new Card("TETRAS", Card.Category.TRAINING, cursus));
                                training.add(new Card("SKI_ETUDES", Card.Category.TRAINING, cursus));
                                break;
                            case "RT":
                                training.add(new Card("RT1", Card.Category.TRAINING, cursus));
                                training.add(new Card("RT2", Card.Category.TRAINING, cursus));
                                training.add(new Card("TETRAS", Card.Category.TRAINING, cursus));
                                training.add(new Card("ALTERNANCE", Card.Category.TRAINING, cursus));
                                break;
                        }
                        break;
                    case "IAE":
                        break;
                    case "POLYTECH":
                        break;
                }
                break;
            case "BOURGET":
                switch (cursus.getSchool()){
                    case "IUT":
                        break;
                    case "POLYTECH":
                        break;
                }
                break;
            case "JACOB":
                switch (cursus.getSchool()){
                    case "DROIT":
                        break;
                    case "IAE":
                        break;
                    case "POLYTECH":
                        break;
                }
                break;
        }

        mAdapter = new CardListAdapter(getApplicationContext(), training);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
