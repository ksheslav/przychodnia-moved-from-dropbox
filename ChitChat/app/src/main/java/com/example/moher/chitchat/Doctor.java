package com.example.moher.chitchat;

import java.util.Date;

public class Doctor {

    private int mlekarzId;
    private String mLekarz;
    private String mSpecjalizacja;

    public Doctor(String lekarz, int mlekarzId, String mSpecjalizacja) {
        this.mlekarzId = mlekarzId;
        this.mSpecjalizacja = mSpecjalizacja;
        this.mLekarz = lekarz;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o == this) {
            return true;
        } else if (!(o instanceof Doctor)) {
            return false;
        }
        Doctor ob = (Doctor) o;
        return checkParameters(ob);
    }

    public String getLekarz() {
        return mLekarz;
    }

    public int getLekarzId() {
        return mlekarzId;
    }

    @Override
    public String toString() {
        return "Lekarz(" + mlekarzId + ") :" + mLekarz + " :" + mSpecjalizacja;

    }

    public String getSpecjalizacja() {
        return mSpecjalizacja;
    }

    private boolean checkParameters(Doctor ob) {
        return ob.mLekarz.equals(this.mLekarz) &
                ob.mlekarzId == this.mlekarzId &
                ob.mSpecjalizacja.equals(this.mSpecjalizacja);
    }


}
