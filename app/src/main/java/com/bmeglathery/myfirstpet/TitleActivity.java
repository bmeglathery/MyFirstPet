package com.bmeglathery.myfirstpet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Brandon on 7/4/2017.
 */

public class TitleActivity extends AppCompatActivity {

    private ImageView title;
    private ImageView petPic;
    private AnimationDrawable titleShake;
    private AnimationDrawable petShake;
    private Pet p;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_screen);

        SharedPreferences sharedPref = this.getSharedPreferences("user_pref", Context.MODE_PRIVATE);

        //Title Screen Animation
        //----------------------
        title = (ImageView) findViewById(R.id.TitleAnimation);
        title.setBackgroundResource(R.drawable.title_animation);

        titleShake = (AnimationDrawable) title.getBackground();
        titleShake.setOneShot(true);

        title.setVisibility(View.VISIBLE);
        titleShake.start();

        //----------------------
        //Display Pet from Parcelable Extra
        //----------------------
        p = getIntent().getExtras().getParcelable("PET");
        if (p != null) {
            //Toast.makeText(this, "Received Pet: " + p.toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Did not receive a pet...", Toast.LENGTH_LONG).show();
        }

        petPic = (ImageView) findViewById(R.id.PetDisplay);

        if(petPic == null)
            throw new AssertionError();

        String form = p.getForm().toLowerCase();
        String type = p.getType().toLowerCase();

        String resource_id = form + "_" + type;
        String anim_res_id = form + "_shake_" + type;

        int id = getResources().getIdentifier(
                resource_id, "drawable", getPackageName());

        int anim_id = getResources().getIdentifier(
                anim_res_id, "drawable", getPackageName());

        petPic.setImageResource(id);
        petPic.setBackgroundResource(anim_id);

        petShake = (AnimationDrawable) petPic.getBackground();
        petShake.setOneShot(true);

        petPic.setVisibility(View.VISIBLE);
        petShake.start();


        //----------------------

        ImageButton beginBtn = (ImageButton) findViewById(R.id.BeginButton);
        beginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TitleActivity.this, MainActivity.class);
                intent.putExtra("PET", p);
                startActivity(intent);
                finish();
            }
        });
    }

}
