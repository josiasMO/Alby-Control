package com.josias.AlbyControl;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static Device currentDevice;
    public static String textReceived;

    Button testJoints, speak, editJoints;
    TextView textSpeak;
    String command;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textSpeak =(TextView)findViewById(R.id.textSpeak);
        testJoints = (Button)findViewById(R.id.button_testJoints);
        speak = (Button)findViewById(R.id.button_TalkText);
        editJoints = (Button)findViewById(R.id.button_editJoints);

        handleButtons();

}
    protected void handleButtons(){
        testJoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                command = "cd ~/alby && python test_audio_module.py";
                new Communicate().execute(command, currentDevice);

            }
        });
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String readText = String.valueOf(textSpeak.getText());
                command = "cd ~/alby && python test_fala_frase.py " +readText;
                new Communicate().execute(command, currentDevice);

            }
        });
        editJoints.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),editJoints.class);
                startActivity(i);
            }
        });
    }


}
