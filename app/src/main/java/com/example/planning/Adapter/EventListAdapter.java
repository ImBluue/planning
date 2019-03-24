package com.example.planning.Adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.planning.Model.Event;
import com.example.planning.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {
    private List<Event> mEventsData;
    private Context mContext;
    AlarmManager alarmManager;

    private static final String ACTION_UPDATE_NOTIFICATION =
            "com.example.planning.ACTION_UPDATE_NOTIFICATION";

    public EventListAdapter(Context context, List<Event> eventsData) {
        this.mEventsData = eventsData;
        this.mContext = context;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new EventViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.eventlist_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {
        // Get current sport.
        Event currentEvent = mEventsData.get(i);
        //Log.e("e", currentEvent.toString());

        // Populate the textviews with data.
        eventViewHolder.bindTo(currentEvent);
        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YY-MM-dd hh:mm");
        /*
        Log.e("current day", currentEvent.getDay());
        Log.e("start",currentEvent.getStart());
        Log.e("day",currentEvent.getDay());
        Log.e("hours",Integer.parseInt(currentEvent.getStart().substring(0,2))+"");
        Log.e("Min",Integer.parseInt(currentEvent.getStart().substring(3))+"");
        Log.e("day",Integer.parseInt(currentEvent.getDay().substring(0,2))+"");
        Log.e("Month",Integer.parseInt(currentEvent.getDay().substring(3,5))+"");
        Log.e("year",Integer.parseInt(currentEvent.getDay().substring(6))+"");
        */
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(currentEvent.getStart().substring(0,2)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(currentEvent.getStart().substring(3)));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(currentEvent.getDay().substring(0,2)));
        calendar.set(Calendar.MONTH, Integer.parseInt(currentEvent.getDay().substring(3,5))-1);
        calendar.set(Calendar.YEAR, Integer.parseInt(currentEvent.getDay().substring(6)));
        calendar.set(Calendar.SECOND,0);
        Date calendarDate = calendar.getTime();
        Log.d("alarm calendar",  sdf.format(calendarDate));
        Intent intentAlarm = new Intent(ACTION_UPDATE_NOTIFICATION);
        intentAlarm.putExtra("date",currentEvent.getDescription());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intentAlarm, 0);

        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), intervalSpan, pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

    public void setEvents(List<Event> events){
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
