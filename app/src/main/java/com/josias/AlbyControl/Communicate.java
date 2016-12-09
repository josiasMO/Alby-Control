package com.josias.AlbyControl;

import android.os.AsyncTask;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class Communicate extends AsyncTask<Object, Void, Void> {
    protected Void doInBackground(Object... args) {

        try{
            Device device = (Device) args[1];

            JSch jsch = new JSch();
            Session session = jsch.getSession(device.getUsername(),
                    device.getIP(), 22);
            session.setPassword(device.getPassword());

            session.setConfig("StrictHostKeyChecking", "no");

            session.connect();

            ChannelExec channelssh = (ChannelExec)
                    session.openChannel("exec");

            // Execute command
            System.out.println(device.getIP());
            System.out.println(device.getUsername());
            System.out.println(device.getPassword());

            channelssh.setCommand((String)args[0]);
            channelssh.connect();
            channelssh.disconnect();

        }
        catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return null;
    }
}