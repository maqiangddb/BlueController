package com.some.blue;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.os.Bundle;

public class MainActivity extends Activity
{

    BroadcastReceiver _receiver;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        _receiver = new BlueBroadcastReceiver();
        registerReceiver(_receiver, BlueBroadcastReceiver.getFilter());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(_receiver);
    }


}
