package com.example.moher.chitchat;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DoctorList extends ListActivity {
    private DoctorListAdapter mDoctorListAdapter;
    private static final String POST_URL = Common.ADRESS + "/get_doctors.php";
    private String mIdPacjenta;
    private String mUserId;
    private String mIdLekarza;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_list);
        mDoctorListAdapter = new DoctorListAdapter(getApplicationContext());
        Intent extras = getIntent();
        mIdPacjenta = extras.getStringExtra("idPacjenta");
        mUserId = Integer.toString(extras.getIntExtra("userId", 0));
        setListAdapter(mDoctorListAdapter);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDoctorList().execute();
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Doctor doc = (Doctor) mDoctorListAdapter.getItem(position);
                Intent examinationIntent = new Intent(DoctorList.this, ExaminationList.class);
                examinationIntent.putExtra("userId", mUserId);
                examinationIntent.putExtra("idPacjenta", mIdPacjenta);
                examinationIntent.putExtra("idLekarza", doc.getLekarzId());
                startActivity(examinationIntent);
            }
        });

    }

    class LoadDoctorList extends AsyncTask<String, String, String> {
        //
        // ProgressDialog mProgressDialog;

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> valuelists = new ArrayList<NameValuePair>();
            valuelists.add(new BasicNameValuePair("getdoctorlist", "getdoctorlist"));
            JSONObject json = new JSONParser().makeRequest(POST_URL, valuelists, "POST");
            final List<Doctor> docs = new ArrayList<>();
            try {
                if (json.getInt("success") == 1) {
                    JSONArray array = json.getJSONArray("lekarz");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        Log.e("SHOW!", object.toString());
                        int lekarzId = Integer.parseInt(object.getString("lekarzId"));
                        String name = object.getString("imie") + " " + object.getString("nazwisko");
                        String specjalizacja = object.getString("specjalizacja");
                        docs.add(new Doctor(name, lekarzId, specjalizacja));
                        mIdLekarza = Integer.toString(lekarzId);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            DoctorList.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDoctorListAdapter.updateDoctorList(docs);
                }
            });
            return null;
        }

    }
}
