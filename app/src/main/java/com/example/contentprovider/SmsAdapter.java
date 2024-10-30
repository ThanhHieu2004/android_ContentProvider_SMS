package com.example.contentprovider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SmsAdapter extends ArrayAdapter<SmsClass> {

    public SmsAdapter(Context context, List<SmsClass> smsList) {
        super(context, 0, smsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SmsClass sms = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sms_item, parent, false);
        }

        TextView senderTextView = convertView.findViewById(R.id.senderTextView);
        TextView messageTextView = convertView.findViewById(R.id.messageTextView);
        TextView dateTextView = convertView.findViewById(R.id.dateTextView);

        senderTextView.setText("From: " + sms.getSender());
        messageTextView.setText(sms.getMessage());
        dateTextView.setText(sms.getDate());

        return convertView;
    }
}
