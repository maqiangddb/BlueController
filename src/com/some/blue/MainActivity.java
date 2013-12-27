package com.some.blue;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

public class MainActivity extends Activity
{

    BroadcastReceiver _receiver;
    TextView debug;
    BluetoothAdapter adapter;
    SensorManager sensorManager;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Util.LOGI("==========================onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
           Util.LOGI("==["+sensor.getName()+"]");
        }

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


}
