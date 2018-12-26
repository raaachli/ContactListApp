package com.example.rache.contact_list;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivityFragment extends Fragment {
    private Button addButton;
    private Button deleteButton;
    private ListView contactList;
    private MyAdapter mAdapter;
    private ArrayList<Contacts> list;//
    private DBhelper dBhelper;
    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_activity, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedState)
    {
        super.onActivityCreated(savedState);
        addButton = (Button)getActivity().findViewById(R.id.add_btn);
        deleteButton = (Button)getActivity().findViewById(R.id.delete_btn);
        contactList = (ListView) getActivity().findViewById(R.id.contact_list);
        Serializable list2 = getActivity().getIntent().getSerializableExtra("List2");
        list = (ArrayList<Contacts>) list2;
        final ContactCRUD contactCRUD = new ContactCRUD(getActivity());

        if(list == null)
        {
            list = contactCRUD.getContactsList();
        }

        mAdapter = new MyAdapter(list,getActivity());
        contactList.setAdapter(mAdapter);
        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ContactDetailsActivity.class);
                intent.putExtra("List",(Serializable)list);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ContactCRUD contactCRUD1 = new ContactCRUD(getActivity());
                ArrayList<Contacts> deleteList = new ArrayList<Contacts>();
                for(int i=0;i<list.size();i++){
                    if (list.get(i).getChecked())
                    {
                        deleteList.add(list.get(i));
                        contactCRUD1.Delete(list.get(i).getName());
                    }
                }
                list.removeAll(deleteList);
                for(int j=0;j<list.size();j++) {
                    for (int k = 0; k < deleteList.size(); k++) {
                        for (int m = 0; m < list.get(j).relationship.size(); m++) {
                            if (list.get(j).relationship.get(m).getName().equals(deleteList.get(k).getName()))
                                list.get(j).relationship.remove(m);
                        }
                    }
                }
                deleteList.clear();
                Toast.makeText(getActivity(), "DELETED", Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
            }
        });

        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent profile_intent = new Intent();
                profile_intent.setClass(getActivity(),ContactProfileActivity.class);
                profile_intent.putExtra("Contact",list.get(position));
                profile_intent.putExtra("List",list);
                startActivity(profile_intent);
            }
        });
    }

}
