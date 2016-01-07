package com.jappka.mirrorclient.widget;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.jappka.mirrorclient.R;

import java.util.ArrayList;

public class WidgetActivity extends Activity {

    private final int WIDTH_BOARD_PROGRESS = 19;
    private final int HEIGHT_BOARD_PROGRESS = 16;
    private int width_board, height_board;
    private int width_board_unit, height_board_unit;


    private ArrayList<Widget> widgetList = new ArrayList<>();
    private Widget currentSelectedWidget;

    private Spinner widgetSpinner;
    private RelativeLayout drawBoard;
    private RelativeLayout twitterIcon, newsIcon, gmailIcon, calendarIcon, spotifyIcon, weatherIcon;
    private SeekBar positionXSeekBar, positionYSeekBar, widthSeekBar, heightSeekBar;
    private Button addWidgetButton, removeWidgetButton, removeAllWidgetsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);

        initializeUIElements();
        setupWidgetList();
        /**
         *  Setup board include also setups of bars and spinner
         *  since those setups needs to be performed after all layout will be loaded
         */
        setupBoard();
        setupAddWidgetButton();
        setupRemoveWidgetButton();
        setupRemoveAllWidgetsButton();
    }

    /**
     * Find all elements on this activity
     * Prepare objects to further use
     */
    public void initializeUIElements(){

        drawBoard = (RelativeLayout) findViewById(R.id.drawBoard);

        /* Widgets icons */
        twitterIcon = (RelativeLayout) findViewById(R.id.twitterIcon);
        newsIcon = (RelativeLayout) findViewById(R.id.newsIcon);
        gmailIcon = (RelativeLayout) findViewById(R.id.gmailIcon);
        calendarIcon = (RelativeLayout) findViewById(R.id.calendarIcon);
        spotifyIcon = (RelativeLayout) findViewById(R.id.spotifyIcon);
        weatherIcon = (RelativeLayout) findViewById(R.id.weatherIcon);

        /* Seek bars */
        positionXSeekBar = (SeekBar) findViewById(R.id.positionXSeekBar);
        positionYSeekBar = (SeekBar) findViewById(R.id.positionYSeekBar);
        widthSeekBar = (SeekBar) findViewById(R.id.widthSeekBar);
        heightSeekBar = (SeekBar) findViewById(R.id.heightSeekBar);

        /* Buttons*/
        addWidgetButton = (Button) findViewById(R.id.addWidgetButton);
        removeWidgetButton = (Button) findViewById(R.id.removeWidgetButton);
        removeAllWidgetsButton = (Button) findViewById(R.id.removeAllWidgetsButton);

    }

    private void setupBoard(){

        drawBoard.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width_board = drawBoard.getWidth();
                height_board = drawBoard.getHeight();

                width_board_unit = width_board / WIDTH_BOARD_PROGRESS;
                height_board_unit = height_board / HEIGHT_BOARD_PROGRESS;

                System.out.print("Width unit " + width_board_unit);
                System.out.print("height unit " + height_board_unit);
                System.out.print("board width " + width_board);
                System.out.print("height board " + height_board);

                setupIcons();
                setupSpinner();
                setupPositionXSeekBar();
                setupPositionYSeekBar();
                setupWidthSeekBar();
                setupHeightSeekBar();

                if (Build.VERSION.SDK_INT < 16) {
                    drawBoard.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    drawBoard.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });

    }

    private void setupIcons(){
        for(Widget widget:widgetList){
            widget.getIcon().getLayoutParams().height = height_board_unit;
            widget.getIcon().getLayoutParams().width = width_board_unit;
        }
    }

    /**
     * Create and fill list of Mirror Widgets
     */
    private void setupWidgetList(){
        widgetList = new ArrayList<>();
        widgetList.add(new Widget("Spotify", "spotify-plugin", spotifyIcon));
        widgetList.add(new Widget("Twitter", "twitter", twitterIcon));
        widgetList.add(new Widget("News", "news", newsIcon));
        widgetList.add(new Widget("Clock and Weather", "clock-and-weather", weatherIcon));
        widgetList.add(new Widget("Gmail", "gmail", gmailIcon));
        widgetList.add(new Widget("Calendar", "calendar", calendarIcon));
    }

    /**
     * Add options to spinner
     */
    private void setupSpinner() {

        ArrayList<String> options = new ArrayList<>();
        String firstElement = "";
        options.add(firstElement);

        for (Widget w : widgetList) {
            options.add(w.getName());
        }

        widgetSpinner = (Spinner) findViewById(R.id.widgetTypeSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        widgetSpinner.setAdapter(adapter);

        widgetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // set icon visible
                if (position != 0) {
                    // remove all icons from board
                    for(Widget widget:widgetList) {
                        widget.getIcon().setVisibility(View.GONE);
                    }

                    // position - 1 because first element on spinner is empty
                    currentSelectedWidget = widgetList.get(position - 1);
                    currentSelectedWidget.getIcon().setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupPositionXSeekBar(){

        positionXSeekBar.setMax(WIDTH_BOARD_PROGRESS - 1);
        positionXSeekBar.setProgress(0);
        positionXSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentSelectedWidget.getIcon().setX(progress * width_board_unit);
                currentSelectedWidget.setxPosition(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void setupPositionYSeekBar(){

        positionYSeekBar.setMax(HEIGHT_BOARD_PROGRESS - 1);
        positionYSeekBar.setProgress(0);
        positionYSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentSelectedWidget.getIcon().setY(progress * height_board_unit);
                currentSelectedWidget.setyPosition(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }


    private void setupWidthSeekBar(){

        widthSeekBar.setMax(WIDTH_BOARD_PROGRESS - 1);
        widthSeekBar.setProgress(0);
        widthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                RelativeLayout currentIcon = currentSelectedWidget.getIcon();
                ViewGroup.LayoutParams p = currentIcon.getLayoutParams();

                p.width = (progress + 1) * height_board_unit;
                currentSelectedWidget.setWidth(progress);

                currentIcon.setLayoutParams(p);
                currentIcon.invalidate();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void setupHeightSeekBar(){

        heightSeekBar.setMax(HEIGHT_BOARD_PROGRESS - 1);
        heightSeekBar.setProgress(0);
        heightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                RelativeLayout currentIcon = currentSelectedWidget.getIcon();
                ViewGroup.LayoutParams p = currentIcon.getLayoutParams();

                p.height = (progress + 1) * height_board_unit;
                currentSelectedWidget.setHeight(progress);

                currentIcon.setLayoutParams(p);
                currentIcon.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void setupAddWidgetButton(){
        addWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        CallAPI.addWidget(currentSelectedWidget);
                    }
                });
                thread.start();
            }
        });
    }

    private void setupRemoveWidgetButton(){
        removeWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CallAPI.removeWidget(currentSelectedWidget);
                    }
                });
                thread.start();
            }
        });
    }

    private void setupRemoveAllWidgetsButton(){
        removeAllWidgetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(Widget widget:widgetList) {
                            CallAPI.removeWidget(widget);
                        }
                    }
                });
                thread.start();
            }
        });
    }

}
