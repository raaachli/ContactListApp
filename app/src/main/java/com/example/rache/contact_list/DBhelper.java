package com.example.rache.contact_list;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "contact_list.db";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_CONTACTS="CREATE TABLE "+ Contacts.TABLE+"("
                +Contacts.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +Contacts.KEY_name+" TEXT, "
                +Contacts.KEY_phonenumber+" TEXT)";
        db.execSQL(CREATE_TABLE_CONTACTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Contacts.TABLE);
        onCreate(db);
    }
}
