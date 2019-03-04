package com.example.planning;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class EventLoader extends AsyncTaskLoader<String> {
    @Nullable
    @Override
    public String loadInBackground() {
        return EventNetworkUtils.getEventsInfo(campus, school, department, training, group);
    }
    @Override
    protected void onStartLoading() {
        super.forceLoad();
    }
    private String campus;
    private String school;
    private String department;
    private String training;
    private String group;

    EventLoader(Context context, String campus, String school, String department, String training, String group) {
        super(context);
        this.campus = campus;
        this.school = school;
        this.department = department;
        this.training = training;
        this.group = group;
    }
}
