package com.techintegrated.vaish.securitycheck;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button b;
    EditText et;
    Switch sw;
    String st, ph, cse = "+919640072040", eee = "+919951923915", ece = "+917702158128", mech = "+919640072040";
    String suff = " has left the college premises", pre = "Your student with regd. roll ";
    String py = " with your permission.", pn = " without your permission.", p;
    String stmt;
    Boolean permit = false;
    int REQUEST_PERMIT = 1;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = (Button) findViewById(R.id.button);
        et = (EditText) findViewById(R.id.editText);
        sw = (Switch) findViewById(R.id.switch1);
        t = (TextView) findViewById(R.id.textView4);
        b.setEnabled(true);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                st = et.getText().toString(); //assigning roll to a variable
                permit = sw.isChecked(); //saving toggle switch's state
                sendSMS();
            }
        });

    }

    private void sendSMS() {
        if(st.length() < 10) {
            //Toast.makeText(getApplicationContext(), "Enter valid roll number!", Toast.LENGTH_LONG).show();
            et.setText("");
            sw.setChecked(false);
        }
        else if(st.charAt(7) == '2')
            ph = eee;
        else if(st.charAt(7) == '5')
            ph = cse;
        else if(st.charAt(7) == '4')
            ph = ece;
        else if(st.charAt(7) == '3')
            ph = mech;

        if(permit)
            p = py;
        else
            p = pn;
        stmt = pre + st + suff + p + ph;
        t.setText(stmt);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS}, REQUEST_PERMIT);
        }
        else {
            SmsManager manager = SmsManager.getDefault();
            manager.sendTextMessage(ph, null, stmt, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent successfully!", Toast.LENGTH_LONG).show();
            et.setText("");
            sw.setChecked(false);
        }
    }
}
