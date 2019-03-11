package com.example.planning.Acitvity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.planning.Adapter.CardListAdapter;
import com.example.planning.Model.Card;
import com.example.planning.Model.Cursus;
import com.example.planning.R;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CardListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Cursus cursus = bundle.getParcelable("cursus");
        Log.e("cursus", cursus.toString());
        mRecyclerView = findViewById(R.id.recyclerViewGroup);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Card> group = new ArrayList<>();
        switch(cursus.getCampus()){
            case "Annecy":
                switch (cursus.getSchool()){
                    case "IUT":
                        switch(cursus.getDepartment()){
                            case "CSSAP":
                                switch (cursus.getTraining()){
                                    case "CSSAP1":
                                        group.add(new Card("TP1A", Card.Category.GROUP, cursus));
                                        group.add(new Card("TP1B", Card.Category.GROUP, cursus));
                                        break;
                                }
                                break;
                            case "GEA":
                                switch (cursus.getTraining()){
                                    case "GEA1":
                                        group.add(new Card("A", Card.Category.GROUP, cursus));
                                        group.add(new Card("B", Card.Category.GROUP, cursus));
                                        group.add(new Card("C", Card.Category.GROUP, cursus));
                                        group.add(new Card("D", Card.Category.GROUP, cursus));
                                        break;
                                    case "GEA2":
                                        group.add(new Card("GFC1", Card.Category.GROUP, cursus));
                                        group.add(new Card("GFC2", Card.Category.GROUP, cursus));
                                        group.add(new Card("GMO1", Card.Category.GROUP, cursus));
                                        group.add(new Card("GMO2", Card.Category.GROUP, cursus));
                                        break;
                                    case "LP":
                                        group.add(new Card("MGO", Card.Category.GROUP, cursus));
                                        group.add(new Card("I3S", Card.Category.GROUP, cursus));
                                        group.add(new Card("PSTF", Card.Category.GROUP, cursus));
                                        group.add(new Card("SDC", Card.Category.GROUP, cursus));
                                        break;
                                }
                                break;
                            case "GEII":
                                switch (cursus.getTraining()){
                                    case "GEIIS3":
                                        group.add(new Card("AUTO1", Card.Category.GROUP, cursus));
                                        group.add(new Card("AUTO2", Card.Category.GROUP, cursus));
                                        group.add(new Card("ENER1", Card.Category.GROUP, cursus));
                                        group.add(new Card("ENER2", Card.Category.GROUP, cursus));
                                        group.add(new Card("INF1", Card.Category.GROUP, cursus));
                                        group.add(new Card("INF2", Card.Category.GROUP, cursus));
                                        break;
                                    case "GEIIS4":
                                        group.add(new Card("PEL1", Card.Category.GROUP, cursus));
                                        group.add(new Card("PEL2", Card.Category.GROUP, cursus));
                                        group.add(new Card("PEP1", Card.Category.GROUP, cursus));
                                        group.add(new Card("PEP2", Card.Category.GROUP, cursus));
                                        group.add(new Card("ASSERV", Card.Category.GROUP, cursus));
                                        group.add(new Card("OBC", Card.Category.GROUP, cursus));
                                        group.add(new Card("SYS", Card.Category.GROUP, cursus));
                                        break;
                                    case "GEII1":
                                        group.add(new Card("1NA1", Card.Category.GROUP, cursus));
                                        group.add(new Card("1NA2", Card.Category.GROUP, cursus));
                                        group.add(new Card("1NB1", Card.Category.GROUP, cursus));
                                        group.add(new Card("1NB2", Card.Category.GROUP, cursus));
                                        group.add(new Card("1NC1", Card.Category.GROUP, cursus));
                                        group.add(new Card("1NC2", Card.Category.GROUP, cursus));
                                        group.add(new Card("1ND1", Card.Category.GROUP, cursus));
                                        break;
                                    case "GESA":
                                        group.add(new Card("SA1", Card.Category.GROUP, cursus));
                                        group.add(new Card("SA2", Card.Category.GROUP, cursus));
                                        group.add(new Card("SA3", Card.Category.GROUP, cursus));

                                        break;

                                }
                                break;
                            case "GMP":
                                /*
                                training.add(new Card("GMP1", Card.Category.TRAINING, cursus));
                                training.add(new Card("GMP2", Card.Category.TRAINING, cursus));
                                training.add(new Card("TETRA", Card.Category.TRAINING, cursus));
                                training.add(new Card("SKI_ETUDES", Card.Category.TRAINING, cursus));
                                */
                                break;
                            case "INFO":
                                /*
                                training.add(new Card("INFO1", Card.Category.TRAINING, cursus));
                                training.add(new Card("INFO2S3", Card.Category.TRAINING, cursus));
                                training.add(new Card("INFO2S4", Card.Category.TRAINING, cursus));
                                training.add(new Card("LP", Card.Category.TRAINING, cursus));
                                */
                                break;
                            case "MPH":
                                /*
                                training.add(new Card("MPH1", Card.Category.TRAINING, cursus));
                                training.add(new Card("MPH2", Card.Category.TRAINING, cursus));
                                training.add(new Card("LP", Card.Category.TRAINING, cursus));
                                training.add(new Card("SA", Card.Category.TRAINING, cursus));
                                */
                                break;
                            case "QLIO":
                                /*
                                training.add(new Card("QLIO1", Card.Category.TRAINING, cursus));
                                training.add(new Card("QLIO2", Card.Category.TRAINING, cursus));
                                */
                                break;
                            case "TC":
                                /*
                                training.add(new Card("TC1", Card.Category.TRAINING, cursus));
                                training.add(new Card("TC2", Card.Category.TRAINING, cursus));
                                training.add(new Card("PRO1", Card.Category.TRAINING, cursus));
                                training.add(new Card("PRO2", Card.Category.TRAINING, cursus));
                                training.add(new Card("TCCP", Card.Category.TRAINING, cursus));
                                training.add(new Card("LP", Card.Category.TRAINING, cursus));
                                training.add(new Card("TETRAS", Card.Category.TRAINING, cursus));
                                training.add(new Card("SKI_ETUDES", Card.Category.TRAINING, cursus));
                                */
                                break;
                            case "RT":
                                /*
                                training.add(new Card("RT1", Card.Category.TRAINING, cursus));
                                training.add(new Card("RT2", Card.Category.TRAINING, cursus));
                                training.add(new Card("TETRAS", Card.Category.TRAINING, cursus));
                                training.add(new Card("ALTERNANCE", Card.Category.TRAINING, cursus));
                                */
                                break;
                        }
                        break;
                    case "IAE":
                        break;
                    case "POLYTECH":
                        break;
                }
                break;
            case "Bourget":
                switch (cursus.getSchool()){
                    case "IUT":
                        break;
                    case "POLYTECH":
                        break;
                }
                break;
            case "Jacob":
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

        mAdapter = new CardListAdapter(getApplicationContext(), group);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
