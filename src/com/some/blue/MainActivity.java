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
    TextView gra_data;
    TextView gyro_data;
    TextView light_data;
    TextView mag_data;
    Button stop;
    Button start;


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
        gra_data = (TextView) findViewById(R.id.gra_data);
        gyro_data = (TextView) findViewById(R.id.gyro_data);
        light_data = (TextView) findViewById(R.id.light_data);
        mag_data = (TextView) findViewById(R.id.mag_data);
        stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorManager.unregisterListener(MainActivity.this);
            }
        });
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        sb.append("]");
        return sb;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
