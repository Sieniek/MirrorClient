package com.jappka.mirrorclient.welcomePage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jappka.mirrorclient.R;
import com.jappka.mirrorclient.googleAuth.GetGmailTokenActivity;
import com.jappka.mirrorclient.widget.WidgetActivity;

/**
 * First activity that users see after open our app
 * TODO: Add logo, verify layout
 */
public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        setupMoveToNextActivityAction();
    }

    /**
     * Setup actions after clicking on start button
     * App should move to next activity
     * TODO: Verify if getGmailToken is right activity
     */
    private void setupMoveToNextActivityAction(){
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(WelcomeActivity.this, GetGmailTokenActivity.class);
                startActivity(nextActivity);

            }
        });
    }
}
