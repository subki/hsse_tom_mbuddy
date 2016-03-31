package com.example.pegawai;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import com.example.pegawai.database.CustomHttpClient;
import com.example.pegawai.database.JSONParserNew;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Employee extends AppCompatActivity{

    //navigasi
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    //instansiasi navigation header
    String tfnik, tfnama, tfbatch, tfjbtn;
    TextView nik, nama;
    Intent in;
    RelativeLayout data;
    // end instansiasi

    //load profile
    String nik_pegawai, a_nik, a_nama, a_ttl, a_alamat, a_alamat_now, a_phone, a_nama_darurat, a_nomor_darurat, a_hubungan, a_batch, a_hp, a_status, a_pendidikan, a_jabatan, a_tmk, a_gambar;
    TextView b_nik, b_nama, b_ttl, b_alamat, b_alamat_now, b_phone, b_nama_darurat, b_nomor_darurat, b_hubungan, b_batch, b_hp, b_status, b_pendidikan, b_jabatan, b_tmk;
    ImageView b_gambar;
    ProgressDialog pDialog;
    JSONParserNew jsonParser = new JSONParserNew();
    JSONObject profile;
    private static final String PROFILE_URL = "http://tom-mbuddy.com/android/pegawai_detail.php";


    //scanner
    private static final int ZBAR_SCANNER_REQUEST = 0;
    private static final int ZBAR_QR_SCANNER_REQUEST = 1;

    ArrayList <String> mkode = new ArrayList<>();
    ArrayList <String> mtraining = new ArrayList<>();
    ArrayList <String> mtrainer = new ArrayList<>();
    ArrayList <String> mlevel = new ArrayList<>();
    ArrayList <String> mtanggals = new ArrayList<>();
    ArrayList <String> mnilai = new ArrayList<>();
    ArrayList <String> mverify = new ArrayList<>();

    int thn, bln, tgl, date_now;

    ArrayList <String> kode = new ArrayList<>();
    ArrayList <String> training = new ArrayList<>();
    ArrayList <String> trainer = new ArrayList<>();
    ArrayList <String> level = new ArrayList<>();
    ArrayList <String> tanggals = new ArrayList<>();
    ArrayList <String> statuss = new ArrayList<>();
    ArrayList <String> score = new ArrayList<>();
    ArrayList <String> verifyed = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        // inisialisasi
        b_nik = (TextView) findViewById(R.id.b_nik);
        b_nama = (TextView) findViewById(R.id.b_nama);
        b_ttl = (TextView) findViewById(R.id.b_ttl);
        b_alamat = (TextView) findViewById(R.id.b_alamat);
        b_jabatan = (TextView) findViewById(R.id.b_jabatan);
        b_tmk = (TextView) findViewById(R.id.b_tmk);
        b_alamat_now = (TextView) findViewById(R.id.b_alamat_now);
        b_phone= (TextView) findViewById(R.id.b_phone);
        b_nama_darurat= (TextView) findViewById(R.id.b_nama_darurat);
        b_nomor_darurat= (TextView) findViewById(R.id.b_nomor_darurat);
        b_hubungan= (TextView) findViewById(R.id.b_hubungan);
        b_batch= (TextView) findViewById(R.id.b_location);
        b_gambar = (ImageView) findViewById(R.id.b_gambar);
        data = (RelativeLayout) findViewById(R.id.R_data);
        data.setVisibility(View.GONE);
        //end

        SharedPreferences sp = getSharedPreferences("DataGlobal", MODE_PRIVATE);
        //penting, untuk navigation header
        nik = (TextView) findViewById(R.id.nav_nik);
        nama = (TextView) findViewById(R.id.nav_nama);
        nik.setText(sp.getString("nik",""));
        nama.setText(sp.getString("nama", ""));
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
                        startActivity(new Intent(Employee.this,Employee.class));
                        return true;
                    case R.id.nav_license:
                        startActivity(new Intent(Employee.this,License.class));
                        return true;
                    case R.id.nav_goal:
                        startActivity(new Intent(Employee.this,Goal_zero.class));
                        return true;
                    case R.id.nav_erp:
                        startActivity(new Intent(Employee.this,ER_Procedure.class));
                        return true;
                    case R.id.nav_ecn:
                        startActivity(new Intent(Employee.this,EC_number.class));
                        return true;
                    case R.id.nav_number:
                        startActivity(new Intent(Employee.this,Team_number.class));
                        return true;
                    case R.id.nav_logout:
                        SharedPreferences sharedpreferences = getSharedPreferences("MyData", Context.MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(Employee.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Toast.makeText(Employee.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }
        });
        // End Navigation Drawer

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(!sp.getString("role","").equals("USER")){
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //nik_pegawai = "TOM073100915";
                    //new AsyncTaskProfile().execute(nik_pegawai);
                    if (isCameraAvailable()) {
                        Intent intent = new Intent(Employee.this, ZBarScannerActivity.class);
                        startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
                    } else {
                        Toast.makeText(Employee.this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            fab.setVisibility(View.GONE);
            nik_pegawai = tfnik;
            new AsyncTaskProfile().execute(nik_pegawai);
            data.setVisibility(View.VISIBLE);
        }

        Calendar kalender = new GregorianCalendar();
        thn = kalender.get(Calendar.YEAR);
        date_now = kalender.get(Calendar.DAY_OF_YEAR);

    }


    //If Camera is On
    public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    //If Camera Detect a Barcode or QRCode
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ZBAR_SCANNER_REQUEST:
            case ZBAR_QR_SCANNER_REQUEST:
                if (resultCode == RESULT_OK) {
                    //get data and parsing then show to view
                    String result = data.getStringExtra(ZBarConstants.SCAN_RESULT);
                    nik_pegawai = result;
                    //new AsyncTaskCek().execute(nik_pegawai);
                    new AsyncTaskProfile().execute(nik_pegawai);
                    //Toast.makeText(this, "Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT), Toast.LENGTH_SHORT).show();
                } else if(resultCode == RESULT_CANCELED && data != null) {
                    String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
                    if(!TextUtils.isEmpty(error)) {
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(Employee.this, Utama.class));
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
    private class AsyncTaskCek extends AsyncTask<String, Void, String> {
        String response = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Employee.this);
            pDialog.setMessage("Load Data \nPlease wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
            post.add(new BasicNameValuePair("nik", params[0]));
            String res = null;
            try {
                response = CustomHttpClient.executeHttpPost("http://tom-mbuddy.com/android/pegawai_cek.php", post);
                System.out.println("Ini respon :" + response);
                res = response.toString();
                System.out.println("Ini respon :" + res);
                res = res.trim();
                res = res.replaceAll("\\s", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            System.out.println("subki:"+result);
            if (result.equals("1")) {
                //kalo user ada
                System.out.println("ini nik nya : "+nik_pegawai);
                new AsyncTaskProfile().execute(nik_pegawai);
                data.setVisibility(View.VISIBLE);
            } else {
                LinearLayout layout = new LinearLayout(Employee.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                TextView pesan = new TextView(Employee.this);
                pesan.setText("NIK : "+nik_pegawai+" invalid \n Scan ulang?");
                layout.addView(pesan);

                AlertDialog.Builder builder = new AlertDialog.Builder(Employee.this);
                builder.setIcon(R.drawable.ic_error);
                builder.setTitle("Error");
                builder.setView(layout);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        //scan ulang
                        if (isCameraAvailable()) {
                            Intent intent = new Intent(Employee.this, ZBarScannerActivity.class);
                            startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
                        } else {
                            Toast.makeText(Employee.this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        }
    }

    class AsyncTaskTraining extends  AsyncTask <String, Integer, ArrayList<HashMap<String, String>>>{


        ArrayList<HashMap<String, String>> newList = new ArrayList<HashMap<String, String>>();
        JSONParserNew jParser = new JSONParserNew();
        String url = "http://tom-mbuddy.com/android/employee_get_training.php";
        JSONArray train = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Employee.this);
            pDialog.setMessage("Getting Data Training...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... args) {
            //Building Parameters
            HashMap<String, String> params = new HashMap<>();
            //params.put("batch", args[0]);
            params.put("nik", args[0]);

            // Getting JSON from URL
            JSONObject json = jParser.makeHttpRequest(url, "GET",params);
            System.out.println("Ini JSON nya : "+json);
            try{
                train = json.getJSONArray("data");
                System.out.println("ini phone : " + train);

                //kosongi arraynya dulu
                mkode.clear();
                mtraining.clear();
                mlevel.clear();
                mtrainer.clear();
                mtanggals.clear();
                mnilai.clear();
                mverify.clear();

                mkode.add("Kode");
                mtraining.add("Topic Training");
                mtanggals.add("Tanggal");
                mtrainer.add("Trainer");
                mlevel.add("Lvl");
                mnilai.add("Scr");
                mverify.add("Verifyed By");
                for(int i=0; i<train.length(); i++){
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject obj = train.getJSONObject(i);

                    String a1 = obj.getString("kode");
                    mkode.add(a1);
                    String a2 = obj.getString("training");
                    mtraining.add(a2);
                    String a3 = obj.getString("tgl");
                    mtanggals.add(a3);
                    String a4 = obj.getString("trainer");
                    mtrainer.add(a4);
                    String a5 = obj.getString("level");
                    mlevel.add(a5);
                    String a6 = obj.getString("nilai");
                    mnilai.add(a6);
                    String a7 = obj.getString("verify");
                    mverify.add(a7);

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

            System.out.println("Kode"+mkode);
            System.out.println("training"+mtraining);
            System.out.println("tanggal"+mtanggals);
            System.out.println("trainer"+mtrainer);
            System.out.println("level"+mlevel);
            System.out.println("level"+mnilai);
            System.out.println("level"+mverify);

            TableLayout tLayout = (TableLayout) findViewById(R.id.tableTrainingNonBasic);
            TableRow tRow;
            TextView t1, t2, t3, t4, t5, t6;
            ImageView bg;

            System.out.println("ini panjangnya : "+mkode.size());
            for(int i =0; i<mkode.size(); i++) {
                if (!mlevel.get(i).equals("1")) {
                    tRow = new TableRow(Employee.this);
                    tRow.setPadding(2,2,2,2);

                    t1 = new TextView(Employee.this);
                    t2 = new TextView(Employee.this);
                    t3 = new TextView(Employee.this);
                    t4 = new TextView(Employee.this);
                    t5 = new TextView(Employee.this);
                    t6 = new TextView(Employee.this);
                    bg = new ImageView(Employee.this);

                    t1.setTextSize(12);
                    t2.setTextSize(12);
                    t3.setTextSize(12);
                    t4.setTextSize(12);
                    t5.setTextSize(12);
                    t6.setTextSize(12);

                    t1.setTextColor(Color.BLACK);
                    t2.setTextColor(Color.BLACK);
                    t3.setTextColor(Color.BLACK);
                    t4.setTextColor(Color.BLACK);
                    t5.setTextColor(Color.BLACK);
                    t6.setTextColor(Color.BLACK);

                    if (i != 0) {
                        if (mnilai.get(i).trim().equals("25")) {
                            bg.setImageResource(R.drawable.ic_25);
                        } else if (mnilai.get(i).trim().equals("50")) {
                            bg.setImageResource(R.drawable.ic_50);
                        } else if (mnilai.get(i).trim().equals("75")) {
                            bg.setImageResource(R.drawable.ic_75);
                        } else if (mnilai.get(i).trim().equals("100")) {
                            bg.setImageResource(R.drawable.ic_100);
                        } else {
                            bg.setImageResource(R.drawable.ic_blank);
                        }
                        tRow.setBackgroundColor(Color.CYAN);
                    } else {
                        tRow.setBackgroundColor(Color.GRAY);

                        t1.setTextColor(Color.WHITE);
                        t2.setTextColor(Color.WHITE);
                        t3.setTextColor(Color.WHITE);
                        t4.setTextColor(Color.WHITE);
                        t5.setTextColor(Color.WHITE);
                        t6.setTextColor(Color.WHITE);
                    }

                    //String kd = (String) kode.get(i).substring(kode.get(i).length()-2);

                    t1.setText((String) mkode.get(i));
                    t2.setText((String) mtraining.get(i));
                    t3.setText((String) mtanggals.get(i));
                    t4.setText((String) mtrainer.get(i));
                    t5.setText((String) mverify.get(i));
                    t6.setText((String) mnilai.get(i));


                tRow.addView(t1);
                    tRow.addView(t2);
                    tRow.addView(t3);
                    tRow.addView(t4);
                    tRow.addView(t5);
                    if (i != 0) {
                        tRow.addView(bg);
                    } else {
                        tRow.addView(t6);
                    }

                    tLayout.addView(tRow, new TableLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                }
            }
            new DownloadImageTask((ImageView) findViewById(R.id.b_gambar)).execute(a_gambar);
        }

    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Employee.this);
            pDialog.setMessage("Load Image \nPlease wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            pDialog.dismiss();
            if(result != null){
                bmImage.setImageBitmap(result);
            }else{
                bmImage.setImageResource(R.drawable.no);
            }

        }
    }

    class AsyncTaskProfile extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Employee.this);
            pDialog.setMessage("Load Data Personal \nPlease wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            // Building Parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("nik", nik_pegawai);
            // getting JSON string from URL
            JSONObject json = jsonParser.makeHttpRequest(PROFILE_URL, "GET",params);
            try {
                // profile json object
                profile = json.getJSONObject("data");
                tfnik = profile.getString("nik");
                tfnama= profile.getString("nama");

                a_nik = profile.getString("nik");
                a_nama = profile.getString("nama");
                a_alamat = profile.getString("alamat");
                a_alamat_now = profile.getString("alamat_now");
                a_ttl = profile.getString("tmp_lhr")+ " / " +profile.getString("tgl_lhr");
                a_hp = profile.getString("nohp");
                a_phone = profile.getString("phone")+" / "+profile.getString("nohp");
                a_status = profile.getString("status");
                a_jabatan = profile.getString("jabatan");
                a_pendidikan = profile.getString("pendidikan");
                a_tmk = profile.getString("tgl_masuk");
                a_gambar = profile.getString("profile");
                a_nama_darurat = profile.getString("nama_darurat");
                a_nomor_darurat = profile.getString("nomor_darurat");
                a_hubungan = profile.getString("hubungan");
                a_batch = profile.getString("location");


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            //close loader
            pDialog.dismiss();
            // updating UI from Background Thread
            if(file_url != "") {
                // displaying all data in textview
                b_nik.setText(a_nik);
                b_nama.setText(a_nama);
                b_alamat.setText(a_alamat);
                b_alamat_now.setText(a_alamat_now);
                b_ttl.setText(a_ttl);
                b_jabatan.setText(a_jabatan);
                b_tmk.setText(a_tmk);
                b_phone.setText(a_phone);
                b_nama_darurat.setText(a_nama_darurat);
                b_nomor_darurat.setText(a_nomor_darurat);
                b_hubungan.setText(a_hubungan);
                b_batch.setText(a_batch);

                data.setVisibility(View.VISIBLE);
                new AsyncTaskBasic().execute(a_nik);
            }
        }
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
            pDialog = new ProgressDialog(Employee.this);
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

        TableLayout basicLayout = (TableLayout) findViewById(R.id.tableTrainingBasic);

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
            tRow = new TableRow(Employee.this);
            tRow.setPadding(1,0,1,0);

            t1 = new TextView(Employee.this);
            t2 = new TextView(Employee.this);
            t3 = new TextView(Employee.this);
            t4 = new TextView(Employee.this);
            t5 = new TextView(Employee.this);
            t6 = new TextView(Employee.this);
            bg = new ImageView(Employee.this);

            t1.setTextSize(12);
            t2.setTextSize(12);
            t3.setTextSize(12);
            t4.setTextSize(12);
            t5.setTextSize(12);
            t6.setTextSize(12);

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


                    if (a != thn) {
                        int ngitung_bulan = bln + 12;
                        if (ngitung_bulan - b == 11 || ngitung_bulan - b == 12) {
                            tRow.setBackgroundColor(Color.YELLOW);
                        } else if (ngitung_bulan - b <= 10 || ngitung_bulan >= 3) {
                            tRow.setBackgroundColor(Color.GREEN);
                        } else if (ngitung_bulan - b >= 13) {
                            tRow.setBackgroundColor(Color.RED);
                        }
                    } else {
                        int ngitung_bulan = bln;
                        if (ngitung_bulan - b <= 2 || ngitung_bulan - b >= 0) {
                            tRow.setBackgroundColor(Color.YELLOW);
                        } else if (ngitung_bulan - b <= 10 || ngitung_bulan >= 3) {
                            tRow.setBackgroundColor(Color.GREEN);
                        } else if (ngitung_bulan - b >= 13) {
                            tRow.setBackgroundColor(Color.RED);
                        }
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
           // tRow.addView(t4);
            tRow.addView(t5);
            if(i != 0){
                tRow.addView(bg);
            }else {
                tRow.addView(t6);
            }

            basicLayout.addView(tRow, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        }

        new AsyncTaskTraining().execute(a_nik);
    }
}
