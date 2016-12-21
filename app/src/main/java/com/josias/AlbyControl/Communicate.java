package com.josias.AlbyControl;

import android.os.AsyncTask;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.io.InputStream;

public class Communicate extends AsyncTask<Object, Void, Void> {
    ChannelExec channelssh;
    InputStream in;

    protected Void doInBackground(Object... args) {
        try{
            Device device = (Device) args[1];

            JSch jsch = new JSch();
            Session session = jsch.getSession(device.getUsername(),
                    device.getIP(), 22);
            session.setPassword(device.getPassword());

            session.setConfig("StrictHostKeyChecking", "no");

            session.connect();

            channelssh = (ChannelExec)
                    session.openChannel("exec");

            in = channelssh.getInputStream();
            // Execute command
            System.out.println(device.getIP());
            System.out.println(device.getUsername());
            System.out.println(device.getPassword());

            channelssh.setCommand((String)args[0]);
            channelssh.connect();

            byte[] tmp = new byte[1024];
            while (true) {
                try {
                    while (in.available() > 0) {
                        int i = 0;
                        try {
                            i = in.read(tmp, 0, 1024);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (i < 0) {
                            break;
                        }
                        MainActivity.textReceived = new String(tmp, 0, i);
                        //System.out.print(MainActivity.textReceived);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (channelssh.isClosed()) {
                    System.out.println("exit-status: " + channelssh.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }

            channelssh.disconnect();

        }
        catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return null;
    }
}