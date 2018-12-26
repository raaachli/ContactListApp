package com.example.rache.contact_list;

import android.media.Image;

import java.io.Serializable;
import java.util.ArrayList;

public class Contacts implements Serializable {

    public static final String TABLE="Contacts";
    public static final String KEY_ID="id";
    public static final String KEY_name="contact_name";
    public static final String KEY_phonenumber="phone_number";

    public int contact_Id;
    public String contact_name;
    public String phone_number;
    public Boolean checked;
    public ArrayList<Contacts> relationship = new ArrayList<>();
    public long photo_Id;

    public int getId(){
        return contact_Id;
    }
    public Boolean getChecked(){return checked;}
    public String getName(){
        return  contact_name;
    }
    public  String getNumber(){
        return  phone_number;
    }
    public long getPhoto(){
        return photo_Id;
    }

    public void setId(int id){
        this.contact_Id = id;
    }

    public void setName(String name){
        this.contact_name = name;
    }

    public void setNumber(String number){
        this.phone_number = number;
    }

    public void setChecked(Boolean checking){
        this.checked = checking;
    }

    public void setRelationName(Contacts Name)
    {
        this.relationship.add(Name);
    }

    public ArrayList<Contacts> getList()
    {
        return this.relationship;
    }

}
