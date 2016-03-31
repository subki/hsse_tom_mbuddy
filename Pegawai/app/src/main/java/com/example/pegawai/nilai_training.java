package com.example.pegawai;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pegawai.database.JSONParserNew;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class nilai_training extends AppCompatActivity {

    //navigasi
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    //instansiasi navigation header
    String tfnik, tfnama, tfbatch, tfjbtn;
    TextView nik, nama;
    Intent in;
    // end instansiasi

    Spinner zkode, znilai;
    EditText xnik_karyawan, xnama_karyawan, xnik_trainer, xnama_trainer, xtanggal;
    TextView kode;
    int thn, bln, tgl;
    Button simpen, batal;
    ArrayList <String> comboKode = new ArrayList<>();
    String nilai_, a,b,c,d,e;

    String[] nilai = {
            "Trained",
            "Able to do",
            "Expert",
            "Able to train others"
    };
    Integer[] imagenilai = {
            R.drawable.ic_25,
            R.drawable.ic_50,
            R.drawable.ic_75,
            R.drawable.ic_100
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nilai_training);

        final SharedPreferences sp = getSharedPreferences("DataGlobal", MODE_PRIVATE);
        //penting, untuk navigation header
        nik = (TextView) findViewById(R.id.nav_nik);
        nama = (TextView) findViewById(R.id.nav_nama);
        nik.setText(sp.getString("nik",""));
        nama.setText(sp.getString("nama", ""));
        tfnik = sp.getString("nik", "");
        tfnama= sp.getString("nama", "");
        tfbatch = sp.getString("batch","");
        tfjbtn = sp.getString("jbtn", "");

        xnik_karyawan = (EditText) findViewById(R.id.xnik_karyawan);
        xnama_karyawan= (EditText) findViewById(R.id.xnama_karyawan);
        xnik_trainer = (EditText) findViewById(R.id.xnik_trainer);
        xnama_trainer = (EditText) findViewById(R.id.xnama_trainer);
        xtanggal = (EditText) findViewById(R.id.xtgl);
        zkode = (Spinner) findViewById(R.id.spinnerKode);
        znilai= (Spinner) findViewById(R.id.spinnerNilai);
        kode = (TextView) findViewById(R.id.kode);
        simpen = (Button) findViewById(R.id.simpan_nilai);
        batal = (Button) findViewById(R.id.batal_nilai);

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
                        startActivity(new Intent(nilai_training.this,Employee.class));
                        return true;
                    case R.id.nav_license:
                        startActivity(new Intent(nilai_training.this,License.class));
                        return true;
                    case R.id.nav_goal:
                        startActivity(new Intent(nilai_training.this,Goal_zero.class));
                        return true;
                    case R.id.nav_erp:
                        startActivity(new Intent(nilai_training.this,ER_Procedure.class));
                        return true;
                    case R.id.nav_ecn:
                        startActivity(new Intent(nilai_training.this,EC_number.class));
                        return true;
                    case R.id.nav_number:
                        startActivity(new Intent(nilai_training.this,Team_number.class));
                        return true;
                    case R.id.nav_logout:
                        SharedPreferences sharedpreferences = getSharedPreferences("MyData", Context.MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(nilai_training.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Toast.makeText(nilai_training.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }
        });
        // End Navigation Drawer

        //view status
        Calendar kalender = new GregorianCalendar();
        thn = kalender.get(Calendar.YEAR);
        bln = kalender.get(Calendar.MONTH);
        tgl = kalender.get(Calendar.DATE);

        int blnn = bln+1;
        String blln;
        if(String.valueOf(blnn).length()==1){
            blln = "0"+blnn;
        }else{
            blln = String.valueOf(blnn);
        }

        new LoadKode().execute();
        znilai.setAdapter(new MyAdapter(nilai_training.this, R.layout.row, nilai));


        xtanggal.setText(thn + "-" + (blln) + "-" + tgl);
        xnik_karyawan.setText(sp.getString("nik_nilai",""));
        xnama_karyawan.setText(sp.getString("nama_nilai",""));
        xnik_trainer.setText(sp.getString("nik",""));
        xnama_trainer.setText(sp.getString("nama",""));
        zkode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kode.setText(comboKode.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(nilai_training.this, "Not Selected Anything", Toast.LENGTH_SHORT).show();
            }
        });
        znilai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nilai_ = nilai[position].toString();
                if(nilai_.equals("Train able")){
                    nilai_ = "25";
                }else if(nilai_.equals("Able to do")){
                    nilai_ = "50";
                }else if(nilai_.equals("Expert")){
                    nilai_ = "75";
                }else if(nilai_.equals("Able to train others")){
                    nilai_ = "100";
                }else{
                    nilai_ = "0";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(nilai_training.this, "Not Selected Anything", Toast.LENGTH_SHORT).show();
            }
        });
        simpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 a = xnik_karyawan.getText().toString();
                 b = kode.getText().toString().substring(0, 6);
                 c = nilai_;
                 d = xtanggal.getText().toString();
                 e = xnik_trainer.getText().toString();
                new SaveNilai().execute(a, b, c, d, e);
            }
        });
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(nilai_training.this, License.class));
            }
        });
    }

    class SaveNilai extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;
        String url = "http://tom-mbuddy.com/android/insert_nilai.php";
        JSONParserNew jsonParser = new JSONParserNew();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(nilai_training.this);
            pDialog.setMessage("Saving...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected String doInBackground(String... args) {

            // Building Parameters
            HashMap<String, String> params = new HashMap<>();

            params.put("nik", a);
            params.put("kode", b);
            params.put("nilai", c);
            params.put("tgl", d);
            params.put("trainer", e);

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
                Toast.makeText(nilai_training.this, "Terjadi masalah! Silahkan cek koneksi Anda!", Toast.LENGTH_SHORT).show();
            }
            else if (result.equalsIgnoreCase("gagal_koneksi_or_exception")){
                pDialog.dismiss();
                Toast.makeText(nilai_training.this, "Terjadi kesalahan! Silahkan cek koneksi Anda!",  Toast.LENGTH_SHORT).show();

            }
            else if (result.equalsIgnoreCase("sukses")){
                pDialog.dismiss();
                startActivity(new Intent(nilai_training.this, License.class));
            }
        }
    }

    public class MyAdapter extends ArrayAdapter<String>{

        public MyAdapter(Context context, int textViewResourceId,   String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.row, parent, false);
            TextView label=(TextView)row.findViewById(R.id.company);
            label.setText(nilai[position]);

            //TextView sub=(TextView)row.findViewById(R.id.sub);
            //sub.setText(subs[position]);

            ImageView icon=(ImageView)row.findViewById(R.id.image);
            icon.setImageResource(imagenilai[position]);

            return row;
        }
    }

    class LoadKode extends AsyncTask<String, Integer, ArrayList<HashMap<String, String>>> {

        ArrayList<HashMap<String, String>> newList = new ArrayList<HashMap<String, String>>();
        JSONParserNew jParser = new JSONParserNew();
        String url = "http://tom-mbuddy.com/android/kode_training.php";
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

                    String a1 = obj.getString("kode")+" - "+obj.getString("training");
                    comboKode.add(a1);

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
            ArrayAdapter<String> aa = new ArrayAdapter<String>(nilai_training.this,R.layout.support_simple_spinner_dropdown_item, comboKode);
            aa.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            zkode.setAdapter(aa);
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(nilai_training.this, Goal_zero.class));
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
