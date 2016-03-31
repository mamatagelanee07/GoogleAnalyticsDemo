package com.example.whatsfordinner;

import android.content.Context;

import java.util.Random;

/**
 * Created by mamata.gelanee on 3/30/2016.
 */
public class DinnerVO {
    public Random randomGen = new Random();
    private Context mContext;
    private int item;
    private String foodPref;
    private String[] mealChoices;

    public DinnerVO(Context mContext, int item) {
        this.mContext = mContext;
        this.item = item;

        foodPref = getFoodPreference(item);

        // Show a vegan meal?
        if (foodPref.equals("vegan")) {
            mealChoices = mContext.getResources().getStringArray(R.array.vegan_meals);
        }
        // Show a vegetarian meal?
        // Combine vegetarian and vegan meals
        else if (foodPref.equals("vegetarian")) {
            mealChoices = Utility.combine(
                    mContext.getResources().getStringArray(R.array.vegan_meals),
                    mContext.getResources().getStringArray(R.array.vegetarian_meals));
        }
        // Show a fish meal?
        else if (foodPref.equals("fish")) {
            mealChoices = mContext.getResources().getStringArray(R.array.fish_meals);
        }
        // Show a meat meal?
        else if (foodPref.equals("meat")) {
            mealChoices = mContext.getResources().getStringArray(R.array.meat_meals);
        } else {
            // No preference, so pick from all the meal choices
            mealChoices = getAllDinners(mContext);
        }
    }

    public String getDinnerTonight() {
        // Get the dinner
        String dinner = getChoiceFromArray(mealChoices);
        return dinner;
    }

    // Utility function to get a random choice from an array
    public String getChoiceFromArray(String[] choices) {
        return choices[randomGen.nextInt(choices.length)];
    }

    public String[] getAllDinners(Context context) {
        // Return all the meal choices
        mealChoices = Utility.combine(
                context.getResources().getStringArray(R.array.vegan_meals),
                context.getResources().getStringArray(R.array.vegetarian_meals),
                context.getResources().getStringArray(R.array.fish_meals),
                context.getResources().getStringArray(R.array.meat_meals)
        );
        return mealChoices;

    }

    public String getFoodPreference(int item) {
        String foodPreference;
        switch (item) {
            case R.id.vegan_pref:
                foodPreference = "vegan";
                break;
            case R.id.vegetarian_pref:
                foodPreference = "vegetarian";
                break;
            case R.id.fish_pref:
                foodPreference = "fish";
                break;
            case R.id.meat_pref:
                foodPreference = "meat";
                break;
            default:
                foodPreference = "unrestricted";
        }
        Utility.showMyToast(foodPreference, mContext);
        return foodPreference;
    }
}
