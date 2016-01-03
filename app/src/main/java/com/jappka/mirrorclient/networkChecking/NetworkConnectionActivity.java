package com.jappka.mirrorclient.networkChecking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jappka.mirrorclient.R;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class NetworkConnectionActivity extends AppCompatActivity {


    EditText hostField;
    TextView responseTextView;
    Button testConnectionButton;


    private final ExecutorService service = Executors.newFixedThreadPool(1);
    private Future<Boolean> networkPingTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_connection);

        setupNetworkTest();
    }

    /**
     * Test network connection
     * Actions on network can't be performed on main thread
     */
    private void setupNetworkTest(){

        hostField = (EditText) findViewById(R.id.inputHostTextField);
        responseTextView = (TextView) findViewById(R.id.responseTextView);
        testConnectionButton = (Button) findViewById(R.id.testConnectionButton);

        testConnectionButton.setOnClickListener(new View.OnClickListener() {

            private void turnOffButton() {
                testConnectionButton.setEnabled(false);
                testConnectionButton.setText(R.string.networkTest_testButton_inProgress);
            }

            private void turnOnButton() {
                testConnectionButton.setEnabled(true);
                testConnectionButton.setText(R.string.networkTest_testButton_normal);
            }

            @Override
            public void onClick(View v) {
                turnOffButton();

                networkPingTask = service.submit(new NetworkPing(hostField.getText().toString()));

                try {
                    Boolean testConnection = networkPingTask.get(1, TimeUnit.SECONDS);
                    responseTextView.setText(testConnection.toString());
                } catch ( TimeoutException e){
                    responseTextView.setText("false");
                    e.printStackTrace();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    responseTextView.setText("And error occurred!");
                }

                turnOnButton();
            }
        });
    }
}
