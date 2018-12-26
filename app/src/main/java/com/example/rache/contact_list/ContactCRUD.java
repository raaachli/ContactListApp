package com.example.rache.contact_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactCRUD {

    private DBhelper DBhelper;

    public ContactCRUD(Context context){
        DBhelper = new DBhelper(context);
    }

    public int Insert(Contacts contacts){
        //insert contacts to database by special id
        SQLiteDatabase db=DBhelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Contacts.KEY_name,contacts.contact_name);
        values.put(Contacts.KEY_phonenumber,contacts.phone_number);

        long contact_Id=db.insert(Contacts.TABLE,null,values);
        db.close();
        return (int)contact_Id;
    }

    public void Delete(String name){
        //delete the items in database by name
        SQLiteDatabase db=DBhelper.getWritableDatabase();
        db.delete(Contacts.TABLE,Contacts.KEY_name+"=?", new String[]{name});
        db.close();
    }

    public void Update(Contacts contacts){
        SQLiteDatabase db=DBhelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Contacts.KEY_name,contacts.contact_name);
        values.put(Contacts.KEY_phonenumber,contacts.phone_number);
        db.update(Contacts.TABLE,values,Contacts.KEY_ID+"=?",new String[] { String.valueOf(contacts.contact_Id) });
        db.close();
    }

    public ArrayList<Contacts> getContactsList(){
        SQLiteDatabase db=DBhelper.getReadableDatabase();
        String selectQuery="SELECT "+
                Contacts.KEY_ID+","+
                Contacts.KEY_name+","+
                Contacts.KEY_phonenumber+" FROM "+Contacts.TABLE;

        ArrayList<Contacts> contactList=new ArrayList<Contacts>();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                Contacts contact=new Contacts();
                contact.contact_Id = cursor.getInt(cursor.getColumnIndex(Contacts.KEY_ID));
                contact.contact_name = cursor.getString(cursor.getColumnIndex(Contacts.KEY_name));
                contact.phone_number = cursor.getString(cursor.getColumnIndex(Contacts.KEY_phonenumber));
                contactList.add(contact);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contactList;
    }

    public void getNewContactsList(){
        SQLiteDatabase db=DBhelper.getReadableDatabase();
        String reorder = "update sqlite_sequence set seq=0 where name='Contacts'";
        db.execSQL(reorder);
    }

    public Contacts getContactById(int Id){
        SQLiteDatabase db=DBhelper.getReadableDatabase();
        String selectQuery="SELECT "+
                Contacts.KEY_ID + "," +
                Contacts.KEY_name + "," +
                Contacts.KEY_phonenumber +
                " FROM " + Contacts.TABLE
                + " WHERE " +
                Contacts.KEY_ID + "=?";
        //int iCount=0;
        Contacts contact=new Contacts();
        Cursor cursor=db.rawQuery(selectQuery,new String[]{String.valueOf(Id)});
        if(cursor.moveToFirst()){
            do{
                contact.contact_Id = cursor.getInt(cursor.getColumnIndex(Contacts.KEY_ID));
                contact.contact_name = cursor.getString(cursor.getColumnIndex(Contacts.KEY_name));
                contact.phone_number  = cursor.getString(cursor.getColumnIndex(Contacts.KEY_phonenumber));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contact;

    }



}