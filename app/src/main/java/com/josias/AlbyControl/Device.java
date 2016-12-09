package com.josias.AlbyControl;

import android.content.Context;
import android.widget.Toast;



public class Device {
    private String devicename, IP, username, password;

    public Device(String devicename, String IP, String username, String password) {
        this.devicename = devicename;
        this.IP = IP;
        this.username = username;
        this.password = password;
    }

    public Device(String IP, String username, String password) {
        this.IP = IP;
        this.devicename = "default";
        this.username = username;
        this.password = password;
    }

    public boolean checkFields(Context context) {
        if (! devicename.matches(".*\\S+.*")) {
            Toast.makeText(context.getApplicationContext(),  R.string.errorDeviceName,
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (!IP.matches("\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}+\\s*")){
            Toast.makeText(context.getApplicationContext(), R.string.errorIPAddress,
                    Toast.LENGTH_LONG).show();
            return false;
        }

        String[] IPparts = IP.split(".");
        for (String s : IPparts) {
            int i = Integer.parseInt(s);
            if (i < 0 || i > 255) {
                Toast.makeText(context.getApplicationContext(), R.string.errorIPAddress,
                        Toast.LENGTH_LONG).show();

                return false;
            }
        }

        if (! username.matches(".*\\S+.*")) {
            Toast.makeText(context.getApplicationContext(), R.string.errorUserName,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        else if (username.matches("\\s+.*")){
            Toast.makeText(context.getApplicationContext(), R.string.errorUserNameSpaceBefore,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        else if (username.matches(".*\\s+")){
            Toast.makeText(context.getApplicationContext(), R.string.errorUserNameSpaceAfter,
                    Toast.LENGTH_LONG).show();
            return false;
        }


        if (! password.matches(".*\\S+.*")) {
            Toast.makeText(context.getApplicationContext(), R.string.errorPassword,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        else if (password.matches("\\s+.*")){
            Toast.makeText(context.getApplicationContext(),R.string.errorPasswordSpaceBefore,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        else if (password.matches(".*\\s+")){
            Toast.makeText(context.getApplicationContext(), R.string.errorPasswordSpaceAfter,
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public String getUsername() {
        return username;
    }

    public String getIP() {
        return IP;
    }

    public String getdevicename() {
        return devicename;
    }

    public String getPassword() {
        return password;
    }

}
