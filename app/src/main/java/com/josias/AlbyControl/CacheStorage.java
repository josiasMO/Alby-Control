package com.josias.AlbyControl;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class CacheStorage extends Activity {

    private static final String fileName = "registeredDevices";
    private static File file;
    private static int recordsAmount;


    private static Context context;

    public CacheStorage(Context context) {
        CacheStorage.context = context;
        CacheStorage.file = new File(context.getFilesDir(), fileName);
        CacheStorage.recordsAmount = countRecordsAmount();
        //Toast.makeText(context.getApplicationContext(), "Constructor cache", Toast.LENGTH_SHORT).show();
    }

    public static String[] getDeviceNames() {
       // if (getRecordsAmount() == 0)
       //     return null;

        String[] names = new String[getRecordsAmount()];
        byte[] b = new byte[(int) file.length()];

        try {
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(b, 0, (int) file.length());

        } catch (Exception ex) {
            Toast.makeText(context.getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
            System.out.println(ex.toString());
            return null;
        }

        String[] s = (new String(b)).split("\n");

        for (int i = 0, j = 0; i < s.length; i++) {
            if (i % 4 == 0) {
                names[j] = s[i];
                j++;
            }
        }

        return names;
    }

    public static Device getDevice(String deviceName) {
        //if (getRecordsAmount() == 0)
           // return null;

        Device device = null;
        byte[] b = new byte[(int) file.length()];

        try {
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(b, 0, (int) file.length());

        } catch (Exception ex) {
            Toast.makeText(context.getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
            System.out.println(ex.toString());
            return null;
        }

        String[] s = (new String(b)).split("\n");

        for (int i = 0; i < s.length; i++) {
            if (s[i].equals(deviceName) && i % 4 == 0) { // i%4 makes sure it's the device name field
                device = new Device(s[i], s[i + 1], s[i + 2], s[i + 3]);
                break;
            }
        }

        return device;
    }

    public static int countRecordsAmount() {
        byte[] b = new byte[(int) file.length()];

        try {
            FileInputStream inputStream = new FileInputStream(file);

            inputStream.read(b, 0, (int) file.length());
        } catch(FileNotFoundException ex){
            System.out.println(ex.toString());
            return 0;
        } catch (Exception ex) {
            Toast.makeText(context.getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
            System.out.println(ex.toString());
            return -1;
        }

        String s = new String(b);
        String[] array = s.split("\n");

        return (array.length / 4);

    }

    public static void storeData(Device device) {

        if (getRecordsAmount() > 0 && getDevice(device.getdevicename()) != null) {
            Toast.makeText(context.getApplicationContext(), R.string.errorDeviceAlreadyExists,
                    Toast.LENGTH_LONG).show();
            return;
        }

        String deviceName = device.getdevicename() + "\n", IP = device.getIP() + "\n",
                userName = device.getUsername() + "\n", password = device.getPassword() + "\n";

        System.out.println(deviceName);
        System.out.println(IP);
        System.out.println(userName);
        System.out.println(password);

        FileOutputStream outputStream;

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Toast.makeText(context.getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
                System.out.println(ex.toString());
                return;
            }
        }

        try {
            outputStream = new FileOutputStream(file, true); // true guarantees append
            outputStream.write(deviceName.getBytes());
            outputStream.write(IP.getBytes());
            outputStream.write(userName.getBytes());
            outputStream.write(password.getBytes());
            outputStream.close();
            recordsAmount++;

        } catch (Exception ex) {
            Toast.makeText(context.getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
            System.out.println(ex.toString());
            return;
        }
    }

    public static void printAll() {
        //byte []b = new byte[inputStream.available()];
        byte[] b = new byte[(int) file.length()];

        try {
            FileInputStream inputStream = new FileInputStream(file);

            //inputStream.read(b, 0, inputStream.available());
            inputStream.read(b, 0, (int) file.length());

        } catch (Exception ex) {
            Toast.makeText(context.getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
            System.out.println(ex.toString());
            return;
        }

        String s = new String(b);
        System.out.println(s);

    }

    public static void deleteFile() {
        recordsAmount = 0;
        file.delete();
    }

    public static int getRecordsAmount(){
        return recordsAmount;
    }
}
