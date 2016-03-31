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
public class ResetAdapter extends BaseAdapter {
    private Activity activity;
    //private ArrayList<HashMap<String, String>> data;
    private ArrayList<ResetMod> data_phone=new ArrayList<ResetMod>();

    private static LayoutInflater inflater = null;

    public ResetAdapter(Activity a, ArrayList<ResetMod> d) {
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
            vi = inflater.inflate(R.layout.list_reset, null);

        TextView tgl = (TextView) vi.findViewById(R.id.reset_tanggal);
        TextView tittle= (TextView) vi.findViewById(R.id.reset_title);
        TextView uraian=(TextView) vi.findViewById(R.id.reset_uraian);
        TextView belajar=(TextView) vi.findViewById(R.id.reset_pembelajaran);

        ResetMod no = data_phone.get(position);
        tgl.setText(no.getTgl());
        tittle.setText(no.getTittle());
        uraian.setText(no.getUraian());
        belajar.setText(no.getBelajar());

        return vi;
    }
}
