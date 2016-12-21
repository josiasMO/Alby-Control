package com.josias.AlbyControl;

import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.StringTokenizer;

public class editJoints extends AppCompatActivity {

    Button getJoints, updateJoints;
    String command;
    EditText[] Centers = new EditText[9];
    EditText[] Min = new EditText[9];
    EditText[] Max = new EditText[9];
    EditText[] MinAngle = new EditText[9];
    EditText[] MaxAngle = new EditText[9];

    String[] centers  = new String[9];
    String[] min  = new String[9];
    String[] max  = new String[9];
    String[] minAngle  = new String[9];
    String[] maxAngle  = new String[9];

    public static String returnValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_joints);

        getJoints = (Button)findViewById(R.id.button_getJointValues);
        updateJoints = (Button)findViewById(R.id.button_updateJoints);

        handleEditText();
        handleButtons();


    }

    public void handleButtons(){
        getJoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                command = "cd ~/alby/movements && cat joints.dat";
                new Communicate().execute(command, MainActivity.currentDevice);
                command = "cd ~/alby/movements && cat joints.dat";
                new Communicate().execute(command, MainActivity.currentDevice);
                updateTableValues();

            }
        });
        updateJoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStringArray();
                generateReturnString();
                command = "cd ~/alby/movements && tee joints.dat <<< \""+ returnValues +"\"";
                new Communicate().execute(command, MainActivity.currentDevice);

            }
        });

    }

    public void handleEditText() {

        Centers[0] = (EditText)findViewById(R.id.editTextCenter1);
        Centers[1] = (EditText)findViewById(R.id.editTextCenter2);
        Centers[2] = (EditText)findViewById(R.id.editTextCenter3);
        Centers[3] = (EditText)findViewById(R.id.editTextCenter4);
        Centers[4] = (EditText)findViewById(R.id.editTextCenter5);
        Centers[5] = (EditText)findViewById(R.id.editTextCenter6);
        Centers[6] = (EditText)findViewById(R.id.editTextCenter7);
        Centers[7] = (EditText)findViewById(R.id.editTextCenter8);
        Centers[8] = (EditText)findViewById(R.id.editTextCenter9);

        Min[0] = (EditText)findViewById(R.id.editTextMin1);
        Min[1] = (EditText)findViewById(R.id.editTextMin2);
        Min[2] = (EditText)findViewById(R.id.editTextMin3);
        Min[3] = (EditText)findViewById(R.id.editTextMin4);
        Min[4] = (EditText)findViewById(R.id.editTextMin5);
        Min[5] = (EditText)findViewById(R.id.editTextMin6);
        Min[6] = (EditText)findViewById(R.id.editTextMin7);
        Min[7] = (EditText)findViewById(R.id.editTextMin8);
        Min[8] = (EditText)findViewById(R.id.editTextMin9);

        Max[0] = (EditText)findViewById(R.id.editTextMax1);
        Max[1] = (EditText)findViewById(R.id.editTextMax2);
        Max[2] = (EditText)findViewById(R.id.editTextMax3);
        Max[3] = (EditText)findViewById(R.id.editTextMax4);
        Max[4] = (EditText)findViewById(R.id.editTextMax5);
        Max[5] = (EditText)findViewById(R.id.editTextMax6);
        Max[6] = (EditText)findViewById(R.id.editTextMax7);
        Max[7] = (EditText)findViewById(R.id.editTextMax8);
        Max[8] = (EditText)findViewById(R.id.editTextMax9);

        MinAngle[0] = (EditText)findViewById(R.id.editTextMinAng1);
        MinAngle[1] = (EditText)findViewById(R.id.editTextMinAng2);
        MinAngle[2] = (EditText)findViewById(R.id.editTextMinAng3);
        MinAngle[3] = (EditText)findViewById(R.id.editTextMinAng4);
        MinAngle[4] = (EditText)findViewById(R.id.editTextMinAng5);
        MinAngle[5] = (EditText)findViewById(R.id.editTextMinAng6);
        MinAngle[6] = (EditText)findViewById(R.id.editTextMinAng7);
        MinAngle[7] = (EditText)findViewById(R.id.editTextMinAng8);
        MinAngle[8] = (EditText)findViewById(R.id.editTextMinAng9);

        MaxAngle[0] = (EditText)findViewById(R.id.editTextMaxAng1);
        MaxAngle[1] = (EditText)findViewById(R.id.editTextMaxAng2);
        MaxAngle[2] = (EditText)findViewById(R.id.editTextMaxAng3);
        MaxAngle[3] = (EditText)findViewById(R.id.editTextMaxAng4);
        MaxAngle[4] = (EditText)findViewById(R.id.editTextMaxAng5);
        MaxAngle[5] = (EditText)findViewById(R.id.editTextMaxAng6);
        MaxAngle[6] = (EditText)findViewById(R.id.editTextMaxAng7);
        MaxAngle[7] = (EditText)findViewById(R.id.editTextMaxAng8);
        MaxAngle[8] = (EditText)findViewById(R.id.editTextMaxAng9);

    }

    public void updateTableValues(){
        String[] temp;

        String receivedData = MainActivity.textReceived;

        if(receivedData != null) {
            String[] items = receivedData.split("]");

            temp = items[0].split("\\[");
            for(int i = 0; i < 9; i++)
                centers = temp[1].split(",");

            temp = items[1].split("\\[");
            for(int i = 0; i < 9; i++)
                min = temp[1].split(",");

            temp = items[2].split("\\[");
            for(int i = 0; i < 9; i++)
                max = temp[1].split(",");

            temp = items[3].split("\\[");
            for(int i = 0; i < 9; i++)
                minAngle = temp[1].split(",");

            temp = items[4].split("\\[");
            for(int i = 0; i < 9; i++)
                maxAngle = temp[1].split(",");

            for(int i = 0; i<9; i++){
                Centers[i].setText(centers[i]);
                Min[i].setText(min[i]);
                Max[i].setText(max[i]);
                MinAngle[i].setText(minAngle[i]);
                MaxAngle[i].setText(maxAngle[i]);

            }

            System.out.println("Centers: " + Arrays.asList(centers));
            System.out.println("Min: " +Arrays.asList(min));
            System.out.println("Max: " +Arrays.asList(max));
            System.out.println("MinAngle: " +Arrays.asList(minAngle));
            System.out.println("MaxAngle: " +Arrays.asList(maxAngle));

        }




    }

    public void updateStringArray(){
        for(int i = 0; i<9;i++){
            centers[i] = Centers[i].getText().toString();
            min[i] = Min[i].getText().toString();
            max[i] = Max[i].getText().toString();
            minAngle[i] = MinAngle[i].getText().toString();
            maxAngle[i] = MaxAngle[i].getText().toString();
        }
    }

    public void generateReturnString(){

        returnValues = "jointCenters = " + Arrays.asList(centers)+"\n";
        returnValues = returnValues+ "jointMinimuns = " + Arrays.asList(min)+"\n";
        returnValues = returnValues+ "jointMaximuns = " + Arrays.asList(max)+"\n";
        returnValues = returnValues+ "jointAngleMins = " + Arrays.asList(minAngle)+"\n";
        returnValues = returnValues+ "jointAngleMaxs = " + Arrays.asList(maxAngle)+"\n";
        System.out.println(returnValues);
    }
}
