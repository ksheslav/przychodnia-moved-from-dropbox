package com.example.moher.chitchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends Activity {
    private static final String POST_URL = Common.ADRESS + "/register_user.php";
    private EditText mPasswordText;
    private EditText mNicknameText;
    private JSONParser mJsonParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        mJsonParser = new JSONParser();
        mNicknameText = (EditText) findViewById(R.id.register_login_entry);
        mPasswordText = (EditText) findViewById(R.id.register_password_entry);
    }

    public void sendAndBack(View view) {
        if (mNicknameText.getText().toString().length() == 0 | mNicknameText.getText().toString().contains(" ")) {
            Alerts.alert("Stop!", "Login field is empty", this);
        } else if (mPasswordText.getText().toString().length() == 0 | mPasswordText.getText().toString().contains(" ")) {
            Alerts.alert("Stop!", "Password field is empty", this);
        } else {
            runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            new RegisterUser().execute();
                        }
                    });
        }
    }

    class RegisterUser extends AsyncTask<String, String, String> {
        private ProgressDialog mProgressDialog;
        private String mPassword;
        private String mNickname;
        private int mSuccess;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(RegisterActivity.this);
            mProgressDialog.setMessage("Creating User");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            mPassword = mPasswordText.getText().toString();
            mNickname = mNicknameText.getText().toString();

            List<NameValuePair> valuelists = new ArrayList<NameValuePair>();
            valuelists.add(new BasicNameValuePair("nickname", mNickname));
            valuelists.add(new BasicNameValuePair("password", mPassword));


            JSONObject json = mJsonParser.makeRequest(POST_URL, valuelists, "POST");
            try {
                mSuccess = json.getInt("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProgressDialog.dismiss();
            if (mSuccess == 2) {
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Alerts.alert("ERROR!", "User exists!", RegisterActivity.this);
                    }
                });
            } else if (mSuccess == 1) {
                finish();
            }
        }
    }


}
