package com.diseasetracker.diseasetracker;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Level1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "Level1";
    private static final String SYMPTOM1 = "S1";
    private static final String SYMPTOM2 = "S2";
    private static final String SYMPTOM3 = "S3";
    private static final String SYMPTOM4 = "S4";

    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
    String disease[] ={"Cancer","Ischemic heart disease","Stroke","Lower respiratory tract infections","COPD"," Diabetes mellitus","Alzheimers disease","Diarrheal diseases","Tuberculosis diseases","Cirrhosis disease"};
    int randomNumber;
    Random rand;
    String sym1,sym2,sym3,sym4;
    Button checkButton;
    int score=0;
    String item = "";

    TextView textSymptom1,textSymptom2,textSymptom3,textSymptom4,textScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);
        checkButton = findViewById(R.id.check_button);

        textSymptom1 = findViewById(R.id.symptom1);
        textSymptom2 = findViewById(R.id.symptom2);
        textSymptom3 = findViewById(R.id.symptom3);
        textSymptom4 = findViewById(R.id.symptom4);
        textScore = findViewById(R.id.score);
        readData();

        textScore.setText("Score: "+String.valueOf(score));

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item==disease[randomNumber]){
                    score +=10;
                    textScore.setText("Score: "+String.valueOf(score));

                    readData();
                }

            }
        });


        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList <String>();
        for(int i=0; i<10; i++){
            categories.add(disease[i]);

        }
        /*categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");
*/
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);



    }




    void readData(){

        rand = new Random();
        randomNumber = rand.nextInt(disease.length-1 );
        // Read from the database
        dbref.child("Disease").child(disease[randomNumber]).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Log.d(TAG, "Key: " + dataSnapshot.getKey());
                sym1 = dataSnapshot.child(SYMPTOM1).getValue(String.class);
                sym2 = dataSnapshot.child(SYMPTOM2).getValue(String.class);
                sym3 = dataSnapshot.child(SYMPTOM3).getValue(String.class);
                sym4 = dataSnapshot.child(SYMPTOM4).getValue(String.class);

                //String value = dataSnapshot.child(SYMPTOM1).getValue(String.class);
                Log.d(TAG, "Value is: " + sym1);
                Log.d(TAG, "Value is: " + sym2);
                Log.d(TAG, "Value is: " + sym3);
                Log.d(TAG, "Value is: " + sym4);


                textSymptom1.setText("1.) "+sym1);
                textSymptom2.setText("2.) "+sym2);
                textSymptom3.setText("3.) "+sym3);
                textSymptom4.setText("4.) "+sym4);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
         item = parent.getItemAtPosition(position).toString();
        //
        //        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

   /* @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }*/
}
