package com.jappka.mirrorclient.welcomePage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jappka.mirrorclient.R;
import com.jappka.mirrorclient.widget.CallAPI;
import com.jappka.mirrorclient.widget.WidgetActivity;

/**
 * First activity that users see after open our app
 * TODO: Add logo, verify layout
 */
public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Remove title bar
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        setupMoveToNextActivityAction();

//        networkTest();
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
                Intent nextActivity = new Intent(WelcomeActivity.this, WidgetActivity.class);
                startActivity(nextActivity);

//                Context context = getApplicationContext();
//                CharSequence text = "Hello toast!";
//                int duration = Toast.LENGTH_SHORT;
//
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
//
//                Thread n = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        CallAPI.networkTest();
//                    }
//                });
//                n.start();
            }
        });
    }
}
