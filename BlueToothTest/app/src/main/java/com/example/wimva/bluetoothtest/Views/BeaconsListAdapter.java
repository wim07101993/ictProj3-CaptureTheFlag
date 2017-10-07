package com.example.wimva.bluetoothtest.Views;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wimva.bluetoothtest.Models.Beacon;
import com.example.wimva.bluetoothtest.R;

import java.util.ArrayList;

public class BeaconsListAdapter extends ArrayAdapter<Beacon> {

    /* ---------------------------------------------------------- */
    /* ------------------------- FIELDS ------------------------- */
    /* ---------------------------------------------------------- */

    // activity to get convertView from if needed
    private Activity activity;

    private int layoutResourceID;

    // list of bluetooth devices
    private ArrayList<Beacon> devices;

    /* --------------------------------------------------------------- */
    /* ------------------------- CONSTRUCTOR ------------------------- */
    /* --------------------------------------------------------------- */

    public BeaconsListAdapter(Activity activity, int resource, ArrayList<Beacon> objects) {
        super(activity.getApplicationContext(), resource, objects);

        // set fields
        this.activity = activity;
        layoutResourceID = resource;
        devices = objects;
    }

    /* ----------------------------------------------------------- */
    /* ------------------------- METHODS ------------------------- */
    /* ----------------------------------------------------------- */

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // if the view is null, get it from the activity
        if (convertView == null) {
            convertView = ((LayoutInflater) activity
                    .getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(layoutResourceID, parent, false);
        }

        // get the needed device
        Beacon device = devices.get(position);

        // create textviews
        fillTextView(convertView, R.id.tv_macaddr, device.getAddress(), "No Address");
        fillTextView(convertView, R.id.tv_rssi, "Signal strength: " + Double.toString(device.getRelativeRssi()), "No strength");

        return convertView;
    }

    private void fillTextView(View convertView, int viewID, String content, String defaultContent) {
        // get textview from vie
        TextView tv = convertView.findViewById(viewID);

        // if there is a content, set it, else set the default content
        if (content != null && content.length() > 0) {
            tv.setText(content);
        } else {
            tv.setText(defaultContent);
        }
    }
}
