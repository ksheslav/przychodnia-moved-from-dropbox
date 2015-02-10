package com.example.moher.chitchat;


import java.util.Date;

public class Examination {
    private String mBadanieId;
    private int mUserId;
    private Date mDate;
    private int mSala;
    private int mAccepted;
    private String mNazwabadania;

    public Examination(String badanieId, int userId, Date date, int sala, int accepted) {
        this.mBadanieId = badanieId;
        this.mUserId = userId;
        this.mDate = date;
        this.mSala = sala;
        this.mAccepted = accepted;
    }

    public void setExaminationName(String name) {
        this.mNazwabadania = name;
    }

    public String getBadanieId() {
        return mBadanieId;
    }

    public int getUserId() {
        return mUserId;
    }

    public Date getDate() {
        return mDate;
    }


    public int getSala() {
        return mSala;
    }

    public int getAccepted() {
        return mAccepted;
    }

    public String getNazwabadania() {
        return mNazwabadania;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o == this) {
            return true;
        } else if (!(o instanceof Examination)) {
            return false;
        }
        Examination ob = (Examination) o;
        return ob.mNazwabadania.equals(this.mNazwabadania)
                & ob.mBadanieId == this.mBadanieId
                & ob.mUserId == this.mUserId
                & ob.mDate.equals(this.mDate)
                & ob.mSala == this.mSala
                & ob.mAccepted == this.mAccepted;
    }


}
