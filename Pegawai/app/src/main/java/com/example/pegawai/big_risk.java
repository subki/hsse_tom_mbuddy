package com.example.pegawai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class big_risk extends AppCompatActivity {

    //navigasi
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    //instansiasi navigation header
    String tfnik, tfnama, tfbatch, tfjbtn;
    TextView nik, nama;
    Intent in;
    // end instansiasi


    TextView risk, why_risk, inisiatif;
    Button simpan, batal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_risk);

        final SharedPreferences sp = getSharedPreferences("DataPrivate", MODE_PRIVATE);
        //penting, untuk navigation header
        nik = (TextView) findViewById(R.id.nav_nik);
        nama = (TextView) findViewById(R.id.nav_nama);
        nik.setText(sp.getString("nik",""));
        nama.setText(sp.getString("name", ""));
        tfnik = sp.getString("nik", "");
        tfnama= sp.getString("nama", "");
        tfbatch = sp.getString("batch","");
        tfjbtn = sp.getString("jbtn", "");


        risk = (TextView) findViewById(R.id.resiko);
        why_risk = (TextView) findViewById(R.id.mengapa);
        inisiatif= (TextView) findViewById(R.id.inisiatif);
        simpan = (Button) findViewById(R.id.simpan_risk);
        batal = (Button) findViewById(R.id.batal_risk);

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
                        startActivity(new Intent(big_risk.this,Employee.class));
                        return true;
                    case R.id.nav_license:
                        startActivity(new Intent(big_risk.this,License.class));
                        return true;
                    case R.id.nav_goal:
                        startActivity(new Intent(big_risk.this,Goal_zero.class));
                        return true;
                    case R.id.nav_erp:
                        startActivity(new Intent(big_risk.this,ER_Procedure.class));
                        return true;
                    case R.id.nav_ecn:
                        startActivity(new Intent(big_risk.this,EC_number.class));
                        return true;
                    case R.id.nav_number:
                        startActivity(new Intent(big_risk.this,Team_number.class));
                        return true;
                    case R.id.nav_logout:
                        SharedPreferences sharedpreferences = getSharedPreferences("MyData", Context.MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(big_risk.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Toast.makeText(big_risk.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }
        });
        // End Navigation Drawer

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = risk.getText().toString();
                String b = why_risk.getText().toString();
                String c = inisiatif.getText().toString();
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("resiko_terbesar", a);
                ed.putString("mengapa_resiko", b);
                ed.putString("inisiatif_resiko", c);
                ed.commit();

                startActivity(new Intent(big_risk.this, Goal_zero.class));

            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(big_risk.this, Goal_zero.class));
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(big_risk.this, Goal_zero.class));
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
