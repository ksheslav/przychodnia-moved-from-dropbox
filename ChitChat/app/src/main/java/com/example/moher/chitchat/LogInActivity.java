package com.example.moher.chitchat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;


public class LogInActivity extends Activity {
    private EditText mLogin;
    private EditText mPasswordText;
    private JSONParser mJsonParser;
    private static final String POST_URL = Common.ADRESS + "/check_user.php";
    private static final String POST_URL_CHECK_PACJENT = Common.ADRESS + "/pacjent_exists.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLogin = (EditText) findViewById(R.id.login_entry);
        mPasswordText = (EditText) findViewById(R.id.password_entry);
        mJsonParser = new JSONParser();

    }

    public boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInterface = connectivityManager.getActiveNetworkInfo();
        return networkInterface != null && networkInterface.isConnected();

    }

    public void logIn(View view) {
        if (!isInternetConnected()) {
            Toast.makeText(this, "InternetIsDOwn", Toast.LENGTH_LONG).show();
        } else {

            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    new Login().execute();
                }
            });
        }
    }

    public void register(View view) {
        if (!isInternetConnected()) {
            Toast.makeText(this, "InternetIsDOwn", Toast.LENGTH_LONG).show();
        } else {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }

    class Login extends AsyncTask<String, String, String> {
        private ProgressDialog mProgressDialog;
        private String mName;
        private String mPassword;
        private int mSuccess;
        private int mUserId;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(LogInActivity.this);
            mProgressDialog.setMessage("Login");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            mName = mLogin.getText().toString();
            mPassword = mPasswordText.getText().toString();

            List<NameValuePair> valuelists = new ArrayList<NameValuePair>();
            valuelists.add(new BasicNameValuePair("nickname", mName));
            valuelists.add(new BasicNameValuePair("password", mPassword));


            JSONObject json = mJsonParser.makeRequest(POST_URL, valuelists, "POST");

            try {
                mSuccess = json.getInt("success");

                if (mSuccess == 1) {
                    String userId = json.getJSONArray("user").getJSONObject(0).getString("userId");
                    Log.e("SHOW!", userId);

                    mUserId = Integer.parseInt(userId);
                    valuelists = new ArrayList<NameValuePair>();
                    valuelists.add(new BasicNameValuePair("userId", Integer.toString(mUserId)));
                    json = mJsonParser.makeRequest(POST_URL_CHECK_PACJENT, valuelists, "POST");

                    switch (json.getInt("success")) {
                        case 1:

                            Intent examinationIntent = new Intent(LogInActivity.this, DoctorList.class);
                            examinationIntent.putExtra("userId", mUserId);
                            examinationIntent.putExtra("idPacjenta",
                                    json.getJSONArray("user").getJSONObject(0).getString("idPacjenta"));
                            finish();
                            startActivity(examinationIntent);

                            break;
                        case 2:
                            Intent intent = new Intent(LogInActivity.this, PacjentRegister.class);
                            intent.putExtra("userId", mUserId);
                            finish();
                            startActivity(intent);
                            break;
                        case 3:
                            break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProgressDialog.dismiss();
            switch (mSuccess) {
                case 2:
                    LogInActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Alerts.alert("ERROR", "user not found!", LogInActivity.this);
                        }
                    });
                    break;
                case 3:
                    break;
            }
        }
    }
}
