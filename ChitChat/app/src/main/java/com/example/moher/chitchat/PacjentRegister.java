package com.example.moher.chitchat;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PacjentRegister extends Activity {
    private EditText mInputs[] = new EditText[6];
    private RadioGroup mRadioGroup;
    private int mUserId;
    private static final String POST_URL = Common.ADRESS + "/register_pacjent.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pacjent_register);
        mUserId = getIntent().getExtras().getInt("userId");
        mInputs[0] = (EditText) findViewById(R.id.imie_pacjent_register);
        mInputs[1] = (EditText) findViewById(R.id.nazwisko_pacjent_register);
        mInputs[2] = (EditText) findViewById(R.id.pesel_pacjent_register);
        mInputs[3] = (EditText) findViewById(R.id.telefon_pacjent_register);
        mInputs[4] = (EditText) findViewById(R.id.email_pacjent_register);
        mInputs[5] = (EditText) findViewById(R.id.adres_pacjent_register);
        mRadioGroup = (RadioGroup) findViewById(R.id.gender_register);
    }

    private boolean isEmpty(EditText[] inputs) {
        for (int i = 0; i < inputs.length; i++) {
            String text = inputs[i].getText().toString();
            if (text.length() == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean genderCheck(RadioGroup radioGroup) {
        if (radioGroup.getCheckedRadioButtonId() == R.id.man_register) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isDataCorrect() {
        if (mInputs[2].getText().toString().length() < 11 | mInputs[2].getText().toString().length() > 11) {
            Alerts.alert("Error", "Pesel jest za krótki", this);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mInputs[4].getText().toString()).matches()) {
            Alerts.alert("Error", "Zły email", this);
            return false;
        } else if (!Patterns.PHONE.matcher(mInputs[3].getText().toString()).matches()) {
            Alerts.alert("Error", "wprowadź poprawny numer telefonu", this);
            return false;
        }
        return true;
    }

    public void registerPacjent(View view) {
        if (isEmpty(mInputs)) {
            Alerts.alert("Error", "Wypełnij wszystkie pola", this);
        } else if (isDataCorrect()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new RegisterPacjent().execute();
                }
            });
        }
    }

    class RegisterPacjent extends AsyncTask<String, String, String> {
        private String mImie;
        private String mNazwisko;
        private String mPesel;
        private String mTelefon;
        private String mEmail;
        private String mAdres;
        private boolean mGender;
        private int mSuccess;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            mImie = mInputs[0].getText().toString();
            mNazwisko = mInputs[1].getText().toString();
            mPesel = mInputs[2].getText().toString();
            mTelefon = mInputs[3].getText().toString();
            mEmail = mInputs[4].getText().toString();
            mAdres = mInputs[5].getText().toString();
            mGender = genderCheck(mRadioGroup);

            List<NameValuePair> valuelists = new ArrayList<NameValuePair>();
            valuelists.add(new BasicNameValuePair("imie", mImie));
            valuelists.add(new BasicNameValuePair("nazwisko", mNazwisko));
            valuelists.add(new BasicNameValuePair("pesel", mPesel));
            valuelists.add(new BasicNameValuePair("telefon", mTelefon));
            valuelists.add(new BasicNameValuePair("email", mEmail));
            valuelists.add(new BasicNameValuePair("adres", mAdres));
            valuelists.add(new BasicNameValuePair("gender", Boolean.toString(mGender)));
            valuelists.add(new BasicNameValuePair("userId", Integer.toString(mUserId)));
            JSONObject json = new JSONParser().makeRequest(POST_URL, valuelists, "POST");
            try {
                mSuccess = json.getInt("success");

                //  finish();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
