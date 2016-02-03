package com.jappka.mirrorclient.welcomePage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jappka.mirrorclient.R;
import com.jappka.mirrorclient.colorPicker.ColorPickerActivity;
import com.jappka.mirrorclient.googleAuth.GetGmailTokenActivity;
import com.jappka.mirrorclient.networkChecking.NetworkConnectionActivity;
import com.jappka.mirrorclient.widget.WidgetActivity;

/**
 * First activity that users see after open our app
 * TODO: Add logo, verify layout
 */
public class WelcomeActivity extends Activity {


    RelativeLayout welcomeButtonsContainer, welcomePageFirstLayout;
    Button widgetActivityButton, networkCheckingButton, googleAuthButton, colorPicketButton,
            startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        setupElements();
        setupStartButton();
        setupWidgetActivityButton();
        setupNetworkCheckingButton();
        setupGoogleAuthButton();
        setupColorPicketButton();
    }

    private void setupElements(){
        welcomePageFirstLayout = (RelativeLayout) findViewById(R.id.welcomePageFirstLayout);
        welcomeButtonsContainer = (RelativeLayout) findViewById(R.id.welcomeButtonsContainer);
        widgetActivityButton = (Button) findViewById(R.id.widgetButton);
        networkCheckingButton = (Button) findViewById(R.id.networkCheckingButton);
        googleAuthButton = (Button) findViewById(R.id.googleAuthButton);
        colorPicketButton = (Button) findViewById(R.id.colorPickerButton);
        startButton = (Button) findViewById(R.id.startButton);
    }

    /**
     * Setup actions after clicking on start button
     * App should move to next activity
     * TODO: Verify if getGmailToken is right activity
     */
    private void setupStartButton(){
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcomeButtonsContainer.setVisibility(View.VISIBLE);
                welcomePageFirstLayout.setVisibility(View.GONE);
            }
        });
    }
    private void setupWidgetActivityButton(){
        widgetActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(WelcomeActivity.this, WidgetActivity.class);
                startActivity(nextActivity);

            }
        });
    }

    private void setupNetworkCheckingButton(){
        networkCheckingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(WelcomeActivity.this, NetworkConnectionActivity.class);
                startActivity(nextActivity);

            }
        });
    }

    private void setupGoogleAuthButton(){
        googleAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(WelcomeActivity.this, GetGmailTokenActivity.class);
                startActivity(nextActivity);

            }
        });
    }

    private void setupColorPicketButton(){
        colorPicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(WelcomeActivity.this, ColorPickerActivity.class);
                startActivity(nextActivity);

            }
        });
    }

}
