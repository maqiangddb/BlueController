package com.some.blue;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

public class MainActivity extends Activity implements SensorEventListener
{

    BroadcastReceiver _receiver;
    TextView debug;
    BluetoothAdapter adapter;
    SensorManager sensorManager;

    TextView acc_data;
    Button acc_stop;
    Button acc_start;
    TextView gra_data;
    Button gra_stop;
    Button gra_start;
    TextView gyro_data;
    Button gyro_stop;
    Button gyro_start;
    TextView light_data;
    Button light_stop;
    Button light_start;
    TextView mag_data;
    Button mag_stop;
    Button mag_start;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Util.LOGI("==========================onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initUI();
        debug = (TextView) findViewById(R.id.debug);

        adapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> devices = adapter.getBondedDevices();
        Util.LOGI("paired device count:"+devices.size()+"--"+devices.toString());

        for (BluetoothDevice device : devices) {
            Util.LOGI("paired device name:"+device.getName());
        }

        _receiver = new BlueBroadcastReceiver();
        registerReceiver(_receiver, BlueBroadcastReceiver.getFilter());

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        Util.LOGI("sensors count:"+sensors.size());
        for (Sensor sensor : sensors) {
           Util.LOGI("==[name:"+sensor.getName()+","
                                    +"type:"+sensor.getType()+","
                                    +"vendor"+sensor.getVendor()+","
                                    +"power:"+sensor.getPower()+","
                                    +"maximumRange:"+sensor.getMaximumRange()+","
                                    +"resolution:"+sensor.getResolution()+","
                                    +"minDelay:"+sensor.getMinDelay()
                                    +"]");
           sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
        }

    }

    private void initUI() {

        acc_data = (TextView) findViewById(R.id.acc_data);
        bindButtonUI(acc_stop, acc_start, Sensor.TYPE_LINEAR_ACCELERATION);
        gra_data = (TextView) findViewById(R.id.gra_data);
        bindButtonUI(gra_stop, gra_start, Sensor.TYPE_GRAVITY);
        gyro_data = (TextView) findViewById(R.id.gyro_data);
        bindButtonUI(gyro_stop, gyro_start, Sensor.TYPE_GYROSCOPE);
        light_data = (TextView) findViewById(R.id.light_data);
        bindButtonUI(light_stop, light_start, Sensor.TYPE_LIGHT);
        mag_data = (TextView) findViewById(R.id.mag_data);
        bindButtonUI(mag_stop, mag_start, Sensor.TYPE_MAGNETIC_FIELD);

    }

    private void bindButtonUI (Button stop, Button start, final int type) {

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorManager.unregisterListener((SensorListener) MainActivity.this, type);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorManager.registerListener((SensorListener) MainActivity.this, type);
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(_receiver);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        //Util.LOGI("=========onSensorChanged===========");
        //Util.LOGI("["+event.sensor.getName()+","+event.values+"]");
        int type = event.sensor.getType();
        int size = event.values.length;
        StringBuilder sb = new StringBuilder();
        sb = debugData(size, sb, event);
        switch (type) {
            case Sensor.TYPE_LINEAR_ACCELERATION:
                acc_data.setText(sb.toString());
                break;
            case Sensor.TYPE_GRAVITY:
                gra_data.setText(sb.toString());
                break;
            case Sensor.TYPE_GYROSCOPE:
                gyro_data.setText(sb.toString());
                break;
            case Sensor.TYPE_LIGHT:
                light_data.setText(sb.toString());
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mag_data.setText(sb.toString());
                break;
        }
    }

    private StringBuilder debugData(int size, StringBuilder sb, SensorEvent event) {
        sb.append("data size:");
        sb.append(size);
        sb.append("[");
        for (int i=0;i<size;i++) {
            float v = event.values[i];
            sb.append(v);
            sb.append(",");
        }
        sb.append("]\n");
        DistanceKeeper keeper = new DistanceKeeper();
        keeper.accelerateToDistance(event.values[0], event.values[1], event.values[2]);
        return sb;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
