package com.example.pegawai;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pegawai.database.CustomHttpClient;
import com.example.pegawai.database.JSONParserNew;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {

    private EditText us, ps;
    private Button login;
    String user, pass;
    String response = null;

    ProgressDialog pDialog;

    //instansiasi navigation header
    String tfnik, tfnama, tfbatch, tfjbtn, tfrole, tfUsername, tfPassword;
    TextView nik, nama;
    JSONParserNew jsonParser = new JSONParserNew();
    JSONObject profile;
    JSONObject log;
    private static final String PROFILE_URL = "http://tom-mbuddy.com/android/pegawai_detail.php";
    // end instansiasi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        us = (EditText) findViewById(R.id.userFill);
        ps = (EditText) findViewById(R.id.passFill);
        nik = (TextView) findViewById(R.id.nav_nik);
        nama = (TextView) findViewById(R.id.nav_nama);

        login = (Button) findViewById(R.id.btnSignIn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = us.getText().toString();
                pass = ps.getText().toString();
                //cek user
                new AsyncTaskProfile().execute(user);
                //cek login
                //validateUserTask task = new validateUserTask();
                //task.execute(new String[]{user, pass});
            }
        });



        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    class AsyncTaskProfile extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            // Building Parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("nik", user);
            // getting JSON string from URL
            JSONObject json = jsonParser.makeHttpRequest(PROFILE_URL, "GET",params);
            try {
                // profile json object
                profile = json.getJSONObject("data");
                tfnik = profile.getString("nik");
                tfnama= profile.getString("nama");
                tfbatch = profile.getString("batch");
                tfjbtn= profile.getString("jabatan");
                tfrole= profile.getString("level");
                tfUsername= profile.getString("username");
                tfPassword= profile.getString("password");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            // updating UI from Background Thread
            if(file_url != "") {
                // displaying all data in textview
                nik.setText(tfnik);
                nama.setText(tfnama);
                System.out.println("username password :"+user+pass+"||"+tfUsername+tfPassword);
                if(tfUsername != null) {
                    if (tfUsername.equals(user) && tfPassword.equals(pass)) {
                        us.setText("");
                        ps.setText("");
                        Toast.makeText(getApplication(), "Login Success", Toast.LENGTH_LONG).show();
                        SharedPreferences sp = getSharedPreferences("DataGlobal", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();
                        ed.putString("nik", tfnik);
                        ed.putString("nama", tfnama);
                        ed.putString("batch", tfbatch);
                        ed.putString("jbtn", tfjbtn);
                        ed.putString("role", tfrole);
                        ed.commit();
                        startActivity(new Intent(MainActivity.this, Utama.class));
                    } else {
                        LinearLayout layoutInput = new LinearLayout(MainActivity.this);
                        layoutInput.setOrientation(LinearLayout.VERTICAL);

                        final TextView a1 = new TextView(MainActivity.this);
                        a1.setText("Login Error\n" +
                                "Wrong username & password or check connection");
                        layoutInput.addView(a1);

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setIcon(R.drawable.ic_phone);
                        builder.setTitle("Error");
                        builder.setView(layoutInput);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                us.setText("");
                                ps.setText("");
                                us.requestFocus();
                            }
                        });
                        builder.show();
                    }
                }else{
                    LinearLayout layoutInput = new LinearLayout(MainActivity.this);
                    layoutInput.setOrientation(LinearLayout.VERTICAL);

                    final TextView a1 = new TextView(MainActivity.this);
                    a1.setText("Login Error\n" +
                            "Wrong username & password or check connection");
                    layoutInput.addView(a1);

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setIcon(R.drawable.ic_phone);
                    builder.setTitle("Error");
                    builder.setView(layoutInput);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            us.setText("");
                            ps.setText("");
                            us.requestFocus();
                        }
                    });
                    builder.show();
                }
                //new validateUserTask().execute(user, pass);
                //System.out.println(tfjbtn);
                //id_penting = nik.getText().toString();
            }else{
                Toast.makeText(getApplication(), "NIK Invalid", Toast.LENGTH_LONG).show();
            }
        }
    }
    private class validateUserTask extends AsyncTask<String, String, String> {
        String urls="http://tom-mbuddy.com/android/login.php";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Login \nPlease wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> post = new HashMap<>();
            post.put("user", params[0]);
            post.put("pass", params[1]);
            System.out.println("parameter:" + post);
            JSONObject json = jsonParser.makeHttpRequest(urls, "GET",post);
            try {
                // profile json object
                log = json.getJSONObject("data");
                response = log.getString("message");
                System.out.println("respon :"+response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }
        protected void onPostExecute(String result) {
            if (response.trim()=="1") {
                us.setText("");
                ps.setText("");
                Toast.makeText(getApplication(), "Login Success", Toast.LENGTH_LONG).show();
                SharedPreferences sp = getSharedPreferences("DataGlobal", MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("nik", tfnik);
                ed.putString("name", tfnama);
                ed.putString("batch", tfbatch);
                ed.putString("jbtn", tfjbtn);
                ed.putString("role", tfrole);
                ed.commit();
                startActivity(new Intent(MainActivity.this, Utama.class));
            } else {
                LinearLayout layoutInput = new LinearLayout(MainActivity.this);
                layoutInput.setOrientation(LinearLayout.VERTICAL);

                final TextView a1 = new TextView(MainActivity.this);
                a1.setText("Login Error\n" +
                        "Wrong username & password or check connection");
                layoutInput.addView(a1);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.ic_phone);
                builder.setTitle("Error");
                builder.setView(layoutInput);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        us.setText("");
                        ps.setText("");
                        us.requestFocus();
                    }
                });
                builder.show();
            }
            pDialog.dismiss();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
