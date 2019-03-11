package com.example.planning.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.planning.Model.Event;

import java.util.List;

@Dao
public interface EventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Event event);
    @Query("SELECT * from event_table WHERE day = :currentDate ORDER BY id ASC")
    LiveData<List<Event>> getEventsDate(String currentDate);
    @Query("SELECT * from event_table ORDER BY id ASC")
    LiveData<List<Event>> getAllEvents();
    @Query("DELETE FROM event_table")
    void deleteAll();
    @Update
    void updateUsers(List<Event> events);


}
