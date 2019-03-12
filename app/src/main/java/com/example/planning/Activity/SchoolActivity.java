package com.example.planning.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.planning.Model.Card;
import com.example.planning.Adapter.CardListAdapter;
import com.example.planning.Model.Cursus;
import com.example.planning.R;

import java.util.ArrayList;

public class SchoolActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CardListAdapter mAdapter;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Cursus cursus = bundle.getParcelable("cursus");
        mTextView = findViewById(R.id.txtSchool_askCampus);
        mTextView.setText(cursus.getCampus());
        mRecyclerView = findViewById(R.id.recyclerViewSchool);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Card> school = new ArrayList<>();
        switch(cursus.getCampus()){
            case "ANNECY":
                school.add(new Card( "IUT", Card.Category.SCHOOL, cursus));
                school.add(new Card("IAE", Card.Category.SCHOOL, cursus));
                school.add(new Card("POLYTECH", Card.Category.SCHOOL, cursus));
                break;
            case "BOURGET":
                school.add(new Card( "IUT", Card.Category.SCHOOL, cursus));
                school.add(new Card("POLYTECH", Card.Category.SCHOOL, cursus));
                break;
            case "JACOB":
                school.add(new Card( "DROIT", Card.Category.SCHOOL, cursus));
                school.add(new Card("IAE", Card.Category.SCHOOL, cursus));
                school.add(new Card("POLYTECH", Card.Category.SCHOOL, cursus));
                break;
        }

        mAdapter = new CardListAdapter(getApplicationContext(), school);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
