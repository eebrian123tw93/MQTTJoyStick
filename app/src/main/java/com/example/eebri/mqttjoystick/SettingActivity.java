package com.example.eebri.mqttjoystick;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private EditText host1EditText;
    private EditText host2EditText;
    private EditText host3EditText;
    private EditText host4EditText;
    private EditText portEditText;
    private EditText topicEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sharedPreferences = getSharedPreferences("SettingActivity", MODE_PRIVATE);
        host1EditText = findViewById(R.id.host1_editText);
        host2EditText = findViewById(R.id.host2_editText);
        host3EditText = findViewById(R.id.host3_editText);
        host4EditText = findViewById(R.id.host4_editText);
        portEditText = findViewById(R.id.port_editText);
        topicEditText = findViewById(R.id.topic_editText);
        setEditTextViews();

        Button connectButton = findViewById(R.id.connect_button);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                String hostName = host1EditText.getText().toString() + '.'
                        + host2EditText.getText().toString() + '.'
                        + host3EditText.getText().toString() + '.'
                        + host4EditText.getText().toString() ;
                intent.putExtra("host", hostName);
                intent.putExtra("port", portEditText.getText().toString());
                intent.putExtra("topic", topicEditText.getText().toString());
                startActivity(intent);
                sharedPreferences.edit()
                        .putString("host1EditText", host1EditText.getText().toString())
                        .putString("host2EditText", host2EditText.getText().toString())
                        .putString("host3EditText", host3EditText.getText().toString())
                        .putString("host4EditText", host4EditText.getText().toString())
                        .putString("portEditText", portEditText.getText().toString())
                        .putString("topicEditText", topicEditText.getText().toString())
                        .apply();
            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void setEditTextViews() {
        String host1EditTextString = sharedPreferences.getString("host1EditText", "");
        String host2EditTextString = sharedPreferences.getString("host2EditText", "");
        String host3EditTextString = sharedPreferences.getString("host3EditText", "");
        String host4EditTextString = sharedPreferences.getString("host4EditText", "");
        String portEditTextString = sharedPreferences.getString("portEditText", "");
        String topicEditTextString = sharedPreferences.getString("topicEditText", "");
        if (!host1EditTextString.isEmpty()) {
            host1EditText.setText(host1EditTextString);
        }
        if (!host2EditTextString.isEmpty()) {
            host2EditText.setText(host2EditTextString);
        }
        if (!host3EditTextString.isEmpty()) {
            host3EditText.setText(host3EditTextString);
        }
        if (!host4EditTextString.isEmpty()) {
            host4EditText.setText(host4EditTextString);
        }

        if (!portEditTextString.isEmpty()) {
            portEditText.setText(portEditTextString);
        } else {
            portEditText.setText("1883");
        }

        if (!topicEditTextString.isEmpty()) {
            topicEditText.setText(topicEditTextString);
        } else {
            topicEditText.setText("/ME301/00/cmd");
        }
    }


}
