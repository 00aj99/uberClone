package com.back4app.quickstartexampleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {


    public  void redirectUser(){

        if(ParseUser.getCurrentUser().get("riderOrDriver").equals("rider")){
            Intent intent= new Intent(getApplicationContext(), RiderActivity.class);
            startActivity(intent);
            Log.i("Proper","InLatlng");

        }else {
            Intent intent= new Intent(getApplicationContext(), DriverActivity.class);
            startActivity(intent);
        }
    }

    public  void getStarted(View view){
        Switch switch1= (Switch) findViewById(R.id.switch1);
        Log.i("SwitchValue", String.valueOf(switch1.isChecked()));

        String userType="rider";
        if(switch1.isChecked()){
            userType="driver";
        }

        ParseUser.getCurrentUser().put("riderOrDriver", userType);

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                redirectUser();
            }
        });
  //      Log.i("Info", "Redirecting as "+ userType);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        if(ParseUser.getCurrentUser()==null){
            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(e==null){
                        Log.i("signin", "Cool");
                    }else{
                        Log.i("signin", "Not Cool");
                    }
                }
            });
        }else if(ParseUser.getCurrentUser().get("riderOrDriver")!= null){
            Log.i("Info", "Redirecting as "+ParseUser.getCurrentUser().get("riderOrDriver"));
            redirectUser();

        }

        // Save the current Installation to Back4App
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }

}
