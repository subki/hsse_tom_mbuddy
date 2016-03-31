package com.example.pegawai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Detail_ER extends AppCompatActivity {

    Integer[] imageJudul = {
            R.drawable.a01,
            R.drawable.a02,
            R.drawable.a03,
            R.drawable.a04,
            R.drawable.a05,
            R.drawable.a06,
            R.drawable.a07,
            R.drawable.a08,
            R.drawable.a09,
            R.drawable.a10,

    };
    //navigasi
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    //instansiasi navigation header
    String tfnik, tfnama, tfbatch, tfjbtn;
    TextView nik, nama;
    Intent in;
    // end instansiasi

    ImageView gambar;
    TextView teks;
    String detail, foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__er);

        gambar = (ImageView) findViewById(R.id.image);
        teks = (TextView) findViewById(R.id.teks);

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
        foto = sp.getString("foto","");
        detail = sp.getString("detail","");

        System.out.println("foto hasil lmparan :"+foto);
        System.out.println("detail hasil lempar: "+detail);

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
                        startActivity(new Intent(Detail_ER.this,Employee.class));
                        return true;
                    case R.id.nav_license:
                        startActivity(new Intent(Detail_ER.this,License.class));
                        return true;
                    case R.id.nav_goal:
                        startActivity(new Intent(Detail_ER.this,Goal_zero.class));
                        return true;
                    case R.id.nav_erp:
                        startActivity(new Intent(Detail_ER.this,ER_Procedure.class));
                        return true;
                    case R.id.nav_ecn:
                        startActivity(new Intent(Detail_ER.this,EC_number.class));
                        return true;
                    case R.id.nav_number:
                        startActivity(new Intent(Detail_ER.this,Team_number.class));
                        return true;
                    case R.id.nav_logout:
                        SharedPreferences sharedpreferences = getSharedPreferences("MyData", Context.MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(Detail_ER.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Toast.makeText(Detail_ER.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }
        });
        // End Navigation Drawer

        //set gambar dan teks
        int no = Integer.parseInt(foto);
        gambar.setImageResource(imageJudul[no]);
        teks.setText(detail);

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(Detail_ER.this, ER_Procedure.class));
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
