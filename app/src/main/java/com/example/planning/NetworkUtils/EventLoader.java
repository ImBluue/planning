package com.example.planning.NetworkUtils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.planning.Model.Cursus;
import com.example.planning.NetworkUtils.EventNetworkUtils;

public class EventLoader extends AsyncTaskLoader<String> {
    @Nullable
    @Override
    public String loadInBackground() {
        return EventNetworkUtils.getEventsInfo(cursus);
    }
    @Override
    protected void onStartLoading() {
        super.forceLoad();
    }
    private Cursus cursus;
    public EventLoader(Context context, Cursus cursus) {
        super(context);
        this.cursus = cursus;
    }
}
