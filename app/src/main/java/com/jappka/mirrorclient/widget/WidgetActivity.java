package com.jappka.mirrorclient.widget;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jappka.mirrorclient.R;

public class WidgetActivity extends Activity {

    private Spinner widgetSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);

        //setupSpinner();
    }

    /**
     * Add options to spinner
     */
    private void setupSpinner(){
        String[] options = {"Twitter", "Clock and Weather", "Spotify"};

        widgetSpinner = (Spinner) findViewById(R.id.widgetTypeSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        widgetSpinner.setAdapter(adapter);

        widgetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
