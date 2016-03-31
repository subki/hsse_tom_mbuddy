package com.example.pegawai;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pegawai.adapter.NomorAdapter;
import com.example.pegawai.adapter.NomorMod;
import com.example.pegawai.adapter.ResetAdapter;
import com.example.pegawai.adapter.ResetMod;
import com.example.pegawai.database.JSONParserNew;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Goal_zero extends AppCompatActivity {

    //navigasi
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    //instansiasi navigation header
    String tfnik, tfnama, tfbatch, tfjbtn;
    TextView nik, nama;
    Intent in;
    // end instansiasi

    Switch aSwitch, bSwitch;
    ScrollView scrollView;
    TableLayout tabel;
    EditText g_nik, g_nama, g_date,g_location,  g_what, g_why, g_how;
    TextView g_goal, g_incident, resiko_terbesar, mengapa_resiko, inisiatif_resiko;
    String s_nik, s_nama, s_date, s_what, s_why, s_how, s_stat_goal, s_stat_incident, s_location;
    TextView goalview, goalval, resview, resval, text1, text2, text3;
    int tot_now = 0;
    int thn, bln, tgl;
    String tahun, last_inc, reset_tgl, reset_jdl, reset_uraian, reset_bljr;

    Button fab, upd_risk;

    ListView listViewReset;

    ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_zero);

        aSwitch = (Switch) findViewById(R.id.switch1);
        bSwitch = (Switch) findViewById(R.id.switch2);
        scrollView = (ScrollView) findViewById(R.id.scrollView2);
        tabel = (TableLayout) findViewById(R.id.tableLayout2);
        g_nik = (EditText) findViewById(R.id.goal_tfnik);
        g_nama = (EditText) findViewById(R.id.goal_tfnama);
        g_date = (EditText) findViewById(R.id.goal_tfdate);
        g_location = (EditText) findViewById(R.id.goal_tflocation);
        g_what = (EditText) findViewById(R.id.goal_what);
        g_why  = (EditText) findViewById(R.id.goal_why);
        g_how  = (EditText) findViewById(R.id.goal_how);
        g_goal = (TextView) findViewById(R.id.goal_goal);
        g_incident = (TextView) findViewById(R.id.goal_incident);
        resiko_terbesar = (TextView) findViewById(R.id.resiko_terbesar);
        mengapa_resiko = (TextView) findViewById(R.id.mengapa_resiko);
        inisiatif_resiko= (TextView) findViewById(R.id.inisiatif_resiko);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        listViewReset = (ListView) findViewById(R.id.list_reset);


        fab = (Button) findViewById(R.id.fab);
        upd_risk = (Button) findViewById(R.id.upd_risk);

        goalview=(TextView)findViewById(R.id.lblTotal1);
        goalval = (TextView) findViewById(R.id.lblgoal);
        resview = (TextView) findViewById(R.id.lblreset1);
        resval = (TextView) findViewById(R.id.lblreset);

        SharedPreferences sp = getSharedPreferences("DataGlobal", MODE_PRIVATE);
        SharedPreferences spPrivate = getSharedPreferences("DataPrivate", MODE_PRIVATE);
        //penting, untuk navigation header
        nik = (TextView) findViewById(R.id.nav_nik);
        nama = (TextView) findViewById(R.id.nav_nama);
        nik.setText(sp.getString("nik",""));
        nama.setText(sp.getString("nama", ""));
        tfnik = sp.getString("nik","");
        tfnama= sp.getString("nama","");
        tfbatch = sp.getString("batch","");
        tfjbtn = sp.getString("jbtn","");

        resiko_terbesar.setText(spPrivate.getString("resiko_terbesar",""));
        mengapa_resiko.setText(spPrivate.getString("mengapa_resiko",""));
        inisiatif_resiko.setText(spPrivate.getString("inisiatif_resiko",""));


        // Begin Navigation Drawer nya nih khusus untuk di layout utama saja
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setupToolbar();
        navView = (NavigationView) findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked())
                    menuItem.setChecked(false);
                else
                    menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.nav_employee:
                        startActivity(new Intent(Goal_zero.this,Employee.class));
                        return true;
                    case R.id.nav_license:
                        startActivity(new Intent(Goal_zero.this,License.class));
                        return true;
                    case R.id.nav_goal:
                        startActivity(new Intent(Goal_zero.this,Goal_zero.class));
                        return true;
                    case R.id.nav_erp:
                        startActivity(new Intent(Goal_zero.this,ER_Procedure.class));
                        return true;
                    case R.id.nav_ecn:
                        startActivity(new Intent(Goal_zero.this,EC_number.class));
                        return true;
                    case R.id.nav_number:
                        startActivity(new Intent(Goal_zero.this,Team_number.class));
                        return true;
                    case R.id.nav_logout:
                        SharedPreferences sharedpreferences = getSharedPreferences("MyData", Context.MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(Goal_zero.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Toast.makeText(Goal_zero.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }
        });
        // End Navigation Drawer


        //ubah font goal
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/digital.TTF");
        goalval.setTypeface(tf);
        resval.setTypeface(tf);

        //view status
        Calendar kalender = new GregorianCalendar();
        thn = kalender.get(Calendar.YEAR);
        bln = kalender.get(Calendar.MONTH);
        tgl = kalender.get(Calendar.DATE);

        tot_now = kalender.get(Calendar.DAY_OF_YEAR);

        tahun= String.valueOf(thn);
        System.out.println(tahun);
        new LoadStatus().execute(tfbatch, tfnik, tahun);
        new getIncident().execute(tfnik, tahun);

        int blnn = bln+1;
        String blln;
        if(String.valueOf(blnn).length()==1){
            blln = "0"+blnn;
        }else{
            blln = String.valueOf(blnn);
        }
        g_date.setText(thn + "-" + (blln) + "-" + tgl);
        //g_what.requestFocus();

        scrollView.setVisibility(View.GONE);
        bSwitch.setVisibility(View.GONE);
        listViewReset.setVisibility(View.GONE);
        aSwitch.setChecked(false);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    scrollView.setVisibility(View.VISIBLE);
                    tabel.setVisibility(View.VISIBLE);
                    g_nik.setText(tfnik);
                    g_nama.setText(tfnama);

                    g_goal.setVisibility(View.GONE);
                    g_incident.setVisibility(View.GONE);
                    fab.setVisibility(View.VISIBLE);
                    bSwitch.setVisibility(View.VISIBLE);
                    listViewReset.setVisibility(View.GONE);
                    goalview.setVisibility(View.GONE);
                    goalval.setVisibility(View.GONE);
                    resview.setVisibility(View.GONE);
                    resval.setVisibility(View.GONE);
                    resiko_terbesar.setVisibility(View.GONE);
                    mengapa_resiko.setVisibility(View.GONE);
                    inisiatif_resiko.setVisibility(View.GONE);
                    text1.setVisibility(View.GONE);
                    text2.setVisibility(View.GONE);
                    text3.setVisibility(View.GONE);
                    upd_risk.setVisibility(View.GONE);

                } else {
                    scrollView.setVisibility(View.GONE);
                    tabel.setVisibility(View.GONE);
                    listViewReset.setVisibility(View.GONE);
                    bSwitch.setVisibility(View.GONE);
                    g_what.setText("");
                    g_why.setText("");
                    g_how.setText("");

                    g_goal.setVisibility(View.GONE);
                    g_incident.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                    goalview.setVisibility(View.VISIBLE);
                    goalval.setVisibility(View.VISIBLE);
                    resview.setVisibility(View.VISIBLE);
                    resval.setVisibility(View.VISIBLE);
                    resiko_terbesar.setVisibility(View.VISIBLE);
                    mengapa_resiko.setVisibility(View.VISIBLE);
                    inisiatif_resiko.setVisibility(View.VISIBLE);
                    text1.setVisibility(View.VISIBLE);
                    text2.setVisibility(View.VISIBLE);
                    text3.setVisibility(View.VISIBLE);
                    upd_risk.setVisibility(View.VISIBLE);
                }
            }
        });

        bSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    listViewReset.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                    tabel.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                }else{
                    listViewReset.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    tabel.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.VISIBLE);
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_nik = g_nik.getText().toString();
                s_date = g_date.getText().toString();
                s_what = g_what.getText().toString();
                s_why = g_why.getText().toString();
                s_how = g_how.getText().toString();
                System.out.println("parameter ss:"+tfbatch+s_date+s_what+s_why+s_how+s_nik);
                new SaveIncident().execute(tfbatch, s_date, s_what, s_why, s_how+s_nik);
            }
        });

        upd_risk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Goal_zero.this, big_risk.class));
            }
        });
    }
    class getIncident extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;
        JSONParserNew jParser = new JSONParserNew();
        JSONArray daftarPhone;
        ArrayList<ResetMod> daftar_phone = new ArrayList<ResetMod>();
        String url = "http://tom-mbuddy.com/android/get_incident.php";
        String res;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Goal_zero.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {
            //Building Parameters
            ResetMod tempNo = new ResetMod();
            HashMap<String, String> params = new HashMap<>();
            params.put("nik", tfnik);
            params.put("tahun", tahun+"%");

            // Getting JSON from URL
            JSONObject json = jParser.makeHttpRequest(url, "GET", params);
            System.out.println("Ini JSON nya : " + json);
            try {
                daftarPhone = json.getJSONArray("data");
                // looping through All daftar_mhs
                for (int i = 0; i < daftarPhone.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject c = daftarPhone.getJSONObject(i);

                    map.put("tanggal", c.getString("tanggal"));
                    map.put("problem", c.getString("problem"));
                    map.put("uraian", c.getString("uraian"));
                    map.put("tindakan", c.getString("tindakan"));
                    myList.add(map);

                }
            } catch (Exception e) {
                e.printStackTrace();
                res= "Exception Caught";
                return res;
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            //new SearchLocation().execute(batch);
            ListAdapter adapter = new SimpleAdapter(Goal_zero.this, myList, R.layout.list_reset,
                    new String[]{"tanggal", "problem", "uraian","tindakan"},
                    new int[]{R.id.reset_tanggal, R.id.reset_title, R.id.reset_uraian, R.id.reset_pembelajaran});
            listViewReset.setAdapter(adapter);
            listViewReset.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    reset_tgl = ((TextView) view.findViewById(R.id.reset_tanggal)).getText().toString();
                    reset_jdl = ((TextView) view.findViewById(R.id.reset_title)).getText().toString();
                    reset_uraian = ((TextView) view.findViewById(R.id.reset_uraian)).getText().toString();
                    reset_bljr = ((TextView) view.findViewById(R.id.reset_pembelajaran)).getText().toString();

                    ScrollView sv = new ScrollView(Goal_zero.this);

                    LinearLayout layoutInput = new LinearLayout(Goal_zero.this);
                    layoutInput.setOrientation(LinearLayout.VERTICAL);
                    NomorMod no = new NomorMod();

                    final TextView vids1 = new TextView(Goal_zero.this);
                    vids1.setText("\n \nTanggal :");
                    vids1.setTextSize(14);
                    vids1.setTextColor(Color.parseColor("#FFFFFF"));
                    layoutInput.addView(vids1);

                    final TextView vids = new TextView(Goal_zero.this);
                    vids.setText(reset_tgl);
                    vids.setTextSize(14);
                    vids.setTextColor(Color.parseColor("#FFFFFF"));
                    layoutInput.addView(vids);

                    final TextView vnama = new TextView(Goal_zero.this);
                    vnama.setText("\n \nTittle");
                    vnama.setTextSize(14);
                    vnama.setTextColor(Color.parseColor("#FFFFFF"));
                    layoutInput.addView(vnama);

                    final TextView vnama11 = new TextView(Goal_zero.this);
                    vnama11.setText( reset_jdl);
                    vnama11.setTextSize(14);
                    vnama11.setTextColor(Color.parseColor("#FFFFFF"));
                    layoutInput.addView(vnama11);

                    final TextView vnama1 = new TextView(Goal_zero.this);
                    vnama1.setText("\n \nUraian");
                    vnama1.setTextSize(14);
                    vnama1.setTextColor(Color.parseColor("#FFFFFF"));
                    layoutInput.addView(vnama1);

                    final TextView vnama21 = new TextView(Goal_zero.this);
                    vnama21.setText(reset_uraian);
                    vnama21.setTextSize(14);
                    vnama21.setTextColor(Color.parseColor("#FFFFFF"));
                    layoutInput.addView(vnama21);

                    final TextView vnama2 = new TextView(Goal_zero.this);
                    vnama2.setText("\n \nPembelajaran");
                    vnama2.setTextSize(14);
                    vnama2.setTextColor(Color.parseColor("#FFFFFF"));
                    layoutInput.addView(vnama2);

                    final TextView vnama22 = new TextView(Goal_zero.this);
                    vnama22.setText( reset_bljr);
                    vnama22.setTextSize(14);
                    vnama22.setTextColor(Color.parseColor("#FFFFFF"));
                    layoutInput.addView(vnama22);

                    sv.addView(layoutInput);

                    AlertDialog.Builder builder = new AlertDialog.Builder(Goal_zero.this);
                    builder.setIcon(R.drawable.ic_time);
                    builder.setTitle("History Reset");
                    builder.setView(sv);

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }

                    });

                    builder.show();
                }
            });
        }
    }

    class SaveIncident extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;
        String url = "http://tom-mbuddy.com/android/insert_incident.php";
        JSONParserNew jsonParser = new JSONParserNew();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Goal_zero.this);
            pDialog.setMessage("Save Incident...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected String doInBackground(String... args) {

            // Building Parameters
            HashMap<String, String> params = new HashMap<>();
            //tfbatch, s_date, s_what, s_why, s_how+s_nik
            params.put("batch", tfbatch);
            params.put("tgl", s_date);
            params.put("problem", s_what);
            params.put("uraian", s_why);
            params.put("tindakan", s_how);
            params.put("nik", s_nik);

            // getting JSON Object
            // Note that create Post url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

            // check for success tag
            try {
                int success = json.getInt("success");

                if (success == 1) {
                    // closing this screen
                    finish();
                } else {
                    return "gagal_database";
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return "gagal_koneksi_or_exception";
            }

            return "sukses";

        }

        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            if (result.equalsIgnoreCase("gagal_database")){
                pDialog.dismiss();
                Toast.makeText(Goal_zero.this, "Terjadi masalah! Silahkan cek koneksi Anda!", Toast.LENGTH_SHORT).show();
            }
            else if (result.equalsIgnoreCase("gagal_koneksi_or_exception")){
                pDialog.dismiss();
                Toast.makeText(Goal_zero.this, "Terjadi masalah! Silahkan cek koneksi Anda!",  Toast.LENGTH_SHORT).show();

            }
            else if (result.equalsIgnoreCase("sukses")){
                pDialog.dismiss();
                startActivity(new Intent(Goal_zero.this, Goal_zero.class));
                Toast.makeText(Goal_zero.this, "Simpan Insiden berhasil!",  Toast.LENGTH_SHORT).show();
            }
        }
    }

    class LoadStatus extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;
        JSONParserNew jsonParser = new JSONParserNew();
        String STATUS = "http://tom-mbuddy.com/android/goal_zero_status.php";
        JSONObject data;

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Goal_zero.this);
            pDialog.setMessage("Please wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            // Building Parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("batch", args[0]);
            params.put("nik", args[1]);
            params.put("tahun", args[2]+"%");
            System.out.println(params);
            // getting JSON string from URL
            JSONObject json = jsonParser.makeHttpRequest(STATUS, "GET", params);
            try {
                // profile json object
                data = json.getJSONObject("data");
                s_stat_incident=data.getString("total");
                s_location=data.getString("location");
                last_inc = data.getString("last_in");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            // updating UI from Background Thread
            if (file_url != "") {
                // displaying all data in textview
                g_location.setText(s_location);
                int i = s_stat_incident.length();
                if(i == 1){
                    //g_incident.setText("   Jumlah Incident  : "+"00"+s_stat_incident);
                    resval.setText("00"+s_stat_incident);
                }else if(i == 2){
                   // g_incident.setText("   Jumlah Incident  : "+"0"+s_stat_incident);
                    resval.setText("0"+s_stat_incident);
                }else{
                    //g_incident.setText("   Jumlah Incident  : "+s_stat_incident);
                    resval.setText(s_stat_incident);
                }

                GoalStatus(last_inc);
            }
        }
    }

    private void GoalStatus(String n) {

        String thn_insiden = n.substring(0,4);
        String bln_insiden = n.substring(5,7);
        String tgl_insiden = n.substring(8,10);

        int yy = Integer.parseInt(thn_insiden);
        int mm = Integer.parseInt(bln_insiden);
        int dd = Integer.parseInt(tgl_insiden);
        int tot = dd;

        int[] bulan = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        for(int i=0; i<mm; i++){
            if(i==2){
                if(yy%4==0){
                    tot += 29;
                }else{
                    tot += 28;
                }
            }else{
                tot += bulan[i] ;
            }
        }
        int grand_tot = tot;

        int goal = tot_now - grand_tot;

        String j = String.valueOf(goal);
        int k = j.length();
        if(k==1){
            //g_goal.setText(" Jumlah Goal  : "+"00"+j);
            goalval.setText("00"+j);
        }else if(k==2){
            //g_goal.setText(" Jumlah Goal  : "+"0"+j);
            goalval.setText("0"+j);
        }else{
           // g_goal.setText(" Jumlah Goal  : "+j);
            goalval.setText(j);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(Goal_zero.this,Utama.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // menghandle ketika tombol home diklik, Navigation View akan terbuka
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // set toolbar ke dalam support action bar
        setSupportActionBar(toolbar);

        // enable home button untuk navigasi
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // mengeset icon untuk home button Toolbar
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        // mengeset title/nama aplikasi
        getSupportActionBar().setTitle("Site HSSE m-Buddy");

        // mengeset subtitle
        getSupportActionBar().setSubtitle("PD. Trivia Oktana Mandiri");

        // set logo toolbar
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_logo_launcher);

    }

}
