package com.example.planning.Acitvity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.planning.Model.Card;
import com.example.planning.Adapter.CardListAdapter;
import com.example.planning.Model.Cursus;
import com.example.planning.R;

import java.util.ArrayList;

public class CampusActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CardListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus);


        mRecyclerView = findViewById(R.id.recyclerViewCampus);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Card> campus = new ArrayList<>();
        campus.add(new Card( "Annecy", Card.Category.CAMPUS, new Cursus()));
        campus.add(new Card("Bourget-du-Lac", Card.Category.CAMPUS, new Cursus()));
        campus.add(new Card("Jacob-Bellecombette", Card.Category.CAMPUS, new Cursus()));

        mAdapter = new CardListAdapter(getApplicationContext(), campus);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }
}
