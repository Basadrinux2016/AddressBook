package com.example.addressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.addressbook.model.Contact;

import java.util.ArrayList;

/**
 * Created by angel on 07/09/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "addressbook.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_LASTNAME = "lastname";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    public static final String CONTACTS_COLUMN_ADDRESS = "phone";


    public DBHelper(Context ctx){
        super(ctx,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table contacts " +
                        "(id integer primary key, name text,lastname text, phone text,email text,address text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertContact (Contact contact){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",contact.getName());
        contentValues.put("lastname",contact.getLastname());
        contentValues.put("phone",contact.getPhone());
        contentValues.put("email",contact.getEmail());
        contentValues.put("address",contact.getAddress());
        db.insert("contacts",null,contentValues);
        return true;
    }

    public boolean updateContact (Contact contact){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",contact.getId());
        contentValues.put("name",contact.getName());
        contentValues.put("lastname",contact.getLastname());
        contentValues.put("phone",contact.getPhone());
        contentValues.put("email",contact.getEmail());
        contentValues.put("address",contact.getAddress());
        db.update("contacts",contentValues,"id=?", new String[]{ contact.getId() } );
        return true;
    }

    public Contact getData(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        res.moveToFirst();
        Contact contact = new Contact();
        contact.setName(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
        contact.setLastname(res.getString(res.getColumnIndex(CONTACTS_COLUMN_LASTNAME)));
        contact.setAddress(res.getString(res.getColumnIndex(CONTACTS_COLUMN_ADDRESS)));
        contact.setPhone(res.getString(res.getColumnIndex(CONTACTS_COLUMN_PHONE)));
        contact.setEmail(res.getString(res.getColumnIndex(CONTACTS_COLUMN_EMAIL)));
        return contact;
    }

    public ArrayList<Contact> getAllContacts()
    {
        ArrayList<Contact> array_list = new ArrayList<Contact>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(!res.isAfterLast()){

            Contact contact = new Contact();
            contact.setId(res.getString(res.getColumnIndex(CONTACTS_COLUMN_ID)));
            contact.setName(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            contact.setLastname(res.getString(res.getColumnIndex(CONTACTS_COLUMN_LASTNAME)));
            contact.setAddress(res.getString(res.getColumnIndex(CONTACTS_COLUMN_ADDRESS)));
            contact.setPhone(res.getString(res.getColumnIndex(CONTACTS_COLUMN_PHONE)));
            contact.setEmail(res.getString(res.getColumnIndex(CONTACTS_COLUMN_EMAIL)));

            array_list.add(contact);
            res.moveToNext();
        }
        return array_list;
    }
}
