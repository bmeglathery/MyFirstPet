package com.bmeglathery.myfirstpet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Brandon on 7/4/2017.
 */

public class SplashActivity extends AppCompatActivity {

    private Pet p;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = this.getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if(!(sharedPref.contains("age"))) {
            //Toast.makeText(this, "Creating anew ~", Toast.LENGTH_SHORT).show();
            p = new Pet();
            editor.putString("name", p.getName());
            editor.putString("form", p.getForm());
            editor.putString("type", p.getType());
            editor.putBoolean("healthy", p.getHealthy());
            editor.putBoolean("clean", p.getClean());
            editor.putInt("age", p.getAge());
            editor.putInt("affection", p.getAffection());
            editor.putInt("hunger", p.getHunger());
            editor.putInt("joy", p.getJoy());
            editor.putInt("energy", p.getEnergy());

            editor.apply();
        } else {
            //Toast.makeText(this, "Pulling from prefs", Toast.LENGTH_SHORT).show();
            p = new Pet(
                    sharedPref.getString("name", null),
                    sharedPref.getString("form", null),
                    sharedPref.getString("type", null),
                    sharedPref.getBoolean("healthy", false),
                    sharedPref.getBoolean("clean", false),
                    sharedPref.getInt("age", 0),
                    sharedPref.getInt("affection", 0),
                    sharedPref.getInt("hunger", 0),
                    sharedPref.getInt("joy", 0),
                    sharedPref.getInt("energy", 0),
                    sharedPref.getInt("passedTime", 0)
                    );
        }

        Intent intent = new Intent(this, TitleActivity.class);
        intent.putExtra("PET", p);
        startActivity(intent);
        finish();
    }
}
