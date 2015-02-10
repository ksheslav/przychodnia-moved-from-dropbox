package com.example.moher.chitchat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExaminationAdapter extends BaseAdapter {
    private Context mContext;
    List<Examination> examinationList = new ArrayList<Examination>();

    public ExaminationAdapter(Context context) {
        mContext = context;
    }

    public void updateExaminations(List<Examination> newExaminations) {
        this.examinationList = newExaminations;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return examinationList.size();
    }

    @Override
    public Object getItem(int position) {
        return examinationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView mAcceptanceImage;
        TextView mNazwaBadania;
        TextView mDzień;
        TextView mGodzina;
        TextView mSala;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.examination_card, parent, false);
            mAcceptanceImage = (ImageView) convertView.findViewById(R.id.acceptance_state);
            mNazwaBadania = (TextView) convertView.findViewById(R.id.examination_name);
            mDzień = (TextView) convertView.findViewById(R.id.examination_time);
            mGodzina = (TextView) convertView.findViewById(R.id.examination_hour);
            mSala = (TextView) convertView.findViewById(R.id.examination_place);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            mAcceptanceImage = holder.mAcceptanceImage;
            mNazwaBadania = holder.mNazwaBadania;
            mDzień = holder.mDzień;
            mGodzina = holder.mGodzina;
            mSala = holder.mSala;
        }

        Examination examination = (Examination) getItem(position);
        mAcceptanceImage.setImageResource(setAccetedState(examination.getAccepted()));
        mNazwaBadania.setText(examination.getNazwabadania());
        mDzień.setText(getDay(examination.getDate()));
        mGodzina.setText(getHour(examination.getDate()));
        mSala.setText(Integer.toString(examination.getSala()));
        return convertView;
    }

    private String getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return day + "." + month + "." + year;

    }

    private String getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        return hour + ":" + minutes;
    }

    private Drawable getDrawable(int state) {
        return mContext.getResources().getDrawable(setAccetedState(state));
    }

    private int setAccetedState(int state) {
        switch (state) {
            case 0:
                return R.drawable.ic_wait_for_acceptance;
            case 1:
                return R.drawable.ic_action_done;
            case 2:
                return R.drawable.ic_rejected;
            default:
                return R.drawable.ic_wait_for_acceptance;
        }
    }

    class ViewHolder {
        private ImageView mAcceptanceImage;
        private TextView mNazwaBadania;
        private TextView mDzień;
        private TextView mGodzina;
        private TextView mSala;

        public ViewHolder(ImageView mAcceptanceImage, TextView mLekarzName,
                          TextView mNazwaBadania, TextView mDzień,
                          TextView mGodzina, TextView mSala) {
            this.mAcceptanceImage = mAcceptanceImage;
            this.mNazwaBadania = mNazwaBadania;
            this.mDzień = mDzień;
            this.mGodzina = mGodzina;
            this.mSala = mSala;
        }
    }
}
