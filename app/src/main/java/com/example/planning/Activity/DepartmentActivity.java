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

public class DepartmentActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CardListAdapter mAdapter;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Cursus cursus = bundle.getParcelable("cursus");
        mTextView = findViewById(R.id.txtDepartment_askSchool);
        mTextView.setText(cursus.getSchool());
        mRecyclerView = findViewById(R.id.recyclerViewDep);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Card> dep = new ArrayList<>();
        switch(cursus.getCampus()){
            case "ANNECY":
                switch (cursus.getSchool()){
                    case "IUT":
                        dep.add(new Card("CSSAP", Card.Category.DEPARTMENT, cursus));
                        dep.add(new Card("GEA", Card.Category.DEPARTMENT, cursus));
                        dep.add(new Card("GEII", Card.Category.DEPARTMENT, cursus));
                        dep.add(new Card("GMP", Card.Category.DEPARTMENT, cursus));
                        dep.add(new Card("INFO", Card.Category.DEPARTMENT, cursus));
                        dep.add(new Card("MPH", Card.Category.DEPARTMENT, cursus));
                        dep.add(new Card("QLIO", Card.Category.DEPARTMENT, cursus));
                        dep.add(new Card("TC", Card.Category.DEPARTMENT, cursus));
                        dep.add(new Card("RT", Card.Category.DEPARTMENT, cursus));
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

        mAdapter = new CardListAdapter(getApplicationContext(), dep);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
