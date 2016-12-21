package com.josias.AlbyControl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner registeredDevices;
    private EditText IPID;
    private EditText passwordID, userNameID;
    private ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameID = (EditText) findViewById(R.id.userNameID);
        passwordID = (EditText) findViewById(R.id.passwordID);
        IPID = (EditText) findViewById(R.id.IPID);
        registeredDevices = (Spinner) findViewById(R.id.registeredDevicesID);

        new CacheStorage(this);

        ImageView imageAndroid = (ImageView) findViewById(R.id.imageViewAndroid);
        ImageView imageRaspberry = (ImageView) findViewById(R.id.imageViewRaspberry);
        imageAndroid.setBackground(getResources().getDrawable(R.mipmap.android));
        imageRaspberry.setBackground(getResources().getDrawable(R.mipmap.ic_launcher));
        ((Button) findViewById(R.id.loginButton)).setOnClickListener(this);
        ((Button) findViewById(R.id.registerButtonId)).setOnClickListener(this);
        registeredDevices.setOnItemSelectedListener(this);


    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        userNameID.setText("");
        IPID.setText("");
        passwordID.setText("");

        //set spinner with all registered ips
        List<String> registeredIPs = new ArrayList<>();
        if (CacheStorage.getRecordsAmount() > 0) {
            registeredIPs.add(getString(R.string.yourDevices));

            for (String deviceName : CacheStorage.getDeviceNames())
                registeredIPs.add(deviceName);

            registeredIPs.add(getString(R.string.clearHistoryDevices));

            registeredDevices.setVisibility(View.VISIBLE);
        } else {
            registeredDevices.setVisibility(View.GONE);
        }
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, registeredIPs);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        registeredDevices.setAdapter(dataAdapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        if (pos != 0 && pos == CacheStorage.getRecordsAmount() + 1) { // Clean device history

            userNameID.setText("");
            IPID.setText("");
            passwordID.setText("");

            for (String deviceName : CacheStorage.getDeviceNames())
                dataAdapter.remove(deviceName);
            dataAdapter.notifyDataSetChanged();
            registeredDevices.setVisibility(View.GONE);

            CacheStorage.deleteFile();

        } else if (pos != 0) {
            Device device = CacheStorage.getDevice(parent.getItemAtPosition(pos).toString());

            userNameID.setText(device.getUsername());
            IPID.setText(device.getIP());
            passwordID.setText(device.getPassword());
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        Toast.makeText(getApplicationContext(), "nothing selected",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.loginButton:

                MainActivity.currentDevice = new Device(IPID.getText().toString(),
                        userNameID.getText().toString(), passwordID.getText().toString());

                if (MainActivity.currentDevice.checkFields(this)) {
                    startActivity(new Intent(this, MainActivity.class));
                }

                break;
            case R.id.registerButtonId:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
