package com.bmeglathery.myfirstpet;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.DialogTitle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int ONE_THIRD_WIDTH = (Resources.getSystem().getDisplayMetrics().widthPixels) / 3;

    private ImageView imageView;
    private RelativeLayout canvas;
    private AnimationDrawable shakeAnimation;

    private ImageButton statusButton;
    private ImageButton playButton;
    private ImageButton optionsButton;
    private ImageButton cleanButton;
    private ImageButton healButton;
    private ImageButton feedButton;

    private static Pet p;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        p = getIntent().getExtras().getParcelable("PET");

        final int INTERVAL  = 1000 * 5; //5 seconds
        final int DECREASE_INTERVAL = 1000 * 10; //30 seconds

        final Handler h = new Handler();

        //Handler makes animation continue
        h.postDelayed(new Runnable(){
            @Override
            public void run(){
                //TODO: Check boolean handlerFlag, set in onStop(), reset in onResume()?
                if(shakeAnimation.isRunning())
                    shakeAnimation.stop();

                shakeAnimation.start();
                h.postDelayed(this, INTERVAL);
            }
        }, INTERVAL);

        //Handler decreases stats, checks for death
        h.postDelayed(new Runnable(){
            @Override
            public void run(){
                if(p.changeStats(1)) {
                    Intent intent = new Intent(MainActivity.this, DeathActivity.class);
                    startActivity(intent);
                    finish();
                }
                h.postDelayed(this, DECREASE_INTERVAL);
            }
        }, DECREASE_INTERVAL);

        //--------------------------------
        SharedPreferences sharedPref = this.getSharedPreferences("user_pref", Context.MODE_PRIVATE);

        canvas = (RelativeLayout) findViewById(R.id.petCanvas);
        imageView = (ImageView) findViewById(R.id.animation);

        if(imageView == null) throw new AssertionError();

        statusButton = (ImageButton) findViewById(R.id.status_btn);
        optionsButton = (ImageButton) findViewById(R.id.options_btn);
        playButton = (ImageButton) findViewById(R.id.play_btn);
        feedButton = (ImageButton) findViewById(R.id.feed_btn);
        healButton = (ImageButton) findViewById(R.id.heal_btn);
        cleanButton = (ImageButton) findViewById(R.id.clean_btn);

        //https://stackoverflow.com/questions/13351003/find-drawable-by-string
        //With modifications and additions, lines 37 - 44

        final Context context = this;

        String pet_form = p.getForm().toLowerCase();
        String pet_type = p.getType().toLowerCase();
        String pet_animation = "";
        String static_pet = "";

        if(pet_form.equals("egg")) {
            pet_animation = pet_form + "_shake_" + pet_type;
        } else {
            //TODO: create xml animation file, mockup version till animations complete
            pet_animation = pet_form + "_greet_" + pet_type;
        }


        /*Currently, the image displayed is the first image identified
        within the xml file describing the animation sequence. The image
        that will be shown after the animation finishes is whatever image
        is listed last.*/
        int id = this.getResources().getIdentifier(
                pet_animation, "drawable", context.getPackageName());
        imageView.setBackgroundResource(id);

        shakeAnimation = (AnimationDrawable) imageView.getBackground();
        shakeAnimation.setOneShot(true);

        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "What's inside...?", Toast.LENGTH_SHORT).show();
                shakeAnimation.start();
            }
        });

        // Menu buttons' onClickListener's are set here
        //----------------------------------------------

        statusButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(context, p.toString(), Toast.LENGTH_LONG).show();
                //Status dialog
                Dialog statusDialog = new Dialog(context);
                statusDialog.setContentView(R.layout.status_layout);

                TextView nameTxt = (TextView) statusDialog.findViewById(R.id.petNameTxt);
                nameTxt.setText(nameTxt.getText() + p.getName());

                TextView ageTxt = (TextView) statusDialog.findViewById(R.id.petAgeTxt);
                ageTxt.setText(ageTxt.getText() + Integer.toString(p.getAge()));

                TextView typeTxt = (TextView) statusDialog.findViewById(R.id.petTypeTxt);
                typeTxt.setText(typeTxt.getText() + p.getType());

                TextView joyTxt = (TextView) statusDialog.findViewById(R.id.petJoyTxt);
                joyTxt.setText(joyTxt.getText() + Integer.toString(p.getJoy()));

                statusDialog.show();
            }
        });

        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Play!", Toast.LENGTH_SHORT).show();
                //Play activity
            }
        });

        optionsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Options!", Toast.LENGTH_SHORT).show();
                //Options activity
            }
        });

        cleanButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clean!", Toast.LENGTH_SHORT).show();
                //Clean activity
            }
        });

        healButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Heal!", Toast.LENGTH_SHORT).show();
                //Heal activity
            }
        });

        feedButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Feed activity
                Dialog feedDialog = new Dialog(context);
                feedDialog.setContentView(R.layout.feed_layout);
                feedDialog.show();

                RelativeLayout apple = (RelativeLayout) feedDialog.findViewById(R.id.appleSelection);
                RelativeLayout banana = (RelativeLayout) feedDialog.findViewById(R.id.bananaSelection);

                apple.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Toast.makeText(context, p.feed(p.getForm()), Toast.LENGTH_LONG).show();
                    }
                });

                banana.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Toast.makeText(context, "Banana!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        //----------------------------------------------

    }


    @Override
    public void onPause(){
        super.onPause();

        SharedPreferences sharedPref = this.getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

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
    }


    @Override
    public void onStop(){
        super.onStop();

        //TODO: Set boolean handlerFlag == false
    }

    @Override
    public void onDestroy(){
        super.onDestroy();


    }

}
