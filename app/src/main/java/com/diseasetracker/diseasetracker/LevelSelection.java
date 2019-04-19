package com.diseasetracker.diseasetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LevelSelection extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);
        findViewById(R.id.level_1_button).setOnClickListener(this);
        findViewById(R.id.level_2_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.level_1_button:
                switchActivity(1);
                break;
            case R.id.level_2_button:
                switchActivity(2);
                break;
        }
    }

    public void switchActivity(int i) {
        Intent intent = null;
        if(i==1){
            intent = new Intent(this, Level1.class);

        }else if(i==2){
            intent = new Intent(this, Level2.class);

        }
        startActivity(intent);
    }
}
