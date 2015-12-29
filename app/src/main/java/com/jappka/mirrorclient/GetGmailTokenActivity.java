package com.jappka.mirrorclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetGmailTokenActivity extends AppCompatActivity {

    private Button requireGmailAccessButton;
    private TextView gmailTokenLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_gmail_token);

        requireGmailAccessButton = (Button) findViewById(R.id.requireGmailAccessButton);
        gmailTokenLabel = (TextView) findViewById(R.id.gmailTokenLabel);

        requireGmailAccessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gmailTokenLabel.setText("Token:\n" + Integer.toString(1234124));
            }
        });
    }

}
