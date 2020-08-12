package com.poly.lab5nc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterMyWeb extends BaseAdapter {
    Context context;
    List<MyItem> myItemList;

    public AdapterMyWeb(Context context, List<MyItem> myItemList) {
        this.context = context;
        this.myItemList = myItemList;
    }

    @Override
    public int getCount() {
        return myItemList.size();
    }

    @Override
    public MyItem getItem(int position) {
        return myItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final MyItem myItem = getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.my_row, parent, false);
        TextView tv_title = convertView.findViewById(R.id.tv_title),
                tv_description = convertView.findViewById(R.id.tv_description);
        tv_title.setText(myItem.title);
        tv_description.setText(myItem.description);
        return convertView;
    }
}
