package com.binhtranphuoc97.sqlitedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by BinhTran on 10/19/2017.
 */

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtName, edtNumber, edtAddress;
    private RadioGroup rbGroup;
    private RadioButton rbgender;
    private Button btnAdd, btnCancel;
    private MyDatabase db = new MyDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setTitle("Add");
        init();
    }

    private void add() {
        Contact contact = new Contact();
        contact.setAddress(edtAddress.getText().toString());
        contact.setName(edtName.getText().toString());
        contact.setNumber(edtNumber.getText().toString());
        long day = System.currentTimeMillis();
        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date(day));
        contact.setDate(date);
        String h = new SimpleDateFormat("hh:mm:ss").format(new Date(day));
        contact.setTime(h);

        int idrad = rbGroup.getCheckedRadioButtonId();
        rbgender = (RadioButton) findViewById(R.id.idrad);
        contact.setGender(rbgender.getText().toString());

        db.open();
        int id = (int) db.addContact(contact);
        contact.setId(id);
        db.close();

        Intent i = new Intent();
        Bundle b = new Bundle();
        b.putSerializable("RETURN", contact);
        i.putExtras(b);
        setResult(RESULT_OK, i);
        finish();

    }


    private void init() {
        edtAddress = (EditText) findViewById(R.id.edtaddress);
        edtName = (EditText) findViewById(R.id.edtuser);
        edtNumber = (EditText) findViewById(R.id.edtphone);
        rbGroup = (RadioGroup) findViewById(R.id.rbGender);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id= view.getId();
        switch (id) {
            case R.id.btnAdd:
                add();break;
            case  R.id.btnCancel:
                finish();
                break;
        }
    }


}
