package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils; //Great with isGlobalPhoneNumber
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecondActivity extends AppCompatActivity {
    private EditText phoneText;
    protected String phoneNumber;
    private String regex = "^\\+?[1]? ?\\(?[0-9]{3}\\)? ?-?[0-9]{3} ?-?[0-9]{4}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        returnToMain();
    }

    public void returnToMain() {
        phoneText = (EditText) findViewById(R.id.editTextPhone);
        phoneText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                        numberCheck();
                }
                return false;
            }
        });
    }

    // Fixing Back button crash!
    @Override
    public void onBackPressed() {
        Intent i = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void numberCheck() {
        phoneNumber = phoneText.getText().toString();
        Intent i_data = new Intent(SecondActivity.this, MainActivity.class);
        if (phoneNumber.matches(regex)) {
            i_data.putExtra("phone_number", phoneNumber);
            setResult(RESULT_OK, i_data);
        } else {
            i_data.putExtra("phone_number", phoneNumber);
            setResult(RESULT_CANCELED, i_data);
        }
        Log.i("From Second Activity", "Phone Number Sent to Main " + phoneNumber);
        finish(); //This should close [terminate] this activity and free up memory
    }
}