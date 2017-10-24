package com.binhtranphuoc97.sqlitedemo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by BinhTran on 10/24/2017.
 */

public class DetailActivity extends AppCompatActivity {
    private Bundle b;
    Contact contact;
    MyDatabase db;
    TextView tvDetailName, tvDetailPhone, tvDetailAddress, tvDetailGender, tvDetailDate, tvDetailTime;
    ImageView ivCall, ivText;
    boolean edited = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setTitle("Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        getData();
        setData();
        btn_DetailAction();
    }

    private void btn_DetailAction() {
        //btn call
        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:" + tvDetailPhone.getText()));
                if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(call);
            }
        });

        // btn message
        ivText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent text = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + tvDetailPhone.getText()));
                text.putExtra("sms_body", "");
                startActivity(text);
            }
        });
    }
    // lay data tu db dua tren ID
    private void getData() {
        db = new MyDatabase(this);
        b = getIntent().getExtras();
        contact = new Contact();
        contact.setId(b.getInt("ID"));
        contact = db.getContact(contact.getId());
    }

    private void setData() {
        tvDetailName.setText(contact.getName());
        tvDetailAddress.setText(contact.getAddress());
        tvDetailGender.setText(contact.getGender());
        tvDetailDate.setText(contact.getDate());
        tvDetailTime.setText(contact.getTime());
        tvDetailPhone.setText(contact.getNumber());
    }

    private void init() {
        tvDetailName = (TextView) findViewById(R.id.tvDetailName);
        tvDetailPhone = (TextView) findViewById(R.id.tvDetailPhone);
        tvDetailAddress = (TextView) findViewById(R.id.tvDetailAddress);
        tvDetailGender = (TextView) findViewById(R.id.tvDetailGender);
        tvDetailDate = (TextView) findViewById(R.id.tvDetailDate);
        tvDetailTime = (TextView) findViewById(R.id.tvDetailTime);

        ivCall = (ImageView) findViewById(R.id.ivDetailCall);
        ivText = (ImageView) findViewById(R.id.ivDetailText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (edited) {
                    Intent i = new Intent();
                    i.putExtra("ID", String.valueOf(contact.getId()));
                    setResult(1, i);
                }
                this.finish();
                break;
            case R.id.btnEdit:
                Intent intent = new Intent(getBaseContext(), AddActivity.class);
                intent.putExtra("ID", contact.getId());
                startActivityForResult(intent, 3);
                break;
            case R.id.btnDelete:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setTitle("Delete this contact");
                alertBuilder.setMessage("Are you Delete this Contact?");
                alertBuilder.setCancelable(true);
                alertBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteContact(contact);
                        db.close();
                        Intent intent = new Intent();
                        intent.putExtra("ID", String.valueOf(contact.getId()));
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                alertBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == RESULT_OK) {
            contact = (Contact) data.getExtras().getSerializable("RETURN");
            setData();
            edited = true;
        }
    }
}
