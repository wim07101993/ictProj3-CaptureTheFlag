package sven.ble;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sven on 05/10/2017.
 */

public class DevicesArrayAdapter extends ArrayAdapter<BLEDevice> {

    private List<BLEDevice> devices;
    private Activity activity;
    private int resource;

    public DevicesArrayAdapter(Activity activity, int resource, List<BLEDevice> devices) {
        super(activity, resource, devices);

        this.devices = devices;
        this.activity = activity;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // if the view is null, get it from the activity
        if (convertView == null) {
            convertView = ((LayoutInflater) activity
                    .getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(resource, parent, false);
        }

        // get the needed device
        BLEDevice device = devices.get(position);

        // create textviews
        fillTextView(convertView, R.id.addressTextView, device.getAddress(), "No address");
        fillTextView(convertView, R.id.nameTextView, device.getName(), "No Name");
        fillTextView(convertView, R.id.rssiTextView, String.valueOf(device.getRssi()), "No RSSI");

        return convertView;
    }

    private void fillTextView(View convertView, int viewID, String content, String defaultContent) {
        // get textview from view
        TextView tv = (TextView) convertView.findViewById(viewID);

        // if there is a content, set it, else set the default content
        if (content != null && content.length() > 0) {
            tv.setText(content);
        } else {
            tv.setText(defaultContent);
        }
    }

}
