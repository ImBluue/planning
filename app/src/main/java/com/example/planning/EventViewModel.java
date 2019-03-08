package com.example.planning;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class EventViewModel extends AndroidViewModel {
    private EventRepository mRepository;
    private LiveData<List<Event>> mAllEvents;
    public EventViewModel(Application application) {
        super(application);
        mRepository = new EventRepository(application);
        mAllEvents = mRepository.getAllEvents();
    }
    LiveData<List<Event>> getAllEvents() { return mAllEvents; }
    public void insert(Event event) { mRepository.insert(event); }
    public void deleteall() { mRepository.deleteAll(); }
    public void update(List<Event> events) { mRepository.update(events); }
    LiveData<List<Event>> getEventsDate(String date) {return mRepository.getEventsDate(date);}
}
