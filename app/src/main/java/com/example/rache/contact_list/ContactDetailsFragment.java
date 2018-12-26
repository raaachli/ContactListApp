package com.example.rache.contact_list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ContactDetailsFragment extends Fragment {
    private EditText edit_name;
    private EditText inputNumber;
    private Button add_btn;
    private ListView Relationship;
    private MyAdapter myAdapter;
    private ArrayList<Contacts> list;
    private ImageButton image;
    public static final int CAM_REQUEST=1111;
    public final static String SAVED_IMAGE_PATH=
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    private boolean imagefileFlag=false;
    private File imageFile;
    private String photoName;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAM_REQUEST) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(thumbnail);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, bytes);

            photoName = edit_name.getText().toString();
            String filePath = Environment.getExternalStorageDirectory()
                    + "/"
                    + photoName
                    + ".png";
            File file = new File(filePath);
            try {
                file.createNewFile();
                FileOutputStream fo = new FileOutputStream(file);
                //5
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public ContactDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_details, container, false);
    }

    private boolean hasSDCard() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    private boolean initImageFile() {
        if (hasSDCard()) {
            photoName = edit_name.getText().toString();
            String filePath = Environment.getExternalStorageDirectory()
                    + "/"
                    + photoName
                    + ".png";
            imageFile = new File(filePath);
            if (!imageFile.exists()) {
                try {
                    imageFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        edit_name = (EditText) getActivity().findViewById(R.id.edit_name);
        inputNumber = (EditText) getActivity().findViewById(R.id.edit_number);
        add_btn = (Button) getActivity().findViewById(R.id.add_person_btn);
        Relationship = (ListView) getActivity().findViewById(R.id.relationship_list);
        image = (ImageButton)getActivity().findViewById(R.id.image);
        final long duration = 500;
        imagefileFlag=false;

        Serializable list1 = getActivity().getIntent().getSerializableExtra("List");
        list = (ArrayList<Contacts>) list1;

        myAdapter = new MyAdapter(list, getActivity());
        Relationship.setAdapter(myAdapter);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactCRUD contactCRUD = new ContactCRUD(getActivity());
                Contacts contact = new Contacts();
                contact.setName(edit_name.getText().toString());
                contact.setNumber(inputNumber.getText().toString());
                //contact.setPhoto_Id(photoName);
                //String dd = String.valueOf(photoName);
                //Toast.makeText(getActivity(), dd, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), (int) photoName, Toast.LENGTH_SHORT).show();
                contact.setChecked(false);
                list.add(contact);
                contactCRUD.Insert(contact);
                for(int i=0;i<list.size();i++){
                    if (list.get(i).getChecked()) {
                        contact.setRelationName(list.get(i));
                        list.get(i).setRelationName(contact);
                    }}
                myAdapter.notifyDataSetChanged();
                Intent intent = new Intent();
                intent.setClass(getActivity(), MainActivity.class);
                intent.putExtra("List2", (Serializable)list);
                startActivity(intent);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTakePhoto();
            }
        });
    }

    public void startTakePhoto(){
        Toast.makeText(getActivity(), "ADDING PHOTO", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        startActivityForResult(intent,CAM_REQUEST);
        imagefileFlag = true;
    }
}


