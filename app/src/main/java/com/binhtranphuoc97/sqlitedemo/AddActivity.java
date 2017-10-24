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
    private RadioGroup rbGender;
    private RadioButton rbgender;
    private Button btnAdd, btnCancel;
    private Bundle b;
    private MyDatabase db = new MyDatabase(this);
    Contact contact = new Contact();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setTitle("add Contact");
        init();
        b = getIntent().getExtras();
        if (b != null) {
            contact.setId(b.getInt("ID"));
            contact = db.getContact(contact.getId());
            setData();
        }
    }

    // update data
    public void setData() {
        getSupportActionBar().setTitle("Update Contact");
        btnAdd.setText("UPDATE");
        edtName.setText(contact.getName());
        edtAddress.setText(contact.getAddress());
        edtNumber.setText(contact.getNumber());
        if (contact.getGender().equals("Male")) {
            rbGender.check(R.id.male);
        } else rbGender.check(R.id.female);
    }

    private void init() {
        edtName = (EditText) findViewById(R.id.edtuser);
        edtAddress = (EditText) findViewById(R.id.edtaddress);
        edtNumber = (EditText) findViewById(R.id.edtphone);
        rbGender = (RadioGroup) findViewById(R.id.rbGender);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnAdd:
                add();
                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }

    private void add() {
        // khởi tạo đối tượng
        Contact contact = new Contact();
        contact.setAddress(edtAddress.getText().toString());
        contact.setName(edtName.getText().toString());
        contact.setNumber(edtNumber.getText().toString());
        long day = System.currentTimeMillis();
        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date(day));
        contact.setDate(date);
        String h = new SimpleDateFormat("hh:mm:ss").format(new Date(day));
        contact.setTime(h);
        // xử lý giới tính
        int idrad = rbGender.getCheckedRadioButtonId();
        rbgender = (RadioButton) findViewById(idrad);
        contact.setGender(rbgender.getText().toString());
        if (b != null) {
            db.updateContact(contact);
        } else {
            contact.setId(db.addContact(contact));
        }
        db.close();
        Intent i = new Intent();
        Bundle b = new Bundle();
        // put môt đối tượng
        b.putSerializable("RETURN", contact);
        i.putExtras(b);
        setResult(RESULT_OK, i);
        finish();
    }
}
