package com.example.rache.contact_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RelationshipAdapter extends BaseAdapter {
    public ArrayList<Contacts> list;
    private Context context;
    private LayoutInflater inflater = null;

    public final class viewHolder
    {
        public TextView tv;
    }
    public RelationshipAdapter(ArrayList<Contacts> list, Context context)
    {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        viewHolder vh = null;
        if (convertView == null)
        {
            vh = new viewHolder();
            convertView = inflater.inflate(R.layout.relation_listview, null);
            vh.tv = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(vh);
        }
        else
            vh = (viewHolder) convertView.getTag();
        vh.tv.setText(list.get(position).getName());
        return convertView;
    }
}
