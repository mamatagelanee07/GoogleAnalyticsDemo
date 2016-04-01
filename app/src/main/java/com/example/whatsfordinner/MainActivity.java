package com.example.whatsfordinner;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class MainActivity extends Activity {

    private Button btnWhatsForDinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTracking();
    }

    /**
     * To start tracking this screen manually
     */
    public void startTracking() {
        // Get a tracker
        Tracker tracker = ((WhatsForDinnerApp) getApplication()).getTracker();
        // Set a screen name to tracker
        tracker.setScreenName(Constants.SCREEN_MAIN);
        // Send hit to Analytics
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    /**
     * To send an event
     */
    public void sendEvent() {
        // Get Tracker
        Tracker tracker = ((WhatsForDinnerApp) getApplication()).getTracker();
        // Send Event
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Main Screen")
                .setAction("Button clicked")
                .setLabel("Label")
                .build());
    }

    /**
     * A method to initialise all resources for screen
     */
    private void initUI() {
        btnWhatsForDinner = (Button) findViewById(R.id.btn_whats_for_dinner);
        btnWhatsForDinner.setOnClickListener(onClickListener);
    }

    /*
     * Show a pop up menu of food preferences.
     * Menu items are defined in menus/food_prefer_menu.xml
     */
    public void showFoodPreferenceMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.food_prefer_menu, popupMenu.getMenu());

        // Set the action of the menu
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getDinnerSuggestion(item.getItemId());
                return true;
            }
        });
        // Show the popup menu
        popupMenu.show();
    }

    /*
    * Suggest dinner for tonight.
    * This method is invoked by the button click action of the food prefs menu.
    * We use the Dinner class to figure out the dinner, based on the food pref.

    */
    public String getDinnerSuggestion(int item) {

        // Get a new Dinner, and use it to get tonight's dinner
        DinnerVO dinner = new DinnerVO(this, item);
        String dinnerChoice = dinner.getDinnerTonight();
        // Utility.showMyToast("dinner suggestion: " + dinnerChoice, this);

        // Start an intent to show the dinner suggestion
        // Put the suggested dinner in the Intent's Extras bundle
        Intent dinnerIntent = new Intent(this, ShowDinnerActivity.class);

        dinnerIntent.putExtra(String.valueOf(R.string.selected_dinner), dinnerChoice);
        startActivity(dinnerIntent);

        return dinnerChoice;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_whats_for_dinner:
                    sendEvent();
                    showFoodPreferenceMenu(view);
                    break;
                default:
                    break;
            }
        }
    };
}
