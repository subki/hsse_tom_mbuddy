package com.example.pegawai;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import com.example.pegawai.adapter.TabFragment;
import com.example.pegawai.database.CustomHttpClient;
import com.example.pegawai.database.JSONParserNew;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class License extends AppCompatActivity {


    //navigasi
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    //instansiasi navigation header
    String tfnik, tfnama, tfbatch, tfjbtn;
    TextView nik, nama;
    Intent in;
    // end instansiasi

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    String lvl;

    //scanner
    private static final int ZBAR_SCANNER_REQUEST = 0;
    private static final int ZBAR_QR_SCANNER_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);


        SharedPreferences sp = getSharedPreferences("DataGlobal", MODE_PRIVATE);

        //penting, untuk navigation header
        nik = (TextView) findViewById(R.id.nav_nik);
        nama = (TextView) findViewById(R.id.nav_nama);
        nik.setText(sp.getString("nik",""));
        nama.setText(sp.getString("nama", ""));

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
                        startActivity(new Intent(License.this,Employee.class));
                        return true;
                    case R.id.nav_license:
                        startActivity(new Intent(License.this,License.class));
                        return true;
                    case R.id.nav_goal:
                        startActivity(new Intent(License.this,Goal_zero.class));
                        return true;
                    case R.id.nav_erp:
                        startActivity(new Intent(License.this,ER_Procedure.class));
                        return true;
                    case R.id.nav_ecn:
                        startActivity(new Intent(License.this,EC_number.class));
                        return true;
                    case R.id.nav_number:
                        startActivity(new Intent(License.this,Team_number.class));
                        return true;
                    case R.id.nav_logout:
                        SharedPreferences sharedpreferences = getSharedPreferences("MyData", Context.MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(License.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Toast.makeText(License.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }
        });
        // End Navigation Drawer

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();

        // new AsyncTaskLicense().execute();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(!sp.getString("role","").equals("USER")){
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nik_pegawai = "TOM019210314";
                    new AsyncTaskProfile().execute(nik_pegawai);
                    //if (isCameraAvailable()) {
                    //    Intent intent = new Intent(License.this, ZBarScannerActivity.class);
                    //    startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
                    //} else {
                    //    Toast.makeText(License.this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
                    //}
                }
            });
        }else{
            fab.setVisibility(View.GONE);
        }
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
                    SharedPreferences sp = getSharedPreferences("DataGlobal", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("nik_scan", result);
                    ed.commit();
                    //new AsyncTaskCek().execute(result);
                    new AsyncTaskProfile().execute(result);
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

    private class AsyncTaskCek extends AsyncTask<String, Void, String> {
        String response = null;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(License.this);
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
            SharedPreferences dp = getSharedPreferences("DataGlobal", MODE_PRIVATE);
            if (result.equals("1")) {
                //kalo user ada
                new AsyncTaskProfile().execute(dp.getString("nik_scan",""));
            } else {
                LinearLayout layout = new LinearLayout(License.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                TextView pesan = new TextView(License.this);
                pesan.setText("NIK : "+dp.getString("nik_scan","")+" invalid \n Scan ulang?");
                layout.addView(pesan);

                AlertDialog.Builder builder = new AlertDialog.Builder(License.this);
                builder.setIcon(R.drawable.ic_error);
                builder.setTitle("Error");
                builder.setView(layout);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        //scan ulang
                        if (isCameraAvailable()) {
                            Intent intent = new Intent(License.this, ZBarScannerActivity.class);
                            startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
                        } else {
                            Toast.makeText(License.this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
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
    class AsyncTaskProfile extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;
        JSONParserNew jsonParser = new JSONParserNew();
        JSONObject profile;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(License.this);
            pDialog.setMessage("Load Data Personal \nPlease wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            // Building Parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("nik", args[0]);
            // getting JSON string from URL
            JSONObject json = jsonParser.makeHttpRequest("http://tom-mbuddy.com/android/pegawai_detail.php", "GET",params);
            try {
                SharedPreferences sp = getSharedPreferences("DataGlobal",MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                // profile json object
                profile = json.getJSONObject("data");
                ed.putString("nik_nilai", profile.getString("nik"));
                ed.putString("nama_nilai", profile.getString("nama"));
                ed.commit();

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
                startActivity(new Intent(License.this, nilai_training.class));
            }
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(License.this, Utama.class));
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
