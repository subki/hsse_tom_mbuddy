package com.example.pegawai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.AvoidXfermode;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class Utama extends AppCompatActivity {

    //drawer layout
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    //instansiasi navigation header
    TextView nik, nama;
    // end instansiasi

    String te;
    TextView intro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);

        te = "Selamat datang di Site HSSE m-Buddy, sebuah aplikasi mobile yang akan membantu Anda " +
                "untuk mengerti, memahami dan melaksanakan HSSE sebagai landasan beroperasi dari " +
                "site kita. \n \n";
        te += "Program ini hanya dipakai untuk lingkungan internal PD Trivia Oktana Mandiri, dan jika " +
                "Anda bukan Karyawan atau pihak yang ditunjuk oleh PD Trivia Oktana Mandiri, harap " +
                "segera Logout dari aplikasi ini. \n \n";
        te += "Aplikasi ini adalah aplikasi yang dikembangkan oleh PD Trivia Oktana Mandiri dan bukan " +
                "merupakan bagian dari PT Shell Indonesia. \n \n";
        te += "Gunakan aplikasi ini di tempat yang aman dari bahaya penggunaan Telepon / Traffic.";


        SharedPreferences sp = getSharedPreferences("DataGlobal", MODE_PRIVATE);

        //penting, untuk navigation header
        nik = (TextView) findViewById(R.id.nav_nik);
        nama = (TextView) findViewById(R.id.nav_nama);
        nik.setText(sp.getString("nik",""));
        nama.setText(sp.getString("nama", ""));

        intro = (TextView) findViewById(R.id.intro);
        intro.setText(te);

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
                        startActivity(new Intent(Utama.this,Employee.class));
                        return true;
                    case R.id.nav_license:
                        startActivity(new Intent(Utama.this,License.class));
                        return true;
                    case R.id.nav_goal:
                        startActivity(new Intent(Utama.this,Goal_zero.class));
                        return true;
                    case R.id.nav_erp:
                        startActivity(new Intent(Utama.this,ER_Procedure.class));
                        return true;
                    case R.id.nav_ecn:
                        startActivity(new Intent(Utama.this,EC_number.class));
                        return true;
                    case R.id.nav_number:
                        startActivity(new Intent(Utama.this,Team_number.class));
                        return true;
                    case R.id.nav_logout:
                        SharedPreferences sharedpreferences = getSharedPreferences("MyData", Context.MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(Utama.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Toast.makeText(Utama.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }
        });
        // End Navigation Drawer
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent in = new Intent();
            in = new Intent(Utama.this, MainActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
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
