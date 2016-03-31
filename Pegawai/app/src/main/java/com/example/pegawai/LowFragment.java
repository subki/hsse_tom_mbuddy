package com.example.pegawai;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.pegawai.database.JSONParserNew;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class LowFragment extends Fragment {

    TextView nik;
    String tfnik, tanggal, status, idtrain;
    int thn, bln, tgl, date_now;

    ArrayList <String> kode = new ArrayList<>();
    ArrayList <String> training = new ArrayList<>();
    ArrayList <String> trainer = new ArrayList<>();
    ArrayList <String> level = new ArrayList<>();
    ArrayList <String> tanggals = new ArrayList<>();
    ArrayList <String> statuss = new ArrayList<>();
    ArrayList <String> score = new ArrayList<>();
    ArrayList <String> verifyed = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View low = inflater.inflate(R.layout.activity_low_fragment, container, false);

        nik = (TextView) getActivity().findViewById(R.id.nav_nik);
        tfnik = nik.getText().toString();

        new AsyncTaskLow().execute(tfnik);


        Calendar kalender = new GregorianCalendar();
        date_now = kalender.get(Calendar.DAY_OF_YEAR);

        return low;
    }

    class AsyncTaskLow extends AsyncTask<String, Integer, ArrayList<HashMap<String, String>>> {

        ProgressDialog pDialog;
        ArrayList<HashMap<String, String>> newList = new ArrayList<HashMap<String, String>>();
        JSONParserNew jParser = new JSONParserNew();
        String url = "http://tom-mbuddy.com/android/training_lisence.php";
        JSONArray train = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Getting Data Training...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... args) {
            //Building Parameters
            HashMap<String, String> params = new HashMap<>();
            //params.put("batch", args[0]);
            params.put("level", "2");
            params.put("nik", args[0]);


            // Getting JSON from URL
            JSONObject json = jParser.makeHttpRequest(url, "GET", params);
            System.out.println("Ini JSON nya : " + json);
            try {
                train = json.getJSONArray("data");
                System.out.println("ini license : " + train);

                //kosongi arraynya dulu
                kode.clear();
                training.clear();
                level.clear();
                tanggals.clear();
                trainer.clear();
                score.clear();
                verifyed.clear();

                kode.add("No");
                training.add("Topic Training");
                trainer.add("Trainer");
                level.add("Lvl");
                tanggals.add("Tanggal");
                score.add("Nilai");
                verifyed.add("Verify By");
                for (int i = 0; i < train.length(); i++) {
                    //HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject obj = train.getJSONObject(i);

                    String a1 = obj.getString("kode");
                    kode.add(a1);
                    String a2 = obj.getString("training");
                    training.add(a2);
                    String a3 = obj.getString("trainer");
                    trainer.add(a3);
                    String a4 = obj.getString("level");
                    level.add(a4);
                    String a5 = obj.getString("tgl");
                    tanggals.add(a5);
                    String a6 = obj.getString("nilai");
                    score.add(a6);
                    String a7 = obj.getString("verify");
                    verifyed.add(a7);

                    System.out.println("panjang data : "+i);
                }
            } catch (JSONException e) {
                Log.e("log_tag", "Error parsing data " + e.toString());
            }
            return newList;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
            pDialog.dismiss();
            super.onPostExecute(result);
            System.out.println(result);
            isiTable();
        }
    }

    public void isiTable(){

        System.out.println("Kode" + kode);
        System.out.println("training" + training);
        System.out.println("trainer" + trainer);
        System.out.println("level" + level);
        System.out.println("tgk" + tanggals);
        System.out.println("stt" + statuss);
        System.out.println("stt" + score);
        System.out.println("stt" + verifyed);

        TableLayout lowLayout = (TableLayout) getActivity().findViewById(R.id.tableLow);

        TableRow tRow;
        TextView t1, t2, t3, t4, t5,t6;
        ImageView bg;

        //hapus isi tabel nya dulu
        if(lowLayout != null) {
            int jml = lowLayout.getChildCount();
            for (int i = 0; i < jml; i++) {
                View anak = lowLayout.getChildAt(i);
                if (anak instanceof TableRow) ((ViewGroup) anak).removeAllViews();
            }
        }

        System.out.println("ini panjangnya : " + kode.size());
        int flag=0;
        for (int i = 0; i < kode.size(); i++) {
            tRow = new TableRow(getActivity());
            tRow.setPadding(2,2,2,2);

            t1 = new TextView(getActivity());
            t2 = new TextView(getActivity());
            t3 = new TextView(getActivity());
            t4 = new TextView(getActivity());
            t5 = new TextView(getActivity());
            t6 = new TextView(getActivity());
            bg = new ImageView(getActivity());

            t1.setTextSize(14);
            t2.setTextSize(14);
            t3.setTextSize(14);
            t4.setTextSize(14);
            t5.setTextSize(14);
            t6.setTextSize(14);

            t1.setTextColor(Color.BLACK);
            t2.setTextColor(Color.BLACK);
            t3.setTextColor(Color.BLACK);
            t4.setTextColor(Color.BLACK);
            t5.setTextColor(Color.BLACK);
            t6.setTextColor(Color.BLACK);


            if(i != 0) {
                if(score.get(i).trim().equals("25")){
                    bg.setImageResource(R.drawable.ic_25);
                }else if(score.get(i).trim().equals("50")){
                    bg.setImageResource(R.drawable.ic_50);
                }else if(score.get(i).trim().equals("75")){
                    bg.setImageResource(R.drawable.ic_75);
                }else if(score.get(i).trim().equals("100")){
                    bg.setImageResource(R.drawable.ic_100);
                }else{
                    bg.setImageResource(R.drawable.ic_blank);
                }
                tRow.setBackgroundColor(Color.CYAN);
            }else{
                tRow.setBackgroundColor(Color.GRAY);

                t1.setTextColor(Color.WHITE);
                t2.setTextColor(Color.WHITE);
                t3.setTextColor(Color.WHITE);
                t4.setTextColor(Color.WHITE);
                t5.setTextColor(Color.WHITE);
                t6.setTextColor(Color.WHITE);
            }

            String kd = (String) kode.get(i).substring(kode.get(i).length()-2);

            t1.setText((String) kd);
            t2.setText((String) training.get(i));
            t3.setText((String) tanggals.get(i));
            t4.setText((String) trainer.get(i));
            t5.setText((String) verifyed.get(i));
            t6.setText((String) score.get(i));


            tRow.addView(t1);
            tRow.addView(t2);
            tRow.addView(t3);
            //tRow.addView(t4);
            tRow.addView(t5);
            if(i != 0){
                tRow.addView(bg);
            }else {
                tRow.addView(t6);
            }

            lowLayout.addView(tRow, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        }
    }
}