package com.service.business.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.service.business.R;

import java.util.ArrayList;
import java.util.List;


public  class ShopCarAdapter extends BaseAdapter {
    Context context;
    private ArrayList<String> datas = new ArrayList<>();
    public ShopCarAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.layout_item, null);
        TextView viewById = view.findViewById(R.id.tv_goods);
//        viewById.setText(childrenItem.get(position));
        return view;
    }

    public void addData(List data, boolean refresh) {
        if (refresh) {
            datas.clear();
        }
        datas.addAll(data);
        notifyDataSetChanged();
    }
}