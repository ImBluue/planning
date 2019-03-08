package com.example.planning;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class EventRepository {
    private EventDao mEventDao;
    private LiveData<List<Event>> mAllEvents;

    EventRepository(Application application) {
        EventRoomDatabase db = EventRoomDatabase.getDatabase(application);
        mEventDao = db.eventDao();
        mAllEvents = mEventDao.getAllEvents();
    }

    LiveData<List<Event>> getAllEvents() {
        return mAllEvents;
    }
    public void deleteAll()  {
        new deleteAllWordsAsyncTask(mEventDao).execute();
    }
    public void insert (Event event) {
        new insertAsyncTask(mEventDao).execute(event);
    }
    public void update (List<Event> events) {
        new UpdateAsyncTask(mEventDao).execute(events);
    }
    LiveData<List<Event>> getEventsDate (String date) {
        return mEventDao.getEventsDate(date);
    }
    private static class insertAsyncTask extends AsyncTask<Event, Void, Void> {

        private EventDao mAsyncTaskDao;

        insertAsyncTask(EventDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Event... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private EventDao mAsyncTaskDao;

        deleteAllWordsAsyncTask(EventDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask<List<Event>, Void, Void> {
        private EventDao mAsyncTaskDao;

        UpdateAsyncTask(EventDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(List<Event>... lists) {
            mAsyncTaskDao.updateUsers(lists[0]);
            return null;
        }
    }

}
