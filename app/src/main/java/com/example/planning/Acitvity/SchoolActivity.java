package com.example.planning.Acitvity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.planning.Model.Card;
import com.example.planning.Adapter.CardListAdapter;
import com.example.planning.Model.Cursus;
import com.example.planning.R;

import java.util.ArrayList;

public class SchoolActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CardListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Cursus cursus = bundle.getParcelable("cursus");

        mRecyclerView = findViewById(R.id.recyclerViewSchool);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Card> school = new ArrayList<>();
        switch(cursus.getCampus()){
            case "Annecy":
                school.add(new Card( "IUT", Card.Category.SCHOOL, cursus));
                school.add(new Card("IAE", Card.Category.SCHOOL, cursus));
                school.add(new Card("POLYTECH", Card.Category.SCHOOL, cursus));
                break;
            case "Bourget":
                school.add(new Card( "IUT", Card.Category.SCHOOL, cursus));
                school.add(new Card("POLYTECH", Card.Category.SCHOOL, cursus));
                break;
            case "Jacob":
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
