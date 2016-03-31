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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pegawai.adapter.ERPadapter;

public class ER_Procedure extends AppCompatActivity {

    //navigasi
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    //instansiasi navigation header
    String tfnik, tfnama, tfbatch, tfjbtn;
    TextView nik, nama;
    Intent in;
    // end instansiasi

    ListView list;
    String[] web = {
            "Jika Terjadi Kebakaran di SPBU",
            "Jika Terjadi Kebakaran di Kendaraan",
            "Jika Tangki Kebakaran",
            "Jika Tangki Bocor",
            "Jika Terjadi Perampokan Saat Kejadian dan Sesudahnya",
            "Jika Terjadi Ancaman Bom",
            "Jika Terjadi Kontak Fisik dengan Bahan Bakar",
            "Jika Terjadi Orang Terbakar",
            "Jika Terjadi Demonstrasi atau Kerusuhan",
            "Jika Terjadi Bencana Alam"
    } ;

    Integer[] imageId = {
            R.drawable.erp1,
            R.drawable.erp2,
            R.drawable.erp3,
            R.drawable.erp4,
            R.drawable.erp5,
            R.drawable.erp6,
            R.drawable.erp7,
            R.drawable.erp8,
            R.drawable.erp9,
            R.drawable.erp10

    };

    String[] texkJudul = {
            "Matikan semua pompa dan semua arus listrik. \n " +
                    "Hubungi pemadan kebakaran. \n " +
                    "Kosongkan tempat kejadian. \n " +
                    "Jika memungkinkan, gunakan tabung pemadam kebakaran.",

            "Segera keluarkan orang-orang yang berada dalam kendaraan. \n" +
                    "Jika memungkinkan, dorong kendaraan menjauh dari pompa bahan bakar.\n" +
                    "Semprotkan rabung pemadam kebakaran melalui celah di bawah kap mesin searah arah angin.\n" +
                    "Jangan membuka kap mesin lebar-lebar.",

            "Hentikan aliran bhan bakar di SPBU shell lalu matikan semua arus listrik.\n" +
                    "Evakuasi semua orang yang melihat kejadian dan para karyawan.\n" +
                    "Hubungi pemdam kebakaran.\n" +
                    "Jika memungkinkan, padamkan api dengan tabung pemadam kebakaran.\n " +
                    "Jika aman, tutup pipa tangki dan pipa pengisian.\n" +
                    "Jka memungkinkan lokalisir dampak kebocoran bakan bakar menggukanakn produk spill kit yang tersedia.",

            "Matikan semua pompa bahan bakar dan arus listrik.\n" +
                    "Jangan biarkan satupun mesin kendaraan menyala.\n" +
                    "Hubungi satuan pemadam kebakaran.\n" +
                    "Lokalisir dampak kebocoran bahan bakar menggunakan produk spill kit yang tersedia.\n" +
                    "Siapkan tabung pemadam kebakaran jika perlu.",

            "Tetap tenang dan turuti petunjuk perampok.\n" +
                    "Tetap waspada dan secara diam-diam perhatikan gerak-gerik perampok.\n" +
                    "Jangan melihat langsung pada para perampok dan jangan membantah.\n" +
                    "Setelah perampokan, tetap tinggal dulu ti tempat kejadian.\n" +
                    "Hubungi polisi dan Territory Manager.\n" +
                    "Tutup tempat kejadian dan biarkan semua seperti saat kejadian.\n" +
                    "Bersama saksi mata, tulis semua kejadian secara terperinci.",

            "Kumpulkan sebanyak mungkin informasi berkaitan dengan bom tersebut, dan catat. Ajukan pertanyaan seperti :\n" +
                    "- Seperti apa bentuk bom tersebut?\n" +
                    "- Kapan akan diledakkan?\n" +
                    "- Dimana diletakkan?\n" +
                    "Laporkan kepada Polisi dan Territory Manager.\n" +
                    "Jika Territory Manager menginstruksikan penutupan SPBU Shell, aktifkan emergency shut-off button. Jelaskan dengan tenang kepada pelanggan untuk segera meninggalkan SPBU Shell.\n" +
                    "Jika ditemukan paket mencurigakan, jangan disentuh. Informasikan kepada Territory Manager.\n" +
                    "Evakuasi orang-orang dari SPBU Shell dan tunggu perintah selanjutnya dari Territory Manager.",

            "Jika cairan bahan bakar kena mata, jangan diusap.\n" +
                    "Pelan-pelan alirkan air ke tempat yang  terkena bocoran selama 20 menit.\n" +
                    "Segera cari pertolongan medis.\n" +
                    "Jika cairan kena baju, jangan digosok karena bisa memicu kebakaran. Namun segera bersihkan dengan air.\n" +
                    "Jika tertelan, jangan dipaksa untuk memuntahkan.\n" +
                    "Segera cari pertolongan medis.",

            "Paksakan korban untuk segera berbaring.\n" +
                    "Padamkan api dengan cara menutup korban dengan selimut mulai dari kepala ke arah kaki.\n" +
                    "Biarkan selimut menutup tubuh korban.\n" +
                    "Ambil selimut dari tubuh korban, dan siram tubuh korban dengan air sampai basah kuyup.\n" +
                    "Segera cari pertolongan medis.",


            "Selam demonstrasi hubungi Territory Manager dan tutup SPBU Shell.\n" +
                    "Evakuasi semua karyawan.\n" +
                    "Matikan semua pompa dan sumber listrik.\n" +
                    "Setelah demonstrasi, cek keadaan dan kelengkapan karyawan.\n" +
                    "Cek adanya kerusakan pada peralatan atau bangunan.\n" +
                    "Hubungi Territory Manager untuk melakukan safety audit sebelum SPBU Shell dibuka untuk umum.",


            "Tetap tenang.\n" +
                    "Evakuasi semua orang yang ada di SPBU Shell.\n" +
                    "Setelah kejadia, cek keadaan dan kelengkapan karyawan.\n" +
                    "Cek adanya kerusakan pada peralatan atau bangunan.\n" +
                    "Hubungi Territory Manager untuk melakukan safety audit sebelum SPBU Shell dibuka untuk umum."



} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_er__procedure);

        final SharedPreferences sp = getSharedPreferences("DataGlobal", MODE_PRIVATE);
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
                        startActivity(new Intent(ER_Procedure.this,Employee.class));
                        return true;
                    case R.id.nav_license:
                        startActivity(new Intent(ER_Procedure.this,License.class));
                        return true;
                    case R.id.nav_goal:
                        startActivity(new Intent(ER_Procedure.this,Goal_zero.class));
                        return true;
                    case R.id.nav_erp:
                        startActivity(new Intent(ER_Procedure.this,ER_Procedure.class));
                        return true;
                    case R.id.nav_ecn:
                        startActivity(new Intent(ER_Procedure.this,EC_number.class));
                        return true;
                    case R.id.nav_number:
                        startActivity(new Intent(ER_Procedure.this,Team_number.class));
                        return true;
                    case R.id.nav_logout:
                        SharedPreferences sharedpreferences = getSharedPreferences("MyData", Context.MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(ER_Procedure.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Toast.makeText(ER_Procedure.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }
        });
        // End Navigation Drawer

        ERPadapter adapter = new ERPadapter(ER_Procedure.this, web, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(ER_Procedure.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();
                String index = String.valueOf(+position);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("foto", index);
                ed.putString("detail", texkJudul[+position]);
                ed.commit();
                startActivity(new Intent(ER_Procedure.this, Detail_ER.class));

            }
        });

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(ER_Procedure.this, Utama.class));
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
