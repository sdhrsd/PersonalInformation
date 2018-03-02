package com.herasiddiqui.personalinformation;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MajorSelection extends AppCompatActivity implements DegreeFragment.OnDegreeSelectListener,MajorFragment.OnMajorSelectListener,DegreeFragment.OnCancelClickListener,MajorFragment.OnCancelMajorClickListener,MajorFragment.OnBackToDegreeClickListener{

    List<String> degreesList = new ArrayList<String>();
    List<String> majorList = new ArrayList<String>();
    String degreeNameReceived;
    String majorNameReceived;
    String [] degreeSend;
    String [] majorSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major_selection);
        getDegrees();
        FragmentManager fragments = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragments.beginTransaction();
        DegreeFragment degreeFragment = new DegreeFragment();
        degreeFragment.setDegrees(degreeSend);
        fragmentTransaction.add(R.id.fragment_container,degreeFragment);
        fragmentTransaction.commit();
    }

    public void getDegrees(){
        try {
            InputStream degreesFile = getAssets().open("degree");
            BufferedReader in = new BufferedReader( new InputStreamReader(degreesFile));
            String degree;
            while((degree = in.readLine()) != null){
                degreesList.add(degree);
            }
        } catch (IOException e) {
            Log.e("hs", "read Error", e);
        }
        degreeSend = degreesList.toArray(new String[degreesList.size()]);
    }

    public void getMajors(int degreePosition){
        majorList.clear();
        try {
            InputStream majorFile = null;
            switch(degreePosition)
            {
                case 0:
                    majorFile = getAssets().open("phd");
                    break;
                case 1:
                    majorFile = getAssets().open("edu");
                    break;
                case 2:
                    majorFile = getAssets().open("ma");
                    break;
                case 3:
                    majorFile = getAssets().open("ms");
                    break;
                case 4:
                    majorFile = getAssets().open("mfa");
                    break;
                case 5:
                    majorFile = getAssets().open("prof");
                    break;
                default :break;
            }

            BufferedReader in = new BufferedReader( new InputStreamReader(majorFile));
            String major;
            while((major = in.readLine()) != null){
                majorList.add(major);
            }
        } catch (IOException e) {
            Log.e("hs", "read Error", e);
        }
        majorSend = majorList.toArray(new String[majorList.size()]);
    }

    @Override
    public void onDegreeSelect(int position, String degreeName) {
        degreeNameReceived = degreeName;
        getMajors(position);
        FragmentManager fragments = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragments.beginTransaction();
        MajorFragment majorFragment = new MajorFragment();
        majorFragment.setMajors(majorSend);
        majorFragment.setMajorHeading(degreeNameReceived);
        fragmentTransaction.replace(R.id.fragment_container,majorFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void onMajorSelect(int position, String majorName) {
        majorNameReceived = majorName;
        Log.e("hs", "Received both" + " " + degreeNameReceived + " " + majorNameReceived);
        Intent goToMainActivity = getIntent();
        //Intent goToMainActivity = new Intent(MajorSelection.this,MainActivity.class);
        goToMainActivity.putExtra("DegreeSelected",degreeNameReceived);
        goToMainActivity.putExtra("MajorSelected",majorNameReceived);
        setResult(RESULT_OK, goToMainActivity);
        finish();
    }

    @Override
    public void onCancelClick() {
        finish();
    }

    @Override
    public void onCancelMajorClick() {
        finish();
    }

    @Override
    public void onBackToDegreeClick() {
        super.onBackPressed();
    }
}
