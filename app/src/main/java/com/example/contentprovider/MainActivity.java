package com.example.contentprovider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_SMS_PERMISSION = 123;
    private ListView smsListView;
    private SmsAdapter smsAdapter;
    private List<SmsClass> smsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smsListView = findViewById(R.id.smsListView);
        smsList = new ArrayList<>();
        smsAdapter = new SmsAdapter(this, smsList);
        smsListView.setAdapter(smsAdapter);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS}, REQUEST_SMS_PERMISSION);
        } else {
            readSmsMessages();
        }

        smsListView.setOnItemClickListener((parent, view, position, id) -> {
            SmsClass selectedSms = smsList.get(position);

            Intent intent = new Intent(MainActivity.this, MessageDetailActivity.class);
            intent.putExtra("sender", selectedSms.getSender());
            intent.putExtra("date", selectedSms.getDate());
            intent.putExtra("messageBody", selectedSms.getMessage());

            startActivity(intent);
        });
    }

    private void readSmsMessages() {
        Uri smsUri = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(smsUri, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String address = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                String body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE));

                smsList.add(new SmsClass(address, body, date));
            }
            cursor.close();
            smsAdapter.notifyDataSetChanged(); // Refresh the ListView with new data
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SMS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readSmsMessages();
            } else {
                Toast.makeText(this, "Không the truy cap SMS", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
