package com.binhtranphuoc97.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BinhTran on 10/19/2017.
 */

public class MyDatabase  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="Contact_MG";
    private static final String TABLE_NAME ="Contact";
    private static final int VERSION =1;
    private static final String ID ="id";
    private static final String NAME ="name";
    private static final String PHONE ="number";
    private static final String ADDRESS ="address";
    private static final String DATE ="date";
    private static final String TIME ="time";
    private static final String GENDER ="gender";

    private Context context;
    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY, " +
                NAME + " TEXT, " +
                PHONE + " TEXT, " +
                ADDRESS + " TEXT, " +
                DATE + " TEXT, " +
                TIME + " TEXT, " +
                GENDER + " TEXT" + ")";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
        Toast.makeText(context, "Drop successfully", Toast.LENGTH_SHORT).show();
    }

    public int addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, contact.getName());
        values.put(PHONE, contact.getNumber());
        values.put(ADDRESS, contact.getAddress());
        values.put(DATE, contact.getDate());
        values.put(TIME, contact.getTime());
        values.put(GENDER, contact.getGender());

        db.insert(TABLE_NAME, null, values);

        db = this.getReadableDatabase();
        String id = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + ID + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(id, null);
        if (cursor != null)
            cursor.moveToFirst();
        db.close();
        return Integer.parseInt(cursor.getString(0));
    }

    // lấy contact theo id
    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{ID, NAME, PHONE, ADDRESS, DATE, TIME, GENDER},
                ID + " =?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3)
                , cursor.getString(4), cursor.getString(5), cursor.getString(6));
        return contact;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        String sql = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setNumber(cursor.getString(2));
                contact.setAddress(cursor.getString(3));
                contact.setDate(cursor.getString(4));
                contact.setTime(cursor.getString(5));
                contact.setGender(cursor.getString(6));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }


    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, contact.getName());
        values.put(PHONE, contact.getNumber());
        values.put(ADDRESS, contact.getAddress());
        values.put(DATE, contact.getDate());
        values.put(TIME, contact.getTime());
        values.put(GENDER, contact.getGender());

        return db.update(TABLE_NAME, values, ID + " =?", new String[]{String.valueOf(contact.getId())});
    }

    public void deleteAllContact() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " =?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }
}
