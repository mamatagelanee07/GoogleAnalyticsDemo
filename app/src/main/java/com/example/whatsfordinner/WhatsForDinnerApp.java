package com.example.whatsfordinner;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

/**
 * We have created this class to avoid multiple tracker in single Application
 * Created by mamata.gelanee on 3/31/2016.
 */
public class WhatsForDinnerApp extends Application {

    private Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        startTracking();
    }

    /**
     * Get Tracker from GoogleAnalytics and start tracking
     */
    public void startTracking() {
        if (mTracker == null) {
            GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(this);
            // Get tracker
            mTracker = googleAnalytics.newTracker(R.xml.track_config);
            // Enable tracking of Activities
            googleAnalytics.enableAutoActivityReports(this);
            googleAnalytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
        }
    }

    /**
     * To get tracker
     *
     * @return single tracker available for app.
     */
    public Tracker getTracker() {
        startTracking();
        return this.mTracker;
    }
}
