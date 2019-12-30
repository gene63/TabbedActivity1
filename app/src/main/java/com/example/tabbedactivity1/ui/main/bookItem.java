package com.example.tabbedactivity1.ui.main;

import android.graphics.drawable.Drawable;

public class bookItem {
    private Drawable img;
    private String strVal;

    public void setImg(Drawable image) {
        img = image;
    }

    public void setVal(String str) {
        strVal = str;
    }

    public Drawable getImg() {
        return this.img;
    }

    public String getVal() {
        return this.strVal;
    }
}
