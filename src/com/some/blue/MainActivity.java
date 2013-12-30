package com.some.blue;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
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

    double s_x;
    double s_y;
    double s_z;


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
        acc_stop = (Button) findViewById(R.id.acc_stop);
        acc_start = (Button) findViewById(R.id.acc_start);
        bindButtonUI(acc_stop, acc_start, Sensor.TYPE_LINEAR_ACCELERATION);
        gra_data = (TextView) findViewById(R.id.gra_data);
        gra_stop = (Button) findViewById(R.id.gra_start);
        gra_start = (Button) findViewById(R.id.gra_start);
        bindButtonUI(gra_stop, gra_start, Sensor.TYPE_GRAVITY);
        gyro_data = (TextView) findViewById(R.id.gyro_data);
        gyro_stop = (Button) findViewById(R.id.gyro_stop);
        gyro_start = (Button) findViewById(R.id.gyro_start);
        bindButtonUI(gyro_stop, gyro_start, Sensor.TYPE_GYROSCOPE);
        light_data = (TextView) findViewById(R.id.light_data);
        light_stop = (Button) findViewById(R.id.light_stop);
        light_start = (Button) findViewById(R.id.light_start);
        bindButtonUI(light_stop, light_start, Sensor.TYPE_LIGHT);
        mag_data = (TextView) findViewById(R.id.mag_data);
        mag_stop = (Button) findViewById(R.id.mag_stop);
        mag_start = (Button) findViewById(R.id.mag_start);
        bindButtonUI(mag_stop, mag_start, Sensor.TYPE_MAGNETIC_FIELD);

    }

    private void bindButtonUI (Button stop, Button start, final int type) {

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorManager.unregisterListener(MainActivity.this, sensorManager.getDefaultSensor(type));
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorManager.registerListener(MainActivity.this, sensorManager.getDefaultSensor(type), SensorManager.SENSOR_DELAY_UI);
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
        //sb.append("data size:");
        //sb.append(size);
        sb.append("[");
        for (int i=0;i<size;i++) {
            float v = event.values[i];
            sb.append(v);
            sb.append(",");
        }
        sb.append("]\n");
        DistanceKeeper keeper = new DistanceKeeper();
        double[] d_values = keeper.accelerateToDistance(event.values);
        s_x += d_values[0];
        s_y += d_values[1];
        s_z += d_values[2];
        double[] all_values = {s_x, s_y, s_z};
        sb.append("[");
        for (int i=0;i<d_values.length;i++) {
            double v = d_values[i];
            double v1 = all_values[i];
            sb.append(String.format("%f", v1));
        }
        sb.append("]");
        return sb;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
