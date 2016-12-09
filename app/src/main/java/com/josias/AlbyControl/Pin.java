package com.josias.AlbyControl;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Pin {
    private TextView label;
    private Button button;
    private boolean status = false;
    private int pinName;
    private boolean enable;

    public Pin(TextView label, Button button, String labelName, int id, int width, int height,
               Drawable d,int pinName, boolean enable){
        setLabel(label, labelName, width, height, pinName);
        setButton(button, id, width, height, d, enable);
    }

    private void setLabel(TextView label, String labelName, int width, int height, int pinName) {

        this.pinName = pinName;
        this.label = label;
        this.label.setTextColor(Color.WHITE);
        this.label.setText(labelName);
        this.label.setTextSize(TypedValue.COMPLEX_UNIT_PX, 50.f);
        this.label.setTypeface(Typeface.DEFAULT_BOLD);
        this.label.setLayoutParams(new LinearLayout.LayoutParams((int)(width*0.3), (int)(width*0.2)));
    }

    private void setButton(Button button, int id, int width, int height, Drawable d, boolean enable) {
        int size = (int)(width* 0.2);

        this.enable = enable;
        this.button = button;
        this.button.setId(id);
        this.button.setBackground(d);
        this.button.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
        this.button.setText(String.valueOf(id + 1));
        this.button.setTextSize(size / 12);
        this.button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        this.button.setGravity(Gravity.CENTER_HORIZONTAL);
        this.button.setLayoutParams(new LinearLayout.LayoutParams((int)(width * 0.2), (int)(width * 0.2)));
    }

    public Button getButton(){
        return button;
    }

    public TextView getLabel(){
        return label;
    }

    public void setStatus(boolean status){
        this.status=status;
    }

    public boolean getStatus(){
        return status;
    }

    public int getPinName(){
        return pinName;
    }

    public boolean getEnable(){
        return enable;
    }
}
