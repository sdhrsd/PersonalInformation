package com.herasiddiqui.personalinformation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText age;
    private EditText email;
    private EditText phone;
    private EditText major;
    private static final int INTENT_DEGREE_MAJOR_REQUEST = 123;
    public static final String PREFS_DEFAULT = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstName = findViewById(R.id.textFirstName);
        lastName = findViewById(R.id.textLastName);
        age = findViewById(R.id.textAge);
        email = findViewById(R.id.textEmail);
        phone = findViewById(R.id.textPhone);
        major = findViewById(R.id.textMajor);

        major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                Intent go = new Intent(MainActivity.this,MajorSelection.class);
                startActivityForResult(go, INTENT_DEGREE_MAJOR_REQUEST);
                //Toast.makeText(MainActivity.this, "Click working", Toast.LENGTH_LONG).show();
            }
        });
        savedData();
    }
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_DEGREE_MAJOR_REQUEST) {
            if (resultCode == RESULT_OK) {
                String degreeToPut = data.getStringExtra("DegreeSelected");
                String majorToPut = data.getStringExtra("MajorSelected");
                String degreeMajorCombined = degreeToPut + " " + majorToPut;
                major.setText(degreeMajorCombined);
            }
        }
    }

    public void submitData(View button) {
        if (firstName.getText().length() == 0) {
            Toast.makeText(MainActivity.this, "Enter your first name", Toast.LENGTH_SHORT).show();
            firstName.requestFocus();
        } else if (lastName.getText().length() == 0) {
            Toast.makeText(MainActivity.this, "Enter your last name", Toast.LENGTH_SHORT).show();
            lastName.requestFocus();
        } else if (phone.getText().length() == 0) {
            Toast.makeText(MainActivity.this, "Enter your phone number", Toast.LENGTH_SHORT).show();
            phone.requestFocus();
        } else if (age.getText().length() == 0) {
            Toast.makeText(MainActivity.this, "Enter your age", Toast.LENGTH_SHORT).show();
            age.requestFocus();
        } else if (email.getText().length() == 0) {
            Toast.makeText(MainActivity.this, "Enter your email address", Toast.LENGTH_SHORT).show();
            email.requestFocus();
        } else if (major.getText().length() == 0) {
            Toast.makeText(MainActivity.this, "Select a major", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences sharedPref = getSharedPreferences("PersonalInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("FirstName", firstName.getText().toString());
            editor.putString("LastName", lastName.getText().toString());
            editor.putString("Phone", phone.getText().toString());
            editor.putString("Age", age.getText().toString());
            editor.putString("Email", email.getText().toString());
            editor.putString("Major", major.getText().toString());
            editor.commit();
            Toast.makeText(MainActivity.this, " Personal Information Saved!", Toast.LENGTH_SHORT).show();
        }
    }
    public void savedData(){
        SharedPreferences sharedPref = getSharedPreferences("PersonalInfo", Context.MODE_PRIVATE);
        String retrievedFirstName = sharedPref.getString("FirstName",PREFS_DEFAULT);
        String retrievedLasttName = sharedPref.getString("LastName",PREFS_DEFAULT);
        String retrievedPhone = sharedPref.getString("Phone",PREFS_DEFAULT);
        String retrievedAge = sharedPref.getString("Age",PREFS_DEFAULT);
        String retrievedEmail = sharedPref.getString("Email",PREFS_DEFAULT);
        String retrievedMajor = sharedPref.getString("Major",PREFS_DEFAULT);
        firstName.setText(retrievedFirstName);
        lastName.setText(retrievedLasttName);
        phone.setText(retrievedPhone);
        age.setText(retrievedAge);
        email.setText(retrievedEmail);
        major.setText(retrievedMajor);
    }
}
