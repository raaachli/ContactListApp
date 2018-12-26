package com.example.rache.contact_list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vatsal.imagezoomer.ZoomAnimation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class ContactProfileFragment extends Fragment {
    private TextView Name;
    private TextView Number;
    private RelationshipAdapter mAdapter;
    private ListView relationshipList;
    private ArrayList<Contacts> list;
    private ImageView photo;
    private Bitmap bitmap;
    //private ZoomAnimation zoomAnimation = new ZoomAnimation(getActivity());

    public ContactProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Name = (TextView) getActivity().findViewById(R.id.p_enter_name);
        Number = (TextView) getActivity().findViewById(R.id.p_enter_phone);
        relationshipList = (ListView) getActivity().findViewById(R.id.p_relation_list);
        photo = (ImageButton)getActivity().findViewById(R.id.photo);
        Contacts Contact = new Contacts();
        Serializable r_list = getActivity().getIntent().getSerializableExtra("Contact");
        Contact = (Contacts) r_list;
        Name.setText(Contact.getName());
        Number.setText(Contact.getNumber());
        list = new ArrayList<>();
        list = Contact.getList();

        String filename = Contact.getName();
        //Toast.makeText(getActivity(), dd, Toast.LENGTH_SHORT).show();
        String path = "/" + filename + ".png";
        File mFile=new File(Environment.getExternalStorageDirectory(),path);//find path
        if (mFile.exists())
        {
            bitmap= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+path);
            photo.setImageBitmap(bitmap);
        }
        else
        {
            Toast.makeText(getActivity(), "photo not exist", Toast.LENGTH_SHORT).show();
            photo.setImageResource(R.mipmap.ic_launcher);
        }
        mAdapter = new RelationshipAdapter(list,getActivity());
        relationshipList.setAdapter(mAdapter);
        relationshipList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent p_Intent = new Intent();
                p_Intent.setClass(getActivity(), ContactProfileActivity.class);
                p_Intent.putExtra("Contact",list.get(position));
                startActivity(p_Intent);
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

}
