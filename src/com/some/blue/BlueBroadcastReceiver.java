package com.some.blue;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by mqddb on 13-12-27.
 */
public class BlueBroadcastReceiver extends BroadcastReceiver {

    ArrayList<BluetoothDevice> devices = new ArrayList();


    @Override
    public void onReceive(Context context, Intent intent) {
        Util.LOGI("onReceive intent:"+intent+"--"+BlueBroadcastReceiver.class.getName());
        String action = intent.getAction();
        if (action.equals(BluetoothDevice.ACTION_FOUND)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            devices.add(device);
        }

    }

    public static IntentFilter getFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction(BluetoothDevice.ACTION_CLASS_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_NAME_CHANGED);
        //filter.addAction(BluetoothDevice.ACTION_UUID);
        return filter;
    }

    class DeviceManager{

        public void getDevice() {

        }

    }

    private class ConnectThread extends Thread {

        private final UUID MY_UUID = UUID.randomUUID();
        private final BluetoothDevice _device;
        private final BluetoothSocket _socket;
        private final BluetoothAdapter _adapter;
        private InputStream _inputStream;
        private OutputStream _outputStream;

        public ConnectThread(BluetoothDevice device, BluetoothAdapter adapter) {

            BluetoothSocket tmp = null;
            _device = device;
            _adapter = adapter;

            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            _socket = tmp;

        }

        public void run() {
            _adapter.cancelDiscovery();

            try {
                _socket.connect();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    _socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    return;
                }
            }

            manageConnectedSocket(_socket);

        }

        private void manageConnectedSocket(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            _inputStream = tmpIn;
            _outputStream = tmpOut;

        }

        private void read() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = _inputStream.read(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }

        }

        public void cancel() {
            try {
                _socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
