package com.josias.AlbyControl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    //private Button bReg;
    private EditText registerDeviceName, registerUsername, registerPassword, registerIP;
    //private Device device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerDeviceName = (EditText) findViewById(R.id.registerDeviceNameID);
        registerIP = (EditText) findViewById(R.id.registerIPID);
        registerUsername = (EditText) findViewById(R.id.registerUsernameID);
        registerPassword = (EditText) findViewById(R.id.registerPasswordID);
        ((Button) findViewById(R.id.registerButtonID)).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerButtonID:
                String devicename = registerDeviceName.getText().toString();
                String IP = registerIP.getText().toString();
                String username = registerUsername.getText().toString();
                String password = registerPassword.getText().toString();

                MainActivity.currentDevice = new Device(devicename,IP,username,password);

                if (MainActivity.currentDevice.checkFields(this)) {

                    CacheStorage.storeData(MainActivity.currentDevice);

                    startActivity(new Intent(this, MainActivity.class));

                    finish();
                }
                break;
        }
    }
}
