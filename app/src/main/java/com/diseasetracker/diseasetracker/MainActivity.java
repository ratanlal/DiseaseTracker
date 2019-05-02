package com.diseasetracker.diseasetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

   /* TextView diseaseTrackerText;
    ImageView diseaseTrackerLogo;
    Button startGameButton;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*diseaseTrackerText.findViewById(R.id.disease_tracker_text);
        diseaseTrackerLogo.findViewById(R.id.disease_tracker_logo);
        startGameButton.findViewById(R.id.start_game_button);*/
        findViewById(R.id.start_game_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.start_game_button:
                switchActivity();
                break;
        }
    }
    public void switchActivity() {
        Intent intent = null;
        intent = new Intent(this, LevelSelection.class);
        startActivity(intent);
    }

}
