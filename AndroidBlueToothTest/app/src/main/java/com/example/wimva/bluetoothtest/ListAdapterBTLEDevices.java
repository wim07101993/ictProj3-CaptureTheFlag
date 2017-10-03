package com.example.wimva.bluetoothtest;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class ListAdapterBTLEDevices extends ArrayAdapter<BTLEDevice> {

    /*----------------------------------------------*/
    /*------------------- FIELDS -------------------*/
    /*----------------------------------------------*/

    Activity activity;
    int layoutResourceID;
    ArrayList<BTLEDevice> devices;

    /*---------------------------------------------------*/
    /*------------------- CONSTRUCTOR -------------------*/
    /*---------------------------------------------------*/

    public ListAdapterBTLEDevices(Activity activity, int resource, ArrayList<BTLEDevice> objects) {
        super(activity.getApplicationContext(), resource, objects);

        this.activity = activity;
        layoutResourceID = resource;
        devices = objects;
    }

    /*----------------------------------------------*/
    /*------------------- METHODS -------------------*/
    /*----------------------------------------------*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResourceID, parent, false);
        }

        BTLEDevice device = devices.get(position);
        String name = device.getName();
        String address = device.getAddress();
        int rssi = device.getRSSI();

        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        if (name != null && name.length() > 0) {
            tv_name.setText(device.getName());
        } else {
            tv_name.setText("No Name");
        }

        TextView tv_rssi = (TextView) convertView.findViewById(R.id.tv_rssi);
        tv_rssi.setText("RSSI: " + Integer.toString(rssi));

        TextView tv_macaddr = (TextView) convertView.findViewById(R.id.tv_macaddr);
        if (address != null && address.length() > 0) {
            tv_macaddr.setText(device.getAddress());
        } else {
            tv_macaddr.setText("No Address");
        }

        return convertView;
    }
}
