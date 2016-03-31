package com.example.pegawai;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

public class BasicFragment extends Fragment {

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
        //return inflater.inflate(R.layout.activity_basic_fragment,null);
        View basic = inflater.inflate(R.layout.activity_basic_fragment, container, false);

        nik = (TextView) getActivity().findViewById(R.id.nav_nik);
        tfnik = nik.getText().toString();

        new AsyncTaskBasic().execute(tfnik);

        Calendar kalender = new GregorianCalendar();
        thn = kalender.get(Calendar.YEAR);
        date_now = kalender.get(Calendar.DAY_OF_YEAR);

        return basic;
    }

    class AsyncTaskBasic extends AsyncTask<String, Integer, ArrayList<HashMap<String, String>>> {

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
            params.put("level", "1");
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

        TableLayout basicLayout = (TableLayout) getActivity().findViewById(R.id.tableBasic);

        TableRow tRow;
        TextView t1, t2, t3, t4, t5,t6;
        ImageView bg;

        //hapus isi tabel nya dulu
        if(basicLayout != null) {
            int jml = basicLayout.getChildCount();
            for (int i = 0; i < jml; i++) {
                View anak = basicLayout.getChildAt(i);
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
                String tanggal;
                tanggal = (String) tanggals.get(i).trim();
                System.out.println("tanggal nya : " + tanggal);
                if (!tanggal.equals("0000-00-00")) {
                    System.out.println(tanggal);
                    String thn1 = tanggal.substring(0, 4);
                    System.out.println("tahun:" + thn1);
                    String bln1 = tanggal.substring(5, 7);
                    System.out.println("bulan:" + bln1);
                    String tgl1 = tanggal.substring(8, 10);
                    System.out.println("tgl:" + tgl1);

                    int a = Integer.parseInt(thn1);
                    int b = Integer.parseInt(bln1);
                    int c = Integer.parseInt(tgl1);

                    int[] bulan1 = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                    int itung1 = c;
                    for (int ii = 0; ii < b; ii++) {
                        if (ii == 2) {
                            if (a % 4 == 0) {
                                itung1 += 29;
                            } else {
                                itung1 += 28;
                            }
                        } else {
                            itung1 += bulan1[ii];
                        }
                    }
                    int date_train = itung1;
                    System.out.println("konversi hitung : " + date_train);
                    System.out.println("angka day skrg  : " + date_now);
                    System.out.println("selisih : " + (date_now - date_train));

                   // if (a != thn) {
                    //    int ngitung_bulan = bln + 12;
                   //     if (ngitung_bulan - b == 11 || ngitung_bulan - b == 12) {
                   //         tRow.setBackgroundColor(Color.YELLOW);
                    //    } else if (ngitung_bulan - b <= 10 || ngitung_bulan >= 3) {
                    //        tRow.setBackgroundColor(Color.GREEN);
                    //    } else if (ngitung_bulan - b >= 13) {
                    //        tRow.setBackgroundColor(Color.RED);
                    //    }
                    //} else {
                    //    int ngitung_bulan = bln;
                    //    if (ngitung_bulan - b <= 2 || ngitung_bulan - b >= 0) {
                    //        tRow.setBackgroundColor(Color.YELLOW);
                    //    } else if (ngitung_bulan - b <= 10 || ngitung_bulan >= 3) {
                    //        tRow.setBackgroundColor(Color.GREEN);
                    //    } else if (ngitung_bulan - b >= 13) {
                    //        tRow.setBackgroundColor(Color.RED);
                    //    }
                    //}

                    int aa=0;
                    if(a == thn){
                        aa = bln;
                    }else{
                        aa = bln+12;
                    }

                    System.out.println("bulan :"+aa);
                    System.out.println("bln tr :"+b);

                    if (aa - b <= 9) {
                        tRow.setBackgroundColor(Color.GREEN);
                    } else if (aa - b == 10 || aa - b == 11) {
                        tRow.setBackgroundColor(Color.YELLOW);
                    } else if (aa - b >= 12) {
                        tRow.setBackgroundColor(Color.RED);
                    }
                } else {
                    tRow.setBackgroundColor(Color.RED);
                }
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

            basicLayout.addView(tRow, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        }
    }
}

