package com.example.pegawai;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pegawai.adapter.NomorAdapter;
import com.example.pegawai.adapter.NomorMod;
import com.example.pegawai.database.CustomHttpClientGet;
import com.example.pegawai.database.JSONParserNew;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Team_number extends AppCompatActivity {

    //drawer layout
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    //instansiasi navigation header
    String tfnik, tfnama, tfbatch, tfjbtn;
    TextView view_nik, view_nama;
    Intent in;
    // end instansiasi

    String batch;

    //load
    ListView listViewPhone;
    CustomHttpClientGet client;
    List<NomorMod> listPhone;
    JSONArray phone = null;
    String Pids, Pnama, Pnomor, Ptelepon, Parea, Pfoto;
    private ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> newList = new ArrayList<HashMap<String, String>>();
    ListAdapter adapter;
    //end

    TextView zid, znama, znomor, zarea;
    ImageView zfoto;
    Spinner spinBatch;
    ArrayList <String> batchkombo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_number);

        listViewPhone = (ListView) findViewById(R.id.listnumber);
        zid = (TextView) findViewById(R.id.txtids);
        znama = (TextView) findViewById(R.id.txtnama);
        znomor = (TextView) findViewById(R.id.txtnomor);
        //zalamat= (TextView) findViewById(R.id.txtalamat);
        spinBatch = (Spinner) findViewById(R.id.spinBatch);



        SharedPreferences sp = getSharedPreferences("DataGlobal", MODE_PRIVATE);
        //penting, untuk navigation header
        view_nik = (TextView) findViewById(R.id.nav_nik);
        view_nama = (TextView) findViewById(R.id.nav_nama);
        view_nik.setText(sp.getString("nik",""));
        view_nama.setText(sp.getString("nama", ""));
        tfnik = sp.getString("nik", "");
        tfnama= sp.getString("nama", "");
        tfbatch = sp.getString("batch","");
        tfjbtn = sp.getString("jbtn","");


        // Begin Navigation Drawer nya nih
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
                        startActivity(new Intent(Team_number.this,Employee.class));
                        return true;
                    case R.id.nav_license:
                        startActivity(new Intent(Team_number.this,License.class));
                        return true;
                    case R.id.nav_goal:
                        startActivity(new Intent(Team_number.this,Goal_zero.class));
                        return true;
                    case R.id.nav_erp:
                        startActivity(new Intent(Team_number.this,ER_Procedure.class));
                        return true;
                    case R.id.nav_ecn:
                        startActivity(new Intent(Team_number.this,EC_number.class));
                        return true;
                    case R.id.nav_number:
                        startActivity(new Intent(Team_number.this,Team_number.class));
                        return true;
                    case R.id.nav_logout:
                        SharedPreferences sharedpreferences = getSharedPreferences("MyData", Context.MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(Team_number.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Toast.makeText(Team_number.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }
        });

        // End Navigation Drawer


        //loadphone
        batch = tfbatch;
        new LoadPhone().execute(batch);

        new LoadBatch().execute();

        spinBatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                batch = batchkombo.get(position).substring(0,5);
                System.out.println("pas di klik:"+batch);
                new LoadPhone().execute(batch);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Team_number.this, "Not Selected Anything", Toast.LENGTH_SHORT).show();
            }
        });
    }


    class LoadBatch extends AsyncTask<String, Integer, ArrayList<HashMap<String, String>>> {

        ArrayList<HashMap<String, String>> newList = new ArrayList<HashMap<String, String>>();
        JSONParserNew jParser = new JSONParserNew();
        String url = "http://tom-mbuddy.com/android/batch.php";
        JSONArray train = null;

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... args) {
            //Building Parameters
            HashMap<String, String> params = new HashMap<>();
            //params.put("batch", args[0]);

            // Getting JSON from URL
            JSONObject json = jParser.makeHttpRequest(url, "GET", params);
            System.out.println("Ini JSON nya : " + json);
            try {
                train = json.getJSONArray("data");
                System.out.println("ini phone : " + train);

                for (int i = 0; i < train.length(); i++) {
                    //HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject obj = train.getJSONObject(i);

                    String a1 = obj.getString("batch")+" - "+obj.getString("location");
                    batchkombo.add(a1);

                    System.out.println("panjang data : "+i);
                }
            } catch (JSONException e) {
                Log.e("log_tag", "Error parsing data " + e.toString());
            }
            return newList;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
            super.onPostExecute(result);
            System.out.println(result);
            ArrayAdapter<String> aa = new ArrayAdapter<String>(Team_number.this,R.layout.support_simple_spinner_dropdown_item, batchkombo);
            aa.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinBatch.setAdapter(aa);
        }
    }


    class LoadPhone extends AsyncTask<String, Void, String> {
        JSONParserNew jParser = new JSONParserNew();
        JSONArray daftarPhone;
        ArrayList<NomorMod> daftar_phone = new ArrayList<NomorMod>();
        String url = "http://tom-mbuddy.com/android/nomor_team.php";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Team_number.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {
            //Building Parameters
            NomorMod tempNo = new NomorMod();
            HashMap<String, String> params = new HashMap<>();
            params.put("batch", batch);

            // Getting JSON from URL
            JSONObject json = jParser.makeHttpRequest(url, "GET", params);
            System.out.println("Ini JSON nya : " + json);
            try {
                int success = json.getInt("success");
                if (success == 1) { //Ada record Data (SUCCESS = 1)
                    //Getting Array of daftar_mhs
                    daftarPhone = json.getJSONArray("data");
                    // looping through All daftar_mhs
                    for (int i = 0; i < daftarPhone.length(); i++) {
                        JSONObject c = daftarPhone.getJSONObject(i);
                        tempNo = new NomorMod();
                        tempNo.setIdno(c.getString("nik"));
                        tempNo.setNama(c.getString("nama"));
                        tempNo.setNomor(c.getString("nohp"));
                        tempNo.setTelepon(c.getString("telepon"));
                        tempNo.setFoto(c.getString("foto"));
                        daftar_phone.add(tempNo);
                    }
                    return "OK";
                } else {
                    return "no result";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Exception Caught";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            if (result.equalsIgnoreCase("Exception Caught")) {
                Toast.makeText(Team_number.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
            }

            if (result.equalsIgnoreCase("no results")) {
                Toast.makeText(Team_number.this, "Data empty", Toast.LENGTH_LONG).show();
            } else {
                //new SearchLocation().execute(batch);
                listViewPhone.setAdapter(new NomorAdapter(Team_number.this, daftar_phone));
                listViewPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Pids = ((TextView) view.findViewById(R.id.txtids)).getText().toString();
                        Pnama = ((TextView) view.findViewById(R.id.txtnama)).getText().toString();
                        Pnomor = ((TextView) view.findViewById(R.id.txtnomor)).getText().toString();
                        Ptelepon = ((TextView) view.findViewById(R.id.txttelepon)).getText().toString();

                        LinearLayout layoutInput = new LinearLayout(Team_number.this);
                        layoutInput.setOrientation(LinearLayout.VERTICAL);
                        NomorMod no = new NomorMod();

                        final TextView vids = new TextView(Team_number.this);
                        vids.setText(Pids);
                        vids.setTextColor(Color.TRANSPARENT);
                        layoutInput.addView(vids);

                        final TextView vnama = new TextView(Team_number.this);
                        vnama.setText("   " + Pnama);
                        vnama.setTextColor(Color.parseColor("#FFFFFF"));
                        layoutInput.addView(vnama);

                        final Button tlp1 = new Button(Team_number.this);
                        tlp1.setText(Pnomor);
                        layoutInput.addView(tlp1);
                        tlp1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String a = tlp1.getText().toString().trim();
                                call(a);
                            }
                        });

                        final Button tlp2 = new Button(Team_number.this);
                        tlp2.setText(Ptelepon);
                        layoutInput.addView(tlp2);
                        tlp2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String a = tlp2.getText().toString().trim();
                                call(a);
                            }
                        });

                        AlertDialog.Builder builder = new AlertDialog.Builder(Team_number.this);
                        builder.setIcon(R.drawable.ic_people);
                        builder.setTitle("Push Button for Calling");
                        builder.setView(layoutInput);

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
    }

    private void call(String nom) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+nom));
            startActivity(callIntent);
        } catch (ActivityNotFoundException e) {
            Log.e("helloandroid dialing ", "Call failed", e);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(Team_number.this, Utama.class));
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
