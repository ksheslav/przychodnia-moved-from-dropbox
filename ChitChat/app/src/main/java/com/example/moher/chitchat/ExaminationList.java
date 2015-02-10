package com.example.moher.chitchat;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExaminationList extends ListActivity {
    private ExaminationAdapter mExaminationAdapter;
    private static final String POST_URL = Common.ADRESS + "/get_examinations.php";
    private static final String BOOK_URL = Common.ADRESS + "/book_examination.php";
    private String mIdPacjenta;
    private String mUserId;
    private String mIdLekarza;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examination_list);
        Intent intent = getIntent();
        mUserId = intent.getStringExtra("userId");
        mIdPacjenta = intent.getStringExtra("idPacjenta");
        mIdLekarza = Integer.toString(intent.getIntExtra("idLekarza", 0));
        Log.e("GET!", mIdLekarza);
        mExaminationAdapter = new ExaminationAdapter(getApplicationContext());
        setListAdapter(mExaminationAdapter);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadExaminationList().execute();
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("Rezerwuj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new BookExamination((Examination) mExaminationAdapter.getItem(position)).execute();
            }
        });
        alert.setNeutralButton("Wróć", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create().show();
    }

    class BookExamination extends AsyncTask<String, String, String> {
        private Examination mExamination;

        public BookExamination(Examination examination) {
            mExamination = examination;
        }

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> valuelists = new ArrayList<NameValuePair>();
            valuelists.add(new BasicNameValuePair("badanieId", mExamination.getBadanieId()));
            valuelists.add(new BasicNameValuePair("userId", mUserId));
            Log.e("USER", mUserId);
            JSONObject json = new JSONParser().makeRequest(BOOK_URL, valuelists, "POST");
            try {
                if (json.getInt("success") == 1) {
                    ExaminationList.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Alerts.alert("Sukces", "Rezerwacja przeszła poprawnie,czekaj na decyzję lekarza", ExaminationList.this);
                        }
                    });
                } else if (json.getInt("success") == 2) {
                    ExaminationList.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Alerts.alert("Błąd", "Miejsce zajęte", ExaminationList.this);
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class LoadExaminationList extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> valuelists = new ArrayList<NameValuePair>();
            valuelists.add(new BasicNameValuePair("getexaminationlist", mIdLekarza));
            JSONObject json = new JSONParser().makeRequest(POST_URL, valuelists, "POST");
            final List<Examination> examinations = new ArrayList<>();
            SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
            try {
                if (json.getInt("success") == 1) {
                    JSONArray array = json.getJSONArray("badanie");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        String badanieId = Integer.toString(object.getInt("badanieId"));
                        Date date = (Date) dt.parse(object.getString("dataBadania"));
                        int sala = object.getInt("sala");
                        String userId = object.getString("userId");
                        int accepted = object.getInt("accepted");
                        String nazwabadania = object.getString("nazwabadania");
                        if (userId.equals("null")) {
                            Examination examination = new Examination(badanieId, 0, date, sala, accepted);
                            examination.setExaminationName(nazwabadania);
                            examinations.add(examination);
                        } else if (userId.equals(mUserId)) {
                            Examination examination = new Examination(badanieId, Integer.parseInt(userId), date, sala, accepted);
                            examination.setExaminationName(nazwabadania);
                            examinations.add(examination);
                        }


                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ExaminationList.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mExaminationAdapter.updateExaminations(examinations);
                }
            });
            return null;
        }
    }
}
