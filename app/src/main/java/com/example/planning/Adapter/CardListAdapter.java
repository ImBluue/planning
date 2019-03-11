package com.example.planning.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.planning.Activity.DepartmentActivity;
import com.example.planning.Activity.GroupActivity;
import com.example.planning.Activity.MainActivity;
import com.example.planning.Activity.SchoolActivity;
import com.example.planning.Activity.TrainingActivity;
import com.example.planning.Model.Card;
import com.example.planning.R;

import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {

    private Context mContext; //Etat application ==> ensembles activités de l'app
    private ArrayList<Card> mCardList; //Liste personnages

    public CardListAdapter(Context mContext, ArrayList<Card> mCardList){
        this.mContext = mContext;
        this.mCardList = mCardList;
    }

    //Creation d'un item
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CardViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cardlist_item, viewGroup, false));
    }

    //Appel contenu du ViewHolder
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder viewHolder, int i) {
        Card card = mCardList.get(i);
        viewHolder.bindTo(card);
    }

    //Nb elements
    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    //UN element de la liste
    class CardViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextView;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.campusname);
            //Evenement OnClick
            mTextView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Card card = mCardList.get(getAdapterPosition());
                    switch (card.getCategory()){
                        case CAMPUS:
                            Intent intentCampus = new Intent(mContext, SchoolActivity.class);
                            card.getCursus().setCampus(card.getName());
                            intentCampus.putExtra("cursus", card.getCursus());
                            mContext.startActivity(intentCampus);
                            break;
                        case SCHOOL:
                            Intent intentSchool = new Intent(mContext, DepartmentActivity.class);
                            card.getCursus().setSchool(card.getName());
                            intentSchool.putExtra("cursus", card.getCursus());
                            mContext.startActivity(intentSchool);
                            break;
                        case DEPARTMENT:
                            Intent intentDep = new Intent(mContext, TrainingActivity.class);
                            card.getCursus().setDepartment(card.getName());
                            intentDep.putExtra("cursus", card.getCursus());
                            mContext.startActivity(intentDep);
                            break;
                        case TRAINING:
                            Intent intentTraining = new Intent(mContext, GroupActivity.class);
                            card.getCursus().setTraining(card.getName());
                            intentTraining.putExtra("cursus", card.getCursus());
                            mContext.startActivity(intentTraining);
                            break;
                        case GROUP:
                            Intent intentGroup = new Intent(mContext, MainActivity.class);
                            card.getCursus().setGroup(card.getName());
                            intentGroup.putExtra("cursus", card.getCursus());
                            mContext.startActivity(intentGroup);
                            break;
                    }
                }
            });
        }
        //Appel donnees qui vont être afficher
        void bindTo(Card card){
            mTextView.setText(card.getName());
        }
    }
}
