package com.example.planning.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "event_table")
public class Event {
    @PrimaryKey
    private int id;
    @NonNull
    @ColumnInfo(name = "description")
    private String description;
    @NonNull
    @ColumnInfo(name = "summary")
    private String summary;
    @NonNull
    @ColumnInfo(name = "location")
    private String location;
    @NonNull
    @ColumnInfo(name = "end")
    private String end;
    @NonNull
    @ColumnInfo(name = "start")
    private String start;
    @NonNull
    @ColumnInfo(name = "day")
    private String day;
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }

    public Event(int id, @NonNull String description, @NonNull String summary, @NonNull String location, @NonNull String end, @NonNull String start, @NonNull String day) {
        this.id = id;
        this.description = description;
        this.summary = summary;
        this.location = location;
        this.day = day;
        this.end = end;
        this.start = start;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getSummary() {
        return summary;
    }

    public void setSummary(@NonNull String sumamary) {
        this.summary = summary;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }

    @NonNull
    public String getEnd() {
        return end;
    }

    public void setEnd(@NonNull String end) {
        this.end = end;
    }

    @NonNull
    public String getStart() {
        return start;
    }

    public void setStart(@NonNull String start) {
        this.start = start;
    }

    @NonNull
    public String getDay() {
        return day;
    }

    public void setDay(@NonNull String day) {
        this.day = day;
    }
}
