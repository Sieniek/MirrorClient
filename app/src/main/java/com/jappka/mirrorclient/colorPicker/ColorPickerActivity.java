package com.jappka.mirrorclient.colorPicker;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.jappka.mirrorclient.R;
import com.jappka.mirrorclient.widget.CallAPI;
import com.jappka.mirrorclient.widget.Widget;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import org.w3c.dom.Text;

import java.io.IOException;

public class ColorPickerActivity extends Activity {

    private SeekBar redSeekBar, blueSeekBar, greenSeekBar;
    private RelativeLayout background;
    private TextView redTextView, blueTextView, greenTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
        setupSeekBars();
    }

    private void updateColor(final String color){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                CallAPI.setColor(color);
            }
        });
        thread.start();
    }

    private void setupSeekBars(){
        background = (RelativeLayout) findViewById(R.id.background);

        redSeekBar = (SeekBar) findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar) findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar) findViewById(R.id.blueSeekBar);

        blueTextView = (TextView) findViewById(R.id.blueTextView);
        greenTextView = (TextView) findViewById(R.id.greenTextView);
        redTextView = (TextView) findViewById(R.id.redTextView);

        SeekBar list[] = {redSeekBar, greenSeekBar, blueSeekBar};


        greenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                background.setBackgroundColor(Color.parseColor(
                        "#"
                                + String.format("%02X", redSeekBar.getProgress())
                                + String.format("%02X", greenSeekBar.getProgress())
                                + String.format("%02X", blueSeekBar.getProgress())
                ));
                background.invalidate();
                greenTextView.setText(Integer.toString(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateColor(String.format("%02X", redSeekBar.getProgress())
                        + String.format("%02X", greenSeekBar.getProgress())
                        + String.format("%02X", blueSeekBar.getProgress()));
            }
        });
        redSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                background.setBackgroundColor(Color.parseColor(
                        "#"
                                + String.format("%02X", redSeekBar.getProgress())
                                + String.format("%02X", greenSeekBar.getProgress())
                                + String.format("%02X", blueSeekBar.getProgress())
                ));
                background.invalidate();
                redTextView.setText(Integer.toString(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateColor(String.format("%02X", redSeekBar.getProgress())
                        + String.format("%02X", greenSeekBar.getProgress())
                        + String.format("%02X", blueSeekBar.getProgress()));
            }
        });
        blueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                background.setBackgroundColor(Color.parseColor(
                        "#"
                                + String.format("%02X", redSeekBar.getProgress())
                                + String.format("%02X", greenSeekBar.getProgress())
                                + String.format("%02X", blueSeekBar.getProgress())
                ));
                background.invalidate();
                blueTextView.setText(Integer.toString(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateColor(String.format("%02X", redSeekBar.getProgress())
                        + String.format("%02X", greenSeekBar.getProgress())
                        + String.format("%02X", blueSeekBar.getProgress()));

            }
        });
    }

}
