package com.example.planning;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {
    private List<Event> mEventsData;
    private Context mContext;

    EventListAdapter(Context context, List<Event> eventsData) {
        this.mEventsData = eventsData;
        this.mContext = context;
    }

    @NonNull
    @Override
    public EventListAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new EventViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.eventlist_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventListAdapter.EventViewHolder eventViewHolder, int i) {
        // Get current sport.
        Event currentEvent = mEventsData.get(i);

        // Populate the textviews with data.
        eventViewHolder.bindTo(currentEvent);
    }

    void setEvents(List<Event> events){
        mEventsData = events;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mEventsData.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView description;
        private TextView summary;
        private TextView location;
        private TextView start;
        private TextView end;
        private TextView day;
        private RelativeLayout container;

        private EventViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            summary = itemView.findViewById(R.id.summary);
            location = itemView.findViewById(R.id.location);
            start = itemView.findViewById(R.id.start);
            end = itemView.findViewById(R.id.end);
            day = itemView.findViewById(R.id.day);
            container = itemView.findViewById(R.id.container);
        }


        void bindTo(Event currentEvent){
            description.setText(currentEvent.getDescription());
            location.setText(currentEvent.getLocation());
            summary.setText(currentEvent.getSummary());
            start.setText(currentEvent.getStart());
            end.setText(currentEvent.getEnd());
            day.setText(currentEvent.getDay());
        }
    }
}
