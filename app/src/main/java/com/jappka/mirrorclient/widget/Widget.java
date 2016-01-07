package com.jappka.mirrorclient.widget;

import android.widget.RelativeLayout;

/**
 * Widget contains information about each widget in system
 * Created by psienkiewicz on 05/01/16.
 */
public class Widget {
    private String name, apiName;
    private int width;
    private int height;
    private int xPosition;
    private int yPosition;

    private RelativeLayout icon;

    public Widget(String name, String apiName, RelativeLayout icon) {
        this.setApiName(apiName);
        this.setName(name);
        this.setIcon(icon);
    }

    public Widget(String name, String apiName, int width, int height, int xPosition, int yPosition) {
        this.setApiName(apiName);
        this.setName(name);
        this.setWidth(width);
        this.setHeight(height);
        this.setxPosition(xPosition);
        this.setyPosition(yPosition);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public RelativeLayout getIcon() {
        return icon;
    }

    public void setIcon(RelativeLayout icon) {
        this.icon = icon;
    }
}
