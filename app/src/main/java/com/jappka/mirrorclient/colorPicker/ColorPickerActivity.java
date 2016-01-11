package com.jappka.mirrorclient.colorPicker;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.jappka.mirrorclient.R;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

public class ColorPickerActivity extends Activity {

    private Button colorPickerButton;
    private Button sendColorToServerButton;
    private ColorPicker colorPicker;
    private int selectedColorRGB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        setupColorPicker();
        setupColorPickerButton();
        setupSendColorToServerButton();
    }


    private void setupColorPicker(){
        colorPicker = new ColorPicker(ColorPickerActivity.this);




    }

    private void setupColorPickerButton(){
        colorPickerButton = (Button) findViewById(R.id.colorPickerButton);

        colorPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPicker.show();
                Button okColor = (Button)colorPicker.findViewById(R.id.okColorButton);
                okColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedColorRGB = colorPicker.getColor();

                        colorPicker.dismiss();
                    }
                });
            }
        });
    }

    private void setupSendColorToServerButton(){
        sendColorToServerButton = (Button) findViewById(R.id.sendColorToServerButton);

        sendColorToServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hexColor = String.format("#%06X", (0xFFFFFF & selectedColorRGB));

            }
        });
    }

}
