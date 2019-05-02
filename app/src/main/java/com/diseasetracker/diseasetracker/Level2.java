package com.diseasetracker.diseasetracker;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Level2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "Level2";

    String imageFile[] = {"skin_disease1","skin_disease2","skin_disease3","skin_disease4","skin_disease5","skin_disease6","skin_disease7","skin_disease8","skin_disease9","skin_disease10"};
    Vector<String> ur = new Vector<String>();                   //vector for sorting downloaded image
    String addressArray[] = new String[10];                // array for storing url of images
    String addressArray1[] = new String[10];            //images downloaded array1
    String addressArray2[] = new String[10];            //images downloaded array2
    String array[] = new String[10];
    int storeRandomNumber [] = new int [10];
    int count = 0;            // count for getting url
    int counter = 0;              // counter for switching the image stored array
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    ImageView iv;
    int whichArrayToStore = 0;                            //array in which downoaded images stored
    //int whichArrayToShow = 0;
    //int counterp;
    int index = 0;
    Random rand;
    int randomNumberArray[] = new int[10];
    int imageShowCount = 0;
    int imageDownloadCount = 0;
    int countshowimage = 0;
    String item = "";
    Button checkButton;
    int score=0;
    TextView textScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);
        checkButton = findViewById(R.id.check_button);
        textScore = findViewById(R.id.score);
        imageDownload();
        textScore.setText("Score: "+String.valueOf(score));

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item==imageFile[randomNumberArray[count]]){
                    score +=10;
                    textScore.setText("Score: "+String.valueOf(score));

                    showImage();
                }

            }
        });

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        for(int i=0; i<10; i++){
            categories.add(imageFile[i]);

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

    void imageDownload() {

        for(int i=0; i<10; i++){
            storeRandomNumber[i] = randomNumberArray[i];
            //  Log.i(TAG,"storerandomnumber: "+ Integer.toString(storeRandomNumber[i]));
        }

        for (int i = 0; i < 10; i++)
        {
            int counttemp = 0;
            imageCharArray[i]="";
            if(addressArray[i]!=null){
                for(int j = 0;; j++){

                    if(addressArray[i].charAt(j)=='/') {
                        counttemp++;
                    }
                    if(counttemp==7){
                        if(addressArray[i].charAt(j)!='/' && addressArray[i].charAt(j)!='.')
                            imageCharArray[i]= imageCharArray[i]+addressArray[i].charAt(j);
                        //Log.i(TAG,"charAT: "+ addressArray[i].charAt(j));
                        if(addressArray[i].charAt(j)=='.'){
                            break;
                        }
                    }

                }
                //Log.i(TAG, "uri: " + addressArray[i] );
                //Log.i(TAG, "imagecharArray: "+ imageCharArray[i]);
            }
        }

            rand = new Random();

            for (int i = 0; i < 10; i++) {
                randomNumberArray[i] = rand.nextInt(imageFile.length - 1);
                // Log.i(TAG,"random num before send:"+ Integer.toString(randomNumberArray[i]));
            }
            Log.i(TAG, "HOST  sending random num info");


        getUrl();

        }



    void getUrl() {
        Arrays.fill(addressArray, null);
        while (count < 10) {
            storageRef.child(imageFile[randomNumberArray[count]] + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    ur.add(uri.toString());
                    // Log.i(TAG,"uri to string: "+uri.toString());
                    counter++;
                    if (counter == 10) {

                        whichArrayToStore = 1 - whichArrayToStore;
                        counter = 0;
                        count = 0;
                        Log.i(TAG,"which array to store : " + Integer.toString(whichArrayToStore));

                        sortImageVector();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.i(TAG, "Failure");
                }
            });
            // Log.i(TAG,"Sorted array size :"+ Integer.toString(ur.size()));
            count++;
        }
    }

    String imageCharArray[] = new String[10];
    void sortImageVector() {
        Collections.sort(ur);
        for (int i = 0; i < ur.size(); i++) {
            addressArray[i] = ur.get(i);

        }
        ur.clear();
        Log.i(TAG,"Downloading start...");
        new DownloadFileFromURL().execute(addressArray);
    }


    @SuppressLint("StaticFieldLeak")
    public class DownloadFileFromURL extends AsyncTask<String, Integer, String> {

        /**
         * Before starting background thread Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {

                for (int i = 0; i < f_url.length; i++) {
                    // Log.i(TAG,"url length: "+ Integer.toString(f_url.length));

                    URL url = new URL(f_url[i]);
                    URLConnection conection = url.openConnection();
                    conection.connect();
                    // getting file length
                    int lenghtOfFile = conection.getContentLength();

                    // input stream to read file - with 8k buffer
                    InputStream input = new BufferedInputStream(
                            url.openStream(), 8192);
                    System.out.println("Data::" + f_url[i]);
                    // Output stream to write file
                    // Log.i(TAG,"before output stream:"+Environment.getExternalStorageDirectory());

                    imageDownloadCount++;
                    Log.i(TAG, "imageDownoadCount: " + Integer.toString(imageDownloadCount));

                    File file;

                    OutputStream output = null;
                    // Log.i(TAG, "string value of file:"+ file.toString());

                    // File file = new File(output.getFilesDir(),filename);
                    if (whichArrayToStore == 1) {
                        String filename = "downloaded1" + i + ".jpg";
                        file = File.createTempFile(filename, null);
                        addressArray1[i] = file.toString();
                        // Log.i(TAG,"after addressarray1");

                    } else {
                        String filename = "downloaded0" + i + ".jpg";
                        file = File.createTempFile(filename, null);
                        addressArray2[i] = file.toString();
                        //  Log.i(TAG,"after addressarray2");


                    }
                    byte data[] = new byte[1024];

                    long total = 0;
                    output = new FileOutputStream(file);

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress((int) ((total * 100) / lenghtOfFile));

                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();
                }
            } catch (Exception e) {
                // Log.e("Error: ", e.getMessage();
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(Integer... progress) {
            // setting progress percentage
            // pDialog.setProgress(progress[0]);
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            Log.i(TAG, "Download complete");
            if(whichArrayToStore==1){
                for (int i = 0; i < 10; i++) {
                    array[i] = addressArray1[i];
                }
            }
            //findViewById(R.id.download_wait).setVisibility(View.GONE);

            // findViewById(R.id.downloadView).setVisibility(View.VISIBLE);
            if (countshowimage == 0) {
                countshowimage++;

                showImage();
            }

            // getUrl();


            // setting downloaded into image view
            // my_image.setImageDrawable(Drawable.createFromPath(imagePath));
        }

    }


    void showImage() {

       // findViewById(R.id.honkView).setVisibility(View.VISIBLE);

        iv = (ImageView) findViewById(R.id.imageDisplay);
        Bitmap bmp = BitmapFactory.decodeFile(array[index]);
        iv.setImageBitmap(bmp);
        //imageUpload = array[index];

        imageShowCount++;
        Log.i(TAG,"imageshowcount:"+Integer.toString(imageShowCount));

        index++;
        Log.i(TAG, "index: "+Integer.toString(index));

        if (index == 10) {
            if (whichArrayToStore == 0) {
                for (int j = 0; j < 10; j++) {
                    array[j] = addressArray2[j];
                    Log.i(TAG, "addressarray2 copied in  array ");
                    File file = null;
                    file = new File(addressArray1[j]);
                    file.delete();

                }

            } else {
                for (int j = 0; j < 10; j++) {
                    array[j] = addressArray1[j];
                    Log.i(TAG, "addressarray1 copied in  array ");

                    File file = null;
                    file = new File(addressArray2[j]);
                    file.delete();
                }
            }
            index = 0;
        }
    }


}
