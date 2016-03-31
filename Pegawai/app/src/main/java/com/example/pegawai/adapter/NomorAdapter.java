package com.example.pegawai.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pegawai.R;

import java.util.ArrayList;

/**
 * Created by subki on 12/10/2015.
 */
public class NomorAdapter extends BaseAdapter {
    private Activity activity;
    //private ArrayList<HashMap<String, String>> data;
    private ArrayList<NomorMod> data_phone=new ArrayList<NomorMod>();

    private static LayoutInflater inflater = null;

    public NomorAdapter(Activity a, ArrayList<NomorMod> d) {
        activity = a; data_phone = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {
        return data_phone.size();
    }
    public Object getItem(int position) {
        return data_phone.get(position);
    }
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if (convertView == null)
            vi = inflater.inflate(R.layout.list_number, null);

            TextView ids = (TextView) vi.findViewById(R.id.txtids);
            TextView nama= (TextView) vi.findViewById(R.id.txtnama);
        TextView nomor=(TextView) vi.findViewById(R.id.txtnomor);
        TextView telepon=(TextView) vi.findViewById(R.id.txttelepon);

        NomorMod no = data_phone.get(position);
        ids.setText(no.getIdno());
        nama.setText(no.getNama());
        nomor.setText(no.getNomor());
        telepon.setText(no.getTelepon());

        return vi;
    }
}
