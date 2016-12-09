package com.josias.AlbyControl;

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

    private ArrayList<Pin> pinList;
    private LinearLayout linearMain1,linearMain2,linearMain3,linearMain4;

    public static Device currentDevice;

    private double delay = 0.05;
    private int gpio;
    private String ON;
    private String OFF;
    private final String labels[] = {"3.3v", "5v", "GPIO02(SDA1, I²C)", "5v", "GPIO03(SCL1, I²C)",
            "GND", "GPIO04(GPIO_GCLK)", "GPIO14(TXD0)", "GND", "GPIO15(RXD0)",
            "GPIO17(GPIO_GEN0)", "GPIO18(GPIO_GEN1)", "GPIO27(GPIO_GEN2)", "GND", "GPIO22(GPIO_GEN3)",
            "GPIO23(GPIO_GEN4)", "3.3v", "GPIO24(GPIO_GEN5)", "GPIO10(SPI_MOSI)", "GND",
            "GPIO09(SPI_MISO)", "GPIO25(GPIO_GEN6)", "GPIO11(SPI_CLK)", "GPIO08(SPI_CEN0_N)", "GND",
            "GPIO07(SPI_CEN1_N)", "ID_SD(I²C ID EEPROM)", "ID_SC(I²C ID EEPROM)", "GPIO05", "GND",
            "GPIO06", "GPIO12", "GPIO13", "GND", "GPIO19",
            "GPIO16", "GPIO26", "GPIO20", "GND", "GPIO21"};
    private final int pinName[] = {0, 0, 2, 0, 3, 0, 4, 14, 0, 15, 17, 18, 27, 0, 22, 23, 0, 24, 10, 0,
            9, 25, 11, 8, 0, 7, 0, 0, 5, 0, 6, 12, 13, 0, 19, 16, 26, 20, 0, 21};
    private final boolean enable[] = {false, false, true, false, true,
            false, true, true, false, true,
            true, true, true, false, true,
            true, false, true, true, false,
            true, true, true, true, false,
            true, false, false, true, false,
            true, true, true, false, true,
            true, true, true, false, true};

    private final int drawables[] = {
            R.mipmap.dc_power3v, R.mipmap.dc_power_5v,
            R.mipmap.gpio2_3, R.mipmap.dc_power_5v,
            R.mipmap.gpio2_3, R.mipmap.ground,
            R.mipmap.gpio_green, R.mipmap.gpio_orange,
            R.mipmap.ground, R.mipmap.gpio_orange,
            R.mipmap.gpio_green, R.mipmap.gpio_green,
            R.mipmap.gpio_green, R.mipmap.ground,
            R.mipmap.gpio_green, R.mipmap.gpio_green,
            R.mipmap.dc_power_5v, R.mipmap.gpio_green,
            R.mipmap.gpio_purple, R.mipmap.ground,
            R.mipmap.gpio_purple, R.mipmap.gpio_green,
            R.mipmap.gpio_purple, R.mipmap.gpio_purple,
            R.mipmap.ground, R.mipmap.gpio_purple,
            R.mipmap.gpio_yellow, R.mipmap.gpio_yellow,
            R.mipmap.gpio_green, R.mipmap.ground,
            R.mipmap.gpio_green, R.mipmap.gpio_green,
            R.mipmap.gpio_green, R.mipmap.ground,
            R.mipmap.gpio_green, R.mipmap.gpio_green,
            R.mipmap.gpio_green, R.mipmap.gpio_green,
            R.mipmap.ground, R.mipmap.gpio_green
    };

    public void addAllPins(int pinNames[],String labels[],boolean enable[],int drawables[]){
        pinList = new ArrayList<Pin>();

        for (int i=0; i < pinNames.length; i++) {
            addPin(labels[i],i,getResources().getDrawable(drawables[i]),pinNames[i],enable[i]);
        }

        for (Pin pin : pinList){
            pin.getButton().setOnClickListener(getOnClickDoSomething(pin));
        }

        int i = 0;
        for (Pin pin : pinList){
            if(i%2 == 0) {
                pin.getLabel().setGravity(Gravity.CENTER|Gravity.RIGHT);
                linearMain1.addView(pin.getLabel());
                linearMain2.addView(pin.getButton());
                i++;
            }
            else {
                pin.getLabel().setGravity(Gravity.CENTER|Gravity.LEFT);
                linearMain3.addView(pin.getButton());
                linearMain4.addView(pin.getLabel());
                i++;
            }
        }
    }

    private void addPin(String labelName,int id, Drawable d,int pinName, boolean enable) {
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        pinList.add(new Pin(new TextView(this),new Button(this),labelName,id,width,height,d,
                pinName,enable));
    }

    public View.OnClickListener getOnClickDoSomething(final Pin pin){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pin.getStatus()) {
                    if(pin.getEnable()) {
                        pin.getButton().getBackground().clearColorFilter();
                        pin.setStatus(!pin.getStatus());

                        gpio = pin.getPinName();
                        ON = "cd ~ && python teste.py";
                        /*ON = "echo " + gpio + " > /sys/class/gpio/export && sleep " +
                                delay + " && echo out > /sys/class/gpio/gpio" + gpio +
                                "/direction && sleep " + delay +
                                " && echo 1 > /sys/class/gpio/gpio" + gpio + "/value";*/
                        new Communicate().execute(ON, currentDevice);
                        //Toast.makeText(getBaseContext(), pin.getPinName()+ " ON", Toast.LENGTH_LONG).show();
                    }

                } else {
                    if(pin.getEnable()) {
                        pin.getButton().getBackground().setColorFilter(0x77000000,
                                PorterDuff.Mode.SRC_ATOP);
                        pin.setStatus(!pin.getStatus());

                        gpio = pin.getPinName();
                        OFF = "echo " + gpio + " > /sys/class/gpio/unexport";
                        new Communicate().execute(OFF, currentDevice);

                        //Toast.makeText(getBaseContext(), pin.getPinName() + " OFF", Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearMain1 = (LinearLayout) findViewById(R.id.linear_view1);
        linearMain2 = (LinearLayout) findViewById(R.id.linear_view2);
        linearMain3 = (LinearLayout) findViewById(R.id.linear_view3);
        linearMain4 = (LinearLayout) findViewById(R.id.linear_view4);

        addAllPins(pinName,labels,enable,drawables);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
