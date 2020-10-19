package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    String phoneNumber;
    Button firstButton;
    Button secondButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstButton = (Button) findViewById(R.id.toSecondActivity);
        secondButton = (Button) findViewById(R.id.toDialer);
        secondButton.setEnabled(false);
        toSecondActivity();
        toDialer();
    }

    public void toSecondActivity(){
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSecondActivity();
            }
        });
    }

    public void openSecondActivity() {
        Intent i = new Intent(this, SecondActivity.class);
        startActivityForResult(i,1);

    }

    public void toDialer() {
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri phoneFormat = Uri.parse("tel:" + phoneNumber);
                Intent i_dialer = new Intent(Intent.ACTION_DIAL, phoneFormat);
                try {
                    startActivity(i_dialer);
                }catch (Exception e){
                    Log.e("TAG", e.toString());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) { //This code should match the "1" we passed in startActivityForResult
            phoneNumber = data.getStringExtra("phone_number");
            if (resultCode == RESULT_OK) {
                secondButton.setEnabled(true);
                // Fixed: Show number in Main Activity
                TextView tv = (TextView) findViewById(R.id.mainPhoneText);
                tv.setText("Entered Number: " + phoneNumber);
                Log.i("From Main Activity", "Phone Number Received " + phoneNumber);
            }
            if (resultCode == RESULT_CANCELED){
                secondButton.setEnabled(false);
                Context context = getApplicationContext();
                CharSequence text = "You have entered an incorrect number: " + phoneNumber;
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Log.i("From Main Activity", "Wrong Format");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}