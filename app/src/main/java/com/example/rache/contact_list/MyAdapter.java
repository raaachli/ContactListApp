package com.example.rache.contact_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    public ArrayList<Contacts> list;
    private Context context;
    private LayoutInflater inflater = null;

    public MyAdapter(ArrayList<Contacts> list, Context context1) {
        this.context = context1;
        this.list = list;
        inflater = LayoutInflater.from(context1);
        for(int i=0; i<list.size();i++)
        {
            list.get(i).setChecked(false);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview, null);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.text_name);
            //viewHolder.id = (TextView) convertView.findViewById(R.id.contact_Id);
            viewHolder.cb = (CheckBox) convertView.findViewById(R.id.check_box);
            //c.image = (ImageButton) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.tv.setText(list.get(position).getName());
        //viewHolder.id.setText(list.get(position).getId());
        viewHolder.cb.setChecked(list.get(position).getChecked());
        //c.image.setImageResource(list.get(position).getImage());
        viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                    list.get(position).setChecked(true);
                else
                    list.get(position).setChecked(false);
            }
        });
        return convertView;
    }

    public final class ViewHolder
    {
        public TextView id;
        public TextView tv;
        public CheckBox cb;
        //public ImageButton image;
    }
}
