package com.example.moher.chitchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DoctorListAdapter extends BaseAdapter {
    private Context mContext;
    List<Doctor> doctorList = new ArrayList<>();

    public DoctorListAdapter(Context context) {
        mContext = context;
    }

    public void updateDoctorList(List<Doctor> newDoctor) {
        this.doctorList = newDoctor;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return doctorList.size();
    }

    @Override
    public Object getItem(int position) {
        return doctorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView name;
        TextView specjalizacja;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.doctor_card, parent, false);
            name = (TextView) convertView.findViewById(R.id.doctor_name);
            specjalizacja = (TextView) convertView.findViewById(R.id.doctor_spec);
            convertView.setTag(R.id.doctor_name, name);
            convertView.setTag(R.id.doctor_spec, specjalizacja);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            name = holder.mLekarzName;
            specjalizacja = holder.mSpecjalizacja;
        }

        Doctor doc = (Doctor) getItem(position);

        name.setText(doc.getLekarz());
        specjalizacja.setText(doc.getSpecjalizacja());

        return convertView;
    }

    class ViewHolder {
        private TextView mLekarzName;
        private TextView mSpecjalizacja;

        ViewHolder(TextView mLekarzName, TextView specjalizacja) {
            this.mLekarzName = mLekarzName;
            this.mSpecjalizacja = specjalizacja;
        }
    }
}
